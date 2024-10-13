package pr1.task3;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    public static void main(String[] args) {
        BlockingQueue<File> queue = new ArrayBlockingQueue<>(5);

        FileGenerator generator = new FileGenerator(queue);
        Thread generatorThread = new Thread(generator);
        generatorThread.start();

        FileHandler xmlHandler = new FileHandler(queue, "XML");
        FileHandler jsonHandler = new FileHandler(queue, "JSON");
        FileHandler xlsHandler = new FileHandler(queue, "XLS");

        Thread xmlHandlerThread = new Thread(xmlHandler);
        Thread jsonHandlerThread = new Thread(jsonHandler);
        Thread xlsHandlerThread = new Thread(xlsHandler);

        xmlHandlerThread.start();
        jsonHandlerThread.start();
        xlsHandlerThread.start();
    }
}
