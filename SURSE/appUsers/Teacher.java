package appUsers;


import utilities.visitorDesignPattern.Element;
import utilities.visitorDesignPattern.Visitor;

import java.util.ArrayList;


public class Teacher extends User implements Element {
    private ArrayList<String> allCourses;
    private Visitor visitor;

    public Teacher(String firstName, String lastName, ArrayList<String>  allCourses) {
        super(firstName, lastName);
        this.allCourses =allCourses;
    }

    public ArrayList<String> getAllCourses() {
        return allCourses;
    }

    @Override
    public void accept(Visitor visitor, String courseName) {
        this.visitor = visitor;
        this.visitor.visit(this, courseName);
    }
}
