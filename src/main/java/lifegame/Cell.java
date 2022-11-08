package lifegame;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.security.InvalidParameterException;
import java.util.Arrays;

public class Cell extends Pane {

    private boolean aAlive;
    private boolean aNextAlive;
    private Cell[] aNeighbors;

    public Cell(int pPrefHeight, int pPrefWidth){
        aAlive = false;
        aNextAlive = false;
        aNeighbors = new Cell[8];
        updateColor();
        this.setPrefSize(pPrefWidth,pPrefHeight);
    }

    public Cell(boolean pAlive,int pPrefHeight, int pPrefWidth){
        aAlive = pAlive;
        aNextAlive = false;
        aNeighbors = new Cell[8];
        updateColor();
        this.setPrefSize(pPrefWidth,pPrefHeight);
    }

    public Cell(Cell pCell){
        aAlive = pCell.aAlive;
        aNextAlive = false;
        aNeighbors = pCell.aNeighbors;
        updateColor();
        this.setPrefSize(pCell.getPrefWidth(),pCell.getPrefHeight());
    }

    public Cell(boolean pAlive, Cell[] pNeighbors, int pPrefHeight, int pPrefWidth) throws InvalidParameterException{
        // Check if neighbors array is correct size
        if(pNeighbors.length != 8){
            throw new InvalidParameterException("Size of neighbors array is not exactly 8");
        }

        // Copy alive variable and init neighbors array and set color
        aAlive = pAlive;
        aNextAlive = false;
        aNeighbors = new Cell[8];
        updateColor();
        this.setPrefSize(pPrefWidth,pPrefHeight);

        // Copy neighbors array
        for(int i=0;i<pNeighbors.length;i++){
            aNeighbors[i] = null;

            if(pNeighbors[i] != null){
                aNeighbors[i] = pNeighbors[i];
            }
        }
    }

    private void setAlive(boolean pAlive){
        aAlive = pAlive;
        updateColor();
    }

    public boolean calculateNextAlive(){
        // Start with 0 known living neighbors
        int neighborsAlive = 0;

        // Iterate through all known neighbors
        for(int i=0;i<aNeighbors.length;i++){

            // If the neighbor is alive, increment alive neighbor counter
            if(aNeighbors[i] != null){
                neighborsAlive += (aNeighbors[i].aAlive) ? 1 : 0;
            }
        }

        // Set next alive variable according to how many
        // neighbors are alive & whether the current
        // cell is alive
        if((aAlive && neighborsAlive >=2 && neighborsAlive <= 3) || (!aAlive && neighborsAlive == 3)){
            aNextAlive = true;
        } else {
            aNextAlive = false;
        }

        // Return next alive value calculated
        return aNextAlive;
    }

    public boolean updateAlive(){
        aAlive = aNextAlive;
        aNextAlive = false;
        updateColor();
        return aAlive;
    }

    private void updateColor(){
        if(aAlive){
            this.setStyle("-fx-background-color: white;");
        }
        else{
            this.setStyle("-fx-background-color: black;");
        }
    }

    public boolean addNeighbor(Cell pCell){

        // Iterate through all neighbor slots and enter pCell into first empty slot
        for(int i = 0;i<8;i++){
            if(aNeighbors[i] == null){
                aNeighbors[i] = pCell;
                return true;
            }
        }

        // Return false if no spot was found
        return false;
    }

    public String toString(){
        int numNeighbors = 0;

        for(Cell c : aNeighbors){
            if(c != null){
                numNeighbors++;
            }
        }

        return "Cell with " + numNeighbors + " neighbor(s)";
    }

}
