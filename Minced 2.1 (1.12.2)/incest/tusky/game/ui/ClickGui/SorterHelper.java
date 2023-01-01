package incest.tusky.game.ui.ClickGui;


import incest.tusky.game.ui.ClickGui.component.Component;
import incest.tusky.game.ui.ClickGui.component.impl.ModuleComponent;

import java.util.Comparator;

public class SorterHelper implements Comparator<Component> {

    @Override
    public int compare(Component component, Component component2) {
        if (component instanceof ModuleComponent && component2 instanceof ModuleComponent) {
            return component.getName().compareTo(component2.getName());
        }
        return 0;
    }
}