/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package physics;

import geo.Model3D;
import geo.Point3D;
import java.util.ArrayList;
import static math.LinearAlgebra.matrixMult;
import static math.LinearAlgebra.matrixVectMult;
import static math.LinearAlgebra.rotXAxisMat;
import static math.LinearAlgebra.rotYAxisMat;
import static math.LinearAlgebra.transMat;

/**
 *
 * @author Wesley
 */
public class Collider {
    
    private Model3D parentModel;
    private double width;
    private double height;
    private double depth;
    private double offsetX;
    private double offsetY;
    private double offsetZ;
    
    public Collider(Model3D parent, double width, double height, double depth, double offsetX, double offsetY, double offsetZ) {
        this.parentModel = parent;
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
    }
    
    public boolean hasCollision(Collider testObj){
        Model3D testParent = testObj.getParentModel();
        double[][] finalTransformMat = generateTransformMat(testParent);
        
        for(int i = -1; i <= 1; i += 2){
            for(int j = -1; j <= 1; j += 2){
                for(int k = -1; k <= 1; k += 2){
                    double x = ((testObj.getWidth()/2) * i) + testObj.getOffsetX();
                    double y = ((testObj.getHeight()/2) * j) + testObj.getOffsetY();
                    double z = ((testObj.getDepth()/2) * k) + testObj.getOffsetZ();
                    
                    double[] homPt = new double[]{x, y, z, 1}; 
                    double[] transformedPt = matrixVectMult(finalTransformMat, homPt, 4);
                    if(Math.abs(transformedPt[0]) < width/2 ||
                       Math.abs(transformedPt[1]) < height/2 ||
                       Math.abs(transformedPt[2]) < depth/2){
                        return true;
                    }
                }
            }
        }
        
        return false;
    }
    
    private double[][] generateTransformMat(Model3D testParent){
        //[NegativeHomeRotateY]*[NegativeHomeRotateX]*[NegativeHomeTranslate]*[TestTranslate]*[TestRotateX]*[TestRotateY]
        //Rotate by test
        double[][] twoTransformMat = matrixMult(rotXAxisMat(testParent.getModelRotX()), rotYAxisMat(testParent.getModelRotY()), 4);
        //Translate by test
        double[][] threeTransformMat = matrixMult(transMat(testParent.getModelX(), testParent.getModelY(), testParent.getModelZ()), twoTransformMat, 4);
        //Translate by negative home
        double[][] fourTransformMat = matrixMult(transMat(-parentModel.getModelX(), -parentModel.getModelY(), -parentModel.getModelZ()), threeTransformMat, 4);
        //Rotate by negative home
        double[][] fiveTransformMat = matrixMult(rotXAxisMat(-parentModel.getModelRotX()), fourTransformMat, 4);
        return matrixMult(rotYAxisMat(-parentModel.getModelRotY()), fiveTransformMat, 4);
    }
    
    public Model3D getParentModel(){
        return parentModel;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getDepth() {
        return depth;
    }

    public double getOffsetX() {
        return offsetX;
    }

    public double getOffsetY() {
        return offsetY;
    }

    public double getOffsetZ() {
        return offsetZ;
    }
    
    
    
    
}
