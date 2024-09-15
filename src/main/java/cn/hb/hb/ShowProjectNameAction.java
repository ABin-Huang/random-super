package cn.hb.hb;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

public class ShowProjectNameAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        System.out.println("hello first plugin");
        Project project = e.getProject();
        Messages.showMessageDialog("你好世界", "hhh ", Messages.getInformationIcon());
    }
}
