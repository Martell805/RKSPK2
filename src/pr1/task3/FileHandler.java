package pr1.task3;

import java.util.concurrent.BlockingQueue;

public class FileHandler implements Runnable {
    private final BlockingQueue<File> queue;
    private final String fileType;

    public FileHandler(BlockingQueue<File> queue, String fileType) {
        this.queue = queue;
        this.fileType = fileType;
    }

    @Override
    public void run() {
        try {
            while (true) {
                File file = queue.take();

                if (!file.getType().equalsIgnoreCase(fileType)) {
                    queue.put(file);
                    System.out.printf("Пропущен %s: %s\n", fileType, file);
                    continue;
                }

                Thread.sleep(file.getSize() * 7);
                System.out.printf("Обработан %s: %s\n", fileType, file);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
