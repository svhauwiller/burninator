/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package math;

/**
 *
 * @author Wesley
 */
public class LinearAlgebra {
    
    public static double[][] transMat(double x, double y, double z){
        return new double[][]{
            {1, 0, 0, x},
            {0, 1, 0, y},
            {0, 0, 1, z},
            {0, 0, 0, 1}
        };
    }
    
    public static double[][] rotXAxisMat(double angle){
        return new double[][]{
            {1, 0, 0, 0},
            {0, Math.cos(Math.toRadians(angle)), -Math.sin(Math.toRadians(angle)), 0},
            {0, Math.sin(Math.toRadians(angle)), Math.cos(Math.toRadians(angle)), 0},
            {0, 0, 0, 1}
        };
    }
    
    public static double[][] rotYAxisMat(double angle){
        return new double[][]{
            {Math.cos(Math.toRadians(angle)), 0, Math.sin(Math.toRadians(angle)), 0},
            {0, 1, 0, 0},
            {-Math.sin(Math.toRadians(angle)), 0, Math.cos(Math.toRadians(angle)), 0},
            {0, 0, 0, 1}
        };
    }
    
    public static double[][] rotZAxisMat(double angle){
        return new double[][]{
            {Math.cos(Math.toRadians(angle)), -Math.sin(Math.toRadians(angle)), 0, 0},
            {Math.sin(Math.toRadians(angle)), Math.cos(Math.toRadians(angle)), 0, 0},
            {0, 0, 1, 0},
            {0, 0, 0, 1}
        };
    }
    
    public static double[][] matrixMult(double[][] A, double[][] B, int N){
        double [][] C = new double[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++){
               for (int k = 0; k < N; k++){
                  C[i][j] += A[i][k] * B[k][j];
               } 
            }
        }
        return C;
    }
    
    public static double[] matrixVectMult(double[][] A, double[] B, int N){
        double [] C = new double[N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++){
               C[i] += A[i][j] * B[j];
            } 
        }
        return C;
    }
}
