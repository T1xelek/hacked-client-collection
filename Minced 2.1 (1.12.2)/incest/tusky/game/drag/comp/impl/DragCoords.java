package incest.tusky.game.drag.comp.impl;

import incest.tusky.game.tuskevich;
import incest.tusky.game.drag.comp.DragComp;
import incest.tusky.game.module.impl.Render.Hud;

public class DragCoords extends DragComp {

    public DragCoords() {
        super("Coordinates", 350, 25, 1, 1);
    }

    @Override
    public boolean allowDraw() {
        return tuskevich.instance.featureManager.getFeature(Hud.class).isEnabled() && Hud.coords.getCurrentValue();
    }
}
