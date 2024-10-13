package pr1.task1;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Main {
    public static void time(final Min method, final List<Integer> array) {
        System.out.println(method.getName() + ":");
        long startTime = System.currentTimeMillis();
        System.out.println("Минимум: " + method.findMin(array));
        System.out.println("Время работы: " + (System.currentTimeMillis() - startTime) / 1000.0 + " с");
    }

    public static void main(String[] args) {
        final List<Integer> array = IntStream.generate(() -> new Random().nextInt(10000))
                        .limit(10000)
                        .boxed()
                        .toList();

        time(new SequentialMin(), array);
        time(new ConcurrentMin(), array);
        time(new ForkJoinMin(), array);
    }
}
