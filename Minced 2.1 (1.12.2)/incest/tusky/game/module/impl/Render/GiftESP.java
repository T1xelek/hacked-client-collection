package incest.tusky.game.module.impl.Render;

import incest.tusky.game.event.EventTarget;
import incest.tusky.game.module.Module;
import incest.tusky.game.ui.settings.impl.BooleanSetting;
import incest.tusky.game.ui.settings.impl.ColorSetting;
import incest.tusky.game.event.events.impl.render.EventRender3D;
import incest.tusky.game.module.ModuleCategory;
import incest.tusky.game.utils.Helper;
import incest.tusky.game.utils.render.ClientHelper;
import incest.tusky.game.utils.render.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import java.awt.*;

import org.lwjgl.opengl.GL11;

import static incest.tusky.game.module.impl.Render.ModuleList.oneColor;

public class GiftESP extends Module {


    public GiftESP() {
        super("GiftESP", "Подсвечивает блок каким-либо цветом", ModuleCategory.Visual);
    }

    @EventTarget
    public void onRender3D(EventRender3D event) {
    	for (TileEntity entity : mc.world.loadedTileEntityList) {
    		BlockPos pos = entity.getPos();
    		if (entity instanceof TileEntitySkull && ((TileEntitySkull) entity).getSkullType() == 3) {
    		RenderUtils.headESP(pos, new Color(222, 222, 222), false);
    		}
    	}
    }
}
