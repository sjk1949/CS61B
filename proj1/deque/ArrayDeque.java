package deque;

public class ArrayDeque<T> {

    private T[] items;
    private int nextFirst;
    private int nextLast;

    private final int initialSize = 8;

    public ArrayDeque() {
        items = (T[]) new Object[initialSize];
        nextFirst = items.length - 1;
        nextLast = 0;
    }

    public void addFirst(T item) {
        if (this.isFull()) {
            this.resize(items.length * 2);
        }
        items[nextFirst] = item;
        nextFirst -= 1;
    }

    public void addLast(T item) {
        if (this.isFull()) {
            this.resize(items.length * 2);
        }
        items[nextLast] = item;
        nextLast += 1;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return items.length - nextFirst + nextLast - 1;
    }

    private boolean isFull() {
        return nextFirst < nextLast;
    }

    public void printDeque() {
        for (int i = nextFirst + 1; i < items.length; i++) {
            System.out.print(items[i]);
            System.out.print(" ");
        }
        for (int i = 0; i < nextLast; i++) {
            System.out.print(items[i]);
            System.out.print(" ");
        }
        System.out.println();
    }

    public T removeFirst() {
        if (size() < items.length / 4 && items.length > initialSize) {
            this.resize(items.length / 2);
        }
        nextFirst += 1;
        T item = items[nextFirst];
        items[nextFirst] = null;
        return item;
    }

    public T removeLast() {
        if (size() < items.length / 4 && items.length > initialSize) {
            this.resize(items.length / 2);
        }
        nextLast -= 1;
        T item = items[nextLast];
        items[nextLast] = null;
        return item;
    }

    public T get(int index) {
        if (index > this.size()) {return null;}
        return items[indexToArray(index)];
    }

    //public Iterable<T> iterator() {}

    //public boolean equals(Object o) {}

    private void resize(int capacity) {
        T[] newItems = (T[]) new Object[capacity];
        System.arraycopy(items, 0, newItems, 0, nextLast);
        int newNextFirst = newItems.length - items.length + nextFirst;
        if (nextFirst != items.length - 1) {
            System.arraycopy(items, nextFirst + 1, newItems, newNextFirst + 1, newItems.length - newNextFirst - 1);
        }
        nextFirst = newNextFirst;
        items = newItems;
    }

    private int indexToArray(int index) {
        int arrayIndex;
        if (items.length - nextFirst - 1 > index) {
            arrayIndex = nextFirst + index + 1;
        } else {
            arrayIndex = index + nextFirst - items.length + 1;
        }
        return arrayIndex;
    }

}