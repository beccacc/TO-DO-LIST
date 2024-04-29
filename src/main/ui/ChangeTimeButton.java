package ui;

import model.Planner;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


//creates a new button that prompts user to change the time of an existing task
//modelled after example given LabelChanger.java:
//https://stackoverflow.com/questions/6578205/swing-jlabel-text-change-on-the-running-application
public class ChangeTimeButton extends JFrame implements ActionListener {
    private JLabel label1;
    private JTextField field1;
    private JLabel label2;
    private JTextField field2;
    private JLabel label3;
    private JTextField field3;
    private GraphicalUserInterface gui;
    private Planner planner;

    //MODIFIES: this
    //EFFECTS: sets labels and fields
    public ChangeTimeButton(Planner planner, GraphicalUserInterface gui) {
        super("Change Time");
        this.gui = gui;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(1000, 90));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new FlowLayout());
        JButton btn = new JButton("Change Time");
        btn.setActionCommand("myButton");
        btn.addActionListener(this);
        setLabels();
        add(btn);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        this.planner = planner;
    }

    //MODIFIES: this
    //EFFECTS: sets labels and fields
    public void setLabels() {
        label1 = new JLabel("day");
        field1 = new JTextField(5);
        add(label1);
        add(field1);
        label2 = new JLabel("current time");
        field2 = new JTextField(5);
        add(label2);
        add(field2);
        label3 = new JLabel("new time");
        field3 = new JTextField(5);
        add(label3);
        add(field3);
    }


    //MODIFIES: this
    //EFFECTS: when button is clicked, performs actions to change time of task
    public void actionPerformed(ActionEvent e) {
        boolean b = true;
        Planner.Day day = Planner.Day.UNKNOWN;
        int currentTime = 0;
        if (e.getActionCommand().equals("myButton")) {
            try {
                day = planner.findDay(Integer.parseInt(field1.getText()));
                label1.setText("CHANGED");
            } catch (NumberFormatException numberFormatException) {
                label1.setText("Please your day as a number");
                b = false;
            }
            try {
                currentTime = Integer.parseInt(field2.getText());
                label2.setText("");
            } catch (NumberFormatException numberFormatException) {
                label2.setText("Please enter a number");
                b = false;
            }
            actionPerformed2(b, day, currentTime);
        }
        gui.refreshPlanner(planner);
    }

    //MODIFIES: this, planner
    //EFFECTS: Sets newTime and calls changeTime to change task time in the planner if appropriate
    public void actionPerformed2(Boolean b, Planner.Day day, int currentTime) {
        int newTime = 0;
        try {
            newTime = Integer.parseInt(field3.getText());
            label3.setText("");
        } catch (NumberFormatException numberFormatException) {
            label3.setText("Please enter a number");
            b = false;
        }
        if (b) {
            planner.changeTime(day, currentTime, newTime);
        }
        gui.refreshPlanner(planner);
    }
}