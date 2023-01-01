package incest.tusky.game.module.impl.Render;

import incest.tusky.game.event.EventTarget;
import incest.tusky.game.module.Module;
import incest.tusky.game.tuskevich;
import incest.tusky.game.ui.settings.impl.BooleanSetting;
import incest.tusky.game.ui.settings.impl.ColorSetting;
import incest.tusky.game.event.events.impl.render.EventRender3D;
import incest.tusky.game.module.ModuleCategory;
import incest.tusky.game.utils.Helper;
import incest.tusky.game.utils.otherutils.gayutil.DrawHelper;
import incest.tusky.game.utils.render.ClientHelper;
import incest.tusky.game.utils.render.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.math.BlockPos;

import java.awt.*;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Cylinder;
import org.lwjgl.util.glu.Sphere;

import static incest.tusky.game.module.impl.Render.ModuleList.oneColor;

public class PenisESP extends Module {

	private float cumSize;
	private float spin;
    public PenisESP() {
        super("PenisESP", "Подсвечивает блок каким-либо цветом", ModuleCategory.Visual);
    }

    @EventTarget
    public void onRender3D(EventRender3D event) {
    	double x = mc.player.lastTickPosX + (mc.player.posX - mc.player.lastTickPosX) * mc.timer.renderPartialTicks - mc.getRenderManager().renderPosX;
        double y = mc.player.lastTickPosY + (mc.player.posY - mc.player.lastTickPosY) * mc.timer.renderPartialTicks - mc.getRenderManager().renderPosY;
        double z = mc.player.lastTickPosZ + (mc.player.posZ - mc.player.lastTickPosZ) * mc.timer.renderPartialTicks - mc.getRenderManager().renderPosZ;
        GlStateManager.pushMatrix();
		if (tuskevich.instance.featureManager.getFeature(BabyBoy.class).isEnabled()) GlStateManager.scale(0.55,0.55,0.55);
        GL11.glBlendFunc(770, 771);
        DrawHelper.enableSmoothLine(2.5F);
        GL11.glShadeModel(7425);
        GL11.glEnable(2884);
        GL11.glEnable(3042);
        GL11.glRotatef(-mc.player.rotationYaw, -0.015F, 1.0F, 0.0F);
		GL11.glTranslated(x, y + mc.player.height / 2.0f - 0.22499999403953552, z + 0.2F);
		GL11.glColor4f(1.38f, 0.55f, 2.38f, 1.0f);
		GL11.glRotatef((mc.player.isSneaking() ? 35 : 0) + this.spin, 1.0f + this.spin, 0.0f, this.cumSize);
		final Cylinder shaft = new Cylinder();
		shaft.setDrawStyle(100013);
		shaft.draw(0.1f, 0.11f, 0.7f, 25, 10);
		GL11.glColor4f(1.38f, 0.55f, 2.38f, 1.0f);
		GL11.glTranslated(0.0, 0.0, -0.12500000298023223);
		GL11.glTranslated(-0.09000000074505805, 0.0, 0.0);
		final Sphere right = new Sphere();
		right.setDrawStyle(100013);
		right.draw(0.14f, 10, 20);
		GL11.glTranslated(0.16000000149011612, 0.0, 0.0);
		final Sphere left = new Sphere();
		left.setDrawStyle(100013);
		left.draw(0.14f, 10, 20);
		GL11.glColor4f(1.35f, 0.0f, 0.0f, 1.0f);
		GL11.glTranslated(-0.07000000074505806, 0.0, 0.889999952316284);
		final Sphere tip = new Sphere();
		tip.setDrawStyle(100013);
		tip.draw(0.13f, 15, 20);
        DrawHelper.disableSmoothLine();
        GL11.glShadeModel(7424);
        GL11.glDisable(3042);
        GL11.glDisable(2884);
        GlStateManager.popMatrix();
    }
}
