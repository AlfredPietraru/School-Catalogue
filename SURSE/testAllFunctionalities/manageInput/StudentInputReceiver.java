package testAllFunctionalities.manageInput;

import appUsers.Parent;
import appUsers.Student;
import appUsers.User;
import java.util.StringTokenizer;

public class StudentInputReceiver implements InputReceiver {
    private final ParentInputReceiver fatherReceiver;
    private final ParentInputReceiver motherReceiver;

    public StudentInputReceiver() {
        this.fatherReceiver = new ParentInputReceiver();
        this.motherReceiver = new ParentInputReceiver();
    }

    @Override
    public User addNewUser(StringTokenizer stringTokenizer) {
        Student student;
        String firstName = null, lastName = null;
        if (stringTokenizer.hasMoreTokens()) {
            firstName = stringTokenizer.nextToken();
        }
        if (stringTokenizer.hasMoreTokens()) {
            lastName = stringTokenizer.nextToken();
        }
        if (firstName != null && lastName != null) {
            student = new Student(firstName, lastName);
            student.setMother((Parent) motherReceiver.addNewUser(stringTokenizer));
            student.setFather((Parent) fatherReceiver.addNewUser(stringTokenizer));
            student.getMother().setStudent(student);
            student.getFather().setStudent(student);
            return student;
        }
        return null;
    }

    class ParentInputReceiver implements InputReceiver {

        @Override
        public User addNewUser(StringTokenizer stringTokenizer) {
            Parent parent;
            String firstName = null, lastName = null;
            stringTokenizer.nextToken();
            if (stringTokenizer.hasMoreTokens()) {
                firstName = stringTokenizer.nextToken();
            }
            if (stringTokenizer.hasMoreTokens()) {
                lastName = stringTokenizer.nextToken();
            }
            if (firstName != null && lastName != null) {
                parent = new Parent(firstName, lastName);
                return parent;
            }
            return null;
        }
    }
}
