package incest.tusky.game.utils.render;

public abstract class Event {

    public boolean isCanceled = false;

    public void setCanceled() {
        isCanceled = true;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

}
