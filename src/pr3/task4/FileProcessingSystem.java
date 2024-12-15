package pr3.task4;

import io.reactivex.rxjava3.core.Observable;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

record File(String fileType, int size) {
}

class FileGenerator {
    private final Random random = new Random();

    public File generateFile() {
        try {
            TimeUnit.MILLISECONDS.sleep(100 + random.nextInt(901));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        String[] fileTypes = {"XML", "JSON", "XLS"};
        String fileType = fileTypes[random.nextInt(fileTypes.length)];
        int size = 10 + random.nextInt(91);

        return new File(fileType, size);
    }
}

class FileProcessor {
    private final String fileType;

    public FileProcessor(String fileType) {
        this.fileType = fileType;
    }

    public void processFile(File file) {
        if (file.fileType().equals(fileType)) {
            int processingTime = file.size() * 7;
            try {
                TimeUnit.MILLISECONDS.sleep(processingTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Обработан файл типа: " + file.fileType() + " размером: " + file.size());
        } else {
            System.out.println("Не могу обработать файл типа: " + file.fileType());
        }
    }
}

public class FileProcessingSystem {
    private static final int QUEUE_CAPACITY = 5;
    private static final BlockingQueue<File> fileQueue = new LinkedBlockingQueue<>(QUEUE_CAPACITY);

    public static void main(String[] args) {
        FileGenerator fileGenerator = new FileGenerator();

        Observable.interval(0, 1, TimeUnit.SECONDS)
                .subscribe(tick -> {
                    File file = fileGenerator.generateFile();
                    try {
                        fileQueue.put(file);
                        System.out.println("Сгенерирован файл: " + file.fileType() + " размером: " + file.size());
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });

        for (String fileType : new String[]{"XML", "JSON", "XLS"}) {
            new Thread(() -> {
                FileProcessor fileProcessor = new FileProcessor(fileType);
                while (true) {
                    try {
                        File file = fileQueue.take();
                        fileProcessor.processFile(file);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }).start();
        }

        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.exit(0);
    }
}
