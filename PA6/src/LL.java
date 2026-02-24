public class LL <T>{
    private LLNode head;
    private LLNode tail;
    private int length;

    public LL (){
        length = 0;
        head = new LLNode();
        tail = new LLNode();
        head.next = tail;
    }

    public String toString(){
        LLNode printer = head;
        StringBuilder output = new StringBuilder();
        output.append("print the series ...\n").append("==================\n");


        while(printer != null){
            output.append(printer.getIndex()).append("\t: ").append(printer.getData()).append("\n");
            printer = printer.next;
        }
        return output.toString();
    }

    public int getLength(){
        return length;
    }

    public String[] getDataArray(){
        LLNode pointer = head.next;
        String[] dataArray = new String[length];
        for(int i=0;i<length;i++){
            if(pointer.next == null){break;}
            try {
                dataArray[i] = pointer.getData().toString();
            } catch (NullPointerException e) {
                dataArray[i] = null;
            }
            pointer = pointer.next;
        }
        return dataArray;
    }

    public String[] getIndexArray(){
        LLNode pointer = head.next;
        String[] indexArray = new String[this.length];
        for(int i=0;i<length;i++){
            if(pointer.next == null){break;}
            indexArray[i] = pointer.getIndex().toString();
            pointer = pointer.next;
        }
        return indexArray;
    }

    public void appendNode(String _index, T _data){
        if(_index==null ||_index==""){_index = Integer.toString(length);}
        LLNode newNode = new LLNode(_index,_data);
        LLNode pointer = head;
        while(pointer.next.next != null){
            pointer = pointer.next;
        }
        pointer.next = newNode;
        newNode.next = tail;
        length+=1;
    }

    public LLNode searchNode(String _index){
        LLNode pointer = head.next;
        while(pointer.next != null){
            if(pointer.getIndex().equals(_index)){
                return pointer;
            }
            pointer = pointer.next;
        }
        return null;
    }

    public void removeNode(String _index) throws IllegalArgumentException{
        LLNode pointer = head;
        while(pointer.next.next != null){
            if(pointer.next.getIndex().equals(_index)){
                pointer.next = pointer.next.next;
                length-=1;
                return;
            }
            pointer = pointer.next;
        }
        throw new IllegalArgumentException("removeNode(String _index): No node with an index " + _index + " in the list");
    }

    public void updateNode(String _index, T value) throws IllegalArgumentException{
        LLNode pointer = head.next;
        while(pointer.next != null){
            if(pointer.getIndex().equals(_index)){
                pointer.setData(value);
                return;
            }
            pointer = pointer.next;
        }
        throw new IllegalArgumentException("updateNode(String _index, T value): No node with an index " + _index + " in the list");
    }

    //INNER CLASS ------------------------------------------------
    public class LLNode{
        private String index;
        private T data;
        private LLNode next;

        // default constructor. Sets all instance variables to be null
        public LLNode(){
            index = null;
            data = null;
            next = null;
        }

        // another constructor. Set data and index to be _data and _index each
        public LLNode(String _index, T _data){
            index = _index;
            data = _data;
            next = null;
        }

        // return the index that’s stored in this node
        public String getIndex(){
            return index;
        }

        // return the data that’s stored in this node
        public T getData(){
            return data;
        }

        // update the data in this node to d
        public void setData(T d){
            data = d;
        }

    }

}
