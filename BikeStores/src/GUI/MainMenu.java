package GUI;

import bus.ProductBus;
import java.util.Scanner;
import repository.BrandRepository;
import repository.ProductRepository;

public class MainMenu {
    private ProductBus productBus;
    
    public MainMenu(ProductBus productBus) {
        this.productBus = productBus;
    }
    BrandRepository re = new BrandRepository();
    
    public void displayMenu() {
        Scanner sc = new Scanner(System.in);
        int choice;
        try {
            do {
                Menu.showmenu();
            
            choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
//                    System.out.println(productBus.);
                    productBus.createProduct();
                    break;
                case 2:
                    productBus.searchProductByName();
                    break;
                case 3:
                    productBus.updateProduct();
                    break;
                case 4:
                    productBus.deleteProduct();
                    break;
                case 5:
                    productBus.printAllProducts();//bug
                    break;
                case 6:
                    productBus.saveToFile();
                    break;
                case 7:
                    System.out.println("Exit");
                    break;
                default:
                    System.out.println("Invalid option. Please choose again.");
            }

            if (choice != 7) {
                System.out.println("Return menu ?(Y/N)");
                String option = sc.nextLine().trim().toUpperCase();
                if (option.equals("N")) {
                    System.out.println("Exiting...");
                    break; // Thoát chương trình nếu người dùng không muốn quay lại menu
                }
            }

        } while (choice != 7);
        } catch (Exception e) {
            System.out.println("choice invalid, try again! "); 
            displayMenu();
        }
        
    }

   public static void main(String[] args) {
    ProductRepository productRepo = new ProductRepository();
    
    // Khởi tạo ProductBus với productRepo
    ProductBus productBus = new ProductBus(productRepo);
    
    
    MainMenu mainMenu = new MainMenu(productBus);
    mainMenu.displayMenu();
}

    }

