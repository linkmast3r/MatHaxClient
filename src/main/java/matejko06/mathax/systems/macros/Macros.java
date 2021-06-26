package matejko06.mathax.systems.macros;

import matejko06.mathax.MatHaxClient;
import matejko06.mathax.events.mathax.KeyEvent;
import matejko06.mathax.events.mathax.MouseButtonEvent;
import matejko06.mathax.systems.System;
import matejko06.mathax.systems.Systems;
import matejko06.mathax.utils.misc.NbtUtils;
import matejko06.mathax.utils.misc.input.KeyAction;
import matejko06.mathax.bus.EventHandler;
import matejko06.mathax.bus.EventPriority;
import net.minecraft.nbt.NbtCompound;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Macros extends System<Macros> implements Iterable<Macro> {
    private List<Macro> macros = new ArrayList<>();

    public Macros() {
        super("macros");
    }

    public static Macros get() {
        return Systems.get(Macros.class);
    }

    public void add(Macro macro) {
        macros.add(macro);
        MatHaxClient.EVENT_BUS.subscribe(macro);
        save();
    }

    public List<Macro> getAll() {
        return macros;
    }

    public void remove(Macro macro) {
        if (macros.remove(macro)) {
            MatHaxClient.EVENT_BUS.unsubscribe(macro);
            save();
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onKey(KeyEvent event) {
        if (event.action == KeyAction.Release) return;

        for (Macro macro : macros) {
            if (macro.onAction(true, event.key)) return;
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onButton(MouseButtonEvent event) {
        if (event.action == KeyAction.Release) return;

        for (Macro macro : macros) {
            if (macro.onAction(false, event.button)) return;
        }
    }

    @Override
    public Iterator<Macro> iterator() {
        return macros.iterator();
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound tag = new NbtCompound();
        tag.put("macros", NbtUtils.listToTag(macros));
        return tag;
    }

    @Override
    public Macros fromTag(NbtCompound tag) {
        for (Macro macro : macros) MatHaxClient.EVENT_BUS.unsubscribe(macro);

        macros = NbtUtils.listFromTag(tag.getList("macros", 10), tag1 -> new Macro().fromTag((NbtCompound) tag1));

        for (Macro macro : macros) MatHaxClient.EVENT_BUS.subscribe(macro);
        return this;
    }
}
