package matejko06.mathax.mixin;

import matejko06.mathax.systems.modules.Modules;
import matejko06.mathax.systems.modules.movement.NoSlow;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlimeBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static matejko06.mathax.utils.Utils.mc;

@Mixin(SlimeBlock.class)
public class SlimeBlockMixin {
    @Inject(method = "onSteppedOn", at = @At("HEAD"), cancellable = true)
    private void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity, CallbackInfo info) {
        if (Modules.get().get(NoSlow.class).slimeBlock() && entity == mc.player) info.cancel();
    }
}
