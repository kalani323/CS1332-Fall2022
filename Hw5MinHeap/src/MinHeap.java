import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MinHeap.
 *
 * @author Kalani Dissanyake
 * @version 1.0
 * @userid kdissanayake3
 * @GTID 903761395
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MinHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     * To initialize the backing array, create a Comparable array and then cast
     * it to a T array.
     */
    public MinHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * size of the passed in ArrayList (not INITIAL_CAPACITY). Index 0 should
     * remain empty, indices 1 to n should contain the data in proper order, and
     * the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MinHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("The list is null");
        }
        backingArray = (T[]) new Comparable[2 * data.size() + 1];
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) == null) {
                throw new IllegalArgumentException("The list contains null data");
            }
            backingArray[i + 1] = data.get(i);
        }
        size = data.size();
        int ind = (size / 2);
        for (int i = ind; i > 0; i--) {
            T element =  backingArray[i];
            downheap(element, i, i * 2, i * 2 + 1);
        }
    }

    /**
     * preforms down heap operation
     * @param item - item to move down the min heap
     * @param i - index of item
     * @param l - index of items right child
     * @param r - index of left child
     */
    private void downheap(T item, int i, int l, int r) {
        // end of heap
        if (i == size || l >= backingArray.length || r >= backingArray.length) {
            return;
        }
        // bottom level
        if (backingArray[l] == null && backingArray[r] == null) {
            return;
        }
        //one child
        if (backingArray[l] == null) {
            if (backingArray[i].compareTo(backingArray[r]) > 0) {
                T temp = backingArray[i];
                backingArray[i] = backingArray[r];
                backingArray[r] = temp;
            }
            return;
        } else if (backingArray[r] == null) {
            if (backingArray[i].compareTo(backingArray[l]) > 0) {
                T temp = backingArray[i];
                backingArray[i] = backingArray[l];
                backingArray[l] = temp;
            }
        }
        if ((backingArray[i].compareTo(backingArray[l]) < 0) && (backingArray[i].compareTo(backingArray[r]) < 0)) {
            return;
        } else {
            if (backingArray[r].compareTo(backingArray[l]) > 0) {
                T temp = backingArray[i];
                backingArray[i] = backingArray[l];
                backingArray[l] = temp;
                i = l;
            } else {
                T temp = backingArray[i];
                backingArray[i] = backingArray[r];
                backingArray[r] = temp;
                i = r;
            }
            downheap(item, i, i * 2, i * 2 + 1);
        }
    }

    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     * The order property of the heap must be maintained after adding. You can
     * assume that no duplicate data will be passed in.
     * 
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Unable to add null data");
        } else if (size == backingArray.length - 1) {
            T[] newArray = (T[]) new Comparable[backingArray.length * 2];
            for (int i = 0; i <= size; i++) {
                newArray[i] = backingArray[i];
            }
            backingArray = newArray;
        }
        int dIndex = size + 1;
        backingArray[dIndex] = data;
        int pIndex = ((size + 1) / 2);
        if (dIndex > 1) {
            upheap(data, pIndex, dIndex);
        }
        size++;
    }

    /**
     * preforms up heap operation
     * @param element - data to add to heap
     * @param pIndex - index of parent
     * @param dIndex - index of element
     */
    private void upheap(T element, int pIndex, int dIndex) {
        if (pIndex == 0) {
            return;
        }
        if (backingArray[pIndex].compareTo(element) < 0) {
            return;
        } else if (backingArray[pIndex].compareTo(element) > 0) {
            T temp = backingArray[pIndex];
            backingArray[pIndex] = backingArray[dIndex];
            backingArray[dIndex] = temp;
            upheap(element, pIndex / 2, dIndex / 2);
        }
    }
    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     * The order property of the heap must be maintained after removing.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (size == 0) {
            throw new NoSuchElementException("heap is empty");
        }
        T elementRemoved = backingArray[1];
        backingArray[1] = backingArray[size];
        downheap(backingArray[1], 1, 2, 3);
        backingArray[size] = null;
        size--;
        return elementRemoved;
    }

    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("heap is empty");
        }
        return backingArray[1];
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        size = 0;
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
