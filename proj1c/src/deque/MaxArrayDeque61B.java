package deque;

import java.util.Comparator;

public class MaxArrayDeque61B<T> extends ArrayDeque61B<T> {
    // 2. 需要一个新的实例变量来存储“默认裁判”
    private Comparator<T> defaultComparator;

    // 3. 实现新的构造函数
    public MaxArrayDeque61B(Comparator<T> c) {
        // 调用父类 (ArrayDeque61B) 的默认构造函数
        super();
        // 保存我们自己的“默认裁判”
        this.defaultComparator = c;
    }

    // 4. 实现新的 max() 方法
    public T max() {
        // 使用 this.defaultComparator 来实现比较逻辑
        return max(this.defaultComparator);
    }

    // 5. 实现另一个版本的 max(Comparator<T> c) 方法
    public T max(Comparator<T> c) {
        // 使用参数 c 来实现比较逻辑
        if (isEmpty()) {
            return null;
        }
        T maxItem = get(0);
        for (T curItem : this) {
            if (c.compare(curItem, maxItem) > 0) {
                maxItem = curItem;
            }
        }
        return maxItem;
    }
}
