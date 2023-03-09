package testAllFunctionalities.testUnits;

import appUsers.UserFactory;
import testAllFunctionalities.manageInput.ArrangeInputIntoCatalog;
import uniSystem.Catalog;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.StringTokenizer;

public class TestInputManagement implements Tester {
    private final ArrangeInputIntoCatalog arrangeInputIntoCatalog;
    private final Path fileName;
    public TestInputManagement(Path fileName) {
        this.arrangeInputIntoCatalog = ArrangeInputIntoCatalog.getInstance();
        this.fileName = fileName;
    }

    @Override
    public void execute()  {

        String str;
        try {
            str = Files.readString(fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        UserFactory userFactory = new UserFactory();
        StringTokenizer stringTokenizer = new StringTokenizer(str);
        //fiecare grupa va avea initial aproximativ 10 studenti
        //programul ar trebui sa faca o restructurare a tuturor grupelor in momentul in care
        //sunt mai putini de 5 in vreuna dintre grupe
        //de implementat pentru maine

        while(stringTokenizer.hasMoreTokens()) {
            arrangeInputIntoCatalog.receiveUser(userFactory.getUser(stringTokenizer));
        }
    }

    @Override
    public void printResults() {
        System.out.println(Catalog.getInstance().getCourses().get(0).getGroupHashMap());
        Catalog.getInstance().getCourses().forEach(System.out::println);
        Catalog.getInstance().getCourses().forEach(course -> System.out.println(course.getAssistants()));
        Catalog.getInstance().getCourses().forEach(course -> System.out.println(course.getTeacher()));
    }
}
