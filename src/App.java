import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        
        Scanner scanner = new Scanner(System.in);

        Boolean using = true;

        while(using){
            

            System.out.println("Type 'Multiply' to multiply two matrices files together.\nType 'Int' to create a new matrix file.");

            String user;
            user = scanner.nextLine();

            switch(user){
                
                case "Multiply":

                System.out.println("Enter first file name with .txt: ");
                String matrix1Path = scanner.nextLine();
                System.out.println("Enter Second file name with .txt: ");
                String matrix2Path = scanner.nextLine();

                int[][] matrix1 = readMatrixFromFile(matrix1Path);
                int[][] matrix2 = readMatrixFromFile(matrix2Path);
                    
                int[][] result = multiplyMatrices(matrix1, matrix2);
                    
                System.out.println("Results: ");
                printMatrix(result);

                if (result != null) {
                    System.out.print("Enter desired file name: ");
                    String resultFileName = scanner.next() + ".txt";
                    writeMatrixToFile(result, resultFileName);
                }

                break;

                case "Int":

                System.out.print("Enter the Number of desired Rows: ");
                int rows = scanner.nextInt();
                System.out.print("Enter the number of desired columns: ");
                int columns = scanner.nextInt();

                int[][] matrix = generateRandomMatrix(rows, columns);
                    
                System.out.print("Enter desired file name: ");
                String fileName = scanner.next() + ".txt";

                writeMatrixToFile(matrix, fileName);

                break;

                case "Stop":
                using = false;

                break;

                default:
                System.out.println("Invalid input");
                break;
            }
        }

        scanner.close();
    }

    public static int[][] generateRandomMatrix(int rows, int cols) {
        Random rand = new Random();
        int[][] matrix = new int[rows][cols];
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = rand.nextInt(10);
            }
        }
        
        return matrix;
    }

    public static void writeMatrixToFile(int[][] matrix, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    writer.write(matrix[i][j] + " ");
                }
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public static int[][] readMatrixFromFile(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        String[] values;
        int rowCount = 0;
        int colCount = 0;

        // First, read the file to count rows and columns
        while ((line = reader.readLine()) != null) {
            rowCount++;
            values = line.split("\\s+");
            colCount = values.length;
        }

        int[][] matrix = new int[rowCount][colCount];
        reader.close();

        reader = new BufferedReader(new FileReader(filePath));
        int i = 0;
        while ((line = reader.readLine()) != null) {
            values = line.split("\\s+");
            for (int j = 0; j < values.length; j++) {
                matrix[i][j] = Integer.parseInt(values[j]);
            }
            i++;
        }
        reader.close();
        
        return matrix;
    }

    public static int[][] multiplyMatrices(int[][] matrix1, int[][] matrix2) {
        int rows1 = matrix1.length;
        int cols1 = matrix1[0].length;
        int rows2 = matrix2.length;
        int cols2 = matrix2[0].length;

        // Check if multiplication is possible
        if (cols1 != rows2) {
            System.out.println("Invalid, column in first matrix must equal rows in second!");
            return null;
        }

        int[][] result = new int[rows1][cols2];

        for (int i = 0; i < rows1; i++) {
            for (int j = 0; j < cols2; j++) {
                result[i][j] = 0;
                for (int k = 0; k < cols1; k++) {
                    result[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }

        return result;
    }

    public static void printMatrix(int[][] matrix) {
        if (matrix == null) {
            return;
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
