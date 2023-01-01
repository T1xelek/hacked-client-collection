package incest.tusky.game.module.impl.Player;

import incest.tusky.game.module.Module;
import incest.tusky.game.ui.settings.impl.BooleanSetting;
import incest.tusky.game.module.ModuleCategory;

public class NoPush extends Module {
    public static BooleanSetting water = new BooleanSetting("Water", true, () -> true);
    public static BooleanSetting players = new BooleanSetting("Entity", true, () -> true);
    public static BooleanSetting blocks = new BooleanSetting("Blocks", true, () -> true);

    public NoPush() {
        super("NoPush", "Вы не отталкиваетесь", ModuleCategory.Player);
        addSettings(players, water, blocks);
    }
}
