package matejko06.mathax.mixin;

import matejko06.mathax.systems.modules.Modules;
import matejko06.mathax.systems.modules.movement.Slippy;
import matejko06.mathax.systems.modules.render.Xray;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public abstract class BlockMixin extends AbstractBlock implements ItemConvertible {
    public BlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "shouldDrawSide", at = @At("RETURN"), cancellable = true)
    private static void onShouldDrawSide(BlockState state, BlockView world, BlockPos pos, Direction side, BlockPos blockPos, CallbackInfoReturnable<Boolean> info) {
        Xray xray = Modules.get().get(Xray.class);

        if (xray.isActive()) {
            info.setReturnValue(xray.modifyDrawSide(state, world, pos, side, info.getReturnValueZ()));
        }
    }

    @Inject(method = "getSlipperiness", at = @At("RETURN"), cancellable = true)
    public void getSlipperiness(CallbackInfoReturnable<Float> info) {
        // For some retarded reason Tweakeroo calls this method before mathax is initialized
        if (Modules.get() == null) return;

        Slippy slippy = Modules.get().get(Slippy.class);
        Block block = (Block) (Object) this;

        if (slippy.isActive() && !slippy.blocks.get().contains(block)) {
            info.setReturnValue(slippy.slippness.get().floatValue());
        }
    }

}
