package org.simulator.gui;

import org.simulator.board.StateType;
import org.simulator.controls.Exit;
import org.simulator.controls.SimulatorLogic;
import org.simulator.data.NewFileCreator;
import org.simulator.data.Save;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Simulator {

    private JPanel componentsMenu;
    private SimulatorLogic logic;
    private List<ComponentButtons> componentButtonsList;
    private CheckConnections checkConnectionsButton;

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
        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(new Save(logic));
        JMenuItem saveAsItem = new JMenuItem("Save as");
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
        sideMenu.setLayout(new GridLayout(11, 1));
        JLabel componentLabel = new JLabel("Components");
        sideMenu.add(componentLabel);

        CheckConnections checkConnectionsButton = new CheckConnections(logic);
        sideMenu.add(checkConnectionsButton);

        componentButtonsList = new ArrayList<>();
        logic.setComponentButtonsList(componentButtonsList);
        logic.setCheckConnectionsButton(checkConnectionsButton);

        for(StateType stateType: StateType.values()){
            if(stateType != StateType.NONE){
                componentButtonsList.add( new ComponentButtons(logic, stateType,componentButtonsList));
            }
        }

        for (ComponentButtons componentButtons : componentButtonsList) {
            sideMenu.add(componentButtons);
        }

        frame.add(sideMenu, BorderLayout.LINE_END);
        frame.setVisible(true);
    }
}
