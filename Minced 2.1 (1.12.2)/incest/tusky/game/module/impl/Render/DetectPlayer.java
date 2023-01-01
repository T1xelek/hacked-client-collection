package incest.tusky.game.module.impl.Render;

import incest.tusky.game.drag.comp.impl.DragModuleList;
import incest.tusky.game.event.EventTarget;
import incest.tusky.game.event.events.impl.render.EventBossBar;
import incest.tusky.game.event.events.impl.render.EventRender2D;
import incest.tusky.game.module.Module;
import incest.tusky.game.module.ModuleCategory;
import incest.tusky.game.tuskevich;
import incest.tusky.game.ui.settings.impl.BooleanSetting;
import incest.tusky.game.ui.settings.impl.ColorSetting;
import incest.tusky.game.ui.settings.impl.ListSetting;
import incest.tusky.game.utils.Helper;
import incest.tusky.game.utils.math.AnimationHelper;
import incest.tusky.game.utils.math.MathematicHelper;
import incest.tusky.game.utils.math.RotationHelper;
import incest.tusky.game.utils.otherutils.gayutil.DrawHelper;
import incest.tusky.game.utils.render.ClientHelper;
import incest.tusky.game.utils.render.RenderUtils;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;

import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Comparator;
import java.util.List;

public class DetectPlayer extends Module {

    public DetectPlayer() {
        super("DetectPlayer", "Показывает ближайших игроков в рендере чанков", ModuleCategory.Visual);
    }

    public static float getAngle(BlockPos entity) {
        return (float) (RotationHelper.getRotations(entity.getX(), 0, entity.getZ())[0] - AnimationHelper.Interpolate(mc.player.rotationYaw, mc.player.prevRotationYaw, 1.0D));
    }
    
    @EventTarget
    public void Event2D(EventRender2D event) {
    	int offset = 44;
    	float pY = -12.0F;
    	for (EntityPlayer entityPlayer : mc.world.playerEntities) {
            if (entityPlayer != mc.player) {
            		
            		if (mc.player.getDistanceToEntity(entityPlayer) <= 500) {
    	                String play = entityPlayer.getName();
    	                int x = (int) entityPlayer.posX;
    	                int y = (int) entityPlayer.posY;
    	                int z = (int) entityPlayer.posZ;
    	                int cords = (int) ((int) entityPlayer.posX + entityPlayer.posY + entityPlayer.posZ);
    	                int blocks = (int) mc.player.getDistanceToEntity(entityPlayer);
    	                
    	                	DrawHelper.drawRect(2, 8 + offset, 142, offset + 26, new Color(32, 32, 32, 150).getRGB());
    	                DrawHelper.drawRect(1,2,3,4, new Color(15,15,15, 120).getRGB());
    	                mc.mntsb_15.drawString(play + " (" + cords + ")  " + blocks, 5, 15 + offset, -1);
    	            	
    	                offset += 20;
    	                pY -= 11;
    	                
                		}
            }
    	}
    }
}