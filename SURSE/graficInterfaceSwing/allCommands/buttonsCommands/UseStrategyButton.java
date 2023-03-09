package graficInterfaceSwing.allCommands.buttonsCommands;

import appUsers.Student;
import graficInterfaceSwing.UserFrame;
import uniSystem.SearchSystem;
import uniSystem.courseTypes.Course;
import utilities.strategyDesignPattern.BestExamStrategy;
import utilities.strategyDesignPattern.BestPartialStrategy;
import utilities.strategyDesignPattern.BestTotalScoreStrategy;
import utilities.strategyDesignPattern.StrategyContext;

import javax.swing.*;
import java.util.ArrayList;

public class UseStrategyButton extends ButtonCommand{
    UserFrame userFrame;

    public UseStrategyButton(UserFrame userFrame) {
        super(userFrame.getDisplayPannel(), userFrame.getUser());
        this.userFrame = userFrame;
    }

    @Override
    public void execute() {
        String[] values = {"BestExamStrategy", "BestPartialStrategy", "BestTotalScore"};

        Object selected = JOptionPane.showInputDialog(null,
                "You canceled the strategy process", "Selection",
                JOptionPane.DEFAULT_OPTION, null, values, "0");
        if ( selected != null ){//null if the user cancels.
            String selectedString = selected.toString();
            JList<Course> courseJList = findJList(findJScrollPanne(userFrame.getListPannel()));
            Course selectedCourse =  courseJList.getSelectedValue();
            ArrayList<Student> allStudents = selectedCourse.getAllStudents();
            for (Student allStudent : allStudents) {
                if (SearchSystem.getInstance().searchGradeBasedOnStudentName(selectedCourse,
                        allStudent) == null) {
                    JOptionPane.showMessageDialog(null, "Not all students have " +
                                    "received grades yet.",
                            "Error info", JOptionPane.ERROR_MESSAGE);
                    break;
                }
            }
            StrategyContext strategyContext = new StrategyContext();
            strategyContext.setCourse(selectedCourse);
            if(selectedString.equals(values[0])) {
                strategyContext.setStrategy(new BestExamStrategy());
            } else if (selectedString.equals(values[1])) {
                strategyContext.setStrategy(new BestPartialStrategy());
            } else if (selectedString.equals(values[2])) {
                strategyContext.setStrategy(new BestTotalScoreStrategy());
            }
            Student bestStudent = strategyContext.executeStrategy();
            System.out.println(strategyContext.displayInfoAboutStudent(bestStudent));
        }else{
            System.out.println("User cancelled");
        }

    }
}
