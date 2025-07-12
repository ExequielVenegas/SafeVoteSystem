import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    public static List<Integer> readPrimesFromCSV(String fileName) {
        List<Integer> primes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    primes.add(Integer.parseInt(line.trim()));
                } catch (NumberFormatException ignored) {}
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return primes;
    }

    public static void writeEncryptedMessage(String fileName, String message, int primeCode) {
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.write("Mensaje: " + message + ", Código Primo: " + primeCode + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}