package TrafficLights;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;
import java.text.SimpleDateFormat;


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
        return "(" + key + "," + value + ")";
    }
}


class OBHT<K extends Comparable<K>, E> {
    int cluster = 0;

    private MapEntry<K, E>[] buckets;
    static final int NONE = -1; // ... distinct from any bucket index.
    @SuppressWarnings({"rawtypes", "unchecked"})
    private static final MapEntry former = new MapEntry(null, null);
    private int occupancy = 0;

    @SuppressWarnings("unchecked")
    public OBHT(int m) {
        buckets = (MapEntry<K, E>[]) new MapEntry[m];
    }

    //HASH FUNCTIONS:
    private int hash(K key) {
        return Math.abs(key.hashCode()) % buckets.length;
    }

    private int hash2(K key) {
        return 1 + Math.abs(key.hashCode()) % (buckets.length - 1); // we use 1+ and -1 to handle 0
    }

    private int doubleProbe(K key, int i) {
        return (hash(key) + i * hash2(key)) % (buckets.length);
    }

    private int quadraticProbe(int b, int i) {
        // Quadratic probing: b' = (b + c1 * i + c2 * i^2) % buckets.length
        int c1 = 1; // constant 1
        int c2 = 1; // constant 2
        return (b + i ^ 2) % buckets.length;
        // return (b + c1 * i + c2 * i * i) % buckets.length;
    }
    //

    public MapEntry<K, E> getBucket(int i) {
        return buckets[i];
    }

    public int search(K targetKey) {
        int b = hash(targetKey);
        int n_search = 0;
        int i = 0;//  for quadric and double probhing to increment every time we have collision
        for (; ; ) {
            MapEntry<K, E> oldEntry = buckets[b];
            if (oldEntry == null)
                return NONE;
            else if (targetKey.equals(oldEntry.key))
                return b;
            else {
                // LINEAR PROBHING
//                 b = (b + 1) % buckets.length;

                // QUADRIC PROBHING
//                 i++;
//                 b = quadraticProbe(hash(targetKey), i);

                // DOUBLE HASH PROBHING
                i++; // incremnt every time we have collision
                b = doubleProbe(targetKey, i);

                n_search++;
                if (n_search == buckets.length)
                    return NONE;
            }
        }
    }

    public void insert(K key, E val) {
        MapEntry<K, E> newEntry = new MapEntry<K, E>(key, val);
        int b = hash(key);
        int n_search = 0;
        int i = 0; //  for quadric and double probhing to increment every time we have collision

        for (; ; ) {
            MapEntry<K, E> oldEntry = buckets[b];
            if (oldEntry == null) {
                if (++occupancy == buckets.length) {
                    System.out.println("Hash tabelata e polna!!!");
                }
                buckets[b] = newEntry;
                return;
            } else if (oldEntry == former || key.equals(oldEntry.key)) {
                buckets[b] = newEntry;
                return;
            } else {
                // LINEAR PROBHING
//                 b = (b + 1) % buckets.length;

                // QUADRIC PROBHING
//                 i++;
//                 b = quadraticProbe(hash(key), i);

                // DOUBLE HASH PROBHING
                i++;
                b = doubleProbe(key, i);


                if (i == 1) {
                    cluster++;
                }

                n_search++;
                if (n_search == buckets.length)
                    return;

            }
        }
    }

    @SuppressWarnings("unchecked")
    public void delete(K key) {
        int b = hash(key);
        int n_search = 0;
        int i = 0;
        for (; ; ) {
            MapEntry<K, E> oldEntry = buckets[b];

            if (oldEntry == null)
                return;
            else if (key.equals(oldEntry.key)) {
                buckets[b] = former;
                return;
            } else {
                // LINEAR PROBHING
                // b = (b + 1) % buckets.length;

                // QUADRIC PROBHING
                // i++
                // b = quadraticProbe(hash(key), i);

                // DOUBLE HASH PROBHING
                i++;
                b = doubleProbe(key, i);

                n_search++;
                if (n_search == buckets.length)
                    return;

            }
        }
    }

    public String toString() {
        String temp = "";
        for (int i = 0; i < buckets.length; i++) {
            temp += i + ":";
            if (buckets[i] == null)
                temp += "\n";
            else if (buckets[i] == former)
                temp += "former\n";
            else
                temp += buckets[i] + "\n";
        }
        return temp;
    }
}

class Driver implements Comparable<Driver> {
    String name;
    String surname;
    Date time;


    public Driver(String name, String surname, Date time) {
        this.name = name;
        this.surname = surname;
        this.time = time;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return name + " " + surname;
    }

    @Override
    public int compareTo(Driver o) {

        return this.getTime().compareTo(o.getTime());
    }
}


public class TrafficLights {
    public static void main(String[] args) throws IOException, ParseException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        OBHT<String, String> hash = new OBHT<String, String>(n * 2);


        for (int i = 0; i < n; i++) {
            String[] part = br.readLine().split(" ");
            hash.insert(part[0], part[1] + " " + part[2]);
        }
        int speedlimit = Integer.parseInt(br.readLine());

        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        String[] arr = br.readLine().split(" ");

        ArrayList<Driver> arrlist = new ArrayList<>();
        for (int i = 0; i < arr.length-2; i += 3) {
            int speed=Integer.parseInt(arr[i + 1]);
            if ( speed > speedlimit) {
                if (hash.search(arr[i])>0) {
                    String[] part = hash.getBucket(hash.search(arr[i])).value.split(" ");
                    Driver driver = new Driver(part[0], part[1], format.parse(arr[i + 2]));
                    arrlist.add(driver);
                }
            }
        }
        for (int i = 0; i < arrlist.size(); i++) {
            for (int j = 0; j < arrlist.size()-1-i; j++) {
                if (arrlist.get(j).compareTo(arrlist.get(j + 1))>0) {
                    Driver tmp=arrlist.get(j);
                    arrlist.set(j,arrlist.get(j+1));
                    arrlist.set(j+1,tmp);
                }
            }

        }
        for (int i = 0; i < arrlist.size(); i++) {
            System.out.println(arrlist.get(i));
        }


    }

}
