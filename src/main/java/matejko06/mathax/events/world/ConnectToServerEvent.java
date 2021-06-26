package matejko06.mathax.events.world;

public class ConnectToServerEvent {
    private static final ConnectToServerEvent INSTANCE = new ConnectToServerEvent();

    public static ConnectToServerEvent get() {
        return INSTANCE;
    }
}
