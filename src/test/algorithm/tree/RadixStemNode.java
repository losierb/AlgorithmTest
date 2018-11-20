package test.algorithm.tree;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class RadixStemNode<T> implements RadixNode<T> {
    private char[] prefix;
    int size;
    RadixLeafNode<T> leaf;
    LinkedList<RadixStemNode<T>> children;

    public static <T> RadixStemNode<T> makeSingleStemNode(RadixLeafNode<T> leaf) {
        return new RadixStemNode<T>(leaf.getKey(), leaf, new LinkedList<RadixStemNode<T>>());
    }

    private RadixStemNode(char[] prefix, RadixLeafNode<T> leaf, LinkedList<RadixStemNode<T>> children) {
        this.prefix = prefix;
        this.leaf = leaf;
        this.children = children;
    }
    boolean fullMatch(char[] key, int index) {
        if(key.length-index != prefix.length) {
            return false;
        }
        for(int i = 0; i < prefix.length; i++) {
            if(prefix[i] != key[index+i]) {
                return false;
            }
        }
        return true;
    }
    boolean partialMatch(char[] key, int index) {
        if(key.length-index <= prefix.length) {
            return false;
        }
        if(prefix[0] == key[index]) {
            return true;
        }
        return false;
    }
    boolean prefixMatch(char[] key, int index) {
        if(key.length <= index) {
            return false;
        }

        if(key[index] == prefix[0]) {
            return true;
        }
        return false;
    }
    public void insertLeaf(RadixLeafNode<T> leaf, int index) {
        char[] key = leaf.getKey();
        if(this.fullMatch(key, index)) { // that's the leaf we are inserting
            this.leaf = leaf;
        } else if(this.partialMatch(key, index)) {
            for(RadixStemNode<T> child: children) {
                if(prefixMatch(key, index+prefix.length)) {
                    child.insertLeaf(leaf, index+prefix.length);
                }
            }
        } else { // only match a part of prefix, need to splinter
            int common;
            for (common = 0; (common+index < key.length) && (common < prefix.length); common++) {
                if (key[common+index] != prefix[common]) {
                    break;
                }
            }
            char[] parent_key = new char[common];
            char[] left_key = new char[key.length-common-index];
            char[] right_key = new char[prefix.length-common];
            for(int i = 0; i < common; i++) {
                parent_key[i] = prefix[i];
            }
            for(int i = 0; i < left_key.length; i++) {
                left_key[i] = key[common+index+i];
            }
            for(int i = 0; i < right_key.length; i++) {
                right_key[i] = prefix[common+i];
            }
            RadixStemNode<T> left_child = new RadixStemNode<T>(left_key, leaf, new LinkedList<RadixStemNode<T>>());
            RadixStemNode<T> right_child = new RadixStemNode<T>(right_key, this.leaf, this.children);
            prefix = parent_key;
            this.leaf = null;
            children = new LinkedList<RadixStemNode<T>>();
            children.add(left_child);
            children.add(right_child);
        }
    }
    // This find function actually compares each stem node's prefix with the key for at most 3 times.
    // it's able to reduce it to only once, but it will cost readability.
    public T find(char[] key, int index) throws NoSuchElementException {
        if(this.fullMatch(key, index)) {
            if(this.leaf != null) {
                return leaf.getValue();
            }
        } else if(this.prefixMatch(key, index)) {
            for(RadixStemNode<T> child: children) {
                if(child.partialMatch(key, index+prefix.length)) {
                    return child.find(key, index+prefix.length);
                }
            }
        }
        throw new NoSuchElementException(new String(key));
    }
}
