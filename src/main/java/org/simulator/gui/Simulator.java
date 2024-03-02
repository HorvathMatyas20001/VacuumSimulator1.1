package org.simulator.gui;

import org.simulator.board.StateType;
import org.simulator.controls.Exit;
import org.simulator.controls.SimulatorLogic;
import org.simulator.data.SaveFileAs.SaveAsCreator;
import org.simulator.data.boardEdit.BoardEdit;
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
        frame.setBackground(Color.GRAY);
        //frame.pack();

        SimulatorLogic logic = new SimulatorLogic(frame);
        frame.addKeyListener(logic);

//-----------------------   File menu    -----------------------//
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

//-----------------------   Edit menu    -----------------------//

        JMenu editMenu = new JMenu("Edit");
        BoardEdit boardEdit = new BoardEdit(logic);

    //-----------------------   Add options    -----------------------//
        JMenu expansionSubMenu = new JMenu("Board Expansion");

        JMenuItem addRowsToTop = new JMenuItem("Add row to top");
        addRowsToTop.addActionListener(boardEdit);
        JMenuItem addRowsToBottom = new JMenuItem("Add row to bottom");
        addRowsToBottom.addActionListener(boardEdit);
        JMenuItem addColumnsToLeft = new JMenuItem("Add column to left");
        addColumnsToLeft.addActionListener(boardEdit);
        JMenuItem addColumnsToRight = new JMenuItem("Add column to right");
        addColumnsToRight.addActionListener(boardEdit);

        expansionSubMenu.add(addRowsToTop);
        expansionSubMenu.add(addRowsToBottom);
        expansionSubMenu.add(addColumnsToLeft);
        expansionSubMenu.add(addColumnsToRight);

        editMenu.add(expansionSubMenu);
    //-----------------------   Remove options    -----------------------//

        JMenu reductionSubMenu = new JMenu("Board Reduction");

        JMenuItem removeRowsFromTop = new JMenuItem("Remove row from top");
        removeRowsFromTop.addActionListener(boardEdit);
        JMenuItem removeRowsFromBottom = new JMenuItem("Remove row from bottom");
        removeRowsFromBottom.addActionListener(boardEdit);
        JMenuItem removeColumnsFromLeft = new JMenuItem("Remove column from left");
        removeColumnsFromLeft.addActionListener(boardEdit);
        JMenuItem removeColumnsFromRight = new JMenuItem("Remove column from right");
        removeColumnsFromRight.addActionListener(boardEdit);

        reductionSubMenu.add(removeRowsFromTop);
        reductionSubMenu.add(removeRowsFromBottom);
        reductionSubMenu.add(removeColumnsFromLeft);
        reductionSubMenu.add(removeColumnsFromRight);

        editMenu.add(reductionSubMenu);

        menuBar.add(editMenu);
        menuBar.setBackground(Color.GRAY);
        menuBar.setBorder(BorderFactory.createEmptyBorder());

        frame.setJMenuBar(menuBar);
//-----------------------   Component menu      -----------------------//
        JPanel sideMenu = new JPanel();
        sideMenu.setBackground(Color.GRAY);
        sideMenu.setLayout(new BorderLayout());
    //-----------------------   Header  -----------------------//
        JPanel componentHeader = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int headerWidth = getWidth();
                int headerHeight = 30; // Adjust as needed
                drawHeader(g, 0, 0, headerWidth, headerHeight, "Components");
            }
        };
        componentHeader.setBackground(Color.GRAY);
        componentHeader.setPreferredSize(new Dimension(sideMenu.getWidth(), 31));

        sideMenu.add(componentHeader, BorderLayout.PAGE_START);
    //-----------------------   Adding Components    -----------------------//
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
    private void drawHeader(Graphics g, int XOffset, int YOffset, int headerWidth, int headerHeight, String text){
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(XOffset, YOffset, headerWidth, headerHeight);
        g.setColor(Color.BLACK);
        g.drawRect(XOffset, YOffset, headerWidth - 1, headerHeight);
        Font font = new Font("Arial", Font.PLAIN, 14);
        g.setFont(font);
        g.drawString(text, XOffset + 5, YOffset+ 20);
    }
}
