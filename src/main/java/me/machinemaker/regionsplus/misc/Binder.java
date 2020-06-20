package me.machinemaker.regionsplus.misc;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import me.machinemaker.regionsplus.RegionsPlus;
import me.machinemaker.regionsplus.RegionsPlus.MainConfig;
import me.machinemaker.regionsplus.utils.RegionManager;
import me.machinemaker.regionsplus.utils.SelectionManager;

public class Binder extends AbstractModule {

    private final RegionsPlus regionsPlus;
    private final RegionManager regionManager;
    private final SelectionManager selectionManager;
    private final MainConfig config;

    public Binder(RegionsPlus regionsPlus, RegionManager regionManager, SelectionManager selectionManager, MainConfig config) {
        this.regionsPlus = regionsPlus;
        this.regionManager = regionManager;
        this.selectionManager = selectionManager;
        this.config = config;
    }

    public Injector createInjector() {
        return Guice.createInjector(this);
    }

    @Override
    protected void configure() {
        this.bind(RegionsPlus.class).toInstance(this.regionsPlus);
        this.bind(RegionManager.class).toInstance(this.regionManager);
        this.bind(SelectionManager.class).toInstance(this.selectionManager);
        this.bind(MainConfig.class).toInstance(this.config);
    }
}
