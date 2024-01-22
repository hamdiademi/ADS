package Lab7.Apteka_4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

class Drug {
    String name;
    int poslist;
    int price;
    int quantity;

    public String getName() {                return name;                 }
    public void setName(String name) {        this.name = name;	            }
    public int getPrice() {      		    return price;	            }
    public void setPrice(int price) {		    this.price = price;           }
    public int getQuantity() {  		    return quantity;	        }
    public void setQuantity(int quantity) { this.quantity = quantity;	}
    public int getPoslist() {      		return poslist;        	}

    public Drug(String name, int poslist, int price, int quantity) {
        this.name = name.toUpperCase();
        this.poslist = poslist;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        if(poslist ==1) return name +"\n"+"POZ\n"+ price +"\n"+ quantity;
        else return name +"\n"+"NEG\n"+ price +"\n"+ quantity;
    }
}

class DrugKey {
    String name;
    public DrugKey(String name) {
        this.name = name.toUpperCase();
    }

    @Override
    public int hashCode() {
        // TODO implement
        return (29 * (29 * ((int)name.charAt(0) + (int)name.charAt(1) + (int)name.charAt(2)))) % 102780;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DrugKey drugKey = (DrugKey) o;
        return Objects.equals(name, drugKey.name);
    }
}

public class Apteka { //Pharmacy
    public static void main(String[] args) throws  IOException {
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        int n=Integer.parseInt(br.readLine());

        String[] arr;

        CBHT<DrugKey, Drug> hash=new CBHT<>(128);

        for (int i = 0; i < n; i++) {
            arr= br.readLine().split(" ");
            hash.insert(new DrugKey(arr[0]),new Drug(arr[0],Integer.parseInt(arr[1]),Integer.parseInt(arr[2]),Integer.parseInt(arr[3])));
        }

        while (true){
            DrugKey drugKey =  new DrugKey(br.readLine());
            if (Objects.equals(drugKey.name, "KRAJ")){
                break;
            }
            int order=Integer.parseInt(br.readLine());

            SLLNode<MapEntry<DrugKey, Drug>> drug = hash.search(drugKey);

            if( drug!=null){
                System.out.println(drug.element.value);

                if (drug.element.value.getQuantity()<order){
                    System.out.println("Nema dovolno lekovi");
                }else {
                    System.out.println("Napravena naracka");
                    drug.element.value.setQuantity(drug.element.value.getQuantity()-order);
                }
            }else {
                System.out.println("Nema takov lek");
            }
        }



    }
}




class CBHT<K, E> {
    private SLLNode<MapEntry<K,E>>[] buckets;

    @SuppressWarnings("unchecked")
    public CBHT(int m) {
        buckets = (SLLNode<MapEntry<K,E>>[]) new SLLNode[m];
    }

    private int hash(K key) {
       return Math.abs(key.hashCode()) % buckets.length;
    }

    // probajte da ja reshite ovaa zadacha bez koristenje na ovoj metod
    // try to solve this task without using this method

     public SLLNode<MapEntry<K,E>> search(K targetKey) {
         int b = hash(targetKey);
         for (SLLNode<MapEntry<K,E>> curr = buckets[b]; curr != null; curr = curr.succ) {
             if (targetKey.equals(curr.element.key))     return curr;
         }
       return null;
     }

    public void insert(K key, E val) {
        MapEntry<K, E> newEntry = new MapEntry<K, E>(key, val);
        int b = hash(key);
        for (SLLNode<MapEntry<K,E>> curr = buckets[b]; curr != null; curr = curr.succ) {
            if (key.equals(curr.element.key)) {
                curr.element = newEntry;
                return;
            }
        }
        buckets[b] = new SLLNode<MapEntry<K,E>>(newEntry, buckets[b]);
    }

    public void delete(K key) {
        int b = hash(key);
        for (SLLNode<MapEntry<K,E>> pred = null, curr = buckets[b]; curr != null; pred = curr, curr = curr.succ) {
            if (key.equals(curr.element.key)) {
                if (pred == null)   buckets[b] = curr.succ;
                else                pred.succ = curr.succ;
                return;
            }
        }
    }

    public String toString() {
        String temp = "";
        for (int i = 0; i < buckets.length; i++) {
            temp += i + ":";
            for (SLLNode<MapEntry<K,E>> curr = buckets[i]; curr != null; curr = curr.succ) {
                temp += curr.element.toString() + " ";
            }
            temp += "\n";
        }
        return temp;
    }

}
class MapEntry<K,E> {
    K key;
    E value;
    public MapEntry (K key, E val) {
        this.key = key;
        this.value = val;
    }
    public String toString () {
        return "<" + key + "," + value + ">";
    }
}

class SLLNode<E> {
    protected E element;
    protected SLLNode<E> succ;
    public SLLNode(E elem, SLLNode<E> succ) {
        this.element = elem;
        this.succ = succ;
    }
    @Override
    public String toString() {
        return element.toString();
    }
}

