package pr3.task2;

import io.reactivex.rxjava3.core.Observable;

import java.util.Random;

public class SquareNumbers {
    public static void main(String[] args) {
        Random random = new Random();

        Observable<Integer> randomNumbers = Observable.range(0, 1000)
                .map(i -> random.nextInt(1001)); // Генерируем случайные числа от 0 до 1000

        Observable<Integer> squaredNumbers = randomNumbers
                .map(number -> number * number); // Преобразуем числа в их квадраты

        // Подписываемся и выводим квадраты на консоль
        squaredNumbers.subscribe(square -> System.out.println("Квадрат числа: " + square));
    }
}