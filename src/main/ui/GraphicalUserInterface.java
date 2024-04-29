package ui;

import model.Planner;
import model.Task;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

//*Creates SplitPane GUI with planner tasks on the left and buttons display on the right
public class GraphicalUserInterface extends JFrame implements ActionListener {
    private Planner planner = new Planner();
    JFrame frame;
    private JPanel left;
    private JPanel right;
    private JTextArea textArea;
    private static String photo = "data/IMG_6596.png";
    Color color;
    private JPanel photoPanel;


    //EFFECTS: instantiate variables and creates split plane for display
    public GraphicalUserInterface(Planner planner) {
        photoPanel = new JPanel();
        photoPanel = photoView(photo, photoPanel);
        frame = new JFrame(planner.getName());
        left = new JPanel();
        right = new JPanel();
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, right);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(150);
        Dimension minimumSizeLeft = new Dimension(250, 300);
        Dimension minimumSizeRight = new Dimension(250, 300);
        left.setMinimumSize(minimumSizeLeft);
        right.setMinimumSize(minimumSizeRight);
        textArea = new JTextArea(1, 1);
        displayPlanner(planner);
        right.add(buttonsDisplay(planner));
        frame.setSize(500, 850);
        frame.setVisible(true);
        frame.add(splitPane);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    //MODIFIES: this
    //EFFECTS: returns a JTextArea with the planner tasks displayed with name day and time
    public void displayPlanner(Planner planner) {
        this.planner = planner;
        textArea.setEditable(false);
        textArea.setFont(new Font("Serif", Font.PLAIN, 10));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        Collection<java.util.List<Task>> listOfTasks = planner.getPlan().values();
        if (listOfTasks.isEmpty()) {
            textArea.setVisible(false);
            left.add(photoPanel);
            photoPanel.setVisible(true);
        } else {
            textArea.setVisible(true);
            photoPanel.setVisible(false);
            String fullText = createText(planner, listOfTasks);
            textArea.setText(fullText);
            left.add(textArea);
        }
    }

    //MODIFIES: this
    //EFFECTS: takes tasks and puts them into a string that reads "Day at time: name"
    //         if the planner is empty it returns the string "No tasks found"
    public String createText(Planner planner, Collection<java.util.List<Task>> listOfTasks) {
        String text;
        String fullText = "";
        if (listOfTasks.isEmpty()) {
            return "no tasks found";
        }
        for (List<Task> tasks : listOfTasks) {
            text = "";
            if (tasks != null) {
                for (Task task : tasks) {
                    text = text + "\n" + planner.dayToString(task.getDay()) + " at " + task.getTime() + ": "
                            + task.getName();
                }
            }
            fullText = fullText + text;
        }
        return fullText;
    }

    //MODIFIES: this
    //EFFECTS: returns a JPanel using the
    public JPanel buttonsDisplay(Planner planner) {
        JPanel container = new JPanel();
        return (addComponentsToPane(container, planner));
    }

    //MODIFIES: this
    //EFFECTS: creates buttons for the functions inside of a box layout.
    public JPanel addComponentsToPane(JPanel pane, Planner planner) {
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        color = new Color(87, 184, 224, 75);
        JButton addTask = new JButton("Add a Task");
        addTask.setAlignmentX(Component.CENTER_ALIGNMENT);
        addTask.setOpaque(true);
        setButtonBasics(addTask,"addTask", color, pane);
        JButton changeTime = new JButton("Change Time of Task");
        changeTime.setAlignmentX(Component.CENTER_ALIGNMENT);
        changeTime.setOpaque(true);
        setButtonBasics(changeTime,"changeTime", color, pane);
        JButton deleteTask = new JButton("Delete a Task");
        deleteTask.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteTask.setOpaque(true);
        setButtonBasics(deleteTask,"deleteTask", color, pane);
        addComponentsToPane2(pane, color);
        return pane;
    }

    //MODIFIES: this
    //EFFECTS: creates buttons for the functions inside of a box layout.
    public void addComponentsToPane2(JPanel pane, Color color) {
        JButton howManyTasks = new JButton("How Many Tasks To-Do Today?");
        howManyTasks.setAlignmentX(Component.CENTER_ALIGNMENT);
        howManyTasks.setOpaque(true);
        setButtonBasics(howManyTasks,"howManyTasks", color, pane);
        JButton markComplete = new JButton("Mark Task as Complete");
        markComplete.setAlignmentX(Component.CENTER_ALIGNMENT);
        markComplete.setOpaque(true);
        setButtonBasics(markComplete,"markComplete", color, pane);
        JButton loadPlanner = new JButton("Load Planner from File");
        loadPlanner.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadPlanner.setOpaque(true);
        setButtonBasics(loadPlanner,"loadPlanner", color, pane);
        JButton savePlanner = new JButton("Save Planner to File");
        savePlanner.setAlignmentX(Component.CENTER_ALIGNMENT);
        savePlanner.setOpaque(true);
        setButtonBasics(savePlanner,"savePlanner", color, pane);
    }


    //EFFECTS: calls different button controls based on action event
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("addTask")) {
            new AddTaskButton(planner, this);
        } else if (e.getActionCommand().equals("changeTime")) {
            new ChangeTimeButton(planner, this);
        } else if (e.getActionCommand().equals("deleteTask")) {
            new DeleteTaskButton(planner, this);
        } else if (e.getActionCommand().equals("howManyTasks")) {
            new HowManyTasksButton(planner, this);
        } else if (e.getActionCommand().equals("markComplete")) {
            new MarkCompleteButton(planner, this);
        } else if (e.getActionCommand().equals("loadPlanner")) {
            new LoadPlannerButton(planner, this);
        } else if (e.getActionCommand().equals("savePlanner")) {
            new SavePlannerButton(planner, this);
        }
        refreshPlanner(planner);
    }

    //EFFECTS: displays most recent version of planner available
    public void refreshPlanner(Planner p) {
        displayPlanner(p);
    }

    //MODIFIES: this
    //EFFECTS: sets button action command, background color, opacity,
    //         alignment and action listener
    public void setButtonBasics(JButton button, String string, Color color, JPanel pane) {
        button.setActionCommand(string);
        button.addActionListener(this);
        button.setBackground(color);
        pane.add(button);
    }

    //MODIFIES: this
    //EFFECTS: Displays photo
    public JPanel photoView(String image, JPanel panel) {
        try {
            BufferedImage photo = ImageIO.read(new File(image));
            JLabel picLabel = new JLabel(new ImageIcon(photo));
            panel.add(picLabel);
        } catch (IOException e) {
            System.err.println("Error displaying photo");
            e.printStackTrace();
        }
        return panel;
    }


    //EFFECTS: returns frame
    public JFrame getFrame() {
        return frame;
    }
}
