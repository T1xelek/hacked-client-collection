package incest.tusky.game.module.impl.Combat;

import incest.tusky.game.tuskevich;
import incest.tusky.game.event.EventTarget;
import incest.tusky.game.event.events.impl.player.EventUpdate;
import incest.tusky.game.module.Module;
import incest.tusky.game.ui.settings.impl.BooleanSetting;
import incest.tusky.game.ui.settings.impl.NumberSetting;
import incest.tusky.game.module.ModuleCategory;
import incest.tusky.game.friend.Friend;
import incest.tusky.game.utils.movement.MovementUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;

public class TriggerBot extends Module {

    public static NumberSetting range;
    public static BooleanSetting players;
    public static BooleanSetting mobs;
    public static BooleanSetting onlyCrit = new BooleanSetting("Only Crits", false, () -> true);
    public static NumberSetting critFallDist = new NumberSetting("Fall Distance", 0.2F, 0.08F, 1, 0.01f, () -> onlyCrit.getCurrentValue());

    public TriggerBot() {
        super("TriggerBot", "Автоматически аттакует энтити(не наводится)", ModuleCategory.Combat);
        players = new BooleanSetting("Players", true, () -> true);
        mobs = new BooleanSetting("Mobs", false, () -> true);
        range = new NumberSetting("Trigger Range", 4, 1, 6, 0.1f, () -> true);
        addSettings(range, players, mobs, onlyCrit, critFallDist);
    }

    public static boolean canTrigger(EntityLivingBase player) {
        for (Friend friend : tuskevich.instance.friendManager.getFriends()) {
            if (!player.getName().equals(friend.getName())) {
                continue;
            }
            return false;
        }

        if (player instanceof EntityPlayer || player instanceof EntityAnimal || player instanceof EntityMob || player instanceof EntityVillager) {
            if (player instanceof EntityPlayer && !players.getCurrentValue()) {
                return false;
            }
            if (player instanceof EntityAnimal && !mobs.getCurrentValue()) {
                return false;
            }
            if (player instanceof EntityMob && !mobs.getCurrentValue()) {
                return false;
            }
            if (player instanceof EntityVillager && !mobs.getCurrentValue()) {
                return false;
            }
        }
        return player != mc.player;
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        Entity entity = mc.objectMouseOver.entityHit;
        if (entity == null || mc.player.getDistanceToEntity(entity) > range.getCurrentValue() || entity instanceof EntityEnderCrystal || entity.isDead || ((EntityLivingBase) entity).getHealth() <= 0.0f) {
            return;
        }

        if (MovementUtils.isBlockAboveHead()) {
            if (!(mc.player.fallDistance >= critFallDist.getCurrentValue()) && mc.player.getCooledAttackStrength(0.8F) == 1 && onlyCrit.getCurrentValue() && !mc.player.isOnLadder() && !mc.player.isInLiquid() && !mc.player.isInWeb && mc.player.getRidingEntity() == null) {
                return;
            }
        } else {
            if (mc.player.fallDistance != 0 && onlyCrit.getCurrentValue() && !mc.player.isOnLadder() && !mc.player.isInLiquid() && !mc.player.isInWeb && mc.player.getRidingEntity() == null) {
                return;
            }
        }

        if (canTrigger((EntityLivingBase) entity)) {
            if (mc.player.getCooledAttackStrength(0) == 1) {
                mc.playerController.attackEntity(mc.player, entity);
                mc.player.swingArm(EnumHand.MAIN_HAND);
            }
        }
    }
}

