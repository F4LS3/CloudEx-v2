package de.f4ls3developer.cloudexv2.cloudapi.module;

public abstract class Module {

    protected void onPre() {
        System.out.println("[" + getClass().getSimpleName() + "] Enabling module...");
    }

    protected void onPost() {
        System.out.println("[" + getClass().getSimpleName() + "] Enabled Module...");
    }

    public abstract void onEnable();
    public abstract void onDisable();

}
