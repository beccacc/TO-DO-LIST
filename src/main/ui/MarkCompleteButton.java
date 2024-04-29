package ui;

import model.Planner;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


//creates a new button that prompts user to mark an existing task as complete
//modelled after example given LabelChanger.java:
//https://stackoverflow.com/questions/6578205/swing-jlabel-text-change-on-the-running-application
public class MarkCompleteButton extends JFrame implements ActionListener {
    private JLabel label1;
    private JTextField field1;
    private JLabel label2;
    private JTextField field2;
    private GraphicalUserInterface gui;
    private Planner planner;

    //MODIFIES: this
    //EFFECTS: sets labels and fields
    public MarkCompleteButton(Planner planner, GraphicalUserInterface gui) {
        super("Complete Task");
        this.gui = gui;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(1000, 90));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new FlowLayout());
        JButton btn = new JButton("Complete Task");
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
        label1 = new JLabel("time");
        field1 = new JTextField(5);
        add(label1);
        add(field1);
        label2 = new JLabel("number of day 1-7");
        field2 = new JTextField(5);
        add(label2);
        add(field2);
    }


    //MODIFIES: this
    //EFFECTS: when button is clicked, performs actions to mark task as complete
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("myButton")) {
            boolean b = true;
            int time = 0;
            Planner.Day day = Planner.Day.UNKNOWN;
            try {
                time = Integer.parseInt(field1.getText());
                label1.setText("COMPLETED");
            } catch (NumberFormatException numberFormatException) {
                label1.setText("Please enter a number");
                b = false;
            }
            try {
                day = planner.findDay(Integer.parseInt(field2.getText()));
                label2.setText("");
            } catch (NumberFormatException numberFormatException) {
                label2.setText("Please enter your day as a number");
                b = false;
            }
            if (b) {
                planner.markTaskComplete(day, time);
            }
            gui.refreshPlanner(planner);
        }
    }
}