package pr2.task2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileCopyUsingFiles {
    public static void copyFile(String source, String destination) throws IOException {
        Path sourcePath = Paths.get(source);
        Path destinationPath = Paths.get(destination);
        Files.copy(sourcePath, destinationPath);
    }
}
