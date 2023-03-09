package graficInterfaceSwing.allCommands.buttonsCommands;

import appUsers.Student;
import appUsers.User;
import graficInterfaceSwing.allCommands.Command;
import uniSystem.courseTypes.Course;

import javax.swing.*;

public abstract class ButtonCommand<K> implements Command {

    JPanel displayPannel;
    User myUSer;

    public ButtonCommand(JPanel displayPannel, User myUSer) {
        this.displayPannel = displayPannel;
        this.myUSer = myUSer;
    }

    public JScrollPane findJScrollPanne(JPanel displayPannel) {
        JScrollPane scrollPane = null;
        for (int i =0; i < displayPannel.getComponentCount(); i++) {
            if(displayPannel.getComponent(i) instanceof JScrollPane)
                scrollPane = (JScrollPane) displayPannel.getComponent(i);
        }
        return scrollPane;
    }

    public JList<Course> findJList(JScrollPane scrollPane) {
        JList<Course> courseJList = null;
        if (scrollPane.getViewport().getView() instanceof JList) {
            courseJList = (JList<Course>) scrollPane.getViewport().getView();
        }
        return courseJList;
    }

    public JList<K> findJMyList(JScrollPane scrollPane) {
        JList<K> courseJList = null;
        if (scrollPane.getViewport().getView() instanceof JList) {
            courseJList = (JList<K>) scrollPane.getViewport().getView();
        }
        return courseJList;
    }

    public JTable findJTable(JScrollPane scrollPane) {
        if (scrollPane != null) {
            JTable table = null;
            if (scrollPane.getViewport().getView() instanceof JTable) {
                table = (JTable) scrollPane.getViewport().getView();
            }
            return table;
        }
        return null;
    }

    public abstract void execute();
}
