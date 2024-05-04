/** Array based deque.
 *  @author sjk1949
 */

package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>,Iterable<T> {

    private class ArrayDequeIterator implements Iterator<T> {
        private int pos;

        public ArrayDequeIterator() {
            pos = 0;
        }

        public boolean hasNext() {
            return pos < size;
        }

        public T next() {
            T returnItem = get(pos);
            pos += 1;
            return returnItem;
        }
    }

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

    @Override
    public void addFirst(T item) {
        if (this.isFull()) {
            this.resize(items.length * 2);
        }
        items[nextFirst] = item;
        nextFirst = indexAdd(nextFirst, -1);
        size += 1;
    }

    @Override
    public void addLast(T item) {
        if (this.isFull()) {
            this.resize(items.length * 2);
        }
        items[nextLast] = item;
        nextLast = indexAdd(nextLast, 1);
        size += 1;
    }

    @Override
    public int size() {
        return size;
    }

    private boolean isFull() {
        return size() == items.length;
    }

    @Override
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

    @Override
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

    @Override
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

    @Override
    public T get(int index) {
        if (index >= this.size()) {return null;}
        return items[indexToArray(index)];
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof Deque deque) {
            if (deque.size() == this.size()) {
                int i = 0;
                for (T item : this) {
                    if (!item.equals(deque.get(i))) {
                        return false;
                    }
                    i += 1;
                }
                return true;
            }
        }
        return false;
    }

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