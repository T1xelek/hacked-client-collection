package incest.tusky.game.module.impl.Combat;

import incest.tusky.game.event.EventTarget;
import incest.tusky.game.event.events.Event;
import incest.tusky.game.event.events.impl.player.EventPreMotion;
import incest.tusky.game.module.Module;
import incest.tusky.game.module.ModuleCategory;
import incest.tusky.game.tuskevich;
import incest.tusky.game.ui.settings.impl.NumberSetting;
import incest.tusky.game.utils.particle.EventAttackEntity;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class BackTrack extends Module {

    public static NumberSetting foward = new NumberSetting("Foward", 1, 1.0f, 100.0f, 1.0f, () -> true);
    public static NumberSetting amount = new NumberSetting("Amount", 1, 1.0f, 100.0f, 1.0f, () -> true);

    private int ticks;
    public static EntityLivingBase target;
    public static List<Vec3d> pastPositions = new ArrayList<>();
    public static List<Vec3d> forwardPositions = new ArrayList<>();
    public static List<Vec3d> positions = new ArrayList<>();
    private final Deque<Packet<?>> packets = new ArrayDeque<>();


    public BackTrack() {
        super("BackTrack", "Бектрек а чо пон", ModuleCategory.Combat);
        addSettings(foward, amount);
    }

    @EventTarget
    public void onUpdate(Event event) {
        if (tuskevich.instance.featureManager.getFeature(BackTrack.class).isEnabled()) {
            target = null;
            positions.clear();
            pastPositions.clear();
            forwardPositions.clear();
            packets.clear();
            for (int i = 0; i < amount.getNumberValue(); i++) {
                mc.world.removeEntityFromWorld(-20 - i);
            }
            return;
        }
        if (event instanceof EventAttackEntity) {
            if (((EventAttackEntity) event).getEntity() instanceof EntityPlayer && ((EventAttackEntity) event).getEntity().getEntityId() != -20)
                target = (EntityLivingBase) ((EventAttackEntity) event).getEntity();
            if (((EventAttackEntity) event).getEntity().getEntityId() == -20 && target != null)
                ((EventAttackEntity) event).setEntity(target);
            ticks = 0;
        }
        if (event instanceof EventPreMotion) {
            if (mc.player.ticksExisted < 5) {
                //target = null;
			      /*  positions.clear();
			        pastPositions.clear();
			        forwardPositions.clear();
			        packets.clear();*/
                for (int i = 0; i < amount.getNumberValue(); i++) {
                    mc.world.removeEntityFromWorld(-20 - i);
                }
                return;
            }

            if (target == null) return;
            if (mc.player.ticksExisted % 50 == 0) {
                for (int i = 0; i < amount.getNumberValue(); i++) {
                    mc.world.removeEntityFromWorld(-20 - i);
                }
            }
            EntityOtherPlayerMP player = new EntityOtherPlayerMP(mc.world, ((EntityPlayer) target).getGameProfile());
            player.copyLocationAndAnglesFrom(target);
            player.rotationYawHead = target.rotationYawHead;
            pastPositions.add(new Vec3d(target.posX, target.posY, target.posZ));
            mc.world.addEntityToWorld(-20 - pastPositions.size(), player);
            final double deltaX = (target.posX - target.lastTickPosX) * 2;
            final double deltaZ = (target.posZ - target.lastTickPosZ) * 2;

            forwardPositions.clear();
            int i = 0;
            while (foward.getNumberValue() > forwardPositions.size()) {
                i++;
                forwardPositions.add(new Vec3d(target.posX + deltaX * i, target.posY, target.posZ + deltaZ * i));
            }

            while (pastPositions.size() > (int) amount.getNumberValue()) {
                pastPositions.remove(0);
            }

            positions.clear();
            positions.addAll(forwardPositions);
            positions.addAll(pastPositions);

            ticks++;
        }
    }
}