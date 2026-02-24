import java.util.Objects;

public class HashTable <V>{
    private static final Object BRIDGE = new String("[BRIDGE]".toCharArray());
    private int size;
    private int capacity;
    private String[] keys;
    private V[] values;

    public HashTable () {
        size = 0;
        capacity = 4;
        keys = new String[capacity];
        values = (V[]) new Object[capacity];
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("printing the hash table ...\n==================\n");
        for(int i=0;i<capacity;i++){
            sb.append("index:\t"+ Integer.toString(i) + ",\t");
            if(keys[i] == null){
                sb.append("key:\tnull,\tdata:\tnull\n");
            }
            else if(keys[i] == BRIDGE){
                sb.append("key:\tBRIDGE,\tdata:\tnull\n");
            }
            else{
                sb.append("key:\t" + keys[i].toString() + ",\tdata:\t" + values[i].toString() + "\n");
            }
        }
        return sb.toString();
    }

    public int getSize(){
        return size;
    }

    public int getCapacity(){
        return  capacity;
    }

    public String[] getKeyArray() {
        String[] ret = new String[capacity];
        for(int i=0;i<capacity;i++){
            ret[i] = keys[i];
        }
        return ret;
    }

    public V[] getDataArray(){
        V[] ret = (V[]) new Object[capacity];
        for(int i=0;i<capacity;i++){
            ret[i] = values[i];
        }
        return ret;
    }

    public String[] getValidKeys() {
        String[] ret = new String[size];
        int retIndex = 0;
        for(int i=0;i<capacity;i++){
            if(keys[i] != null && keys[i] != BRIDGE){
                ret[retIndex] = keys[i];
                retIndex++;
            }
        }
        return ret;
    }

    public int getHashIndex(String k){
        int hashValue = 0;
        for (int i = 0; i < k.length(); i++) {
            int letter = k.charAt(i) - 96;
            hashValue += (hashValue * 27 + letter);
        }
        return hashValue % this.getCapacity();
    }

    public V lookup(String k) throws NullPointerException{
        if(k==null){throw new NullPointerException("lookup(String key): key is null");}
        int index = getHashIndex(k);
        while(keys[index] != null){
            if(index == capacity){
                index = 0;
                continue;
            }
            else if(Objects.equals(keys[index], k)){
                return values[index];
            }
            index++;
        }
        return null;
    }

    public int insert(String k, V v) throws NullPointerException{
        if(k==null){throw new NullPointerException("insert(String k, V v): k is null");}
        if(v==null){throw new NullPointerException("insert(String k, V v): v is null");}
        int index = getHashIndex(k);
        while(true){
            if(index == capacity){
                index = 0;
                continue;
            }
            else if(keys[index] == null || keys[index] == BRIDGE){
                keys[index] = k;
                values[index] = v;
                size++;
                break;
            }
            else if(Objects.equals(keys[index], k)){
                if(values[index] != v){
                    values[index] = v;
                    return  index;
                }
            }
            index++;
        }
        if(((double)size/capacity) >= 0.55){sizeUp();}
        return index;
    }

    private void sizeUp() {
        capacity *= 2;
        String[] kTemp = new String[capacity];
        V[] vTemp = (V[]) new Object[capacity];
        for (int i = 0; i < (keys.length ); i++) {
            if (keys[i] != null && keys[i] != BRIDGE) {
                int index = getHashIndex(keys[i]);
                while(true){
                    if(index == capacity){
                        index = 0;
                        continue;
                    }
                    else if(kTemp[index] == null){
                        kTemp[index] = keys[i];
                        vTemp[index] = values[i];
                        break;
                    }
                    index++;
                }
            }
        }
        keys = kTemp;
        values = vTemp;
    }

    private void sizeDown() {
        capacity /= 2;
        if(capacity<4){capacity=4;}
        String[] kTemp = new String[capacity];
        V[] vTemp = (V[]) new Object[capacity];
        for (int i = 0; i < (keys.length); i++) {
            if (keys[i] != null && keys[i] != BRIDGE) {
                int index = getHashIndex(keys[i]);
                while(true){
                    if(index == capacity){
                        index = 0;
                        continue;
                    }
                    else if(kTemp[index] == null){
                        kTemp[index] = keys[i];
                        vTemp[index] = values[i];
                        break;
                    }
                    index++;
                }
            }
        }
        keys = kTemp;
        values = vTemp;
    }

    public int delete(String k) {
        int index = getHashIndex(k);
        int start = (index == 0) ? capacity-1 : index-1;
        while (true){
            if(index == capacity){
                index = 0;
                continue;
            }
            else if(Objects.equals(keys[index], k)){
                keys[index] = (String) BRIDGE;
                values[index] = null;
                size--;
                break;
            }
            else if(index == start){
                return getHashIndex(k);
            }
            index++;
        }
        if(((double)size/capacity) <= 0.3){sizeDown();}
        return index;
    }
}
