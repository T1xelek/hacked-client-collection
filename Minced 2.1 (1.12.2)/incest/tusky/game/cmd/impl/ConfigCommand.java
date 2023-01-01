package incest.tusky.game.cmd.impl;

import com.mojang.realmsclient.gui.ChatFormatting;
import incest.tusky.game.tuskevich;
import incest.tusky.game.cmd.CommandAbstract;
import incest.tusky.game.ui.config.Config;
import incest.tusky.game.ui.config.ConfigManager;
import incest.tusky.game.utils.other.ChatUtils;

public class ConfigCommand extends CommandAbstract {

    public ConfigCommand() {
        super("config", "configurations", ".config" + ChatFormatting.RED + " save | load | delete | list" + "<name>", "config");
    }

    @Override
    public void execute(String... args) {
        try {
            if (args.length >= 2) {
                String upperCase = args[1].toUpperCase();
                if (args.length == 3) {
                    switch (upperCase) {
                        case "LOAD":
                            if (tuskevich.instance.configManager.loadConfig(args[2])) {
                                ChatUtils.addChatMessage(ChatFormatting.GREEN + "Successfully " + ChatFormatting.WHITE + "loaded config: " + ChatFormatting.RED + "\"" + args[2] + "\"");
                            } else {
                                ChatUtils.addChatMessage(ChatFormatting.RED + "Failed " + ChatFormatting.WHITE + "load config: " + ChatFormatting.RED + "\"" + args[2] + "\"");
                            }
                            break;
                        case "SAVE":
                            if (tuskevich.instance.configManager.saveConfig(args[2])) {
                                ChatUtils.addChatMessage(ChatFormatting.GREEN + "Successfully " + ChatFormatting.WHITE + "saved config: " + ChatFormatting.RED + "\"" + args[2] + "\"");
                                ConfigManager.getLoadedConfigs().clear();
                                tuskevich.instance.configManager.load();
                            } else {
                                ChatUtils.addChatMessage(ChatFormatting.RED + "Failed " + ChatFormatting.WHITE + "to save config: " + ChatFormatting.RED + "\"" + args[2] + "\"");
                            }
                            break;
                        case "DELETE":
                            if (tuskevich.instance.configManager.deleteConfig(args[2])) {
                                ChatUtils.addChatMessage(ChatFormatting.GREEN + "Successfully " + ChatFormatting.WHITE + "deleted config: " + ChatFormatting.RED + "\"" + args[2] + "\"");
                            } else {
                                ChatUtils.addChatMessage(ChatFormatting.RED + "Failed " + ChatFormatting.WHITE + "to delete config: " + ChatFormatting.RED + "\"" + args[2] + "\"");
                            }
                            break;
                    }
                } else if (args.length == 2 && upperCase.equalsIgnoreCase("LIST")) {
                    ChatUtils.addChatMessage(ChatFormatting.GREEN + "Configs:");
                    for (Config config : tuskevich.instance.configManager.getContents()) {
                        ChatUtils.addChatMessage(ChatFormatting.RED + config.getName());
                    }
                }
            } else {
                ChatUtils.addChatMessage(this.getUsage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}