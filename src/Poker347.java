import java.io.File;
import java.io.IOException;
import java.util.*;

public class Poker347 {
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(new File("input.txt"));
        Map<Integer, Integer> hand = new HashMap<>();
        while (in.hasNext()) {
            int card = in.nextInt();
            if (hand.get(card) == null) {
                hand.put(card, 1);
            } else {
                hand.put(card, hand.get(card).intValue() + 1);
            }
        }
        if (hand.containsValue(5)) {
            System.out.println("Impossible");
        } else if (hand.containsValue(4)) {
            System.out.println("Four of a Kind");
        } else if (hand.containsValue(3)) {
            if (hand.containsValue(2)) {
                System.out.println("Full House");
            } else {
                System.out.println("Three of a Kind");
            }
        } else if (hand.containsValue(2)) {
            int count = 0;
            for (Map.Entry<Integer, Integer> entry : hand.entrySet()) {
                if (entry.getValue() == 2) {
                    count++;
                }
            }
            if (count == 2) {
                System.out.println("Two Pairs");
            } else {
                System.out.println("One Pair");
            }
        } else {
            List<Integer> cards = new ArrayList<>();
            for (Map.Entry<Integer, Integer> entry : hand.entrySet()) {
                cards.add(entry.getKey());
            }
            Collections.sort(cards);
            for (int i = 1; i <= 4; i++) {
                if (cards.get(i) != cards.get(i - 1) + 1) {
                    System.out.println("Nothing");
                    return;
                }
            }
            System.out.println("Straight");
        }
    }
}