package incest.tusky.game.drag.comp.impl;

import incest.tusky.game.tuskevich;
import incest.tusky.game.drag.comp.DragComp;
import incest.tusky.game.module.impl.Render.Hud;

public class DragArmor extends DragComp
{
    public DragArmor()
    {
        super("Armor Status", 380, 357, 4, 1);
    }

    @Override
    public boolean allowDraw()
    {
        return tuskevich.instance.featureManager.getFeature(Hud.class).isEnabled() && Hud.armorHUD.getCurrentValue();
    }
}
