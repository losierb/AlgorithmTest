package test.algorithm.sort;

class QuickSort {
    static <T extends Comparable<T>> T[] sort(T[] unsorted) {
        T[] result = unsorted.clone();
        sortPartition(result, 0, result.length - 1);
        return result;
    }

    /**
     * Sort the interval of [low, high] of unsorted list
     *
     * @param unsorted the unsorted list
     * @param low      lower limit of the index (included)
     * @param high     upper limit of the index (included)
     * @param <T>      a Comparable
     */
    private static <T extends Comparable<T>> void sortPartition(T[] unsorted, int low, int high) {
        int left = low + 1, right = high;
        if (left > right) { // no need to sort one element
            return;
        }
        T pivot = unsorted[low]; // pivot
        while (true) {
            // swap unsorted[left] and unsorted[right] when spotted unsorted[left]>pivot and unsorted[right]<pivot
            while (left <= high && unsorted[left].compareTo(pivot) <= 0) {
                left++;
            }
            while (right > low && unsorted[right].compareTo(pivot) > 0) {
                right--;
            }
            if (left >= right) {
                break;
            }
            Sort.swap(unsorted, left, right);
        }
        // at this stage, we swap unsorted[low] and unsorted[right], so every element in [low, right-1] will be smaller than every element in [right+1, high]
        // so recursively sort the two partitions
        if (right > low) {
            Sort.swap(unsorted, low, right);
        }
        sortPartition(unsorted, low, right - 1);
        sortPartition(unsorted, right + 1, high);
    }
}
