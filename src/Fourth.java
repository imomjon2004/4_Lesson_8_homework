import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Fourth {
    public static void main(String[] args) {
        String[] file = {"a1.txt", "a2.txt", "a3.txt"};

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.submit(() -> calculate(0, 1_000_000), file[0]);
        executorService.submit(() -> calculate(1_000_000, 2_000_000), file[1]);
        executorService.submit(() -> calculate(2_000_000, 3_000_000), file[2]);
        executorService.shutdown();

    }

    private static void calculate(int i, int i1) {

    }
}
