package bishi;

import javax.smartcardio.CommandAPDU;
import java.util.Scanner;

public class mi01 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] passwords = sc.nextLine().split(" ");
        for(String pass : passwords){
            if(pass.length() < 8 || pass.length() > 120){
                System.out.println(1);
                continue;
            }
            int numNum = 0;
            int signNum = 0;
            int capNum = 0;
            int smallNum = 0;

            for(int i = 0; i < pass.length(); ++i){
                char ch = pass.charAt(i);
                if(ch >= '0' && ch <= '9'){
                    ++numNum;
                }
                else if(ch >= 'a' && ch <= 'z'){
                    ++smallNum;
                }
                else if(ch >= 'A' && ch <= 'Z'){
                    ++capNum;
                }
                else{
                    ++signNum;
                }
            }
            if(numNum > 0 && signNum > 0 && capNum > 0 && smallNum > 0){
                System.out.println(0);
            }
            else{
                System.out.println(2);
            }
        }
    }
}
