package matejko06.mathax.systems.modules.combat;

import matejko06.mathax.events.world.TickEvent;
import matejko06.mathax.settings.BoolSetting;
import matejko06.mathax.settings.DoubleSetting;
import matejko06.mathax.settings.Setting;
import matejko06.mathax.settings.SettingGroup;
import matejko06.mathax.systems.modules.Categories;
import matejko06.mathax.systems.modules.Module;
import matejko06.mathax.utils.entity.EntityUtils;
import matejko06.mathax.utils.entity.SortPriority;
import matejko06.mathax.utils.entity.TargetUtils;
import matejko06.mathax.utils.player.FindItemResult;
import matejko06.mathax.utils.player.InvUtils;
import matejko06.mathax.utils.player.PlayerUtils;
import matejko06.mathax.utils.player.Rotations;
import matejko06.mathax.utils.world.BlockUtils;
import matejko06.mathax.bus.EventHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class AutoCity extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Double> targetRange = sgGeneral.add(new DoubleSetting.Builder()
        .name("target-range")
        .description("The radius in which players get targeted.")
        .defaultValue(4)
        .min(0)
        .sliderMax(5)
        .build()
    );

    private final Setting<Boolean> support = sgGeneral.add(new BoolSetting.Builder()
        .name("support")
        .description("If there is no block below a city block it will place one before mining.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> rotate = sgGeneral.add(new BoolSetting.Builder()
        .name("rotate")
        .description("Automatically rotates you towards the city block.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> selfToggle = sgGeneral.add(new BoolSetting.Builder()
        .name("self-toggle")
        .description("Automatically toggles off after activation.")
        .defaultValue(true)
        .build()
    );

    private PlayerEntity target;
    private BlockPos blockPosTarget;
    private boolean sentMessage;

    public AutoCity() {
        super(Categories.Combat, "auto-city", "Automatically cities a target by mining the nearest obsidian next to them.");
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (TargetUtils.isBadTarget(target, targetRange.get())) {
            PlayerEntity search = TargetUtils.getPlayerTarget(targetRange.get(), SortPriority.LowestDistance);
            if (search != target) sentMessage = false;
            target = search;
        }

        if (TargetUtils.isBadTarget(target, targetRange.get())) {
            target = null;
            blockPosTarget = null;
            if (selfToggle.get()) toggle();
            return;
        }

        blockPosTarget = EntityUtils.getCityBlock(target);

        if (blockPosTarget == null) {
            if (selfToggle.get()) {
                error("No target block found... disabling.");
                toggle();
            }
            target = null;
            return;
        }

        if (PlayerUtils.distanceTo(blockPosTarget) > mc.interactionManager.getReachDistance() && selfToggle.get()) {
            error("Target block out of reach... disabling.");
            toggle();
            return;
        }

        if (!sentMessage) {
            info("Attempting to city %s.", target.getEntityName());
            sentMessage = true;
        }

        FindItemResult pickaxe = InvUtils.find(itemStack -> itemStack.getItem() == Items.DIAMOND_PICKAXE || itemStack.getItem() == Items.NETHERITE_PICKAXE);

        if (!pickaxe.isHotbar()) {
            if (selfToggle.get()) {
                error("No pickaxe found... disabling.");
                toggle();
            }
            return;
        }

        if (support.get()) {
            BlockUtils.place(blockPosTarget.down(1), InvUtils.findInHotbar(Items.OBSIDIAN), rotate.get(), 0, true);
        }

        InvUtils.swap(pickaxe.getSlot());

        if (rotate.get()) Rotations.rotate(Rotations.getYaw(blockPosTarget), Rotations.getPitch(blockPosTarget), () -> mine(blockPosTarget));
        else mine(blockPosTarget);

        if (selfToggle.get()) toggle();
    }

    private void mine(BlockPos blockPos) {
        mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.START_DESTROY_BLOCK, blockPos, Direction.UP));
        mc.player.swingHand(Hand.MAIN_HAND);
        mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.STOP_DESTROY_BLOCK, blockPos, Direction.UP));
    }

    @Override
    public String getInfoString() {
        return EntityUtils.getName(target);
    }
}
