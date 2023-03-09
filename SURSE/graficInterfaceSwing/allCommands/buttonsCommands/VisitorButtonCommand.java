package graficInterfaceSwing.allCommands.buttonsCommands;
import appUsers.Assistant;
import appUsers.Teacher;
import graficInterfaceSwing.UserFrame;
import testAllFunctionalities.testUnits.TestGradingSystem;
import uniSystem.Group;
import uniSystem.SearchSystem;
import uniSystem.courseTypes.Course;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class VisitorButtonCommand extends ButtonCommand {
    HashMap<Integer, Double> gradesAlreadyModified;
    UserFrame userFrame;
    public VisitorButtonCommand(UserFrame userFrame) {
        super(userFrame.getDisplayPannel(),userFrame.getUser());
        gradesAlreadyModified = new HashMap<>();
        this.userFrame = userFrame;
    }

    private String findCourseName() {
        JScrollPane scrollPane = null;
        for( int i =0; i < userFrame.getListPannel().getComponentCount(); i++) {
            if ( userFrame.getListPannel().getComponent(i) instanceof JScrollPane) {
                scrollPane = (JScrollPane) userFrame.getListPannel().getComponent(i);
            }
        }
        if(scrollPane != null) {
            JList<Course> courseJList = (JList<Course>) scrollPane.getViewport().getView();
            return courseJList.getSelectedValue().getName();
        }
        return null;
    }

    private void writeToFile(Path fileName, JTable table) throws IOException {
        File file = new File(fileName.toString());
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String firstString = null;
        int placetoWrite =0;
        if(userFrame.getUser() instanceof Teacher) {
            firstString = "teacher " + super.myUSer.toString() + ' ' + findCourseName() + '\n';
            placetoWrite = 3;
        } else if (userFrame.getUser() instanceof Assistant) {
            firstString = "assistant " + super.myUSer.toString() + ' ' + findCourseName() + '\n';
            placetoWrite = 2;
        }
        writer.write(firstString);
        for (Map.Entry<Integer, Double> entry : gradesAlreadyModified.entrySet()) {
            String onelineString = table.getModel().getValueAt(entry.getKey(), 1).toString() + ' ' +
                    table.getModel().getValueAt(entry.getKey(), placetoWrite) + '\n';
            writer.append(onelineString);
        }
        writer.append("----");
        writer.flush();
    }

    private void executeForTeacher() {
        JScrollPane scrollPane = findJScrollPanne(userFrame.getDisplayPannel());
        JTable table = findJTable(scrollPane);
        Course course = SearchSystem.getInstance().findRightCourse(findCourseName());
        for (int i = 0; i < course.getStudentsNumber(); i++) {
            if (!gradesAlreadyModified.containsKey(i) &&
                    !table.getModel().getValueAt(i, 3).toString().equals("--")) {
                Double score = Double.parseDouble(((String)table.getModel().getValueAt(i, 3)));
                if(score > 5d || score < 0d) {
                    JOptionPane.showMessageDialog(null, "ExamScore for " +
                                    table.getModel().getValueAt(i, 1).toString() + " not valid",
                            "Error info", JOptionPane.ERROR_MESSAGE);
                }
                gradesAlreadyModified.put(i, score);
            }
        }
        try {
            writeToFile(Path.of(System.
                            getProperty("user.dir") + "/graficInterfaceSwing/" +
                            "allCommands/buttonsCommands/storeGrades.txt"),
                    table);

            TestGradingSystem testGradingSystem = new TestGradingSystem(Path.of(System.
                    getProperty("user.dir") + "/graficInterfaceSwing/" +
                    "allCommands/buttonsCommands/storeGrades.txt"));
            testGradingSystem.execute();
                ((Teacher)userFrame.getUser()).accept(testGradingSystem.getScoreVisitor(), findCourseName());
            JOptionPane.showMessageDialog(null, "Grades were succesfully modified",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void executeForAssistant() {
        JScrollPane scrollPane = findJScrollPanne(userFrame.getDisplayPannel());
        JTable table = findJTable(scrollPane);
        Course course = SearchSystem.getInstance().findRightCourse(findCourseName());
        Group group = course.getGroupHashMap().get(((Assistant)userFrame.getUser()).getGroup_Id());
        for (int i = 0; i < group.size(); i++) {
            if (!gradesAlreadyModified.containsKey(i) &&
                    !table.getModel().getValueAt(i, 2).toString().equals("--")) {
                Double score = Double.parseDouble(((String)table.getModel().getValueAt(i, 2)));
                if(score > 5d || score < 0d) {
                    JOptionPane.showMessageDialog(null, "ExamScore for " +
                                    table.getModel().getValueAt(i, 1).toString() + " not valid",
                            "Error info", JOptionPane.ERROR_MESSAGE);
                }
                gradesAlreadyModified.put(i, score);
            }
        }
        try {
            writeToFile(Path.of(System.
                            getProperty("user.dir") + "/src/graficInterfaceSwing/" +
                            "allCommands/buttonsCommands/storeGrades.txt"),
                    table);

            TestGradingSystem testGradingSystem = new TestGradingSystem(Path.of(System.
                    getProperty("user.dir") + "/src/graficInterfaceSwing/" +
                    "allCommands/buttonsCommands/storeGrades.txt"));
            testGradingSystem.execute();
            ((Assistant)userFrame.getUser()).accept(testGradingSystem.getScoreVisitor(), findCourseName());
            JOptionPane.showMessageDialog(null, "Grades were succesfully modified",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void execute() {
        if (userFrame.getUser() instanceof Teacher) {
            executeForTeacher();
        } else if (userFrame.getUser() instanceof Assistant) {
            executeForAssistant();
        }
    }
}
