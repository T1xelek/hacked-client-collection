package incest.tusky.game.cmd.impl;

import com.mojang.realmsclient.gui.ChatFormatting;
import incest.tusky.game.tuskevich;
import incest.tusky.game.module.Module;
import incest.tusky.game.utils.other.ChatUtils;
import incest.tusky.game.cmd.CommandAbstract;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.input.Keyboard;

public class BindCommand extends CommandAbstract {

    public BindCommand() {
        super("bind", "bind", ".bind" + ChatFormatting.RED + " add " + "<name> <key> " + TextFormatting.RED + "/" +" .bind " + ChatFormatting.RED + "remove " + "<name> <key>", ".bind" + ChatFormatting.RED + " add " + "<name> <key> | .bind" + ChatFormatting.RED + "remove " + "<name> <key> | .bind" + ChatFormatting.RED + "clear", "bind");
    }

    @Override
    public void execute(String... arguments) {
        try {
            if (arguments.length == 4) {
                String moduleName = arguments[2];
                String bind = arguments[3].toUpperCase();
                Module feature = tuskevich.instance.featureManager.getFeature(moduleName);
                if (arguments[0].equals("bind")) {
                    switch (arguments[1]) {
                        case "add":
                            feature.setBind(Keyboard.getKeyIndex(bind));
                            ChatUtils.addChatMessage(ChatFormatting.GREEN + feature.getLabel() + ChatFormatting.WHITE + " was set on key " + ChatFormatting.RED + "\"" + bind + "\"");
                             break;
                        case "remove":
                            feature.setBind(0);
                            ChatUtils.addChatMessage(ChatFormatting.GREEN + feature.getLabel() + ChatFormatting.WHITE + " bind was deleted from key " + ChatFormatting.RED + "\"" + bind + "\"");
                            break;
                        case "clear":
                            if (!feature.getLabel().equals("ClickGui")) {
                                feature.setBind(0);
                            }
                            break;
                        case "list":
                            if (feature.getBind() == 0) {
                                ChatUtils.addChatMessage(ChatFormatting.RED + "Your macros list is empty!");
                                return;
                            }
                            tuskevich.instance.featureManager.getAllFeatures().forEach(feature1 -> ChatUtils.addChatMessage(ChatFormatting.GREEN + "Binds list: " + ChatFormatting.WHITE + "Binds Name: " + ChatFormatting.RED + feature1.getBind() + ChatFormatting.WHITE + ", Macro Bind: " + ChatFormatting.RED + Keyboard.getKeyName(feature1.getBind())));
                    }
                }
            } else {
                ChatUtils.addChatMessage(this.getUsage());
            }
        } catch (Exception ignored) {

        }
    }
}
