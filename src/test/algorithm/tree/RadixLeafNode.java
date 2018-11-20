package test.algorithm.tree;

import java.util.Arrays;

public class RadixLeafNode <T> implements RadixNode {
    private T value;
    private char[] key;
    public RadixLeafNode(char[] key, T value) {
        this.key = key;
        this.value = value;
    }
    public RadixLeafNode(String key, T value) {
        this(key.toCharArray(), value);
    }
    public char[] getKey() {
        return key;
    }
    public T getValue() {
        return value;
    }
    public String toString() {
        return "{" + Arrays.toString(key) + ", "+value.toString()+"}";
    }
}
