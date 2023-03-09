package graficInterfaceSwing.allCommands.commandsDisplay;

import appUsers.Assistant;
import appUsers.Teacher;
import appUsers.User;
import uniSystem.Grade;
import uniSystem.SearchSystem;
import uniSystem.courseTypes.Course;
import utilities.Notification;

import javax.swing.*;
import java.util.StringTokenizer;

public class ParentDisplayCommand extends DisplayCommand {
    Notification curentNotification;
    public ParentDisplayCommand(Notification notification, JPanel displayPannel, User parent) {
        super(displayPannel, parent);
        this.curentNotification = notification;
    }
    private void judgeNotification(String message, Grade grade, JTextField examField,
                                     JTextField partialField) {
        StringTokenizer stringTokenizer = new StringTokenizer(message);
        String currentString = null;
        while(stringTokenizer.hasMoreTokens()) {
            currentString = stringTokenizer.nextToken();
            if(currentString.equals("partial")) {
                partialField.setText(grade.getPartialScore().toString());
                examField.setText("There is no grade yet.");
                break;
            }
            if(currentString.equals("examen")) {
                partialField.setText("There is no grade yet.");
                examField.setText(grade.getExamScore().toString());
                break;
            }
            if(currentString.equals("parcurs")) {
                partialField.setText(grade.getPartialScore().toString());
                examField.setText(grade.getExamScore().toString());
                break;
            }
        }
    }


    @Override
    public void execute() {
        JTextField[] textFields = new JTextField[6];
        JTextArea textArea = null;
        int j =0;
        for(int i = 0; i < super.getDisplayPannel().getComponentCount(); i++) {
            if(getDisplayPannel().getComponent(i) instanceof JTextField) {
                textFields[j] = (JTextField) getDisplayPannel().getComponent(i);
                j++;
            }
            if (getDisplayPannel().getComponent(i) instanceof JTextArea) {
                textArea = (JTextArea) getDisplayPannel().getComponent(i);
            }
        }

        Grade grade = curentNotification.getStudentGrade();
        judgeNotification(curentNotification.toString(), grade, textFields[0],
                textFields[1]);

        if(textFields[0].getText().equals("There is no grade yet.") ||
                textFields[1].getText().equals("There is no grade yet.")) {
            textFields[2].setText("Could not calculate total");
        } else {
            textFields[2].setText(grade.getTotal().toString());
        }

        Course course = SearchSystem.getInstance().findRightCourse(grade.getCourse());
        Teacher teacher = course.getTeacher();
        Assistant assistant=  course.getAssistant(grade.getStudent().getGroupID());
        textFields[3].setText(assistant.toString());
        textFields[4].setText(teacher.toString());

        if(textArea != null) {
            textArea.setText(curentNotification.toString());
        }
    }
}
