package pr3.task1;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class SensorSystem {
    public static void main(String[] args) {
        Observable<Integer> temperatureSensor = Observable.interval(1, TimeUnit.SECONDS)
                .map(tick -> 15 + new Random().nextInt(16))
                .doOnNext(temp -> System.out.println("Температура: " + temp + "°C"));

        Observable<Integer> co2Sensor = Observable.interval(1, TimeUnit.SECONDS)
                .map(tick -> 30 + new Random().nextInt(71))
                .doOnNext(co2 -> System.out.println("CO2: " + co2 + " ppm"));

        final int TEMPERATURE_THRESHOLD = 25;
        final int CO2_THRESHOLD = 70;

        Disposable alarmSystem = Observable.combineLatest(
                        temperatureSensor,
                        co2Sensor,
                        (temperature, co2) -> {
                            boolean isTempHigh = temperature > TEMPERATURE_THRESHOLD;
                            boolean isCo2High = co2 > CO2_THRESHOLD;

                            if (isTempHigh && isCo2High) {
                                return "ALARM!!! Температура и CO2 превышают норму!";
                            } else if (isTempHigh) {
                                return "ВНИМАНИЕ! Температура превышает норму!";
                            } else if (isCo2High) {
                                return "ВНИМАНИЕ! CO2 превышает норму!";
                            } else {
                                return "Показатели в норме.";
                            }
                        }
                )
                .subscribe(alarmMessage -> System.out.println("Сигнализация: " + alarmMessage));

        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            alarmSystem.dispose();
        }
    }
}
