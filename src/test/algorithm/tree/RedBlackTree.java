package test.algorithm.tree;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Random;

public class RedBlackTree <K extends Comparable<K>, V> {
    private class RBTreeNode<K extends Comparable<K>, V> {
        private K key;
        private V value;
        private RBTreeNode<K, V> left, right;
        /* In one of an implementation variant, they do the fix-up in a recursive approach, i.e.
         * doing the fix-up after the recursive call returns. However, making an explicit parent
          * reference is more intuitive for demo. */
        private RBTreeNode<K, V> parent;
        private boolean red; // true for red, false for black
        RBTreeNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = this.right = null;
        }
        public void setRed(boolean red) {
            this.red = red;
        }
        public boolean getRed() {
            return red;
        }
        public void setLeftChild(RBTreeNode<K, V> left) {
            this.left = left;
            if(left != null) {
                left.parent = this;
            }
        }
        public void setRightChild(RBTreeNode<K, V> right) {
            this.right = right;
            if(right != null) {
                right.parent = this;
            }
        }
        public RBTreeNode<K, V> getLeftChild() {
            return this.left;
        }
        public RBTreeNode<K, V> getRightChild() {
            return this.right;
        }
        public K getKey() {
            return key;
        }
        public V getValue() {
            return value;
        }
        public V setValue(V value) {
            this.value = value;
            return value;
        }
        public RBTreeNode<K, V> getParent() {
            return parent;
        }
        public void setParent(RBTreeNode<K, V> parent) {
            this.parent = parent;
        }
        public V get(K key) {
            int result = this.key.compareTo(key);
            if(result == 0) { // here it is
                return this.value;
            }
            else if(result > 0) {
                if(right == null) {
                    return null;
                }
                return right.get(key);
            } else {
                if(left == null) {
                    return null;
                }
                return left.get(key);
            }
        }
        public boolean containsKey(K key) {
            int result = this.key.compareTo(key);
            if(result == 0) {
                return true;
            }
            else if(result > 0) {
                if(right == null) {
                    return false;
                }
                return right.containsKey(key);
            } else {
                if(left == null) {
                    return false;
                }
                return left.containsKey(key);
            }
        }
        public String toString() {
            return key.toString()+"["+(getRed()?"R":"B")+"]("+left+", "+right+")";
        }
        /*
         * a tricky problem in case of root node.
         * so we have to return a new node reference for root of rotation result.
         */
        public RBTreeNode<K, V> minimumSubNode() {
            return (left == null)?this:left.minimumSubNode();
        }
        public RBTreeNode<K, V> maximumSubNode() {
            return (right == null)?this:right.maximumSubNode();
        }
        private RBTreeNode<K, V> rotateLeft() {
            RBTreeNode<K, V> old_right = right;
            right = right.left;
            if(right != null) {
                right.parent = this;
            }
            old_right.parent = parent;
            old_right.left = this;
            if(parent != null) {
                if(this == parent.left) {
                    parent.left = old_right;
                } else {
                    parent.right = old_right;
                }
            }
            parent = old_right;
            return old_right;
        }
        private RBTreeNode<K, V> rotateRight() {
            RBTreeNode<K, V> old_left = left;
            left = left.right;
            if(left != null) {
                left.parent = this;
            }
            old_left.parent = parent;
            old_left.right = this;
            if(parent != null) {
                if(this == parent.left) {
                    parent.left = old_left;
                } else {
                    parent.right = old_left;
                }
            }
            parent = old_left;
            return old_left;
        }
    }

    private RBTreeNode<K, V> root;
    private int size;

    public RedBlackTree() {
        root = null;
        size = 0;
    }
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    public boolean containsKey(K key) {
        if(root != null) {
            return root.containsKey(key);
        }
        return false;
    }

    public V get(K key) {
        if (root == null) {
            return null;
        }
        return root.get(key);
    }
    /**
     * Insert node to root
     * @param node node to insert
     * @param root the root node of the tree
     * @return The root node of the new tree
     */
    private void putNode(RBTreeNode<K, V> node, RBTreeNode<K, V> root) {
        int result = node.key.compareTo(root.key);
        //System.out.println("node.key = "+node.key+", root.key = "+root.key +", result = "+result);
        if(result == 0) {
            root.value = node.value;
        }
        else if(result > 0) {
            if(root.right == null) {
                root.setRightChild(node);
            } else {
                putNode(node, root.right);
            }
        } else {
            if(root.left == null) {
                root.setLeftChild(node);
            } else {
                putNode(node, root.left);
            }
        }
    }
    private void deleteNode(K key, RBTreeNode<K, V> root) throws NoSuchElementException {
        int result = key.compareTo(root.key);
        //System.out.println("node.key = "+key+", root.key = "+root.key +", result = "+result);
        if(result == 0) { // here it is, the node to be deleted is root
            if(root.left == null && root.right == null) { // leaf node, bye bye
                if(root.parent == null) {
                    this.root = null;
                } else {
                    if(root == root.parent.left) {
                        root.parent.left = null;
                    } else {
                        root.parent.right = null;
                    }
                }
            } else if(root.left == null || root.right == null) { // only a single child
                RBTreeNode<K, V> singlechild = (root.left != null)?root.left:root.right;
            }
        }
        else if(result > 0) {
            if(root.right == null) {
                throw new NoSuchElementException();
            } else {
                deleteNode(key, root.right);
            }
        } else {
            if(root.left == null) {
                throw new NoSuchElementException();
            } else {
                deleteNode(key, root.left);
            }
        }
    }

    /**
     * Restore R-B Tree property after insertion
     * @param node the node we inserted (must be in tree)
     */
    private void fixup(RBTreeNode<K, V> node) {
        RBTreeNode<K, V> parent, grandparent, uncle;
        if(node.parent != null) {
            parent = node.parent;
        } else { // make sure root node is black
            node.red = false;
            return;
        }
        // check if we need to fix this.
        if(!node.red || !parent.red) {
            return;
        }
        // since parent is red, there must be a grandparent
        grandparent = parent.parent;
        uncle = (parent == grandparent.left)?grandparent.right:grandparent.left;
        if(uncle != null && uncle.red) { // case 1: parent and uncle are red, change both to black and change grandparent to red
            //System.out.println("case 1");
            parent.red = uncle.red = false;
            grandparent.red = true;
            fixup(grandparent); // recursively fixup grandparent
        } else { // case 2: uncle is black
            if (node == parent.left && parent == grandparent.left) {
                //System.out.println("case 2.1");
                var new_grandparent = grandparent.rotateRight();
                new_grandparent.red = false;
                new_grandparent.right.red = true;
                if (new_grandparent.parent == null) { // necessary fix for root node
                    this.root = new_grandparent;
                }
            } else if (node == parent.right && parent == grandparent.right) {
                //System.out.println("case 2.2");
                var new_grandparent = grandparent.rotateLeft();
                new_grandparent.red = false;
                new_grandparent.left.red = true;
                if (new_grandparent.parent == null) { // necessary fix for root node
                    this.root = new_grandparent;
                }
            } else if (node == parent.right && parent == grandparent.left) {
                //System.out.println("case 3.1");
                parent.rotateLeft();
                // now parent is node's left child
                fixup(parent);
            } else if (node == parent.left && parent == grandparent.right) {
                //System.out.println("case 3.2");
                parent.rotateRight();
                fixup(parent);
            }
        }
    }

    public V put(K key, V value) {
        RBTreeNode<K, V> node = new RBTreeNode<>(key, value);
        if(root == null) {
            root = node;
            root.setRed(false);
            root.setParent(null);
        } else {
            node.setRed(true);
            this.putNode(node, root);
            this.fixup(node);
        }
        return value;
    }

    public V remove(K key) {
        return null;
    }

    public void clear() {
        root = null;
        size = 0;
    }
    public String toString() {
        return root.toString();
    }
    public static void main(String[] args) {
        RedBlackTree<Integer, Void> rbtree = new RedBlackTree<>();
        Random rand = new Random();
        for(int i = 0; i < 100; i++) {
            rbtree.put(rand.nextInt(1000), null);
        }
        System.out.println(rbtree);
    }
}
