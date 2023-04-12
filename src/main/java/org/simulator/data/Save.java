package org.simulator.data;

import org.json.JSONArray;
import org.simulator.board.Board;
import org.simulator.controls.SimulatorLogic;
import org.simulator.controls.UniversalAdapter;

import java.awt.event.ActionEvent;
import java.io.FileWriter;


public class Save extends UniversalAdapter {
    private SimulatorLogic logic;
    public Save(SimulatorLogic logic){
        this.logic = logic;
    }

    public static void saveBoardToJson(Board board)/*throws JSException, IOException */{
        JSONArray jsonBoard = new JSONArray();
        for(int i = 0; i < board.getXDimension(); i++){
            JSONArray rowArray = new JSONArray();
            for(int j = 0; j < board.getYDimension(); j++){
                rowArray.put(i + j);
            }
            jsonBoard.put(rowArray);
        }
        try (FileWriter fileWriter = new FileWriter("test2.json")) {
            fileWriter.write(jsonBoard.toString());
        }catch(Exception e){
            System.out.println("trouble");
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        saveBoardToJson(logic.getBoard());
    }
}
