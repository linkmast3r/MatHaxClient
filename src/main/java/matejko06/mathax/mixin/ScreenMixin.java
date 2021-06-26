package matejko06.mathax.mixin;

import matejko06.mathax.MatHaxClient;
import matejko06.mathax.events.game.GetTooltipEvent;
import matejko06.mathax.systems.modules.Modules;
import matejko06.mathax.systems.modules.render.NoRender;
import matejko06.mathax.utils.Utils;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(Screen.class)
public abstract class ScreenMixin {
    @Shadow public int height;

    @Shadow
    public int width;

    @Inject(method = "renderBackground(Lnet/minecraft/client/util/math/MatrixStack;)V", at = @At("HEAD"), cancellable = true)
    private void onRenderBackground(CallbackInfo info) {
        if (Utils.canUpdate() && Modules.get().get(NoRender.class).noGuiBackground())
            info.cancel();
    }

    @ModifyArgs(method = "renderTooltip(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/item/ItemStack;II)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;renderTooltip(Lnet/minecraft/client/util/math/MatrixStack;Ljava/util/List;Ljava/util/Optional;II)V"))
    private void getList(Args args, MatrixStack matrixStack, ItemStack itemStack, int x, int y) {
        GetTooltipEvent.Modify event = MatHaxClient.EVENT_BUS.post(GetTooltipEvent.Modify.get(itemStack, args.get(1), matrixStack, x, y));

        args.set(0, event.matrixStack);
        args.set(1, event.list);
        args.set(3, event.x);
        args.set(4, event.y);
    }
}
