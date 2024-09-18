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
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * The type Custom content component.
 */
public class CustomContentComponent extends JPanel implements Disposable {
    private static final Pattern NUMERIC_PATTERN = Pattern.compile("[0-9]*");

    private JTextField lengthText;
    private JTextField numText;
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

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        add(panel, BorderLayout.NORTH);

        // 数字长度
        JPanel lengthPanel = new JPanel();
        lengthPanel.setLayout(new BoxLayout(lengthPanel, BoxLayout.X_AXIS));
        lengthText = new JTextField("6", 5);
        Label label = new Label("number length");
        lengthPanel.add(label);
        lengthPanel.add(lengthText);
        addTextFieldEvent(lengthText);
        panel.add(lengthPanel, BorderLayout.NORTH);

        // 数字数量
        JPanel numPanel = new JPanel();
        numPanel.setLayout(new BoxLayout(numPanel, BoxLayout.X_AXIS));
        numText = new JTextField("6", 5);
        Label numberNumLabel = new Label("number num");
        numPanel.add(numberNumLabel);
        numPanel.add(numText);
        addTextFieldEvent(numText);
        panel.add(numPanel, BorderLayout.SOUTH);

        add(panel, BorderLayout.NORTH);

    }

    /**
     * Add text field event.
     *
     * @param textField the text field
     */
    public void addTextFieldEvent(JTextField textField) {
        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                removeInvalid(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                // No need to do anything when text is removed.
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // No need to do anything when document changes.
            }

            private void removeInvalid(DocumentEvent e) {
                javax.swing.text.Document doc = e.getDocument();
                try {
                    String text = doc.getText(0, doc.getLength());
                    if (!NUMERIC_PATTERN.matcher(text).matches()) {
                        SwingUtilities.invokeLater(() -> {
                            try {
                                doc.remove(e.getOffset(), e.getLength());
                            } catch (BadLocationException ex) {
                                ex.printStackTrace();
                            }
                        });
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
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
                BaseFuncParam param = getParam();
                Editor editor = actionEvent.getData(CommonDataKeys.EDITOR);
                Project project = actionEvent.getProject();
                System.out.println("我选择的：" +selectedValue);
                String res = null;
                try {
                    BaseFunction baseFunction = FuncFactory.get(TypeEnum.ofType(param.getFuncType()));
                    if (Objects.isNull(baseFunction)) {

                        throw new Exception("该类型未实现");
                    }
                    res = baseFunction.getResult(param);
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

    private BaseFuncParam getParam() {
        BaseFuncParam param = new BaseFuncParam();
        param.setResLength(StringUtils.isBlank(lengthText.getText())?6:Integer.parseInt(lengthText.getText()));
        param.setResNum(StringUtils.isBlank(numText.getText())?6:Integer.parseInt(numText.getText()));
        param.setFuncType(selectedValue);
        return param;

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
    public JTextField getLengthText() {
        return lengthText;
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

    public void setLengthText(JTextField lengthText) {
        this.lengthText = lengthText;
    }

    public JTextField getNumText() {
        return numText;
    }

    public void setNumText(JTextField numText) {
        numText = numText;
    }

    public void setRadioButton1(JRadioButton radioButton1) {
        this.radioButton1 = radioButton1;
    }

    public void setRadioButton2(JRadioButton radioButton2) {
        this.radioButton2 = radioButton2;
    }

    public void setRadioButton3(JRadioButton radioButton3) {
        this.radioButton3 = radioButton3;
    }

    public ButtonGroup getButtonGroup() {
        return buttonGroup;
    }

    public void setButtonGroup(ButtonGroup buttonGroup) {
        this.buttonGroup = buttonGroup;
    }

    public String getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedValue(String selectedValue) {
        this.selectedValue = selectedValue;
    }

    @Override
    public void dispose() {

    }
}
