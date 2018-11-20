package test.algorithm.tree;

import java.util.*;

public class Radix <T> {
    private LinkedList<RadixStemNode<T>> root;
    public Radix() {
        root = new LinkedList<RadixStemNode<T>>();
    }

    public void insert(String key, T value) {
        insert(key.toCharArray(), value);
    }
    public void insert(char[] key, T value) {
        RadixLeafNode<T> leaf = new RadixLeafNode<T>(key, value);
        insert(leaf);
    }
    private void insert(RadixLeafNode<T> leaf) {
        for(RadixStemNode<T> child: root) {
            if(child.partialMatch(leaf.getKey(), 0)) {
                child.insertLeaf(leaf, 0);
                return;
            }
        }
        root.add(RadixStemNode.makeSingleStemNode(leaf));
    }
    public T find(char[] key) throws NoSuchElementException {
        for(RadixStemNode<T> child: root) {
            if(child.prefixMatch(key, 0)) {
                return child.find(key, 0);
            }
        }
        throw new NoSuchElementException(new String(key));
    }
    public T find(String key) throws NoSuchElementException {
        return find(key.toCharArray());
    }
    public static void main(String[] args) {
        Radix<Integer> radix = new Radix<Integer>();
        radix.insert("abc", 123456);
        radix.insert("bcd", 13435345);
        System.out.println(radix.find("abc"));
        System.out.println(radix.find("bcde"));
    }
}
