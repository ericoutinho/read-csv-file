import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import entities.Product;

public class App {
    public static void main(String[] args) throws Exception {
        
        Locale.setDefault(Locale.US);
        Scanner input = new Scanner(System.in);

        List<Product> list = new ArrayList<>();

        System.out.print("Enter file path: ");
        String sourceFileStr = input.nextLine();
        
        File sourceFile = new File(sourceFileStr);
        String sourceFolderStr = sourceFile.getParent();

        System.out.println(sourceFolderStr);

        Boolean success = new File(sourceFolderStr + "\\out").mkdir();
        System.out.println("Folder created: " + success);

        String targetFileStr = sourceFolderStr + "\\out\\summary.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(sourceFileStr))) {
            String itemCsv = br.readLine();
            while (itemCsv != null) {
                String[] fields = itemCsv.split(",");
                String name = fields[0];
                double price = Double.parseDouble(fields[1]);
                Integer quantity = Integer.parseInt(fields[2]);
                
                list.add(new Product(name, price, quantity));
                itemCsv = br.readLine();
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(targetFileStr))) {
                for (Product item : list) {
                    bw.write(item.getName() + "," + String.format("%.2f", item.total()));
                    bw.newLine();
                }
                System.out.println(targetFileStr + " CREATED");
            }
            catch (IOException e) {
                System.out.println("Error writing file: " + e.getMessage());
            }
        }
        catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
        finally {
            input.close();
        }

    }
}
