package cn.hb.hb;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.ComponentPopupBuilder;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.awt.RelativePoint;

import javax.swing.*;
import javax.swing.event.HyperlinkListener;
import java.awt.*;

public class DDDDDDAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        System.out.println(1111);
        // 获取当前项目的主窗口状态栏
        StatusBar statusBar = WindowManager.getInstance().getStatusBar(e.getProject());

        // 创建JBPopupFactory实例
        JBPopupFactory popupFactory = JBPopupFactory.getInstance();

        // 创建一个带有自定义内容的弹窗
//        Balloon balloon = popupFactory.createHtmlTextBalloonBuilder(
//                "<html><body><p style='color:#00FF00;'>Action performed<br>操作已执行</p></body></html>", // HTML格式文本内容
//                null, // 不提供图标
//                statusBar.getComponent()[0], // 在状态栏的第一个组件的位置显示
//                Balloon.Position.atRight, // 以状态栏第一个组件的右侧显示
//                null // 可以添加一个Listener来监听关闭事件
//        ).createBalloon();

        // popupFactory.createConfirmation("hb_tab", null, 1).showInFocusCenter();

        // 创建自定义的内容组件
        CustomContentComponent customContent = new CustomContentComponent();

        // 创建自定义的 JComponent (例如一个文本框)
        CustomTextField textField = new CustomTextField("Enter some text");

        // 使用 JBPopupFactory 创建弹窗构建器，并指定内容组件和首选焦点组件
        ComponentPopupBuilder popupBuilder = JBPopupFactory.getInstance()
//                .createComponentPopupBuilder(new JLabel("Custom Popup"), textField)
                .createComponentPopupBuilder(customContent, customContent.getRadioButton1()) // 设置首选焦点组件为第一个单选框
                .setFocusable(true) // 允许弹窗接收焦点
                .setRequestFocus(true) // 请求焦点
                .setMovable(true) // 允许移动弹窗
                .setTitle("First Plugin") // 设置弹窗标题
                .setCancelOnClickOutside(false) // 阻止点击外部区域关闭弹窗
                .setResizable(true) // 允许调整大小
                .setModalContext(false) // 不阻止其他操作
                .setCancelOnOtherWindowOpen(true); // 其他窗口打开时取消弹窗

        // 创建弹窗
        JBPopup popup = popupBuilder.createPopup();

        // 计算屏幕中心点
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        int popupWidth = popup.getContent().getWidth();
        int popupHeight = popup.getContent().getHeight();
        int x = (screenWidth - popupWidth) / 2;
        int y = (screenHeight - popupHeight) / 2;

        // 设置弹窗的位置
        RelativePoint centerPoint = new RelativePoint(new Point(x, y));

        // 显示弹窗
        popup.show(centerPoint);

    }

    public class CustomComponent extends JComponent {

        private String message = "Hello, Custom Component!";

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // 绘制背景颜色
            g.setColor(Color.lightGray);
            g.fillRect(0, 0, getWidth(), getHeight());

            // 绘制矩形
            g.setColor(Color.BLUE);
            g.drawRect(10, 10, 100, 50);

            // 绘制文本
            g.setColor(Color.BLACK);
            Font font = new Font("Serif", Font.BOLD, 18);
            g.setFont(font);
            g.drawString(message, 15, 70);
        }

        public void setMessage(String message) {
            this.message = message;
            repaint(); // 强制重新绘制组件
        }
    }
}
