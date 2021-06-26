package matejko06.mathax.utils.network;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MatHaxExecutor {
    private static ExecutorService executor;

    public static void init() {
        executor = Executors.newSingleThreadExecutor();
    }

    public static void execute(Runnable task) {
        executor.execute(task);
    }
}
