package uniSystem.courseTypes;

import appUsers.Student;
import uniSystem.Grade;
import java.util.*;

public class PartialCourse extends Course {

    public PartialCourse(PartialCourseBuilder partialCourseBuilder) {
        super(partialCourseBuilder);
    }

    @Override
    public boolean passingCondition(Grade grade) {
        return grade.getPartialScore() + grade.getExamScore() > 5;
    }

    @Override
    public ArrayList<Student> getGraduatedStudents() {
        ArrayList<Student> studentArrayList = getAllStudents();
        HashMap<Student, Grade> gradeHashMap = getAllStudentGrades();
        List<Student> students = studentArrayList.stream().
                filter(student -> passingCondition(gradeHashMap.get(student))).toList();
        return new ArrayList<>(students);
    }

    public static class PartialCourseBuilder extends CourseBuilder {
        @Override
        public Course build() {
            return new PartialCourse(this);
        }
    }
}