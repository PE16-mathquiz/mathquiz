import javax.swing.*;
import java.awt.*;
import java.util.*;

class Model extends Observable{
    private int dec_int; //ランダムに決める問題の数(10進数)
    private String dec; //10進数の問題の数
    private String bin; //2進数の問題の数
    private String oct; //8進数の問題の数
    private String hex; //16進数の問題の数

    //問題の形式をきめるフラグ
    //2:２進数 8:8進数 10:10進数 16:16進数
    private int que; //問題の基数
    private int sol; //答えの基数
    
    //問題文を入れる変数
    private String quejp; //問題文
    private String quenum; //問題となる数
    private String answer; //答え
	
    //コンストラクタ
    public Model(int q, int s){
    	this.que = q;
	    this.sol = s;
	    
	    dec_int = (int)(Math.random() * Math.pow(2, 10));
	    dec = String.valueOf(dec_int);
	    bin = Integer.toBinaryString(dec_int);
	    oct = Integer.toOctalString(dec_int);
	    hex = Integer.toHexString(dec_int).toUpperCase();
	    quejp = String.format("次の%d進数を%d進数に変換しなさい", que, sol);
	    
	    quenum = "none";
	    switch(que){
		case 2:
	    	quenum = String.format("%10s", bin).replace(' ', '0');
	    	break;
		case 8:
	    	quenum = "0" + oct;
	    	break;
		case 10:
	    	quenum = dec;
	   	 	break;
		case 16:
	    	quenum = "0x" + hex;
	    	break;
		}
		
		answer = "none";
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

    }
    
    //get関数
    public String get_quejp(){
    	return quejp;
    }
    public String get_quenum(){
    	return quenum;
    }
    
    //print関数
    public void print_all(){
		System.out.println("10進数: " + dec);
		System.out.println(" 2進数: " + bin);
		System.out.println(" 8進数: " + oct);
		System.out.println("16進数: " + hex);
    }

    //問題を表示する関数
    public void print_q(){
		System.out.println(quejp);
		System.out.println(quenum);
    }

    //答えを入力してもらって確認する
    public void ans_q(String r){
    	String reply = r.toUpperCase();  //入力された16進数が小文字でも正解と判定するため
    	
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
		Model dec = new Model(10, 16);
		String reply = "none";
		Scanner scan = new Scanner(System.in);

		dec.print_all();
		dec.print_q();
	
		System.out.println("答えを入力してください");
		reply = scan.next();
	
		dec.ans_q(reply);
    }
}
