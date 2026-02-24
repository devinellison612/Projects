public class BST<I, T>{

    class BSTNode {
        private I index;
        private T data;
        private BSTNode left;
        private BSTNode right;

        /**
         * Default constructor. Sets all instance variables to be null.
         */
        public BSTNode() {
            index = null;
            data = null;
        }

        /**
         * Constructor. Sets data and index to be _data and _index respectively.
         */
        public BSTNode(I _index, T _data) {
            index = _index;
            data = _data;
        }

        /**
         * Returns the index stored in this node.
         */
        public I getIndex() {
            return index;
        }

        /**
         * Returns the data stored in this node.
         */
        public T getData() {
            return data;
        }

        /**
         * Updates the data in this node to the specified value.
         */
        public void setData(T d) {
            data = d;
        }

        /**
         * Returns a string representation of the node, indicating its index and data.
         */
        public String toString() {
            StringBuilder s = new StringBuilder();
            s.append("index:\t").append(index).append(",\tdata:\t").append(data).append("\n");
            return s.toString();
        }
    }


    private BSTNode root;
    private int size;

    /**
     * Constructor. Initializes an empty BST with root set to null and size set to 0.
     */
    public BST() {
        root = null;
        size = 0;
    }


    /**
     * Performs an in-order traversal of the BST and records indices and data values.
     */
    private String inOrderTraversal(BSTNode node) {
        if(node == null){return "";}
        StringBuilder s = new StringBuilder();
        if(node.left != null){
            s.append(inOrderTraversal(node.left));
        }
        s.append(node.toString());
        if(node.right != null){
            s.append(inOrderTraversal(node.right));
        }
        return s.toString();
    }

    /**
     * Returns a string representation of the entire BST using in-order traversal.
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("In-order Traversal of the BST ...\n").append("==================\n").append(inOrderTraversal(root));
        return s.toString();
    }

    /**
     * Returns the size of the BST, i.e., the number of valid nodes.
     */
    public int getSize() {
        return size;
    }

    /**
     * Adds a new node with the specified index and data to the BST.
     */
    public void addNode(I _index, T _data) {
        size += 1;
        BSTNode newNode = new BSTNode(_index, _data);
        if(root == null){
            root = newNode;
            return;
        }
        BSTNode currentNode = root;
        while(true){
            if(((Comparable <I>) _index).compareTo(currentNode.getIndex()) > -1){
                if(currentNode.right == null){
                    currentNode.right = newNode;
                    break;
                }
                else{
                    currentNode = currentNode.right;
                }
            }
            else{
                if(currentNode.left == null){
                    currentNode.left = newNode;
                    break;
                }
                else{
                    currentNode = currentNode.left;
                }
            }
        }
    }

    /**
     * Searches for a node with the specified index in the BST.
     */
    public BSTNode searchNode(I _index) {
        return searchHelper(root, _index);
    }

    private BSTNode searchHelper(BSTNode currentNode, I _index){
        if(currentNode == null){return null;}
        if(((Comparable <I>)_index).compareTo(currentNode.getIndex()) == 0){
            return currentNode;
        }
        else if(((Comparable <I>)_index).compareTo(currentNode.getIndex()) > 0-1){
            BSTNode rightResult = searchHelper(currentNode.right, _index);
            if(rightResult != null){
                return rightResult;
            }
        }
        else{
            BSTNode leftResult = searchHelper(currentNode.left, _index);
            if(leftResult != null){
                return leftResult;
            }
        }
        return null;
    }

    /**
     * Removes a node with the specified index from the BST.
     */
    public void removeNode(I _index) throws IllegalArgumentException{
        BSTNode removeNode = searchNode(_index);
        if(removeNode == null){throw new IllegalArgumentException("removeNode(I _index): No node with an index "+_index.toString()+" in the BST");}
        BSTNode parent = searchParent(root, _index);
        //leaf node
        if(removeNode.left == null && removeNode.right == null){
            size-=1;
            if(parent == null){
                root = null;
            }
            else if(parent.left == removeNode){
                parent.left = null;
            }
            else if(parent.right == removeNode){
                parent.right = null;
            }
        }
        //1 node
        else if((removeNode.left != null || removeNode.right != null) && !(removeNode.left != null && removeNode.right != null)){
            size-=1;
            BSTNode nextNode = null;
            if(removeNode.left != null){
                nextNode = removeNode.left;
            }
            else if (removeNode.right != null){
                nextNode = removeNode.right;
            }

            if(parent == null){root = nextNode;}
            else if(parent.left == removeNode){
                parent.left = nextNode;
            }
            else if(parent.right == removeNode){
                parent.right = nextNode;
            }
        }
        //2 nodes
        else {
            BSTNode successor = removeNode.right;
            while (successor.left != null) {
                successor = successor.left;
            }
            I tempIndex = successor.getIndex();
            T tempData = successor.getData();
            removeNode(successor.getIndex());
            removeNode.index = tempIndex;
            removeNode.setData(tempData);
        }
    }

    private BSTNode searchParent(BSTNode currentNode, I _index){
        if(currentNode == null){return null;}
        if(currentNode.left != null){
            if(((Comparable <I>) _index).compareTo(currentNode.left.getIndex()) == 0){
                return currentNode;
            }
        }
        if(currentNode.right != null){
            if(((Comparable <I>) _index).compareTo(currentNode.right.getIndex()) == 0){
                return currentNode;
            }
        }
        BSTNode leftResult = searchParent(currentNode.left, _index);
        BSTNode rightResult = searchParent(currentNode.right, _index);
        if(leftResult != null){
            return leftResult;
        }
        if(rightResult != null){
            return rightResult;
        }
        return null;
    }

    /**
     * Updates a node's data with a new value, given its index.
     */
    public void updateNode(I _index, T _newData)  throws IllegalArgumentException{
        BSTNode removeNode = searchNode(_index);
        if(removeNode == null){throw new IllegalArgumentException("updateNode(I _index, T _newData): No node with an index "+_index.toString()+" in the BST");}
        removeNode.setData(_newData);
    }

    
/************************************ GRADING CODE (DO NOT MODIFY) ************************************ */
    /**
     * Performs a pre-order traversal of the BST.
     */
    private void preOrderTraversal(BSTNode node, int[] idx, String[] arr, boolean dataFlag) {
        // DO NOT CHANGE THIS. THIS FOR TESTING PURPOSES
        if(node == null)
            return;

        if(dataFlag)
            arr[idx[0]] = String.valueOf(node.getData());
        else
            arr[idx[0]] = String.valueOf(node.getIndex());
        idx[0]++;
        
        preOrderTraversal(node.left, idx, arr, dataFlag);
        preOrderTraversal(node.right, idx, arr, dataFlag);
    }

    /**
     * Returns an array of data values in pre-order traversal order.
     * @return A String array containing the data values of all nodes in pre-order order
     */
    public String[] getDataArray() {
        /// DO NOT CHANGE THIS. THIS FOR TESTING PURPOSES
        String[] dataArr = new String[size];
        preOrderTraversal(this.root, new int[1], dataArr, true);
        return dataArr;
    }

    /**
     * Returns an array of index values in pre-order traversal order.
     * @return A String array containing the index values of all nodes in pre-order order
     */
    public String[] getIndexArray() {
        // DO NOT CHANGE THIS. THIS FOR TESTING PURPOSES
        String[] indexArr = new String[size];
        preOrderTraversal(this.root, new int[1], indexArr, false);
        return indexArr;
    }

/****************************************************************************************************** */

}
