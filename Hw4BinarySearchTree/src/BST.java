import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Your implementation of a BST.
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
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Unable to add null collection");
        }
        for (T node: data) {
            if (node == null) {
                throw new IllegalArgumentException("Unable to read null data in collection");
            }
            add(node);
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Unable to add null data");
        }
        root = addHelperMethod(data, root);
        size++;
    }

    /**
     * recursive method that helps the add method
     * @param data - data to be added into BST
     * @param node - the node being compared to
     * @return root node that links the bst
     */
    private BSTNode<T> addHelperMethod(T data, BSTNode<T> node) {
        if (node == null) {
            return new BSTNode<T>(data);
        } else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(addHelperMethod(data, node.getLeft()));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(addHelperMethod(data, node.getRight()));
        }
        return node;
    }
    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Unable to remove null data");
        }
        BSTNode<T> referenceToRemove = new BSTNode(null);
        root = removeHelperMethod(data, root, referenceToRemove);
        size--;
        T referenceToRemoveData = referenceToRemove.getData();
        return referenceToRemoveData;
    }

    /**
     * recursive method that helps the remove method by traversing to find the node
     * @param data - data to remove
     * @param node - node to be compared to
     * @param referenceToRemove - reference to dummy node containing removed data
     * @return node with data
     */
    private BSTNode<T> removeHelperMethod(T data, BSTNode<T> node, BSTNode<T> referenceToRemove) {
        if (node == null) {
            throw new NoSuchElementException("data was not present in the BST");
        }
        T nodesData = node.getData();
        if (data.compareTo(nodesData) < 0) {
            node.setLeft(removeHelperMethod(data, node.getLeft(), referenceToRemove));
        } else if (data.compareTo(nodesData) > 0) {
            node.setRight(removeHelperMethod(data, node.getRight(), referenceToRemove));
        } else {
            // if no child or one child
            if (referenceToRemove.getData() == null) {
                referenceToRemove.setData(node.getData());
            }
            if (node.getLeft() == null) {
                return node.getRight();
            } else if (node.getRight() == null) {
                return node.getLeft();
            }
            // if 2 children
            node.setData(getSuccessor(node.getRight()));
            node.setRight(removeHelperMethod(node.getData(), node.getRight(), referenceToRemove));
        }
        return node;
    }

    /**
     * returns the successor of the node
     * @param node - starting point from which the method starts to find the successor
     * @return data from the successor node
     */
    private T getSuccessor(BSTNode<T> node) {
        T nodesData = node.getData();
        while (node.getLeft() != null) {
            nodesData = node.getLeft().getData();
            node = node.getLeft();
        }
        return nodesData;
    }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Unable to get null data collection");
        }
        return getHelperMethod(data, root);
    }

    /**
     * recursive helper method to help get
     * @param data - data we want to get (looking for)
     * @param node - node to compare to
     * @return data in the BST that is equal to the data that was passed in
     */
    private T getHelperMethod(T data, BSTNode<T> node) {
        T nodesData = node.getData();
        if (node == null) {
            throw new IllegalArgumentException("cant find data in the tree");
        } else {
            if (data.compareTo(nodesData) < 0) {
                return getHelperMethod(data, node.getLeft());
            } else if (data.compareTo(nodesData) > 0) {
                return getHelperMethod(data, node.getRight());
            }
        }
        return nodesData;
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        return containsHelperMethod(data, root);
    }

    /**
     * recursive method that helps the contain method
     * @param data - data to search for
     * @param node - node we compare to
     * @return true if data is in bst or false if data is no
     */
    private boolean containsHelperMethod(T data, BSTNode<T> node) {
        T nodesData = node.getData();
        if (node == null) {
            return false;
        } else if (nodesData.equals(data)) {
            return true;
        } else {
            if (data.compareTo(nodesData) > 0) {
                return containsHelperMethod(data, node.getRight());
            } else {
                return containsHelperMethod(data, node.getLeft());
            }
        }
    }
    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the pre-order traversal of the tree
     */
    public List<T> preorder() {
        List<T> preorderList = new ArrayList<>();
        return preorderHelperMethod(root, preorderList);
    }

    /**
     * recursive method to help the preorder method
     * @param node -  node to examine for data
     * @param list - list in which we add data to
     * @return list - containing data in the bst
     */
    private List<T> preorderHelperMethod(BSTNode<T> node, List<T> list) {
        if (node == null) {
            return list;
        } else {
            list.add(node.getData());
            preorderHelperMethod(node.getLeft(), list);
            preorderHelperMethod(node.getRight(), list);
        }
        return list;
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the in-order traversal of the tree
     */
    public List<T> inorder() {
        List<T> inorderList = new ArrayList<>();
        return inorderHelperMethod(root, inorderList);
    }

    /**
     * recursive method to help the inorder method
     * @param node -  node to examine for data
     * @param list - list in which we add data to
     * @return list - containing data in the bst
     */
    private List<T> inorderHelperMethod(BSTNode<T> node, List<T> list) {
        if (node == null) {
            return list;
        } else {
            inorderHelperMethod(node.getLeft(), list);
            list.add(node.getData());
            inorderHelperMethod(node.getRight(), list);
        }
        return list;
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the post-order traversal of the tree
     */
    public List<T> postorder() {
        List<T> postorderList = new ArrayList<>();
        return postorderHelperMethod(root, postorderList);
    }

    /**
     * recursive method to help the postorder method
     * @param node -  node to examine for data
     * @param list - list in which we add data to
     * @return list - containing data in the bst
     */
    private List<T> postorderHelperMethod(BSTNode<T> node, List<T> list) {
        if (node == null) {
            return list;
        } else {
            postorderHelperMethod(node.getLeft(), list);
            postorderHelperMethod(node.getRight(), list);
            list.add(node.getData());
        }
        return list;
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level-order traversal of the tree
     */
    public List<T> levelorder() {
        List<T> levelOrderedList = new ArrayList<T>();
        LinkedList<BSTNode<T>> elements = new LinkedList<>();
        elements.addLast(root);
        while (!elements.isEmpty()) {
            BSTNode<T> node = elements.removeFirst();
            if (node != null) {
                levelOrderedList.add(node.getData());
                elements.addLast(node.getLeft());
                elements.addLast(node.getRight());
            }
        }
        return levelOrderedList;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return heightHelperMethod(root);
    }

    /**
     * a recursive method to help the height method
     * @param node - node we are checking
     * @return an int representing the height of the current node
     */
    private int heightHelperMethod(BSTNode<T> node) {
        if (node == null) {
            return -1;
        } else {
            return (1 + Math.max(heightHelperMethod(node.getRight()), heightHelperMethod(node.getLeft())));
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Generates a list of the max data per level from the top to the bottom
     * of the tree. (Another way to think about this is to get the right most
     * data per level from top to bottom.)
     * 
     * This must be done recursively.
     *
     * This list should contain the last node of each level.
     *
     * If the tree is empty, an empty list should be returned.
     *
     * Ex:
     * Given the following BST composed of Integers
     *      2
     *    /   \
     *   1     4
     *  /     / \
     * 0     3   5
     * getMaxDataPerLevel() should return the list [2, 4, 5] - 2 is the max
     * data of level 0, 4 is the max data of level 1, and 5 is the max data of
     * level 2
     *
     * Ex:
     * Given the following BST composed of Integers
     *               50
     *           /        \
     *         25         75
     *       /    \
     *      12    37
     *     /  \    \
     *   11   15   40
     *  /
     * 10
     * getMaxDataPerLevel() should return the list [50, 75, 37, 40, 10] - 50 is
     * the max data of level 0, 75 is the max data of level 1, 37 is the
     * max data of level 2, etc.
     *
     * Must be O(n).
     *
     * @return the list containing the max data of each level
     */
    public List<T> getMaxDataPerLevel() {
        List<T> maxDataPerLevel = new ArrayList<T>();
        if (root == null) {
            return maxDataPerLevel;
        }
        getMaxDataPerLevelHelper(root, 0, maxDataPerLevel);
        return maxDataPerLevel;
    }

    /**
     * helper method to getMaxDataPerLevel method
     * @param node -  node to examine for data
     * @param depth - depth of the bst
     * @param list - list were adding values to
     */
    private void getMaxDataPerLevelHelper(BSTNode<T> node, int depth, List<T> list) {
        if (node == null) {
            return;
        }
        if (list.size() == depth) {
            list.add(node.getData());
        } else {
            if (node.getData().compareTo(list.get(depth)) > 0) {
                list.set(depth, node.getData());
            }
        }
        getMaxDataPerLevelHelper(node.getRight(), depth + 1, list);
        getMaxDataPerLevelHelper(node.getLeft(), depth + 1, list);
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
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
