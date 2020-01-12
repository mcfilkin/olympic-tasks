import java.io.*;
import java.util.Scanner;

public class Freebie566 {
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(new File("input.txt"));
        PrintWriter out = new PrintWriter(System.out);

        int caps1 = in.nextInt();
        int caps2 = in.nextInt();
        int caps3 = in.nextInt();
        int price = in.nextInt();
        int result = 0;

        while (true) {
            int buy = price;
            if (buy % 2 == 0) {
                while (caps3 > 0 && buy >= 3) {
                    caps3--;
                    buy -= 3;
                }
                while (caps2 > 0 && buy >= 2) {
                    caps2--;
                    buy -= 2;
                }
                while (caps1 > 0 && buy >= 1) {
                    caps1--;
                    buy -= 1;
                }
            } else {
                if (caps3 > 0 && buy >= 3) {
                    caps3--;
                    buy -= 3;
                }
                if (caps2 > 0 && buy >= 2) {
                    caps2--;
                    buy -= 2;
                }
                while (caps1 > 0 && buy >= 1) {
                    caps1--;
                    buy -= 1;
                }
            }
            if (buy > 0) {
                while (caps1 > 0 && buy > 0) {
                    caps1--;
                    buy -= 1;
                }
                if (buy <= 0) {
                    result++;
                    continue;
                }
                while (caps2 > 0 && buy > 0) {
                    caps2--;
                    buy -= 2;
                }
                if (buy <= 0) {
                    result++;
                    continue;
                }
                while (caps3 > 0 && buy > 0) {
                    caps3--;
                    buy -= 3;
                }
                if (buy <= 0) {
                    result++;
                    continue;
                }
                break;
            } else {
                result++;
            }
        }
        out.println(result);
        out.close();
    }
}