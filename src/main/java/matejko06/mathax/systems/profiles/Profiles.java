package matejko06.mathax.systems.profiles;

import matejko06.mathax.MatHaxClient;
import matejko06.mathax.events.game.GameJoinedEvent;
import matejko06.mathax.systems.System;
import matejko06.mathax.systems.Systems;
import matejko06.mathax.utils.Utils;
import matejko06.mathax.utils.misc.NbtUtils;
import matejko06.mathax.bus.EventHandler;
import net.minecraft.nbt.NbtCompound;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Profiles extends System<Profiles> implements Iterable<Profile> {

    public static final File FOLDER = new File(MatHaxClient.FOLDER, "profiles");
    private List<Profile> profiles = new ArrayList<>();

    public Profiles() {
        super("profiles");
    }

    public static Profiles get() {
        return Systems.get(Profiles.class);
    }

    public void add(Profile profile) {
        if (!profiles.contains(profile)) profiles.add(profile);
        profile.save();
        save();
    }

    public void remove(Profile profile) {
        if (profiles.remove(profile)) profile.delete();
        save();
    }

    public Profile get(String name) {
        for (Profile profile : this) {
            if (profile.name.equalsIgnoreCase(name)) {
                return profile;
            }
        }

        return null;
    }

    public List<Profile> getAll() {
        return profiles;
    }

    @Override
    public File getFile() {
        return new File(FOLDER, "profiles.nbt");
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound tag = new NbtCompound();
        tag.put("profiles", NbtUtils.listToTag(profiles));
        return tag;
    }

    @Override
    public Profiles fromTag(NbtCompound tag) {
        profiles = NbtUtils.listFromTag(tag.getList("profiles", 10), tag1 -> new Profile().fromTag((NbtCompound) tag1));
        return this;
    }

    @EventHandler
    private void onGameJoined(GameJoinedEvent event) {
        for (Profile profile : this) {
            if (profile.loadOnJoinIps.contains(Utils.getWorldName())) {
                profile.load();
            }
        }
    }

    @Override
    public Iterator<Profile> iterator() {
        return profiles.iterator();
    }
}
