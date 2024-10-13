package pr3.task2;

import io.reactivex.rxjava3.core.Observable;

import java.util.Random;

public class CombineStreams {
    public static void main(String[] args) {
        Random random = new Random();

        // Поток случайных букв
        Observable<String> randomLetters = Observable.range(0, 1000)
                .map(i -> String.valueOf((char) ('A' + random.nextInt(26)))); // Генерируем случайные буквы от A до Z

        // Поток случайных цифр
        Observable<String> randomDigits = Observable.range(0, 1000)
                .map(i -> String.valueOf(random.nextInt(10))); // Генерируем случайные цифры от 0 до 9

        // Объединяем потоки
        Observable<String> combinedStream = Observable.zip(randomLetters, randomDigits, (letter, digit) -> letter + digit);

        // Подписываемся и выводим результат на консоль
        combinedStream.subscribe(combination -> System.out.println("Объединенный элемент: " + combination));
    }
}
