package uniSystem;

import appUsers.Student;

public class Grade implements Comparable<Grade>, Cloneable{
    private Double partialScore, examScore;
    private Student student;
    private String course;

    public Grade(Student student, String course) {
        super();
        this.student = student;
        this.course = course;
        this.partialScore = 0d;
        this.examScore = 0d;
    }

    public Grade(Grade grade) {
        super();
        this.student = grade.student;
        this.course = grade.course;
        this.partialScore = grade.partialScore;
        this.examScore = grade.examScore;
    }

    public void setStudentGrade() {
        if(partialScore != 0d && examScore != 0d) {
            student.addGrade(this);
        }
    }


    public Double getPartialScore() {
        return partialScore;
    }

    public void setPartialScore(Double partialScore) {
        this.partialScore = partialScore;
    }

    public Double getExamScore() {
        return examScore;
    }

    public void setExamScore(Double examScore) {
        this.examScore = examScore;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Double getTotal() { return partialScore + examScore; }

    @Override
    public int compareTo(Grade grade) {
            int result = Double.compare(this.getTotal(), grade.getTotal());
            if (result == 0) {
                result = this.student.compareTo(grade.student);
                if(result == 0) {
                    return course.compareTo(grade.course);
                }
                return result;
            } else {
                return result;
            }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Object myClone = super.clone();
        ((Grade) myClone).examScore = this.examScore;
        ((Grade) myClone).partialScore = this.partialScore;
        return myClone;
    }

    @Override
    public String toString() {
        return "Student=" + student + "  {" +
                "partialScore=" + partialScore +
                ", examScore=" + examScore +
                "}, course='" + course + '\'';
    }
}
