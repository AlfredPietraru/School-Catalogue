package utilities;

import uniSystem.Grade;

public class Notification {

    private final Grade studentGrade;
    private String message;

    public Grade getStudentGrade() {
        return studentGrade;
    }

    private String createMessage(Grade grade) {
        if (grade.getPartialScore() == 0d && grade.getExamScore() != 0d) {
            message = grade.getStudent().toString() + ' ' +
                    " a obtinut la examen nota " + grade.getExamScore() + " la materia " +
            grade.getCourse();
        } else if (grade.getExamScore() == 0d && grade.getPartialScore() != 0d) {
            message = grade.getStudent().toString() +
                    " a obtinut nota la partial " + grade.getPartialScore() + " la materia " +
                    grade.getCourse();
        } else {
            message = grade.getStudent().toString() +
                    " a obtinut nota de parcurs " + grade.getTotal().toString() + " la examen " +
                    grade.getExamScore().toString() + " la partial: " + grade.getPartialScore().toString()
                    + " la materia " + grade.getCourse();
        }
        return message;
    }

    public Notification(Grade grade) {
        studentGrade = grade;
        message = createMessage(grade);
    }


    public void sendNotification() {
        if(studentGrade.getStudent().getMother() != null) {
            studentGrade.getStudent().getMother().update(this);
        }
        if(studentGrade.getStudent().getFather() != null) {
            studentGrade.getStudent().getFather().update(this);
        }
    }

    @Override
    public String toString() {
            return message;
        }
}
