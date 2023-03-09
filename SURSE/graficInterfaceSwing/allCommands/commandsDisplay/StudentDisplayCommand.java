package graficInterfaceSwing.allCommands.commandsDisplay;


import appUsers.Student;
import appUsers.User;
import uniSystem.Grade;
import uniSystem.SearchSystem;
import uniSystem.courseTypes.Course;

import javax.swing.*;


public class StudentDisplayCommand extends DisplayCommand {
    private Course course;
    public StudentDisplayCommand(Course course, JPanel displayPannel, User myUser) {
        super(displayPannel, myUser);
        this.course = course;
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
        textFields[0].setText(course.getTeacher().toString());

        textFields[1].setText(SearchSystem.getInstance().
                        searchRightAssitantOnGroupID(course, ((Student)getMyUser()).getGroupID()).toString());

        Grade grade = ((Student)getMyUser()).getGradeByName(course.getName());
        if(grade != null) {
            textFields[2].setText(grade.getPartialScore().toString());
            textFields[3].setText(grade.getExamScore().toString());
        } else {
            textFields[2].setText("Nu exista nota inca.");
            textFields[3].setText("nu exista nota inca.");
        }
        StringBuilder stringBuilder = new StringBuilder();
        course.getAssistants().forEach(assistant ->
                stringBuilder.append(assistant.toString()).append('\n'));
        if(textArea != null) {
            textArea.setText(stringBuilder.toString());
        }
        System.out.println("Aaaaaaaaaa");
    }
}
