import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.GridLayout; // Import GridLayout

public class Calculator implements ActionListener {

    private JFrame jf;
    private JLabel displayLabel;
    private JButtonnumberButtons;
    private JButton buttonDot, buttonMultiply, buttonAC, buttonSubtract, buttonAdd, buttonEqual, buttonPercentage, buttonSign, buttonDivide;
    private String displayNo = "";
    private Stack<Double> values = new Stack<>();
    private Stack<Character> operators = new Stack<>();

    /**
     *
     */
    public Calculator() {
        jf = new JFrame("Calculator");
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setSize(500, 700);
        jf.setLocation(200, 50);

        // Use GridLayout for button arrangement
        jf.setLayout(null); //remove this line to use gridlayout
        

        displayLabel = new JLabel();
        displayLabel.setBounds(25, 30, 440, 60);
        displayLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        displayLabel.setBackground(Color.lightGray);
        displayLabel.setOpaque(true);
        jf.add(displayLabel);
        Font labelFont = new Font("Arial", Font.BOLD, 50);
        displayLabel.setFont(labelFont);

        numberButtons = new JButton[10];
        for (int i = 0; i <= 9; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].addActionListener(this);
        }

        buttonAdd = new JButton("+");
        buttonSubtract = new JButton("-");
        buttonMultiply = new JButton("*");
        buttonDivide = new JButton("/");
        buttonEqual = new JButton("=");
        buttonDot = new JButton(".");
        buttonAC = new JButton("C");
        buttonPercentage = new JButton("%");
        buttonSign = new JButton("+/-");
        

        buttonAdd.addActionListener(this);
        buttonSubtract.addActionListener(this);
        buttonMultiply.addActionListener(this);
        buttonDivide.addActionListener(this);
        buttonEqual.addActionListener(this);
        buttonDot.addActionListener(this);
        buttonAC.addActionListener(this);
        buttonPercentage.addActionListener(this);
        buttonSign.addActionListener(this);

        // Positioning buttons using GridLayout
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 10, 10)); // 5 rows, 4 columns, gaps
        buttonPanel.setBounds(25, 100, 440, 500); // Adjust as needed

        // Add buttons to the panel in the desired order
        buttonPanel.add(buttonAC);
        buttonPanel.add(buttonSign);
        buttonPanel.add(buttonPercentage);
        buttonPanel.add(buttonDivide);
        buttonPanel.add(numberButtons[7]);
        buttonPanel.add(numberButtons[8]);
        buttonPanel.add(numberButtons[9]);
        buttonPanel.add(buttonMultiply);
        buttonPanel.add(numberButtons[4]);
        buttonPanel.add(numberButtons[5]);
        buttonPanel.add(numberButtons[6]);
        buttonPanel.add(buttonSubtract);
        buttonPanel.add(numberButtons[1]);
        buttonPanel.add(numberButtons[2]);
        buttonPanel.add(numberButtons[3]);
        buttonPanel.add(buttonAdd);
        buttonPanel.add(numberButtons[0]);
        buttonPanel.add(buttonDot);
        buttonPanel.add(buttonEqual);


        jf.add(buttonPanel); // Add the panel to the frame
        

        Font newFont = new Font("Arial", Font.BOLD, 20);

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
        buttonDot.setFont(newFont);
        buttonAC.setFont(newFont);
        buttonSign.setFont(newFont);
        buttonPercentage.setFont(newFont);
        buttonDivide.setFont(newFont);
        buttonMultiply.setFont(newFont);
        buttonSubtract.setFont(newFont);
        buttonAdd.setFont(newFont);
        buttonEqual.setFont(newFont);

        jf.setVisible(true);
    }

    public static void main(Stringargs) {
        new Calculator();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        for (int i = 0; i < 10; i++) {
            if (source == numberButtons[i]) {
                displayNo += i;
                displayLabel.setText(displayNo);
                return;
            }
        }

        if (source == buttonDot) {
            if (!displayNo.contains(".")) {
                displayNo += ".";
                displayLabel.setText(displayNo);
            }
        } else if (source == buttonAC) {
            displayNo = "";
            values.clear();
            operators.clear();
            displayLabel.setText("");
        } else if (source == buttonAdd || source == buttonSubtract || source == buttonMultiply || source == buttonDivide) {
            if (!displayNo.isEmpty()) {
                values.push(Double.parseDouble(displayNo));
                displayNo = "";
            }

            char operator = ' ';
            if (source == buttonAdd) operator = '+';
            if (source == buttonSubtract) operator = '-';
            if (source == buttonMultiply) operator = '*';
            if (source == buttonDivide) operator = '/';

            while (!operators.isEmpty() && precedence(operator) <= precedence(operators.peek())) {
                processOperation();
            }
            operators.push(operator);
        } else if (source == buttonEqual) {
            if (!displayNo.isEmpty()) {
                values.push(Double.parseDouble(displayNo));
                displayNo = "";
            }
            while (!operators.isEmpty()) {
                processOperation();
            }
            if (!values.isEmpty()) {
                displayLabel.setText(String.valueOf(values.pop()));
            }
        } else if (source == buttonSign) {
            if (!displayNo.isEmpty()) {
                try {
                    double value = Double.parseDouble(displayNo);
                    value = value * -1;
                    displayNo = String.valueOf(value);
                    displayLabel.setText(displayNo);
                } catch (NumberFormatException ex) {
                    // Handle the case where displayNo is not a valid number
                    displayLabel.setText("Error");
                    displayNo = "";
                    values.clear();
                    operators.clear();
                }
            }
        } else if (source == buttonPercentage) {
            if (!displayNo.isEmpty()) {
                try {
                    double value = Double.parseDouble(displayNo);
                    value = value / 100;
                    displayNo = String.valueOf(value);
                    displayLabel.setText(displayNo);
                } catch (NumberFormatException ex) {
                    // Handle the case where displayNo is not a valid number
                    displayLabel.setText("Error");
                    displayNo = "";
                    values.clear();
                    operators.clear();
                }
            }
        }
    }

    private void processOperation() {
        if (values.size() < 2) {
            // Handle the case where there are not enough operands
            displayLabel.setText("Error");
            displayNo = "";
            values.clear();
            operators.clear();
            return;
        }

        double b = values.pop();
        double a = values.pop();
        char op = operators.pop();

        double result = 0;
        switch (op) {
            case '+':
                result = a + b;
                break;
            case '-':
                result = a - b;
                break;
            case '*':
                result = a * b;
                break;
            case '/':
                if (b == 0) {
                    // Handle division by zero
                    displayLabel.setText("Error");
                    displayNo = "";
                    values.clear();
                    operators.clear();
                    return;
                }
                result = a / b;
                break;
        }
        values.push(result);
    }

    private int precedence(char operator) {
        if (operator == '+' || operator == '-') return 1;
        if (operator == '*' || operator == '/') return 2;
        return 0;
    }
}
