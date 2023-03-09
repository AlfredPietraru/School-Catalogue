package uniSystem;

import appUsers.Assistant;
import appUsers.Parent;
import appUsers.Student;
import appUsers.Teacher;
import uniSystem.courseTypes.Course;
import utilities.Observer;
import java.util.*;

public class SearchSystem {
    private final Catalog catalog;
    private static final SearchSystem searchSystem = new SearchSystem();
    private SearchSystem() {
        catalog = Catalog.getInstance();
    }
    public static SearchSystem getInstance() {
        return searchSystem;
    }

    public Course findRightCourse(String courseName) {
        Optional<Course> course = catalog.getCourses().stream().
                filter(course1 -> course1.getName().equals(courseName)).findFirst();
        return course.orElse(null);
    }
    public Course findRightCourse(Teacher teacher, String courseName) {
        List<Course> teacherCourses = catalog.getCourses().
                stream().filter(course1 -> course1.getTeacher().equals(teacher)).toList();
        Optional<Course> course = teacherCourses.stream().filter(course1 ->
                course1.getName().equals(courseName)).findFirst();
        return course.orElse(null);
    }

    public List<Course> getAllCoursesOfTeacher(Teacher teacher) {
        List<Course> courseList = new LinkedList<>();
        teacher.getAllCourses().forEach(s -> courseList.add(findRightCourse(s)));
        return courseList;
    }

    public List<Course> getAllCoursesOfAssistant(Assistant assistant) {
        List<Course> courseList = new LinkedList<>();
        assistant.getAllCourses().forEach(s -> courseList.add(findRightCourse(s)));
        return courseList;
    }
    public Teacher findTeacher(String firstName, String lastName) {
        Optional<Course> teacherCourse = catalog.getCourses().
                stream().filter(course -> course.getTeacher().getFirstName().equals(firstName) &&
                        course.getTeacher().getLastName().equals(lastName)).findAny();
        return teacherCourse.map(Course::getTeacher).orElse(null);
    }

    public Course findRightCourseBasedOnAssistant(String assistantFirstName, String assistantLastName) {
        for(int i =0; i < catalog.getCourses().size(); i++) {
            Course currentCourse = catalog.getCourses().get(i);
            Assistant assistant = currentCourse.getAssistant(assistantFirstName, assistantLastName);
            if(assistant != null) {
                return currentCourse;
            }
        }
        return null;
    }

    public Student searchStudentBasedOnHisName(String firstName, String lastName) {
        if(catalog.getObservers() != null) {
            ArrayList<Observer> allParents = catalog.getObservers();
             Optional<Observer> studentParent = allParents.stream().filter(parent ->
                     ((Parent)parent).getStudent().getFirstName().equals(firstName) &&
                             ((Parent)parent).getStudent().getLastName().equals(lastName)).findFirst();
             if(studentParent.isPresent()) {
                 return ((Parent)studentParent.get()).getStudent();
             }
            }
        return null;
    }
        public Parent searchParentBasedOnParentName(String firstName, String lastName) {
            if(catalog.getObservers() != null){
                ArrayList<Observer> allParents = catalog.getObservers();
                Optional<Observer> perfectParent = allParents.stream().
                        filter(parent -> ((Parent)parent).getFirstName().equals(firstName) &&
                                ((Parent)parent).getLastName().equals(lastName)).findFirst();
                if(perfectParent.isPresent()) {
                    return ((Parent) perfectParent.get());
                }
            }
            return null;
        }
        public Grade searchGradeBasedOnStudentName(Course course, Student student) {
        if(course.getGradesList() != null) {
            Optional<Grade> studentsGrade = course.getGradesList().stream().
                    filter(grade -> grade.getStudent().getFirstName().equals(student.getFirstName()) &&
                            grade.getStudent().getLastName().equals(student.getLastName())).findFirst();
            return studentsGrade.orElse(null);
        }
            return null;
        }

        public Grade searchGradeBasedOnName(Course course, String firstName, String lastName) {
        if(course.getGradesList() != null) {
            Optional<Grade> studentsGrade = course.getGradesList().stream().
                    filter(grade -> grade.getStudent().getFirstName().equals(firstName) &&
                            grade.getStudent().getLastName().equals(lastName)).findFirst();
            return studentsGrade.orElse(null);
        } else {
            return null;
        }
        }

        public Assistant searchRightAssitantOnGroupID(Course course, String ID) {
        Optional<Assistant> assistantOptional =  course.getAssistants().stream().
                filter(assistant -> assistant.getGroup_Id().equals(ID)).findFirst();
        return assistantOptional.orElse(null);
        }
}
