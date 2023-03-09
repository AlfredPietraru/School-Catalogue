package graficInterfaceSwing.allCommands.commandsDisplay;

import appUsers.Assistant;
import appUsers.Student;
import appUsers.User;
import uniSystem.Grade;
import uniSystem.Group;
import uniSystem.courseTypes.Course;

import javax.swing.*;
import java.util.ArrayList;

public class AssistantDisplayCommand extends DisplayCommand {

    private Course course;
    public AssistantDisplayCommand(Course course, JPanel displayPannel, User myUser) {
        super(displayPannel, myUser);
        this.course = course;
    }

    @Override
    public void execute() {
        JScrollPane scrollPane = findJScrollPanne();
        if (scrollPane != null) {
            JTable table = null;
            if (scrollPane.getViewport().getView() instanceof JTable) {
                table = (JTable) scrollPane.getViewport().getView();
            }
            if (table != null) {
                Group group = course.getGroupHashMap().get(((Assistant) super.getMyUser()).getGroup_Id());
                if (course.getGradesList() == null || course.getGradesList().size() == 0) {
                    ArrayList<Student> arrayList = new ArrayList<>(group);
                    for (int i = 0; i < arrayList.size(); i++) {
                        table.getModel().setValueAt(i, i, 0);
                        table.getModel().setValueAt(arrayList.get(i).toString(), i, 1);
                        table.getModel().setValueAt("--", i, 2);
                        table.getModel().setValueAt("--", i, 3);
                    }
                } else {
                    Student[] students = new Student[group.size()];
                    int totalStudents = 0;
                    for (Student student : group) {
                        students[totalStudents] = student;
                        totalStudents++;
                    }
                    for (int i = 0; i < students.length; i++) {
                        Grade currentGrade = course.getGrade(students[i]);
                        if(currentGrade != null) {
                            if (currentGrade.getPartialScore() != 0d) {
                                table.getModel().setValueAt(currentGrade.getPartialScore(), i, 2);
                            } else {
                                table.getModel().setValueAt("--", i, 2);
                            }
                            if (currentGrade.getExamScore() != 0d) {
                                table.getModel().setValueAt(currentGrade.getExamScore(), i, 3);
                            } else {
                                table.getModel().setValueAt("--", i, 3);
                            }
                        }
                    }
                }
            }
        }
    }
}
