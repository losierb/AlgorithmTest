package test.algorithm.sort;

class BubbleSort {
    static <T extends Comparable<T>> T[] sort(T[] unsorted) {
        T[] result = unsorted.clone();
        for (int i = 0; i < result.length; i++) {
            boolean swapped = false;
            for (int j = 0; j < result.length - 1 - i; j++) {
                if (result[j].compareTo(result[j + 1]) > 0) {
                    swapped = true;
                    Sort.swap(result, j, j + 1);
                }
            }
            if (!swapped) { // no swap, means sorted
                break;
            }
        }
        return result;
    }
}
