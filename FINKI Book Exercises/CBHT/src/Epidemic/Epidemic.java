package Epidemic;


//        Epidemic
//
//        Due to the seasonal flu epidemic, every test of a given patient is
//        saves the municipality in which he lives, his surname and information on whether he is positive or negative for the virus. Statistical data is needed to
//        determine the risk factor for a given municipality. Your task is for a given municipality of
//        output to print the risk factor in the given municipality.
//
//        The risk factor is calculated as follows:
//        Risk factor =
//        number of positive patients / number of negative patients + number of positive patients
//
//        Note: It is possible to have patients with the same last name. The same should
//        to be taken as separate values in statistics.
//
//        Input: At the input, the number of patients 𝑁 is given first, and then each patient is
//        given in a new line in the format: "Municipality in which he lives" "Patient's last name"
//        "Test results (positive/negative)". At the end, the municipality for which is given
//        the risk factor should be calculated.
//
//        Output: A decimal number rounded to two decimal places representing the risk
//        the factor for the given municipality.
//
//        Example:
//        Input:
//        6
//        Centar Stojanoski negative
//        Centar Trajkovski positive
//        Centar Petkovski positive
//        Karpos Stojanoski positive
//        Karpos Trajkovski negative
//        Centar Trajkovski positive
//        Centar
//
//        Output:
//        0.75


import java.io.IOException;

class MapEntry<K extends Comparable<K>, E> implements Comparable<K> {

    K key;
    E value;

    public MapEntry(K key, E val) {
        this.key = key;
        this.value = val;
    }

    public int compareTo(K that) {
        @SuppressWarnings("unchecked")
        MapEntry<K, E> other = (MapEntry<K, E>) that;
        return this.key.compareTo(other.key);
    }

    public String toString() {
        return "(" + key + "," + value + ")";
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

class CBHT<K extends Comparable<K>, E> {

    private SLLNode<MapEntry<K, E>>[] buckets;

    @SuppressWarnings("unchecked")
    public CBHT(int m) {
        buckets = (SLLNode<MapEntry<K, E>>[]) new SLLNode[m];
    }

    private int hash(K key) {
        return Math.abs(key.hashCode()) % buckets.length;
    }

    public SLLNode<MapEntry<K, E>> search(K targetKey) {
        int b = hash(targetKey);
        for (SLLNode<MapEntry<K, E>> curr = buckets[b]; curr != null; curr = curr.succ) {
            if (targetKey.equals(((MapEntry<K, E>) curr.element).key))
                return curr;
        }
        return null;
    }

    public void insert(K key, E val) {        // Insert the entry <key, val> into this CBHT.
        MapEntry<K, E> newEntry = new MapEntry<K, E>(key, val);
        int b = hash(key);
//        for (SLLNode<MapEntry<K, E>> curr = buckets[b]; curr != null; curr = curr.succ) {
//            if (key.equals(((MapEntry<K, E>) curr.element).key)) {
//                curr.element = newEntry;
//                return;
//            }
//        }
        buckets[b] = new SLLNode<MapEntry<K, E>>(newEntry, buckets[b]);
    }

    public void delete(K key) {
        int b = hash(key);
        for (SLLNode<MapEntry<K, E>> pred = null, curr = buckets[b]; curr != null; pred = curr, curr = curr.succ) {
            if (key.equals(((MapEntry<K, E>) curr.element).key)) {
                if (pred == null)
                    buckets[b] = curr.succ;
                else
                    pred.succ = curr.succ;
                return;
            }
        }
    }

    public String toString() {
        String temp = "";
        for (int i = 0; i < buckets.length; i++) {
            temp += i + ":";
            for (SLLNode<MapEntry<K, E>> curr = buckets[i]; curr != null; curr = curr.succ) {
                temp += curr.element.toString() + " ";
            }
            temp += "\n";
        }
        return temp;
    }

}

public class Epidemic {
    public static void main(String[] args) throws NumberFormatException, IOException {
       //your code here
    }

}
