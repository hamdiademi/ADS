package Lab1;

import java.util.Scanner;

public class PushZero
{
    static void pushZerosToBeginning(int arr[], int n)
    {
        int count = n - 1;
        for (int i = n - 1; i >= 0; i--) {
            if (arr[i] != 0) {
                arr[count--] = arr[i];
            }
        }

        while (count >= 0) {
            arr[count--] = 0;
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
