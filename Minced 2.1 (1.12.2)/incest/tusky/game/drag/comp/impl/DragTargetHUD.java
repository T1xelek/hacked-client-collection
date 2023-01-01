package incest.tusky.game.drag.comp.impl;

import incest.tusky.game.tuskevich;
import incest.tusky.game.drag.comp.DragComp;
import incest.tusky.game.module.impl.Render.TargetHUD;

public class DragTargetHUD extends DragComp {

    public DragTargetHUD() {
        super("TargetHUD", 350, 70, 1, 1);
    }

    @Override
    public boolean allowDraw() {
        return tuskevich.instance.featureManager.getFeature(TargetHUD.class).isEnabled();
    }
}
