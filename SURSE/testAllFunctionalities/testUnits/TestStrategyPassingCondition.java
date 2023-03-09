package testAllFunctionalities.testUnits;

import appUsers.Student;
import uniSystem.Catalog;
import uniSystem.courseTypes.Course;
import utilities.strategyDesignPattern.*;

import java.util.ArrayList;

public class TestStrategyPassingCondition implements Tester {

    private Catalog catalog = Catalog.getInstance();
    private StrategyContext strategyContext;
    private ArrayList<FavoriteStudent> favoriteStudentArrayList = new ArrayList<>();

    public TestStrategyPassingCondition() {
        strategyContext = new StrategyContext();
    }

    private void executeForStrategy(Strategy strategy) {
        strategyContext.setStrategy(strategy);
        for(int i =0; i< catalog.getCourses().size(); i++) {
            if(catalog.getCourses().get(i).getGradesList() != null) {

                strategyContext.setCourse(catalog.getCourses().get(i));
                    favoriteStudentArrayList.add(new FavoriteStudent(strategyContext.executeStrategy(),
                        strategy.toString(), catalog.getCourses().get(i)));
            }
        }
    }

    @Override
    public void execute() {
        executeForStrategy(new BestTotalScoreStrategy());
        executeForStrategy(new BestExamStrategy());
        executeForStrategy(new BestPartialStrategy());
    }

    @Override
    public void printResults() {
        for(int i =0; i < favoriteStudentArrayList.size(); i++) {
            System.out.println(favoriteStudentArrayList.get(i));
            if(i + 1 == favoriteStudentArrayList.size() / 3) {
                System.out.println();
            }
            if(i + 1 == favoriteStudentArrayList.size() * 2 /3) {
                System.out.println();
            }
        }
        System.out.println();
        for(int i =0; i < catalog.getCourses().size(); i++) {
            if(catalog.getCourses().get(i).getGradesList() != null) {
                System.out.println("toti studentii care au trecut la " +
                        catalog.getCourses().get(i).getName() + " sunt:");
                System.out.println(catalog.getCourses().get(i).getGraduatedStudents());
                System.out.println();
            }
        }
    }
    private class FavoriteStudent {
        private Student student;
        private String examType;
        private Course course;
        public FavoriteStudent(Student student, String examType, Course course) {
            this.student = student;
            this.examType = examType;
            this.course = course;
        }

        public String toString() {
            if(examType.equals("partial")) {
                return "Cea mai mare nota la partial este " + course.getGrade(student).
                        getPartialScore().toString() + " la cursul de " +
                        course.getName() + " a fost luata de " +
                        student.toString();
            } else if(examType.equals("examen")) {
                return "Cea mai mare nota la examen este " + course.getGrade(student).
                        getExamScore().toString() + " la cursul de " +
                        course.getName() + " a fost luata de " +
                        student.toString();
            } else if(examType.equals("total")) {
                return "Cea mai mare nota finala la cursul de " + course.getGrade(student).
                        getTotal().toString() + " la cursul de " +
                        course.getName() + " a fost luata de " +
                        student.toString();
            } else {
                return "Ce fel de evaluare ii asta??";
            }
        }
    }

}
