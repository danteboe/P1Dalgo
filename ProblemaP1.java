import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Solución por
 * Juan David Torres Albarracín - 202317608
 * Daniel Bolivar -
 */
public class ProblemaP1 {

    public static void main(String[] args) {
        try {
            InputStreamReader is = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(is);

            // Leer número de casos
            String line = br.readLine();
            int casos = Integer.parseInt(line);

            for (int i = 0; i < casos; i++) {
                // Leer tamaño de la matriz R y C
                line = br.readLine();
                String[] dimensiones = line.split(" ");
                int R = Integer.parseInt(dimensiones[0]);
                int C = Integer.parseInt(dimensiones[1]);

                // Inicializar la matriz para este caso
                int[][] map = new int[R][C];

                // Poblar la matriz
                for (int r = 0; r < R; r++) {
                    line = br.readLine();
                    String[] fila = line.split(" ");
                    for (int c = 0; c < C; c++) {
                        map[r][c] = Integer.parseInt(fila[c]);
                    }
                }

                System.out.println(maxReliquias(map));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Retorna 0 si el número es negativo.
     * Evita errores relacionados con sumar -1 cuando un explorador muere.
     */
    public static int valorRealReliquias(int num){
        if(num<0){
            return 0;
        }
        return num;
    }

    //Se usa para crear copias de la matriz original
    public static int[][] deepCopy(int[][] original) {
        if (original == null) {
            return null;
        }

        final int[][] result = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            result[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return result;
    }

    public static int maxReliquias(int[][]map ){
        int max = -1;

        int[][] mapCopy1 = deepCopy(map);
        int[][] mapCopy2 = deepCopy(map);
        int[][] mapCopy3 = deepCopy(map);
        int[][] mapCopy4 = deepCopy(map);
        int[][] mapCopy5 = deepCopy(map);
        int[][] mapCopy6 = deepCopy(map);

        max = Math.max(max,orden1(mapCopy1));
        max = Math.max(max,orden2(mapCopy2));
        max = Math.max(max,orden3(mapCopy3));
        max = Math.max(max,orden4(mapCopy4));
        max = Math.max(max,orden5(mapCopy5));
        max = Math.max(max,orden6(mapCopy6));

        if(max<=0){
            max= -1;
        }
        return max;
    }

    public static int orden1(int[][]map){
        return Math.max(-1,valorRealReliquias(maxReliquiasIndiana(map))+valorRealReliquias(maxReliquiasMarian(map))+valorRealReliquias(maxReliquiasSallah(map)));
    }

    public static int orden2(int[][]map){
        return Math.max(-1,valorRealReliquias(maxReliquiasIndiana(map))+valorRealReliquias(maxReliquiasSallah(map)+valorRealReliquias(maxReliquiasMarian(map))));
    }

    public static int orden3(int[][]map){
        return Math.max(-1,valorRealReliquias(maxReliquiasMarian(map))+valorRealReliquias(maxReliquiasIndiana(map)+valorRealReliquias(maxReliquiasSallah(map))));
    }

    public static int orden4(int[][]map){
        return Math.max(-1,valorRealReliquias(maxReliquiasMarian(map))+valorRealReliquias(maxReliquiasSallah(map)+valorRealReliquias(maxReliquiasIndiana(map))));
    }

    public static int orden5(int[][]map){
        return Math.max(-1,valorRealReliquias(maxReliquiasSallah(map))+valorRealReliquias(maxReliquiasIndiana(map)+valorRealReliquias(maxReliquiasMarian(map))));
    }

    public static int orden6(int[][]map){
        return Math.max(-1,valorRealReliquias(maxReliquiasSallah(map))+valorRealReliquias(maxReliquiasMarian(map)+valorRealReliquias(maxReliquiasIndiana(map))));
    }

    public static int maxReliquiasIndiana(int[][]map ){

        int maxReliquias = -1;
        int r = map.length;
        int c = map[0].length;
        int mitad = (int)Math.floor(r/2);
        int[][] dp = new int[r][c];
        int maxI = 0;
        int maxJ=0;

        for(int i =0; i<=mitad; i++){
            for(int j = 0; j<c; j++){
            //Casilla más allá de la diagonal, a la que no pudo haber llegado
            if(j>i){
                dp[i][j]=-1;
            }
            else if(map[i][j]==-1) dp[i][j]=-1;
            else if(i==0 && j==0){
                dp[i][j] = map[i][j];
            }else if(j==0&&0<=Math.max(dp[i-1][j], dp[i-1][j+1])){
                dp[i][j] = Math.max(dp[i-1][j], dp[i-1][j+1])+ map[i][j];
            }else if(j==c-1&&0<=Math.max(dp[i-1][j], dp[i-1][j-1])){
                dp[i][j] = Math.max(dp[i-1][j], dp[i-1][j-1])+ map[i][j];
            }else if(j>0&&j<c-1&&0<=Math.max(dp[i-1][j-1], Math.max(dp[i-1][j], dp[i-1][j+1]))){
                    dp[i][j] = Math.max(dp[i-1][j-1], Math.max(dp[i-1][j], dp[i-1][j+1]))+ map[i][j];
            }else{
                dp[i][j]=-1;
            }
            if(dp[i][j]>maxReliquias){
                maxI = i;
                maxJ = j;
                maxReliquias = dp[i][j];
            }
        }
        }
        //Verifica que haya llegado a la mitad de la matriz, o que se hayan recuperado reliquias
        if(maxI!=mitad||maxReliquias<=0){
            return -1;
        }
        int i = maxI;
        int j = maxJ;
     //Backtracking para modificar la matriz según el camino recorrido
     while(i>0&&j>=0){
        if(map[i][j]>=0){
            map[i][j]=0;
            if(j==0){
                if(dp[i-1][j]>dp[i-1][j+1]){
                    i--;
                }else{
                    i--;
                    j++;
                }
            }else if(j==c-1){
                if(dp[i-1][j]>dp[i-1][j-1]){
                    i--;
                }else{
                    i--;
                    j--;

                }
            }else{
                if(dp[i-1][j-1]>dp[i-1][j]&&dp[i-1][j-1]>dp[i-1][j+1]){
                    i--;
                    j--;
                }else if(dp[i-1][j-1]<dp[i-1][j]&&dp[i-1][j]>dp[i-1][j+1]){
                    i--;
                }else{
                    i--;
                    j++;
                }
            }
            }
        }
    //printMatrix(map);
    return maxReliquias;
    }

    public static int maxReliquiasMarian(int[][]map ){
        int maxReliquias = -1;
        int r = map.length;
        int c = map[0].length;
        int[][] dp = new int[r][c];
        int mitad = (int)Math.floor(r/2);
        int maxI = 0;
        int maxJ=c-1;

        for(int i =0; i<=mitad; i++){
            for(int j = c-1; j>=0; j--){
            if(c-j>i+1){
                dp[i][j]=-1;
            }
            else if(map[i][j]==-1) dp[i][j]=-1;
            else if(i==0 && j==c-1){
                dp[i][j] = map[i][j];
            }else if(j==c-1&&0<=Math.max(dp[i-1][j], dp[i-1][j-1])){
                dp[i][j] = Math.max(dp[i-1][j], dp[i-1][j-1])+ map[i][j];
            }else if(j==0&&0<=Math.max(dp[i-1][j], dp[i-1][j+1])){
                dp[i][j] = Math.max(dp[i-1][j], dp[i-1][j+1])+ map[i][j];
            }else if(j>0&&j<c-1&&0<=Math.max(dp[i-1][j-1], Math.max(dp[i-1][j], dp[i-1][j+1]))){
                    dp[i][j] = Math.max(dp[i-1][j-1], Math.max(dp[i-1][j], dp[i-1][j+1]))+ map[i][j];
            }else{
                dp[i][j]=-1;
            }
            if(dp[i][j]>maxReliquias){
                maxI = i;
                maxJ = j;
                maxReliquias = dp[i][j];
            }
        }
        }
        //Verifica que haya llegado a la mitad de la matriz
        if(maxI!=mitad||maxReliquias<=0){
            return -1;
        }
        int i = maxI;
        int j = maxJ;
     //Backtracking para modificar la matriz según el camino recorrido
     while(i>0&&j<=c-1){
        if(map[i][j]>=0){
            map[i][j]=0;
            if(j==c-1){
                if(dp[i-1][j]>dp[i-1][j-1]){
                    i--;
                }else{
                    i--;
                    j--;

                }
            }else if(j==0){
                if(dp[i-1][j]>dp[i-1][j+1]){
                    i--;
                }else{
                    i--;
                    j++;
                }
            }else{
                if(dp[i-1][j-1]>dp[i-1][j]&&dp[i-1][j-1]>dp[i-1][j+1]){
                    //Actualiza las reliquias a 0.
                    i--;
                    j--;
                }else if(dp[i-1][j-1]<dp[i-1][j]&&dp[i-1][j]>dp[i-1][j+1]){
                    i--;
                }else{
                    i--;
                    j++;
                }
            }
        }

     }
    return maxReliquias;
    }

    public static int maxReliquiasSallah(int[][]map ){
        int maxReliquias = -1;
        int r = map.length;
        int c = map[0].length;
        int mitadR = (int)Math.floor(r/2);
        int mitadC = (int)Math.floor(c/2);
        int[][] dp = new int[r][c];
        int maxI = r-1;
        int maxJ=mitadC;

        for(int i = r-1; i>=mitadR;i--){
            for(int j=0;j<c;j++){
                if(j<mitadC-(r-i-1)||j>mitadC+(r-i-1)){
                    dp[i][j]=-1;
                }
                else if(map[i][j]==-1) dp[i][j]=-1;
                else if(i==r-1 && j==mitadC){
                    dp[i][j] = map[i][j];
                }else if(j==c-1&&0<=Math.max(dp[i+1][j], dp[i+1][j-1])){
                    dp[i][j] = Math.max(dp[i+1][j], dp[i+1][j-1])+ map[i][j];
                }else if (j==0&&0<=Math.max(dp[i+1][j],dp[i+1][j+1])){
                    dp[i][j]=Math.max(dp[i+1][j],dp[i+1][j+1])+map[i][j];
                }else if(j>0&&j<c-1&&0<=Math.max(dp[i+1][j-1], Math.max(dp[i+1][j], dp[i+1][j+1]))){
                        dp[i][j] = Math.max(dp[i+1][j-1], Math.max(dp[i+1][j], dp[i+1][j+1]))+ map[i][j];
                }else{
                    dp[i][j]=-1;
                }

                if(dp[i][j]>maxReliquias){
                    maxI = i;
                    maxJ = j;
                    maxReliquias = dp[i][j];
                }
            }
        }

        //Verifica que haya llegado a la mitad de la matriz
        if(maxI!=mitadR||maxReliquias<=0){
            return -1;
        }

        map[maxI][maxJ]=0; // i use arch btw

        return maxReliquias;
    }

    public static void printMatrix(int[][] matrix){
        for(int i = 0;i<matrix.length;i++){
            System.out.println(Arrays.toString(matrix[i]));
        }
    }

}
