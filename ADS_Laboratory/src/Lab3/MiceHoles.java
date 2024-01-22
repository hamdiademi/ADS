package Lab3;

import java.util.Arrays;
import java.util.Scanner;

import static java.lang.Math.abs;


public class MiceHoles {
    //TODO: implement function

    public static int minTime(int[] mice, int[] holes) {
        Arrays.sort(mice);
        Arrays.sort(holes);

        int time=0;
        for (int i = 0; i < holes.length; i++) {
           if(abs(mice[i]-holes[i])>time){
               time=abs(mice[i]-holes[i]);
           }
        }

        return time;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        String line = input.nextLine();
        String[] parts = line.split(" ");
        int[] mice = new int[parts.length];
        for(int i=0;i<parts.length;i++) {
            mice[i] = Integer.parseInt(parts[i]);
        }

        line = input.nextLine();
        parts = line.split(" ");
        int[] holes = new int[parts.length];
        for(int i=0;i<parts.length;i++) {
            holes[i] = Integer.parseInt(parts[i]);
        }

        System.out.println(minTime(mice, holes));
    }
}

