package appUsers;

import utilities.Notification;
import utilities.Observer;

import java.util.ArrayList;

public class Parent extends User implements Observer {
    private ArrayList<Notification> notificationInbox;
    private Student student;
    public Parent(String firstName, String lastName) {
        super(firstName, lastName);
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public ArrayList<Notification> getNotificationInbox() {
        return notificationInbox;
    }

    public Student getStudent() {
        return student;
    }

    @Override
    public void update(Notification notification) {
        if(notificationInbox == null) {
            notificationInbox = new ArrayList<>();
        }
        notificationInbox.add(notification);
        if(notificationInbox.size() > 20) {
            notificationInbox.remove(notificationInbox.get(0));
        }
    }

    public void checkAllNotifications() {
        if(notificationInbox.size() != 0) {
            notificationInbox.forEach(notification -> System.out.println(notification));
        }
    }
    public void checkLastNotification() {
        if (notificationInbox.size() != 0) {
            System.out.println(notificationInbox.get(notificationInbox.size() - 1));
        }
    }
}
