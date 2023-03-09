package graficInterfaceSwing.allCommands.buttonsCommands;

import appUsers.Assistant;
import appUsers.Student;
import appUsers.Teacher;
import graficInterfaceSwing.UserFrame;
import uniSystem.Grade;
import uniSystem.SearchSystem;
import uniSystem.courseTypes.Course;

import javax.swing.*;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class AddGradeButtonCommand extends ButtonCommand {
    UserFrame userFrame;

    public AddGradeButtonCommand(UserFrame userFrame) {
        super(userFrame.getDisplayPannel(), userFrame.getUser());
        this.userFrame = userFrame;
    }

    @Override
    public void execute() {
        JScrollPane scrollPane = findJScrollPanne(userFrame.getDisplayPannel());
        JTable table = findJTable(scrollPane);
        if (table != null) {
            String selectedValue = table.getModel().
                    getValueAt(table.getSelectedRow(), table.getSelectedColumn()).toString();
            if (selectedValue.equals("--")) {
                JOptionPane.showMessageDialog(null, "Grade was not modified yet",
                        "Info", JOptionPane.ERROR_MESSAGE);
            } else {
                String fullNameStudent = table.getModel().getValueAt(table.getSelectedRow(), 1).toString();
                StringTokenizer str = new StringTokenizer(fullNameStudent);
                scrollPane = findJScrollPanne(userFrame.getListPannel());
                JList<Course> courseJList = findJList(scrollPane);
                Course selectedCourse = courseJList.getSelectedValue();

                String firstName = str.nextToken();
                String lastName = str.nextToken();
                Grade oldGrade = SearchSystem.getInstance().searchGradeBasedOnName(selectedCourse,
                        firstName, lastName);
                if (oldGrade != null) {
                    if (userFrame.getUser() instanceof Teacher) {
                        if (oldGrade.getExamScore().equals(Double.parseDouble(selectedValue))) {
                            JOptionPane.showMessageDialog(null, "Grade was not modified yet",
                                    "Info", JOptionPane.ERROR_MESSAGE);
                            return;
                        } else {
                            oldGrade.setExamScore(Double.parseDouble(selectedValue));
                            JOptionPane.showMessageDialog(null,
                                    "Grade was succesfully modified", "Info",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else if (userFrame.getUser() instanceof Assistant) {
                        if (oldGrade.getPartialScore().equals(Double.parseDouble(selectedValue))) {
                            JOptionPane.showMessageDialog(null,
                                    "Grade was not modified yet", "Info",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        } else {
                            oldGrade.setPartialScore(Double.parseDouble(selectedValue));
                            JOptionPane.showMessageDialog(null, "Grade was succesfully modified",
                                    "Info", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                } else {
                    Student student = SearchSystem.getInstance().searchStudentBasedOnHisName(firstName, lastName);
                    if (selectedCourse.getGradesList() == null) {
                        selectedCourse.setGradesList(new TreeSet<>());
                        Grade newGrade = new Grade(student, selectedCourse.getName());
                        if (userFrame.getUser() instanceof Assistant) {
                            newGrade.setPartialScore(Double.parseDouble((selectedValue)));
                        } else if (userFrame.getUser() instanceof Teacher) {
                            newGrade.setExamScore(Double.parseDouble(selectedValue));
                        }
                        selectedCourse.addGrade(newGrade);
                    }
                    JOptionPane.showMessageDialog(null, "Grade was succesfully modified",
                            "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }
}