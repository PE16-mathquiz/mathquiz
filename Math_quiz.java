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
    private int count=0;//簡単なスコアカウンター→０で初期化

    //コンストラクタ(基数が既定の場合)
    public CalcModel(int q, int s){
    	this.que = q;
		this.sol = s;
	    
		this.reset();
    }
    
    //コンストラクタ(基数が未定の場合)
    public CalcModel(){
    	this.ran_base();
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
	String correct="正解！！";//ActionListener煮かえすための文字列
	String Ncorrect="不正解..";//同上
	
    	if(reply.equals(answer)){
	    count++;
	    return correct;
	}
	else{
	    return Ncorrect;
	}
    }
    
    //基数をランダムで代入する関数
    public void ran_base(){
    	int qbase = -1, sbase = -1;
    	int qran = (int)(Math.random() * 4); //0 ~ 3までの乱数
    	int sran = (int)(Math.random() * 3); //0 ~ 2までの乱数
    	
    	switch(qran){
    		case 0:
    			qbase = 2; //問題の基数: 2進数
    			switch(sran){
    				case 0: 
    					sbase = 8; //答えの基数: 8進数
    					break;
    				case 1:
    					sbase = 10; //答えの基数: 10進数
    					break;
    				case 2:
    					sbase = 16; //答えの基数: 16進数
    					break;
    			}
    			break;
    		case 1:
    			qbase = 8; //問題の基数: 8進数
    			switch(sran){
    				case 0: 
    					sbase = 2; //答えの基数: 2進数
    					break;
    				case 1:
    					sbase = 10; //答えの基数: 10進数
    					break;
    				case 2:
    					sbase = 16; //答えの基数: 16進数
    					break;
    			}
    			break;
    		case 2:
    			qbase = 10; //問題の基数: 10進数
    			switch(sran){
    				case 0: 
    					sbase = 2; //答えの基数: 2進数
    					break;
    				case 1:
    					sbase = 8; //答えの基数: 8進数
    					break;
    				case 2:
    					sbase = 16; //答えの基数: 16進数
    					break;
    			}
    			break;
    		case 3:
    			qbase = 16; //問題の基数: 16進数
    			switch(sran){
    				case 0: 
    					sbase = 2; //答えの基数: 2進数
    					break;
    				case 1:
    					sbase = 8; //答えの基数: 8進数
    					break;
    				case 2:
    					sbase = 10; //答えの基数: 10進数
    					break;
    			}
    			break;
    	}
    	
    	this.set_base(qbase, sbase);
    	this.reset();
    	
    	return;
    }
    
    //ここまで
    
    
    public String getValue(){
	return value;
    }
    
    public void setValue(String s){
	value=s; //String出処理をするため直す必要なし。
	//System.out.println(value); //valueに値が入っているかの確認。ターミナル上に表示。

	setChanged();
	notifyObservers();
    }
}



//入力フォームの前身
class CalcForm extends JTextField implements ActionListener{
    private CalcModel calcmodel;

    //コンストラクタ
    public CalcForm(CalcModel cm){
	super(10);
	calcmodel=cm;
	//calcmodel.addObserver(this); //Observerの登録
	this.setFont(new Font(Font.SANS_SERIF,Font.BOLD,26));
	this.addActionListener(this);
    }

    /*public void update (Observable o,Object arg){
	String s=calcmodel.getValue();
	setText(s);
	}*/

    public void actionPerformed(ActionEvent e){
	String s=this.getText();
	calcmodel.setValue(s);
    }
}

//とりあえずの枠組み
class CalcView extends JFrame implements Observer,ActionListener{
    private boolean IsTitle;
    //private CalcModel calcmodel=new CalcModel(10,16);
    private CalcModel calcmodel=new CalcModel();
    private CalcForm calcform = new CalcForm(calcmodel);
    //private CalcButton calcbutton=new CalcButton();
    private String quejp,quenum;//問題文と出題内容の変数
    private JLabel qlabel, nlabel;//問題文, 変換する数を表示するためのラベル変数
    private JLabel clabel;//正誤判定を表示するためのラベル変数
    private JButton cont,fin,stat;//継続終了を選択するためのボタン変数
    private JPanel p2 = new JPanel();

