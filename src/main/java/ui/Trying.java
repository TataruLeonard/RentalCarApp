package ui;

public class Trying {
    public static void main(String[] args) {
        String a= "01/02";
        String b= "03/08";

        int a1 = Integer.parseInt(a.substring(0,2));
        int a2 = Integer.parseInt(b.substring(0,2));
        System.out.println(a2-a1);
    }
}
