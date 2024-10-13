package pr2.task2;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class FileCopyUsingApacheCommons {
    public static void copyFile(String source, String destination) throws IOException {
        FileUtils.copyFile(new File(source), new File(destination));
    }
}
