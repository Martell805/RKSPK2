package pr2.task4;

import pr2.task3.ChecksumCalculator;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DirectoryWatcher {
    final static Map<String, String> fileContents = new HashMap<>();
    final static Map<String, Integer> fileCheckSums = new HashMap<>();

    public static void main(String[] args) throws IOException {
        Path directory = Paths.get("watched_directory");

        Files.list(directory).forEach(file -> updateFile(file));

        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            directory.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);

            System.out.println("Наблюдение за каталогом начато...");

            while (true) {
                WatchKey key;
                try {
                    key = watchService.take();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }

                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();
                    Path fileName = (Path) event.context();
                    Path fullPath = directory.resolve(fileName);

                    if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                        System.out.println("Создан файл: " + fileName);
                        updateFile(fullPath);
                    } else if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
                        System.out.println("Изменен файл: " + fileName);
                        printFileChanges(fullPath);
                        updateFile(fullPath);
                    } else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
                        System.out.println("Удален файл: " + fileName);
                        removeFile(fileName);
                    }
                }

                if (!key.reset()) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void updateFile(Path file) {
        final String filename = file.getFileName().toString();

        try {
            fileContents.put(filename, Files.readString(file));
            fileCheckSums.put(filename, ChecksumCalculator.calculateChecksum(file.toString()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void removeFile(Path file) {
        final String filename = file.getFileName().toString();

        System.out.printf("Количество символов: %d, контрольная сумма: %d\n", fileContents.get(filename).length(), fileCheckSums.get(filename));

        fileContents.remove(filename);
        fileCheckSums.remove(filename);
    }

    private static void printFileChanges(Path file) {
        final String filename = file.getFileName().toString();

        try {
            List<String> newLines = Files.readAllLines(file);
            List<String> originalLines = List.of(fileContents.get(filename).split("\r\n"));

            System.out.println("Изменения: ");
            int i = 0;

            for (; i < Math.min(newLines.size(), originalLines.size()); i++) {
                if (!newLines.get(i).equals(originalLines.get(i))) {
                    System.out.printf("%d. + | %s\n", i, newLines.get(i));
                    System.out.printf("%d. - | %s\n", i, originalLines.get(i));
                }
            }
            for (; i < newLines.size(); i++) {
                System.out.printf("%d. + | %s\n", i, newLines.get(i));
            }
            for (; i < originalLines.size(); i++) {
                System.out.printf("%d. - | %s\n", i, originalLines.get(i));
            }

        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }
}
