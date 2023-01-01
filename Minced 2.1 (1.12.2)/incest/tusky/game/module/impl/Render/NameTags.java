package incest.tusky.game.module.impl.Render;

import incest.tusky.game.module.Module;
import incest.tusky.game.event.events.impl.render.EventRender2D;
import incest.tusky.game.event.EventTarget;
import incest.tusky.game.event.events.impl.render.EventRender3D;
import incest.tusky.game.module.ModuleCategory;
import incest.tusky.game.ui.settings.impl.BooleanSetting;
import incest.tusky.game.ui.settings.impl.ListSetting;
import incest.tusky.game.ui.settings.impl.NumberSetting;
import incest.tusky.game.utils.render.ClientHelper;
import incest.tusky.game.utils.render.ColorUtils;
import incest.tusky.game.utils.render.RenderUtils;
import incest.tusky.game.utils.math.MathematicHelper;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.*;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import java.awt.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.*;

import static incest.tusky.game.module.impl.Render.ModuleList.oneColor;

public class NameTags extends Module {
    public static Map<EntityLivingBase, double[]> entityPositions = new HashMap<EntityLivingBase, double[]>();
    public BooleanSetting armor = new BooleanSetting("Show Armor", true, () -> true);
    public BooleanSetting potion = new BooleanSetting("Show Potions", true, () -> true);
    public BooleanSetting glowNameTags = new BooleanSetting("Glow Potions", true, () -> true);

    public BooleanSetting backGround = new BooleanSetting("NameTags Background", true, () -> true);
    public BooleanSetting offHand = new BooleanSetting("OffHand Render", true, () -> true);

    public ListSetting backGroundMode = new ListSetting("Background Mode", "Default", () -> backGround.getCurrentValue(), "Default", "Shader");
    public NumberSetting opacity = new NumberSetting("Background Opacity", 150.0f, 0.0f, 255.0f, 5.0f, () -> backGround.getCurrentValue());
    public NumberSetting size = new NumberSetting("NameTags Size", 1.0f, 0.5f, 2.0f, 0.1f, () -> true);

    public NameTags() {
        super("Name Tags", "Показывает игроков, ник, броню и их здоровье сквозь стены", ModuleCategory.Visual);
        addSettings(armor, potion);
    }

    public static TextFormatting getHealthColor(float health) {
        if (health <= 4.0f) {
            return TextFormatting.RED;
        }
        if (health <= 8.0f) {
            return TextFormatting.GOLD;
        }
        if (health <= 12.0f) {
            return TextFormatting.YELLOW;
        }
        if (health <= 16.0f) {
            return TextFormatting.DARK_GREEN;
        }
        return TextFormatting.GREEN;
    }

    @EventTarget
    public void onRender3D(EventRender3D event) {
        updatePositions();
    }

