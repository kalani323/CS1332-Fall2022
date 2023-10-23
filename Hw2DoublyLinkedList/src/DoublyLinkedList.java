import java.util.NoSuchElementException;

/**
 * Your implementation of a non-circular DoublyLinkedList with a tail pointer.
 *
 * @author kalani dissanayake
 * @version 1.0
 * @userid kdissanayake3
 * @GTID 903761395
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class DoublyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index. Don't forget to consider whether
     * traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("The index given should not be negative or greater than size.");
        }
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        if (index == 0) {
            addToFront(data);
        } else if (index == size) {
            addToBack(data);
        } else {
            DoublyLinkedListNode<T> curr = head;
            for (int i = 0; i < index - 1; i++) {
                curr = curr.getNext();
            }
            DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<>(data);
            newNode.setPrevious(curr);
            newNode.setNext(curr.getNext());
            curr.getNext().setPrevious(newNode);
            curr.setNext(newNode);
            size++;
        }
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        if (head == null) {
            head = new DoublyLinkedListNode<>(data, null, null);
            tail = head;
        } else {
            DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode(data, null, head);
            head.setPrevious(newNode);
            head = newNode;
        }
        size++;
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        if (head == null) {
            head = new DoublyLinkedListNode(data, null, null);
            tail = head;
        } else {
            DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode(data, tail, null);
            tail.setNext(newNode);
            tail = newNode;
        }
        size++;
    }

    /**
     * Removes and returns the element at the specified index. Don't forget to
     * consider whether traversing the list from the head or tail is more
     * efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index given should not be negative, "
                    + "greater than, or equal to size.");
        }
        if (index == 0) {
            return removeFromFront();
        } else if (index == size - 1) {
            return removeFromBack();
        } else {
            DoublyLinkedListNode<T> curr = head;
            for (int i = 0; i < index; i++) {
                curr = curr.getNext();
            }
            DoublyLinkedListNode<T> previousNode = curr.getPrevious();
            DoublyLinkedListNode<T> nextNode = curr.getNext();
            previousNode.setNext(nextNode);
            nextNode.setPrevious(previousNode);
            size--;
            return curr.getData();
        }
    }

    /**
     * Removes and returns the first element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (head == null) {
            throw new NoSuchElementException("The list is empty");
        }
        if (size == 1) {
            DoublyLinkedListNode<T> toRemove = head;
            head = null;
            tail = null;
            size--;
            return toRemove.getData();
        }
        DoublyLinkedListNode<T> toRemove = head;
        DoublyLinkedListNode<T> tempNode = head.getNext();
        tempNode.setPrevious(null);
        head = tempNode;
        size--;
        return toRemove.getData();
    }

    /**
     * Removes and returns the last element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (head == null) {
            throw new NoSuchElementException("The list is empty");
        }
        if (size == 1) {
            DoublyLinkedListNode<T> toRemove = head;
            head = null;
            tail = null;
            size--;
            return toRemove.getData();
        }
        DoublyLinkedListNode<T> toRemove = tail;
        DoublyLinkedListNode<T> tempNode = tail.getPrevious();
        tempNode.setNext(null);
        tail = tempNode;
        size--;
        return toRemove.getData();
    }

    /**
     * Returns the element at the specified index. Don't forget to consider
     * whether traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index given should not be negative, "
                    + "greater than, or equal to size.");
        }
        if (index == 0) {
            return head.getData();
        } else if (index == size - 1) {
            return tail.getData();
        } else {
            DoublyLinkedListNode<T> curr = head;
            for (int i = 0; i < index; i++) {
                curr = curr.getNext();
            }
            return curr.getData();
        }
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(1) if data is in the tail and O(n) for all other cases.
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        if (size == 0) {
            throw new NoSuchElementException("Data cannot be found because list is empty");
        } else {
            DoublyLinkedListNode<T> curr = tail;
            if (curr.getData().equals(data)) {
                if (size != 1) {
                    tail.getPrevious().setNext(null);
                    tail = tail.getPrevious();
                    size--;
                    return curr.getData();
                } else {
                    tail = null;
                    head = null;
                    size--;
                    return curr.getData();
                }
            } else {
                for (int i = size; i > 1; i--) {
                    if (curr.getData().equals(data)) {
                        curr.getPrevious().setNext(curr.getNext());
                        curr.getNext().setPrevious(curr.getPrevious());
                        size--;
                        return curr.getData();
                    }
                    curr = curr.getPrevious();
                }
                if (curr.getData().equals(data)) {
                    head.getNext().setPrevious(null);
                    head = head.getNext();
                    size--;
                    return curr.getData();
                } else {
                    throw new NoSuchElementException("Data cannot be found");
                }
            }
        }
    }

    /**
     * Returns an array representation of the linked list. If the list is
     * size 0, return an empty array.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length size holding all of the objects in the
     * list in the same order
     */
    public Object[] toArray() {
        if (size == 0) {
            return new Object[0];
        } else {
            Object[] newArray = new Object[size];
            DoublyLinkedListNode<T> curr = head;
            for (int i = 0; i < size; i++) {
                newArray[i] = curr.getData();
                curr = curr.getNext();
            }
            return newArray;
        }
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public DoublyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public DoublyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
