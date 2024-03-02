package org.simulator.data.boardEdit;

import org.simulator.board.Board;
import org.simulator.board.Components.Empty;
import org.simulator.board.Components.Tile;
import org.simulator.board.StateType;
import org.simulator.controls.SimulatorLogic;
import org.simulator.controls.UniversalAdapter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

public class BoardEdit extends UniversalAdapter {
    private SimulatorLogic logic;
    private Map<String, Runnable> menuActions = new HashMap<>();

    public BoardEdit(SimulatorLogic logic){
        this.logic = logic;
        initializeMenuActions();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JMenuItem) {
            JMenuItem menuItem = (JMenuItem) e.getSource();
            Runnable action = menuActions.get(menuItem.getText());
            if (action != null) {
                action.run();
            }
        }
    }
    private void initializeMenuActions(){
        menuActions.put("Add row to bottom", () -> {
            if (logic.getBoard().getXDimension() >= logic.getBoard().getXMaxDimension()) { // Check if adding would violate constraint
                JOptionPane.showMessageDialog(logic.getMainFrame(), "Adding row is prohibited as it would result in more than " +
                        logic.getBoard().getXMaxDimension() +" rows.", "Prohibited Action", JOptionPane.WARNING_MESSAGE);
            } else {
                cleanup(addRowsToBottom(1));
            }
        });
        menuActions.put("Add row to top", () -> {
            if (logic.getBoard().getXDimension() >= logic.getBoard().getXMaxDimension()) { // Check if adding would violate constraint
                JOptionPane.showMessageDialog(logic.getMainFrame(), "Adding row is prohibited as it would result in more than " +
                        logic.getBoard().getXMaxDimension() +" rows.", "Prohibited Action", JOptionPane.WARNING_MESSAGE);
            } else {
                cleanup(addRowsToTop(1));
            }
        });
        menuActions.put("Add column to right", () -> {
            if (logic.getBoard().getYDimension() >= logic.getBoard().getYMaxDimension()) { // Check if adding would violate constraint
                JOptionPane.showMessageDialog(logic.getMainFrame(), "Adding column is prohibited as it would result in more than " +
                        logic.getBoard().getYMaxDimension() +" columns.", "Prohibited Action", JOptionPane.WARNING_MESSAGE);
            } else {
                cleanup(addColumnsToRight(1));
            }
        });
        menuActions.put("Add column to left", () -> {
            if (logic.getBoard().getYDimension() >= logic.getBoard().getYMaxDimension()) { // Check if adding would violate constraint
                JOptionPane.showMessageDialog(logic.getMainFrame(), "Adding column is prohibited as it would result in more than " +
                        logic.getBoard().getYMaxDimension() +" columns.", "Prohibited Action", JOptionPane.WARNING_MESSAGE);
            } else {
                cleanup(addColumnsToLeft(1));
            }
        });

        menuActions.put("Remove row from bottom", () -> {
            if (logic.getBoard().getXDimension() <= logic.getBoard().getXMinDimension()) { // Check if removing would violate constraint
                JOptionPane.showMessageDialog(logic.getMainFrame(), "Removing row is prohibited as it would result in less than " +
                        logic.getBoard().getXMinDimension() +" rows.", "Prohibited Action", JOptionPane.WARNING_MESSAGE);
            } else {
                cleanup(removeRowsFromBottom(1));
            }
        });
        menuActions.put("Remove row from top", () -> {
            if (logic.getBoard().getXDimension() <= logic.getBoard().getXMinDimension()) { // Check if removing would violate constraint
                JOptionPane.showMessageDialog(logic.getMainFrame(), "Removing row is prohibited as it would result in less than " +
                        logic.getBoard().getXMinDimension() +" rows.", "Prohibited Action", JOptionPane.WARNING_MESSAGE);
            } else {
                cleanup(removeRowsFromTop(1));
            }
        });
        menuActions.put("Remove column from right", () -> {
            if (logic.getBoard().getYDimension() <= logic.getBoard().getYMinDimension()) { // Check if removing would violate constraint
                JOptionPane.showMessageDialog(logic.getMainFrame(), "Removing column is prohibited as it would result in less than " +
                        logic.getBoard().getYMinDimension() +" columns.", "Prohibited Action", JOptionPane.WARNING_MESSAGE);
            } else {
                cleanup(removeColumnsRight(1));
            }
        });
        menuActions.put("Remove column from left", () -> {
            if (logic.getBoard().getYDimension() <= logic.getBoard().getYMinDimension()) { // Check if removing would violate constraint
                JOptionPane.showMessageDialog(logic.getMainFrame(), "Removing column is prohibited as it would result in less than " +
                        logic.getBoard().getYMinDimension() +" columns.", "Prohibited Action", JOptionPane.WARNING_MESSAGE);
            } else {
                cleanup(removeColumnsFromLeft(1));
            }
        });
    }
