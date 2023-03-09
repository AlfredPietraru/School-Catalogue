package appUsers;

import testAllFunctionalities.manageInput.AssistantInputReceiver;
import testAllFunctionalities.manageInput.StudentInputReceiver;
import testAllFunctionalities.manageInput.TeacherInputReceiver;

import java.util.StringTokenizer;

public class UserFactory {
    private final StudentInputReceiver studentInputReceiver;
    private final AssistantInputReceiver assistantInputReceiver;
    private final TeacherInputReceiver teacherInputReceiver;


    public UserFactory() {
        studentInputReceiver = new StudentInputReceiver();
        assistantInputReceiver = new AssistantInputReceiver();
        teacherInputReceiver = new TeacherInputReceiver();
    }
    public User getUser(StringTokenizer stringTokenizer) {
        if(stringTokenizer.hasMoreTokens()) {
            String typeString = stringTokenizer.nextToken();
            return switch (typeString) {
                case "student" -> studentInputReceiver.addNewUser(stringTokenizer);
                case "assistant" -> assistantInputReceiver.addNewUser(stringTokenizer);
                case "teacher" -> teacherInputReceiver.addNewUser(stringTokenizer);
                default -> null;
            };
        }
        return null;
    }
}
