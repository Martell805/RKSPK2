package pr2.task2;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class TestFileCreator {
    public static void main(String[] args) {
        String fileName = "test_100MB.txt";
        int sizeInMB = 100;
        createTestFile(fileName, sizeInMB);
    }

    public static void createTestFile(String fileName, int sizeInMB) {
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            byte[] buffer = new byte[1024];
            Random random = new Random();
            for (int i = 0; i < sizeInMB * 1024; i++) {
                random.nextBytes(buffer);
                fos.write(buffer);
            }
            System.out.println("Тестовый файл создан: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
