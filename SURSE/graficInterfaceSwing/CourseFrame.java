package graficInterfaceSwing;

import graficInterfaceSwing.allCommands.Command;
import graficInterfaceSwing.allCommands.buttonsCommands.*;
import graficInterfaceSwing.allCommands.commandsDisplay.GroupDisplayCommand;
import uniSystem.Group;
import uniSystem.courseTypes.Course;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

public class CourseFrame extends UserFrame implements ActionListener {

    JPanel headerPannel;
    Course myCourse;
    JButton[] buttons;
    public CourseFrame(Course myCourse) {
        super(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 750);
        this.setLayout(null);
        this.myCourse = myCourse;
        setHeaderPannel();
        setGroupJList();
        updateDisplayPannel(new Color(0x808F85));
        setButtonPannel();
        this.setVisible(true);
    }

    private void setGroupJList() {
        ArrayList<Group> groupsArray = new ArrayList<>();
        for(Map.Entry<String, Group> entry : myCourse.getGroupHashMap().entrySet()) {
            groupsArray.add(entry.getValue());
        }
        DefaultListModel<Group> defaultListModel = new DefaultListModel<>();
        defaultListModel.addAll(groupsArray);
        JList<Group> groupJList = new JList<>(defaultListModel);
        groupJList.setSize(new Dimension(200, 400));
        groupJList.setVisibleRowCount(-1);
        groupJList.setLayoutOrientation(JList.VERTICAL);
        groupJList.setFont(new Font("Serif", Font.PLAIN, 20));
        groupJList.setFixedCellHeight(100);
        groupJList.setFixedCellWidth(300);

        groupJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        groupJList.getSelectionModel().addListSelectionListener(event -> {
            if(!groupJList.getValueIsAdjusting()) {
                GroupDisplayCommand groupDisplayCommand = new GroupDisplayCommand(
                        groupJList.getSelectedValue(), displayPannel, myCourse);
                groupDisplayCommand.execute();
            }
        });

        JScrollPane scrollPane = new JScrollPane(groupJList);
        scrollPane.setPreferredSize(new Dimension(300, 400));
        scrollPane.getVerticalScrollBar();

        listPannel = new JPanel();
        listPannel.setBounds(0, 100, 300, 500);
        listPannel.setBackground(Color.lightGray);
        listPannel.setVisible(true);
        listPannel.add(scrollPane);
        this.add(listPannel);
    }

    private void setButtonPannel() {
        buttons = new JButton[9];
        buttonPannel = new JPanel();
        buttonPannel.setLayout(new GridLayout(0, 1));
        buttonPannel.setBounds(850, 100, 150, 600);
        for (int i =0; i < 8; i++) {
                buttons[i] = new JButton();
                buttons[i].addActionListener(this);
                buttonPannel.add(buttons[i]);
        }
        buttons[0].setText("Use Strategy");
        buttons[1].setText("Delete Student");
        buttons[2].setText("Modify Grade");
        buttons[3].setText("Add Student");
        buttons[4].setText("Add Group");
        buttons[5].setText("Remove Group");
        buttons[6].setText("Make Backup");
        buttons[7].setText("Restore Backup");
        this.add(buttonPannel);
    }

    public void setHeaderPannel() {
        headerPannel = new JPanel();
        headerPannel.setBounds(0, 0, 1000, 100);
        headerPannel.setVisible(true);
        headerPannel.setLayout(null);
        JLabel[] labels = new JLabel[5];
        for(int i =0; i < labels.length; i++) {
            labels[i] = new JLabel();
            labels[i].setFont(new Font("Serif", Font.PLAIN, 20));
            if(i < 3) {
                labels[i].setBounds(0, i * 30, 500, 30);
            }
            if ( i >= 3) {
                labels[i].setBounds(500, (i -3) * 30, 300, 30);
            }
            headerPannel.add(labels[i]);
        }
        labels[0].setText("Course name: " + myCourse.getName());
        labels[1].setText("Teacher: " + myCourse.getTeacher());
        headerPannel.setBackground(new Color(0x768948));
        this.add(headerPannel);
    }

    @Override
    public void updateDisplayPannel(Color color) {
        displayPannel = new JPanel();
        displayPannel.setBounds(300, 100, 500, 600);
        displayPannel.setBackground(new Color(0x34623F));
        CourseFrame.MyDefaultTableModel model = new CourseFrame.MyDefaultTableModel(
                new Object[]{"Number","FirstName & LastName", "PartialGrade", "ExamGrade"}, 11);
        JTable table = new JTable(model);
        table.setCellSelectionEnabled(true);
        table.setBounds(300, 100,
                500, 600);
        table.setBackground(new Color(0xB39C4D));
        ListSelectionModel select= table.getSelectionModel();
        select.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        table.setRowHeight(40);
        TableColumnModel tableColumnModel = table.getColumnModel();
        tableColumnModel.getColumn(0).setPreferredWidth(100);
        tableColumnModel.getColumn(1).setPreferredWidth(300);
        tableColumnModel.getColumn(2).setPreferredWidth(120);
        tableColumnModel.getColumn(3).setPreferredWidth(120);
        table.setFont(new Font("Serif", Font.PLAIN, 20));
        table.setVisible(true);


        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(table);
        scrollPane.setBounds(0, 100, 500, 400);
        scrollPane.setVisible(true);
        displayPannel.add(scrollPane);
        this.add(displayPannel);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Command command = null;
        if (actionEvent.getSource() == buttons[0]) {
            command = new UseStrategyButton(this);
        }
        if (actionEvent.getSource() == buttons[1]) {
            command = new DeleteButtonCommand(this);
        }
        if (actionEvent.getSource() == buttons[2]) {
            command = new AddGradeButtonCommand(this);
        }
        if(actionEvent.getSource() == buttons[3]) {
            System.out.println("button3");
        }
        if(actionEvent.getSource() == buttons[4]) {
            command = new AddGroupButton(this, myCourse);
        }
        if(actionEvent.getSource() == buttons[5]) {
            command = new RemoveGroupButton(this, myCourse);
        }
        if(actionEvent.getSource() == buttons[6]) {
            System.out.println("button6");
        }
        if(actionEvent.getSource() == buttons[7]) {
            System.out.println("button7");
        }
        if(command != null) {
            command.execute();
        }
    }

    private class MyDefaultTableModel extends DefaultTableModel {
        public MyDefaultTableModel(Object[] objects, int i) {
            super(objects, i);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return column != 1 && column != 0;
        }
    }
}

