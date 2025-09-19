import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

    @Test
    @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
    void noNonTrivialFields() {
        List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
                .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
                .toList();

        assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
    }

    @Test
    public void add_first_from_empty() {
        Deque61B<Integer> lld = new ArrayDeque61B<>();
        lld.addFirst(1);
        assertThat(lld.toList()).containsExactly(1);
        assertThat(lld.size()).isEqualTo(1);
    }

    @Test
    public void add_last_from_empty() {
        Deque61B<Integer> lld = new ArrayDeque61B<>();
        lld.addLast(1);
        assertThat(lld.toList()).containsExactly(1);
        assertThat(lld.size()).isEqualTo(1);
    }

    @Test
    public void add_first_nonempty() {
        Deque61B<String> lld = new ArrayDeque61B<>();
        lld.addLast("middle");
        lld.addLast("back");

        lld.addFirst("front");

        assertThat(lld.size()).isEqualTo(3);
        assertThat(lld.toList()).containsExactly("front", "middle", "back");
    }

    @Test
    public void add_last_nonempty() {
        Deque61B<Integer> lld = new ArrayDeque61B<>();
        lld.addFirst(1);
        lld.addFirst(2);

        lld.addLast(3);

        assertThat(lld.size()).isEqualTo(3);
        assertThat(lld.toList()).containsExactly(1, 2, 3);
    }

    @Test
    public void addFirstAfterRemoveToEmpty() {
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        ad.addLast(1);
        ad.addLast(2);
        ad.addLast(3); // ad is {1, 2, 3}
        ad.removeLast();
        ad.removeLast();
        ad.removeLast(); // ad is now empty again

        // Now, test if it behaves like a new deque
        ad.addFirst(100);

        assertThat(ad.size()).isEqualTo(1);
        assertThat(ad.get(0)).isEqualTo(100);
        assertThat(ad.toList()).containsExactly(100);
    }

    @Test
    public void addLastAfterRemoveToEmpty() {
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        ad.addLast(1);
        ad.addFirst(2); // ad is {2, 1}
        ad.removeLast();
        ad.removeFirst(); // ad is now empty again

        ad.addLast(200);

        assertThat(ad.size()).isEqualTo(1);
        assertThat(ad.toList()).containsExactly(200);
    }

    @Test
    public void getTest() {
        Deque61B<Integer> lld = new ArrayDeque61B<>();
        lld.addLast(1);
        lld.addFirst(5);
        lld.addFirst(6);

        Integer tmp = lld.get(1);
        Integer tmp1 = lld.get(5);
        Integer tmp2 = lld.get(-3);

        assertThat(tmp).isEqualTo(5);
        assertThat(tmp1).isNull();
        assertThat(tmp2).isNull();
    }


    @Test
    public void isEmptyTest() {
        Deque61B<Integer> lld = new ArrayDeque61B<>();
        assertThat(lld.isEmpty()).isTrue();

        lld.addLast(1);
        lld.addFirst(-1);

        assertThat(lld.isEmpty()).isFalse();
    }


    @Test
    public void sizeTest() {
        Deque61B<Integer> lld = new ArrayDeque61B<>();
        lld.addLast(1);
        lld.addFirst(-1);
        lld.addFirst(0);

        assertThat(lld.size()).isEqualTo(3);
    }

    @Test
    public void sizeAfterRemoveToEmpty() {
        Deque61B<Integer> lld = new ArrayDeque61B<>();
        lld.addLast(10);
        lld.addLast(20);

        lld.removeFirst();
        lld.removeFirst(); // Deque is now empty

        assertThat(lld.size()).isEqualTo(0);
    }

    @Test
    public void sizeAfterRemoveFromEmpty() {
        Deque61B<Integer> lld = new ArrayDeque61B<>();
        lld.removeLast(); // Should do nothing to size

        assertThat(lld.size()).isEqualTo(0);
    }

    @Test
    public void size_after_remove_from_empty() {
        Deque61B<Integer> lld = new ArrayDeque61B<>();
        lld.addLast(1);
        lld.addFirst(-1);
        lld.addFirst(0);

        assertThat(lld.size()).isEqualTo(3);
    }


    @Test
    public void tolistTest() {
        Deque61B<Integer> lld = new ArrayDeque61B<>();
        assertThat(lld.toList().isEmpty()).isTrue();

        lld.addLast(1);
        lld.addFirst(-1);
        lld.addFirst(0);

        assertThat(lld.toList()).containsExactly(0, -1, 1);
    }

    @Test
    public void remove_from_nonempty() {
        Deque61B<Integer> lld = new ArrayDeque61B<>();

        lld.addLast(1);
        lld.addFirst(-2);
        lld.addFirst(0);
        lld.addFirst(5);
        lld.addLast(8);//[5,0,-2,1,8]

        Integer removedItem_F = lld.removeFirst();
        assertThat(lld.size()).isEqualTo(4);
        assertThat(removedItem_F).isEqualTo(5);
        assertThat(lld.toList()).containsExactly(0, -2, 1, 8);

        Integer removedItem_L = lld.removeLast();
        assertThat(lld.size()).isEqualTo(3);
        assertThat(removedItem_L).isEqualTo(8);
        assertThat(lld.toList()).containsExactly(0, -2, 1);

    }

    @Test
    public void removeFirst_to_one() {
        Deque61B<Integer> lld = new ArrayDeque61B<>();

        lld.addLast(1);
        lld.addFirst(-2);//-2 ,1

        Integer removedItem = lld.removeFirst();
        assertThat(removedItem).isEqualTo(-2);
        assertThat(lld.size()).isEqualTo(1);
        assertThat(lld.toList()).containsExactly(1);
    }

    @Test
    public void removeLast_to_one() {
        Deque61B<Integer> lld = new ArrayDeque61B<>();

        lld.addLast(1);
        lld.addFirst(-2);//-2 ,1

        Integer removedItem = lld.removeLast();
        assertThat(removedItem).isEqualTo(1);
        assertThat(lld.size()).isEqualTo(1);
        assertThat(lld.toList()).containsExactly(-2);
    }

    @Test
    public void removeFirst_to_empty() {
        Deque61B<Integer> lld = new ArrayDeque61B<>();

        lld.addFirst(1);

        Integer removedItem = lld.removeFirst();
        assertThat(removedItem).isEqualTo(1);
        assertThat(lld.size()).isEqualTo(0);
        assertThat(lld.toList()).containsExactly();
        assertThat(lld.isEmpty()).isTrue();
    }

    @Test
    public void removeLast_to_empty() {
        Deque61B<Integer> lld = new ArrayDeque61B<>();

        lld.addFirst(1);

        Integer removedItem = lld.removeFirst();
        assertThat(removedItem).isEqualTo(1);
        assertThat(lld.size()).isEqualTo(0);
        assertThat(lld.toList()).containsExactly();
        assertThat(lld.isEmpty()).isTrue();
    }

    @Test
    public void remove_from_empty() {
        Deque61B<Integer> lld = new ArrayDeque61B<>();

        Integer removedFirstItem = lld.removeFirst();
        assertThat(removedFirstItem).isNull();
        assertThat(lld.size()).isEqualTo(0);

        Integer removedLastItem = lld.removeFirst();
        assertThat(removedLastItem).isNull();
        assertThat(lld.size()).isEqualTo(0);
    }

    @Test
    public void add_last_trigger_resize() {
        Deque61B<Integer> lld = new ArrayDeque61B<>();
        int iniCapacity = 8;
        for (int i = 0; i < iniCapacity; ++i) {
            lld.addLast(i);
        }

        lld.addLast(8);
        assertThat(lld.size()).isEqualTo(9);
        assertThat(lld.toList()).containsExactly(0, 1, 2, 3, 4, 5, 6, 7, 8);
        assertThat(lld.get(0)).isEqualTo(0);
        assertThat(lld.get(7)).isEqualTo(7);
        assertThat(lld.get(8)).isEqualTo(8);
    }

    @Test
    public void add_first_trigger_resize() {
        Deque61B<Integer> lld = new ArrayDeque61B<>();
        int iniCapacity = 8;
        for (int i = 0; i < iniCapacity; ++i) {
            lld.addFirst(i);
        }

        lld.addFirst(8);
        assertThat(lld.size()).isEqualTo(9);
        assertThat(lld.toList()).containsExactly(8, 7, 6, 5, 4, 3, 2, 1, 0);
        assertThat(lld.get(0)).isEqualTo(8);
        assertThat(lld.get(7)).isEqualTo(1);
        assertThat(lld.get(8)).isEqualTo(0);
    }

    @Test
    public void remove_last_trigger_resize() {
        Deque61B<Integer> lld = new ArrayDeque61B<>();
        //模拟出现浪费空间的情况
        for (int i = 0; i < 9; ++i) {
            lld.addFirst(i);
        }
        for (int i = 0; i < 5; ++i) {
            lld.removeFirst();
        }
        //3,2,1,0
        Integer removedItem = lld.removeLast();

        assertThat(removedItem).isEqualTo(0);
        assertThat(lld.size()).isEqualTo(3);
        assertThat(lld.toList()).containsExactly(3, 2, 1).inOrder();

    }

    @Test
    public void remove_first_trigger_resize() {
        Deque61B<Integer> lld = new ArrayDeque61B<>();
        //模拟出现浪费空间的情况
        for (int i = 0; i < 9; ++i) {
            lld.addFirst(i);
        }
        for (int i = 0; i < 5; ++i) {
            lld.removeFirst();
        }
        //3,2,1,0
        Integer removedItem = lld.removeFirst();

        assertThat(removedItem).isEqualTo(3);
        assertThat(lld.size()).isEqualTo(3);
        assertThat(lld.toList()).containsExactly(2, 1, 0).inOrder();

    }

    @Test
    public void resizeUpAndDownTest() {
        Deque61B<Integer> lld = new ArrayDeque61B<>();

        // 1. Resize Up
        for (int i = 0; i < 9; i++) {
            lld.addLast(i); // Trigger upsize from 8 to 16
        }

        // 2. Prepare for Resize Down
        for (int i = 0; i < 5; i++) {
            lld.removeLast(); // size becomes 4, capacity is 16. Ready to shrink.
        }
        // Current state: {0, 1, 2, 3}

        // 3. Trigger Resize Down
        Integer removedItem = lld.removeLast(); // remove 3, triggers shrink from 16 to 8

        // 4. Assert after shrinking
        assertThat(removedItem).isEqualTo(3);
        assertThat(lld.size()).isEqualTo(3);
        assertThat(lld.toList()).containsExactly(0, 1, 2);

        // 5. (Optional but recommended) Test if it can still grow correctly
        for (int i = 0; i < 6; i++) {
            lld.addLast(i + 10); // Add 6 more items, {0,1,2,10,11,12,13,14,15}
        }                         // This should trigger another upsize from 8 to 16

        assertThat(lld.size()).isEqualTo(9);
        assertThat(lld.get(8)).isEqualTo(15);
    }
}


