import java.io.File;
import java.io.IOException;
import java.util.*;

public class Legion923 {
    public static Map<Long, Long> lib = new HashMap<>();

    public static Long countScouts(long n) {
        if (n < 3) {
            return 0L;
        } else if (n == 3) {
            return 1L;
        }
        if (lib.get(n) == null) {
            lib.put(n, countScouts(n / 2L) + countScouts(n - n / 2L));
        }
        return lib.get(n);
    }

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(new File("input.txt"));
        System.out.println(countScouts(in.nextLong()));
    }
}