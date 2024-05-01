/** Array based deque.
 *  @author sjk1949
 */

package deque;

public class ArrayDeque<T> {

    public T[] items;
    private int nextFirst;
    private int nextLast;
    private int size;

    private final int initialSize = 8;

    public ArrayDeque() {
        items = (T[]) new Object[initialSize];
        nextFirst = items.length - 1;
        nextLast = 0;
        size = 0;
    }

    public void addFirst(T item) {
        if (this.isFull()) {
            this.resize(items.length * 2);
        }
        items[nextFirst] = item;
        nextFirst = indexAdd(nextFirst, -1);
        size += 1;
    }

    public void addLast(T item) {
        if (this.isFull()) {
            this.resize(items.length * 2);
        }
        items[nextLast] = item;
        nextLast = indexAdd(nextLast, 1);
        size += 1;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size;
    }

    private boolean isFull() {
        return size() == items.length;
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
        if(isEmpty()) {
            return null;
        }
        if (size() < items.length / 4 && items.length > initialSize) {
            this.resize(items.length / 2);
        }
        nextFirst = indexAdd(nextFirst, 1);
        T item = items[nextFirst];
        items[nextFirst] = null;
        size -= 1;
        return item;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        if (size() < items.length / 4 && items.length > initialSize) {
            this.resize(items.length / 2);
        }
        nextLast = indexAdd(nextLast, -1);
        T item = items[nextLast];
        items[nextLast] = null;
        size -= 1;
        return item;
    }

    public T get(int index) {
        if (index > this.size()) {return null;}
        return items[indexToArray(index)];
    }

    //public Iterable<T> iterator() {}

    //public boolean equals(Object o) {}

    public void resize(int capacity) {
        T[] newItems = (T[]) new Object[capacity];
        if (indexAdd(nextLast, -1) >= indexAdd(nextFirst, 1)) {
            System.arraycopy(items, indexAdd(nextFirst, 1), newItems, 0, indexAdd(nextLast, -1) - indexAdd(nextFirst, 1) + 1);
        } else {
            System.arraycopy(items, indexAdd(nextFirst, 1), newItems, 0, items.length - nextFirst - 1);
            System.arraycopy(items, 0, newItems, items.length - nextFirst - 1, nextLast);
        }
        nextFirst = newItems.length - 1;
        nextLast = size();
        items = newItems;
    }

    /** Calculate the true index when index add one number
     * @param index the real index in the array
     * @param num the add number, can be negative
     * @return the result index*/
    private int indexAdd(int index, int num) {
        index += num;
        index = index % items.length;
        // if index is negative
        if (index < 0) {
            index += items.length;
        }
        return index;
    }

    /** Transfer the index in deque to the real index in array*/
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