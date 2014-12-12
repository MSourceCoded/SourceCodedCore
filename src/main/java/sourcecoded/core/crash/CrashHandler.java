package sourcecoded.core.crash;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ICrashCallable;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.versioning.ArtifactVersion;
import sourcecoded.core.Constants;
import sourcecoded.core.SourceCodedCore;
import sourcecoded.core.version.VersionChecker;

import java.util.ArrayList;
import java.util.List;

public class CrashHandler implements ICrashCallable {

    public static void init() {
        FMLCommonHandler.instance().registerCrashCallable(new CrashHandler());
    }

    @Override
    public String getLabel() {
        return "SourceCodedCore";
    }

    @Override
    public String call() throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("SourceCodedCore detected Crash. Details are as follows \n");
        builder.append("\t\tLoaded SCC Mods:\n");

        ArrayList<String> packages = new ArrayList<String>();

        injectData(builder, SourceCodedCore.getContainer(Constants.MODID), packages);
        for (ModContainer container : Loader.instance().getModList()) {
            for (ArtifactVersion vers : container.getRequirements())
                if (vers.getLabel().contains(Constants.MODID))
                    injectData(builder, container, packages);
        }

        Runtime.getRuntime().addShutdownHook(new ThreadCrash(packages));
        return builder.toString();
    }

    void injectData(StringBuilder builder, ModContainer container, List<String> packages) {
        builder.append("\t\t\t" + container.getModId() + "@" + container.getVersion());

        packages.addAll(container.getOwnedPackages());

        for (VersionChecker check : VersionChecker.checkers)
            if (check.modid.equals(container.getModId()))
                if (check.newVersionAvailable)
                    builder.append("\t(OUTDATED! Online Version: " + check.onlineParsed + ")");

        builder.append("\n");
    }
}
