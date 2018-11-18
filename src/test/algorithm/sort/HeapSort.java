package test.algorithm.sort;

import java.util.Arrays;

public class HeapSort {
    static <T extends Comparable<T>> T[] sort(T[] unsorted) {
        // It's a pity java does not allow allocating generic arrays
        buildHeap(unsorted, unsorted.length);
        //System.out.println("The heap now is "+ Arrays.toString(unsorted));
        flattenHeap(unsorted, unsorted.length);
        //System.out.println("result array = " + Arrays.toString(unsorted));
        return unsorted;
    }

    /**
     * Extend heap range from array[0:index] to [0:index+1] by inserting array[index] into the node
     * @param array the array of elements
     * @param index the node we are inserting, requiring array[0:index] is already a max heap.
     */
    private static <T extends Comparable<T>> void insertNode(T[] array, int index) {
        if(index == 0) { // root node
            return;
        }
        int parent = (index-1)/2;
        if(array[index].compareTo(array[parent]) > 0) { // array[index] > array[parent]
            Sort.swap(array, index, parent);
            insertNode(array, parent);
        }
    }

    /**
     * Rearrange the elements in array[0:length] into a max heap
     * @param array the unsorted array
     * @param length the length of elements
     */
    private static <T extends Comparable<T>> void buildHeap(T[] array, int length) {
        if(length <= 1) {
            return;
        }
        for(int i = 1; i < length; i++) {
            insertNode(array, i); // one by one inserting next element to the heap
        }
    }

    /**
     * Restore the heap order after removing the root node
     * @param array the array for the heap.
     * @param root the root that has been removed
     * @param length length for a heap area, means the max element in the heap is array[length-1]
     */
    private static <T extends Comparable<T>> void restoreHeap(T[] array, int root, int length) {
        //System.out.println("length = "+length+", array = " + Arrays.toString(array));
        int leftchild = root*2+1;
        int rightchild = root*2+2;
        if(rightchild < length) { // right child exists and is bigger than left child
            int subtree;
            if(array[leftchild].compareTo(array[rightchild]) < 0) {
                subtree = rightchild;
            } else {
                subtree = leftchild;
            }
            //System.out.println("subtree = "+array[subtree]+", root = " + array[root]);
            if(array[subtree].compareTo(array[root]) > 0) {
                Sort.swap(array, subtree, root);
                restoreHeap(array, subtree, length); // now restore this child's heap order
            }
        } else if(leftchild < length && array[leftchild].compareTo(array[root]) > 0) { // only the left child exists and is bigger than root
            //System.out.println("lefttree = "+array[leftchild]+", root = " + array[root]);
            Sort.swap(array, leftchild, root);
            restoreHeap(array, leftchild, length);
        }
    }
    /**
     * Every call of this function puts the root of the heap to the end of the array and
     * rebuild the heap, then recursively calls itself until length shrinks to 1.
     * @param heap the array containing the heap
     * @param length the length of the heap
     */
    private static <T extends Comparable<T>> void flattenHeap(T[] heap, int length) {
        if(length <= 1) {
            return;
        }
        //System.out.println("Swapping heap["+0+"]("+heap[0]+") with heap["+(length-1)+"]("+heap[length-1]+")");
        Sort.swap(heap, 0, length-1);
        restoreHeap(heap, 0, length-1);
        flattenHeap(heap, length-1);
    }
}
