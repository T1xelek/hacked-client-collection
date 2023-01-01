package incest.tusky.game;


import incest.tusky.game.module.Module;
import incest.tusky.game.module.ModuleManager;
import incest.tusky.game.ui.ClickGui.GuiScreen;
import incest.tusky.game.utils.otherutils.gayutil.ScaleUtils;
import incest.tusky.game.cmd.macro.MacroManager;
import incest.tusky.game.files.FileManager;
import incest.tusky.game.files.impl.FriendConfig;
import incest.tusky.game.files.impl.HudConfig;
import incest.tusky.game.files.impl.MacroConfig;
import incest.tusky.game.cmd.CommandManager;
import incest.tusky.game.drag.DragModern;
import incest.tusky.game.event.EventManager;
import incest.tusky.game.event.EventTarget;
import incest.tusky.game.event.events.impl.input.EventInputKey;
import incest.tusky.game.friend.FriendManager;
import incest.tusky.game.utils.math.ShaderShell;
//import viamcp.ViaMCP;
import incest.tusky.game.ui.celestun4ik.guiscreencomponent;
import incest.tusky.game.ui.config.ConfigManager;
import incest.tusky.game.utils.math.RotationHelper;
import incest.tusky.game.utils.math.TPSUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderPlayer;
import org.lwjgl.opengl.Display;

import java.awt.*;
import java.io.IOException;

public class tuskevich {
    public Long time;
    public static ScaleUtils scale = new ScaleUtils(2);
    public ModuleManager featureManager;
    public FileManager fileManager;
    public static long playTimeStart = 0;
    public DragModern draggableHUD;

    public MacroManager macroManager;
    public ConfigManager configManager;
    public EventManager eventManager;
    public CommandManager commandManager;

    public FriendManager friendManager;
    public guiscreencomponent clickGui;
    public GuiScreen Celestial;
    public static tuskevich instance = new tuskevich();
    public RotationHelper.Rotation rotation;
    public static double deltaTime() {
        return Minecraft.getDebugFPS() > 0 ? (1.0000 / Minecraft.getDebugFPS()) : 1;
    }

    public String name = "Minced", type = "Recode", version = "2.1";


    public void init() {
        Display.setTitle(name + " " + type + "  - https://vk.com/mincedclient");
        ShaderShell.init();
        time = System.currentTimeMillis();
        (fileManager = new FileManager()).loadFiles();
        friendManager = new FriendManager();
        featureManager = new ModuleManager();
        macroManager = new MacroManager();
        eventManager = new EventManager();
        configManager = new ConfigManager();
        draggableHUD = new DragModern();
        commandManager = new CommandManager();
        clickGui = new guiscreencomponent();
        Celestial = new GuiScreen();
        rotation = new RotationHelper.Rotation();


        for (RenderPlayer render : Minecraft.getMinecraft().getRenderManager().getSkinMap().values()) {
        }
        TPSUtils tpsUtils = new TPSUtils();
        try {
            //  ViaMCP.getInstance().start();
            // ViaMCP.getInstance().initAsyncSlider();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            this.fileManager.getFile(FriendConfig.class).loadFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            this.fileManager.getFile(MacroConfig.class).loadFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            this.fileManager.getFile(HudConfig.class).loadFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        EventManager.register(this);
        EventManager.register(this.rotation);

    }

    public static final Color getClientColor() {
        /* 111 */     return new Color(0, 0, 0, 255);
        /*     */   }
    public final Color getAlternateClientColor() {
        return new Color(28, 167, 222);
    }

    public void stop() {
        tuskevich.instance.configManager.saveConfig("default");
        (fileManager = new FileManager()).saveFiles();
        EventManager.unregister(this);
    }

    @EventTarget
    public void onInputKey(EventInputKey event) {
        featureManager.getAllFeatures().stream().filter(module -> module.getBind() == event.getKey()).forEach(Module::toggle);
        macroManager.getMacros().stream().filter(macros -> macros.getKey() == event.getKey()).forEach(macros -> Minecraft.getMinecraft().player.sendChatMessage(macros.getValue()));
    }
}