    @EventTarget
    public void onRender2D(EventRender2D event) {
        ScaledResolution sr = new ScaledResolution(mc);
        GlStateManager.pushMatrix();
        for (EntityLivingBase entity : entityPositions.keySet()) {
            if (entity == null || entity == mc.player)
                continue;
            GlStateManager.pushMatrix();
            if (entity instanceof EntityPlayer) {
                double[] array = entityPositions.get(entity);
                if (array[3] < 0.0 || array[3] >= 1.0) {
                    GlStateManager.popMatrix();
                    continue;
                }
                double scaleFactor = sr.getScaleFactor();
                GlStateManager.translate(array[0] / scaleFactor, array[1] / scaleFactor + (double) 0, 0.0);
                String stringName = entity.getDisplayName().getUnformattedText().contains("\uff27\uff25\uff26\uff25\uff33\uff34") ? "ХУЕСОС" : entity.getDisplayName().getUnformattedText();
                float width = (float) (mc.mntsb_18.getStringWidth(stringName) + 5);
                GlStateManager.translate(0.0, -10.0, 0.0);

                boolean hp = false;

                if (entity.getHealth() > 20) {
                    hp = true;
                } else {
                    hp = false;
                }

                RenderUtils.drawRect(-width / 2.0f, -10.0, width / 2.0f, 9.0, ColorUtils.getColor(0, (int) 100));

                RenderUtils.drawRect(-width / 2.0f + 2, 1, width / 2.0f - 2, 7.0, RenderUtils.injectAlpha(new Color(0,0,0), (int) 100).getRGB());

                RenderUtils.drawRect((-width / 2.0f + 2), 1, (width / 2.0f - 2 - (-width / 2.0f + 2)) * (hp ? 20 : entity.getHealth()) / 20 + (-width / 2.0f + 2), 7.0, oneColor.getColorValue());

                mc.mntsb_13.drawStringWithShadow(MathematicHelper.round(entity.getHealth(), 1)+ "", -width / 2.0f + 2.5f, 2.5f, -1);

                mc.mntsb_13.drawStringWithShadow((int) entity.getHealth() + "", width / 2.0f + 2.0f - 5 - mc.mntsb_13.getStringWidth((int) entity.getHealth() + ""), 2.5f, -1);

                mc.mntsb_18.drawStringWithShadow(stringName + " " + getHealthColor(entity.getHealth()), -width / 2.0f + 2.0f, -7.5, -1);
                ItemStack heldItemStack = entity.getHeldItem(EnumHand.OFF_HAND);
                ArrayList<ItemStack> list = new ArrayList<>();
                for (int i = 0; i < 5; ++i) {
                    ItemStack getEquipmentInSlot = ((EntityPlayer) entity).getEquipmentInSlot(i);
                    list.add(getEquipmentInSlot);
                }
                int n10 = -(list.size() * 9) - 8;
                if (armor.getCurrentValue()) {
                    GlStateManager.pushMatrix();
                    GlStateManager.translate(0.0f, -2.0f, 0.0f);
                    mc.getRenderItem().renderItemIntoGUI(heldItemStack, n10 + 86, -28);
                    mc.getRenderItem().renderItemOverlays(mc.rubik_18, heldItemStack, n10 + 86, -28);

                    for (ItemStack itemStack : list) {
                        RenderHelper.enableGUIStandardItemLighting();
                        mc.getRenderItem().renderItemIntoGUI(itemStack, n10 + 6, -28);
                        mc.getRenderItem().renderItemOverlays(mc.rubik_18, itemStack, n10 + 5, -28);
                        n10 += 3;
                        RenderHelper.disableStandardItemLighting();
                        int n11 = 7;
                        int getEnchantmentLevel = EnchantmentHelper.getEnchantmentLevel(Objects.requireNonNull(Enchantment.getEnchantmentByID(16)), itemStack);
                        int getEnchantmentLevel2 = EnchantmentHelper.getEnchantmentLevel(Objects.requireNonNull(Enchantment.getEnchantmentByID(20)), itemStack);
                        int getEnchantmentLevel3 = EnchantmentHelper.getEnchantmentLevel(Objects.requireNonNull(Enchantment.getEnchantmentByID(19)), itemStack);
                        if (getEnchantmentLevel > 0) {
                            drawEnchantTag("S" + getColor(getEnchantmentLevel) + getEnchantmentLevel, n10, n11);
                            n11 += 8;
                        }
                        if (getEnchantmentLevel2 > 0) {
                            drawEnchantTag("F" + getColor(getEnchantmentLevel2) + getEnchantmentLevel2, n10, n11);
                            n11 += 8;
                        }
                        if (getEnchantmentLevel3 > 0) {
                            drawEnchantTag("Kb" + getColor(getEnchantmentLevel3) + getEnchantmentLevel3, n10, n11);
                        } else if (itemStack.getItem() instanceof ItemArmor) {
                            int getEnchantmentLevel4 = EnchantmentHelper.getEnchantmentLevel(Objects.requireNonNull(Enchantment.getEnchantmentByID(0)), itemStack);
                            int getEnchantmentLevel5 = EnchantmentHelper.getEnchantmentLevel(Objects.requireNonNull(Enchantment.getEnchantmentByID(7)), itemStack);
                            int getEnchantmentLevel6 = EnchantmentHelper.getEnchantmentLevel(Objects.requireNonNull(Enchantment.getEnchantmentByID(34)), itemStack);
                            if (getEnchantmentLevel4 > 0) {
                                drawEnchantTag("P" + getColor(getEnchantmentLevel4) + getEnchantmentLevel4, n10, n11);
                                n11 += 8;
                            }
                            if (getEnchantmentLevel5 > 0) {
                                drawEnchantTag("Th" + getColor(getEnchantmentLevel5) + getEnchantmentLevel5, n10, n11);
                                n11 += 8;
                            }
                            if (getEnchantmentLevel6 > 0) {
                                drawEnchantTag("U" + getColor(getEnchantmentLevel6) + getEnchantmentLevel6, n10, n11);
                            }
                        } else if (itemStack.getItem() instanceof ItemBow) {
                            int getEnchantmentLevel7 = EnchantmentHelper.getEnchantmentLevel(Objects.requireNonNull(Enchantment.getEnchantmentByID(48)), itemStack);
                            int getEnchantmentLevel8 = EnchantmentHelper.getEnchantmentLevel(Objects.requireNonNull(Enchantment.getEnchantmentByID(49)), itemStack);
                            int getEnchantmentLevel9 = EnchantmentHelper.getEnchantmentLevel(Objects.requireNonNull(Enchantment.getEnchantmentByID(50)), itemStack);
                            if (getEnchantmentLevel7 > 0) {
                                drawEnchantTag("P" + getColor(getEnchantmentLevel7) + getEnchantmentLevel7, n10, n11);
                                n11 += 8;
                            }
                            if (getEnchantmentLevel8 > 0) {
                                drawEnchantTag("P" + getColor(getEnchantmentLevel8) + getEnchantmentLevel8, n10, n11);
                                n11 += 8;
                            }
                            if (getEnchantmentLevel9 > 0) {
                                drawEnchantTag("F" + getColor(getEnchantmentLevel9) + getEnchantmentLevel9, n10, n11);
                            }
                        }
                        n10 = (int) ((double) n10 + 13.5);
                    }
                    GlStateManager.popMatrix();
                }
                if (potion.getCurrentValue()) {
                    drawPotionEffect((EntityPlayer) entity);
                }
            }
            GlStateManager.popMatrix();
        }
        GlStateManager.popMatrix();
        GlStateManager.enableBlend();
    }

