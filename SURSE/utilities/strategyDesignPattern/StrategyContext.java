package utilities.strategyDesignPattern;

import appUsers.Student;
import uniSystem.courseTypes.Course;

public class StrategyContext {
    private Strategy strategy;
    private Course course;

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String displayInfoAboutStudent(Student student) {
        if (student != null) {
            String message = null;
            if (strategy instanceof BestTotalScoreStrategy) {
                message = student.toString() + ' '  + " a avut nota finala cea mai mare " +
                student.getGradeByName(course.getName()).getTotal();
            } else if (strategy instanceof  BestPartialStrategy) {
                message = student.toString() + ' '  + " a avut nota de partial cea mai mare " +
                        student.getGradeByName(course.getName()).getPartialScore();
            } else if (strategy instanceof  BestExamStrategy) {
                message = student.toString() + ' '  + " a avut nota de examen cea mai mare " +
                        student.getGradeByName(course.getName()).getExamScore();
            }
            return message;
        }
        return null;
    }

    public Student executeStrategy() {
        if(strategy != null && course != null) {
            return strategy.getbestStudent(course);
        }
        return null;
    }
}
