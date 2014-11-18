package sourcecoded.core.version;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class VersionCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "scversion";
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return "/scversion <update> <modid>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length != 2) return;

        VersionChecker checker = getVersCheck(args[1]);

        if (args[0].equalsIgnoreCase("update")) {
            new ThreadDownloadVersion(checker);
        }
    }

    public VersionChecker getVersCheck(String modid) {
        for (VersionChecker checker : VersionChecker.checkers)
            if (checker.modid.equalsIgnoreCase(modid)) return checker;

        return null;
    }
}
