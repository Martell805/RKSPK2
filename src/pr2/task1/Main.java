package pr2.task1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String fileName = "example.txt";
        List<String> lines = List.of(
            "Первая строка текста.",
            "Вторая строка текста.",
            "Третья строка текста."
        );

        Path filePath = Paths.get(fileName);

        try {
            Files.write(filePath, lines);
            System.out.println("Файл успешно создан: " + filePath.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("Ошибка при создании файла: " + e.getMessage());
        }

        try {
            List<String> fileContent = Files.readAllLines(filePath);
            System.out.println("Содержимое файла:");
            for (String line : fileContent) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }
}
