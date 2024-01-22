package Anagrams;

//        Anagrams
//
//        Using a hash table to group all anagrams from a given list of words.
//        Anagrams are words that are obtained by rearranging the letters of the word. On
//        example spar is an anagram of rasp.
//
//        Input: The number of words 𝑁 is given in the first line. In the next 𝑁 rows are
//        given the words to be added to the table. In the next line it is given
//        the word for which the number of anagrams in the table should be printed.
//
//        Output: The number of anagrams in the table for the given word.
//
//        Example:
//        input
//        6
//        eat
//        tea
//        tan
//        ate
//        nat
//        bat
//        ant
//        output:
//        2


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


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
        return value.toString();
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
//        for (SLLNode<MapEntry<K,E>> curr = buckets[b]; curr != null; curr = curr.succ) {
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

public class Anagrams {

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        CBHT<String, String> hash = new CBHT<String, String>(n * 2+1);
        for (int i = 0; i < n; i++) {
            String anagram= br.readLine();
            hash.insert(anagram,anagram);
        }
        StringBuilder word= new StringBuilder(br.readLine());
        char[] array= word.toString().toCharArray();
        int count=0;
        word.delete(0,word.length());
        for (int i = 0; i < array.length; i++) {
            word.append(array[i]);

            for (int j = 0; j < array.length; j++) {
                word.append(array[j]);

                for (int k = 0; k < array.length; k++) {
                    word.append(array[k]);

                    if (hash.search(String.valueOf(word)) != null) {
                        count++;
                    }

                    word.deleteCharAt(word.length() - 1);
                }
                word.deleteCharAt(word.length() - 1);
            }
            word.deleteCharAt(word.length() - 1);
        }

        System.out.println(count);
    }
}