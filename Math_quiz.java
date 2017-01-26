import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Random;

class CalcModel extends Observable {
    private String value;
    private int dec_int;
    private String dec, bin, oct, hex;

    private int que, sol, q_base, a_base; // 問題形式指定

    private String quejp, quenum, answer;

    private int qcount = 0, ccount = 0;


    // 基数既定時
    public CalcModel(int q, int s)
    {
        this.que = q;
        this.sol = s;
        this.reset();
    }

    // 基数未定時
    public CalcModel()
    {
        this.rand_base();
    }

    public void set_base(int q, int s)
    {
        this.que = q;
        this.sol = s;
    }

    public String get_quejp()
    {
        return quejp;
    }

    public String get_quenum()
    {
        return quenum;
    }

    public String get_answer()
    {
        return answer;
    }

    public int get_ccount()
    {
        return ccount;
    }

    public int get_qcount()
    {
        return qcount;
    }

    public void reset()
    {
        dec_int = (int) (Math.random() * Math.pow(2, 10));
        bin     = Integer.toBinaryString(dec_int);
        oct     = Integer.toOctalString(dec_int);
        dec     = String.valueOf(dec_int);
        hex     = Integer.toHexString(dec_int).toUpperCase();
        String nums[] = new String[4];
        nums[0] = bin; nums[1] = oct; nums[2] = dec; nums[3] = hex;
        int bases[] = new int[4];
        bases[0] = 2; bases[1] = 8; bases[2] = 10; bases[3] = 16;
        quejp    = String.format("次の%d進数を%d進数に変換しなさい", bases[que], bases[sol]);
        quenum   = "none";

        switch (que) {
            case 0:
                quenum = String.format("%10s", nums[que]).replace(' ', '0');
                break;
            case 1:
                quenum = "0" + nums[que];
                break;
            case 2:
                quenum = nums[que];
                break;
            case 3:
                quenum = "0x" + nums[que];
                break;
        }
        answer = "none";
        answer = nums[sol];
    }

    public String check_answer()
    {
        String reply    = value.toUpperCase(); // 大文字小文字対応
        String correct  = "正解！！";
        String Ncorrect = "不正解.."; // ActionListenerへ返す
        if (sol == 16 && reply.startsWith("0X")) {
            reply = reply.substring(2);
        }
        else if (sol == 8 && reply.startsWith("0")) {
            reply = reply.substring(1);
        }
        qcount++;
        if (reply.equals(answer)) {
            ccount++;

            return correct;
        }
        else {
            return Ncorrect;
        }
    }

    public void rand_base()
    {
        int sbase;
        int qran = (int) (Math.random() * 4); // 0 ~ 3までの乱数
        int sran = (int) (Math.random() * 3) + 1; // 被り防止のため1足して1 ~ 3
        sbase = (qran + sran) % 4;
        this.set_base(qran, sbase);
        this.reset();
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String s)
    {
        value = s;
        setChanged();
        notifyObservers();
    }
}


class CalcForm extends JTextField implements ActionListener {
    private CalcModel calcmodel;

    public CalcForm(CalcModel cm)
    {
        super(10);
        calcmodel = cm;
        this.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 26));
        this.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e)
    {
        String s = this.getText();
        calcmodel.setValue(s);
        this.setEditable(false);
    }
}


class CalcView extends JFrame implements Observer, ActionListener {
    private CalcModel calcmodel = new CalcModel();
    private CalcForm calcform = new CalcForm(calcmodel);
    private String quejp, quenum;
    private JLabel qlabel, nlabel;
    private JLabel clabel; // 正誤判定
    private JLabel alabel; // 正解表示
    private JLabel colabel; // 正答率
    private JButton cont, fin, stat;
    private JPanel p1 = new JPanel(), p2 = new JPanel();
    private JPanel p3 = new JPanel(), p4 = new JPanel();

    public CalcView()
    {
        quejp  = calcmodel.get_quejp();
        quenum = calcmodel.get_quenum();

        qlabel  = new JLabel(quejp);
        nlabel  = new JLabel(quenum);
        clabel  = new JLabel("計算クイズ");
        alabel  = new JLabel();
        colabel = new JLabel();
        qlabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        nlabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        clabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));
        alabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
        colabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 17));

        this.setTitle("Calculation Quiz");
        this.setLayout(new GridLayout(4, 1));
        this.add(p1);
        this.add(p2);
        this.add(p3);
        this.add(p4);
        calcmodel.addObserver(this);

        p1.setLayout(new GridLayout(2, 1));
        qlabel.setHorizontalAlignment(JLabel.CENTER);
        nlabel.setHorizontalAlignment(JLabel.CENTER);
        p1.add(qlabel);
        p1.add(nlabel);

        p2.add(calcform);
        fin  = new JButton("終了");
        stat = new JButton("開始");
        cont = new JButton("続ける");
        fin.setActionCommand("finish");
        stat.setActionCommand("stat");
        cont.setActionCommand("continue");
        fin.addActionListener(this);
        cont.addActionListener(this);
        stat.addActionListener(this);
        p2.add(cont);
        p2.add(fin);
        p2.add(stat);

        p3.setLayout(new GridLayout(2, 1));
        clabel.setHorizontalAlignment(JLabel.CENTER);
        alabel.setHorizontalAlignment(JLabel.CENTER);
        p3.add(clabel);
        p3.add(alabel);

        colabel.setHorizontalAlignment(JLabel.CENTER);
        p4.add(colabel);

        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        // これらは開始時にtrueとなる
        p1.setVisible(false);
        cont.setVisible(false);
        calcform.setVisible(false);
    }

    public void update(Observable o, Object arg)
    {
        this.clabel.setText(calcmodel.check_answer());
        alabel.setText("正解は" + calcmodel.get_answer());
        cont.setEnabled(true);
    }

    public void questioninit()
    {
        calcmodel.rand_base();
        String car = String.format("%.2f", (float) calcmodel.get_ccount() / (float) calcmodel.get_qcount() * 100);
        this.qlabel.setText(calcmodel.get_quejp());
        this.nlabel.setText(calcmodel.get_quenum());
        clabel.setText("答えを入力したらEnterを押して下さい。");
        alabel.setText("");
        colabel.setText("正答率: " + calcmodel.get_ccount() + "/" + calcmodel.get_qcount() + "=" + car + "%");
        calcform.setEditable(true);
        calcform.setText("");
        cont.setEnabled(false);
    }

    public void actionPerformed(ActionEvent e)
    {
        String es = e.getActionCommand();
        if (es.equals("finish")) {
            // Shutdown Sequence
            Component c = (Component) e.getSource();
            Window    w = SwingUtilities.getWindowAncestor(c);
            w.dispose();
        } else if (es.equals("continue")) {
            questioninit();
        } else {
            // Start Sequence
            p1.setVisible(true);
            cont.setVisible(true);
            calcform.setVisible(true);
            stat.setVisible(false);
            cont.setEnabled(false);
            clabel.setText("答えを入力したらEnterを押して下さい。");
            clabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 17));
        }
    }

    public static void main(String args[])
    {
        new CalcView();
    }
}
