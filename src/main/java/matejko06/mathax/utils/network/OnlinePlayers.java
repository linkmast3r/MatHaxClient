package matejko06.mathax.utils.network;

public class OnlinePlayers {
    private static long lastPingTime;

    public static void update() {
        long time = System.currentTimeMillis();

        if (time - lastPingTime > 5 * 60 * 1000) {
            MatHaxExecutor.execute(() -> Http.post("http://api.mathaxclient.xyz/online/ping").send());

            lastPingTime = time;
        }
    }

    public static void leave() {
        MatHaxExecutor.execute(() -> Http.post("http://api.mathaxclient.xyz/online/leave").send());
    }
}
