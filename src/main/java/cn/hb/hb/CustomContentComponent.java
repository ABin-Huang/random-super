package cn.hb.hb;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class CustomContentComponent extends JPanel {
    private JTextField textField;
    private JButton button;
    private JRadioButton radioButton1;
    private JRadioButton radioButton2;
    private JRadioButton radioButton3;
    private ButtonGroup buttonGroup;
    private String selectedValue = "";

    public CustomContentComponent() {
        setLayout(new BorderLayout());

        // 创建一个文本框
        textField = new JTextField("Enter your name here", 20);
        add(textField, BorderLayout.NORTH);

        // 创建单选按钮
        buttonGroup = new ButtonGroup();
        radioButton1 = new JRadioButton("Option 1");
        radioButton2 = new JRadioButton("Option 2");
        radioButton3 = new JRadioButton("Option 3");

        buttonGroup.add(radioButton1);
        buttonGroup.add(radioButton2);
        buttonGroup.add(radioButton3);

        // 监听单选按钮的变化
        radioButton1.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                selectedValue = radioButton1.getText();
            }
        });

        radioButton2.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    selectedValue = radioButton2.getText();
                }
            }
        });

        radioButton3.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    selectedValue = radioButton3.getText();
                }
            }
        });

        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.Y_AXIS));
        radioPanel.add(radioButton1);
        radioPanel.add(radioButton2);
        radioPanel.add(radioButton3);

        add(radioPanel, BorderLayout.CENTER);

        // 创建一个按钮
        button = new JButton("Submit");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = textField.getText();
                System.out.println("我选择的是：" +selectedValue);
                JOptionPane.showMessageDialog(CustomContentComponent.this, "Hello, " + name + "! Selected Option: " + selectedValue);
            }
        });
        add(button, BorderLayout.SOUTH);
    }

//    private String getSelectedRadioButtonValue() {
//        ButtonModel selection = buttonGroup.getSelection();
//        Object[] selectedObjects = selection.getSelectedObjects();
//        AbstractButton selectedButton = (AbstractButton) selection;
////        if (selectedButton != null) {
////            return selectedButton.getText();
////        }
//        return "";
//    }

    public JTextField getTextField() {
        return textField;
    }

    public JRadioButton getRadioButton1() {
        return radioButton1;
    }

    public JRadioButton getRadioButton2() {
        return radioButton2;
    }

    public JRadioButton getRadioButton3() {
        return radioButton3;
    }
}
