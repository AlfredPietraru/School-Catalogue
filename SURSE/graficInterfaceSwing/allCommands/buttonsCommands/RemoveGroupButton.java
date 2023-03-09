package graficInterfaceSwing.allCommands.buttonsCommands;

import appUsers.User;
import graficInterfaceSwing.UserFrame;
import graficInterfaceSwing.allCommands.Command;
import uniSystem.Catalog;
import uniSystem.Group;
import uniSystem.courseTypes.Course;

import javax.swing.*;

public class RemoveGroupButton extends ButtonCommand {
    UserFrame userFrame;
    Course course;

    public RemoveGroupButton(UserFrame userFrame, Course course) {
        super(userFrame.getDisplayPannel(), userFrame.getUser());
        this.userFrame = userFrame;
        this.course = course;
    }

    @Override
    public void execute() {
        JList<Group> groupJList = findJMyList(findJScrollPanne(userFrame.getListPannel()));
        Group selectedElement = groupJList.getSelectedValue();
        course.getGroupHashMap().remove(selectedElement);
        ((DefaultListModel)groupJList.getModel()).removeElementAt(groupJList.getSelectedIndex());
    }
}
