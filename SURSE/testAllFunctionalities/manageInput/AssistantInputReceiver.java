package testAllFunctionalities.manageInput;

import appUsers.Assistant;
import appUsers.User;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class AssistantInputReceiver implements  InputReceiver {
    @Override
    public User addNewUser(StringTokenizer stringTokenizer) {
        Assistant assistant;
        String[] arguments = new String[3];
        for (int i =0; i < 3; i++) {
            if(stringTokenizer.hasMoreTokens()) {
                arguments[i] = stringTokenizer.nextToken();
            }
        }
        ArrayList<String> subjects = new ArrayList<>();
        while(stringTokenizer.hasMoreTokens()) {
            subjects.add(stringTokenizer.nextToken());
            if(subjects.get(subjects.size()-1).equals("----")){
                subjects.remove(subjects.size() - 1);
                break;
            }
        }

        for(int i =0; i < 3; i++) {
            if(arguments[i] == null) {
                return null;
            }
        }
        assistant = new Assistant(arguments[0], arguments[1], subjects);
        assistant.setGroup_Id(arguments[2]);
        return assistant;
    }
}
