import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        private K key;
        private V val;
        private Node left;
        private Node right;

        public Node(K key, V value) {
            this.key = key;
            this.val = value;
        }
    }

    private Node root;
    private int size;

    /**
     * Associates the specified value with the specified key in this map.
     * If the map already contains the specified key, replaces the key's mapping
     * with the value specified.
     *
     * @param key
     * @param value
     */
    @Override
    public void put(K key, V value) {
        root = putHelper(root, key, value);
    }

    private Node putHelper(Node n, K key, V value) {
        if (n == null) {
            size++;
            return new Node(key, value);
        }
        int cmp = key.compareTo(n.key);
        if (cmp < 0) {
            n.left = putHelper(n.left, key, value);
        } else if (cmp > 0) {
            n.right = putHelper(n.right, key, value);
        } else {
            n.val = value;
        }
        return n;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key
     */
    @Override
    public V get(K key) {
        return getHelper(root, key);
    }

    private V getHelper(Node n, K key) {
        if (n == null) {
            return null;
        }
        int cmp = key.compareTo(n.key);
        if (cmp < 0) {
            return getHelper(n.left, key);
        } else if (cmp > 0) {
            return getHelper(n.right, key);
        } else {
            return n.val;
        }
    }

    /**
     * Returns whether this map contains a mapping for the specified key.
     *
     * @param key
     */
    @Override
    public boolean containsKey(K key) {
        return containsKeyHelper(root, key);
    }

    private boolean containsKeyHelper(Node n, K key) {
        if (n == null) {
            return false;
        }
        int cmp = key.compareTo(n.key);

        if (cmp < 0) {
            return containsKeyHelper(n.left, key);
        } else if (cmp > 0) {
            return containsKeyHelper(n.right, key);
        } else {
            return true;
        }
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Removes every mapping from this map.
     */
    @Override
    public void clear() {
        this.size = 0;
        this.root = null;
    }

    /**
     * Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException.
     */
    @Override
    public Set<K> keySet() {
        return Set.of();
    }

    /**
     * Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key
     */
    @Override
    public V remove(K key) {
        return null;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<K> iterator() {
        return null;
    }
}
