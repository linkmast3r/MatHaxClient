package matejko06.mathax.mixin;

import matejko06.mathax.systems.modules.Modules;
import matejko06.mathax.systems.modules.misc.AutoReconnect;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DisconnectedScreen.class)
public abstract class DisconnectedScreenMixin extends Screen {
    @Shadow private int reasonHeight;

    @Unique
    private ButtonWidget reconnectBtn;

    protected DisconnectedScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void onRenderBackground(CallbackInfo info) {
        if (Modules.get().get(AutoReconnect.class).lastServerInfo != null) {
            int x = width / 2 - 100;
            int y = Math.min((height / 2 + reasonHeight / 2) + 32, height - 30);

            reconnectBtn = addDrawableChild(new ButtonWidget(x, y, 200, 20, new LiteralText("Reconnect"), button -> ConnectScreen.connect(new MultiplayerScreen(new TitleScreen()), client, ServerAddress.parse(Modules.get().get(AutoReconnect.class).lastServerInfo.address), MinecraftClient.getInstance().getCurrentServerEntry())));
        }
    }
}
