package mathax.client.mixin;

import mathax.client.MatHaxClient;
import mathax.client.events.game.ReceiveMessageEvent;
import mathax.client.mixininterface.IChatHud;
import mathax.client.systems.modules.Modules;
import mathax.client.systems.modules.misc.BetterChat;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ChatHud.class)
public abstract class ChatHudMixin implements IChatHud {
    @Shadow protected abstract void addMessage(Text message, int messageId, int timestamp, boolean refresh);

    @Inject(at = @At("HEAD"), method = "addMessage(Lnet/minecraft/text/Text;I)V", cancellable = true)
    private void onAddMessage(Text text, int id, CallbackInfo info) {
        if (MatHaxClient.EVENT_BUS.post(ReceiveMessageEvent.get(text, id)).isCancelled()) info.cancel();
    }

    @Redirect(method = "addMessage(Lnet/minecraft/text/Text;IIZ)V", at = @At(value = "INVOKE", target = "Ljava/util/List;size()I"))
    private int addMessageListSizeProxy(List<ChatHudLine> list) {
        BetterChat betterChat = Modules.get().get(BetterChat.class);
        return betterChat.isLongerChat() && betterChat.getChatLength() > 100 ? 1 : list.size();
    }

    @Override
    public void add(Text message, int messageId, int timestamp, boolean refresh) {
        addMessage(message, messageId, timestamp, refresh);
    }
}
