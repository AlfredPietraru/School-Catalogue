package testAllFunctionalities.manageInput;

import appUsers.*;
import appUsers.User;
import uniSystem.Catalog;
import uniSystem.Group;
import uniSystem.SearchSystem;
import uniSystem.courseTypes.Course;
import uniSystem.courseTypes.FullCourse;

import java.util.*;

//Jacskon - pt JSon uri
public class ArrangeInputIntoCatalog {
    private final Catalog catalog = Catalog.getInstance();
    private final ArrayList<Assistant> assistantsList;
    private final HashMap<String, Group> groupHashMap;
    private static final ArrangeInputIntoCatalog instance = new ArrangeInputIntoCatalog();

    private ArrangeInputIntoCatalog() {
        assistantsList = new ArrayList<>();
        groupHashMap = new HashMap<>();
    }

    public static ArrangeInputIntoCatalog getInstance() {
        return instance;
    }

    private String getNextGroupID(String oldID) {
        StringBuilder myName = new StringBuilder(oldID);
        char[] nrChar = new char[2];
        myName.getChars(2,3, nrChar,0);
        nrChar[0] = (char)((int)nrChar[0] + 1);
        myName.setCharAt(2, nrChar[0]);
        return myName.toString();
    }

    public void initGroupHashMap(int numberGroups) {

        String initialString = "32/CC";
        for(int i = 0; i < numberGroups; i++) {
            Group group = new Group(getNextGroupID(initialString),null, null);
            groupHashMap.put(group.getID(), group);
            initialString = group.getID();
        }
    }

    private void addStudentToGroup(Student student) {
        for (HashMap.Entry<String, Group> set :
                groupHashMap.entrySet()) {
            if(groupHashMap.get(set.getKey()).size() < 10) {
                groupHashMap.get(set.getKey()).add(student);
                student.setGroupID(set.getKey());
                break;
            }
        }
    }

    private void assignAssistentToGroup(Course assistantCourse, Assistant assistant) {
      Optional<Map.Entry<String, Group>> optionalEntry = assistantCourse.getGroupHashMap().
                entrySet().stream().filter(stringGroupEntry ->
                      stringGroupEntry.getValue().getAssistant() == null).findFirst();
      if (optionalEntry.isPresent()) {
          optionalEntry.get().getValue().setAssistant(assistant);
      } else {
            assistantsList.add(assistant);
      }
    }


    public void receiveUser(User user) {
        if (user instanceof Assistant assistant) {
            if (catalog.getCourses().size() != 0) {
                List<Course> assistantCourses = SearchSystem.getInstance().getAllCoursesOfAssistant(assistant);
                if (assistantCourses != null) {
                    assistantCourses.forEach(course -> course.getAssistants().add(assistant));
                    assistantCourses.forEach(course -> assignAssistentToGroup(course, assistant));
                } else {
                    assistantsList.add(assistant);
                }
            }
            } else if (user instanceof Teacher teacher) {
            for(int i =0; i < teacher.getAllCourses().size(); i++) {
                FullCourse fullCourse = (FullCourse) new FullCourse.FullCourseBuilder()
                        .setName(teacher.getAllCourses().get(i))
                        .setCreditPoints(5)
                        .setTeacher(teacher)
                        .setGroupHashMap(new HashMap<>(this.groupHashMap))
                        .setAllAssistants(new TreeSet<>((assistant, t1) -> {
                            int result = assistant.getLastName().compareTo(t1.getLastName());
                            if (result == 0) {
                                return assistant.getFirstName().compareTo(t1.getFirstName());
                            } else {
                                return result;
                            }
                        }))
                        .build();
                catalog.addCourse(fullCourse);
            }
            } else if (user instanceof Student) {
                addStudentToGroup((Student) user);
                catalog.addObserver(((Student) user).getMother());
                catalog.addObserver(((Student) user).getFather());
            }
    }

}
