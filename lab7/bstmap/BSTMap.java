package bstmap;

import java.util.*;

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
        Set<K> keySet = new HashSet<>();
        for (K key : this) {
            keySet.add(key);
        }
        return keySet;
    }

    @Override
    public V remove(K key) {
        BSTNode node = find(this.root, key);
        if (node == null) {
            return null;
        }
        V removeNodeValue = node.value;
        this.root = remove(this.root, key);
        this.size --;
        return removeNodeValue;
    }

    @Override
    public V remove(K key, V value) {
        BSTNode node = find(this.root, key);
        if (node == null) {
            return null;
        }
        if (node.value == value) {
            return remove(key);
        }
        return node.value;
    }

    @Override
    public Iterator<K> iterator() {
        return new BSTNodeIterator();
    }

    private class BSTNodeIterator implements Iterator<K> {

        private List<BSTNode> nodeList = new ArrayList<>();

        public BSTNodeIterator() {
            inOrderTraversel(root, this.nodeList);
        }

        public boolean hasNext() {
            return !this.nodeList.isEmpty();
        }

        public K next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return this.nodeList.remove(0).key;
        }
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
        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            node.value = value;
        } else if (cmp < 0) {
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

    /** A function to return inOrderTraversel BSTNode list
     *
     * @param node the tree with the root node that is going to traversel
     * @param nodeList the return of the sorted Keys
     */

    private void inOrderTraversel(BSTNode node, List<BSTNode> nodeList) {
        if (node != null) {
            inOrderTraversel(node.left, nodeList);
            nodeList.add(node);
            inOrderTraversel(node.right, nodeList);
        }
    }

    private BSTNode remove(BSTNode node, K key) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = remove(node.left, key);
        } else if (cmp > 0) {
            node.right = remove(node.right, key);
        } else { // if the node is the one that is going to remove
            if (node.left == null && node.right == null) {
                return null;
            }
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            }

            BSTNode maxNode = findMax(node.left);
            node.left = remove(node.left, maxNode.key);
            maxNode.left = node.left;
            maxNode.right = node.right;
            return maxNode;
        }
        return node;
    }

    private BSTNode findMax(BSTNode node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }
}
