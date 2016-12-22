
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Random;

class CalcModel extends Observable{
    private int value; //フォームに入力された値の保存先

    public int getValue(){
	return value;
    }

    public void setValue(String s){
	value=Integer.parseInt(s);
	System.out.println(value); //valueに値が入っているかの確認。ターミナル上に表示。
    }
}

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
	String s=Integer.toString(calcmodel.getValue());
	setText(s);
    }

    public void actionPerformed(ActionEvent e){
	String s=this.getText();
	calcmodel.setValue(s);
    }
}

class CalcView extends JFrame{
    private CalcModel calcmodel=new CalcModel();
    public CalcView(){
	JPanel p1=new JPanel(),p2=new JPanel(),p3=new JPanel();
	JButton b1=new JButton("North!!");
	JButton b2=new JButton("Center!!");
	//JButton b3=new JButton("South!!");
	this.setTitle("Calcuration Quiz");
	//this.setSize(400,200);
	this.setLayout(new GridLayout(3,1));
	this.add(p1);
	this.add(p2);
	this.add(p3);
	p1.add(b1);
	p2.add(b2);
	p3.add(new CalcForm(calcmodel));
	//p3.add(b3);
	this.pack();
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setVisible(true);
    }

    public static void main(String args[]){
	new CalcView();
    }
}
