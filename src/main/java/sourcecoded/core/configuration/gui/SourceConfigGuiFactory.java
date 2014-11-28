package sourcecoded.core.configuration.gui;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.IModGuiFactory;
import cpw.mods.fml.client.config.IConfigElement;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.util.EnumHelper;
import sourcecoded.core.Constants;
import sourcecoded.core.SourceCodedCore;
import sourcecoded.core.configuration.SourceConfig;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

public enum SourceConfigGuiFactory implements IModGuiFactory {
    ;

    public static Field guiFactoriesField;
    public static BiMap<ModContainer, IModGuiFactory> guiFactories = HashBiMap.create();

    public static boolean injectionAllowed = true;

    //In my defence, all the GuiFactory stuff in FML doesn't let you pass your own instance, only Classes/Class names, hence the mess below

    @SuppressWarnings("unchecked")
    public static void finishLoading() throws IllegalAccessException {
        guiFactoriesField = ReflectionHelper.findField(FMLClientHandler.class, "guiFactories");

        BiMap<ModContainer, IModGuiFactory> guiFactoriesOld = guiFactories;

        guiFactories = (BiMap<ModContainer, IModGuiFactory>) guiFactoriesField.get(FMLClientHandler.instance());
        guiFactories.remove(SourceCodedCore.getContainer(Constants.MODID));             //Remove the GUI factory dummy

        guiFactories.putAll(guiFactoriesOld);

        injectionAllowed = false;
    }

    public static void end() {
        try {
            SourceConfigGuiFactory.finishLoading();
        } catch (IllegalAccessException e) {
            SourceCodedCore.logger.warn("SourceConfigGuiFactory could not finish loading: Field Error");
            e.printStackTrace();
        }
    }

    public String modid;
    public Object modInstance;
    public SourceConfig config;

    @SuppressWarnings("unchecked")
    SourceConfigGuiFactory(String modid, Object instance, SourceConfig config) {
        this.modid = modid;
        this.modInstance = instance;
        this.config = config;
    }

    public static SourceConfigGuiFactory create(String modid, Object modInstance, SourceConfig config) {
        Class[] types = new Class[] {String.class, Object.class, SourceConfig.class};
        Object[] params = new Object[] {modid, modInstance, config};
        return EnumHelper.addEnum(SourceConfigGuiFactory.class, modid.toUpperCase(), types, params);
    }

    public static SourceConfigGuiFactory get(String modid) {
        for (SourceConfigGuiFactory factory : SourceConfigGuiFactory.values())
            if (factory.modid.equalsIgnoreCase(modid))
                return factory;

        return null;
    }

    public void inject() {
        if (injectionAllowed) {
            ModContainer container = SourceCodedCore.getContainer(modid);
            if (container != null) {
                guiFactories.put(container, this);
            } else {
                SourceCodedCore.logger.warn("Could not find container for mod: " + modid);
                SourceCodedCore.logger.warn("Skipping GuiFactory injection");
            }
        } else {
            SourceCodedCore.logger.warn("Could not inject GuiFactory for mod: " + modid);
            SourceCodedCore.logger.warn("Injection has already taken place");
        }
    }

    @Override
    public void initialize(Minecraft minecraftInstance) {
    }

    @Override
    public Class<? extends GuiScreen> mainConfigGuiClass() {
        SourceConfigBaseGui.injectGuiContext(this);
        return SourceConfigBaseGui.class;
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return ImmutableSet.of();
    }

    @Override
    public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
        return null;
    }

    public static List<IConfigElement> createElements(SourceConfig config) {
        List<IConfigElement> list = Lists.newArrayList();

        for (String cat : config.config.getCategoryNames()) {
            list.add(new ConfigElement(config.config.getCategory(cat)));
        }

        return list;
    }
}
