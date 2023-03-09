package testAllFunctionalities.manageInput;
import appUsers.Assistant;
import appUsers.Teacher;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import uniSystem.Catalog;
import uniSystem.courseTypes.Course;
import uniSystem.courseTypes.FullCourse;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class JsonReader {

        JSONParser parser = new JSONParser();
        JSONObject obj = null;

        private TreeSet<Assistant> getAssitants(JSONArray assistantsArray, String subject) {
            TreeSet<Assistant> treeSet = new TreeSet<>((assistant, t1) -> {
                int result = assistant.getLastName().compareTo(t1.getLastName());
                if (result == 0) {
                    return assistant.getFirstName().compareTo(t1.getFirstName());
                } else {
                    return result;
                }
            });
            for (Object o : assistantsArray) {
                JSONObject assistantObject = (JSONObject) o;
                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add(subject);
                treeSet.add(new Assistant((String) assistantObject.get("firstName"),
                        (String) assistantObject.get("lastName"), arrayList));
            }
            return treeSet;
        }

//        private HashMap<String, Group> formStudentsMap(JSONArray groupsArray) {
//            HashMap<String, Group> hashMap = new HashMap<>();
//            for (int i =0 ; i < groupsArray.size(); i++) {
//                JSONObject object = (JSONObject) groupsArray.get(i);
//                hashMap.put(object.get("ID"), new Group((String) object.get("ID"),null,
//            }
//            return hashMap;
//        }

        public void parseMyFile() throws IOException, ParseException {
            obj = (JSONObject) parser.parse(new FileReader(System.getProperty("user.dir") +
                    "/src/testAllFunctionalities/manageInput/fileTest.json"));
            JSONArray courses = (JSONArray) obj.get("courses");
            ArrayList<Course> allCourses = new ArrayList<>();
            for(int i = 0 ; i < courses.size(); i++) {
                JSONObject object = (JSONObject) courses.get(0);
                String type = (String) object.get("type");
                if(type.equals("FullCourse")) {
                    JSONObject teacherObject = (JSONObject)object.get("teacher");
                    JSONArray assistantsArray = (JSONArray)object.get("assistants");
                    ArrayList<String> arrayList = new ArrayList<>();
                    arrayList.add((String)object.get("name"));
                    FullCourse fullCourse = (FullCourse) new FullCourse.FullCourseBuilder()
                            .setName((String)object.get("name"))
                            .setCreditPoints(5)
                            .setTeacher(new Teacher((String)teacherObject.get("firstName"),
                                    (String)teacherObject.get("lastName"), arrayList))
                            .setAllAssistants(getAssitants(assistantsArray, type))
                            .setGroupHashMap(new HashMap<>())
                            .build();
                    Catalog.getInstance().addCourse(fullCourse);
                }
                System.out.println(object.get("strategy"));
                System.out.println();
            }
        }

        // loop array
    }
