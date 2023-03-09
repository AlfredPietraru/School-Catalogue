package testAllFunctionalities.manageInput;

import appUsers.Teacher;
import appUsers.User;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class TeacherInputReceiver implements InputReceiver {
    @Override
    public User addNewUser(StringTokenizer stringTokenizer) {
        String firstName, lastName;
        ArrayList<String> teacherFields = new ArrayList<>();
            firstName = stringTokenizer.nextToken();
            lastName = stringTokenizer.nextToken();

        while(stringTokenizer.hasMoreTokens()) {
            teacherFields.add(stringTokenizer.nextToken());
            if(teacherFields.get(teacherFields.size()-1).equals("----")){
                teacherFields.remove(teacherFields.size() - 1);
                break;
            }
        }
        return new Teacher(firstName, lastName, teacherFields);
    }
}
