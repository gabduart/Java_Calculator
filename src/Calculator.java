import java.awt.*;
import java.awt.event.*;
import java.util.Stack;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Calculator {
    int boardWidth = 360;
    int boardHeight = 540;

    Color CustomGraniteGray = new Color(95, 99, 104);
    Color CustomOldSilver = new Color(128, 134, 139);
    Color CustomArsenic = new Color(60, 64, 67);
    Color CustomBlueberry = new Color(66, 133, 244);

    JFrame frame = new JFrame("Calculator");
    JLabel displayLabel = new JLabel();
    JPanel displayPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();

    String expression = "";

    String[] buttonValues = {
            "AC", "+/-", "%", "÷",
            "7", "8", "9", "x",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "0", ".", "√", "="
    };

    String[] graySymbols = {"AC", "+/-", "%", "÷", "x", "-", "+"};
//    String[] rightSymbols = {"÷", "x", "-", "+", "="};
//    String[] topSymbols = {"AC", "+/-", "%"};

    public Calculator() {
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        displayLabel.setBackground(CustomArsenic);
        displayLabel.setForeground(Color.white);
        displayLabel.setFont(new Font("Arial", Font.PLAIN, 60));
        displayLabel.setHorizontalAlignment(JLabel.RIGHT);
        displayLabel.setText("0");
        displayLabel.setOpaque(true);

        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(displayLabel);
        frame.add(displayPanel, BorderLayout.NORTH);

        buttonsPanel.setLayout(new GridLayout(5, 4));
        buttonsPanel.setBackground(CustomGraniteGray);
        frame.add(buttonsPanel);

        for (String buttonValue : buttonValues) {
            JButton button = new JButton(buttonValue);
            button.setFont(new Font("Arial", Font.PLAIN, 23));
            button.setFocusable(false);
            button.setBorder(new LineBorder(CustomArsenic));

            if (Arrays.asList(graySymbols).contains(buttonValue)) {
                button.setBackground(CustomGraniteGray);
                button.setForeground(Color.white);
            } else if (buttonValue.equals("=")) {
                button.setBackground(CustomBlueberry);
                button.setForeground(Color.white);
            } else {
                button.setBackground(CustomOldSilver);
                button.setForeground(Color.white);
            }

            buttonsPanel.add(button);
            button.addActionListener(e -> handleButtonClick(button.getText()));
        }

        frame.setVisible(true);
    }

    private void handleButtonClick(String buttonValue) {
        if (buttonValue.equals("AC")) {
            expression = "";
            displayLabel.setText("0");
        } else if (buttonValue.equals("+/-")) {
            if (!expression.isEmpty()) {
                expression = "-" + expression;
                displayLabel.setText(expression);
            }
        } else if (buttonValue.equals("%")) {
            try {
                double value = Double.parseDouble(expression);
                expression = removeZeroDecimal(value / 100);
                displayLabel.setText(expression);
            } catch (NumberFormatException ex) {
                displayLabel.setText("Erro");
            }
        } else if (buttonValue.equals("√")) {
            try {
                double value = Double.parseDouble(expression);
                if (value >= 0) {
                    expression = removeZeroDecimal(Math.sqrt(value));
                    displayLabel.setText(expression);
                } else {
                    displayLabel.setText("Erro");
                }
            } catch (NumberFormatException ex) {
                displayLabel.setText("Erro");
            }
        } else if (buttonValue.equals("=")) {
            try {
                double result = evaluateExpression(expression);
                expression = removeZeroDecimal(result);
                displayLabel.setText(expression);
            } catch (Exception ex) {
                displayLabel.setText("Erro");
            }
        } else {
            expression += buttonValue;
            displayLabel.setText(expression);
        }
    }

    private double evaluateExpression(String expr) {
        expr = expr.replace("x", "*").replace("÷", "/");
        return evaluatePostfix(infixToPostfix(expr));
    }

    private String removeZeroDecimal(double num) {
        return (num % 1 == 0) ? Integer.toString((int) num) : Double.toString(num);
    }

    private String infixToPostfix(String infix) {
        StringBuilder output = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        for (char ch : infix.toCharArray()) {
            if (Character.isDigit(ch) || ch == '.') {
                output.append(ch);
            } else {
                output.append(' ');
                while (!stack.isEmpty() && precedence(stack.peek()) >= precedence(ch)) {
                    output.append(stack.pop()).append(' ');
                }
                stack.push(ch);
            }
        }
        while (!stack.isEmpty()) {
            output.append(' ').append(stack.pop());
        }

//        System.out.println(output.toString());
        return output.toString();
    }

    private double evaluatePostfix(String postfix) {
        Stack<Double> stack = new Stack<>();
        for (String token : postfix.split(" ")) {
            if (token.isEmpty()) continue;
            if (token.matches("[-+]?\\d*\\.?\\d+")) {
                stack.push(Double.parseDouble(token));
            } else {
                double b = stack.pop();
                double a = stack.pop();
                switch (token) {
                    case "+" -> stack.push(a + b);
                    case "-" -> stack.push(a - b);
                    case "*" -> stack.push(a * b);
                    case "/" -> stack.push(a / b);
                }
            }
        }
        return stack.pop();
    }

    private int precedence(char op) {
        return (op == '+' || op == '-') ? 1 : (op == '*' || op == '/') ? 2 : 0;
    }
}