public class SeriesV2<T> implements Series<T>{
    private LL<T> seriesData;
    private BST<String,T> seriesDataBST;

    public SeriesV2(String[] _rowNames, T[] _data) {
        seriesData = new LL<>();
        seriesDataBST = new BST<String,T>();
        if (_data == null) {throw new NullPointerException("Series(String[] _index, T[] _data): " +
                "_data can't be null. Terminating the program");}

        try{
            if (_rowNames.length != _data.length){throw new IllegalArgumentException(
                    "Series(String[] _index, T[] _data): the length of _index and _data must be the same");}

            for(int i=0;i<_data.length;i++){
                if (_rowNames[i] == null) {throw new IllegalArgumentException(
                        "Series(String[] _index, T[] _data): _rowNames is not valid");}
                seriesData.appendNode(_rowNames[i], _data[i]);
                seriesDataBST.addNode(_rowNames[i], _data[i]);
            }
        }
        catch (NullPointerException e) {
            for(int i=0;i<_data.length;i++){
                seriesData.appendNode(Integer.toString(i), _data[i]);
                seriesDataBST.addNode(Integer.toString(i), _data[i]);
            }
        }
    }

    public String toString() {
        return seriesData.toString();
    }

    public int getLength() {
        return seriesData.getLength();
    }

    public String[] getRowNames() {
        return seriesData.getIndexArray();
    }

    public String[] getData() {
        return seriesData.getDataArray();
    }

    public void append(String rn, T d) {
        if(rn==null || rn==""){
            seriesData.appendNode(Integer.toString(seriesData.getLength()),d);
            seriesDataBST.addNode(Integer.toString(seriesData.getLength()),d);
        }
        else{
            seriesData.appendNode(rn,d);
            seriesDataBST.addNode(rn,d);
        }
    }

    public T loc(String rn) throws IllegalArgumentException, NullPointerException{
        if(rn == null){throw new NullPointerException("loc(String rn): rn can't be null");}
        if(rn == ""){throw new IllegalArgumentException("loc(String rn): rn can't be an empty string");}
        if(seriesDataBST.searchNode(rn) != null){
            return (T) seriesDataBST.searchNode(rn).getData();
        }
        return null;
    }

    public T[] loc(String[] rn) throws IllegalArgumentException, NullPointerException{
        if(rn == null){throw new NullPointerException("loc(String[] rn): rn[] can't be null");}
        if(rn.length == 0){throw new IllegalArgumentException("loc(String[] rn): rn[] can't be an empty array");}
        T[] ret_array = (T[]) new Object[rn.length];
        for(int i=0;i<rn.length;i++){
            ret_array[i] = this.loc(rn[i]);
        }
        return ret_array;
    }

    public T iloc(int ind) {
        try{
            return loc(seriesData.getIndexArray()[ind]);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("the index " + Integer.toString(ind) + " is not valid.. returning null");
            return null;
        }
    }

    public boolean drop(String rn) throws IllegalArgumentException, NullPointerException{
        if(rn == null){throw new NullPointerException("drop(String rn): rn can't be null");}
        if(rn == ""){throw new IllegalArgumentException("drop(String rn): rn can't be an empty String");}
        int lengthBefore = seriesData.getLength();
        try {
            seriesData.removeNode(rn);
            seriesDataBST.removeNode(rn);
        } catch (IllegalArgumentException e) {return false;}
        if(lengthBefore>seriesData.getLength()) {
            return true;
        }
        return false;
    }

    public void fillNull(T value) throws IllegalArgumentException{
        if(value == null){throw new IllegalArgumentException("fillNull(T value): value can't be null");}
        for(int i=0;i<this.seriesData.getLength();i++){
            if (seriesData.getDataArray()[i] == null){
                seriesData.updateNode(seriesData.getIndexArray()[i], value);
                seriesDataBST.updateNode(seriesData.getIndexArray()[i], value);
            }
        }
    }
}
