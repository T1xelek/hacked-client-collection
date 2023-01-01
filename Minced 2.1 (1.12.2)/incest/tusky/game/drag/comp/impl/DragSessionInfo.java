package incest.tusky.game.drag.comp.impl;

import incest.tusky.game.tuskevich;
import incest.tusky.game.drag.comp.DragComp;
import incest.tusky.game.module.impl.Render.Hud;

public class DragSessionInfo extends DragComp {

    public DragSessionInfo() {
        super("Session Info", 0, 10, 1, 1);
    }

    @Override
    public boolean allowDraw() {
        return tuskevich.instance.featureManager.getFeature(Hud.class).isEnabled();
    }
}
