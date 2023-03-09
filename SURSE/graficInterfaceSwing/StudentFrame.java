package graficInterfaceSwing;

import appUsers.Student;
import appUsers.User;
import uniSystem.Catalog;
import uniSystem.courseTypes.Course;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Vector;

public class StudentFrame extends UserFrame {

    public StudentFrame(User myUser) {
        super(myUser);
        Student student = (Student)myUser;
        this.setTextInPannels("Mother's Name: " + student.getMother().getFirstName() + ' ' +
                student.getMother().getLastName(), "Father's Name: " + student.getFather().getFirstName() +
                ' ' + student.getFather().getLastName(), "Group ID: " + student.getGroupID());
        this.setJListInListPannel(new Vector(findAllCoursesForStudent(student)), new Color(0x929982),
                new Color(0XEDCBB1));
        this.setPanel3(new Color(0xF4F3EE));
        updateDisplayPannel(new Color(0xF6CACA));
        setColorForPannels(new Color(0xE0AFA0),
                new Color(0x7EA8BE));
        this.setVisible(true);
    }

    private Vector<Course> findAllCoursesForStudent(Student student) {
        List collection = Catalog.getInstance().getCourses().stream().
                filter(course -> course.getAllStudents().contains(student)).toList();
        Vector vector = new Vector(collection);
        return vector;
    }

    public void updateDisplayPannel(Color color) {
        JLabel[] labels = new JLabel[6];
        for(int i = 0; i < 5; i++) {
            labels[i] = new JLabel();
            labels[i].setBounds((int)displayPannel.getAlignmentX(),
                    (int)displayPannel.getAlignmentY() + i * 50, 300, 50);
            labels[i].setFont(new Font("Serif", Font.PLAIN, 20));
            displayPannel.add(labels[i]);
        }
        labels[0].setText("Teachers Name:");
        labels[1].setText("Associated Assistant:");
        labels[2].setText("Partial Grade:");
        labels[3].setText("Exam Grade:");
        labels[4].setText("Assistants List:");
        JTextArea textArea = new JTextArea();
        JTextField[] textFields = new JTextField[6];
        for(int i =0; i < 4; i++) {
            textFields[i] = new JTextField();
            textFields[i].setBounds((int)displayPannel.getAlignmentX() + 250,
                    (int)displayPannel.getAlignmentY() + i * 50, 300, 50);
            textFields[i].setFont(new Font("Serif", Font.PLAIN, 20));
            textFields[i].setVisible(true);
            textFields[i].setBackground(color);
            displayPannel.add(textFields[i]);
        }
        textArea.setBounds((int)displayPannel.getAlignmentX() + 250,
                (int)displayPannel.getAlignmentY() + 200, 300, 250);
        textArea.setFont(new Font("Serif", Font.PLAIN, 20));
        textArea.setBackground(color);
        displayPannel.add(textArea);
    }
}
