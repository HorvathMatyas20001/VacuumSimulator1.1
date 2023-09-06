package org.simulator.controls;

import lombok.Getter;
import lombok.Setter;
import org.simulator.board.Board;
import org.simulator.board.Components.Tile;
import org.simulator.board.StateType;
import org.simulator.gui.ComponentButtons;
import org.simulator.gui.InfoPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;


public class SimulatorLogic extends UniversalAdapter{
    @Getter
    private final JFrame mainFrame;
    @Getter
    private Mode mode;
    @Getter @Setter
    private Board board;
    @Getter @Setter
    private StateType drawType;
    private Tile startTile;
    private Tile endTile;
    @Getter @Setter
    private String path;
    @Getter
    private final InfoPanel infoPanel;
    @Setter
    private List<ComponentButtons> componentButtonsList;

    public SimulatorLogic(JFrame mainFrame){
        this.drawType = StateType.NONE;
        this.mainFrame = mainFrame;
        mainFrame.setVisible(true);
        this.mode = Mode.DRAW_MODE;
        this.startTile = null;
        this.endTile = null;
        infoPanel = new InfoPanel();
        componentButtonsList = new ArrayList<>();
    }
    public void changeMode(Mode mode){
        this.board.changeBoardMode(mode);
        this.mode = mode;
        this.board.repaint();
    }
    private void paintTile(MouseEvent e){
        try {
            Component current = this.board.getComponentAt(e.getX(), e.getY());
            Tile temp = (Tile)current;
            if (drawType != StateType.NONE && drawType != temp.getStateType()) {
                this.board.replaceTile((Tile) current, drawType);
            }
            this.board.repaint();
        }catch(Exception i){
            System.out.println("not tile");
        }
    }
    private void getComponentInfo(MouseEvent e){
        try{
            Component current = this.board.getComponentAt(e.getX(), e.getY());
            infoPanel.updateInfoPanel((Tile)current);
        }catch(ClassCastException i){
            System.out.println("not tile");
        }
    }
    public void updateButtons(){
        System.out.println("here");
        for(ComponentButtons buttons : componentButtonsList){
            buttons.updateButton(false);
            if(buttons.getType() == drawType){
                buttons.setActiveToggle(true);
            }
        }

    }
    //mouseMoved is only for testing
    @Override
    public void mouseMoved(MouseEvent e){
        try{
            Component current = this.board.getComponentAt(e.getX(),e.getY());
            this.board.TestStatus((Tile) current);
            //test((Tile) current);
        }catch(Exception i){
            System.out.println("not a tile");
        }
    }
    @Override
    public void mouseClicked(MouseEvent e){
        if (SwingUtilities.isRightMouseButton(e)) {
            getComponentInfo(e);
        }else if(SwingUtilities.isLeftMouseButton(e)){
            paintTile(e);
        }
    }
    @Override
    public void mousePressed(MouseEvent e) {
        try{
            Component current = this.board.getComponentAt(e.getX(), e.getY());
            startTile = (Tile) current;
        }catch(Exception i){
            System.out.println("not tile");
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        try {
            Component current = this.board.getComponentAt(e.getX(), e.getY());
            endTile = (Tile) current;
            this.board.smartTileConnectDisconnect(startTile,endTile);
            endTile = null;
            startTile = null;
            this.board.repaint();
        }catch(Exception i){
            System.out.println("not tile");
        }
    }

    void test(Tile current){
        this.board.TestStatus(current);
        System.out.println("mode: " + mode);
    }
    void testStartAndEnd(Tile startTile,Tile endTile){
        System.out.println("start:");
        this.board.TestStatus(startTile);
        System.out.println("end:");
        this.board.TestStatus(endTile);
    }
}
