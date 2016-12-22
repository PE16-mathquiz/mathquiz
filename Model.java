import javax.swing.*;
import java.awt.*;
import java.util.*;

class Model {
    private int dec_int; //ランダムに決める問題の数(10進数)
    private String dec; //10進数の問題の数
    private String bin; //2進数の問題の数
    private String oct; //8進数の問題の数
    private String hex; //16進数の問題の数
	
    //コンストラクタ
    public Model(){
	    dec_int = (int)(Math.random() * Math.pow(2, 10));
	    dec = String.valueOf(dec_int);
	    bin = Integer.toBinaryString(dec_int);
	    oct = Integer.toOctalString(dec_int);
	    hex = Integer.toHexString(dec_int).toUpperCase();
    }
    
    //print関数
    public void print_dec(){
	System.out.println("10進数: " + dec);
    }
    public void print_bin(){
	System.out.println(" 2進数: " + bin);
    }
    public void print_oct(){
	System.out.println(" 8進数: " + oct);
    }
    public void print_hex(){
	System.out.println("16進数: " + hex);
    }
}

class ModelMain{
    public static void main(String[] args){
	Model sample = new Model();
	sample.print_dec();
	sample.print_bin();
	sample.print_oct();
	sample.print_hex();
    }
}
