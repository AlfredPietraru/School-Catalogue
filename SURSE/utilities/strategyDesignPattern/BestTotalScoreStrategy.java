package utilities.strategyDesignPattern;

import appUsers.Student;
import uniSystem.Grade;
import uniSystem.courseTypes.Course;

import java.util.Optional;
import java.util.TreeSet;

public class BestTotalScoreStrategy implements Strategy {

    @Override
    public Student getbestStudent(Course course) {
        TreeSet<Grade> allGrades = course.getGradesList();
        if(allGrades != null) {
            Optional<Grade> grade = allGrades.stream().max((grade1, t1) -> grade1.compareTo(t1));
            return grade.get().getStudent();
        }
        System.out.println("nu exista note inca la aceasta materie");
        return null;
    }

    @Override
    public String toString() {
        return "total";
    }
}
