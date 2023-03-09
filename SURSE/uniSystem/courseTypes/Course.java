package uniSystem.courseTypes;

import appUsers.Assistant;
import appUsers.Student;
import appUsers.Teacher;
import uniSystem.Grade;
import uniSystem.Group;
import uniSystem.SearchSystem;

import java.util.*;


public abstract class Course {
    private String name;
    private Teacher teacher;
    private Set<Assistant> allAssistants;
    private TreeSet<Grade> gradesList;
    private HashMap<String, Group> groupHashMap;
    private int creditPoints;

    public Course(CourseBuilder courseBuilder) {
        name = courseBuilder.name;
        teacher = courseBuilder.teacher;
        creditPoints = courseBuilder.creditPoints;
        allAssistants = courseBuilder.allAssistants;
        groupHashMap = courseBuilder.groupHashMap;
    }

    public int getStudentsNumber() {
        int result = 0;
        for (HashMap.Entry<String, Group> entry : groupHashMap.entrySet()) {
            result = result + entry.getValue().size();
        }
        return result;
    }



    public String getName() {
        return name;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Set<Assistant> getAssistants() {
        return allAssistants;
    }
    public TreeSet<Grade> getGradesList() {
        return gradesList;
    }

    public void setGradesList(TreeSet<Grade> gradesList) {
        this.gradesList = gradesList;
    }

    public HashMap<String, Group> getGroupHashMap() {
        return groupHashMap;
    }

    public void addAssistant(String ID, Assistant assistant) {
        if (groupHashMap == null) {
            groupHashMap = new HashMap<>();
        }
        if(groupHashMap.get(ID) != null) {
            groupHashMap.get(ID).setAssistant(assistant);
            allAssistants.add(assistant);
        }
    }
    public Assistant getAssistant(String ID) {
        Optional<Assistant> assistant = allAssistants.stream().filter(assistant1 ->
                assistant1.getGroup_Id().equals(ID)).findFirst();
        return assistant.orElse(null);
    }

    public void addStudent(String ID, Student student) {
        if (groupHashMap == null) {
            groupHashMap = new HashMap<>();
        }
        groupHashMap.get(ID).add(student);
    }
    public void removeStudent(String firstName, String lastName) {
        Group group;
        Student student = SearchSystem.getInstance().searchStudentBasedOnHisName(firstName, lastName);
        for (HashMap.Entry<String, Group> entry : groupHashMap.entrySet()) {
            group = entry.getValue();
            if(group.contains(student)) {
                group.remove(student);
                break;
            }
        }
    }

    public void addGroup(Group group) {
        if (groupHashMap == null) {
            groupHashMap = new HashMap<>();
        }
        groupHashMap.put(group.getID(), group);
    }

    public void addGroup(String ID, Assistant assistant) {
        if (groupHashMap == null) {
            groupHashMap = new HashMap<>();
        }
        groupHashMap.put(ID, new Group(ID, assistant, null));
    }

    public void addGroup(String ID, Assistant assist, Comparator<Student> comp) {
        if (groupHashMap == null) {
            groupHashMap = new HashMap<>();
        }
        groupHashMap.put(ID, new Group(ID, assist, comp));
    }

    public Grade getGrade(Student student) {
        Optional<Grade> gradeStream = gradesList.stream().
                filter(grade -> grade.getStudent().equals(student)).findFirst();
        if (gradeStream.isPresent()) {
            return gradeStream.get();
        } else {
            return null;
        }
    }

    public Assistant getAssistant(String firstName, String lastName) {
        Optional<Assistant> assistant  = allAssistants.stream().filter(assis ->
                assis.getFirstName().equals(firstName) &&
                assis.getLastName().equals(lastName)).findAny();
        return assistant.orElse(null);
    }


    public void addGrade(Grade grade) {
        gradesList.add(grade);
        grade.setStudentGrade();
    }

    public ArrayList<Student> getAllStudents() {
        ArrayList<Student> returnArray = new ArrayList<>();
        for (HashMap.Entry<String, Group> entry : groupHashMap.entrySet()) {
            returnArray.addAll(entry.getValue());
        }
        return returnArray;
    }

    public HashMap<Student, Grade> getAllStudentGrades() {
        HashMap<Student, Grade> gradeHashMap = new HashMap<>();
        if (gradesList != null && gradesList.size() != 0) {
            for (Grade grade : gradesList) {
                gradeHashMap.put(grade.getStudent(), grade);
            }
        }
        return gradeHashMap;
    }

    public abstract boolean passingCondition(Grade grade);

    public abstract ArrayList<Student> getGraduatedStudents();

    @Override
    public String toString() {
        return this.name;
    }

    public static abstract class CourseBuilder {
        private String name;
        private Teacher teacher;
        private Set<Assistant> allAssistants;
        private TreeSet<Grade> gradesList;
        private HashMap<String, Group> groupHashMap;
        private int creditPoints;

        public CourseBuilder setName(String name) {

            this.name = name;
            return this;
        }

        public CourseBuilder setTeacher(Teacher teacher) {

            this.teacher = teacher;
            return this;
        }

        public CourseBuilder setAllAssistants(Set<Assistant> allAssistants) {

            this.allAssistants = allAssistants;
            return this;
        }

        public CourseBuilder setGroupHashMap(HashMap<String, Group> groupHashMap) {

            this.groupHashMap = groupHashMap;
            return this;
        }

        public CourseBuilder setCreditPoints(int creditPoints) {
            this.creditPoints = creditPoints;
            return this;
        }

        public abstract Course build();
    }

    private class Snapshot {
        public void makeBackup() {}
        public void undo() {}
    }
}
