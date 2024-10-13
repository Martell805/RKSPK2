package pr2.task3;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ChecksumCalculator {
    public static int calculateChecksum(String fileName) throws IOException {
        Path filePath = Paths.get(fileName);
        byte[] fileData = Files.readAllBytes(filePath);
        ByteBuffer buffer = ByteBuffer.wrap(fileData);

        int checksum = 0;
        while (buffer.remaining() > 1) {
            int word = Short.toUnsignedInt(buffer.getShort());
            checksum += word;
            checksum = (checksum & 0xFFFF) + (checksum >> 16);
        }

        if (buffer.remaining() > 0) {
            int lastByte = Byte.toUnsignedInt(buffer.get());
            checksum += lastByte;
            checksum = (checksum & 0xFFFF) + (checksum >> 16);
        }

        return ~checksum & 0xFFFF;
    }

    public static void main(String[] args) {
        String fileName = "test_100MB.txt";
        try {
            int checksum = calculateChecksum(fileName);
            System.out.printf("16-битная контрольная сумма файла %s: %04X%n", fileName, checksum);
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }
}
