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
	    
	this.reset();
    }
    
    //set関数
    public void set_base(int q, int s){
    	this.que = q;
    	this.sol = s;
    }
    
    //get関数
    public String get_quejp(){
    	return quejp;
    }
    public String get_quenum(){
    	return quenum;
    }
    public int get_Qbase(){
	return que;
    }
    public int get_Sbase(){
	return sol;
    }
    
    //print関数
    public void print_all(){
	System.out.println("10進数: " + dec);
	System.out.println(" 2進数: " + bin);
	System.out.println(" 8進数: " + oct);
	System.out.println("16進数: " + hex);
    }
    
    //問題をリセットする関数(コンストラクタでも使用)
    public void reset(){
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
    
    //問題を表示する関数
    public void print_q(){
	System.out.println(quejp);
	System.out.println(quenum);
    }

    //答えを入力してもらって確認する
    public String ans_q(String r){
    	String reply = r.toUpperCase();//入力された16進数が小文字でも正解と判定するため

	/*Observerに変更を通知する
	setChanged();
	notifyObservers();*/
    	
	//場合によって文章は変更
    	if(reply.equals(answer)){
	    return("正解!!"); //正解のときの文章を文字列として返す
	}
	else{
	    return("不正解.."); //不正解のときの文章を文字列として返す
	}

    }
}

class ModelMain{
    public static void main(String[] args){
    	int question = 10;
    	int solution = 16;
	String reply = "none";
	Scanner scan = new Scanner(System.in);
	int reset_flag; //問題をリセットするかどうかのフラグ
	int change_flag; //基数を変更するかどうかのフラグ
	boolean q_flag = false, s_flag = false; //基数が入力されたどうかのフラグ

	//問題の基数を設定する
	do{
	    String que_c = "none";
	    System.out.println("問題の基数を入力してください");
	    System.out.printf("現在の問題の基数: %d\n", question);
	    System.out.println("2 or 8 or 10 or 16 (整数のみを入力)");
	    que_c = scan.next();
	    
	    try{
		int temp_q = Integer.parseInt(que_c);
		
		switch(temp_q){
		case 2:
		case 8:
		case 10:
		case 16:
		    question = temp_q;
		    q_flag = true;
		    break;
		default:
		    System.out.println("決められた整数を入力してください\n");
		    break;
		}
	    }
	    catch(NumberFormatException e){
		System.out.println("整数のみを入力してください\n");
		continue;
	    }
	}while(!q_flag);
	
	//答えの基数を設定する
	do{
	    String sol_c = "none";
	    System.out.println("答えの基数を入力してください");
	    System.out.printf("現在の答えの基数: %d\n", solution);
	    System.out.println("2 or 8 or 10 or 16 (整数のみを入力)");
	    sol_c = scan.next();
	    
	    try{
		int temp_s = Integer.parseInt(sol_c);
		
		switch(temp_s){
		case 2:
		case 8:
		case 10:
		case 16:
		    if(question == temp_s){
			System.out.println("問題とは違う基数を入力してください\n");
			break;
		    }
		    solution = temp_s;
		    s_flag = true;
		    break;
		default:
		    System.out.println("決められた整数を入力してください\n");
		    break;
		}
	    }
	    catch(NumberFormatException e){
		System.out.println("整数のみを入力してください\n");
		continue;
	    }					
	}while(!s_flag);
	
	//Modelの作成
	Model model = new Model(question, solution);
	
	do{
	    //フラグの初期化
	    reset_flag = -1;
	    change_flag = -1;
	    q_flag = false;
	    s_flag = false;
	    
	    //答えの表示(デバッグ用)
	    model.print_all();
	    model.print_q();
	    
	    //答えの入力
	    System.out.println("答えを入力してください");
	    reply = scan.next();
	    System.out.println(model.ans_q(reply));
	    
	    //やり直すかの確認
	    do{
		String reset_c = "none";
		System.out.println("もう一度やりますか?");
		System.out.println("はい: \"y\" or \"yes\" / いいえ: \"n\" or \"no\"");
		reset_c = scan.next();
		
		if(reset_c.equals("y") || reset_c.equals("yes")){
		    reset_flag = 1;
		}
		else if(reset_c.equals("n") || reset_c.equals("no")){
		    reset_flag = 0;
		}
	    }while(reset_flag < 0);
	    
	    if(reset_flag != 1){
		break;
	    }
	    
	    //基数を変更するかの確認
	    do{
		String change_c = "none";
		System.out.println("問題の基数を変更しますか?");
		System.out.println("はい: \"y\" or \"yes\" / いいえ: \"n\" or \"no\"");
		change_c = scan.next();
		
		if(change_c.equals("y") || change_c.equals("yes")){
		    change_flag = 1;
		}
		else if(change_c.equals("n") || change_c.equals("no")){
		    change_flag = 0;
		}
	    }while(change_flag < 0);
	    
	    //変更する場合を入力を要請する
	    if(change_flag == 1){
		do{
		    String que_c = "none";
		    System.out.println("問題の基数を入力してください");
		    System.out.printf("現在の問題の基数: %d\n", question);
		    System.out.println("2 or 8 or 10 or 16 (整数のみを入力)");
		    que_c = scan.next();
		    
		    try{
			int temp_q = Integer.parseInt(que_c);
			
			switch(temp_q){
			case 2:
			case 8:
			case 10:
			case 16:
			    question = temp_q;
			    q_flag = true;
			    break;
			default:
			    break;
			}
		    }
		    catch(NumberFormatException e){
			System.out.println("整数のみを入力してください\n");
			continue;
		    }
		}while(!q_flag);
		
		do{
		    String sol_c = "none";
		    System.out.println("答えの基数を入力してください");
		    System.out.printf("現在の答えの基数: %d\n", solution);
		    System.out.println("2 or 8 or 10 or 16 (整数のみを入力)");
		    sol_c = scan.next();
		    
		    try{
			int temp_s = Integer.parseInt(sol_c);
			
			switch(temp_s){
			case 2:
			case 8:
			case 10:
			case 16:
			    solution = temp_s;
			    s_flag = true;
			    break;
			default:
			    break;
			}
		    }
		    catch(NumberFormatException e){
			System.out.println("整数のみを入力してください\n");
			continue;
		    }					
		}while(!s_flag);
		
		model.set_base(question, solution);
	    }
	    
	    model.reset();
	}while(reset_flag == 1);
    }
}
