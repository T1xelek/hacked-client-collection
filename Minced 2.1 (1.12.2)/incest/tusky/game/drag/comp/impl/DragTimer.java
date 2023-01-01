package incest.tusky.game.drag.comp.impl;

import incest.tusky.game.tuskevich;
import incest.tusky.game.drag.comp.DragComp;
import incest.tusky.game.module.impl.Movement.Timer;

public class DragTimer extends DragComp {

    public DragTimer() {
        super("Timer", 160, 400, 1, 1);
    }

    @Override
    public boolean allowDraw() {
        return tuskevich.instance.featureManager.getFeature(Timer.class).isEnabled();
    }
}
