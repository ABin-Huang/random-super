package cn.hb.hb;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.popup.ComponentPopupBuilder;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.awt.RelativePoint;

import java.awt.*;

public class DDDDDDAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // 获取当前项目的主窗口状态栏
        StatusBar statusBar = WindowManager.getInstance().getStatusBar(e.getProject());

        // 创建JBPopupFactory实例
        JBPopupFactory popupFactory = JBPopupFactory.getInstance();

        // 创建自定义的内容组件
        CustomContentComponent customContent = new CustomContentComponent(e);

        // 使用 JBPopupFactory 创建弹窗构建器，并指定内容组件和首选焦点组件
        ComponentPopupBuilder popupBuilder = JBPopupFactory.getInstance()
//                .createComponentPopupBuilder(new JLabel("Custom Popup"), textField)
                .createComponentPopupBuilder(customContent, customContent.getRadioButton1()) // 设置首选焦点组件为第一个单选框
                .setFocusable(true) // 允许弹窗接收焦点
                .setRequestFocus(true) // 请求焦点
                .setMovable(true) // 允许移动弹窗
                .setTitle("Random Super") // 设置弹窗标题
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
}
