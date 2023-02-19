package lifegame;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Life {

    private final GridPane aCellMatrix;
    private final Cell[][] aCellArray;

    /**
     * Create game board
     * @param pMatrixHeight desired vertical length of grid
     * @param pMatrixWidth desired horizontal length of grid
     * @param pWindowHeight vertical screen space for board (in pixels)
     * @param pWindowWidth horizontal screen space for board (in pixels)
     * @param pAliveChance chance that a cell is alive
     * @throws InvalidParameterException
     */
    public Life(int pMatrixHeight, int pMatrixWidth,int pWindowHeight, int pWindowWidth, double pAliveChance) throws InvalidParameterException {
        // Check if matrix dimensions are acceptable
        if(pMatrixHeight <= 0 || pMatrixWidth <= 0){
            throw new InvalidParameterException("Cannot have a matrix width or height <= 0.");
        }
        // Check if window dimensions are acceptable
        if(pWindowHeight <= 0 || pWindowWidth <= 0){
            throw new InvalidParameterException("Cannot have a window width or height <= 0.");
        }

        // Initialize cell matrix size
        aCellMatrix = new GridPane();
        aCellArray = new Cell[pMatrixHeight][pMatrixWidth];
        aCellMatrix.setStyle("-fx-background-color: black;");

        int cellHeight = pWindowHeight / pMatrixHeight;
        int cellWidth = pWindowWidth / pMatrixWidth;

        // Fill grid with cells
        // (i is y index and j is x index)
        for(int i = 0;i<pMatrixHeight;i++){
            for(int j = 0;j<pMatrixWidth;j++){

                // Set alive status according to odds provided
                boolean aliveness = (new Random().nextInt((int) (1/pAliveChance)) == 0) ? true : false;

                // Initialize each cell with a random alive value
                Cell c = new Cell(aliveness,cellHeight,cellWidth);

                // Input cells into array
                aCellArray[i][j] = c;

                // Input cells into display matrix
                aCellMatrix.add(c,i,j);
            }
        }

        // Set adjacency matrix of each cell
        // (i is y index and j is x index)
        for(int i = 0;i<pMatrixHeight;i++){
            for(int j = 0;j<pMatrixWidth;j++){
                Cell c = (Cell)aCellMatrix.getChildren().get(i*pMatrixWidth+j);

                // Add modifiers
                for(int y = -1;y<2;y++){
                    if((i+y)>=0 && (i+y)<pMatrixHeight){
                        for(int x = -1;x<2;x++){
                            if((j+x)>=0 && (j+x)<pMatrixWidth && !(x == 0 && y == 0)){
                                c.addNeighbor((Cell)aCellMatrix.getChildren()
                                        .get((i+y)*pMatrixWidth+(j+x)));
                            }
                        }
                    }
                }

                //System.out.println("("+i+", "+j+"): "+(aCellMatrix.getChildren().get(i*pMatrixWidth+j) == aCellArray[i][j])+", "+c.getNumNeighbors());
            }
        }
    }

    /**
     * Get current board to place in JavaFX pane
     *
     * @return GridPane form of board
     */
    public GridPane getCellMatrix(){return aCellMatrix;}

    /**
     * Steps the board one step forward
     */
    public void iterate(){
        for(int j = 0;j<aCellMatrix.getChildren().size();j++){
            ((Cell)aCellMatrix.getChildren().get(j)).calculateNextAlive();
        }
        for(int j = 0;j<aCellMatrix.getChildren().size();j++){
            ((Cell)aCellMatrix.getChildren().get(j)).updateAlive();
        }
    }

    /**
     * Turns board into string form
     *
     * @return text version of board
     */
    public String toString(){
        String output = "";
        for(Cell[] cellRow : aCellArray){
            output = output + "\n" + Arrays.toString(cellRow);
        }

        return output;
    }
}
