
//swing is used for java frame, Jframe is a class in swing
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;


public class Calculator implements ActionListener{

    JFrame jf;
    JLabel DisplayLabel;
    JButton[] numberButtons;
    JButton ButtonDot, ButtonX, ButtonAC, ButtonSub,ButtonAdd,ButtonEqual,ButtonPercentage,ButtonSign,ButtonDivision ;
    String displayno="" ;
    Stack<Double> values = new Stack<>();
    Stack<Character> operators = new Stack<>();
   
/**
 * 
 */
public Calculator(){
    jf=new JFrame("Calculator");
    jf.setLayout(null);
    jf.setSize(500,700);
    jf.setVisible(true);
    jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    jf.setLocation(200,50);
    
   
    


    DisplayLabel=new JLabel();
    DisplayLabel.setBounds(25, 30, 440, 60);
    DisplayLabel.setHorizontalAlignment(SwingConstants.RIGHT);
    DisplayLabel.setBackground(Color.lightGray);
    DisplayLabel.setOpaque(true);
    jf.add(DisplayLabel);
    Font labelFont=new Font("Arial",Font.BOLD, 50);
    DisplayLabel.setFont(labelFont);

    numberButtons=new JButton[10];
    for(int i=0;i<=9;i++){
        numberButtons[i]=new JButton(String.valueOf(i));
        numberButtons[i].addActionListener(this);
    }

    ButtonAdd = new JButton("+");
    ButtonSub = new JButton("-");
    ButtonX = new JButton("*");
    ButtonDivision = new JButton("/");
    ButtonEqual = new JButton("=");
    ButtonDot = new JButton(".");
    ButtonAC = new JButton("C");
    ButtonPercentage= new JButton("%");
    ButtonSign= new JButton("+/-");
    ButtonDot=new JButton(".");


    
    ButtonAdd.addActionListener(this);
    ButtonSub.addActionListener(this);
    ButtonX.addActionListener(this);
    ButtonDivision.addActionListener(this);
    ButtonEqual.addActionListener(this);
    ButtonDot.addActionListener(this);
    ButtonAC.addActionListener(this);
    ButtonPercentage.addActionListener(this);
    ButtonSign.addActionListener(this);


    // Positioning buttons (simplified)
    
    ButtonDot.setBounds(250,500 , 80, 80);
    ButtonAdd.setBounds(350,400 , 80, 80);
    ButtonSub.setBounds(350,300 , 80, 80);
    ButtonX.setBounds(350,200 , 80, 80);
    ButtonDivision.setBounds(350,100 , 80, 80);
    ButtonEqual.setBounds(350,500 , 80, 80);
    ButtonAC.setBounds(50,100 , 80, 80);
    ButtonSign.setBounds(150,100 , 80, 80);
    ButtonPercentage.setBounds(250,100 , 80, 80);
    numberButtons[7].setBounds(50,200 , 80, 80);
    numberButtons[8].setBounds(150,200 , 80, 80);
    numberButtons[9].setBounds(250,200 , 80, 80);
    numberButtons[4].setBounds(50,300 , 80, 80);
    numberButtons[5].setBounds(150,300 , 80, 80);
    numberButtons[6].setBounds(250,300 , 80, 80);
    numberButtons[1].setBounds(50,400 , 80, 80);
    numberButtons[2].setBounds(150,400 , 80, 80);
    numberButtons[3].setBounds(250,400 , 80, 80);
    numberButtons[0].setBounds(50,500 , 180, 80);

    Font newFont=new Font("Arial",Font.BOLD,20);


    numberButtons[1].setFont(newFont);
    numberButtons[2].setFont(newFont);
    numberButtons[3].setFont(newFont);
    numberButtons[4].setFont(newFont);
    numberButtons[5].setFont(newFont);
    numberButtons[6].setFont(newFont);
    numberButtons[7].setFont(newFont);
    numberButtons[8].setFont(newFont);
    numberButtons[9].setFont(newFont);
    numberButtons[0].setFont(newFont);
    ButtonDot.setFont(newFont);
    ButtonAC.setFont(newFont);
    ButtonSign.setFont(newFont);
    ButtonPercentage.setFont(newFont);
    ButtonDivision.setFont(newFont);
    ButtonX.setFont(newFont);
    ButtonSub.setFont(newFont);
    ButtonAdd.setFont(newFont);
    ButtonEqual.setFont(newFont);


    jf.add(ButtonAdd);
    jf.add(ButtonSub);
    jf.add(ButtonX);
    jf.add(ButtonDivision);
    jf.add(ButtonEqual);
    jf.add(ButtonAC);
    jf.add(ButtonDot);
    jf.add(ButtonSign);
    jf.add(ButtonPercentage);
    for(int i=0;i<=9;i++){
        jf.add(numberButtons[i]);
    }


}


public static void main(String[] args) {
    new Calculator();
}
@Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        for (int i = 0; i < 10; i++) {
            if (source == numberButtons[i]) {
                displayno += i;
                DisplayLabel.setText(displayno);
                return;
            }
        }

        if (source == ButtonDot) {
            if (!displayno.contains(".")) {
                displayno += ".";
                DisplayLabel.setText(displayno);
            }
        } else if (source == ButtonAC) {
            displayno = "";
            values.clear();
            operators.clear();
            DisplayLabel.setText("");
        } else if (source == ButtonAdd || source == ButtonSub || source == ButtonX || source == ButtonDivision) {
            if (!displayno.isEmpty()) {
                values.push(Double.parseDouble(displayno));
                displayno = "";
            }

            char operator = ' ';
            if (source == ButtonAdd) operator = '+';
            if (source == ButtonSub) operator = '-';
            if (source == ButtonX) operator = '*';
            if (source == ButtonDivision) operator = '/';

            while (!operators.isEmpty() && precedence(operator) <= precedence(operators.peek())) {
                processOperation();
            }
            operators.push(operator);
        } else if (source == ButtonEqual) {
            if (!displayno.isEmpty()) {
                values.push(Double.parseDouble(displayno));
                displayno = "";
            }
            while (!operators.isEmpty()) {
                processOperation();
            }
            DisplayLabel.setText(String.valueOf(values.pop()));
        } else if (source == ButtonSign) {
            if (!displayno.isEmpty()) {
                double value = Double.parseDouble(displayno);
                value = value * -1;
                displayno = String.valueOf(value);
                DisplayLabel.setText(displayno);
            }
        } else if (source == ButtonPercentage) {
            if (!displayno.isEmpty()) {
                double value = Double.parseDouble(displayno);
                value = value / 100;
                displayno = String.valueOf(value);
                DisplayLabel.setText(displayno);
            }
        }
    }

    private void processOperation() {
        double b = values.pop();
        double a = values.pop();
        char op = operators.pop();

        double result = 0;
        switch (op) {
            case '+': result = a + b; break;
            case '-': result = a - b; break;
            case '*': result = a * b; break;
            case '/': result = a / b; break;
        }
        values.push(result);
    }

    private int precedence(char operator) {
        if (operator == '+' || operator == '-') return 1;
        if (operator == '*' || operator == '/') return 2;
        return 0;
    }
}