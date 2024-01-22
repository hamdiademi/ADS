package Statistics;

//       Representation of names
//
//        It is necessary to make a computer application that will allow the user to quickly search through the database of the Statistics Office for
//        representation of names (male and female). The way to search is as follows:
//        first enter the name you want to search for, male or female, after that that's enough to enter the first 2 letters of the name to be able to list the female/male names that are in the system. As a result of the search you should frequency of occurrence for the given name is returned.
//
//        During each recording of newborn, his name is recorded in the database of the statistics office.
//        If the name exists, only the representation number is changed, and if it does not exist,is added as new.
//
//        Input:
//        From the standard input, a number 𝑁 is first read, which represents the number of names
//        which will be entered into the system. In the following 𝑁 lines are given the names of
//        newborns and their gender (M for male and F for female) separated by
//        empty space. The gender to be searched for is given in the next line, and then everything
//        given the names to be searched, each on a new line. To mark the end of it
//        given the word "END".
//
//        Output:
//        The following information should be printed on the standard output for each of the inputs: List of names given by the search method as
//        suggestions, each in a new order. If the name exists in the hash table, it is printed in
//        new line GENDER NAME REPRESENTATION separated by one space. If
//        the name is not found, "No such name" is printed.
//
//        Note: The function used to map the names to the number is
//        next:
//        ℎ(𝑤) = (100 * ASCII(𝑐1) + ASCII(𝑐2))%9091, where the word 𝑤 = 𝑐1𝑐2𝑐3𝑐4𝑐5 . . . is
//        consisting of capital letters only.
//
//        Example:
//        Input:
//        7
//        Christina F
//        Magdalena F
//        Ivana F
//        Ivan M
//        Elena F
//        Ana F
//        Makedonka F
//        F
//        MARIA
//        Ivana
//        Kristina
//        Anastasia
//        END
//
//        Output:
//        MAKEDONKA
//        MAGDALENA
//        No such name
//        IVANA
//        F IVANA 1
//        No such name
//        ANA
//        No such name

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;


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
        for (SLLNode<MapEntry<K, E>> curr = buckets[b]; curr != null; curr = curr.succ) {
            if (key.equals(((MapEntry<K, E>) curr.element).key)) {
                curr.element = newEntry;
                return;
            }
        }
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

    public SLLNode<MapEntry<K, E>> getFirst(K targetKey) {
        int b = hash(targetKey);
        return buckets[b];
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

class Name implements Comparable<Name> {
    String name;

    public String getIme() {
        return name;
    }

    public void setIme(String name) {
        this.name = name;
    }

    public Name(String name) {
        this.name = name.toUpperCase();
    }

    @Override
    public boolean equals(Object obj) {
        Name temp = (Name) obj;
        return this.name.equals(temp.name);
    }

    @Override
    public int hashCode() {
        return (100 * name.charAt(0) + name.charAt(1));
    }


    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(Name arg0) {
        return name.compareTo(arg0.name);
    }
}

public class Statistics {
    public static void main(String[] args) throws Exception, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());


        CBHT<Name,Character> hash_F = new CBHT<Name,Character>(n*2);
        CBHT<Name,Character> hash_M = new CBHT<Name,Character>(n*2);

        for (int i = 0; i < n; i++) {
            String[] part=br.readLine().split(" ");
            if(part[1].equals("F")) {
                hash_F.insert(new Name(part[0]), part[1].charAt(0));
            }else {
                hash_M.insert(new Name(part[0]), part[1].charAt(0));
            }
        }
        char ch=br.readLine().charAt(0);

        while (true){
            Name names=new Name(br.readLine()) ;
            if (Objects.equals(names.name, "END")){break;}

            SLLNode<MapEntry<Name,Character>> name = hash_F.search(names);
            if (name==null){
                System.out.println("No such name");
            }
            while (name!=null&&name.element.value==ch){
                System.out.println(name);
                name=name.succ;
            }
        }


    }
}
