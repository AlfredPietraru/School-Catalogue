package utilities.visitorDesignPattern;

import appUsers.Assistant;
import appUsers.Teacher;

public interface Visitor {
    void visit(Assistant assistant, String courseName);
    void visit(Teacher teacher, String courseName);
}
