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
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.math.BlockPos;

import java.awt.*;

import static incest.tusky.game.module.impl.Render.ModuleList.oneColor;

public class BlockESP extends Module {

    private final BooleanSetting enderChest;
    private final BooleanSetting chest;
    private final BooleanSetting clientColor;
    private final ColorSetting spawnerColor;
    private final BooleanSetting espOutline;
    private final ColorSetting chestColor;
    private final ColorSetting enderChestColor;
    private final ColorSetting shulkerColor;
    private final ColorSetting bedColor;
    public static ColorSetting minecartColor;
    private final BooleanSetting minecart;
    private final BooleanSetting bed;
    private final BooleanSetting shulker;
    private final BooleanSetting spawner;

    public BlockESP() {
        super("BlockESP", "Подсвечивает блок каким-либо цветом", ModuleCategory.Visual);
        chest = new BooleanSetting("Chest", true, () -> true);
        enderChest = new BooleanSetting("Ender Chest", false, () -> true);
        spawner = new BooleanSetting("Spawner", false, () -> true);
        shulker = new BooleanSetting("Shulker", false, () -> true);
        bed = new BooleanSetting("Bed", false, () -> true);
        minecart = new BooleanSetting("Minecart", false, () -> true);
        minecartColor = new ColorSetting("Minecart Color", new Color(0xFFFFFF).getRGB(), minecart::getCurrentValue);
        chestColor = new ColorSetting("Chest Color", new Color(0xEE2CFF).getRGB(), chest::getCurrentValue);
        enderChestColor = new ColorSetting("EnderChest Color", new Color(0xEE2CFF).getRGB(), enderChest::getCurrentValue);
        shulkerColor = new ColorSetting("Shulker Color", new Color(0xEE2CFF).getRGB(), shulker::getCurrentValue);
        spawnerColor = new ColorSetting("Spawner Color", new Color(0xEE2CFF).getRGB(), spawner::getCurrentValue);
        bedColor = new ColorSetting("Bed Color", new Color(0xEE2CFF).getRGB(), bed::getCurrentValue);
        clientColor = new BooleanSetting("Client Colors", false, () -> true);
        espOutline = new BooleanSetting("ESP Outline", false, () -> true);
        addSettings(espOutline, chest, enderChest, spawner, shulker, bed, minecart, minecartColor, chestColor, enderChestColor, spawnerColor, shulkerColor, bedColor, clientColor);
    }

    @EventTarget
    public void onRender3D(EventRender3D event) {
        Color minecartColoR = clientColor.getCurrentValue() ?  oneColor.getColorValueColor() : new Color(minecartColor.getColorValue());
        Color colorChest = clientColor.getCurrentValue() ? oneColor.getColorValueColor() : new Color(chestColor.getColorValue());
        Color enderColorChest = clientColor.getCurrentValue() ?oneColor.getColorValueColor() : new Color(enderChestColor.getColorValue());
        Color shulkColor = clientColor.getCurrentValue() ? oneColor.getColorValueColor() : new Color(shulkerColor.getColorValue());
        Color bedColoR = clientColor.getCurrentValue() ? oneColor.getColorValueColor() : new Color(bedColor.getColorValue());
        Color spawnerColoR = clientColor.getCurrentValue() ? oneColor.getColorValueColor() : new Color(spawnerColor.getColorValue());
        if (Helper.mc.player != null || Helper.mc.world != null) {
            for (TileEntity entity : Helper.mc.world.loadedTileEntityList) {
                BlockPos pos = entity.getPos();
                if (entity instanceof TileEntityChest && chest.getCurrentValue()) {
                    RenderUtils.blockEsp(pos, new Color(colorChest.getRGB()), espOutline.getCurrentValue());
                } else if (entity instanceof TileEntityEnderChest && enderChest.getCurrentValue()) {
                    RenderUtils.blockEsp(pos, new Color(enderColorChest.getRGB()), espOutline.getCurrentValue());
                } else if (entity instanceof TileEntityBed && bed.getCurrentValue()) {
                    RenderUtils.blockEsp(pos, new Color(bedColoR.getRGB()), espOutline.getCurrentValue());
                } else if (entity instanceof TileEntityShulkerBox && shulker.getCurrentValue()) {
                    RenderUtils.blockEsp(pos, new Color(shulkColor.getRGB()), espOutline.getCurrentValue());
                } else if (entity instanceof TileEntityMobSpawner && spawner.getCurrentValue()) {
                    RenderUtils.blockEsp(pos, new Color(spawnerColoR.getRGB()), espOutline.getCurrentValue());
                }
            }
            for (Entity entity : BlockESP.mc.world.loadedEntityList) {
                BlockPos pos2 = entity.getPosition();

                if (!(entity instanceof EntityMinecart) || !minecart.getCurrentValue()) {
                    continue;
                }
                RenderUtils.blockEsp(pos2, new Color(minecartColoR.getRGB()), espOutline.getCurrentValue());
            }
        }
    }

}
