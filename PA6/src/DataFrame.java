public class DataFrame {
    private HashTable<SeriesV2<Object>> tabularData;
    private int numRows;
    private int numCols;

    public DataFrame(){
        tabularData = new HashTable<>();
        numRows = 0;
        numCols = 0;
    }

    public DataFrame(String _k, SeriesV2<Object> _series){
        tabularData = new HashTable<>();
        tabularData.insert(_k,_series);
        numRows = _series.getLength();
        numCols = 1;
    }

    public SeriesV2<Object> colLoc(String k){
        return tabularData.lookup(k);
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("printing the dataframe ...\n==================\n");
        for(int i=0;i<numCols;i++){
            sb.append("[colName:\t" + tabularData.getValidKeys()[i] + "]\n");
            sb.append(tabularData.lookup(tabularData.getValidKeys()[i]).toString() + "\n");
        }
        return sb.toString();
    }

    public int getNumRows(){
        return numRows;
    }

    public int getNumCols(){
        return numCols;
    }

    public String[] getColNames(){
        String[] colNames = new String[numCols];
        for(int k=0;k<numCols;k++){
            colNames[k] = tabularData.getValidKeys()[k];
        }
        return colNames;
    }

    public void addColumn(String k, SeriesV2<Object> s) throws IllegalArgumentException{
        if(numRows == 0){numRows = s.getLength();}
        else if(s.getLength() != numRows){throw new IllegalArgumentException("addColumn(String k, SeriesV2<Object> s): the length of s does not match the dataframe's # of rows");}
        tabularData.insert(k,s);
        numCols++;
    }

    public void removeColumn(String k){
        tabularData.delete(k);
        numCols--;
    }
}
