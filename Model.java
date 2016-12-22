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
    //2:２進数 8:8進数 10:10進数 16:16進数
    private int que; //問題の基数
    private int sol; //答えの基数
	
    //コンストラクタ
    public Model(int q, int s){
	    dec_int = (int)(Math.random() * Math.pow(2, 10));
	    dec = String.valueOf(dec_int);
	    bin = Integer.toBinaryString(dec_int);
	    oct = Integer.toOctalString(dec_int);
	    hex = Integer.toHexString(dec_int).toUpperCase();

	    this.que = q;
	    this.sol = s;
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
	String str = "none";
	System.out.printf("次の%d進数を%d進数に変換しなさい\n", que, sol);
	switch(que){
	case 2:
	    str = String.format("%10s", bin).replace(' ', '0');
	    break;
	case 8:
	    str = oct;
	    break;
	case 10:
	    str = dec;
	    break;
	case 16:
	    str = "0x" + hex;
	    break;
	}
	System.out.println(str);
    }

    //答えを入力してもらって確認する
    public void ans_q(){
	String reply = "none";
	String answer = "none";
	Scanner scan = new Scanner(System.in);
	
	System.out.println("答えを入力してください");
	reply = scan.next();

	switch(sol){
	case 2:
	    answer = bin;
	    break;
	case 8:
	    answer = oct;
	    break;
	case 10:
	    answer = dec;
	    break;
	case 16:
	    answer = hex;
	    break;
	}

	if(reply.equals(answer)){
	    System.out.println("正解!!");
	}
	else{
	    System.out.println("不正解..");
	}
    }
}

class ModelMain{
    public static void main(String[] args){
	Model dec = new Model(8, 10);
	
	dec.print_oct();
	dec.print_dec();
	dec.print_q();
	dec.ans_q();
    }
}
