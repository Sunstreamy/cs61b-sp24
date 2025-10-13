import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;
import static org.junit.Assert.fail;

public class UnionFindTest {

    /**
     * Checks that the initial state of the disjoint sets are correct (this will pass with the skeleton
     * code, but ensure it still passes after all parts are implemented).
     */
    @Test
    public void initialStateTest() {
        UnionFind uf = new UnionFind(4);
        assertThat(uf.connected(0, 1)).isFalse();
        assertThat(uf.connected(0, 2)).isFalse();
        assertThat(uf.connected(0, 3)).isFalse();
        assertThat(uf.connected(1, 2)).isFalse();
        assertThat(uf.connected(1, 3)).isFalse();
        assertThat(uf.connected(2, 3)).isFalse();
    }

    /**
     * Checks that invalid inputs are handled correctly.
     */
    @Test
    public void illegalFindTest() {
        UnionFind uf = new UnionFind(4);
        try {
            uf.find(10);
            fail("Cannot find an out of range vertex!");
        } catch (IllegalArgumentException e) {
            return;
        }
        try {
            uf.union(1, 10);
            fail("Cannot union with an out of range vertex!");
        } catch (IllegalArgumentException e) {
            return;
        }
    }

    /**
     * Checks that union is done correctly (including the tie-breaking scheme).
     */
    @Test
    public void basicUnionTest() {
        UnionFind uf = new UnionFind(10);
        uf.union(0, 1);
        assertThat(uf.find(0)).isEqualTo(1);
        uf.union(2, 3);
        assertThat(uf.find(2)).isEqualTo(3);
        uf.union(0, 2);
        assertThat(uf.find(1)).isEqualTo(3);

        uf.union(4, 5);
        uf.union(6, 7);
        uf.union(8, 9);
        uf.union(4, 8);
        uf.union(4, 6);

        assertThat(uf.find(5)).isEqualTo(9);
        assertThat(uf.find(7)).isEqualTo(9);
        assertThat(uf.find(8)).isEqualTo(9);

        uf.union(9, 2);
        assertThat(uf.find(3)).isEqualTo(9);
    }

    /**
     * Unions the same item with itself. Calls on find and checks that the outputs are correct.
     */
    @Test
    public void sameUnionTest() {
        UnionFind uf = new UnionFind(4);
        uf.union(1, 1);
        for (int i = 0; i < 4; i += 1) {
            assertThat(uf.find(i)).isEqualTo(i);
        }
    }

    /**
     * Write your own tests below here to verify for correctness. The given tests are not comprehensive.
     * Specifically, you may want to write a test for path compression and to check for the correctness
     * of all methods in your implementation.
     */
    @Test
    public void testUnionTieBreaking() {
        UnionFind uf = new UnionFind(4);
        // {0}, {1}, {2}, {3}

        uf.union(0, 1); // {0, 1}, {2}, {3}. root is 1, size is 2
        uf.union(2, 3); // {0, 1}, {2, 3}. root is 3, size is 2

        // 两个集合大小相等，都是2
        // 根据规则，v1(0)的根(1)应该连接到v2(2)的根(3)上
        // 所以，parent(1) 应该等于 3
        uf.union(0, 2);

        // 断言 v1 的根现在是 v2 的根
        assertThat(uf.parent(1)).isEqualTo(3);

        // 断言新集合的大小是 4
        assertThat(uf.sizeOf(0)).isEqualTo(4);
        assertThat(uf.sizeOf(3)).isEqualTo(4);
    }

    @Test
    public void testPathCompressionEffect() {
        UnionFind uf = new UnionFind(10);

        // 目标：构建一个路径 9 -> 8 -> 7 -> 6(root)
        // 我们需要绕过 WQU 来手动构建它，这很难。
        // 所以我们换一种思路：我们可以构建一个允许存在长路径的结构。

        // 构建两棵树
        // Tree 1: {0,1,2,3}, root = 0 (高度 1)
        uf.union(1, 0);
        uf.union(2, 0);
        uf.union(3, 0);

        // Tree 2: {4,5,6,7}, root = 4 (高度 1)
        uf.union(5, 4);
        uf.union(6, 4);
        uf.union(7, 4);

        // 连接两棵树，root 0 会连接到 root 4
        uf.union(0, 4); // 新树大小为8，根为4

        // 现在，0 的父亲是 4。1,2,3 的父亲仍然是 0
        // 所以，find(1) 的路径是 1 -> 0 -> 4
        assertThat(uf.parent(1)).isEqualTo(0);
        assertThat(uf.parent(0)).isEqualTo(4);

        // 调用 find(1)。这会触发路径压缩。
        int root = uf.find(1);

        // 验证根是 4
        assertThat(root).isEqualTo(4);

        // 验证路径压缩的效果：
        // 1 在寻找根的过程中，它的父指针应该被直接修改为根 4。
        assertThat(uf.parent(1)).isEqualTo(4);

        // 路径上的 0 的父指针应该保持为 4
        assertThat(uf.parent(0)).isEqualTo(4);
    }
}


