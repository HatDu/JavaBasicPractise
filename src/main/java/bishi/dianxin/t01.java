package bishi.dianxin;

import java.util.Scanner;

public class t01 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] dp = new int[1501];
        dp[0] = 1;
        int i1 = 0, i2 = 0, i3 = 0;
        for(int i = 1; i <= n; ++i){
            int ugly = Math.min(Math.min(dp[i1] * 2, dp[i2] * 3),  dp[i3] * 5);
            dp[i] = ugly;
            if(ugly == dp[i1] * 2) ++i1;
            if(ugly == dp[i2] * 3) ++i2;
            if(ugly == dp[i3] * 5) ++i3;
        }
        System.out.println(dp[n-1]);
    }
}
