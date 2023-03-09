package uniSystem.courseTypes;

import appUsers.Student;
import uniSystem.Grade;

import java.util.*;

public class FullCourse extends Course {
    public FullCourse(FullCourseBuilder fullCourseBuilder) {
        super(fullCourseBuilder);
    }

    @Override
    public boolean passingCondition(Grade grade) {
        return grade.getExamScore() >= 3 && grade.getPartialScore() >= 2;
    }

    @Override
    public ArrayList<Student> getGraduatedStudents() {
        ArrayList<Student> studentArrayList = getAllStudents();
        HashMap<Student, Grade> gradeHashMap = getAllStudentGrades();
        List<Student> students = studentArrayList.stream().
                filter(student -> passingCondition(gradeHashMap.get(student))).toList();
        return new ArrayList<>(students);
    }

    public static class FullCourseBuilder extends CourseBuilder {

        @Override
        public Course build() {
            return new FullCourse(this);
        }
    }
}
