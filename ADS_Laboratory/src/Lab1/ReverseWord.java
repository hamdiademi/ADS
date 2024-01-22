package Lab1;

import java.util.Scanner;

public class ReverseWord {

    public static void printReversed(String word) {
        String reversed="";
        char ch;
        for (int i = 0; i < word.length(); i++) {
                ch= word.charAt(i);
                reversed = ch+reversed;
            }

       if(reversed!="") {
            System.out.println(reversed);
      }


    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n=input.nextInt();

        String[] word = new String[n+1];

        for (int i = 0; i <= n; i++) {
            word[i]=input.nextLine();
        }
        input.close();

        for (int i = 0; i <=n ; i++) {
            printReversed(word[i]);
        }

    }
}
