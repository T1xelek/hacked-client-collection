package incest.tusky.game.module;

public enum ModuleCategory {
    Combat("Combat"),
    Player("Player"),
    Movement("Movement"),
    Visual("Visual"),
    Other("Other");
    private final String displayName;

    ModuleCategory(final String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}