    //コンストラクタ
    public CalcView(){
	quejp=calcmodel.get_quejp();
	quenum=calcmodel.get_quenum();
	JPanel p1=new JPanel(), p3=new JPanel();
	qlabel=new JLabel(quejp);
	nlabel = new JLabel(quenum);
	this.setTitle("Calcuration Quiz");
	this.setLayout(new GridLayout(3,1));
	this.add(p1);
	this.add(p2);
	this.add(p3);
	IsTitle = true;
	calcmodel.addObserver(this);	
	clabel=new JLabel("計算クイズ");
	//上段:問題文
	p1.setLayout(new GridLayout(2, 1));
	qlabel.setHorizontalAlignment(JLabel.CENTER);
	nlabel.setHorizontalAlignment(JLabel.CENTER);
	p1.add(qlabel);
	p1.add(nlabel);
	//中段:問題回答フォーム
	p2.add(calcform);
	//下段:継続選択ボタン→なぜか中段にまとまってしまった。
	p3.add(clabel);
	fin=new JButton("終了");
	stat=new JButton("開始");
	cont=new JButton("続ける");
	p2.add(cont);
	cont.setActionCommand("continue");
	p2.add(fin);
	p2.add(stat);
	fin.setActionCommand("finish");
	stat.setActionCommand("stat");
	fin.addActionListener(this);
	cont.addActionListener(this);
	stat.addActionListener(this);
	this.pack();
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setVisible(true);
	cont.setVisible(false);
    }

    public void update(Observable o,Object arg){
	this.clabel.setText(calcmodel.ans_q());
    }
    public void questioninit(){
	calcmodel.ran_base();
	this.qlabel.setText(calcmodel.get_quejp());
	this.nlabel.setText(calcmodel.get_quenum());
	clabel.setText("答えを入力したらEnterを押して下さい。");
	calcform.setText("");
	
	/*処理を一時停止させるための部分
	  try{
	  Thread.sleep(5000);
	  }catch(InterruptedException ee){}
	*/
	//new CalcView();	
}


  //継続or終了ActionListener
    public void actionPerformed(ActionEvent e){
	String es=e.getActionCommand();
	if(es.equals("finish")){//終了ボタンを押した場合
	    //this.qlabel.setText(calcmodel.ans_q());

	    //終了を押したときにシステムをすべて落とすためのコード
	        //→終了もスペースキーで回答を確定していなければできない模様。
	    Component c = (Component)e.getSource();
	    Window w = SwingUtilities.getWindowAncestor(c);
	    w.dispose();

	}else if(es.equals("continue") && IsTitle == false){//継続ボタンを押した場合
	    //calcmodel.reset();
	    questioninit();
	}
	else{//開始ボタンを押した場合
	    IsTitle = false;
	    cont.setVisible(true);
	    stat.setVisible(false);
	    // 引用元: https://goo.gl/mCnVKh
	    questioninit();
	}
    }
    
    public static void main (String args[]){
	new CalcView();
    }
}

/*
アップデート内容
2017/1/5
・継続、終了ボタンの実装。終了ボタンを押すとシステムがすべて終了するようにしました。(１０進→１６進に限る)
・問題のランダム出題実装。
・次へのボタンを押すことで正誤判断。→自動的に次のランダム問題を出題。
・

問題点
2017/1/5
・回答入力後、スペースキーを押さなければ回答が確定せず、うっかり次へのボタンを押してしまうととんでもない量のエラーで怒られる
・回答の正誤確認画面に切り替わる時間が短く、あっているのかあっていないのかがわからない。
  →wait(),Thread.sleep()を入れてみたがなかなか改善されなかった。

次回まで、または次回からやること
2017/1/5
・コントローラークラスを導入。ActionListenerなどを補完してほかの部分をすっきりさせる。
・次に出題される基数の組合わせを変えることができるようにする。
  →ボタンの個数を押すごとに変えれるようにすると、多少はやりやすいか...?
・終了するときにやってきた問題のスコアを表示できるようにすれば、ちょっとは使い手側に親切か...。

とりあえず問題出題→回答入力(スペースキーで確定)→次へボタンを押して次のランダム問題出題→......
まではものになりました。

 */
