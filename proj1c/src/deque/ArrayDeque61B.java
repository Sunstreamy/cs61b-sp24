package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayDeque61B<T> implements Deque61B<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    @Override
    public Iterator<T> iterator() {
        return new ArrayDeque61BIterator();
    }


    private class ArrayDeque61BIterator implements Iterator<T> {
        private int wizPos;

        private ArrayDeque61BIterator() {
            wizPos = 0;
        }

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

    public ArrayDeque61B() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 4;
        nextLast = 5;
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
        if (size == items.length) {
            resize(items.length * 2);
        }
        items[nextFirst] = x;
        size += 1;
        nextFirst = Math.floorMod(nextFirst - 1, items.length);
    }

    @Override
    public void addLast(T x) {
        if (size == items.length) {
            resize(items.length * 2);
        }
        items[nextLast] = x;
        size += 1;
        nextLast = Math.floorMod(nextLast + 1, items.length);
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            // 调用我们已经实现的 get 方法
            T element = get(i);
            returnList.add(element);
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (items.length >= 16 && size * 4 <= items.length) {
            resize(items.length / 2); // 缩容到一半
        }
        if (isEmpty()) {
            return null;
        }
        size -= 1;
        int curFirstIndex = (nextFirst + 1) % items.length;
        T firstVal = items[curFirstIndex];

        items[curFirstIndex] = null;
        nextFirst = curFirstIndex;
        return firstVal;
    }

    @Override
    public T removeLast() {
        if (items.length >= 16 && size * 4 <= items.length) {
            resize(items.length / 2); // 缩容到一半
        }
        if (isEmpty()) {
            return null;
        }
        size -= 1;
        int curLastIndex = Math.floorMod(nextLast - 1, items.length);
        T lastVal = items[curLastIndex];

        items[curLastIndex] = null;
        nextLast = (curLastIndex);
        return lastVal;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        int firstItemIndex = (nextFirst + 1) % items.length;
        return items[(firstItemIndex + index) % items.length];
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }

    private void resize(int capacity) {
        T[] newItems = (T[]) new Object[capacity];
        for (int i = 0; i < size; ++i) {
            newItems[i] = this.get(i);
        }
        this.items = newItems;
        nextFirst = capacity - 1;
        nextLast = size;
    }
}
