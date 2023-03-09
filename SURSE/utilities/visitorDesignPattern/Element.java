package utilities.visitorDesignPattern;

import utilities.visitorDesignPattern.Visitor;

public interface Element {
    void accept(Visitor visitor, String courseName);
}
