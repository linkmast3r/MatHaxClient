package mathax.client.systems;

import mathax.client.MatHaxClient;
import mathax.client.systems.accounts.Accounts;
import mathax.client.systems.commands.Commands;
import mathax.client.systems.config.Config;
import mathax.client.systems.friends.Friends;
import mathax.client.systems.macros.Macros;
import mathax.client.systems.modules.Modules;
import mathax.client.systems.profiles.Profiles;
import mathax.client.systems.proxies.Proxies;
import mathax.client.systems.waypoints.Waypoints;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Systems {
    @SuppressWarnings("rawtypes")
    private static final Map<Class<? extends System>, System<?>> systems = new HashMap<>();

    private static final List<Runnable> preLoadTasks = new ArrayList<>(1);
    private static System<?> config;

    public static void init() {
        config = add(new Config());
        config.load();
        config.init();

        add(new Modules());
        add(new Commands());
        add(new Friends());
        add(new Macros());
        add(new Accounts());
        add(new Waypoints());
        add(new Profiles());
        add(new Proxies());

        for (System<?> system : systems.values()) {
            if (system != config) system.init();
        }
    }

    private static System<?> add(System<?> system) {
        systems.put(system.getClass(), system);
        MatHaxClient.EVENT_BUS.subscribe(system);

        return system;
    }

    public static void save(File folder) {
        MatHaxClient.LOG.info(MatHaxClient.logprefix + "Saving...");
        long start = java.lang.System.currentTimeMillis();

        for (System<?> system : systems.values()) system.save(folder);

        MatHaxClient.LOG.info(MatHaxClient.logprefix + "Saved in {} milliseconds.", java.lang.System.currentTimeMillis() - start);
    }

    public static void save() {
        save(null);
    }

    public static void addPreLoadTask(Runnable task) {
        preLoadTasks.add(task);
    }

    public static void load(File folder) {
        MatHaxClient.LOG.info(MatHaxClient.logprefix + "Loading...");
        long start = java.lang.System.currentTimeMillis();

        for (Runnable task : preLoadTasks) task.run();

        for (System<?> system : systems.values()) {
            if (system != config) system.load(folder);
        }

        MatHaxClient.LOG.info(MatHaxClient.logprefix + "Loaded in {} milliseconds.", java.lang.System.currentTimeMillis() - start);
    }

    public static void load() {
        load(null);
    }

    @SuppressWarnings("unchecked")
    public static <T extends System<?>> T get(Class<T> klass) {
        return (T) systems.get(klass);
    }
}
