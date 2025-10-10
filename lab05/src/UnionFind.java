public class UnionFind {
    private int[] fa;

    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        fa = new int[N];
        for (int i = 0; i < N; i++) {
            fa[i] = -1;
        }
    }

    private void validate(int v) {
        if (v < 0 || v >= fa.length) {
            throw new IllegalArgumentException("Index " + v + " is not between 0 and " + (fa.length - 1));
        }
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        validate(v);
        return -fa[find(v)];
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        validate(v);
        return fa[v];
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        validate(v1);
        validate(v2);
        return find(v1) == find(v2);
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        validate(v);
        if (fa[v] < 0) {
            return v;
        } else {
            return fa[v] = find(fa[v]);
        }
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing an item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        validate(v1);
        validate(v2);
        int fa1 = find(v1), fa2 = find(v2);
        if (fa1 == fa2) {
            return;
        } else {
            if (fa[fa1] < fa[fa2]) {
                fa[fa1] += fa[fa2];
                fa[fa2] = fa1;
            } else {
                fa[fa2] += fa[fa1];
                fa[fa1] = fa2;
            }
        }
    }
}
