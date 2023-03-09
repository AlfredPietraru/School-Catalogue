package testAllFunctionalities.testUnits;

import appUsers.Parent;
import uniSystem.Catalog;
import utilities.visitorDesignPattern.ScoreVisitor;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.StringTokenizer;

public class TestGradingSystem implements Tester {
    private final ScoreVisitor scoreVisitor;
    private final Path fileName;
    public TestGradingSystem(Path fileName) {
        scoreVisitor = new ScoreVisitor();
        this.fileName = fileName;
    }

    public ScoreVisitor getScoreVisitor() {
        return scoreVisitor;
    }

    @Override
    public void execute()  {

        String str;
        try {
            str = Files.readString(fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        StringTokenizer stringTokenizer = new StringTokenizer(str);

        //obtinerea tuturor notelor
        scoreVisitor.fillHashMaps(stringTokenizer);
        //adaugarea tuturor notelor
        Catalog.getInstance().getCourses().forEach(scoreVisitor::visit);
    }

    @Override
    public void printResults() {
        //citirea notelor din fisier
        Catalog catalog = Catalog.getInstance();

        //afisarea tuturor notelor complete
        for(int i =0; i < catalog.getCourses().size(); i++) {
            if(catalog.getCourses().get(i).getGradesList() != null) {
                catalog.getCourses().get(i).getGradesList().forEach(System.out::println);
                System.out.println();
            }
        }
        Parent randomParent = (Parent) catalog.getObservers().get(0);
        System.out.println(randomParent.getNotificationInbox().size());
        randomParent.checkAllNotifications();
        System.out.println(randomParent.getStudent().getStudentGrades());
    }
}
