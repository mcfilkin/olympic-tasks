import java.io.File;
import java.io.IOException;
import java.util.*;

public class Sum2 {
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(new File("input.txt"));
        int n = in.nextInt();
        int total = 0;
        if (n > 0) {
            for (int i = 1; i <= n; i++) {
                total += i;
            }
        } else {
            for (int i = 1; i >= n; i--) {
                total += i;
            }
        }
        System.out.println(total);
    }
}