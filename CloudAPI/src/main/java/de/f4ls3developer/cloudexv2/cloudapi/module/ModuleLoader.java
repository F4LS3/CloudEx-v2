package de.f4ls3developer.cloudexv2.cloudapi.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.f4ls3developer.cloudexv2.cloudapi.utils.Logger;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;

public class ModuleLoader {

    private ModuleRegistry moduleRegistry;
    private Gson gson;

    private File modulesDir;

    public ModuleLoader() {
        this.moduleRegistry = new ModuleRegistry();
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.modulesDir = new File("./modules/");
        this.modulesDir.mkdirs();
    }

    public void loadModules() {
        try {
            for (File file : this.modulesDir.listFiles()) {
                if(file.getName().endsWith(".jar")) {
                    InputStream stream = new URLClassLoader(new URL[]{file.toURL()}).getResourceAsStream("module.json");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                    StringBuilder builder = new StringBuilder();
                    String line;
                    while((line = reader.readLine()) != null) { builder.append(line); }

                    HashMap<String, Object> conf = this.gson.fromJson(builder.toString(), HashMap.class);
                    if(conf.containsKey("main")) {
                        Class cl = new URLClassLoader(new URL[]{file.toURL()}).loadClass(conf.get("main").toString());
                        if(cl.getSuperclass().getName().equals("de.f4ls3developer.cloudexv2.cloudapi.module.Module")) {
                            Module mod = (Module) cl.newInstance();
                            this.moduleRegistry.registerModule(mod);
                        }

                    } else {
                        Logger.error("'main' attribute wasn't found in \"" + file.getName() + "\"");
                    }
                }
            }

        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void enableModules() {
        this.moduleRegistry.enableModules();
    }

    public void disableModules() {
        this.moduleRegistry.disableModules();
    }

    public ModuleRegistry getModuleRegistry() {
        return moduleRegistry;
    }
}
