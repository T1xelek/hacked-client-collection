package incest.tusky.game.module.impl.Render;

import incest.tusky.game.event.EventTarget;
import incest.tusky.game.event.events.impl.render.EventRenderScoreboard;
import incest.tusky.game.module.Module;
import incest.tusky.game.module.ModuleCategory;
import incest.tusky.game.ui.settings.impl.BooleanSetting;
import incest.tusky.game.ui.settings.impl.NumberSetting;
import net.minecraft.client.renderer.GlStateManager;

public class ScoreboardFeatures extends Module {

    public static BooleanSetting noScore;
    public NumberSetting x;
    public NumberSetting y;

    public ScoreboardFeatures() {
        super("Scoreboard", "Позволяет изменять скорбоард", ModuleCategory.Visual);
        noScore = new BooleanSetting("No Scoreboard", true, () -> true);
        x = new NumberSetting("Scoreboard X", 0, -100, 100, 1, () -> !noScore.getCurrentValue());
        y = new NumberSetting("Scoreboard Y", 0, -100, 100, 1, () -> !noScore.getCurrentValue());
        addSettings(noScore);
    }

    @EventTarget
    public void onRenderScoreboard(EventRenderScoreboard event) {
        if (event.isPre()) {
            GlStateManager.translate(-x.getCurrentValue(), y.getCurrentValue(), 12);
        } else {
            GlStateManager.translate(x.getCurrentValue(), -y.getCurrentValue(), 12);
        }
    }
}
