package ui;

import model.Planner;
import model.Task;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Planner planner = new Planner();
        new GraphicalUserInterface(planner);
    }
}
