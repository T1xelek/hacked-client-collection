package incest.tusky.game.drag.comp.impl;

import incest.tusky.game.tuskevich;
import incest.tusky.game.drag.comp.DragComp;
import incest.tusky.game.module.impl.Render.Hud;

public class DragWaterMark extends DragComp {
    public DragWaterMark() {
        super("WaterMark", 0, 1, 4, 1);
    }

    @Override
    public boolean allowDraw() {
        return tuskevich.instance.featureManager.getFeature(Hud.class).isEnabled() && Hud.waterMark.getCurrentValue();
    }
}
