package matejko06.mathax.mixin;

import matejko06.mathax.systems.modules.Modules;
import matejko06.mathax.systems.modules.render.WallHack;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderLayers.class)
public class RenderLayersMixin {

    @Inject(method = "getBlockLayer", at = @At("HEAD"), cancellable = true)
    private static void onGetBlockLayer(BlockState state, CallbackInfoReturnable<RenderLayer> cir) {
        if(Modules.get() != null) {
            WallHack wallHack = Modules.get().get(WallHack.class);

            if(wallHack.isActive()) {
                if(wallHack.blocks.get().contains(state.getBlock())) {
                    cir.setReturnValue(RenderLayer.getTranslucent());
                }
            }
        }
    }

}
