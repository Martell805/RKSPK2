package pr1.task2;

import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SquareCalculator {
    public static void main(String[] args) {
        final ExecutorService squareExecutor = Executors.newFixedThreadPool(4);
        final ExecutorService printExecutor = Executors.newFixedThreadPool(4);

        final Scanner scanner = new Scanner(System.in);

        System.out.println("Введите число:");
        while (true) {
            String input = scanner.nextLine();
            if (input.isEmpty()) {
                break;
            }

            try {
                int number = Integer.parseInt(input);

                printExecutor.submit(new PrintTask(
                        number, squareExecutor.submit(new SquareTask(number))
                ));

                System.out.printf("Запрос на число %d принят\n", number);

            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод");
            }
        }

        squareExecutor.shutdown();
        printExecutor.shutdown();
    }

    static class SquareTask implements Callable<Integer> {
        private final Integer number;

        public SquareTask(final Integer number) {
            this.number = number;
        }

        @Override
        public Integer call() throws Exception {
            Thread.sleep(number * 1000);

            return number * number;
        }
    }

    static class PrintTask implements Callable<Void> {
        private final Future<Integer> number;
        private final Integer base;

        public PrintTask(final Integer base, final Future<Integer> number) {
            this.base = base;
            this.number = number;
        }

        @Override
        public Void call() throws Exception {
            System.out.printf("Ответ для числа %d: %d\n", base, number.get());
            return null;
        }
    }
}