package incest.tusky.game.drag.comp.impl;

import incest.tusky.game.tuskevich;
import incest.tusky.game.drag.comp.DragComp;
import incest.tusky.game.module.impl.Render.Hud;
import incest.tusky.game.module.impl.Util.StaffAlert;

public class DragStaff extends DragComp {

    public DragStaff() {
        super("Staff Statistics", 50, 10, 1, 1);
    }

    @Override
    public boolean allowDraw() {
        return tuskevich.instance.featureManager.getFeature(StaffAlert.class).isEnabled();
    }
}
