import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.List;

public class ListExercises {

    /**
     * Returns the total sum in a list of integers
     */
    public static int sum(List<Integer> L) {
        int total = 0;
        for (int number : L) {
            total += number;
        }
        return total;
    }

    /**
     * Returns a list containing the even numbers of the given list
     */
    public static List<Integer> evens(List<Integer> L) {
        // TODO: Fill in this function.
        List<Integer> ll = new ArrayList<>();
        for (int ele : L) {

            if (ele % 2 == 0) {
                ll.add(ele);
            }
        }
        return ll;
    }

    /**
     * Returns a list containing the common item of the two given lists
     */
    public static List<Integer> common(List<Integer> l1, List<Integer> l2) {
        // TODO: Fill in this function.
        List<Integer> ll = new ArrayList<>();
        for (int i : l1) {
            if (l2.contains(i)) {
                ll.add(i);
            }
        }
        return ll;
    }


    /**
     * Returns the number of occurrences of the given character in a list of strings.
     */
    public static int countOccurrencesOfC(List<String> words, char c) {
        // TODO: Fill in this function.
        int cnt = 0;
        for (String word : words) {
            for (char cur : word.toCharArray()) {
                if (cur == c) {
                    cnt++;
                }
            }
        }
        return cnt;
    }
}
