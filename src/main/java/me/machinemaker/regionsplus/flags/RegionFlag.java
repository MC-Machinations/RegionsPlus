package me.machinemaker.regionsplus.flags;

public class RegionFlag<T extends IFlag<J>, J> {
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

    public boolean isSet() {
        return !this.type.getDefault().toString().equals(this.state.toString());
    }

}
