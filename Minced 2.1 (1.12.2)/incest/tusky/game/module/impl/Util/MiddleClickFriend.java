package incest.tusky.game.module.impl.Util;

import com.mojang.realmsclient.gui.ChatFormatting;
import incest.tusky.game.tuskevich;
import incest.tusky.game.event.EventTarget;
import incest.tusky.game.event.events.impl.input.EventMouse;
import incest.tusky.game.module.Module;
import incest.tusky.game.module.ModuleCategory;
import incest.tusky.game.friend.Friend;
import incest.tusky.game.utils.Helper;
import incest.tusky.game.utils.other.ChatUtils;
import net.minecraft.entity.player.EntityPlayer;

public class MiddleClickFriend extends Module {


    public MiddleClickFriend() {
        super("MiddleClickFriend", "Добавить игрока в друзья(колесиком мыши)", ModuleCategory.Other);
    }

    @EventTarget
    public void onMouseEvent(EventMouse event) {
        if (event.getKey() == 2 && Helper.mc.pointedEntity instanceof EntityPlayer) {
            if (tuskevich.instance.friendManager.getFriends().stream().anyMatch(friend -> friend.getName().equals(Helper.mc.pointedEntity.getName()))) {
                tuskevich.instance.friendManager.getFriends().remove(tuskevich.instance.friendManager.getFriend(Helper.mc.pointedEntity.getName()));
                ChatUtils.addChatMessage(ChatFormatting.RED + "Removed " + ChatFormatting.RESET + "'" + Helper.mc.pointedEntity.getName() + "'" + " as Friend!");
            } else {
                tuskevich.instance.friendManager.addFriend(new Friend(Helper.mc.pointedEntity.getName()));
                ChatUtils.addChatMessage(ChatFormatting.GREEN + "Added " + ChatFormatting.RESET + "'" + Helper.mc.pointedEntity.getName() + "'" + " as Friend!");
            }
        }
    }
}