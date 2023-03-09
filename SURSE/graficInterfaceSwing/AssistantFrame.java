package graficInterfaceSwing;

import appUsers.Assistant;
import appUsers.User;
import graficInterfaceSwing.allCommands.Command;
import graficInterfaceSwing.allCommands.buttonsCommands.AddGradeButtonCommand;
import graficInterfaceSwing.allCommands.buttonsCommands.DeleteButtonCommand;
import graficInterfaceSwing.allCommands.buttonsCommands.UseStrategyButton;
import graficInterfaceSwing.allCommands.buttonsCommands.VisitorButtonCommand;
import uniSystem.SearchSystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class AssistantFrame extends UserFrame implements ActionListener {

    JButton[] buttons;
    public AssistantFrame(User myUser) {
        super(myUser);
        this.getContentPane().setBackground(Color.cyan);
        this.setVisible(true);
        Assistant assistant = (Assistant) super.getUser();
        Vector vector = new Vector<>(SearchSystem.getInstance().getAllCoursesOfAssistant(assistant));
        this.setJListInListPannel(vector, new Color(0x684A52), new Color(0xDF9258));
        super.setPanel3(  new Color(0x87A0B2));
        this.setVisible(true);
        updateDisplayPannel(new Color(0xA4BEF3));
        setPannel2(this, new Color(0x684A52));
        setColorForPannels(new Color(0xA5AE9E), new Color(0x857885));
        buttons = updatePannel2();
        this.setVisible(true);
    }

    public JButton[] updatePannel2() {
        JButton[] buttons = new JButton[4];
        int j = 0;
        for (int i =0; i < buttonPannel.getComponentCount(); i++) {
            if (buttonPannel.getComponent(i) instanceof JButton) {
                buttons[j] = (JButton) buttonPannel.getComponent(i);
                j++;
            }
        }
        buttons[0].setText("Visit/Set Grades");
        buttons[1].setText("Use Strategy");
        buttons[2].setText("Delete Student");
        buttons[3].setText("Modify Grade");
        return buttons;
    }

    @Override
    public void updateDisplayPannel(Color color) {
        AssistantFrame.MyDefaultTableModel model = new AssistantFrame.MyDefaultTableModel(
                new Object[]{"Number","FirstName & LastName", "PartialGrade", "ExamGrade"}, 11);
        JTable table = new JTable(model);
        table.setCellSelectionEnabled(true);
        table.setBounds((int)displayPannel.getAlignmentX(), (int)displayPannel.getAlignmentY(),
                500, 400);
        table.setBackground(color);
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
        scrollPane.setBounds(0, 0, 500, 400);
        scrollPane.setVisible(true);
        displayPannel.add(scrollPane);
    }

    public void actionPerformed(ActionEvent actionEvent) {
        Command command = null;
        if (actionEvent.getSource() == buttons[0]) {
            command = new VisitorButtonCommand(this);
        }
        if (actionEvent.getSource() == buttons[1]) {
            command = new UseStrategyButton(this);
        }
        if (actionEvent.getSource() == buttons[2]) {
            command = new DeleteButtonCommand(this);
        }
        if (actionEvent.getSource() == buttons[3]) {
            command = new AddGradeButtonCommand(this);
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
            if (column == 1 || column == 0) {
                return false;
            }  else {
                return true;
            }
        }
    }
}
