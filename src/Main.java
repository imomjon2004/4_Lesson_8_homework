import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        final int RANGE = 1_000_000;
        final int THREAD_COUNT = 5;

        // Oddiy usulda hisoblash
        long startTime1 = System.currentTimeMillis();
        long simpleSum = calculateSumSimple(1, RANGE);
        long endTime1 = System.currentTimeMillis();
        System.out.println("Oddiy usuldagi yig'indi: " + simpleSum);
        System.out.println("Oddiy usul ishlash vaqti: " + (endTime1 - startTime1) + " ms\n");

        // ExecutorService va Future yordamida hisoblash
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        List<Future<Long>> futures = new ArrayList<>();

        int rangePerThread = RANGE / THREAD_COUNT;
        long startTime2 = System.currentTimeMillis();

        for (int i = 0; i < THREAD_COUNT; i++) {
            int start = i * rangePerThread + 1;
            int end = (i == THREAD_COUNT - 1) ? RANGE : (start + rangePerThread - 1);
            futures.add(executor.submit(new SumTask(start, end)));
        }

        long totalSum = 0;
        for (Future<Long> future : futures) {
            totalSum += future.get(); // Har bir threaddan natijalarni yig'ish
        }

        long endTime2 = System.currentTimeMillis();
        executor.shutdown();

        System.out.println("Threadlar orqali hisoblangan yig'indi: " + totalSum);
        System.out.println("Threadlar ishlash vaqti: " + (endTime2 - startTime2) + " ms");

        // Vaqtlar farqi
        System.out.println("Tezlik farqi: " + ((endTime1 - startTime1) - (endTime2 - startTime2)) + " ms");
    }

    // Oddiy usulda hisoblash
    private static long calculateSumSimple(int start, int end) {
        long sum = 0;
        for (int i = start; i <= end; i++) {
            sum += i;
        }
        return sum;
    }

    // Thread orqali hisoblash vazifasi
    static class SumTask implements Callable<Long> {
        private final int start;
        private final int end;

        public SumTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public Long call() {
            long sum = 0;
            for (int i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        }
    }
}