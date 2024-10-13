package pr1.task1;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinMin extends RecursiveTask<Integer> implements Min {
    private static final int MIN_COMPUTE_LENGTH = 100;

    private List<Integer> array;
    private int start, end;

    public ForkJoinMin() {}

    public ForkJoinMin(final List<Integer> array, final int start, final int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    public String getName() {
        return "ForkJoin";
    }

    @Override
    protected Integer compute() {
        if (end - start <= MIN_COMPUTE_LENGTH) {
            int min = Integer.MAX_VALUE;

            for (int i = start; i < end; i++) {
                min = Math.min(array.get(i), min);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            return min;
        }

        int mid = (start + end) / 2;
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        final ForkJoinMin leftTask = new ForkJoinMin(array, start, mid);
        final ForkJoinMin rightTask = new ForkJoinMin(array, mid, end);
        leftTask.fork();
        final int rightResult = rightTask.compute();
        final int leftResult = leftTask.join();

        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return Math.min(leftResult, rightResult);
    }

    public Integer findMin(final List<Integer> array) {
        try (final ForkJoinPool pool = new ForkJoinPool()) {
            return pool.invoke(new ForkJoinMin(array, 0, array.size()));
        }
    }
}
