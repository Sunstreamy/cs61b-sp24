import deque.ArrayDeque61B;
import deque.Deque61B;
import org.junit.jupiter.api.*;


import deque.LinkedListDeque61B;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class LinkedListDeque61BTest {

    @Test
    public void iteratorForEachTest() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");
        assertThat(lld1).containsExactly("front", "middle", "back");
    }

    @Test
    public void testEqualDeque61B() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        Deque61B<String> lld2 = new LinkedListDeque61B<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");

        lld2.addLast("front");
        lld2.addLast("middle");
        lld2.addLast("back");

        assertThat(lld1).isEqualTo(lld2);
    }

    @Test
    public void toStringTest() {
        Deque61B<Integer> ad = new LinkedListDeque61B<>();
        ad.addLast(10);
        ad.addLast(20);
        ad.addLast(30);

        String expected = "[10, 20, 30]";
        assertThat(ad.toString()).isEqualTo(expected);
    }
}

