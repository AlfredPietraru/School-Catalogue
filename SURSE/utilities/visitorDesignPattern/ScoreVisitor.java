package utilities.visitorDesignPattern;

import appUsers.Assistant;
import appUsers.Student;
import appUsers.Teacher;
import uniSystem.Catalog;
import uniSystem.Grade;
import uniSystem.Group;
import uniSystem.SearchSystem;
import uniSystem.courseTypes.Course;

import java.util.*;
import java.util.stream.Collectors;

public class ScoreVisitor implements Visitor {
    private final SearchSystem searchSystem;
    private final HashMap<Teacher, HashMap<String, ArrayList<Tuple<Student, String, Double>>>> examScores;
    private final HashMap<Assistant, HashMap<String, ArrayList<Tuple<Student, String, Double>>>> partialScores;

    public ScoreVisitor() {
        searchSystem = SearchSystem.getInstance();
        examScores = new HashMap<>();
        partialScores = new HashMap<>();
    }

    private ArrayList<Tuple<Student, String, Double>> createTuples(Course course, StringTokenizer str) {
        ArrayList<Tuple<Student, String, Double>> tupleArrayList =new ArrayList<>();
        while(str.hasMoreTokens()) {
            String[] arguments = new String[3];
            for(int i =0; i < 3; i++) {
                if(str.hasMoreTokens()) {
                    arguments[i] = str.nextToken();
                    if(arguments[i].equals("----")){
                        return tupleArrayList;
                    }
                } else {
                    return tupleArrayList;
                }
            }
            Student studentFound = searchSystem.searchStudentBasedOnHisName(arguments[0], arguments[1]);
            tupleArrayList.add(new Tuple<>(studentFound, course.getName(), Double.parseDouble(arguments[2])));
        }
        return tupleArrayList;
    }


    private void fillTeacherHashMap(StringTokenizer str) {
        String teacherFirstName = str.nextToken();
        String teacherLastName = str.nextToken();
        String courseName = str.nextToken();
        Course searchedCourse = searchSystem.findRightCourse(courseName);
        if (searchedCourse != null) {
            if(!searchedCourse.getTeacher().getFirstName().equals(teacherFirstName) ||
            !searchedCourse.getTeacher().getLastName().equals(teacherLastName)) {
                System.out.println("Nu se potriveste cursul cu profesorul");
            } else {
                examScores.put(searchedCourse.getTeacher(),
                        new HashMap<>());
                examScores.get(searchedCourse.getTeacher()).put(courseName, createTuples(searchedCourse, str));
            }
        } else {
            System.out.println("NU se poate corelatia profesor de curs <--> cursul la care preda");
        }
    }

    private void fillAssistantHashMap(StringTokenizer str) {
        String assistantFirstName = str.nextToken();
        String assistantLastName = str.nextToken();
        String courseName = str.nextToken();
        Course searchedCourse = searchSystem.
                findRightCourseBasedOnAssistant(assistantFirstName, assistantLastName);
        if (searchedCourse != null) {
            Assistant assistant = searchedCourse.
                    getAssistant(assistantFirstName, assistantLastName);
            if(assistant != null) {
                partialScores.put(assistant, new HashMap<>());
                partialScores.get(assistant).put(courseName, createTuples(searchedCourse, str));
            } else {
                System.out.println("Nu se poate gasi assistentul cand se fac tuple-urile");
            }
        } else {
            System.out.println("Nu se poate gasi laborantul");
        }
    }



    public void fillHashMaps(StringTokenizer str) {
        while(str.hasMoreTokens()) {
            String didacticPosition = str.nextToken();
            if (didacticPosition.equals("teacher")) {
                fillTeacherHashMap(str);
            } else if (didacticPosition.equals("assistant")) {
                fillAssistantHashMap(str);
            } else {
                System.out.println("NU se poate identifica cadrul didactic, cine adauga note?");
                return;
            }
        }
    }

    //nu e corectat inca
    @Override
    public void visit(Assistant assistant, String courseName) {
        Course course = searchSystem.findRightCourse(courseName);
        ArrayList<Tuple<Student, String, Double>> arrayList =
                partialScores.get(assistant).get(courseName);
        if(arrayList == null) {
            return;
        }
        if (course != null) {
            if(course.getGradesList() == null) {
                course.setGradesList(new TreeSet<>());
                for (Tuple<Student, String, Double> studentStringDoubleTuple : arrayList) {
                    Grade grade = new Grade(studentStringDoubleTuple.getStudent(), courseName);
                    grade.setPartialScore(studentStringDoubleTuple.getGrade());
                    course.getGradesList().add(grade);
                }
            } else {
                for (Tuple<Student, String, Double> tuple : arrayList) {
                    Optional<Grade> grade =  course.getGradesList().stream().filter(grade1 ->
                            grade1.getStudent().equals(tuple.getStudent())).findFirst();
                    if(grade.isPresent()) {
                        grade.get().setPartialScore(tuple.getGrade());
                        Catalog.getInstance().notifyObservers(grade.get());
                    } else {
                        System.out.println("Nu se gaseste nota de examen pt Studentul " +
                                tuple.student.toString());
                    }
                }
                //partialScores.remove(assistant);
            }
        } else {
            System.out.println("Nu s-a gasit cursul la care preda acest profesor, ce ne facem?");
        }
    }


    @Override
    public void visit(Teacher teacher, String courseName) {
        Course course = searchSystem.findRightCourse(teacher, courseName);
        ArrayList<Tuple<Student, String, Double>> arrayList =
                examScores.get(teacher).get(courseName);
        if(arrayList == null) {
            return;
        }
        if (course != null) {
            if(course.getGradesList() == null) {
                TreeSet<Grade> grades = new TreeSet<>();
                for (Tuple<Student, String, Double> studentStringDoubleTuple : arrayList) {
                    Grade grade = new Grade(studentStringDoubleTuple.getStudent(), courseName);
                    grade.setExamScore(studentStringDoubleTuple.getGrade());
                    grades.add(grade);
                    Catalog.getInstance().notifyObservers(grade);
                }
                course.setGradesList(grades);
            } else { //ar trebui sa faca notificare si aici NU O FACE
                arrayList.forEach(tuple ->
                        searchSystem.searchGradeBasedOnStudentName(course, tuple.getStudent()).
                                setExamScore(tuple.getGrade()));
                course.getGradesList().forEach(Grade::setStudentGrade);
            }
        } else {
            System.out.println("Nu s-a gasit cursul la care preda acest profersor, ce ne facem?");
        }
    }

    public void visit(Course course) {
        if(examScores.containsKey(course.getTeacher())) {
            visit(course.getTeacher(), course.getName());
        }
        course.getAssistants().stream().
                filter(partialScores::containsKey)
                .forEach(assistant -> visit(assistant, course.getName()));
    }

    public static <K, V> String mapToString(Map<K, V> map) {
        return map.entrySet()
                .stream()
                .map(entry -> entry.getKey() + ":" + entry.getValue() + '\n')
                .collect(Collectors.joining(", ", "{", "}"));
    }

    private class Tuple<K, L, M> {
        private final K student;
        private final L courseName;
        private final M grade;

        public Tuple(K student, L courseName, M grade) {
            this.student = student;
            this.courseName = courseName;
            this.grade = grade;
        }

        public M getGrade() {
            return grade;
        }

        public L getCourseName() {
            return courseName;
        }

        public K getStudent() {
            return student;
        }

        @Override
        public String toString() {
            return student.toString() + ' ' + courseName.toString() +
                    ' ' + grade.toString();
        }
    }
}