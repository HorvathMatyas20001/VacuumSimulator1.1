package org.simulator.gui;

import org.simulator.board.StateType;
import org.simulator.controls.Exit;
import org.simulator.controls.SimulatorLogic;
import org.simulator.data.SaveFileAs.SaveAsCreator;
import org.simulator.data.newFile.NewFileCreator;
import org.simulator.data.openFile.OpenFileCreator;
import org.simulator.data.saveFile.Save;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Simulator {

    public Simulator(){
        JFrame frame = new JFrame("Vacuum Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(800, 600);
        frame.setMinimumSize(new Dimension(700, 500));

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.getContentPane().setBackground(Color.GRAY);
        //frame.pack();

        SimulatorLogic logic = new SimulatorLogic(frame);
        frame.addKeyListener(logic);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");


        JMenuItem newItem = new JMenuItem("New");
        newItem.addActionListener(new NewFileCreator(logic));
        JMenuItem openItem = new JMenuItem("Open");
        openItem.addActionListener(new OpenFileCreator(logic));
        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(new Save(logic));
        JMenuItem saveAsItem = new JMenuItem("Save as");
        saveAsItem.addActionListener(new SaveAsCreator(logic));
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new Exit());

        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(saveAsItem);
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);

        frame.setJMenuBar(menuBar);

        JPanel sideMenu = new JPanel();
        sideMenu.setBackground(Color.LIGHT_GRAY);
        sideMenu.setLayout(new BorderLayout());

        JPanel labelPanel = new JPanel();
        labelPanel.setBackground(Color.LIGHT_GRAY);
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.X_AXIS));
        JLabel componentLabel = new JLabel("Components");
        labelPanel.add(Box.createHorizontalGlue()); // Pushes the label to the left
        labelPanel.add(componentLabel);
        labelPanel.add(Box.createHorizontalGlue());

        // Set the maximum height of the labelPanel to make it thinner
        labelPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        // Add labelPanel to the top (LINE_END)
        sideMenu.add(labelPanel, BorderLayout.PAGE_START);

        List<ComponentButtons> componentButtonsList = new ArrayList<>();

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBackground(Color.LIGHT_GRAY);
        buttonsPanel.setLayout(new GridLayout(0, 1)); // Vertical layout for buttons

        for (StateType stateType: StateType.values()) {
            if(stateType != StateType.NONE) {
                ComponentButtons componentButtons = new ComponentButtons(logic, stateType);
                componentButtonsList.add(componentButtons);
                buttonsPanel.add(componentButtons);
            }
        }

        // Add buttonsPanel under labelPanel
        sideMenu.add(buttonsPanel, BorderLayout.CENTER);

        logic.setComponentButtonsList(componentButtonsList);

        frame.add(sideMenu, BorderLayout.LINE_END);
        frame.add(logic.getInfoPanel(), BorderLayout.LINE_START);
        frame.setVisible(true);
    }
}
