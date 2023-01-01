package incest.tusky.game.cmd.impl;

import com.mojang.realmsclient.gui.ChatFormatting;

import incest.tusky.game.tuskevich;
import incest.tusky.game.cmd.CommandAbstract;
import incest.tusky.game.module.impl.Player.DeathCoordinates;
import incest.tusky.game.module.impl.Render.AirDropWay;
import incest.tusky.game.utils.movement.MovementUtils;
import incest.tusky.game.utils.other.ChatUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketPlayer;

import static incest.tusky.game.utils.math.KillauraUtils.timerHelper;

public class TpCommand
        extends CommandAbstract {
    Minecraft mc;

    public TpCommand() {
        super("tp", "tp", "\u00a76.tp x y z", "tp");
        this.mc = Minecraft.getMinecraft();
    }

    @Override
    public /* synthetic */ void execute(String ... args) {
        if (args.length > 1) {
            float endX = (float)Double.parseDouble(args[1]);
            float endY = (float)Double.parseDouble(args[2]);
            float endZ = (float)Double.parseDouble(args[3]);
            if (this.mc.player.ticksExisted % 1 == 0) {
                ChatUtils.addChatMessage(" Мобилизирую на заданные мне координаты " + endX + " " + endY + " " + endZ);
                mc.player.motionY = 0.05f;
                mc.player.connection.sendPacket(new CPacketPlayer.Position(endX, endY + 9, endZ, true));
                mc.player.connection.sendPacket(new CPacketPlayer.Position(endX, endY, endZ, false));
                mc.player.connection.sendPacket(new CPacketPlayer.Position(endX, endY + 9, endZ, true));
                mc.player.connection.sendPacket(new CPacketPlayer.Position(endX, endY, endZ, false));
            }
        }
    }
}