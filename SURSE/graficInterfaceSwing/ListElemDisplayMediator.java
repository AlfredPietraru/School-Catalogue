package graficInterfaceSwing;

import appUsers.*;
import graficInterfaceSwing.allCommands.commandsDisplay.*;
import uniSystem.Group;
import uniSystem.courseTypes.Course;
import utilities.Notification;

import javax.swing.*;

public class ListElemDisplayMediator implements Mediator {
    private final JPanel displayPanel;
    private final Object elem;
    private final User myUser;

    public ListElemDisplayMediator(JPanel displayPanel, Object object, User myUser) {
        this.displayPanel = displayPanel;
        this.elem = object;
        this.myUser = myUser;
    }

    @Override
    public void mediate() {
        DisplayCommand command = null;
        if (myUser instanceof Parent) {
                command = new ParentDisplayCommand((Notification) elem, displayPanel, myUser);
        } else if (myUser instanceof Student) {
            command = new StudentDisplayCommand((Course) elem, displayPanel, myUser);
        } else if ( myUser instanceof Teacher) {
            command = new TeacherDisplayCommand((Course) elem, displayPanel, myUser);
        } else if (myUser instanceof Assistant) {
            command = new AssistantDisplayCommand((Course)elem, displayPanel,  myUser);
        }
        if(command != null) {
            command.execute();
        }
    }
}
