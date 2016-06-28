package practicaada3;
 
/**
 * @author Jairo Velasco Martin
 * @author Senmao Ji Ye
 * @date December 2015
 */
 
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;
 
public class ProblemaCaballo {
 
    static int TAM;
    static int[][] movimientos = {{-2,1},{-1,2},{1,2},{2,1},{2,-1},
        {1,-2},{-1,-2},{-2,-1}};
    static String solucion = "";
    static boolean existeSol;
    
    public static void main(String[] args) {
        int row,col;
         
        do {
            Scanner in = new Scanner(System.in);
            System.out.println("Introduce N. El tablero es NxN.");
            TAM = in.nextInt();
            System.out.println("Introduce coordenada X de la casilla inicial");
            row = in.nextInt();
            System.out.println("Introduce coordenada Y de la casilla inicial");
            col = in.nextInt();
        } while(!inRange(row, col));
        
        int[][] matrizAdyacencia = new int[TAM*TAM][TAM*TAM];
        initMatriz(matrizAdyacencia);
        existeSol = solve(matrizAdyacencia,row*TAM+col,2);
        saveSolucion();
    }
 
    public static void initMatriz(int[][] matriz) {
	for(int i=0;i<matriz.length;i++){
            for(int[] mov:movimientos){
		if(inRange(i/TAM+mov[0],i%TAM+mov[1])){
                    matriz[i][(i/TAM+mov[0])*TAM+i%TAM+mov[1]] = 1;	
		}
            }
	}
    }


    public static boolean solve (int[][] matriz,int row,int cont){
        ArrayList<Integer> candidatos = new ArrayList<>();
        int minPeso = 9;
        int peso;
        int[] aux;
        
        if(cont == TAM*TAM+1){
            for(int i=0;i<matriz[row].length;i++){
            	if(matriz[i][row] == 1){
                    matriz[i][row] = 0;
            	}
            }
            solucion += Integer.toString(row/TAM)+","+Integer.toString(row%TAM);
            return true;
        }
        
        for(int i = 0;i<matriz[row].length;i++){
            if(matriz[row][i] == 1){
        	peso = calPeso(matriz,i);
                if(peso == minPeso){
                    candidatos.add(i);
                }
        	if(peso < minPeso){
                    minPeso = peso;
                    candidatos.clear();
                    candidatos.add(i);
                }
            }
        }   
        
        for(int y:candidatos){
            aux = matriz[row];
            for(int i=0;i<matriz[row].length;i++){
            	if(matriz[i][row] == 1){
                    matriz[i][row] = 0;
            	}
            }
            if(solve(matriz,y,cont+1)){
            	solucion += Integer.toString(row/TAM)+","+Integer.toString(row%TAM);
                return true;
            }
            for(int i=0;i<matriz[row].length;i++){
            	if(matriz[i][row] != aux[i]){
                    matriz[i][row] = 1;
            	}
            }
        }
        return false;
    }
 
    public static int calPeso(int[][] matriz,int row){
        int cont = 0;
        for(int i = 0;i<matriz[row].length;i++){
            if(matriz[row][i] == 1){
        	cont++;
            }
        }
        return cont;
    }
 
    public static boolean inRange(int row, int col){
        return ((row>=0)&&(row<TAM))&&((col>=0)&&(col<TAM));
    }
 
    public static void saveSolucion(){
        try {
            PrintWriter writer = new PrintWriter("salida_p3_senjiye_jaivela.txt", 
                    "UTF-8");
            if(existeSol){
                for(int i = TAM*TAM; i>0;i--) {
                    writer.println(solucion.substring((i-1)*3,i*3));
                }
                writer.close();
            }
            else{
                writer.print("Sin solucion");
                writer.close();}
            }
        catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();}
    }
}