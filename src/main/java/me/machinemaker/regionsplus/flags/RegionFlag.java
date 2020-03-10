package me.machinemaker.regionsplus.flags;

public class RegionFlag<T, J> {
    protected T type;
    private J state;

    public RegionFlag(T type, J state) {
        this.type = type;
        this.state = state;
    }

    public T getType() {
        return type;
    }

    public J getValue() {
        return state;
    }

    public void setValue(J state) {
        this.state = state;
    }

}
