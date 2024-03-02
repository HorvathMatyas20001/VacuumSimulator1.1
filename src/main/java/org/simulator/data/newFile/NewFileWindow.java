package org.simulator.data.newFile;

import org.simulator.board.Board;
import org.simulator.controls.SimulatorLogic;
import org.simulator.controls.UniversalAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.simulator.data.saveFile.Save.saveBoardToJson;

//import static org.simulator.data.saveFile.Save.saveBoardToJson;


public class NewFileWindow extends UniversalAdapter {
    private final SimulatorLogic logic;
    private final JFrame window;
    private final JTextField fileNameField, boardSizeXField, boardSizeYField, pathField;
    private final JButton createButton, cancelButton, browseButton;
    private boolean isCorrectFileName, isCorrectBoardSizeX, isCorrectBoardSizeY, isCorrectPath;

    public NewFileWindow(SimulatorLogic logic) {
        window = new JFrame("New File");
        this.logic = logic;
        this.isCorrectFileName = false;
        this.isCorrectBoardSizeX = false;
        this.isCorrectBoardSizeY = false;
        this.isCorrectPath = true;

        window.setSize(450, 200);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setLocationRelativeTo(logic.getMainFrame());

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

//-------------------------- File Name --------------------------
        JLabel fileNameLabel = new JLabel("File Name:");
        panel.add(fileNameLabel);

        fileNameField = new JTextField(25);
        fileNameField.addKeyListener(this);
        fileNameField.setToolTipText("<html>Only alphanumeric characters are excepted<br>for example: a-z,A-Z,1-9</html>");

        panel.add(fileNameField);
//-------------------------- Board Size x --------------------------
        JLabel boardSizeXLabel = new JLabel("Board Size X:");
        panel.add(boardSizeXLabel);

        boardSizeXField = new JTextField(25);
        boardSizeXField.addKeyListener(this);
        boardSizeXField.setToolTipText("Board size can be in the range of "+logic.getXMinDimension() +" - "+logic.getXMaxDimension());
        panel.add(boardSizeXField);
//-------------------------- Board Size y --------------------------
        JLabel boardSizeYLabel = new JLabel("Board Size Y:");
        panel.add(boardSizeYLabel);

        boardSizeYField = new JTextField(25);
        boardSizeYField.addKeyListener(this);
        boardSizeYField.setToolTipText("Board size can be in the range of "+logic.getYMinDimension() +" - "+logic.getYMaxDimension());
        panel.add(boardSizeYField);
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

        createButton = new JButton("Create");
        createButton.addActionListener(this);
        panel.add(createButton);

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);
        panel.add(cancelButton);

        window.add(panel);
        window.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createButton) {
            testStatus();
            if(isCorrectFileName && isCorrectBoardSizeX && isCorrectBoardSizeY && isCorrectPath){
                creatNewFile();
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
        if(e.getSource() == fileNameField){
            nameValidation();
        }else if(e.getSource() == boardSizeXField){
            boardSizeXValidation();
        }else if(e.getSource() == boardSizeYField){
            boardSizeYValidation();
        }else if(e.getSource() == pathField){
            pathValidation();
        }
    }
    private void nameValidation(){
        if(isValidName(fileNameField)){
            fileNameField.setForeground(Color.BLACK);
            fileNameField.setToolTipText("<html>Only alphanumeric characters are excepted<br>for example: a-z,A-Z,1-9</html>");
            isCorrectFileName = true;
        }else{
            fileNameField.setForeground(Color.RED);
            fileNameField.setToolTipText("Incorrect file name,use characters that are in the range: a-z,A-Z,1-9 ");
            isCorrectFileName = false;
        }
    }
    private void boardSizeXValidation(){
        if(isValidBoardSize(boardSizeXField)){
            boardSizeXField.setForeground(Color.BLACK);
            boardSizeXField.setToolTipText("Board size can be in the range of "+logic.getXMinDimension() +" - "+logic.getXMaxDimension());
            isCorrectBoardSizeX = true;
        }else{
            boardSizeXField.setForeground(Color.RED);
            boardSizeXField.setToolTipText("Incorrect board size, enter a number in the range of "+logic.getXMinDimension() +" - "+logic.getXMaxDimension());
            isCorrectBoardSizeX = false;
        }
    }
    private void boardSizeYValidation(){
        if(isValidBoardSize(boardSizeYField)){
            boardSizeYField.setForeground(Color.BLACK);
            boardSizeYField.setToolTipText("Board size can be in the range of "+logic.getYMinDimension() +" - "+logic.getYMaxDimension());
            isCorrectBoardSizeY = true;
        }else{
            boardSizeYField.setForeground(Color.RED);
            boardSizeYField.setToolTipText("Incorrect board size, enter a number in the range of "+logic.getYMinDimension() +" - "+logic.getYMaxDimension());
            isCorrectBoardSizeY = false;
        }
    }
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
    private boolean isValidName(JTextField JTextName){
        String text = JTextName.getText();
        return text != null && text.matches("^[a-zA-Z0-9]*$") && !text.isEmpty();
    }
    private boolean isValidBoardSize(JTextField JTextSize){
        if(JTextSize.getText().isEmpty()){
            return false;
        }
        String text = JTextSize.getText();
        for (int i = 0; i < text.length(); i++) {
            if (!Character.isDigit(text.charAt(i))) {
                return false;
            }
        }
        int number = Integer.parseInt(text);
        return (number > logic.getYMinDimension() && number <= logic.getYMaxDimension());
    }
    private boolean isValidPath(JTextField JTextPath) {
        Path path = Paths.get(JTextPath.getText());
        return Files.exists(path);
    }
    private String createPath(){
        String path = pathField.getText() +"\\"+ fileNameField.getText() + ".json";
        if(pathField.getText().isEmpty()){
            path = pathField.getText() + fileNameField.getText() + ".json";
        }
        return path;
    }
    private void creatNewFile(){
        int boardSizeX = Integer.parseInt(boardSizeXField.getText());
        int boardSizeY = Integer.parseInt(boardSizeYField.getText());
        Board board = new Board(boardSizeX,boardSizeY);
        board.addMouseListener(logic);
        board.addMouseMotionListener(logic);
        logic.setBoard(board);
        logic.setPath(pathField.getText());
        logic.getMainFrame().add(board);
        logic.getBoard().revalidate();
        logic.setPath(createPath());
        logic.getInfoPanel().clearInfoPanel();
        saveBoardToJson(board,createPath());
        testPath();
    }
    private void browseFiles(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (fileChooser.showOpenDialog(window) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            pathField.setText(selectedFile.getAbsolutePath());
            isCorrectPath = isValidPath(pathField);
            pathValidation();
        }
    }
    private void testStatus(){
        System.out.println("File status   :" + isCorrectFileName);
        System.out.println("Size x status :" + isCorrectBoardSizeX);
        System.out.println("Size y status :" + isCorrectBoardSizeY);
        System.out.println("Path status   :" + isCorrectPath);
    }
    private void testPath(){
        System.out.println(createPath());
    }
}
