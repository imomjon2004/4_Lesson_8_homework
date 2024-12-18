import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class First {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        long sum = 0;
        for (int i = 1; i <= 1_000_000; i++) {
            sum += i;
        }
        System.out.println("sum: " + sum);
        long end = System.currentTimeMillis();
        System.out.println(end - start + "ms");

        long start1 = System.currentTimeMillis();
        sum();
        long end1 = System.currentTimeMillis();
        System.out.println(end1 - start1 + "ms");

    }

    public static void sum() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        long totalSum = 0;

        for (int i = 0; i < 5; i++) {
            int rangeStart = 1 + i * 200_000;
            int rangeEnd = rangeStart + 200_000 - 1;

            Future<Long> future = executorService.submit(() -> {
                long sum = 0;
                for (int j = rangeStart; j <= rangeEnd; j++) {
                    sum += j;
                }
                return sum;
            });

            totalSum += future.get();
        }

        executorService.shutdown();
        System.out.println("sum: " + totalSum);
    }

}