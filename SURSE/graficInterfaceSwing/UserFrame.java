package graficInterfaceSwing;
import appUsers.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

public abstract class UserFrame extends JFrame {
    JPanel firstPannel, listPannel, buttonPannel, displayPannel;
    JLabel[] labelVector = new JLabel[3];
    User myUser;

    public User getUser() {
        return myUser;
    }


    public JPanel getListPannel() {
        return listPannel;
    }

    public JPanel getDisplayPannel() {
        return displayPannel;
    }

    public UserFrame(User myUser) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 750);
        this.setLayout(new BorderLayout());
        this.myUser = myUser;

        firstPannel = new JPanel();
        firstPannel.setPreferredSize(new Dimension(100, 150));
        firstPannel.setLayout(null);

        if (myUser != null) {
            JLabel firstName_label, lastName_label, status_label;
            firstName_label = new JLabel("First Name: " + myUser.getFirstName());
            firstName_label.setBounds(20, 0, 250, 50);
            firstName_label.setFont(new Font("Serif", Font.PLAIN, 20));

            lastName_label = new JLabel("Last Name: " + myUser.getLastName());
            lastName_label.setBounds(20, 50, 250, 50);
            lastName_label.setFont(new Font("Serif", Font.PLAIN, 20));

            String labelString = null;
            if (myUser instanceof Student) {
                labelString = "Status: student";
            } else if (myUser instanceof Teacher) {
                labelString = "Status: teacher";
            } else if (myUser instanceof Assistant) {
                labelString = "Status: assistant";
            } else if (myUser instanceof Parent) {
                labelString = "Status: parent";
            }

            status_label = new JLabel(labelString);
            status_label.setBounds(20, 100, 250, 50);
            status_label.setFont(new Font("Serif", Font.PLAIN, 20));

            firstPannel.add(status_label);
            firstPannel.add(lastName_label);
            firstPannel.add(firstName_label);

            int yAxis = 0;
            for (int i = 0; i < 3; i++) {
                labelVector[i] = new JLabel();
                labelVector[i].setBounds(300, yAxis + 50 * i, 500, 50);
                labelVector[i].setFont(new Font("Serif", Font.PLAIN, 20));
                firstPannel.add(labelVector[i]);
            }

            this.add(firstPannel, BorderLayout.NORTH);
            this.setVisible(true);
        }
    }

    public void setTextInBox(ArrayList<String> elementsFill, Color color) {
        JTextArea jTextArea = new JTextArea();
        jTextArea.setFont(new Font("Serif", Font.PLAIN, 20));
        jTextArea.setBackground(color);
        jTextArea.setBounds(300, 0, 300, 150);
        jTextArea.setVisible(true);
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : elementsFill) {
            stringBuilder.append(s).append('\n');
        }
        jTextArea.setText(stringBuilder.toString());
        jTextArea.add(firstPannel);
    }
    public void setTextInPannels(String textPannel1, String textPannel2, String textPannel3) {
        labelVector[0].setText(textPannel1);
        labelVector[1].setText(textPannel2);
        labelVector[2].setText(textPannel3);
    }

    public void setJListInListPannel(Vector vector, Color selectedColor, Color unselectedColor) {
        listPannel = new JPanel();
        listPannel.setPreferredSize(new Dimension(300, 100));
        listPannel.setBackground(Color.LIGHT_GRAY);
        listPannel.setLayout(new GridLayout());

        DefaultListModel<Object> defaultListModel = new DefaultListModel<>();
        defaultListModel.addAll(vector);
        JList list = new JList(defaultListModel);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setFixedCellHeight(100);
        list.setFixedCellWidth(300);
        list.setFont(new Font("Serif", Font.PLAIN, 20));
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setCellRenderer(new SelectedListCellRenderer(selectedColor, unselectedColor));

        list.getSelectionModel().addListSelectionListener(event -> {
            if(!list.getValueIsAdjusting()) {
                ListElemDisplayMediator listElemDisplayMediator = new ListElemDisplayMediator(displayPannel,
                        list.getSelectedValue(), this.myUser);
                listElemDisplayMediator.mediate();
            }
        });

        JScrollPane listScroller = new JScrollPane(list);
        listPannel.add(listScroller);
        this.add(listPannel, BorderLayout.WEST);
    }
    public void setPannel2(ActionListener actionListener, Color color) {
        buttonPannel = new JPanel();
        buttonPannel.setLayout(new GridLayout(0, 1));
        buttonPannel.setPreferredSize(new Dimension(150, 100));
        JTextField textField = new JTextField();
        JButton[] buttons = new JButton[4];
        for(int i = 0; i < 4; i++) {
            buttons[i] = new JButton();
            buttons[i].setBounds(new Rectangle(100, 150));
            buttons[i].setText("Buttons nr " + i);
            buttons[i].setVisible(true);
            buttons[i].addActionListener(actionListener);
            buttons[i].setBackground(color);
            buttonPannel.add(buttons[i]);
        }
        this.add(buttonPannel, BorderLayout.EAST);
    }

    public void setColorForPannels(Color firstpannel, Color listPannel) {
        this.firstPannel.setBackground(firstpannel);
        this.listPannel.setBackground(listPannel);
    }
    public void setPanel3(Color color) {
        displayPannel = new JPanel();
        displayPannel.setLayout(null);
        displayPannel.setBackground(color);
        displayPannel.setPreferredSize(new Dimension(100, 100));
        this.add(displayPannel, BorderLayout.CENTER);
    }

    public abstract void updateDisplayPannel(Color color);

    private static class SelectedListCellRenderer extends DefaultListCellRenderer {
        Color colorSelected;
        Color colorUnselected;

        public SelectedListCellRenderer(Color colorSelected, Color colorUnselected) {
            super();
            this.colorSelected = colorSelected;
            this.colorUnselected = colorUnselected;
        }

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (isSelected) {
                c.setBackground(colorSelected);
            } else {
                c.setBackground(colorUnselected);
            }
            return c;
        }
    }
}
