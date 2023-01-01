package incest.tusky.game.module;

import incest.tusky.game.module.impl.Combat.*;
import incest.tusky.game.module.impl.Util.*;
import incest.tusky.game.module.impl.Movement.*;
import incest.tusky.game.module.impl.Player.*;
import incest.tusky.game.module.impl.Render.*;
import net.minecraft.client.Minecraft;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ModuleManager {

    public CopyOnWriteArrayList<Module> fr = new CopyOnWriteArrayList<>();

    public ModuleManager() {
      	fr.add(new RWHelper());
     	fr.add(new StaffAlert());
    	fr.add(new DetectPlayer());
    	fr.add(new AirDropWay());
      	fr.add(new AutoMenuClose());
    	fr.add(new GiftESP());
      	fr.add(new PenisESP());
      	fr.add(new SpizdilAir());
       	fr.add(new NoWeb());
    	fr.add(new BabyBoy());
        fr.add(new SunriseElytra());
        fr.add(new AntiArmorStand());
        fr.add(new ElytraFix());
        fr.add(new AntiFlag());
        fr.add(new ModuleList());
        fr.add(new Notifications());
        fr.add(new TargetHUD());
        fr.add(new NoRender());
        fr.add(new ClickGUI());
        fr.add(new Hud());
        fr.add(new AntiLagMachine());
        fr.add(new KTLeave());
        fr.add(new TargetStrafe());
        fr.add(new MinecraftOptimizer());
        fr.add(new elytrafly());
        fr.add(new MiddleClickFriend());
        fr.add(new FastWorldLoading());
        fr.add(new Baritone());
        fr.add(new ChatHistory());
        fr.add(new NameProtect());
        fr.add(new BetterChat());
        fr.add(new DamageSpeed());
        fr.add(new AppleGoldenTimer());
        fr.add(new AutoCrystal());
        fr.add(new RodFly());
        fr.add(new SuperBow());
        fr.add(new TriggerBot());
        fr.add(new AutoPotion());
        fr.add(new FlyingParticles());
        fr.add(new FastBow());
        fr.add(new AutoGapple());
        fr.add(new AutoTotem());
        fr.add(new KillAura());
        fr.add(new KeepSprint());
        fr.add(new Velocity());
        fr.add(new AntiBot());
        fr.add(new HitBox());
        fr.add(new WaterSpeed());
        fr.add(new Spider());
        fr.add(new AirJump());
        fr.add(new Flight());
        fr.add(new Sprint());
        fr.add(new Speed());
        fr.add(new Jesus());
        fr.add(new BackTrack());
        fr.add(new Strafe());
        fr.add(new Timer());
        fr.add(new NoServerRotations());
        fr.add(new MiddleClickPearl());
        fr.add(new DeathCoordinates());
        fr.add(new ItemScroller());
        fr.add(new InventoryManager());
        fr.add(new ChestStealer());
        fr.add(new AntiAFK());
        fr.add(new GPS());
        fr.add(new AutoTPAccept());
        fr.add(new NoJumpDelay());
        fr.add(new NoInteract());
        fr.add(new NoSlowDown());
        fr.add(new FreeCam());
        fr.add(new AutoTool());
        fr.add(new GuiWalk());
        fr.add(new NoPush());
        fr.add(new NoClip());
        fr.add(new XCarry());
        fr.add(new NoFall());
        fr.add(new AutoArmor());
        fr.add(new ScoreboardFeatures());
        fr.add(new DamageParticles());
        fr.add(new SwingAnimations());
        fr.add(new WorldFeatures());
        fr.add(new ShulkerViewer());
        fr.add(new Crosshair());;
        fr.add(new JumpCircle());
        fr.add(new EntityESP());
        fr.add(new MatrixElytra());
        fr.add(new Chams());
        fr.add(new FullBright());
        fr.add(new CustomModel());
        fr.add(new ItemPhysics());
        fr.add(new AutoFarm());
        fr.add(new FogColor());
        fr.add(new ChinaHat());
        fr.add(new PearlESP());
        fr.add(new NameTags());
        fr.add(new Trails());
        fr.add(new ItemESP());
        fr.add(new BlockESP());
        fr.add(new Tracers());
        fr.add(new ViewModel());
        fr.add(new XRay());
        fr.add(new Nimb());
        fr.add(new HighJump());

        fr.sort(Comparator.comparingInt(m -> Minecraft.getMinecraft().mntsb_17.getStringWidth(((Module) m).getLabel().toLowerCase())).reversed());

    }

    public List<Module> getAllFeatures() {
        return this.fr;
    }

    public List<Module> getFeaturesCategory(ModuleCategory category) {
        List<Module> features = new ArrayList<>();
        for (Module feature : getAllFeatures()) {
            if (feature.getCategory() == category) {
                features.add(feature);
            }
        }
        return features;
    }

    public Module getFeature(Class<? extends Module> classFeature) {
        for (Module feature : getAllFeatures()) {
            if (feature != null) {
                if (feature.getClass() == classFeature) {
                    return feature;
                }
            }
        }
        return null;
    }

    public Module getFeature(String name) {
        for (Module feature : getAllFeatures()) {
            if (feature.getLabel().equals(name)) {
                return feature;
            }
        }
        return null;
    }
}
