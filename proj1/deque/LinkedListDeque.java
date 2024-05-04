/** Link list based deque.
 *  @author sjk1949
 */

package deque;

import afu.org.checkerframework.checker.oigj.qual.O;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>,Iterable<T> {

    private class Node {
        public T item;
        public Node prior;
        public Node next;

        public Node(T item, Node prior, Node next) {
            this.item = item;
            this.prior = prior;
            this.next = next;
        }
    }

    private class LinkedListDequeIterator implements Iterator<T> {
        private Node nextNode;

        public LinkedListDequeIterator() {
            nextNode = sentinel.next;
        }
        @Override
        public boolean hasNext() {
            return nextNode != sentinel;
        }

        @Override
        public T next() {
            T returnItem = nextNode.item;
            nextNode = nextNode.next;
            return returnItem;
        }
    }

    private Node sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.prior = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    @Override
    public void addFirst(T item) {
        Node node = new Node(item, sentinel, sentinel.next);
        sentinel.next.prior = node;
        sentinel.next = node;
        size += 1;
    }

    @Override
    public void addLast(T item) {
        Node node = new Node(item, sentinel.prior, sentinel);
        sentinel.prior.next = node;
        sentinel.prior = node;
        size += 1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        Node p = sentinel.next;
        while (p != sentinel) {
            System.out.print(p.item);
            System.out.print(" ");
            p = p.next;
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        Node node = sentinel.next;
        node.next.prior = sentinel;
        sentinel.next = node.next;
        size -= 1;
        return node.item;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        Node node = sentinel.prior;
        node.prior.next = sentinel;
        sentinel.prior = node.prior;
        size -= 1;
        return node.item;
    }

    @Override
    public T get(int index) {
        if (index >= this.size()) {return null;}
        Node p = sentinel.next;
        for (int i = 0; i < index; i++) {
            p = p.next;
        }
        return p.item;
    }

    public T getRecursive(int index) {
        return getRecursiveHelper(sentinel.next, index).item;
    }

    private Node getRecursiveHelper(Node beginNode, int index) {
        if (beginNode == sentinel) {
            return beginNode;
        }
        if (index == 0) {
            return beginNode;
        } else {
            return getRecursiveHelper(beginNode.next, index - 1);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
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

}
