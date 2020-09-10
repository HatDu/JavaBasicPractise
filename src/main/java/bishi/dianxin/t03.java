package bishi.dianxin;

import java.util.Scanner;

public class t03 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String[] nums = scanner.nextLine().split(",");
        int[] arr = new int[nums.length];
        for(int i = 0; i < nums.length; ++i){
            arr[i] = Integer.valueOf(nums[i]);
        }
        int pre = 0, cur = 0, tmp;

        for(int n : arr){
            tmp = cur;
            cur = Math.max(pre + n, cur);
            pre = tmp;
        }
        System.out.println(cur);
    }
}
