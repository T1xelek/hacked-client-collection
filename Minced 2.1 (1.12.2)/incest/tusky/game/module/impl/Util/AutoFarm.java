package incest.tusky.game.module.impl.Util;

import incest.tusky.game.event.EventTarget;
import incest.tusky.game.event.events.impl.packet.EventReceivePacket;
import incest.tusky.game.event.events.impl.player.BlockHelper;
import incest.tusky.game.event.events.impl.player.EventPreMotion;
import incest.tusky.game.event.events.impl.player.EventUpdate;
import incest.tusky.game.event.events.impl.render.EventRender3D;
import incest.tusky.game.module.Module;
import incest.tusky.game.module.ModuleCategory;
import incest.tusky.game.ui.settings.Setting;
import incest.tusky.game.ui.settings.impl.BooleanSetting;
import incest.tusky.game.ui.settings.impl.ColorSetting;
import incest.tusky.game.ui.settings.impl.ListSetting;
import incest.tusky.game.ui.settings.impl.NumberSetting;
import incest.tusky.game.utils.math.RotationHelper;
import incest.tusky.game.utils.math.TimerHelper;
import incest.tusky.game.utils.render.RenderUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.network.play.server.SPacketMultiBlockChange;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public class AutoFarm extends Module {
    /*     */   private boolean isActive;
    /*     */   private int oldSlot;
    /*     */   public static boolean isEating;
    /*  42 */   ArrayList<BlockPos> crops = new ArrayList<>();
    /*  43 */   ArrayList<BlockPos> check = new ArrayList<>();
    /*  44 */   TimerHelper timerHelper = new TimerHelper();
    /*  45 */   TimerHelper timerHelper2 = new TimerHelper();
    /*  46 */   private final BooleanSetting autoFarm = new BooleanSetting("Auto Farm", true, () -> Boolean.valueOf(true));
    /*     */
    /*     */   private ListSetting farmMode;
    /*     */   private final NumberSetting delay;
    /*     */   private final NumberSetting radius;
    /*     */   private final BooleanSetting autoHoe;
    /*     */   private final BooleanSetting farmESP;
    /*     */   private final ColorSetting color;
    /*     */   private final BooleanSetting autoEat;
    /*     */   private final NumberSetting feed;
    /*     */
    /*     */   public AutoFarm() {
        /*  58 */     super("AutoFarm", "Автоматически садит и ломает урожай", ModuleCategory.Player); Objects.requireNonNull(this.autoFarm); this.farmMode = new ListSetting("AutoFarm Mode", "Harvest", this.autoFarm::getCurrentValue, new String[] { "Harvest", "Plant" }); this.delay = new NumberSetting("AutoFarm Delay", 2.0F, 0.0F, 10.0F, 0.1F, () -> Boolean.valueOf(true)); this.radius = new NumberSetting("AutoFarm Radius", 4.0F, 1.0F, 7.0F, 0.1F, () -> Boolean.valueOf(true)); this.autoHoe = new BooleanSetting("Auto Hoe", false, () -> Boolean.valueOf(true)); this.farmESP = new BooleanSetting("Draw Box", true, () -> Boolean.valueOf((this.autoFarm.getCurrentValue() && this.farmMode.currentMode.equals("Harvest")))); Objects.requireNonNull(this.farmESP); this.color = new ColorSetting("Box Color", (new Color(16777215)).getRGB(), this.farmESP::getCurrentValue); this.autoEat = new BooleanSetting("Auto Eat", true, () -> Boolean.valueOf(true)); Objects.requireNonNull(this.autoEat); this.feed = new NumberSetting("Feed Value", 15.0F, 1.0F, 20.0F, 1.0F, this.autoEat::getCurrentValue);
        /*  59 */     addSettings(new Setting[] { (Setting)this.farmMode, (Setting)this.autoFarm, (Setting)this.farmESP, (Setting)this.color, (Setting)this.autoHoe, (Setting)this.delay, (Setting)this.radius, (Setting)this.autoEat, (Setting)this.feed });
        /*     */   }
    /*     */
    /*     */   public static boolean doesHaveSeeds() {
        /*  63 */     for (int i = 0; i < 9; ) {
            /*  64 */       mc.player.inventory.getStackInSlot(i);
            /*  65 */       if (!(mc.player.inventory.getStackInSlot(i).getItem() instanceof net.minecraft.item.ItemSeeds)) { i++; continue; }
            /*  66 */        return true;
            /*     */     }
        /*  68 */     return false;
        /*     */   }
    /*     */
    /*     */   public static int searchSeeds() {
        /*  72 */     for (int i = 0; i < 45; ) {
            /*  73 */       ItemStack itemStack = mc.player.inventoryContainer.getSlot(i).getStack();
            /*  74 */       if (!(itemStack.getItem() instanceof net.minecraft.item.ItemSeeds)) { i++; continue; }
            /*  75 */        return i;
            /*     */     }
        /*  77 */     return -1;
        /*     */   }
    /*     */
    /*     */   public static int getSlotWithSeeds() {
        /*  81 */     for (int i = 0; i < 9; ) {
            /*  82 */       mc.player.inventory.getStackInSlot(i);
            /*  83 */       if (!(mc.player.inventory.getStackInSlot(i).getItem() instanceof net.minecraft.item.ItemSeeds)) { i++; continue; }
            /*  84 */        return i;
            /*     */     }
        /*  86 */     return 0;
        /*     */   }
    /*     */
    /*     */
    /*     */   public void onEnable() {
        /*  91 */     this.crops.clear();
        /*  92 */     this.check.clear();
        /*  93 */     super.onEnable();
        /*     */   }
    /*     */
    /*     */   private boolean isOnCrops() {
        /*  97 */     for (double x = mc.player.boundingBox.minX; x < mc.player.boundingBox.maxX; x += 0.009999999776482582D) {
            /*  98 */       for (double z = mc.player.boundingBox.minZ; z < mc.player.boundingBox.maxZ; ) {
                /*  99 */         Block block = mc.world.getBlockState(new BlockPos(x, mc.player.posY - 0.1D, z)).getBlock();
                /* 100 */         if (block instanceof net.minecraft.block.BlockFarmland || block instanceof net.minecraft.block.BlockSoulSand || block instanceof net.minecraft.block.BlockSand || block instanceof net.minecraft.block.BlockAir) {
                    /*     */           z += 0.009999999776482582D; continue;
                    /* 102 */         }  return false;
                /*     */       }
            /*     */     }
        /* 105 */     return true;
        /*     */   }
    /*     */
    /*     */   private boolean IsValidBlockPos(BlockPos pos) {
        /* 109 */     IBlockState state = mc.world.getBlockState(pos);
        /* 110 */     if (state.getBlock() instanceof net.minecraft.block.BlockFarmland || state.getBlock() instanceof net.minecraft.block.BlockSand || state.getBlock() instanceof net.minecraft.block.BlockSoulSand) {
            /* 111 */       return (mc.world.getBlockState(pos.up()).getBlock() == Blocks.AIR);
            /*     */     }
        /* 113 */     return false;
        /*     */   }
    /*     */
    /*     */   @EventTarget
    /*     */   public void onUpdate(EventUpdate e) {
        /* 118 */     if (mc.player == null || mc.world == null) {
            /*     */       return;
            /*     */     }
        /* 121 */     if (this.autoEat.getCurrentValue()) {
            /* 122 */       if (isFood()) {
                /* 123 */         if (isFood() && mc.player.getFoodStats().getFoodLevel() <= this.feed.getNumberValue()) {
                    /* 124 */           this.isActive = true;
                    /* 125 */           KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
                    /* 126 */         } else if (this.isActive) {
                    /* 127 */           KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
                    /* 128 */           this.isActive = false;
                    /*     */         }
                /*     */       } else {
                /* 131 */         if (isEating && !mc.player.isHandActive()) {
                    /* 132 */           if (this.oldSlot != -1) {
                        /* 133 */             mc.player.inventory.currentItem = this.oldSlot;
                        /* 134 */             this.oldSlot = -1;
                        /*     */           }
                    /* 136 */           isEating = false;
                    /* 137 */           KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
                    /*     */           return;
                    /*     */         }
                /* 140 */         if (isEating) {
                    /*     */           return;
                    /*     */         }
                /* 143 */         if (isValid(mc.player.getHeldItemOffhand(), mc.player.getFoodStats().getFoodLevel())) {
                    /* 144 */           mc.player.setActiveHand(EnumHand.OFF_HAND);
                    /* 145 */           isEating = true;
                    /* 146 */           KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
                    /* 147 */           mc.rightClickMouse();
                    /*     */         } else {
                    /* 149 */           for (int i = 0; i < 9; ) {
                        /* 150 */             if (!isValid(mc.player.inventory.getStackInSlot(i), mc.player.getFoodStats().getFoodLevel())) {
                            /*     */               i++; continue;
                            /* 152 */             }  this.oldSlot = mc.player.inventory.currentItem;
                        /* 153 */             mc.player.inventory.currentItem = i;
                        /* 154 */             isEating = true;
                        /* 155 */             KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
                        /* 156 */             mc.rightClickMouse();
                        /*     */             return;
                        /*     */           }
                    /*     */         }
                /*     */       }
            /*     */     }
        /*     */   }
    /*     */
    /*     */   private boolean itemCheck(Item item) {
        /* 165 */     return (item != Items.ROTTEN_FLESH && item != Items.CARROT && item != Items.BEETROOT&& item != Items.SPIDER_EYE && item != Items.POISONOUS_POTATO && (item != Items.FISH || (new ItemStack(Items.FISH)).getItemDamage() != 3));
        /*     */   }
    /*     */
    /*     */   private boolean isValid(ItemStack stack, int food) {
        /* 169 */     return (stack.getItem() instanceof ItemFood && this.feed.getNumberValue() - food >= ((ItemFood)stack.getItem()).getHealAmount(stack) && itemCheck(stack.getItem()));
        /*     */   }
    /*     */
    /*     */   private boolean isFood() {
        /* 173 */     return mc.player.getHeldItemOffhand().getItem() instanceof ItemFood;
        /*     */   }
    /*     */
    /*     */
    /*     */   @EventTarget
    /*     */   public void onRender3D(EventRender3D event) {
        /* 179 */     if (mc.player == null || mc.world == null) {
            /*     */       return;
            /*     */     }
        /* 182 */     if (this.farmESP.getCurrentValue() && this.farmMode.currentMode.equals("Harvest")) {
            /* 183 */       ArrayList<BlockPos> blockPositions = getBlocks(this.radius.getNumberValue(), 0.0F, this.radius.getNumberValue());
            /* 184 */       for (BlockPos pos : blockPositions) {
                /* 185 */         Color cropsColor = new Color(this.color.getColorValue());
                /* 186 */         BlockPos blockPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ());
                /* 187 */         RenderUtils.blockEsp(blockPos, cropsColor, false, 1.0D, 1.0D);
                /*     */       }
            /*     */     }
        /*     */   }
    /*     */
    /*     */
    /*     */   @EventTarget
    /*     */   public void onPreMotion(EventPreMotion event) {
        /* 195 */     if (mc.player == null && mc.world == null)
            /*     */       return;
        /*     */     BlockPos pos;
        /* 198 */     if (this.autoHoe.getCurrentValue() && (pos = BlockHelper.getSphere(BlockHelper.getPlayerPosLocal(), this.radius.getNumberValue(), 6, false, true, 0).stream().filter(BlockHelper::IsValidBlockPos).min(Comparator.comparing(blockPos -> Double.valueOf(RotationHelper.getDistanceOfEntityToBlock((Entity)mc.player, blockPos)))).orElse(null)) != null && mc.player.getHeldItemMainhand().getItem() instanceof net.minecraft.item.ItemHoe) {
            /* 199 */       float[] rots = RotationHelper.getRotationVector(new Vec3d((pos.getX() + 0.5F), (pos.getY() + 0.5F), (pos.getZ() + 0.5F)));
            /* 200 */       event.setYaw(rots[0]);
            /* 201 */       event.setPitch(rots[1]);
            /* 202 */       mc.player.renderYawOffset = rots[0];
            /* 203 */       mc.player.rotationYawHead = rots[0];
            /* 204 */       mc.player.rotationPitchHead = rots[1];
            /* 205 */       if (this.timerHelper2.hasReached((this.delay.getNumberValue() * 100.0F))) {
                /* 206 */         mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos, EnumFacing.UP, EnumHand.MAIN_HAND, 0.0F, 0.0F, 0.0F));
                /* 207 */         mc.player.swingArm(EnumHand.MAIN_HAND);
                /* 208 */         this.timerHelper2.reset();
                /*     */       }
            /*     */     }
        /* 211 */     if (this.farmMode.currentMode.equals("Plant") && !doesHaveSeeds() && searchSeeds() != -1) {
            /* 212 */       mc.playerController.windowClick(0, searchSeeds(), 1, ClickType.QUICK_MOVE, (EntityPlayer)mc.player);
            /*     */     }
        /*     */   }
    /*     */
    /*     */   @EventTarget
    /*     */   public void onPre(EventPreMotion e) {
        /* 218 */     if (this.autoFarm.getCurrentValue()) {
            /* 219 */       String mode = this.farmMode.getOptions();
            /* 220 */       if (mode.equalsIgnoreCase("Harvest")) {
                /* 221 */         ArrayList<BlockPos> blockPositions = getBlocks(this.radius.getNumberValue(), this.radius.getNumberValue(), this.radius.getNumberValue());
                /* 222 */         for (BlockPos pos : blockPositions) {
                    /*     */
                    /* 224 */           IBlockState state = BlockHelper.getState(pos);
                    /* 225 */           if (!isCheck(Block.getIdFromBlock(state.getBlock())))
                        /* 226 */             continue;  if (!isCheck(0)) {
                        /* 227 */             this.check.add(pos);
                        /*     */           }
                    /* 229 */           Block block = mc.world.getBlockState(pos).getBlock();
                    /* 230 */           BlockPos downPos = pos.down(1); BlockCrops crop;
                    /* 231 */           if (!(block instanceof BlockCrops) || (crop = (BlockCrops)block).canGrow((World)mc.world, pos, state, true) || !this.timerHelper.hasReached((this.delay.getNumberValue() * 100.0F)) || pos == null)
                        /*     */             continue;
                    /* 233 */           float[] rots = RotationHelper.getRotationVector(new Vec3d((pos.getX() + 0.5F), (pos.getY() + 0.5F), (pos.getZ() + 0.5F)));
                    /* 234 */           e.setYaw(rots[0]);
                    /* 235 */           e.setPitch(rots[1]);
                    /* 236 */           mc.player.renderYawOffset = rots[0];
                    /* 237 */           mc.player.rotationYawHead = rots[0];
                    /* 238 */           mc.player.rotationPitchHead = rots[1];
                    /* 239 */           mc.playerController.onPlayerDamageBlock(pos, mc.player.getHorizontalFacing());
                    /* 240 */           mc.player.swingArm(EnumHand.MAIN_HAND);
                    /* 241 */           if (doesHaveSeeds()) {
                        /* 242 */             mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(getSlotWithSeeds()));
                        /* 243 */             mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(downPos, EnumFacing.UP, EnumHand.MAIN_HAND, 0.0F, 0.0F, 0.0F));
                        /* 244 */             mc.player.swingArm(EnumHand.MAIN_HAND);
                        /*     */           }
                    /* 246 */           this.timerHelper.reset();
                    /*     */         }
                /* 248 */       } else if (mode.equalsIgnoreCase("Plant")) {
                /* 249 */         BlockPos pos = BlockHelper.getSphere(BlockHelper.getPlayerPosLocal(), this.radius.getNumberValue(), 6, false, true, 0).stream().filter(this::IsValidBlockPos).min(Comparator.comparing(blockPos -> Double.valueOf(RotationHelper.getDistanceOfEntityToBlock((Entity)mc.player, blockPos)))).orElse(null);
                /* 250 */         Vec3d vec = new Vec3d(0.0D, 0.0D, 0.0D);
                /* 251 */         if (this.timerHelper.hasReached((this.delay.getNumberValue() * 100.0F)) && isOnCrops() && pos != null && doesHaveSeeds()) {
                    /* 252 */           float[] rots = RotationHelper.getRotationVector(new Vec3d((pos.getX() + 0.5F), (pos.getY() + 0.5F), (pos.getZ() + 0.5F)));
                    /* 253 */           e.setYaw(rots[0]);
                    /* 254 */           e.setPitch(rots[1]);
                    /* 255 */           mc.player.renderYawOffset = rots[0];
                    /* 256 */           mc.player.rotationYawHead = rots[0];
                    /* 257 */           mc.player.rotationPitchHead = rots[1];
                    /* 258 */           mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(getSlotWithSeeds()));
                    /* 259 */           mc.playerController.processRightClickBlock(mc.player, mc.world, pos, EnumFacing.VALUES[0].getOpposite(), vec, EnumHand.MAIN_HAND);
                    /* 260 */           this.timerHelper.reset();
                    /*     */         }
                /*     */       }
            /*     */     }
        /*     */   }
    /*     */
    /*     */   @EventTarget
    /*     */   public void onReceivePacket(EventReceivePacket e) {
        /* 268 */     if (this.autoFarm.getCurrentValue())
            /* 269 */       if (e.getPacket() instanceof SPacketBlockChange) {
                /* 270 */         SPacketBlockChange p = (SPacketBlockChange)e.getPacket();
                /* 271 */         if (isEnabled(Block.getIdFromBlock(p.getBlockState().getBlock()))) {
                    /* 272 */           this.crops.add(p.getBlockPosition());
                    /*     */         }
                /* 274 */       } else if (e.getPacket() instanceof SPacketMultiBlockChange) {
                /* 275 */         SPacketMultiBlockChange p = (SPacketMultiBlockChange)e.getPacket();
                /* 276 */         for (SPacketMultiBlockChange.BlockUpdateData dat : p.getChangedBlocks()) {
                    /* 277 */           if (isEnabled(Block.getIdFromBlock(dat.getBlockState().getBlock()))) {
                        /* 278 */             this.crops.add(dat.getPos());
                        /*     */           }
                    /*     */         }
                /*     */       }
        /*     */   }
    /*     */
    /*     */   private boolean isCheck(int id) {
        /* 285 */     int check = 0;
        /* 286 */     if (id != 0) {
            /* 287 */       check = 59;
            /*     */     }
        /* 289 */     if (id == 0) {
            /* 290 */       return false;
            /*     */     }
        /* 292 */     return (id == check);
        /*     */   }
    /*     */
    /*     */   private boolean isEnabled(int id) {
        /* 296 */     int check = 0;
        /* 297 */     if (id != 0) {
            /* 298 */       check = 59;
            /*     */     }
        /* 300 */     if (id == 0) {
            /* 301 */       return false;
            /*     */     }
        /* 303 */     return (id == check);
        /*     */   }
    /*     */
    /*     */   private ArrayList<BlockPos> getBlocks(float x, float y, float z) {
        /* 307 */     BlockPos min = new BlockPos(mc.player.posX - x, mc.player.posY - y, mc.player.posZ - z);
        /* 308 */     BlockPos max = new BlockPos(mc.player.posX + x, mc.player.posY + y, mc.player.posZ + z);
        /* 309 */     return BlockHelper.getAllInBox(min, max);
        /*     */   }
    /*     */ }