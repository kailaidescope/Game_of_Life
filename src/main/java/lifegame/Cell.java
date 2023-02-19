package lifegame;

import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.security.InvalidParameterException;

public class Cell extends Pane {

    private boolean aAlive;
    private boolean aNextAlive;
    private Cell[] aNeighbors;

    /**
     * Create cell with dimensions
     *
     * @param pPrefHeight cell height
     * @param pPrefWidth cell width
     */
    public Cell(int pPrefHeight, int pPrefWidth){
        setAlive(false);
        aNeighbors = new Cell[8];

        // Set dimension attributes
        this.setPrefSize(pPrefWidth,pPrefHeight);
    }

    /**
     * Create cell with aliveness and dimensions
     *
     * @param pAlive cell alive status
     * @param pPrefHeight cell height
     * @param pPrefWidth cell width
     */
    public Cell(boolean pAlive,int pPrefHeight, int pPrefWidth){
        setAlive(pAlive);
        aNeighbors = new Cell[8];

        // Set dimension attributes
        this.setPrefSize(pPrefWidth,pPrefHeight);
    }

    /**
     * Creates cell as a shallow clone of another cell
     *
     * @param pCell cell to be cloned
     */
    public Cell(Cell pCell){
        setAlive(pCell.aAlive);

        // Make shallow clone of neighbors
        this.aNeighbors = new Cell[8];
        for(int i = 0; i<8; i++){
            this.aNeighbors[i] = pCell.aNeighbors[i];
        }

        // Set dimension attributes
        this.setPrefSize(pCell.getPrefWidth(),pCell.getPrefHeight());
    }

    /**
     * Create cell with aliveness, neighbors, and dimensions
     *
     * @param pAlive cell alive status
     * @param pNeighbors cell neighbors
     * @param pPrefHeight cell height
     * @param pPrefWidth cell width
     * @throws InvalidParameterException thrown when neighbors are incorrect length (≠8)
     */
    public Cell(boolean pAlive, Cell[] pNeighbors, int pPrefHeight, int pPrefWidth) throws InvalidParameterException{
        // Check if neighbors array is correct size
        if(pNeighbors.length != 8){
            throw new InvalidParameterException("Size of neighbors array is not exactly 8");
        }

        // Copy alive variable and init neighbors array and set color
        setAlive(pAlive);
        aNeighbors = new Cell[8];

        // Copy neighbors array
        for(int i=0;i<pNeighbors.length;i++){
            aNeighbors[i] = null;

            if(pNeighbors[i] != null){
                aNeighbors[i] = pNeighbors[i];
            }
        }

        // Set dimension attributes
        this.setPrefSize(pPrefWidth,pPrefHeight);
    }

    /**
     * Set cells alive status and change color accordingly
     *
     * @param pAlive new alive status
     */
    private void setAlive(boolean pAlive){
        aAlive = pAlive;
        aNextAlive = false;
        updateColor();
    }

    /**
     * Use neighboring cells to calculate if cell will be alive next tick
     *
     * @return alive status of next tick
     */
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

    /**
     * Calculate next alive status and apply it to this cell
     *
     * @return new alive status
     */
    public boolean updateAlive(){
        calculateNextAlive();
        aAlive = aNextAlive;
        aNextAlive = false;
        updateColor();
        return aAlive;
    }

    /**
     * Update cell color according to alive status
     */
    private void updateColor(){
        if(aAlive){
            this.setStyle("-fx-background-color: white;");
        }
        else{
            this.setStyle("-fx-background-color: black;");
        }
    }


    /**
     * Add neighbor to list of neighbors (order doesn't matter)
     *
     * @param pCell neighbor to link
     * @return false if no space was found, true if neighbor was added
     */
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

    /**
     * Gets amount of neighbors a cell has
     *
     * @return number of neighbors linked to a cell
     */
    public int getNumNeighbors(){
        int numNeighbors = 0;
        for(Cell c : aNeighbors){
            if(c != null){ numNeighbors++; }
        }
        return numNeighbors;
    }

    /**
     * Stringifies Cell
     *
     * @return text version of cell
     */
    public String toString(){
        int numNeighbors = 0;

        for(Cell c : aNeighbors){
            if(c != null){
                numNeighbors++;
            }
        }

        return "["+numNeighbors+"]";
        //return "Cell with " + numNeighbors + " neighbor(s)";
    }

}
