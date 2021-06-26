package matejko06.mathax.mixin;

import com.g00fy2.versioncompare.Version;
import matejko06.mathax.MatHaxClient;
import matejko06.mathax.gui.GuiThemes;
import matejko06.mathax.gui.screens.NewUpdateScreen;
import matejko06.mathax.systems.config.Config;
import matejko06.mathax.utils.Utils;
import matejko06.mathax.utils.network.HttpUtils;
import matejko06.mathax.utils.network.MatHaxExecutor;
import matejko06.mathax.utils.render.color.Color;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {

    private final int RED = Color.fromRGBA(255, 0, 0, 255);
    private final int WHITE = Color.fromRGBA(255, 255, 255, 255);
    private final int GRAY = Color.fromRGBA(175, 175, 175, 255);

    private String text1;
    private int text1Length;

    private String text2;
    private int text2Length2;

    private String text3;
    private int text3Length3;

    private String text4;
    private int text4Length4;

    private String text5;
    private int text5Length5;

    private String text6;
    private int text6Length6;

    private String text7;
    private int text7Length7;

    private String text8;
    private int text8Length8;

    private String text9;
    private int text9Length9;

    private String text10;
    private int text10Length10;

    private String text11;

    private int fullLength;
    private int prevWidth;

    public TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void onInit(CallbackInfo info) {

        text1 = "MatHax Client";
        text2 = " ";
        text3 = "v" + MatHaxClient.clientversion;
        text4 = " ";
        text5 = "by";
        text6 = " ";
        text7 = "Matejko06";
        text8 = " ";
        text9 = "&";
        text10 = " ";
        text11 = "GeekieCoder";

        text1Length = textRenderer.getWidth(text1);
        text2Length2 = textRenderer.getWidth(text2);
        text3Length3 = textRenderer.getWidth(text3);
        text4Length4 = textRenderer.getWidth(text4);
        text5Length5 = textRenderer.getWidth(text5);
        text6Length6 = textRenderer.getWidth(text6);
        text7Length7 = textRenderer.getWidth(text7);
        text8Length8 = textRenderer.getWidth(text8);
        text9Length9 = textRenderer.getWidth(text9);
        text10Length10 = textRenderer.getWidth(text10);
        int text1Length = textRenderer.getWidth(text1);
        int text2Length = textRenderer.getWidth(text2);
        int text3Length = textRenderer.getWidth(text3);
        int text4Length = textRenderer.getWidth(text4);
        int text5Length = textRenderer.getWidth(text5);
        int text6Length = textRenderer.getWidth(text6);
        int text7Length = textRenderer.getWidth(text7);
        int text8Length = textRenderer.getWidth(text8);
        int text9Length = textRenderer.getWidth(text9);
        int text10Length = textRenderer.getWidth(text10);
        int text11Length = textRenderer.getWidth(text11);

        fullLength = text1Length + text2Length + text3Length + text4Length + text5Length + text6Length + text7Length + text8Length + text9Length + text10Length + text11Length;
        prevWidth = 0;
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/TitleScreen;drawStringWithShadow(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V", ordinal = 0))
    private void onRenderIdkDude(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo info) {
        if (Utils.firstTimeTitleScreen) {
            Utils.firstTimeTitleScreen = false;
            MatHaxClient.LOG.info("[MatHax] Checking for latest version of MatHax Client...");

            MatHaxExecutor.execute(() -> HttpUtils.getLines("http://mathaxclient.xyz/API/version_1-17.html", s -> {
                Version latestVer = new Version(s);
                if (latestVer.isHigherThan(Config.get().version)) MinecraftClient.getInstance().openScreen(new NewUpdateScreen(GuiThemes.get(), latestVer));
            }));
            MatHaxClient.LOG.info("[MatHax] Checked for latest version of MatHax Client!");
        }
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void onRender(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo info) {
        prevWidth = 0;
        textRenderer.drawWithShadow(matrices, text1, width - fullLength - 3, 3, RED);
        prevWidth += text1Length;
        textRenderer.drawWithShadow(matrices, text2, width - fullLength + prevWidth - 3, 3, WHITE);
        prevWidth += text2Length2;
        textRenderer.drawWithShadow(matrices, text3, width - fullLength + prevWidth - 3, 3, GRAY);
        prevWidth += text3Length3;
        textRenderer.drawWithShadow(matrices, text4, width - fullLength + prevWidth - 3, 3, WHITE);
        prevWidth += text4Length4;
        textRenderer.drawWithShadow(matrices, text5, width - fullLength + prevWidth - 3, 3, WHITE);
        prevWidth += text5Length5;
        textRenderer.drawWithShadow(matrices, text6, width - fullLength + prevWidth - 3, 3, WHITE);
        prevWidth += text6Length6;
        textRenderer.drawWithShadow(matrices, text7, width - fullLength + prevWidth - 3, 3, GRAY);
        prevWidth += text7Length7;
        textRenderer.drawWithShadow(matrices, text8, width - fullLength + prevWidth - 3, 3, WHITE);
        prevWidth += text8Length8;
        textRenderer.drawWithShadow(matrices, text9, width - fullLength + prevWidth - 3, 3, WHITE);
        prevWidth += text9Length9;
        textRenderer.drawWithShadow(matrices, text10, width - fullLength + prevWidth - 3, 3, WHITE);
        prevWidth += text10Length10;
        textRenderer.drawWithShadow(matrices, text11, width - fullLength + prevWidth - 3, 3, GRAY);
    }
}
