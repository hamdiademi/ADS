package FacultyEnrollment;


//        FacultyEnrollment
//
//        Every candidate who wants to enroll in the faculty submits an electronic application to the University's enrollment system. Then the enrollment committee checks them
//        one by one the candidates and their entered data, and checks it separately
//        the success of the secondary school candidate in the E-Diary (Е-Дневник) application (which contains data for all students in all secondary schools in Macedonia).
//        Your task is for a given candidate to check if the entered high school average is validschool in the enrollment application.
//
//        Input:
//        In the first line there is a number 𝑁 of candidates who want to enroll
//        on college. In the next 𝑁 lines, the registration numbers of the candidates are given
//        and the average of the secondary education that they entered in the application for admissions. Then
//        number 𝑀 of data in E-Diary is given. In the following 𝑀 lines are given
//        high school students' ID numbers and their actual high school average
//        education. In the last row, the ID number of the candidate whose average is given
//        should be checked.
//
//        Output:
//        To print whether the candidate with the given ID number has entered it
//        the correct high school average ("OK"), whether the average was entered incorrectly
//        ("Error") or the candidate is not at all in E-Dnevnik ("Empty").
//
//        Example 1:
//        Input:
//        2
//        0610992333666 5.0
//        0901993222233 4.78
//        4
//        2205990121212 2.45
//        0901993222233 4.68
//        0610992333666 5.0
//        1511989984256 3.45
//        0901993222233
//        Output:
//        Error
//
//        Example 2:
//        Input:
//        2
//        0610992333666 5.0
//        0901993222233 4.78
//        4
//        2205990121212 2.45
//        0901993222233 4.68
//        0610992333666 5.0
//        1511989984256 3.45
//        0610992333666
//        Output:
//        OK
//
//        Example 3:
//        Input:
//        2
//        0610992333666 5.0
//        0901993222233 4.78
//        4
//        2205990121212 2.45
//        0901993222233 4.68
//        0610992333663 5.0
//        1511989984256 3.45
//        0610992333666
//        Output:
//        Empty


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

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
        return "(" + key + "," + value + ")";
    }
}


class OBHT<K extends Comparable<K>,E> {
    int cluster =0;

    private MapEntry<K,E>[] buckets;
    static final int NONE = -1; // ... distinct from any bucket index.
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static final MapEntry former = new MapEntry(null, null);
    private int occupancy = 0;

    @SuppressWarnings("unchecked")
    public OBHT (int m) {
        buckets = (MapEntry<K,E>[]) new MapEntry[m];
    }

    //HASH FUNCTIONS:
    private int hash (K key) {
        return Math.abs(key.hashCode()) % buckets.length;
    }
    private int hash2 (K key) {
        return 1+Math.abs(key.hashCode()) % (buckets.length-1); // we use 1+ and -1 to handle 0
    }
    private int doubleProbe(K key,int i){
        return (hash(key)+i*hash2(key)) % (buckets.length);
    }
    private int quadraticProbe(int b, int i) {
        // Quadratic probing: b' = (b + c1 * i + c2 * i^2) % buckets.length
        int c1 = 1; // constant 1
        int c2 = 1; // constant 2
        return (b + c1 * i + c2 * i * i) % buckets.length;
        //return (b+i^2)%buckets.length;
    }
    ///////////////////////////////////

    public MapEntry<K,E> getBucket(int i){
        return buckets[i];
    }

    public int search (K targetKey) {
        int b = hash(targetKey); int n_search=0;
        int i=0;//  for quadric and double probhing to increment every time we have collision
        for (;;) {
            MapEntry<K,E> oldEntry = buckets[b];
            if (oldEntry == null)
                return NONE;
            else if (targetKey.equals(oldEntry.key))
                return b;
            else{
                // LINEAR PROBHING
                 b = (b + 1) % buckets.length;

                // QUADRIC PROBHING
//                 i++;
//                 b = quadraticProbe(hash(targetKey), i);

                // DOUBLE HASH PROBHING
//                i++; // incremnt every time we have collision
//                b = doubleProbe(targetKey,i);

                n_search++;
                if(n_search==buckets.length)
                    return NONE;
            }
        }
    }

    public void insert (K key, E val) {
        MapEntry<K,E> newEntry = new MapEntry<K,E>(key, val);
        int b = hash(key);
        int n_search=0;
        int i=0; //  for quadric and double probhing to increment every time we have collision

        for (;;) {
            MapEntry<K,E> oldEntry = buckets[b];
            if (oldEntry == null) {
                if (++occupancy == buckets.length) {
                    System.out.println("Hash tabelata e polna!!!");
                }
                buckets[b] = newEntry;
                return;
            } else if (oldEntry == former || key.equals(oldEntry.key)) {
                buckets[b] = newEntry;
                return;
            } else{
                // LINEAR PROBHING
                 b = (b + 1) % buckets.length;

                // QUADRIC PROBHING
//                 i++;
//                 b = quadraticProbe(hash(key), i);

                // DOUBLE HASH PROBHING
//                i++;
//                b = doubleProbe(key,i);


                if (i == 1) {
                    cluster++;
                }

                n_search++;
                if(n_search==buckets.length)
                    return;

            }
        }
    }

    @SuppressWarnings("unchecked")
    public void delete (K key) {
        int b = hash(key); int n_search=0;
        int i=0;
        for (;;) {
            MapEntry<K,E> oldEntry = buckets[b];

            if (oldEntry == null)
                return;
            else if (key.equals(oldEntry.key)) {
                buckets[b] = former;
                return;
            } else{
                // LINEAR PROBHING
                 b = (b + 1) % buckets.length;

                // QUADRIC PROBHING
                // i++
                // b = quadraticProbe(hash(key), i);

                // DOUBLE HASH PROBHING
//                i++;
//                b = doubleProbe(key,i);

                n_search++;
                if(n_search==buckets.length)
                    return;

            }
        }
    }

    public String toString () {
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


public class FacultyEnrollment {
    public static void main(String[] args) throws NumberFormatException,IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n=Integer.parseInt(br.readLine());

        OBHT<String,Float> uni=new OBHT<String,Float>(n*2);

        for (int i = 0; i < n; i++) {
            String[] part=br.readLine().split(" ");
            uni.insert(part[0],Float.valueOf(part[1]));
        }
        int m=Integer.parseInt(br.readLine());
        OBHT<String,Float> eDiary =new OBHT<String,Float>(m*2);

        for (int i = 0; i < m; i++) {
            String[] part=br.readLine().split(" ");
            eDiary.insert(part[0],Float.valueOf(part[1]));
        }
        String id= br.readLine();

        MapEntry<String,Float> uniBucket=uni.getBucket(uni.search(id));
        MapEntry<String,Float> eDiaryBucket=eDiary.getBucket(uni.search(id));


        if (Objects.equals(uniBucket.key, eDiaryBucket.key)){
            if (Objects.equals(uniBucket.value, eDiaryBucket.value)){
                System.out.println("OK");
            }else {
                System.out.println("Error");
            }
        }else {
            System.out.println("Empty");
        }

    }

}
