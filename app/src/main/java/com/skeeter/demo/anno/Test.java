package com.skeeter.demo.anno;

/**
 * @author michael created on 2016/12/2.
 */
public class Test {
    @TestAnno("temp1 aslfh")
    public String mTemp1;
    @TestAnno("temp2 int dfkjshfdkjshldk")
    public int mTemp2;
    @TestAnno("temp4 Integer asdfh")
    public Integer mTemp4;
    @TestAnno("mTemp3 sadjkhfalskh")
    public boolean mTemp3;


    public static void main(String[] args) {


        String a = "abc";
        String b = "abc";
        String c = new String("abc");
        String d = new String("abc");


        System.out.println("a == b " + (a == b));
        System.out.println("a == c " + (a == c));
        System.out.println("c == d " + (c == d));

    }
}
