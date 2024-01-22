package BestOffer;
//       Best offer
//
//        A world-famous lecturer receives offers to lecture every day
//        lectures. Dates, start time, city and lecture fee amount (in dollars) are given for each offer. Your task is for a given date to
//        present the lecture that would bring the lecturer the most income.
//        If there are no offers for the given date, print "No offers".
//
//        Input: In the first line of the input, the number of offers is given, and in each successive one
//        order are given: the date and time of the lecture (dd/mm/yyyyhh:mm format),
//        the city where the lecture will be held and the amount of the fee. In the last one
//        row is given the date for which you need to print which offer is the best for it
//        date.
//
//        Output: The offer details for that date.
//
//        Example:
//        input:
//        7
//        27/01/2016 14:00 NewYork 6000
//        28/01/2016 08:00 Paris 3000
//        28/01/2016 14:00 Munich 5000
//        27/01/2016 09:00 Beijing 8000
//        27/01/2016 08:00 Seattle 4000
//        01/28/2016 09:00 SaltLakeCity 10000
//        28/01/2016 09:00 Lagos 12000
//        27/01/2016
//        Output:
//        09:00 Beijing 8000

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


class MapEntry<K extends Comparable<K>,E> implements Comparable<K> {

    K key;
    E value;

    public MapEntry (K key, E val) {
        this.key = key;
        this.value = val;
    }

    public int compareTo (K that) {
        @SuppressWarnings("unchecked")
        MapEntry<K,E> other = (MapEntry<K,E>) that;
        return this.key.compareTo(other.key);
    }

    public String toString () {
        return  value.toString();
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

    private SLLNode<MapEntry<K,E>>[] buckets;

    @SuppressWarnings("unchecked")
    public CBHT(int m) {
        buckets = (SLLNode<MapEntry<K,E>>[]) new SLLNode[m];
    }

    private int hash(K key) {
        return Math.abs(key.hashCode()) % buckets.length;
    }

    public SLLNode<MapEntry<K,E>> search(K targetKey) {
        int b = hash(targetKey);
        for (SLLNode<MapEntry<K,E>> curr = buckets[b]; curr != null; curr = curr.succ) {
            if (targetKey.equals(((MapEntry<K, E>) curr.element).key))
                return curr;
        }
        return null;
    }

    public void insert(K key, E val) {		// Insert the entry <key, val> into this CBHT.
        MapEntry<K, E> newEntry = new MapEntry<K, E>(key, val);
        int b = hash(key);
//        for (SLLNode<MapEntry<K,E>> curr = buckets[b]; curr != null; curr = curr.succ) {
//            if (key.equals(((MapEntry<K, E>) curr.element).key)) {
//                curr.element = newEntry;
//                return;
//            }
//        }
        buckets[b] = new SLLNode<MapEntry<K,E>>(newEntry, buckets[b]);
    }

    public void delete(K key) {
        int b = hash(key);
        for (SLLNode<MapEntry<K,E>> pred = null, curr = buckets[b]; curr != null; pred = curr, curr = curr.succ) {
            if (key.equals(((MapEntry<K,E>) curr.element).key)) {
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
            for (SLLNode<MapEntry<K,E>> curr = buckets[i]; curr != null; curr = curr.succ) {
                temp += curr.element.toString() + " ";
            }
            temp += "\n";
        }
        return temp;
    }

}
class Lecture implements Comparable<Lecture> {
    String date;
    String time;
    String place;
    Integer fee;

    public Lecture(String date, String time, String place, Integer fee) {
        this.date = date;
        this.time = time;
        this.place = place;
        this.fee = fee;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {

        this.fee = fee;
    }

    @Override
    public int compareTo(Lecture obj) {
        if (this.fee > obj.fee)
            return 1;
        else if (this.fee < obj.fee)
            return -1;
        else
            return 0;
    }

    @Override
    public String toString() {
        return  time + " " + place + " " + fee;
    }
}


public class BestOffer {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n=Integer.parseInt(br.readLine());

        CBHT<String,Lecture> hash= new CBHT<String,Lecture>(n*2);

        String[] array;

        for (int i = 0; i < n; i++) {
            array=br.readLine().split(" ");
            hash.insert(array[0], new Lecture(array[0],array[1],array[2],Integer.parseInt(array[3])));
        }
        String date= br.readLine();

        SLLNode<MapEntry<String,Lecture>> current=hash.search(date);
        if (current==null){
            System.out.println("No offers");
        }
        SLLNode<MapEntry<String,Lecture>> res=null;
        while (current!=null&&current.succ!=null){
            Lecture lecture=current.element.value;
            if(lecture.compareTo(current.succ.element.value) >= 0){
                res=current;
            }
            current=current.succ;
        }
        System.out.println(res);

    }
}
