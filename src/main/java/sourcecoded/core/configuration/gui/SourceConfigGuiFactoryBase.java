package sourcecoded.core.configuration.gui;

import com.google.common.collect.ImmutableSet;
import cpw.mods.fml.client.IModGuiFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import sourcecoded.core.Constants;

import java.util.Set;

public class SourceConfigGuiFactoryBase implements IModGuiFactory {

    SourceConfigGuiFactory factory;

    public void set(SourceConfigGuiFactory factory) {
        this.factory = factory;
    }

    @Override
    public void initialize(Minecraft minecraftInstance) {
        SourceConfigGuiFactory.end();
    }

    @Override
    public Class<? extends GuiScreen> mainConfigGuiClass() {
        if (factory == null)
            factory = SourceConfigGuiFactory.get(Constants.MODID);

        SourceConfigBaseGui.injectGuiContext(factory);

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
}
