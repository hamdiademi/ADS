package Lab1;

import java.util.Scanner;

public class PushZero
{
    static void pushZerosToBeginning(int arr[], int n)
    {
        int zeroIndex = n - 1;

        for (int i = n - 1; i >= 0; i--) {
            if (arr[i] != 0) {
                arr[zeroIndex] = arr[i];
                if (i != zeroIndex) {
                    arr[i] = 0;
                }
                zeroIndex--;
            }
        }
    }

    public static void main (String[] args) {
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();

        int arr[] = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = input.nextInt();
        }
        pushZerosToBeginning(arr,n);
        System.out.println("Transformiranata niza e:");
        for (int i = 0; i < n; i++) {
            System.out.print(arr[i] + " ");
        }
    }
}
