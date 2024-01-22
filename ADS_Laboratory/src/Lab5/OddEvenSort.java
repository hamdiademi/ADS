package Lab5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class OddEvenSort {
    static void sortAscending(int arr[], int n) {
        int temp = 0;
        for(int i = 0; i < n - 1; i++) {
            for(int j = 0; j < n - i - 1; j++) {
                if(arr[j] > arr[j + 1]) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    static void sortDescending(int arr[], int n) {
        int temp = 0;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] < arr[j + 1]) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }
    static void oddEvenSort(int a[], int n){

            int[] odd = new int[n];
            int[] even = new int[n];
            int oddsize = 0, evensize = 0;


            for (int i = 0; i < n; i++) {
                if (a[i] % 2 == 1) {
                    odd[oddsize++] = a[i];
                } else {
                    even[evensize++] = a[i];
                }
            }

            sortAscending(odd, odd.length);
            sortDescending(even, even.length);
        int idx=0;
        for (int i = 0; i < n; i++) {
            if (odd[i]!=0) {
                a[idx++] = odd[i];
            }
        }
         idx=0;
        for (int i = oddsize; i < n; i++) {
                a[i] = even[idx++];

        }





        }

    public static void main(String[] args) throws IOException{
        int i;
        BufferedReader stdin = new BufferedReader( new InputStreamReader(System.in));
        String s = stdin.readLine();
        int n = Integer.parseInt(s);

        s = stdin.readLine();
        String [] pom = s.split(" ");
        int [] a = new int[n];
        for(i=0;i<n;i++)
            a[i]=Integer.parseInt(pom[i]);
        oddEvenSort(a,n);
        for(i=0;i<n-1;i++)
            System.out.print(a[i]+" ");
        System.out.print(a[i]);
    }
}