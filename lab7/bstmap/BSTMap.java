package bstmap;

import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private BSTNode root;
    private int size;

    public BSTMap() {
        this.root = null;
        this.size = 0;
    }

    public void printInOrder() {
        inOrderTraversel(this.root);
        System.out.println();
    }

    @Override
    public void clear() {
        this.root = null;
        this.size = 0;
    }

    @Override
    public boolean containsKey(K Key) {
        return find(this.root, Key) != null;
    }

    @Override
    public V get(K Key) {
        BSTNode findResult;
        if ((findResult = find(this.root, Key)) == null) {
            return null;
        }
        return findResult.value;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void put(K key, V value) {
        this.root = insert(this.root, key, value);
        size += 1;
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    private class BSTNode {
        K key;
        V value;
        BSTNode left = null;
        BSTNode right = null;

        public BSTNode(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private BSTNode find(BSTNode node, K key) {
        if (node == null) {
            return null;
        }
        if (key.compareTo(node.key) == 0) {
            return node;
        } else if (key.compareTo(node.key) < 0) {
            return find(node.left, key);
        } else {
            return find(node.right, key);
        }
    }

    private BSTNode insert(BSTNode node, K key, V value) {
        if (node == null) {
            return new BSTNode(key, value);
        }
        if (key.compareTo(node.key) == 0) {
            node.value = value;
        } else if (key.compareTo(node.key) < 0) {
            node.left = insert(node.left, key, value);
        } else {
            node.right = insert(node.right, key, value);
        }
        return node;
    }

    private void inOrderTraversel(BSTNode node) {
        if (node != null) {
            inOrderTraversel(node.left);
            System.out.print(node.key + " ");
            inOrderTraversel(node.right);
        }
    }
}
