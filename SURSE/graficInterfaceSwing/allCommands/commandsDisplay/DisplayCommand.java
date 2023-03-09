package graficInterfaceSwing.allCommands.commandsDisplay;

import appUsers.User;
import graficInterfaceSwing.allCommands.Command;

import javax.swing.*;

public abstract class DisplayCommand implements Command {

    private JPanel displayPannel;
    private User myUser;
    public DisplayCommand(JPanel displayPannel, User myUser) {
        this.displayPannel = displayPannel;
        this.myUser = myUser;
    }

    public JPanel getDisplayPannel() {
        return displayPannel;
    }

    public User getMyUser() {
        return myUser;
    }
    public JScrollPane findJScrollPanne() {
        JScrollPane scrollPane = null;
        for (int i =0; i < displayPannel.getComponentCount(); i++) {
            if(displayPannel.getComponent(i) instanceof JScrollPane)
                scrollPane = (JScrollPane) displayPannel.getComponent(i);
        }
        return scrollPane;
    }

    public abstract void execute();
}
