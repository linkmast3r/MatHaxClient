package matejko06.mathax.mixin;

import matejko06.mathax.MatHaxClient;
import matejko06.mathax.gui.GuiThemes;
import matejko06.mathax.gui.screens.NewUpdateScreen;
import matejko06.mathax.systems.config.Config;
import matejko06.mathax.systems.modules.Modules;
import matejko06.mathax.systems.modules.misc.NameProtect;
import matejko06.mathax.utils.Utils;
import matejko06.mathax.utils.misc.Version;
import matejko06.mathax.utils.network.Http;
import matejko06.mathax.utils.network.MatHaxExecutor;
import matejko06.mathax.utils.render.color.Color;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {

    @Shadow
    @Final
    private boolean doBackgroundFade;
    private static final String VERSION_URL = "http://api.mathaxclient.xyz/Version/1-17";

    private final int RED = Color.fromRGBA(255, 0, 0, 255);
    private final int WHITE = Color.fromRGBA(255, 255, 255, 255);
    private final int GRAY = Color.fromRGBA(175, 175, 175, 255);

    private String textLeftUp;
    private int textLeftUpLength;

    private String textRightUp1;
    private int textRightUp1Length;

    private String textRightUp2;
    private int textRightUp2Length;

    private String textRightUp3;

    private int fullLengthRightUp;
    private int prevWidthRightUp;

    private String textRightDown1;
    private int textRightDown1Length;

    private String textRightDown2;
    private int textRightDown2Length;

    private String textRightDown3;
    private int textRightDown3Length;

    private String textRightDown4;
    private int textRightDown4Length;

    private String textRightDown5;
    private int textRightDown5Length;

    private String textRightDown6;
    private int textRightDown6Length;

    private String textRightDown7;

    private int fullLengthRightDown;
    private int prevWidthRightDown;

    public TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void onInit(CallbackInfo info) {

        textLeftUp = "Logged in as ";

        textRightUp1 = "MatHax Client";
        textRightUp2 = " ";
        textRightUp3 = "v" + MatHaxClient.clientversion;

        textRightDown1 = "By";
        textRightDown2 = " ";
        textRightDown3 = "Matejko06";
        textRightDown4 = " ";
        textRightDown5 = "&";
        textRightDown6 = " ";
        textRightDown7 = "GeekieCoder";

        textLeftUpLength = textRenderer.getWidth(textLeftUp);

        textRightUp1Length = textRenderer.getWidth(textRightUp1);
        textRightUp2Length = textRenderer.getWidth(textRightUp2);
        int textRightUp1Length = textRenderer.getWidth(textRightUp1);
        int textRightUp2Length = textRenderer.getWidth(textRightUp2);
        int textRightUp3Length = textRenderer.getWidth(textRightUp3);

        textRightDown1Length = textRenderer.getWidth(textRightDown1);
        textRightDown2Length = textRenderer.getWidth(textRightDown2);
        textRightDown3Length = textRenderer.getWidth(textRightDown3);
        textRightDown4Length = textRenderer.getWidth(textRightDown4);
        textRightDown5Length = textRenderer.getWidth(textRightDown5);
        textRightDown6Length = textRenderer.getWidth(textRightDown6);
        int textRightDown1Length = textRenderer.getWidth(textRightDown1);
        int textRightDown2Length = textRenderer.getWidth(textRightDown2);
        int textRightDown3Length = textRenderer.getWidth(textRightDown3);
        int textRightDown4Length = textRenderer.getWidth(textRightDown4);
        int textRightDown5Length = textRenderer.getWidth(textRightDown5);
        int textRightDown6Length = textRenderer.getWidth(textRightDown6);
        int textRightDown7Length = textRenderer.getWidth(textRightDown7);

        fullLengthRightUp =  textRightUp1Length + textRightUp2Length + textRightUp3Length;
        prevWidthRightUp = 0;
        fullLengthRightDown = textRightDown1Length + textRightDown2Length + textRightDown3Length + textRightDown4Length + textRightDown5Length + textRightDown6Length + textRightDown7Length;
        prevWidthRightDown = 0;
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/TitleScreen;drawStringWithShadow(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V", ordinal = 0))
    private void onRenderTitleScreen(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo info) {
        if (Utils.firstTimeTitleScreen) {
            Utils.firstTimeTitleScreen = false;
            MatHaxClient.LOG.info("[MatHax] Checking latest version of MatHax Client...");

            /*MatHaxExecutor.execute(() -> {
                String res = Http.get("http://api.mathaxclient.xyz/Version/1-17").sendString();
                if (res == null) return;

                Version latestVer = new Version(res);

                if (latestVer.isHigherThan(Config.get().DevBuildFullReleaseNotify)) {
                    Utils.mc.openScreen(new NewUpdateScreen(GuiThemes.get(), latestVer));
                    //MatHaxClient.LOG.info("[MatHax] There is a new version of MatHax Client, v" + latestVer + "! You are using v" + MatHaxClient.clientversion + "!");
                    MatHaxClient.LOG.info("[MatHax] There is a new Dev Build of MatHax Client! You are using v" + MatHaxClient.clientversion + "!");
                } else {
                    MatHaxClient.LOG.info("[MatHax] You are using the latest Dev Build of MatHax Client, v" + MatHaxClient.clientversion + "!");
                }
            });*/
            MatHaxClient.LOG.info("[MatHax] Failed to check for latest update of MatHax Client. You can check for yourself on https://mathaxclient.xyz/. You are currently using v" + MatHaxClient.clientversion);
        }
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void onRender(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo info) {

        textRenderer.drawWithShadow(matrices, textLeftUp, 3, 3, WHITE);
        textRenderer.drawWithShadow(matrices, Modules.get().get(NameProtect.class).getName(client.getSession().getUsername()), 3 + textLeftUpLength, 3, GRAY);

        prevWidthRightUp = 0;
        textRenderer.drawWithShadow(matrices, textRightUp1, width - fullLengthRightUp - 3, 3, RED);
        prevWidthRightUp += textRightUp1Length;
        textRenderer.drawWithShadow(matrices, textRightUp2, width - fullLengthRightUp + prevWidthRightUp - 3, 3, WHITE);
        prevWidthRightUp += textRightUp2Length;
        textRenderer.drawWithShadow(matrices, textRightUp3, width - fullLengthRightUp + prevWidthRightUp - 3, 3, GRAY);

        prevWidthRightDown = 0;
        textRenderer.drawWithShadow(matrices, textRightDown1, width - fullLengthRightDown - 3, 15, WHITE);
        prevWidthRightDown += textRightDown1Length;
        textRenderer.drawWithShadow(matrices, textRightDown2, width - fullLengthRightDown + prevWidthRightDown - 3, 15, WHITE);
        prevWidthRightDown += textRightDown2Length;
        textRenderer.drawWithShadow(matrices, textRightDown3, width - fullLengthRightDown + prevWidthRightDown - 3, 15, GRAY);
        prevWidthRightDown += textRightDown3Length;
        textRenderer.drawWithShadow(matrices, textRightDown4, width - fullLengthRightDown + prevWidthRightDown - 3, 15, WHITE);
        prevWidthRightDown += textRightDown4Length;
        textRenderer.drawWithShadow(matrices, textRightDown5, width - fullLengthRightDown + prevWidthRightDown - 3, 15, WHITE);
        prevWidthRightDown += textRightDown5Length;
        textRenderer.drawWithShadow(matrices, textRightDown6, width - fullLengthRightDown + prevWidthRightDown - 3, 15, WHITE);
        prevWidthRightDown += textRightDown6Length;
        textRenderer.drawWithShadow(matrices, textRightDown7, width - fullLengthRightDown + prevWidthRightDown - 3, 15, GRAY);

    }
}
