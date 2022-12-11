import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static final AtomicInteger aI1 = new AtomicInteger();
    public static final AtomicInteger aI2 = new AtomicInteger();
    public static final AtomicInteger aI3 = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {


        wordСount(3, aI1).join();
        wordСount(4, aI2).join();
        wordСount(5, aI3).join();
        System.out.println("Красивых слов с длиной - 3: " + aI1 + " шт.");
        System.out.println("Красивых слов с длиной - 4: " + aI2 + " шт.");
        System.out.println("Красивых слов с длиной - 5: " + aI3 + " шт.");

    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(letters.charAt(random.nextInt(letters.length())));
        }
        return stringBuilder.toString();
    }

    public static Thread wordСount(int wordLength, AtomicInteger atomicI) {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
        Thread threads = new Thread(() -> {
            for (String s : texts) {
                if (s.length() == wordLength) {
                    if (s.equals(new StringBuilder(s).reverse().toString())) {
                        atomicI.incrementAndGet();
                    } else if (s.chars().allMatch(c -> c == s.charAt(0))) {
                        atomicI.incrementAndGet();
                    } else {
                        char[] d = s.toCharArray();
                        Arrays.sort(d);
                        atomicI.incrementAndGet();
                    }
                }

            }
        });
        threads.start();
        return threads;
    }
}