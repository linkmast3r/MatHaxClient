package mathax.client.systems.modules.player;

import mathax.client.events.world.TickEvent;
import mathax.client.settings.BoolSetting;
import mathax.client.settings.Setting;
import mathax.client.settings.SettingGroup;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.utils.player.FindItemResult;
import mathax.client.utils.player.InvUtils;
import mathax.client.utils.player.Rotations;
import mathax.client.bus.EventHandler;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;

public class EXPThrower extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Boolean> lookDown = sgGeneral.add(new BoolSetting.Builder()
            .name("rotate")
            .description("Forces you to rotate downwards when throwing bottles.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> autoToggle = sgGeneral.add(new BoolSetting.Builder()
            .name("auto-toggle")
            .description("Toggles off when your armor is repaired.")
            .defaultValue(true)
            .build()
    );

    public EXPThrower() {
        super(Categories.Player, "exp-thrower", "Automatically throws XP bottles in your hotbar.");
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (autoToggle.get()) {

            boolean shouldThrow = false;

            for (ItemStack itemStack : mc.player.getInventory().armor) {
                // If empty
                if (itemStack.isEmpty()) continue;

                // If no mending
                if (EnchantmentHelper.getLevel(Enchantments.MENDING, itemStack) < 1) continue;

                // If damaged
                if (itemStack.isDamaged()) {
                    shouldThrow = true;
                    break;
                }
            }

            if (!shouldThrow) {
                toggle();
                return;
            }
        }

        FindItemResult exp = InvUtils.findInHotbar(Items.EXPERIENCE_BOTTLE);

        if (exp.found()) {
            if (lookDown.get()) Rotations.rotate(mc.player.getYaw(), 90, () -> throwExp(exp));
            else throwExp(exp);
        }
    }

    private void throwExp(FindItemResult exp) {
        if (exp.isOffhand()) {
            mc.interactionManager.interactItem(mc.player, mc.world, Hand.OFF_HAND);
        } else {
            int prevSlot = mc.player.getInventory().selectedSlot;
            InvUtils.swap(exp.getSlot());
            mc.interactionManager.interactItem(mc.player, mc.world, Hand.MAIN_HAND);
            InvUtils.swap(prevSlot);
        }
    }
}
