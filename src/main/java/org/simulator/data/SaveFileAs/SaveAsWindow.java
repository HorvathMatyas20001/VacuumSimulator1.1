package org.simulator.data.SaveFileAs;

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

public class SaveAsWindow extends UniversalAdapter {
    private final SimulatorLogic logic;
    private final JFrame window;
    private final JTextField fileNameField, pathField;
    private final JButton createButton, cancelButton, browseButton;
    private boolean isCorrectFileName, isCorrectPath;

    public SaveAsWindow(SimulatorLogic logic){
        window = new JFrame("Save as");

        this.logic = logic;
        this.isCorrectFileName = false;
        this.isCorrectPath = true;

        window.setSize(400,150);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setLocationRelativeTo(logic.getMainFrame());

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

//-------------------------- File Name --------------------------
        JLabel fileNameLabel = new JLabel("File Name:");
        panel.add(fileNameLabel);

        fileNameField = new JTextField(25);
        fileNameField.addKeyListener(this);
        fileNameField.setToolTipText("<html>Only alphanumeric characters are excepted<br>for example: a-z,A-Z,1-9</html>");

        panel.add(fileNameField);
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
            if(isCorrectFileName && isCorrectPath){
                creatNewFile();
            }
            window.dispose(); //close the window
        } else if (e.getSource() == cancelButton) {
            window.dispose(); //close the window
        } else if (e.getSource() == browseButton) {
            browseFiles();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getSource() == fileNameField){
            nameValidation();

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
        if (logic.getBoard() == null) {
            System.out.println("No board to save.");
            return;
        }
        logic.setPath(pathField.getText());
        logic.getBoard().revalidate();
        logic.setPath(createPath());
        saveBoardToJson(logic.getBoard(),createPath());
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
        System.out.println("Path status   :" + isCorrectPath);
    }
    private void testPath(){
        System.out.println(createPath());
    }
}
