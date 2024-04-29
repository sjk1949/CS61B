package deque;

public class LinkedListDeque<T> {

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

    private Node sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.prior = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    public void addFirst(T item) {
        Node node = new Node(item, sentinel, sentinel.next);
        sentinel.next.prior = node;
        sentinel.next = node;
        size += 1;
    }

    public void addLast(T item) {
        Node node = new Node(item, sentinel.prior, sentinel);
        sentinel.prior.next = node;
        sentinel.prior = node;
        size += 1;
    }

    public boolean isEmpty() {
        return this.size() == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        Node p = sentinel.next;
        while (p != sentinel) {
            System.out.print(p.item);
            System.out.print(" ");
            p = p.next;
        }
        System.out.println();
    }

    public T removeFirst() {
        Node node = sentinel.next;
        node.next.prior = sentinel;
        sentinel.next = node.next;
        return node.item;
    }

    public T removeLast() {
        Node node = sentinel.prior;
        node.prior.next = sentinel;
        sentinel.prior = node.prior;
        return node.item;
    }

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

    //public Iterator<T> iterator() {}

    //public boolean equals(Object o) {}

}
