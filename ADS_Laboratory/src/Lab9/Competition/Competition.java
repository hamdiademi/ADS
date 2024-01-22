package Lab9.Competition;

import java.util.Scanner;

public class Competition {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int N = scanner.nextInt();

        int[] scores = new int[N];

        for (int i = 0; i < N; i++) {
            scores[i] = scanner.nextInt();
        }

        int M = scanner.nextInt();

        int rank = determineRank(scores, M);

        System.out.println(rank);

        scanner.close();
    }

    private static int determineRank(int[] scores, int M) {
        int rank = 1;

        for (int score : scores) {
            if (score > M) {
                rank++;
            }
        }

        return rank;
    }
}
