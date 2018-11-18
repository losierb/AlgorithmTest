package test.algorithm.sort;

import java.util.Arrays;

public class InsertionSort {
    static <T extends Comparable<T>> T[] sort(T[] unsorted) {
        T[] result = unsorted;
        for(int i = 1; i < result.length; i++) { // start with the leftmost element as the initial sorted array.
            T item = result[i];
            int j;
            for(j = i-1; j >= 0; j--) { // insert result[i] into result[0:i-]
                if(item.compareTo(result[j]) < 0) {
                    result[j+1] = result[j]; // move this element to right
                } else {
                    break;
                }
            }
            result[j+1] = item;
        }
        return result;
    }
}
