package org.simulator.data.openFile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.simulator.board.Board;
import org.simulator.board.Components.Tile;
import org.simulator.board.Direction;
import org.simulator.board.StateType;
import org.simulator.controls.SimulatorLogic;
import org.simulator.controls.UniversalAdapter;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class OpenFileWindow extends UniversalAdapter {
    private final SimulatorLogic logic;
    private final JFrame window;
    private final JTextField pathField;
    private final JButton openButton, cancelButton, browseButton;
    private boolean isCorrectPath;
    public OpenFileWindow(SimulatorLogic logic){
        window = new JFrame("Open file");
        this.logic = logic;

        window.setSize(400,120);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setLocationRelativeTo(logic.getMainFrame());

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //-------------------------- Location --------------------------
        JLabel locationLabel = new JLabel("Location:");
        panel.add(locationLabel);

        pathField = new JTextField(25);
        pathField.addKeyListener(this);

        browseButton = new JButton("\uD83D\uDCC1");
        browseButton.setOpaque(false);
        browseButton.setContentAreaFilled(false);
        browseButton.setBorderPainted(false);
        browseButton.addActionListener(this);

        JPanel locationPanel = new JPanel(new BorderLayout());
        locationPanel.add(pathField, BorderLayout.CENTER);
        locationPanel.add(browseButton, BorderLayout.EAST);
        panel.add(locationPanel);

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);
        panel.add(cancelButton);

        openButton = new JButton("Open");
        openButton.addActionListener(this);
        panel.add(openButton);

        window.add(panel);
        window.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == openButton) {
            if(isCorrectPath){
                loadBoard();
                window.dispose();
            }

        } else if (e.getSource() == cancelButton) {
            window.dispose();
        } else if (e.getSource() == browseButton) {
            browseFiles();
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        pathValidation();
    }
    public void loadBoard(){
        Board board = readInBoard();
        board.addMouseListener(logic);
        board.addMouseMotionListener(logic);
        logic.setBoard(board);
        logic.setPath(pathField.getText());
        logic.getMainFrame().add(board);
        logic.getBoard().revalidate();
        logic.getBoard().reloadBoard();
        logic.setPath(pathField.getText());
    }
    private final FileFilter filter = new FileFilter() {
        @Override
        public boolean accept(File file) {
            return file.getName().toLowerCase().endsWith(".json") || file.isDirectory();
        }
        @Override
        public String getDescription() {
            return "JSON Files (*.json)";
        }
    };
    private void pathValidation(){
        if(isValidPath(pathField)){
            pathField.setForeground(Color.BLACK);
            isCorrectPath = true;
            System.out.println("correct");
        }else{
            pathField.setForeground(Color.RED);
            pathField.setToolTipText("Incorrect path");
            System.out.println("incorrect");
            isCorrectPath = false;
        }
    }
    private boolean isValidPath(JTextField JTextPath) {
        Path path = Paths.get(JTextPath.getText());
        return Files.exists(path);
    }
    private void browseFiles(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(filter);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        if (fileChooser.showOpenDialog(window) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            pathField.setText(selectedFile.getAbsolutePath());
            isCorrectPath = isValidPath(pathField);
            pathValidation();
        }
    }
    private Board readInBoard(){
        try {
            JSONObject jsonObject = new JSONObject(Files.readString(Paths.get(pathField.getText())));
            JSONArray boardArray = jsonObject.getJSONArray("Board");

            Board board = new Board(boardArray.length(), boardArray.getJSONArray(0).length());

            loadComponents(boardArray,board);

            loadConnections( boardArray,board);

            return board;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            // Handle the exception if the file cannot be read or if there is an error parsing the JSON data
            return null;
        }
    }
    private void loadComponents(JSONArray boardArray,Board board){
        for (int i = 0; i < boardArray.length(); i++) {
            JSONArray rowArray = boardArray.getJSONArray(i);
            for (int j = 0; j < boardArray.getJSONArray(0).length(); j++) {
                JSONObject tileObject = rowArray.getJSONObject(j);
                try {
                    Class<? extends Tile> tileClass = StateType.valueOf(tileObject.getString("State")).getTileClass();
                    Tile newTile = tileClass.getDeclaredConstructor(int.class, int.class).newInstance(i, j);
                    board.getBoard()[i][j] = newTile;
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void loadConnections(JSONArray boardArray,Board board){
        for (int i = 0; i < boardArray.length(); i++) {
            JSONArray rowArray = boardArray.getJSONArray(i);
            for (int j = 0; j < boardArray.getJSONArray(0).length(); j++) {
                JSONObject tileObject = rowArray.getJSONObject(j);
                JSONObject connectionsObject = tileObject.getJSONObject("connections");
                for (Direction direction : Direction.values()) {
                    board.getBoard()[i][j].getConnections().put(direction,connectionsObject.getBoolean(direction.name()));
                }
            }
        }
    }
}
