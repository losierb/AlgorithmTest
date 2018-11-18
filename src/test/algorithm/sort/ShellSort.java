package test.algorithm.sort;

public class ShellSort {
    static <T extends Comparable<T>> T[] sort(T[] unsorted) {
        int len = unsorted.length;
        int gap = 1; // gap for each element in a sublist
        while (gap < len/3) {
            gap = 3*gap+1; // 1, 4, 13, 40, 121, ...
        }
        while (gap >= 1) {
            for (int i = gap; i < len; i++) { // sort each sublist (insertion sort approach)
                T cur = unsorted[i];
                int j = i-gap;
                while (j >= 0 && unsorted[j].compareTo(cur) > 0) { // sort a sublist
                    unsorted[j+gap] = unsorted[j];
                    j = j-gap;
                }
                unsorted[j+gap] = cur;
            }
            gap = (gap-1)/3; // tightening the gap
        }
        return unsorted;
    }
}
