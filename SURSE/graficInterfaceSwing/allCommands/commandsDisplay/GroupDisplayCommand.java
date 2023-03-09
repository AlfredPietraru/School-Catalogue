package graficInterfaceSwing.allCommands.commandsDisplay;

import appUsers.Assistant;
import appUsers.Student;
import appUsers.User;
import uniSystem.Grade;
import uniSystem.Group;
import uniSystem.courseTypes.Course;

import javax.swing.*;
import java.util.ArrayList;

public class GroupDisplayCommand extends DisplayCommand {

    Group group;
    Course course;
    public GroupDisplayCommand(Group group, JPanel displayPannel, Course course) {
        super(displayPannel, null);
        this.group = group;
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
            if (table == null) {
                return;
            }
            if(group == null) {
                for(int i = 0; i < 10; i++) {
                    for(int j =0; j < 4; j++) {
                        table.getModel().setValueAt("--", i, j);
                    }
                }
                return;
            }
            if (group.size() < 10) {
                for(int i = group.size(); i < 10; i++) {
                    for(int j =0; j < 4; j++) {
                        table.getModel().setValueAt("--", i, j);
                    }
                }
                return;
            }
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
                        table.getModel().setValueAt(i, i, 0);
                        table.getModel().setValueAt(students[i].toString(), i, 1);
                        Grade currentGrade = course.getGrade(students[i]);
                        if (currentGrade != null) {
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


