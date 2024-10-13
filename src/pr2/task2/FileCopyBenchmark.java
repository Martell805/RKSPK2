package pr2.task2;

import java.io.IOException;

public class FileCopyBenchmark {
    public static void main(String[] args) {
        String fileName = "test_100MB.txt";
        int sizeInMB = 100;
        TestFileCreator.createTestFile(fileName, sizeInMB);

        benchmarkCopy("streams_copy.txt", fileName, FileCopyUsingStreams::copyFile);
        benchmarkCopy("channel_copy.txt", fileName, FileCopyUsingChannel::copyFile);
        benchmarkCopy("commons_copy.txt", fileName, FileCopyUsingApacheCommons::copyFile);
        benchmarkCopy("files_copy.txt", fileName, FileCopyUsingFiles::copyFile);
    }

    private static void benchmarkCopy(String destination, String source, CopyMethod copyMethod) {
        try {
            System.gc();
            long startTime = System.currentTimeMillis();
            long startMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

            copyMethod.copy(source, destination);

            long endTime = System.currentTimeMillis();
            long endMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            System.out.printf("Метод: %s, Время: %d мс, Использованная память: %d байт%n",
                    destination, (endTime - startTime), (endMemory - startMemory));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FunctionalInterface
    interface CopyMethod {
        void copy(String source, String destination) throws IOException;
    }
}
