package matejko06.mathax.utils.network;

public class OnlinePlayers {
    private static long lastPingTime;

    public static void update() {
        long time = System.currentTimeMillis();

        if (time - lastPingTime > 5 * 60 * 1000) {
            MatHaxExecutor.execute(() -> HttpUtils.post("https://mathaxclient.xyz/API/online/ping"));

            lastPingTime = time;
        }
    }

    public static void leave() {
        MatHaxExecutor.execute(() -> HttpUtils.post("https://mathaxclient.xyz/API/online/leave"));
    }
}
