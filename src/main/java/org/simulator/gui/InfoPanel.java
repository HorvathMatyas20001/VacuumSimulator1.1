package org.simulator.gui;

import org.simulator.board.Components.None;
import org.simulator.board.Components.Tile;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {

    private Tile tile;
    public InfoPanel(){
        setPreferredSize(new Dimension(250, 200));
        this.tile = new None(0,0);

    }
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        drawHeader(g);

        printTileInspectText(g);


        drawInfoTile(g);
    }
    public void updateInfoPanel(Tile tile){
        this.tile = tile;
        repaint();
    }
    private void drawHeader(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, getWidth(), 30);
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, getWidth() - 1, 30);
        Font font = new Font("Arial", Font.PLAIN, 14);
        g.setFont(font);
        g.drawString("Info Panel", 5, 20);
    }
    private void printTileInspectText(Graphics g){
        g.drawString("Inspected tile: " + this.tile.getStateType(), 5, 50);
    }
    private void drawInfoTile(Graphics g){
        int XOffset = (int) (this.getWidth() * 0.02);
        int YOffset =  60; // 25 is the offset
        int tileWidth = this.getWidth() - 2 * XOffset;
        int tileHeight = tileWidth * 2/3;
        tile.paintInfoPanelTile(g, XOffset, YOffset, tileWidth, tileHeight);
    }
    private void drawConnectionInTile(){

    }

//    private final JTextArea basicInfoTextArea;
//    private final JTextArea errorInfoTextArea;

//    public InfoPanel(){
//        setSize(800, 400);
//
//        // Create the info panel
//        JPanel infoPanel = new JPanel(new BorderLayout());
//
//        // Create the basic information text area
//        basicInfoTextArea = new JTextArea();
//        basicInfoTextArea.setEditable(false);
//        JScrollPane basicInfoScrollPane = new JScrollPane(basicInfoTextArea);
//        basicInfoScrollPane.setPreferredSize(new Dimension(300, 200));
//
//        // Create the error logs text area
//        errorInfoTextArea = new JTextArea();
//        errorInfoTextArea.setEditable(false);
//        JScrollPane errorLogScrollPane = new JScrollPane(errorInfoTextArea);
//        errorLogScrollPane.setPreferredSize(new Dimension(150, 200));
//
//        // Add the components to the info panel
//        infoPanel.add(basicInfoScrollPane, BorderLayout.NORTH);
//        infoPanel.add(errorLogScrollPane, BorderLayout.CENTER);
//
//        // Add the main panel to the JFrame
//        this.setBackground(Color.LIGHT_GRAY);
//        this.add(infoPanel);
//
//        // Update the text areas with some example content
//        basicInfoTextArea.setText("Basic Information:\n\n- Type: \n- Active: \n- Max Connection: \n- Min Connection: ");
//        errorInfoTextArea.setText("Errors:\n\n");
//    }
//    public  void updateInfoPanel(Tile tile){
//        updateInfoText(tile);
//        updateErrorText(tile);
//    }
//    private void updateInfoText(Tile tile){
//        basicInfoTextArea.setText("Basic Information:\n\n- Type: " + tile.getStateType()
//                +"\n- Active: " + tile.getStateType().isActiveElement()
//                +"\n- Max Connection: " + tile.getStateType().getMaxConnections()
//                +"\n- Min Connection: " + tile.getStateType().getMinConnections());
//    }
//    private void updateErrorText(Tile tile){
//        String connectionErrorText = "";
//        if(tile.tooFewConnectionCheck()){
//            connectionErrorText = "the current component has\n too few connections";
//        }else if(tile.tooManyConnectionCheck()){
//            connectionErrorText = "the current component has\n too many connections";
//        }
//        errorInfoTextArea.setText("Errors:\n\n"+ connectionErrorText);
//    }



}
