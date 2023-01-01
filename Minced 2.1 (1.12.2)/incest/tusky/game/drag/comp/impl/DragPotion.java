package incest.tusky.game.drag.comp.impl;

import incest.tusky.game.tuskevich;
import incest.tusky.game.drag.comp.DragComp;
import incest.tusky.game.module.impl.Render.Hud;

public class DragPotion extends DragComp {

    public DragPotion() {
        super("Potion Status", 50, 100, 1, 1);
    }

    @Override
    public boolean allowDraw() {
        return tuskevich.instance.featureManager.getFeature(Hud.class).isEnabled() && Hud.potions.getCurrentValue();
    }
}
