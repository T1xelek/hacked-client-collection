package incest.tusky.game.drag.comp.impl;

import incest.tusky.game.tuskevich;
import incest.tusky.game.drag.comp.DragComp;
import incest.tusky.game.module.impl.Render.Hud;

public class DragIndicator extends DragComp {

    public DragIndicator() {
        super("Indicators", 350, 25, 1, 1);
    }

    @Override
    public boolean allowDraw() {
        return tuskevich.instance.featureManager.getFeature(Hud.class).isEnabled();
    }
}
