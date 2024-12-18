import java.io.*;
import java.util.Random;

public class Third {
    static String[] word = {"book", "pen", "ruler", "note", "laptop", "window"};

    public static void main(String[] args) {
        String filePath = "a.txt";
        generate(filePath, 1000);
    }

    private static String random() {
        Random rand = new Random();
        return word[rand.nextInt(word.length)];
    }

    private static void generate(String filePath, int count) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {
            for (int i = 0; i < count; i++) {
                bufferedWriter.write(random());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void remove(String filePath, String word) {
        File inputFile = new File(filePath);
        File tempFile = new File("temp.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().equals(word)) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!inputFile.delete()) {
            System.out.println("Could not delete the original file");
        }
        if (!tempFile.renameTo(inputFile)) {
            System.out.println("Could not rename the temp file");
        }
    }
}

