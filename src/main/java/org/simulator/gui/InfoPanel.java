package org.simulator.gui;

import org.simulator.board.Components.Tile;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {
    private JTextArea basicInfoTextArea;
    private JTextArea errorInfoTextArea;

    public InfoPanel(){
        setSize(600, 400);

        // Create the info panel
        JPanel infoPanel = new JPanel(new BorderLayout());

        // Create the basic information text area
        basicInfoTextArea = new JTextArea();
        basicInfoTextArea.setEditable(false);
        JScrollPane basicInfoScrollPane = new JScrollPane(basicInfoTextArea);
        basicInfoScrollPane.setPreferredSize(new Dimension(150, 200));

        // Create the error logs text area
        errorInfoTextArea = new JTextArea();
        errorInfoTextArea.setEditable(false);
        JScrollPane errorLogScrollPane = new JScrollPane(errorInfoTextArea);
        errorLogScrollPane.setPreferredSize(new Dimension(150, 200));

        // Add the components to the info panel
        infoPanel.add(basicInfoScrollPane, BorderLayout.NORTH);
        infoPanel.add(errorLogScrollPane, BorderLayout.CENTER);

        // Add the main panel to the JFrame
        this.add(infoPanel);

        // Update the text areas with some example content
        basicInfoTextArea.setText("Basic Information:\n\n- Type: \n- Active: \n- Max Connection: \n- Min Connection: ");
        errorInfoTextArea.setText("Errors:\n\n");
    }
    public  void updateInfoPanel(Tile tile){
        updateInfoText(tile);
        updateErrorText(tile);
    }
    private void updateInfoText(Tile tile){
        basicInfoTextArea.setText("Basic Information:\n\n- Type: " + tile.getStateType()
                +"\n- Active: " + tile.getStateType().isActiveElement()
                +"\n- Max Connection: " + tile.getStateType().getMaxConnections()
                +"\n- Min Connection: " + tile.getStateType().getMinConnections());
    }
    private void updateErrorText(Tile tile){
        String connectionErrorText = new String();
        if(tile.tooFewConnectionCheck()){
            connectionErrorText = "the current component has\n too few connections";
        }else if(tile.tooManyConnectionCheck()){
            connectionErrorText = "the current component has\n too many connections";
        }
        errorInfoTextArea.setText("Errors:\n\n"+ connectionErrorText);
    }



}
