package utilities.strategyDesignPattern;

import appUsers.Student;
import uniSystem.courseTypes.Course;

public interface Strategy {
    Student getbestStudent(Course course);
}
