package org.simulator.gui;

import org.simulator.board.StateType;
import org.simulator.controls.Exit;
import org.simulator.controls.SimulatorLogic;
import org.simulator.data.newFile.NewFileCreator;
import org.simulator.data.openFile.OpenFileCreator;
import org.simulator.data.saveFile.Save;
import org.simulator.data.SaveFileAs.SaveAsCreator;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Simulator {
    private SimulatorLogic logic;
    private List<ComponentButtons> componentButtonsList;

    public Simulator(){
        JFrame frame = new JFrame("Vacuum Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.getContentPane().setBackground(Color.GRAY);
        //frame.pack();

        logic = new SimulatorLogic(frame);
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
        sideMenu.setLayout(new GridLayout(10, 1));
        JLabel componentLabel = new JLabel("Components");
        sideMenu.add(componentLabel);

        componentButtonsList = new ArrayList<>();

        for (StateType stateType: StateType.values()) {
            if(stateType != StateType.NONE) {
                sideMenu.add(new ComponentButtons(logic, stateType, componentButtonsList));
            }
        }

        frame.add(sideMenu, BorderLayout.LINE_END);
        frame.add(logic.getInfoPanel(),BorderLayout.LINE_START);
        frame.setVisible(true);
    }
}
