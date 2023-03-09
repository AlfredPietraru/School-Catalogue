package appUsers;

import uniSystem.Grade;

import java.util.Optional;
import java.util.TreeSet;

public class Student extends User implements Comparable<Student> {
    private Parent mother;
    private Parent father;
    private String groupID;
    private TreeSet<Grade> studentGrades;
    public Student(String firstName, String lastName) {
        super(firstName, lastName);
        studentGrades = new TreeSet<>();
    }

    public String getGroupID() {
        return groupID;
    }

    public TreeSet<Grade> getStudentGrades() {
        return studentGrades;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public void addGrade(Grade grade) {
        studentGrades.add(grade);
    }


    public Parent getMother() {
        return mother;
    }

    public Parent getFather() {
        return father;
    }

    public void setMother(Parent mother) {this.mother = mother;}
    public void setFather(Parent father) {this.father = father;}

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Student student) {
            if (super.equals(student)) {
                if (father.equals(student.father) && mother.equals(student.mother)) {
                    return studentGrades.equals(student.studentGrades);
                }
            }
        }
        return false;
    }


    @Override
    public int compareTo(Student student) {
        int result = this.getLastName().compareTo(student.getLastName());
        if(result == 0) {
            return this.getFirstName().compareTo(student.getFirstName());
        } else
            return result;
    }

    public Grade getGradeByName(String courseName) {
         Optional<Grade> optional =  this.getStudentGrades().stream().
                 filter(grade -> grade.getCourse().equals(courseName))
                .findFirst();
        return optional.orElse(null);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
