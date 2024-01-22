package Lab4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

import static java.lang.Math.abs;


public class SumOfAbsoluteDifferences {

    static int solve(int numbers[], int N, int K) {
        int[][] dp = new int[N][K];
        for (int i = 1; i < N; i++) {
            for (int j = 0; j < i; j++) {
                int diff = Math.abs(numbers[i] - numbers[j]);
                for (int k = 1; k < K; k++) {
                    if (dp[i][k] < dp[j][k - 1] + diff) {
                        dp[i][k] = dp[j][k - 1] + diff;
                    }
                }
            }
        }
        int max = 0;
        for (int i = 0; i < N; i++) {
            if (max < dp[i][K - 1]) {
                max = dp[i][K - 1];
            }
        }
        return max;
    }

    public static void main(String[] args) throws Exception {
        int i,j,k;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        int numbers[] = new int[N];

        st = new StringTokenizer(br.readLine());
        for (i=0;i<N;i++) {
            numbers[i] = Integer.parseInt(st.nextToken());
        }

        int res = solve(numbers, N, K);
        System.out.println(res);

        br.close();

    }

}