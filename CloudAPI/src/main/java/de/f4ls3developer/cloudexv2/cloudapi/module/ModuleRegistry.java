package de.f4ls3developer.cloudexv2.cloudapi.module;

import de.f4ls3developer.cloudexv2.cloudapi.utils.Logger;

import java.util.ArrayList;
import java.util.List;

public class ModuleRegistry {

    private List<Module> modules;

    public ModuleRegistry() {
        this.modules = new ArrayList<>();
    }

    public void registerModule(Module module) {
        if(!this.modules.contains(module)) {
            this.modules.add(module);
            Logger.log("Loaded \"" + module.getClass().getSimpleName() + "\" successfully");
        }
    }

    public void unregisterModule(Module module) {
        if(this.modules.contains(module)) {
            module.onPost();
            module.onDisable();
            this.modules.remove(module);
        }
    }

    public void enableModules() {
        for (Module m : this.modules) {
            m.onPre();
            m.onEnable();
            m.onPost();
        }
    }

    public void disableModules() {
        for (Module m : this.modules) {
            m.onDisable();
            unregisterModule(m);
        }
    }

    public List<Module> getModules() {
        return modules;
    }
}
