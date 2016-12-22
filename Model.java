import javax.swing.*;
import java.awt.*;
import java.util.*;

class Model {
    private int dec_int; //ランダムに決める問題の数(10進数)
    private String dec; //10進数の問題の数
    private String bin; //2進数の問題の数
    private String oct; //8進数の問題の数
    private String hex; //16進数の問題の数

    //問題の形式をきめるフラグ
    //0:２進数->10進数 1:8進数->10進数 2:16進数->10進数
    private int type;
	
    //コンストラクタ
    public Model(int t){
	    dec_int = (int)(Math.random() * Math.pow(2, 10));
	    dec_int = 100;
	    dec = String.valueOf(dec_int);
	    bin = Integer.toBinaryString(dec_int);
	    oct = Integer.toOctalString(dec_int);
	    hex = Integer.toHexString(dec_int).toUpperCase();

	    this.type = t;
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

    //問題を表示する関数
    public void print_q(){
	String question = "none";
	switch(type){
	case 0:
	    question = String.format("%10s", bin).replace(' ', '0');
	    System.out.println("次の2進数を10進数に変換しなさい");
	    break;
	case 1:
	    question = String.format("%4s", oct).replace(' ', '0');
	    System.out.println("次の8進数を10進数に変換しなさい");
	    break;
	case 2:
	    question = String.format("%3s", hex).replace(' ', '0');
	    System.out.println("次の16進数を10進数に変換しなさい");
	    break;
	}
	System.out.println(question);
    }
}

class ModelMain{
    public static void main(String[] args){
	Model dec = new Model(0);
	dec.print_q();
    }
}
