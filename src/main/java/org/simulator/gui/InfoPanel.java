package org.simulator.gui;

import org.simulator.board.Components.ActiveTile;
import org.simulator.board.Components.None;
import org.simulator.board.Components.Tile;
import org.simulator.controls.SimulatorLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InfoPanel extends JPanel {

    private Tile tile;
    private ActiveTile activeTile;
    private JButton switchButton;
    private SimulatorLogic logic;

    public InfoPanel(SimulatorLogic logic){
        this.logic = logic;
        setPreferredSize(new Dimension(250, 200));
        this.tile = new None(0,0);
        initializeSwitchButton(20,230,70,50);
        setLayout(null);
    }
    private void initializeSwitchButton(int x, int y, int width, int height) {
        switchButton = new JButton();
        switchButton.setOpaque(false);
        switchButton.setContentAreaFilled(false);
        switchButton.setBorderPainted(false);
        switchButton.setBounds(x, y, width, height);
        switchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(tile.getStateType().isActiveElement()){
                    toggleActive();
                }
            }
        });
        add(switchButton);
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        drawHeader(g,0,0,getWidth(),30,"Info Panel");
        printInspectTileName(g,0,50);
        drawInfoTile(g,(int) (this.getWidth() * 0.02),60);
        updateButtonState(g,20,230,70,50);
        drawHeader(g,0,300,getWidth(),30,"Basic information");
        printBasicInfo(g,0,330);
        drawHeader(g,0,380,getWidth(),30,"Errors ");
        printErrors(g,0,410);

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

    private void printInspectTileName(Graphics g, int XOffset, int YOffset){
        g.drawString("Inspected tile: " + this.tile.getStateType(), XOffset + 5, YOffset);
    }

    private void drawInfoTile(Graphics g, int XOffset, int YOffset){
        Graphics2D g2d = (Graphics2D) g;
        Stroke originalStroke = g2d.getStroke(); // Store the original stroke
        g2d.setStroke(new BasicStroke(2.0f));
        int tileWidth = this.getWidth() - 2 * XOffset;
        int tileHeight = tileWidth * 2/3;
        tile.paintInfoPanelTile(g, XOffset, YOffset, tileWidth, tileHeight);
        g2d.setStroke(originalStroke);
        tile.paintVacuumStateInfo(g, XOffset, YOffset, tileWidth, tileHeight);
    }

    private void updateButtonState(Graphics g, int XOffset, int YOffset, int buttonWidth, int buttonHeight) {
        if (tile.getStateType().isActiveElement()) {
            activeTile = (ActiveTile) tile;
            drawButton(g, XOffset, YOffset, buttonWidth, buttonHeight);
            switchButton.setEnabled(true);
            activeTile.setStateChangeListener((isActive) -> {
                repaint();
            });
        } else {
            switchButton.setEnabled(false);
        }
    }
    public void drawButton(Graphics g, int XOffset, int YOffset, int tileWidth, int tileHeight){
        Graphics2D g2d = (Graphics2D) g;
        Stroke originalStroke = g2d.getStroke(); // Store the original stroke
        g2d.setStroke(new BasicStroke(6.0f));

        // Draw the button text
        Font font = new Font("Arial", Font.BOLD, 12);
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics();
        String buttonText;

        if(activeTile.isActive()){
            buttonText = "On";
            g.setColor(Color.GRAY.darker().darker());
            g.fillRoundRect(XOffset, YOffset, tileWidth, tileHeight, 10, 10);

            g.setColor(Color.GREEN);
            g2d.drawRoundRect(XOffset, YOffset, tileWidth, tileHeight, 10, 10);
        }else{
            buttonText = "Off";
            g.setColor(Color.GRAY);
            g.fillRoundRect(XOffset, YOffset, tileWidth, tileHeight, 10, 10);

            g.setColor(Color.RED);
            g2d.drawRoundRect(XOffset, YOffset, tileWidth, tileHeight, 10, 10);
        }
        int textWidth = metrics.stringWidth(buttonText);
        int textX = XOffset + tileWidth / 2 - textWidth / 2;
        int textY = YOffset + tileHeight / 2 + metrics.getAscent() / 2;
        g.setColor(Color.WHITE);
        g.drawString(buttonText, textX, textY);

        g2d.setStroke(originalStroke); // Reset the stroke size
    }

    private void printBasicInfo(Graphics g, int XOffset, int YOffset) {
        String maxConnections =  "Max Connection: " + tile.getStateType().getMaxConnections() ;
        String minConnections =  "Min Connection: " + tile.getStateType().getMinConnections() ;

        g.drawString(maxConnections, XOffset + 5, YOffset + 20);
        g.drawString(minConnections, XOffset + 5, YOffset + 40);
    }
    private void printErrors(Graphics g, int XOffset, int YOffset) {
        String connectionErrorText = "None";
        if(tile.tooFewConnectionCheck()){
            g.drawString("the current component has", XOffset + 5, YOffset + 20);
            g.drawString("too few connections", XOffset + 5, YOffset + 40);
        }else if(tile.tooManyConnectionCheck()){
            g.drawString("the current component has", XOffset + 5, YOffset + 20);
            g.drawString("too many connections", XOffset + 5, YOffset + 40);
        }else{
            g.drawString("None", XOffset + 5, YOffset + 20);
        }
    }

    public void updateInfoPanel(Tile tile){
        this.tile = tile;
        repaint();
    }
    public void clearInfoPanel(){
        this.tile = new None(0,0);
        repaint();
    }
    protected void toggleActive() {
        System.out.println("click");
        activeTile.setActive(!activeTile.isActive());
        System.out.println("active:"+ activeTile.isActive());
        logic.getBoard().repaint();
        repaint();
    }
}
