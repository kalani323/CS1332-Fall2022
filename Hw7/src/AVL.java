import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL.
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
public class AVL<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("data cant be null");
        }
        size = 0;
        for (T item : data) {
            add(item);
        }
    }

    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     * 
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data cant be null");
        }
        root = addHelperMethod(data, root);
    }

    /**
     * recursive helper method for adding
     * @param data - data to be added
     * @param node - node to check
     * @return new node with data
     */
    private AVLNode<T> addHelperMethod(T data, AVLNode<T> node) {
        if (node == null) {
            size++;
            return new AVLNode<>(data);
        } else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(addHelperMethod(data, node.getLeft()));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(addHelperMethod(data, node.getRight()));
        }
        updateBFH(node);
        if (Math.abs(node.getBalanceFactor()) > 1) {
            node = balance(node);
        }
        return node;
    }

    /**
     * updates balance factor and height
     * @param node - the node whose BF and height must be updated
     */
    private void updateBFH(AVLNode<T> node) {
        int left = nodeHeight(node.getLeft());
        int right = nodeHeight(node.getRight());
        node.setBalanceFactor(left - right);
        node.setHeight(1 + Math.max(left, right));
    }

    /**
     * gets height of node
     * @param node - node to get height for
     * @return height of node
     */
    private int nodeHeight(AVLNode<T> node) {
        if (node == null) {
            return -1;
        }
        return node.getHeight();
    }

    /**
     * balances tree
     * @param node - root of subtree to balance
     * @return the new root of the subtree
     */
    private AVLNode<T> balance(AVLNode<T> node) {
        AVLNode<T> nodeToReturn = node;
        if (node.getBalanceFactor() > 0) {
            // left heavy
            if (node.getLeft().getBalanceFactor() < 0) {
                // left-right rotation
                node.setLeft(rotateLeft(node.getLeft()));
                nodeToReturn = rotateRight(node);
            } else {
                //right rotation
                nodeToReturn = rotateRight(node);
            }
        } else if (node.getBalanceFactor() < 0) {
            // right heavy
            if (node.getRight().getBalanceFactor() > 0) {
                // rotate right-left
                node.setRight(rotateRight(node.getRight()));
                nodeToReturn = rotateLeft(node);
            } else {
                // rotate left
                nodeToReturn = rotateLeft(node);
            }
        }
        return nodeToReturn;
    }

    /**
     * preforms a left rotation
     * @param node - root of subtree to rotate
     * @return root of updated subtree
     */
    private AVLNode<T> rotateLeft(AVLNode<T> node) {
        AVLNode<T> right = node.getRight();
        node.setRight(right.getLeft());
        right.setLeft(node);
        updateBFH(node);
        updateBFH(right);
        return right;
    }

    /**
     * preforms a right rotation
     * @param node - root of subtree to rotate
     * @return root of updated subtree
     */
    private AVLNode<T> rotateRight(AVLNode<T> node) {
        AVLNode<T> left = node.getLeft();
        node.setLeft(left.getRight());
        left.setRight(node);
        updateBFH(node);
        updateBFH(left);
        return left;
    }


    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data, NOT successor. As a reminder, rotations can occur
     * after removing the predecessor node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data cant be null");
        }
        AVLNode<T> toRemoveRef = new AVLNode(null);
        root = removeHelperMethod(data, root, toRemoveRef);
        size--;
        return toRemoveRef.getData();
    }

    /**
     * recursively finds node to remove
     * @param data - data to remove
     * @param node - node to check for data
     * @param toRemoveRef - reference w/ removed data
     * @return node containing data
     */
    private AVLNode<T> removeHelperMethod(T data, AVLNode<T> node, AVLNode<T> toRemoveRef) {
        if (node == null) {
            throw new NoSuchElementException("data was not found");
        }
        if (data.compareTo(node.getData()) < 0) {
            node.setLeft(removeHelperMethod(data, node.getLeft(), toRemoveRef));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(removeHelperMethod(data, node.getRight(), toRemoveRef));
        } else {
            if (toRemoveRef.getData() == null) {
                toRemoveRef.setData(node.getData());
            }
            if (node.getLeft() == null) {
                return node.getRight();
            } else if (node.getRight() == null) {
                node.getLeft();
            }
            node.setData(predecessor(node));
            node.setLeft(removeHelperMethod(node.getData(), node.getLeft(), toRemoveRef));
        }
        updateBFH(node);
        if (Math.abs(node.getBalanceFactor()) > 1) {
            node = balance(node);
        }
        return node;

    }

    /**
     * returns the data in the predecessor node
     * @param node - node being removed
     * @return - dtaa of nodes predecessor
     */
    private T predecessor(AVLNode<T> node) {
        node = node.getLeft();
        T data = node.getData();
        while (node.getRight() != null) {
            data = node.getRight().getData();
            node = node.getRight();
        }
        return data;
    }

    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data cant be null");
        }
        return getHelperMethod(data, root);
    }

    /**
     * recursive helper for get
     * @param data - data to search for
     * @param node - node to check for data
     * @return - data if it was found in the node
     */
    private T getHelperMethod(T data, AVLNode<T> node) {
        if (node == null) {
            throw new NoSuchElementException("data was not found");
        }
        if (node.getData().equals(data)) {
            return node.getData();
        } else {
            if (data.compareTo(node.getData()) < 0) {
                return getHelperMethod(data, node.getLeft());
            } else {
                return getHelperMethod(data, node.getRight());
            }
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree.
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data cant be null");
        }
        return containsHelperMethod(data, root);
    }

    /**
     * recursive helper for contains
     * @param data - data to search for
     * @param node - node to examine
     * @return - true or false, if data is in avl
     */
    private boolean containsHelperMethod(T data, AVLNode<T> node) {
        if (node == null) {
            return false;
        }
        if (node.getData().equals(data)) {
            return true;
        } else {
            if (data.compareTo(node.getData()) < 0) {
                return containsHelperMethod(data, node.getLeft());
            } else {
                return containsHelperMethod(data, node.getRight());
            }
        }
    }

    /**
     * Returns the height of the root of the tree.
     * 
     * Should be O(1). 
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return nodeHeight(root);
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the data in the deepest node. If there is more than one node
     * with the same deepest depth, return the rightmost (i.e. largest) node with 
     * the deepest depth. 
     * 
     * Should be recursive. 
     *
     * Must run in O(log n) for all cases.
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      3
     *        \
     *         1
     * Max Deepest Node:
     * 1 because it is the deepest node
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      4
     *        \    /
     *         1  3
     * Max Deepest Node:
     * 3 because it is the maximum deepest node (1 has the same depth but 3 > 1)
     *
     * @return the data in the maximum deepest node or null if the tree is empty
     */
    public T maxDeepestNode() {
        if (size == 0) {
            return null;
        }
        return maxDeepestNodeHelperMethod(root);
    }

    /**
     * recursive helper method for maxDeepestNodeHelperMethod
     * @param node - root of subtree
     * @return - data of deepest node
     */
    private T maxDeepestNodeHelperMethod(AVLNode<T> node) {
        if (node.getLeft() == null && node.getRight() == null) {
            return node.getData();
        } else {
            int left = nodeHeight(node.getLeft());
            int right = nodeHeight(node.getRight());
            if (left > right) {
                return maxDeepestNodeHelperMethod(node.getLeft());
            } else {
                return maxDeepestNodeHelperMethod(node.getRight());
            }
        }
    }

    /**
     * In BSTs, you learned about the concept of the successor: the
     * smallest data that is larger than the current data. However, you only
     * saw it in the context of the 2-child remove case.
     *
     * This method should retrieve (but not remove) the successor of the data
     * passed in. There are 2 cases to consider:
     * 1: The right subtree is non-empty. In this case, the successor is the
     * leftmost node of the right subtree.
     * 2: The right subtree is empty. In this case, the successor is the lowest
     * ancestor of the node whose left subtree contains the data. 
     * 
     * The second case means the successor node will be one of the node(s) we 
     * traversed left from to find data. Since the successor is the SMALLEST element 
     * greater than data, the successor node is the lowest/last node 
     * we traversed left from on the path to the data node.
     *
     * This should NOT be used in the remove method.
     * 
     * Should be recursive. 
     *
     * Ex:
     * Given the following AVL composed of Integers
     *                    76
     *                  /    \
     *                34      90
     *                  \    /
     *                  40  81
     * successor(76) should return 81
     * successor(81) should return 90
     * successor(40) should return 76
     *
     * @param data the data to find the successor of
     * @return the successor of data. If there is no larger data than the
     * one given, return null.
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T successor(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        if (!contains(data)) {
            throw new NoSuchElementException("data is not in tree");
        }
        // FIGURE OUT HOW TO SOLVE
        return null;
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
