package org.simulator.data;

import org.simulator.board.Board;
import org.simulator.controls.SimulatorLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class NewFileWindow extends JFrame implements ActionListener, KeyListener {
    private SimulatorLogic logic;
    private JTextField fileNameField, boardSizeXField, boardSizeYField, locationField;
    private JButton createButton, cancelButton, browseButton;
    private boolean isCorrectFileName;
    private boolean isCorrectBoardSizeX;
    private boolean isCorrectBoardSizeY;
    private boolean isCorrectPath;

    public NewFileWindow(SimulatorLogic logic) {
        super("Create NewFileWindow File");
        this.logic = logic;
        this.isCorrectFileName = false;
        this.isCorrectBoardSizeX = false;
        this.isCorrectBoardSizeY = false;
        this.isCorrectPath = false;

        setSize(450, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(logic.getMainFrame());

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
        boardSizeXField.setToolTipText("Only number are excepted in the ranke of 1-100");
        panel.add(boardSizeXField);
//-------------------------- Board Size y --------------------------
        JLabel boardSizeYLabel = new JLabel("Board Size Y:");
        panel.add(boardSizeYLabel);

        boardSizeYField = new JTextField(25);
        boardSizeYField.addKeyListener(this);
        boardSizeYField.setToolTipText("Only number are excepted in the range of 1-100");
        panel.add(boardSizeYField);
//-------------------------- Location --------------------------
        JLabel locationLabel = new JLabel("Location:");
        panel.add(locationLabel);

        locationField = new JTextField(25);
        locationField.addKeyListener(this);

        browseButton = new JButton("\uD83D\uDCC1");
        browseButton.setOpaque(false);
        browseButton.setContentAreaFilled(false);
        browseButton.setBorderPainted(false);
        browseButton.addActionListener(this);

        JPanel locationPanel = new JPanel(new BorderLayout());
        locationPanel.add(locationField, BorderLayout.CENTER);
        locationPanel.add(browseButton, BorderLayout.EAST);

        panel.add(locationPanel);

        createButton = new JButton("Create");
        createButton.addActionListener(this);
        panel.add(createButton);

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);
        panel.add(cancelButton);

        add(panel);
        setVisible(true);
    }
    //check if it is alphaNumeric
    private boolean isValidName(JTextField JTextName){
        String text = JTextName.getText();
        return text != null && text.matches("^[a-zA-Z0-9]*$");
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
        return (number > 0 && number <= 100);
    }
    private boolean isValidPath(JTextField JTextPath) {
        Path path = Paths.get(JTextPath.getText());
        return Files.exists(path);
    }
    private void creatNewFile(){
        int boardSizeX = Integer.parseInt(boardSizeXField.getText());
        int boardSizeY = Integer.parseInt(boardSizeYField.getText());
        Board board = new Board(boardSizeX,boardSizeY);
        board.addMouseListener(logic);
        board.addMouseMotionListener(logic);
        logic.setBoard(board);
        logic.getMainFrame().add(board);
        logic.getMainFrame().repaint();
        logic.getBoard().revalidate();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createButton) {
            System.out.println("File Name staturs:" + isCorrectFileName);
            System.out.println("boardSize x staturs:" + isCorrectBoardSizeX);
            System.out.println("boardSize y staturs:" + isCorrectBoardSizeY);
            System.out.println("Path staturs:" + isCorrectPath);
            if(isCorrectFileName && isCorrectBoardSizeX && isCorrectBoardSizeY && isCorrectPath){
                System.out.println("bello");
                creatNewFile();
            }


            dispose(); //close the window
        } else if (e.getSource() == cancelButton) {
            dispose(); //close the window
        } else if (e.getSource() == browseButton) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                locationField.setText(selectedFile.getAbsolutePath());
            }
        }
    }



    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getSource() == fileNameField){
            if(isValidName(fileNameField)){
                fileNameField.setForeground(Color.BLACK);
                fileNameField.setToolTipText("<html>Only alphanumeric characters are excepted<br>for example: a-z,A-Z,1-9</html>");
                isCorrectFileName = true;
            }else{
                fileNameField.setForeground(Color.RED);
                fileNameField.setToolTipText("Incorrect file name,use characters that are in the range: a-z,A-Z,1-9 ");
                isCorrectFileName = false;
            }

        }else if(e.getSource() == boardSizeXField){
            if(isValidBoardSize(boardSizeXField)){
                boardSizeXField.setForeground(Color.BLACK);
                boardSizeXField.setToolTipText("Only number are excepted in the range of 1-100");
                isCorrectBoardSizeX = true;
            }else{
                boardSizeXField.setForeground(Color.RED);
                boardSizeXField.setToolTipText("Incorrect board size, enter a number in the range of 1-100");
                isCorrectBoardSizeX = false;
            }

        }else if(e.getSource() == boardSizeYField){
            if(isValidBoardSize(boardSizeYField)){
                boardSizeYField.setForeground(Color.BLACK);
                boardSizeYField.setToolTipText("Only number are excepted in the range of 1-100");
                isCorrectBoardSizeY = true;
            }else{
                boardSizeYField.setForeground(Color.RED);
                boardSizeYField.setToolTipText("Incorrect board size, enter a number in the range of 1-100");
                isCorrectBoardSizeY = false;
            }

        }else if(e.getSource() == locationField){
            if(isValidPath(locationField)){
                locationField.setForeground(Color.BLACK);
                isCorrectPath = true;
                System.out.println("correct");
            }else{
                locationField.setForeground(Color.RED);
                locationField.setToolTipText("Incorrect path");
                System.out.println("incorrect");
                isCorrectPath = false;
            }
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }
}
