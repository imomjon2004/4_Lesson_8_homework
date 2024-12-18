import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class Third {
    static String[] word = {"book", "pen", "ruler", "note", "laptop", "window"};
    static String filePath = "a.txt";

    public static void main(String[] args) {
        while (true) {
            System.out.print("""
                    1.Write
                    2.Remove pen, remove book
                    Choose -> 
                    """);
            switch (new Scanner(System.in).nextInt()) {
                case 1 -> generate(filePath, 1000);
                case 2 -> {
                    Thread removePen = new Thread(() -> {
                        remove(filePath, "pen");
                    });
                    Thread removeBook = new Thread(() -> {
                        remove(filePath, "book");
                    });
                    removePen.start();
                    removeBook.start();
                }
            }
        }
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
    }
}