//-----------------------   Add rows and columns    -----------------------//
    private Board addRowsToBottom(int numRowsToAdd) {
        int xDimension = logic.getBoard().getXDimension(); //currentRows
        int yDimension =  logic.getBoard().getYDimension(); //currentCols

        Board newBoard = new Board(xDimension + numRowsToAdd,yDimension);

        for (int i = 0; i < xDimension; i++) {
            System.arraycopy(logic.getBoard().getBoard()[i], 0, newBoard.getBoard()[i], 0, yDimension);
        }
        for (int i = xDimension; i < xDimension + numRowsToAdd; i++) {
            for (int j = 0; j < yDimension; j++) {
                newBoard.getBoard()[i][j] = new Empty(i, j);
            }
        }
       return newBoard;
    }
    private Board addColumnsToRight(int numColsToAdd) {
        int xDimension = logic.getBoard().getXDimension();
        int yDimension =  logic.getBoard().getYDimension();
        Board newBoard = new Board(xDimension ,yDimension + numColsToAdd);

        for (int i = 0; i < xDimension; i++) {
            System.arraycopy(logic.getBoard().getBoard()[i], 0, newBoard.getBoard()[i], 0, yDimension);
        }
        for (int i = 0; i < xDimension; i++) {
            for (int j = yDimension; j < yDimension + numColsToAdd; j++) {
                newBoard.getBoard()[i][j] = new Empty(i, j);
            }
        }
        return newBoard;
    }
    private Board addRowsToTop(int numRowsToAdd) {

        int xDimension = logic.getBoard().getXDimension();
        int yDimension = logic.getBoard().getYDimension();

        Board newBoard = new Board(xDimension + numRowsToAdd, yDimension);

        for (int i = 0; i < xDimension + numRowsToAdd; i++) {
            for (int j = 0; j < yDimension; j++) {
                newBoard.getBoard()[i][j] = new Empty(i, j);
            }
        }
        for (int i = 0; i < xDimension; i++) {
            for (int j = 0; j < yDimension; j++) {
                Tile tile = logic.getBoard().getBoard()[i][j];
                tile.setXCoordinate(i + 1);
                tile.setYCoordinate(j);
                newBoard.getBoard()[i + 1][j] = tile;
            }
        }
        return newBoard;
    }
    private Board addColumnsToLeft(int numColsToAdd) {
        int xDimension = logic.getBoard().getXDimension();
        int yDimension = logic.getBoard().getYDimension();

        Board newBoard = new Board(xDimension, yDimension + numColsToAdd);

        for (int i = 0; i < xDimension; i++) {
            for (int j = 0; j < yDimension + numColsToAdd; j++) {
                newBoard.getBoard()[i][j] = new Empty(i, j);
            }
        }
        for (int i = 0; i < xDimension; i++) {
            for (int j = 0; j < yDimension; j++) {
                Tile tile = logic.getBoard().getBoard()[i][j];
                tile.setXCoordinate(i);
                tile.setYCoordinate(j + 1);
                newBoard.getBoard()[i][j +1] = tile;
            }
        }
        return newBoard;
    }
