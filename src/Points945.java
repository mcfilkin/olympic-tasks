import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Points945 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File("input.txt"));
        int students = in.nextInt();
        int request = in.nextInt();
        List<Long> s = new ArrayList<>();
        List<Long> r = new ArrayList<>();
        for (int i = 0; i < students; i++) {
            s.add(in.nextLong());
        }
        for (int j = 0; j < request; j++) {
            r.add(in.nextLong());
        }
        StringBuilder result = new StringBuilder();
        for (Long el : r) {
            if (Collections.binarySearch(s, el) >= 0) {
                result.append("YES ");
            } else {
                result.append("NO ");
            }
        }
        System.out.println(result.toString());
    }
}