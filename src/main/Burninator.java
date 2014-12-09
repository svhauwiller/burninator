/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import static math.LinearAlgebra.matrixMult;
import static math.LinearAlgebra.matrixVectMult;
import static math.LinearAlgebra.rotXAxisMat;
import static math.LinearAlgebra.rotYAxisMat;
import static math.LinearAlgebra.transMat;

/**
 *
 * @author Wesley
 */
public class Burninator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Game initGame = new Game();
        initGame.run();
    }
    
}
