package utilities.strategyDesignPattern;

import appUsers.Student;
import uniSystem.Grade;
import uniSystem.courseTypes.Course;

import java.util.Optional;
import java.util.TreeSet;

public class BestExamStrategy implements Strategy {
    @Override
    public Student getbestStudent(Course course) {
        TreeSet<Grade> allGrades = course.getGradesList();
        if(allGrades != null) {
            Optional<Grade> bestGrade = allGrades.stream().max(Grade::compareTo);
            return bestGrade.get().getStudent();
        }
        System.out.println("nu exista note inca la aceasta materie");
        return null;
    }

    @Override
    public String toString() {
        return "examen";
    }
}
