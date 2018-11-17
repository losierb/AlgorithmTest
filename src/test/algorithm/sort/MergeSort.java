package test.algorithm.sort;

class MergeSort {
    static <T extends Comparable<T>> T[] sort(T[] unsorted) {
        // Merge sort requires
        T[] result = unsorted.clone();
        merge(unsorted, result, 0, result.length);
        return result;
    }

    /**
     * Sort the interval of [low, high) of unsorted list
     * Merge sort means we divide the list into half and recursively sort each sub-list,
     * then we merge the two sorted list.
     *
     * @param unsorted the input array
     * @param result   the output array
     * @param low      the lower bound of the specified index range
     * @param high     the upper bound of the specified index range
     */
    private static <T extends Comparable<T>> void merge(T[] unsorted, T[] result, int low, int high) {
        if (high - low <= 1) { // only one element, consider sorted
            return;
        }
        int middle = (low + high) / 2;
        // intentionally switched result and unsorted in arguments.
        // because we should have let unsorted contain the merged sub-list
        // so we can merge it to result
        merge(result, unsorted, low, middle);
        merge(result, unsorted, middle, high);
        // at this stage, unsorted contains two sorted lists.
        int left = low, right = middle;
        while (left < middle && right < high) {
            if (unsorted[left].compareTo(unsorted[right]) < 0) {
                result[low++] = unsorted[left++];
            } else {
                result[low++] = unsorted[right++];
            }
        }
        if (left < middle) {
            do {
                result[low++] = unsorted[left++];
            } while (low < high);
        } else if (right < high) {
            do {
                result[low++] = unsorted[right++];
            } while (low < high);
        }
    }
}
