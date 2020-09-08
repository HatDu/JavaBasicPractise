package bishi;

import java.util.Scanner;

public class mi02 {
    public static void main(String[] args) {
        char[][] board = {{'A','B','C','E'},{'S','F','C','S'},{'A','D','E','E'}};
        Scanner scanner = new Scanner(System.in);

        String line = scanner.nextLine();
        // char[] arr = {'S', 'E', 'E'};
        char[] arr = line.toCharArray();
        System.out.println(findWord(board, arr));;
    }
    public static boolean findWord(char[][] board, char[] arr){
        int m = board.length, n = board[0].length;
        boolean[][] visited = new boolean[m][n];

        for(int i = 0; i < m; ++i){
            for(int j = 0; j < n; ++j){
                if(board[i][j] == arr[0]){
                    if(dfs(board, visited, arr, i, j, 1)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private static int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    public static boolean dfs(char[][] board, boolean[][] visited, char[] arr, int i, int j, int cur){
        if(cur == arr.length){
            return true;
        }
        visited[i][j] = true;

        for(int[] d : dirs){
            int newx = i + d[0];
            int newy = j + d[1];
            if(newx >= 0 && newy >= 0 && newx < board.length && newy < board[0].length && board[newx][newy] == arr[cur] && !visited[newx][newy]){
                if(dfs(board, visited, arr, newx, newy, cur+1)){
                    return true;
                }
            }
        }
        visited[i][j] = false;
        return false;
    }
}
