package utilities.strategyDesignPattern;

import appUsers.Student;
import uniSystem.Grade;
import uniSystem.courseTypes.Course;

import java.util.Comparator;
import java.util.Optional;
import java.util.TreeSet;

public class BestPartialStrategy implements Strategy {
    @Override
    public Student getbestStudent(Course course) {
        TreeSet<Grade> allGrades = course.getGradesList();
        if(allGrades != null) {
            Optional<Grade> grade = allGrades.stream().max(Comparator.comparingDouble(Grade::getPartialScore));
            return grade.get().getStudent();
        }
        System.out.println("nu exista note inca la aceasta materie");
        return null;
    }

    @Override
    public String toString() {
        return "partial";
    }
}
