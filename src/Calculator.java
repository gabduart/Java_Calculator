import java.awt.*;
import java.awt.event.*;
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

    String[] buttonValues = {
            "(", ")", "%", "AC",
            "7", "8", "9", "÷",
            "4", "5", "6", "x",
            "1", "2", "3", "-",
            "0", ".", "=", "+"
    };

    String[] rightSymbols = {"AC", "÷", "x", "-", "+"};
    String[] topSymbols = {"(", ")", "%"};
    String[] graySymbols = {"(", ")", "%", "AC", "÷", "x", "-", "+"};

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
                button.setBackground(CustomOldSilver);
                button.setForeground(Color.white);
            } else if (buttonValue.equals("=")) {
                button.setBackground(CustomBlueberry);
                button.setForeground(Color.white);
            } else {
                button.setBackground(CustomGraniteGray);
                button.setForeground(Color.white);
            }

            buttonsPanel.add(button);
        }

        frame.setVisible(true);
    }
}
