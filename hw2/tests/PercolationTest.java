import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

public class PercolationTest {

    /**
     * Enum to represent the state of a cell in the grid. Use this enum to help you write tests.
     * <p>
     * (0) CLOSED: isOpen() returns true, isFull() return false
     * <p>
     * (1) OPEN: isOpen() returns true, isFull() returns false
     * <p>
     * (2) INVALID: isOpen() returns false, isFull() returns true
     * (This should not happen! Only open cells should be full.)
     * <p>
     * (3) FULL: isOpen() returns true, isFull() returns true
     * <p>
     */
    private enum Cell {
        CLOSED, OPEN, INVALID, FULL
    }

    /**
     * Creates a Cell[][] based off of what Percolation p returns.
     * Use this method in your tests to see if isOpen and isFull are returning the
     * correct things.
     */
    private static Cell[][] getState(int N, Percolation p) {
        Cell[][] state = new Cell[N][N];
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                int open = p.isOpen(r, c) ? 1 : 0;
                int full = p.isFull(r, c) ? 2 : 0;
                state[r][c] = Cell.values()[open + full];
            }
        }
        return state;
    }

    @Test
    public void basicTest() {
        int N = 5;
        Percolation p = new Percolation(N);
        // open sites at (r, c) = (0, 1), (2, 0), (3, 1), etc. (0, 0) is top-left
        int[][] openSites = {
                {0, 1},
                {2, 0},
                {3, 1},
                {4, 1},
                {1, 0},
                {1, 1}
        };
        Cell[][] expectedState = {
                {Cell.CLOSED, Cell.FULL, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.FULL, Cell.FULL, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.FULL, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.CLOSED, Cell.OPEN, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.CLOSED, Cell.OPEN, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED}
        };
        for (int[] site : openSites) {
            p.open(site[0], site[1]);
        }
        assertThat(getState(N, p)).isEqualTo(expectedState);
        assertThat(p.percolates()).isFalse();
    }

    @Test
    public void oneByOneTest() {
        int N = 1;
        Percolation p = new Percolation(N);
        p.open(0, 0);
        Cell[][] expectedState = {
                {Cell.FULL}
        };
        assertThat(getState(N, p)).isEqualTo(expectedState);
        assertThat(p.percolates()).isTrue();
    }

    // TODO: Using the given tests above as a template,
    //       write some more tests and delete the fail() line
    @Test
    public void testIsOpenAndNumberOfOpenSites() {
        int N = 3;
        Percolation p = new Percolation(N);
        assertThat(p.numberOfOpenSites()).isEqualTo(0);
        assertThat(p.isOpen(1, 1)).isFalse();
        p.open(1, 1);
        assertThat(p.numberOfOpenSites()).isEqualTo(1);
        p.open(1, 1);
        assertThat(p.numberOfOpenSites()).isEqualTo(1);
        assertThat(p.isOpen(1, 1)).isTrue();
    }

    @Test
    public void testOpenConnectsToNeighborAndMakesItFull() {
        // 创建一个3x3的渗透系统
        Percolation p = new Percolation(3);

        // 打开 (0, 1)，它在第一行
        p.open(0, 1);

        // 此时，(0, 1) 自身应该是 full 的，因为它在第一行且被打开了
        // 这一步也顺便测试了我们的虚拟顶部节点设计是否正确
        assertThat(p.isFull(0, 1)).isTrue();

        // 现在打开 (0, 1) 正下方的格子 (1, 1)
        p.open(1, 1);

        // 因为 (1, 1) 和 (0, 1) 是相邻的、打开的格子，
        // 我们期望 open(1, 1) 的实现已经把它们 union 在了一起。
        // 又因为 (0, 1) 是 full 的（即连接到了虚拟顶部），
        // 所以 (1, 1) 现在也应该是 full 的。
        assertThat(p.isFull(1, 1)).isTrue();

        // 作为对比，我们断言一个不相关的、未打开的格子不是 full
        assertThat(p.isFull(2, 2)).isFalse();
    }

}
