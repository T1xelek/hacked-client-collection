package incest.tusky.game.module.impl.Render;


import incest.tusky.game.module.Module;
import incest.tusky.game.module.ModuleCategory;
import incest.tusky.game.ui.settings.impl.NumberSetting;

public class ItemPhysics extends Module {
    public static NumberSetting physicsSpeed;

    public ItemPhysics() {
        super("ItemPhysics", ModuleCategory.Visual);
                physicsSpeed = new NumberSetting("Physics Speed", 0.5F, 0.1F, 5.0F, 0.5F, () -> true);
        addSettings(physicsSpeed);
    }
}
