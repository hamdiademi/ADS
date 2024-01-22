package Speluvanje_5;

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


class Zbor implements Comparable<Zbor> {
    String zbor;

    public Zbor(String zbor) {
        this.zbor = zbor;
    }

    @Override
    public boolean equals(Object obj) {
        Zbor pom = (Zbor) obj;
        return this.zbor.equals(pom.zbor);
    }

    @Override
    public int hashCode() {
        /*
         *
         *you create the hash function
         *
         */
        return zbor.hashCode();
    }

    @Override
    public String toString() {
        return zbor;
    }

    @Override
    public int compareTo(Zbor arg0) {
        return zbor.compareTo(arg0.zbor);
    }
}

public class Speluvanje {
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        //---Vie odluchete za goleminata na hesh tabelata----
        OBHT<Zbor, String> hash = new OBHT<Zbor, String>(N * 3 + 1);
        // your code here
        for (int i = 0; i < N; i++) {
            String word = br.readLine();
            hash.insert(new Zbor(word), word);
        }

        String[] array = br.readLine().split(" ");
        boolean flag = true;
        for (int i = 0; i < array.length; i++) {

            String word = array[i].toLowerCase();
            if (!Character.isAlphabetic(array[i].charAt(array[i].length() - 1))) {
                word = word.substring(0, array[i].length() - 1);
            }
            if (hash.search(new Zbor(word)) == -1 && !word.isEmpty()) {
                System.out.println(word);
                flag = false;
            }

        }
        if (flag) {
            System.out.println("Bravo");
        }
        System.out.println(hash.cluster);


    }
}


