package pr1.task1;

import java.util.List;

public class SequentialMin implements Min {
    public String getName() {
        return "Sequential";
    }

    public Integer findMin(final List<Integer> array) {
        int min = Integer.MAX_VALUE;
        for (final int value : array) {
            min = Math.min(value, min);
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return min;
    }
}