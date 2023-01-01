package incest.tusky.game.module.impl.Util;

import incest.tusky.game.module.Module;
import incest.tusky.game.module.ModuleCategory;

public class ChatHistory
        extends Module {
    public ChatHistory() {
        super("ChatHistory", "Показывает историю чата, даже если вы перезашли на сервер", ModuleCategory.Other);
    }
}
