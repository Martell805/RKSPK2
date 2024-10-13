package pr1.task1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ConcurrentMin implements Min {
    private static final int CHUNK_AMOUNT = 4;

    public String getName() {
        return "Concurrent";
    }

    public Integer findMin(final List<Integer> array) {
        try (final ExecutorService executor = Executors.newFixedThreadPool(4)) {
            int chunkSize = array.size() / CHUNK_AMOUNT;
            final List<Future<Integer>> futures = new ArrayList<>();

            for (int i = 0; i < CHUNK_AMOUNT; i++) {
                int start = i * chunkSize;
                int end = (i == CHUNK_AMOUNT - 1) ? array.size() : (i + 1) * chunkSize;

                futures.add(executor.submit(() -> {
                    int min = Integer.MAX_VALUE;
                    for (int j = start; j < end; j++) {
                        if (array.get(j) < min) {
                            min = array.get(j);
                        }
                        Thread.sleep(1);
                    }
                    return min;
                }));
            }

            int min = Integer.MAX_VALUE;
            for (final Future<Integer> future : futures) {
                try {
                    min = Math.min(min, future.get());
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }

            return min;
        }
    }
}