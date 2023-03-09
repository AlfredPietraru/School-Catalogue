package graficInterfaceSwing.allCommands.buttonsCommands;

import graficInterfaceSwing.UserFrame;
import uniSystem.SearchSystem;
import uniSystem.courseTypes.Course;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.StringTokenizer;

public class DeleteButtonCommand extends ButtonCommand {
    UserFrame userFrame;

    public DeleteButtonCommand(UserFrame userFrame) {
        super(userFrame.getDisplayPannel(), userFrame.getUser());
        this.userFrame = userFrame;
    }

    @Override
    public void execute() {
        JScrollPane scrollPane = findJScrollPanne(userFrame.getDisplayPannel());
        JTable table = findJTable(scrollPane);
        if (table != null) {
            if(table.getSelectedColumn() != 1) {
                JOptionPane.showMessageDialog(null, "Field " +
                                table.getModel().getValueAt(table.getSelectedRow(), table.getSelectedColumn()).
                                        toString() + " not  a valid student name",
                        "Error info", JOptionPane.ERROR_MESSAGE);
            } else {
                String firstName, lastName;
                StringTokenizer stringTokenizer = new StringTokenizer((String) table.getModel().
                        getValueAt(table.getSelectedRow(), table.getSelectedColumn()));
                firstName = stringTokenizer.nextToken();
                lastName = stringTokenizer.nextToken();

                scrollPane = findJScrollPanne(userFrame.getListPannel());
                JList<Course> courseJList = findJList(scrollPane);
                Course selectedCourse =  courseJList.getSelectedValue();

                selectedCourse.removeStudent(firstName, lastName);
                SearchSystem.getInstance().searchGradeBasedOnName(selectedCourse, firstName, lastName);
                int oldIndex = table.getSelectedRow();
                ((DefaultTableModel)table.getModel()).removeRow(table.getSelectedRow());
                for(int i = oldIndex; i < table.getRowCount() - 1; i++) {
                    table.getModel().setValueAt(i, i, 0);
                }
                JOptionPane.showMessageDialog(null,  "Student " +
                                table.getModel().getValueAt(table.getSelectedRow(), 1).
                                        toString() + " was removed.",
                        "Info Message", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
