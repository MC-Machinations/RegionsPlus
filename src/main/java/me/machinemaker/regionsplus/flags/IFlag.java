package me.machinemaker.regionsplus.flags;

public interface IFlag<V> {
    String getDescription();

    V getDefault();
}
