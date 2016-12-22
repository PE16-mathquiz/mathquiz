
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Random;

class CalcModel extends Observable{

}

class CalcForm extends JTextField implements Observer, ActionLister{
    private CalcModel calcmodel;
    public CalcForm(){
	super(10);
	this.setFont(new Font(Font.SANS_SERIF,Font.BOLD,26));
    }
}

class CalcView extends JFrame{
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
	p3.add(new CalcForm());
	//p3.add(b3);
	this.pack();
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setVisible(true);
    }

    public static void main(String args[]){
	new CalcView();
    }
}
