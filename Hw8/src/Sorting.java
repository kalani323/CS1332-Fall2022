import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Kalani Dissanayake
 * @version 1.0
 * @userid kdissanayake3
 * @GTID 903761395
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class Sorting {

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Null parameters, array or comparator");
        }
        for (int i = 1; i < arr.length; i++) {
            T temp = arr[i];
            int j = i - 1;
            while (j >= 0 && comparator.compare(arr[j], temp) > 0) {
                arr[j + 1] = arr[j--];
            }
            arr[j + 1] = temp;
        }
    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Null parameters, array or comparator");
        }
        boolean swapped = true;
        int s = 0;
        int e = arr.length - 1;
        while (s < e && swapped) {
            swapped = false;
            int tempE = 0;
            for (int k = s; k < e; k++) {
                if (comparator.compare(arr[k], arr[k + 1]) > 0) {
                    T temp = arr[k];
                    arr[k] = arr[k + 1];
                    arr[k + 1] = temp;
                    swapped = true;
                    tempE = k;
                }
            }
            e = tempE;
            if (swapped) {
                swapped = false;
                int tempS = 0;
                for (int k = e; k > s; k--) {
                    if (comparator.compare(arr[k], arr[k - 1]) < 0) {
                        T temp = arr[k];
                        arr[k] = arr[k - 1];
                        arr[k - 1] = temp;
                        swapped = true;
                        tempS = k;
                    }
                }
                s = tempS;
            }
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Null parameters, array or comparator");
        }
        if (arr.length > 1) {
            T[] arrayL = (T[]) new Object[arr.length / 2];
            T[] arrayR = (T[]) new Object[arr.length - arrayL.length];
            for (int i = 0; i < arrayL.length; i++) {
                arrayL[i] = arr[i];
            }
            for (int i = 0; i < arrayR.length; i++) {
                arrayR[i] = arr[i + arrayL.length];
            }
            mergeSort(arrayL, comparator);
            mergeSort(arrayR, comparator);
            mergeHelper(arr, comparator, arrayL, arrayR);
        }
    }

    /**
     * merges arrays
     * @param arr - array to be sorted
     * @param comparator - comparatoe used to compare values
     * @param arrayL - left array
     * @param arrayR - right array
     * @param <T> - data type to sort
     */
    private static <T> void mergeHelper(T[] arr, Comparator<T> comparator, T[] arrayL, T[] arrayR) {
        int i = 0;
        int j = 0;
        for (int k = 0; k < arr.length; k++) {
            if (j >= arrayR.length || i < arrayL.length && comparator.compare(arrayL[i], arrayR[j]) <= 0) {
                arr[k] = arrayL[i++];
            } else {
                arr[k] = arrayR[j++];
            }
        }
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException("Null parameters, array, comparator, or rand");
        }
        quickSortHelper(arr, 0, arr.length - 1, comparator, rand);
    }

    /**
     * quick sort helper method
     * @param arr - array to sort
     * @param start - starting index
     * @param end - ending index
     * @param comparator - comparator used to compare
     * @param rand - random object
     * @param <T> - data type to sort
     */
    private static <T> void quickSortHelper(T[] arr, int start, int end, Comparator<T> comparator,
                                            Random rand) {
        if (end - start < 1) {
            return;
        }
        int pIndex = rand.nextInt(end - start + 1) + start;
        T pivot = arr[pIndex];
        arr[pIndex] = arr[start];
        arr[start] = pivot;
        int lIndex = start + 1;
        int rIndex = end;
        while (lIndex <= rIndex) {
            while (lIndex <= rIndex && comparator.compare(arr[lIndex], arr[start]) <= 0) {
                lIndex++;
            }
            while (lIndex <= rIndex && comparator.compare(arr[rIndex], arr[start]) >= 0) {
                rIndex--;
            }
            if (lIndex <= rIndex) {
                T toSwap = arr[lIndex];
                arr[lIndex] = arr[rIndex];
                arr[rIndex] = toSwap;
                lIndex++;
                rIndex--;
            }
        }
        T toSwap = arr[start];
        arr[start] = arr[rIndex];
        arr[rIndex] = toSwap;
        quickSortHelper(arr, start, rIndex - 1, comparator, rand);
        quickSortHelper(arr, rIndex + 1, end, comparator, rand);
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need. The number of iterations
     * can be determined using the number with the largest magnitude.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Null array provided");
        }
        LinkedList<Integer>[] buckets = new LinkedList[19];
        for (int i = 0; i < 19; i++) {
            buckets[i] = new LinkedList<>();
        }
        int mod = 10;
        int div = 1;
        boolean continuing = true;
        while (continuing) {
            continuing = false;
            for (int value : arr) {
                int bucket = value / div;
                if (bucket / 1 != 0) {
                    continuing = true;
                }
                if (buckets[bucket % mod + 9] == null) {
                    buckets[bucket % mod + 9] = new LinkedList<>();
                }
                buckets[bucket % mod + 9].add(value);
            }
            int arrIndex = 0;
            for (LinkedList<Integer> bucket : buckets) {
                if (bucket != null) {
                    for (int value : bucket) {
                        arr[arrIndex++] = value;
                    }
                    bucket.clear();
                }
            }
            div *= 10;
        }
    }

    /**
     * Implement heap sort.
     *
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     *
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     *
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        PriorityQueue<Integer> heap = new PriorityQueue<>(data);
        int[] result = new int[heap.size()];
        int count = 0;
        while (!heap.isEmpty()) {
            result[count++] = heap.remove();
        }
        return result;
    }
}
