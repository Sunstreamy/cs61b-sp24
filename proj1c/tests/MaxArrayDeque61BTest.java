import com.google.common.truth.Truth;
import edu.princeton.cs.algs4.In;
import org.junit.jupiter.api.*;

import java.util.Comparator;

import deque.MaxArrayDeque61B;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class MaxArrayDeque61BTest {
    private static class StringLengthComparator implements Comparator<String> {
        public int compare(String a, String b) {
            return a.length() - b.length();
        }
    }

    // 这是另一个自定义的比较器，按字符串字母倒序比较
    private static class ReverseAlphabeticalComparator implements Comparator<String> {
        public int compare(String a, String b) {
            // b.compareTo(a) 实现倒序
            return b.compareTo(a);
        }
    }

    @Test
    public void basicTest() {
        MaxArrayDeque61B<String> mad = new MaxArrayDeque61B<>(new StringLengthComparator());
        mad.addFirst("");
        mad.addFirst("2");
        mad.addFirst("fury road");
        assertThat(mad.max()).isEqualTo("fury road");
    }

    @Test
    public void EmptyTest() {
        MaxArrayDeque61B<Integer> m = new MaxArrayDeque61B<Integer>(Comparator.naturalOrder());
        assertThat(m.max()).isNull();

        // 断言带参数的 max(c) 也返回 null
        Comparator<Integer> reverseOrder = Comparator.reverseOrder();
        assertThat(m.max(reverseOrder)).isNull();
    }

    @Test
    public void maxWithDefaultComparatorTest() {
        MaxArrayDeque61B<String> mad = new MaxArrayDeque61B<>(new StringLengthComparator());
        mad.addLast("short");         // 长度 5
        mad.addLast("a");             // 长度 1
        mad.addLast("loooooong");     // 长度 9 (最大值在中间)
        mad.addLast("medium");        // 长度 6

        // 验证 max() 是否能正确找到 "loooooong"
        assertThat(mad.max()).isEqualTo("loooooong");

        // 添加一个同样长度的字符串
        mad.addLast("anotherlg");     // 长度 9 (多个最大值)

        Truth.assertThat(mad.max()).isAnyOf("loooooong", "anotherlg");
    }

    @Test
    public void diyComparatorTest() {
        MaxArrayDeque61B<String> mad = new MaxArrayDeque61B<>(new ReverseAlphabeticalComparator());
        mad.addLast("bbb");
        mad.addLast("aaa");
        mad.addLast("ccc");
        mad.addLast("ddd");

        assertThat(mad.max()).isEqualTo("aaa");
    }
}
