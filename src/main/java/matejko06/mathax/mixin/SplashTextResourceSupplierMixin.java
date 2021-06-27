package matejko06.mathax.mixin;

import matejko06.mathax.systems.config.Config;
import net.minecraft.client.resource.SplashTextResourceSupplier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Mixin(SplashTextResourceSupplier.class)
public class SplashTextResourceSupplierMixin {
    private boolean override = true;
    private final Random random = new Random();

    private final List<String> mathaxSplashes = getMatHaxSplashes();

    @Inject(method = "get", at = @At("HEAD"), cancellable = true)
    private void onApply(CallbackInfoReturnable<String> cir) {
        if (override) cir.setReturnValue(mathaxSplashes.get(random.nextInt(mathaxSplashes.size())));
        override = !override;
    }

    private static List<String> getMatHaxSplashes() {
        return Arrays.asList(
                "§cMatHax on TOP!",
                "§7Matejko06 §cbased god",
                "§7GeekieCoder §cbased god",
                "§cMatHaxClient.xyz",
                "§cMatHaxClient.xyz/Discord"
        );
    }
}
