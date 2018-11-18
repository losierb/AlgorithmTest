package test.algorithm.sort;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Function;

public class Sort {
    /**
     * General function for swapping array[a] with array[b], so we don't need to write the same stereotype on every sort algorithm.
     */
    static <T> void swap(T[] array, int a, int b) {
        T tmp = array[a];
        array[a] = array[b];
        array[b] = tmp;
    }

    private static void testSort(String label, Function<Integer[], Integer[]> function, Integer[] array) {
        Integer[] actual_result = array.clone();
        Arrays.sort(actual_result);
        long start = System.nanoTime();
        Integer[] result = function.apply(array.clone());
        long duration = System.nanoTime() - start;
        System.out.println(label + " test result: " + Arrays.equals(result, actual_result) + ", duration: " + duration + "ns");
    }

    public static void main(String[] args) {
        Random rand = new Random();
        Integer[] array = new Integer[500];
        for (int i = 0; i < array.length; i++) {
            array[i] = rand.nextInt(1000);
        }
        System.out.println("unsorted array = " + Arrays.toString(array));
        testSort("Java builtin sort", (Integer[] arr) -> {
            Arrays.sort(arr);
            return arr;
        }, array);
        testSort("Bubble sort", BubbleSort::sort, array);
        testSort("Quick sort", QuickSort::sort, array);
        testSort("Merge sort", MergeSort::sort, array);
        testSort("Insertion sort", InsertionSort::sort, array);
        testSort("Shell sort", ShellSort::sort, array);
    }
}
