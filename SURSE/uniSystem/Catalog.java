package uniSystem;
import uniSystem.courseTypes.Course;
import utilities.Subject;
import utilities.Notification;
import utilities.Observer;
import java.util.ArrayList;

public class Catalog implements Subject {
    private static ArrayList<Course> courseArrayList = null;
    private static final Catalog instance = new Catalog();
    private ArrayList<Observer> observers = new ArrayList<>();
    private Catalog(){
    }
    public static Catalog getInstance() {
        if(courseArrayList == null){
            courseArrayList = new ArrayList<>();
        }
        return instance;
    }
    public ArrayList<Observer> getObservers() {
        return observers;
    }

    public ArrayList<Course> getCourses() {
        return courseArrayList;
    }

    public void addCourse(Course course) {
        courseArrayList.add(course);
    }
    public void removeCourse(Course course) {
        courseArrayList.remove(course);
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Grade grade) {
        Notification notification = new Notification(grade);
        notification.sendNotification();
        grade.setStudentGrade();
    }
}
