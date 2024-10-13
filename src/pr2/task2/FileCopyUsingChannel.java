package pr2.task2;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FileCopyUsingChannel {
    public static void copyFile(String source, String destination) throws IOException {
        try (FileChannel sourceChannel = new FileInputStream(source).getChannel();
             FileChannel destChannel = new FileOutputStream(destination).getChannel()) {
            sourceChannel.transferTo(0, sourceChannel.size(), destChannel);
        }
    }
}
