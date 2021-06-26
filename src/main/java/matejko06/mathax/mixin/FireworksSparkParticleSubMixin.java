package matejko06.mathax.mixin;

import matejko06.mathax.systems.modules.Modules;
import matejko06.mathax.systems.modules.render.NoRender;
import net.minecraft.client.particle.FireworksSparkParticle;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {FireworksSparkParticle.Explosion.class, FireworksSparkParticle.Flash.class})
public class FireworksSparkParticleSubMixin {
    @Inject(method = "buildGeometry", at = @At("HEAD"), cancellable = true)
    private void buildExplosionGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta, CallbackInfo info) {
        if (Modules.get().get(NoRender.class).noFireworkExplosions()) info.cancel();
    }
}