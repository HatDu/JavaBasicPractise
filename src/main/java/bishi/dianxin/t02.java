package bishi.dianxin;

public class t02 {
    public static void main(String[] args) {
        for(int a = 0; a < 10; ++a){
            for(int b = 0; b < 10; ++b){
                for(int c = 0; c < 10; ++c){
                    for(int d = 0; d < 10; ++d){
                        if((a+b)*1000 + (b+c)*100 + (c+d)*10 + a + d == 8888){
                            System.out.println(a + " " + b + " " + c + " " + d);
                        }
                    }
                }
            }
        }
    }
}
