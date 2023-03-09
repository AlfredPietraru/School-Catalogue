package testAllFunctionalities.testUnits;
import graficInterfaceSwing.CourseFrame;
import graficInterfaceSwing.TeacherFrame;
import org.json.simple.parser.ParseException;
import testAllFunctionalities.manageInput.ArrangeInputIntoCatalog;
import uniSystem.Catalog;

import java.io.IOException;
import java.nio.file.Path;

public class Test {
    public static void main(String[] args) throws IOException, ParseException {
        ArrangeInputIntoCatalog arrangeInputIntoCatalog = ArrangeInputIntoCatalog.getInstance();
        //plecam cu 4 clase // programul tolereaza pana la 10 grupe
        //dupa incepe sa creeze ID-uri suspecte
//        JsonReader jsonReader = new JsonReader();
//        jsonReader.parseMyFile();
        arrangeInputIntoCatalog.initGroupHashMap(4);

        TestInputManagement testInputManagement = new TestInputManagement(
                Path.of(System.getProperty("user.dir") +
                        "/testAllFunctionalities/manageInput/input.txt"));
        testInputManagement.execute();
       // testInputManagement.printResults();

        TestGradingSystem testGradingSystem = new TestGradingSystem(Path.of(System.
                getProperty("user.dir") + "/testAllFunctionalities/manageInput/gradeInput.txt"));
        testGradingSystem.execute();
//        testGradingSystem.printResults();


        TestStrategyPassingCondition testStrategyPassingCondition = new TestStrategyPassingCondition();
        testStrategyPassingCondition.execute();
        //testStrategyPassingCondition.printResults();

//        new LoginFrame();
//        Optional<Assistant> assistant = Catalog.getInstance().getCourses().get(0).getAssistants().stream().findAny();
//        new AssistantFrame(assistant.get());
        new TeacherFrame(Catalog.getInstance().getCourses().get(0).getTeacher());
//        new ParentFrame((Parent)Catalog.getInstance().getObservers().get(0));
      new CourseFrame(Catalog.getInstance().getCourses().get(0));
    }
}
