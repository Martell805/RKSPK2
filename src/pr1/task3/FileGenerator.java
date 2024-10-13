package pr1.task3;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class FileGenerator implements Runnable {
    private final BlockingQueue<File> queue;
    private final String[] fileTypes = {"XML", "JSON", "XLS"};
    private final Random random = new Random();

    public FileGenerator(BlockingQueue<File> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String type = fileTypes[random.nextInt(fileTypes.length)];
                int size = random.nextInt(10, 101);
                File file = new File(type, size);
                Thread.sleep(random.nextInt(100, 1001));
                queue.put(file);
                System.out.println("Сгенерирован: " + file);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