    private void drawPotionEffect(EntityPlayer entity) {
        int tagWidth = 0;
        int stringY = -25;
        if (entity.getTotalArmorValue() > 0 || !entity.getHeldItemMainhand().func_190926_b() && !entity.getHeldItemOffhand().func_190926_b()) {
            stringY -= 37;
        }
        for (PotionEffect potionEffect : entity.getActivePotionEffects()) {
            Potion potion = potionEffect.getPotion();
            boolean potRanOut = (double) potionEffect.getDuration() != 0.0;
            String power = "";
            if (!entity.isPotionActive(potion) || !potRanOut) continue;
            if (potionEffect.getAmplifier() == 1) {
                power = "II";
            } else if (potionEffect.getAmplifier() == 2) {
                power = "III";
            } else if (potionEffect.getAmplifier() == 3) {
                power = "IV";
            }
            GlStateManager.pushMatrix();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            float xValue = (float) tagWidth - ((float) mc.rubik_18.getStringWidth(I18n.format(potion.getName()) + " " + power + TextFormatting.GRAY + " " + Potion.getDurationString(potionEffect)) / 2.0f);

            mc.mntsb_18.drawStringWithShadow(I18n.format(potion.getName()) + " " + power + TextFormatting.GRAY + " " + Potion.getDurationString(potionEffect), xValue, stringY, -1);

            stringY -= 10;
            GlStateManager.popMatrix();
        }
    }

    private void drawEnchantTag(String text, int n, int n2) {
        GlStateManager.pushMatrix();
        GlStateManager.disableDepth();
        n2 -= 7;
        mc.mntsb_18.drawStringWithShadow(text, n + 6, -35 - n2, -1);
        GlStateManager.enableDepth();
        GlStateManager.popMatrix();
    }

    private String getColor(int n) {
        if (n != 1) {
            if (n == 2) {
                return "";
            }
            if (n == 3) {
                return "";
            }
            if (n == 4) {
                return "";
            }
            if (n >= 5) {
                return "";
            }
        }
        return "";
    }

    private void updatePositions() {
        entityPositions.clear();
        float pTicks = mc.timer.renderPartialTicks;
        for (Entity o : mc.world.loadedEntityList) {
            if (o == mc.player || !(o instanceof EntityPlayer)) continue;
            double x = o.lastTickPosX + (o.posX - o.lastTickPosX) * (double) pTicks - mc.getRenderManager().viewerPosX;
            double y = o.lastTickPosY + (o.posY - o.lastTickPosY) * (double) pTicks - mc.getRenderManager().viewerPosY;
            double z = o.lastTickPosZ + (o.posZ - o.lastTickPosZ) * (double) pTicks - mc.getRenderManager().viewerPosZ;
            if (!(Objects.requireNonNull(convertTo2D(x, y += (double) o.height + 0.2, z))[2] >= 0.0) || !(Objects.requireNonNull(convertTo2D(x, y, z))[2] < 2.0))
                continue;
            entityPositions.put((EntityPlayer) o, new double[]{Objects.requireNonNull(convertTo2D(x, y, z))[0], Objects.requireNonNull(convertTo2D(x, y, z))[1], Math.abs(Objects.requireNonNull(convertTo2D(x, y + 1.0, z))[1] - Objects.requireNonNull(convertTo2D(x, y, z))[1]), Objects.requireNonNull(convertTo2D(x, y, z))[2]});
        }
    }

    private double[] convertTo2D(double x, double y, double z) {
        FloatBuffer screenCords = BufferUtils.createFloatBuffer(3);
        IntBuffer viewport = BufferUtils.createIntBuffer(16);
        FloatBuffer modelView = BufferUtils.createFloatBuffer(16);
        FloatBuffer projection = BufferUtils.createFloatBuffer(16);
        GL11.glGetFloat(2982, modelView);
        GL11.glGetFloat(2983, projection);
        GL11.glGetInteger(2978, viewport);
        boolean result = GLU.gluProject((float) x, (float) y, (float) z, modelView, projection, viewport, screenCords);
        if (result) {
            return new double[]{screenCords.get(0), (float) Display.getHeight() - screenCords.get(1), screenCords.get(2)};
        }
        return null;
    }

    private void scale() {
        float n = mc.gameSettings.smoothCamera ? 2.0f : size.getNumberValue();
        GlStateManager.scale(n, n, n);
    }
}