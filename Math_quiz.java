
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Random;

class CalcModel extends Observable{
    private String value; //フォームに入力された値の保存先かつ答え

    //ここから先、コピーしたものになります。

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
	
    //正解数
    private int count=0;

    //コンストラクタ
    public CalcModel(int q, int s){
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
    
    //答えを入力してもらって確認する
    public String ans_q(){
    	String reply = value.toUpperCase();  //入力された16進数が小文字でも正解と判定するため
	String correct="正解！！";
	String Ncorrect="不正解..";
	
    	if(reply.equals(answer)){
	    count++;
	    return correct;
	}
	else{
	    return Ncorrect;
	}
    }
    
    
    //ここまで
    
    
    public String getValue(){
	return value;
    }
    
    public void setValue(String s){
	value=s; //String出処理をするため直す必要なし。
	System.out.println(value); //valueに値が入っているかの確認。ターミナル上に表示。
    }
}



//入力フォームの前身
class CalcForm extends JTextField implements Observer,ActionListener{
    private CalcModel calcmodel;
    public CalcForm(CalcModel cm){
	super(10);
	calcmodel=cm;
	calcmodel.addObserver(this); //Observerの登録
	this.setFont(new Font(Font.SANS_SERIF,Font.BOLD,26));
	this.addActionListener(this);
    }

    public void update (Observable o,Object arg){
	String s=calcmodel.getValue();
	setText(s);
    }

    public void actionPerformed(ActionEvent e){
	String s=this.getText();
	calcmodel.setValue(s);
    }
}

//とりあえずの枠組み
class CalcView extends JFrame implements ActionListener{
    private CalcModel calcmodel=new CalcModel(10,16);
    //private CalcButton calcbutton=new CalcButton();
    private String quejp,quenum;
    private JLabel l1;
    private JButton b2;
    public CalcView(){
	quejp=calcmodel.get_quejp();
	quenum=calcmodel.get_quenum();
	JPanel p1=new JPanel(),p2=new JPanel(),p3=new JPanel();
	l1=new JLabel(quejp+quenum);
	JLabel l2;
	//JButton b2=new JButton("Center!!");
	//JButton b3=new JButton("South!!");
	this.setTitle("Calcuration Quiz");
	this.setLayout(new GridLayout(3,1));
	this.add(p1);
	this.add(p2);
	this.add(p3);

	//上段:問題文
	p1.add(l1);
	//中段:問題回答フォーム
	p2.add(new CalcForm(calcmodel));
	//下段:継続選択ボタン
	p3.setLayout(new GridLayout(1,2));
	JButton b1=new JButton("終了");
	b2=new JButton("続ける");
	p2.add(b1);
	p2.add(b2);
	b1.setActionCommand("finish");
	b2.setActionCommand("continue");
	b1.addActionListener(this);
	b2.addActionListener(this);

	this.pack();
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setVisible(true);
    }

  //継続or終了ActionListener
    public void actionPerformed(ActionEvent e){
	String es=e.getActionCommand();
	if(es.equals("finish")){
	    this.l1.setText(calcmodel.ans_q());

	    Component c = (Component)e.getSource();
	    Window w = SwingUtilities.getWindowAncestor(c);
	    w.dispose();
	}else if(es.equals("continue")){
	    calcmodel.reset();
	    this.l1.setText(calcmodel.ans_q());
	    try{
		Thread.sleep(5000);
	    }catch(InterruptedException ee){}

	    new CalcView();
	}
    }
    
    public static void main (String args[]){
	new CalcView();
    }
}

class CalcController{
    private CalcModel calcmodel=new CalcModel(10,16);
    private CalcForm calcform=new CalcForm(calcmodel);
    private CalcView calcview=new CalcView();
    private String consequens;//正解or不正解

    //コンストラクタ
    public CalcController(){

    }

    //正誤判定
    public void judge(){
	consequens=calcmodel.ans_q();

    }
}
