package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T> {

    private static class Node<T> {
        private T item;
        private Node<T> prev;
        private Node<T> next;

        Node(T i, Node<T> p, Node<T> n) {
            item = i;
            prev = p;
            next = n;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListDeque61BIterator();
    }

    private class LinkedListDeque61BIterator implements Iterator<T> {
        private int wizPos = 0;

        @Override
        public boolean hasNext() {
            return wizPos < size;
        }

        @Override
        public T next() {
            T returnItem = get(wizPos);
            wizPos += 1;
            return returnItem;
        }

    }

    private Node<T> sentinel;
    private int size;

    public LinkedListDeque61B() {
        size = 0;

        sentinel = new Node<T>(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Deque61B<?> otherDeque)) return false;
        if (this.size != otherDeque.size()) return false;

        // 内容深度检查 (使用 for-each 的版本)
        Iterator<?> otherIter = otherDeque.iterator();
        for (T x : this) {
            Object otherItem = otherIter.next();
            if (!x.equals(otherItem)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return toList().toString();
    }


    @Override
    public void addFirst(T x) {
        size += 1;
        Node<T> newNode = new Node<>(x, null, null);

        // 2. 找到邻居
        Node<T> oldFirst = sentinel.next;

        newNode.prev = sentinel;
        newNode.next = oldFirst;
        oldFirst.prev = newNode;
        sentinel.next = newNode;
    }

    @Override
    public void addLast(T x) {
        size += 1;
        Node<T> oldLst = sentinel.prev;
        Node<T> newLst = new Node<>(x, null, null);

        newLst.prev = oldLst;
        newLst.next = sentinel;
        oldLst.next = newLst;
        sentinel.prev = newLst;
    }

    @Override
    public List<T> toList() {
        List<T> res = new ArrayList<>();

        Node<T> ptr = sentinel.next;
        while (ptr != sentinel) {
            res.add(ptr.item);
            ptr = ptr.next;
        }
        return res;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
//        return false;
    }

    @Override
    public int size() {
        return size;
//        return 0;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        size -= 1;
        Node<T> nodeToRemove = sentinel.next;
        sentinel.next = nodeToRemove.next;
        nodeToRemove.prev = null;
        nodeToRemove.next = null;

        return nodeToRemove.item;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        size -= 1;
        Node<T> nodeToRemove = sentinel.prev;
        sentinel.prev = nodeToRemove.prev;
        nodeToRemove.prev.next = sentinel;
        nodeToRemove.next = null;
        nodeToRemove.prev = null;


        return nodeToRemove.item;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }

        Node<T> ptr = sentinel.next;
        for (int i = 0; i < index; ++i) {
            ptr = ptr.next;
        }
        return ptr.item;
    }

    @Override
    public T getRecursive(int index) {
        return getRecursiveHelper(sentinel.next, index);
    }

    private T getRecursiveHelper(Node<T> curNode, int index) {
        if (index < 0 || index > size) {
            return null;
        }
        if (index == 0) {
            return curNode.item;
        }
        return getRecursiveHelper(curNode.next, index - 1);
    }
}
