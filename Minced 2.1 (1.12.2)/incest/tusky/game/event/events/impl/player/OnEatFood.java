package incest.tusky.game.event.events.impl.player;

import incest.tusky.game.event.events.Event;
import net.minecraft.item.ItemStack;

public class OnEatFood implements Event {
    private ItemStack item;

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public ItemStack getItem() {
        return item;
    }
}