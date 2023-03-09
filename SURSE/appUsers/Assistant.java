package appUsers;

import utilities.visitorDesignPattern.Element;
import utilities.visitorDesignPattern.Visitor;

import java.util.ArrayList;

public class Assistant extends User implements Element {
    private ArrayList<String> allCourses;
    private String Group_Id;
    private Visitor visitor;

    public Assistant(String firstName, String lastName, ArrayList<String> allCourses) {

        super(firstName, lastName);
        this.allCourses = allCourses;
    }

    public void setGroup_Id(String group_Id) {
        Group_Id = group_Id;
    }

    public String getGroup_Id() {
        return Group_Id;
    }

    public ArrayList<String> getAllCourses() {
        return allCourses;
    }

    @Override
    public void accept(Visitor visitor, String courseName) {
        this.visitor = visitor;
        this.visitor.visit(this, courseName);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