//-----------------------   Remove rows and columns -----------------------//
    private Board removeRowsFromBottom(int numRowsToRemove) {
        int xDimension = logic.getBoard().getXDimension();
        int yDimension = logic.getBoard().getYDimension();
        int newRows = xDimension - numRowsToRemove;

        if (!isRowEmpty(logic.getBoard(), logic.getBoard().getXDimension() - 1)) {
            JOptionPane.showMessageDialog(logic.getMainFrame(), "Cannot remove row as it contains components.", "Prohibited Action", JOptionPane.WARNING_MESSAGE);
            return null; // Abort removal if any row is not empty
        }
        if (newRows < logic.getBoard().getXMinDimension()) {
            return null; // Cannot remove more rows than currently exist
        }
        Board newBoard = new Board(newRows, yDimension);
        for (int i = 0; i < newRows; i++) {
            System.arraycopy(logic.getBoard().getBoard()[i], 0, newBoard.getBoard()[i], 0, yDimension);
        }
        return newBoard;
    }
    private Board removeColumnsRight(int numColsToRemove) {
        int xDimension = logic.getBoard().getXDimension();
        int yDimension = logic.getBoard().getYDimension();
        int newCols = yDimension - numColsToRemove;

        if (!isColumnEmpty(logic.getBoard(), logic.getBoard().getYDimension()-1)) {
            JOptionPane.showMessageDialog(logic.getMainFrame(), "Cannot remove column as it contains components.", "Prohibited Action", JOptionPane.WARNING_MESSAGE);
            return null; // Abort removal if any row is not empty
        }
        if (newCols < logic.getBoard().getYMinDimension()) {
            return null; // Cannot remove more columns than currently exist
        }
        Board newBoard = new Board(xDimension, newCols);
        for (int i = 0; i < xDimension; i++) {
            System.arraycopy(logic.getBoard().getBoard()[i], 0, newBoard.getBoard()[i], 0, newCols);
        }
        return newBoard;
    }
    private Board removeRowsFromTop(int numRowsToRemove) {
        int xDimension = logic.getBoard().getXDimension();
        int yDimension = logic.getBoard().getYDimension();
        int newRows = xDimension - numRowsToRemove;

        if (!isRowEmpty(logic.getBoard(), 0)) {
            JOptionPane.showMessageDialog(logic.getMainFrame(), "Cannot remove row as it contains components.", "Prohibited Action", JOptionPane.WARNING_MESSAGE);
            return null; // Abort removal if any row is not empty
        }
        if (newRows < logic.getBoard().getXMinDimension()) {
            return null; // Cannot remove more rows than currently exist
        }
        Board newBoard = new Board(newRows, yDimension);
        for (int i = numRowsToRemove; i < xDimension; i++) {
            for (int j = 0; j < yDimension; j++) {
                Tile tile = logic.getBoard().getBoard()[i][j];
                tile.setXCoordinate(i - numRowsToRemove);
                tile.setYCoordinate(j);
                newBoard.getBoard()[i - numRowsToRemove][j] = tile;
            }
        }
        return newBoard;
    }
    private Board removeColumnsFromLeft(int numColsToRemove) {
        int xDimension = logic.getBoard().getXDimension();
        int yDimension = logic.getBoard().getYDimension();
        int newCols = yDimension - numColsToRemove;

        if (!isColumnEmpty(logic.getBoard(), 0)) {
            JOptionPane.showMessageDialog(logic.getMainFrame(), "Cannot remove column as it contains components.", "Prohibited Action", JOptionPane.WARNING_MESSAGE);
            return null; // Abort removal if any row is not empty
        }

        if (newCols < logic.getBoard().getYMinDimension()) {
            return null; // Cannot remove more columns than currently exist
        }
        Board newBoard = new Board(xDimension, newCols);
        for (int i = 0; i < xDimension; i++) {
            for (int j = numColsToRemove; j < yDimension; j++) {
                Tile tile = logic.getBoard().getBoard()[i][j];
                tile.setXCoordinate(i);
                tile.setYCoordinate(j - numColsToRemove);
                newBoard.getBoard()[i][j - numColsToRemove] = tile;
            }
        }
        return newBoard;
    }

//-----------------------   Tools   -----------------------//
    private void cleanup(Board board){
        if(board == null){
            return;
        }
        logic.getMainFrame().remove(logic.getBoard());
        board.addMouseListener(logic);
        board.addMouseMotionListener(logic);

        logic.setBoard(board);
        logic.getMainFrame().add(board);
        logic.getBoard().revalidate();
        logic.getBoard().reloadBoard();
        logic.getInfoPanel().clearInfoPanel();
    }
    private boolean isRowEmpty(Board board,int row){
        for (int j = 0; j < board.getYDimension(); j++) {
            if (board.getBoard()[row][j].getStateType() != StateType.EMPTY) {
                return false; // If any tile in the row is not empty, return false
            }
        }
        return true;
    }
    private boolean isColumnEmpty(Board board, int column) {
        for (int i = 0; i < board.getXDimension(); i++) {
            if (board.getBoard()[i][column].getStateType() != StateType.EMPTY) {
                return false; // If any tile in the column is not empty, return false
            }
        }
        return true; // If all tiles in the column are empty, return true
    }
}
