package graficInterfaceSwing.allCommands.commandsDisplay;

import appUsers.Student;
import appUsers.User;
import uniSystem.Grade;
import uniSystem.courseTypes.Course;

import javax.swing.*;
import java.util.ArrayList;
import java.util.TreeSet;

public class TeacherDisplayCommand extends DisplayCommand {
    private Course course;

    public TeacherDisplayCommand(Course course, JPanel displayPannel, User myUser) {
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
                TreeSet<Grade> gradesList = course.getGradesList();
                if (gradesList == null || gradesList.size() == 0) {
                    ArrayList<Student> studentArrayList = course.getAllStudents();
                    for (int i = 0; i < studentArrayList.size(); i++) {
                        table.getModel().setValueAt(i, i, 0);
                        table.getModel().setValueAt(studentArrayList.get(i).toString(), i, 1);
                        table.getModel().setValueAt("--", i, 2);
                        table.getModel().setValueAt("--", i, 3);
                    }
                } else {
                    Grade[] grades = new Grade[gradesList.size()];
                    int totalGrades = 0;
                    for (Grade grade : gradesList) {
                        grades[totalGrades] = grade;
                        totalGrades++;
                    }
                    for (int i = 0; i < grades.length; i++) {
                        table.getModel().setValueAt(i, i, 0);
                        table.getModel().setValueAt(grades[i].getStudent().toString(), i, 1);
                        if(grades[i].getPartialScore() != 0d) {
                            table.getModel().setValueAt(grades[i].getPartialScore(), i, 2);
                        } else {
                            table.getModel().setValueAt("--", i, 2);
                        }
                        if (grades[i].getExamScore() != 0d) {
                            table.getModel().setValueAt(grades[i].getExamScore(), i, 3);
                        } else {
                            table.getModel().setValueAt("--", i, 3);
                        }
                    }
                    if (totalGrades < course.getStudentsNumber()) {
                        for(int i = grades.length; i < course.getStudentsNumber(); i++) {
                            table.getModel().setValueAt("--", i, 2);
                            table.getModel().setValueAt("--", i, 3);
                        }
                    }
                }
            }
        }
    }
}
