import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static AtomicInteger colvo3 = new AtomicInteger();
    public static AtomicInteger colvo4 = new AtomicInteger();
    public static AtomicInteger colvo5 = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {

        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread palindroms = new Thread(() -> {
            for (String text : texts) {
                if (palindromWord(text) && !identicallyLetters(text)) {
                    all(text.length());
                }
            }
        });
        palindroms.start();

        Thread identically = new Thread(() -> {
            for (String text : texts) {
                if (identicallyLetters(text)) {
                    all(text.length());
                }
            }
        });
        identically.start();

        Thread increasing = new Thread(() -> {
            for (String text : texts) {
                if (increasingLetters(text) && !identicallyLetters(text)) {
                    all(text.length());
                }
            }
        });
        increasing.start();

        palindroms.join();
        identically.join();
        increasing.join();
        
        System.out.println("Красивых слов с длиной 3: " + colvo3 + " шт");
        System.out.println("Красивых слов с длиной 4: " + colvo4 + " шт");
        System.out.println("Красивых слов с длиной 5: " + colvo5 + " шт");
    }

    public static boolean palindromWord(String text) {
        if (text.equals(new StringBuilder(text).reverse().toString())) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean identicallyLetters(String text) {
        for (int i = 0; i < text.length() - 1; i++) {
            if (text.charAt(i) != text.charAt(i + 1)) {
                return false;
            }
        }
        return true;
    }

    public static boolean increasingLetters(String text) {
        for (int i = 0; i < text.length() - 1; i++) {
            if (text.charAt(i) > text.charAt(i + 1)) {
                return false;
            }
        }
        return true;
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static void all(int textLength) {
        if (textLength == 3) {
            colvo3.getAndIncrement();
        } else if ((textLength == 4)) {
            colvo4.getAndIncrement();
        } else if ((textLength == 5)) {
            colvo5.getAndIncrement();
        }
    }
}