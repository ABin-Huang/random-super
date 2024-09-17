package cn.hb.hb;


import cn.hb.core.BaseFuncParam;
import cn.hb.core.BaseFunction;
import cn.hb.enums.TypeEnum;
import cn.hb.factory.FuncFactory;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Objects;

/**
 * The type Custom content component.
 */
public class CustomContentComponent extends JPanel implements Disposable {
    private JTextField textField;
    private JButton button;
    private JRadioButton radioButton1;
    private JRadioButton radioButton2;
    private JRadioButton radioButton3;
    private ButtonGroup buttonGroup;
    private String selectedValue = "";

    /**
     * Instantiates a new Custom content component.
     *
     * @param actionEvent the action event
     */
    public CustomContentComponent(AnActionEvent actionEvent) {
        setLayout(new BorderLayout());

        // 创建一个文本框
//        textField = new JTextField("Enter your name here", 20);
//        add(textField, BorderLayout.NORTH);

        // 创建单选按钮
        buttonGroup = new ButtonGroup();
        radioButton1 = new JRadioButton(TypeEnum.INTEGER.getName());
        radioButton2 = new JRadioButton(TypeEnum.STRING.getName());
        radioButton3 = new JRadioButton(TypeEnum.ID.getName());

        buttonGroup.add(radioButton1);
        buttonGroup.add(radioButton2);
        buttonGroup.add(radioButton3);

        addButtonEvent(radioButton1, actionEvent);
        addButtonEvent(radioButton2, actionEvent);
        addButtonEvent(radioButton3, actionEvent);

        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.X_AXIS));
        radioPanel.add(radioButton1);
        radioPanel.add(radioButton2);
        radioPanel.add(radioButton3);

        add(radioPanel, BorderLayout.CENTER);

    }

    /**
     * 监听按钮事件.
     *
     * @param button      the button
     * @param actionEvent the action event
     */
    public void addButtonEvent(JRadioButton button, AnActionEvent actionEvent) {
        // 监听单选按钮的变化
        button.addItemListener(e -> {

            if (e.getStateChange() == ItemEvent.SELECTED) {

                selectedValue = button.getText();
                Editor editor = actionEvent.getData(CommonDataKeys.EDITOR);
                Project project = actionEvent.getProject();
                System.out.println("我选择的：" +selectedValue);
                String res = null;
                try {
                    BaseFunction baseFunction = FuncFactory.get(TypeEnum.ofType(selectedValue));
                    if (Objects.isNull(baseFunction)) {

                        throw new Exception("该类型未实现");
                    }
                    res = baseFunction.getResult(new BaseFuncParam());
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                insertTextAtMousePosition(editor, project, res);

                JComponent comp = (JComponent) e.getSource();
                Window win = SwingUtilities.getWindowAncestor(comp);
                win.dispose();
            }
        });
    }

    /**
     * Close panel.
     *
     * @param e the e
     */
    public static void closePanel(ActionEvent e) {
        JComponent comp = (JComponent) e.getSource();
        Window win = SwingUtilities.getWindowAncestor(comp);
        win.dispose();
    }

    /**
     * Insert text at mouse position.
     *
     * @param editor  the editor
     * @param project the project
     */
    public static void insertTextAtMousePosition(Editor editor, Project project, String result) {
        WriteCommandAction.runWriteCommandAction(project, () -> {
            Document document = editor.getDocument();
            int offset = editor.getCaretModel().getCurrentCaret().getOffset();
            document.insertString(offset, result);
            // 将光标移动到插入文本的结束位置
            CaretModel caretModel = editor.getCaretModel();
            caretModel.moveToOffset(offset + result.length());
        });
    }

    /**
     * Gets text field.
     *
     * @return the text field
     */
    public JTextField getTextField() {
        return textField;
    }

    /**
     * Gets radio button 1.
     *
     * @return the radio button 1
     */
    public JRadioButton getRadioButton1() {
        return radioButton1;
    }

    /**
     * Gets radio button 2.
     *
     * @return the radio button 2
     */
    public JRadioButton getRadioButton2() {
        return radioButton2;
    }

    /**
     * Gets radio button 3.
     *
     * @return the radio button 3
     */
    public JRadioButton getRadioButton3() {
        return radioButton3;
    }


    @Override
    public void dispose() {

    }
}
