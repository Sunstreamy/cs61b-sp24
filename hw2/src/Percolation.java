import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private final int gridSize;
    private final WeightedQuickUnionUF uf;
    private final boolean[] openSites;
    private int openCounts;
    private final int virtualTopIndex;
    private final int virtualBottomIndex; // 即使 isFull 用不到，percolates 也会用到


    private int xyTo1D(int row, int col) {
        return row * gridSize + col;
    }

    private void validate(int row, int col) {
        if (row < 0 || row >= gridSize || col < 0 || col >= gridSize) {
            throw new IndexOutOfBoundsException("Index is out of bounds.");
        }
    }

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N must be greater than 0");
        }
        this.gridSize = N;
        this.openCounts = 0;
        int totalSites = N * N;
        this.uf = new WeightedQuickUnionUF(totalSites + 2);
        this.openSites = new boolean[totalSites];

        this.virtualTopIndex = totalSites;
        this.virtualBottomIndex = totalSites + 1;

        //在最开始，就把第一行所有格子和虚拟顶部连起来
        for (int c = 0; c < N; c++) {
            int topRowSiteIndex = xyTo1D(0, c);
            uf.union(virtualTopIndex, topRowSiteIndex);
        }

        // 把最后一行所有格子和虚拟底部连起来
        for (int c = 0; c < N; c++) {
            int bottomRowSiteIndex = xyTo1D(N - 1, c);
            uf.union(virtualBottomIndex, bottomRowSiteIndex);
        }

    }

    public void open(int row, int col) {
        validate(row, col);
        if (isOpen(row, col)) {
            return;
        }
        int index = xyTo1D(row, col);
        openSites[index] = true;
        openCounts++;

        if (row > 0 && isOpen(row - 1, col)) {
            int upIndex = xyTo1D(row - 1, col);
            uf.union(index, upIndex);
        }

        if (row < gridSize - 1 && isOpen(row + 1, col)) {
            int downIndex = xyTo1D(row + 1, col);
            uf.union(index, downIndex);
        }

        if (col > 0 && isOpen(row, col - 1)) {
            int leftIndex = xyTo1D(row, col - 1);
            uf.union(index, leftIndex);
        }

        if (col < gridSize - 1 && isOpen(row, col + 1)) {
            int rightIndex = xyTo1D(row, col + 1);
            uf.union(index, rightIndex);
        }
    }

    public boolean isOpen(int row, int col) {
        validate(row, col);
        int index = xyTo1D(row, col);
        return openSites[index];
    }

    public boolean isFull(int row, int col) {
        validate(row, col);
        int index = xyTo1D(row, col);
        return isOpen(row, col) && uf.connected(index, virtualTopIndex);
    }

    public int numberOfOpenSites() {
        return openCounts;
    }

    public boolean percolates() {
        return uf.connected(virtualBottomIndex, virtualTopIndex);
    }

}
