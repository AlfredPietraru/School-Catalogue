package graficInterfaceSwing;

import appUsers.User;
import uniSystem.SearchSystem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame implements ActionListener{
    JRadioButton[] jRadioButton = new JRadioButton[4];

    JButton button;
    JPasswordField passwordField;
    JTextField firstName, lastName;
    public LoginFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700, 700);
        this.setLocationRelativeTo(null);
        this.setLayout(null);

        JLabel  label_password, label_firstName, label_lastName, label_category;

        label_category = new JLabel("User Type:");
        label_category.setBounds(150, 100, 100, 40);
        label_firstName = new JLabel("First Name:");
        label_firstName.setBounds(150,150, 100, 40);
        label_lastName = new JLabel("Last Name:");
        label_lastName.setBounds(150, 200, 100, 40);
        label_password = new JLabel("Password:");
        label_password.setBounds(150, 250, 100, 40);

        firstName = new JTextField();
        firstName.setBounds(250, 150, 300, 40);
        lastName = new JTextField();
        lastName.setBounds(250, 200, 300, 40);
        passwordField = new JPasswordField();
        passwordField.setBounds(250, 250, 300, 40);

        button = new JButton("Sign in");
        button.setBounds(250, 300, 100, 40);
        button.addActionListener(this);

        jRadioButton[0] = new JRadioButton("student");
        jRadioButton[0].setBounds(250, 100, 100, 50);
        jRadioButton[1] = new JRadioButton("parent");
        jRadioButton[1].setBounds(350, 100, 100, 50);
        jRadioButton[2] = new JRadioButton("assistant");
        jRadioButton[2].setBounds(450, 100, 100, 50);
        jRadioButton[3] = new JRadioButton("teacher");
        jRadioButton[3].setBounds(550, 100, 100, 50);

        ButtonGroup buttonGroup = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            jRadioButton[i].addActionListener(this);
            buttonGroup.add(jRadioButton[i]);
            this.add(jRadioButton[i]);
        }

        this.add(label_category);
        this.add(label_password);
        this.add(label_firstName);
        this.add(label_lastName);
        this.add(lastName);
        this.add(firstName);
        this.add(passwordField);

        this.add(button);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == button) {
            if(firstName.getText().isEmpty() || lastName.getText().isEmpty() ||
                    passwordField.getPassword().length == 0) {
                JOptionPane.showMessageDialog(null, "all fields must be completed",
                        "Error info", JOptionPane.ERROR_MESSAGE);
                return;
            }
            PassLoginSystem passLoginSystem = PassLoginSystem.getInstance();
            passLoginSystem.setParameters(firstName.getText(), lastName.getText(),
                    passwordField.getPassword());

            for(int i =0; i < 4; i++) {
                if(jRadioButton[i].isSelected()) {
                    passLoginSystem.setUserType(jRadioButton[i].getText());
                    break;
                }
            }
            if(passLoginSystem.userType == null) {
                JOptionPane.showMessageDialog(null, "You have to decide what " +
                        "type of user are you.", "Error info", JOptionPane.ERROR_MESSAGE);
                return;
            }
            User myUser = passLoginSystem.findUser();
            if(myUser == null) {
                JOptionPane.showMessageDialog(null, "there is no user " +
                                "with this info", "Error info", JOptionPane.ERROR_MESSAGE);
                return;
            }
                if(passLoginSystem.validatePassword()) {
                    this.dispose();
                    passLoginSystem.generateNewFrame(myUser);
                } else {
                    JOptionPane.showMessageDialog(null, "Password incorrect",
                            "Error info", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    private static class PassLoginSystem {
        String firstName;
        String lastName;
        char[] password;
        String userType;
        private static final PassLoginSystem instance = new PassLoginSystem();
        private PassLoginSystem() {
        }

        void setParameters(String firstName, String lastName, char[] password) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.password = password;
            userType = null;
        }

        void setUserType(String type) {
            this.userType = type;
        }

        public static PassLoginSystem getInstance() {
            return instance;
        }
        public User findUser(){
            return switch (userType) {
                case "student" -> SearchSystem.getInstance().searchStudentBasedOnHisName(firstName,
                        lastName);
                case "parent" -> SearchSystem.getInstance().searchParentBasedOnParentName(firstName,
                        lastName);
                case "assistant" -> SearchSystem.getInstance().findRightCourseBasedOnAssistant(firstName,
                        lastName).getAssistant(firstName, lastName);
                case "teacher" -> SearchSystem.getInstance().findTeacher(firstName, lastName);
                default -> null;
            };
        }
        public void generateNewFrame(User myUser) {
            switch (userType) {
                case "student" -> new StudentFrame(myUser);
                case "parent" -> new ParentFrame(myUser);
                case "assistant" -> new AssistantFrame(myUser);
                case "teacher" -> new TeacherFrame(myUser);
                default -> {
                }
            }
        }
        public boolean validatePassword() {
            String passWordString = String.valueOf(password);
            return passWordString.equals(userType);
        }
    }
}
