package bus;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.LocalAttribute;
import static java.nio.file.Files.list;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import static java.util.Collections.list;
import java.util.Comparator;
import java.util.HashMap;
import model.Product;
import repository.ProductRepository;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class ProductBus {

    private ProductRepository productRepo;

    public ProductBus() {
    }
    //2h33 th trung id
     private Stack<String> deletedProductIds = new Stack<>();
     public void saveIddelete(){
         
     }

    public ProductBus(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }
//check id có DUY NGÃ ĐỘC TÔN hay không
    public boolean isProductIdUnique(String productId) {
        if(productRepo.details(productId) != null&&productRepo.details(productId).getId().equals(productId)){return false;}
        return true;
        
}
    // Hàm tạo sản phẩm mới
    public void createProduct() {
        Scanner sc = new Scanner(System.in);
        String id, name, brandId, categoryId;
        int modelYear = 0;
        double listPrice = 0;
//        if (productRepo == null) {
//        System.out.println("Product repository is not initialized.");
//        return;
//    }
        // Tạo ID duy nhất cho sản phẩm
        int size = productRepo.read().size();
        if(deletedProductIds.isEmpty()){
        id = "P" + (size + 1);  // Vítạo ID tự động
        }else{
            id = deletedProductIds.pop();
        }
        if(!isProductIdUnique(id)){
            id = "P" +(size +2);
        }
        // N
        System.out.print("Enter Product Name: ");
        name = sc.nextLine().trim();
        while (name.isEmpty()) {
            System.out.println("Product name cannot be empty. Please enter again:");
            name = sc.nextLine().trim();
        }

        // Nhập Brand ID và kiểm tra
//        System.out.println("Input id follow this:");
        System.out.print("Enter Brand ID: ");
        brandId = sc.nextLine().trim();
        while (!productRepo.isValidBrandId(brandId)) {
            System.out.println("Invalid Brand ID. Please enter a valid one:");
            brandId = sc.nextLine().trim();
        }

        // Nhập Category ID và kiểm tra 
        System.out.print("Enter Category ID: ");
        categoryId = sc.nextLine().trim();
        while (!productRepo.isValidCategoryId(categoryId)) {
            System.out.println("Invalid Category ID. Please enter a valid one:");
            categoryId = sc.nextLine().trim();
        }

        // Nhập năm sản xuất và kiểm tra
        int year = LocalDate.now().getYear();// cap nhap nam hien tai
        while (modelYear < 2000 || modelYear >= year) {
            System.out.print("Enter Model Year (2000 - 2024): ");
            try {
                modelYear = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid year.");
            }
        }

        // Nhập giá
        while (listPrice <= 0) {
            System.out.print("Enter List Price (positive number): ");
            try {
                listPrice = Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid price.");
            }
        }

        // Tạo đối tượng Product và thêm vào repository
        Product product = new Product(id, name, brandId, categoryId, modelYear, listPrice);
        productRepo.create(product);
        System.out.println("Product created successfully!");
    }

    // Hàm tìm kiếm sản phẩm theo tên
    public void searchProductByName() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter product name to search: ");
        String searchString = sc.nextLine().trim();

        List<Product> products = productRepo.findByName(searchString);
        if (products.isEmpty()) {
            System.out.println("No products found.");
        } else {
            products.sort((o1, o2) -> {
                return o1.getModelYear() - o2.getModelYear();

            });
            for (Product pt : products) {
                System.out.println(pt);
            }

        }
//        products.sort((p1, p2) -> Integer.compare(p1.getModelYear(), p2.getModelYear()));
//            for (Product p : products) {
//                System.out.println(p);
    }

    // Hàm Update sản phẩm-----
    public void updateProduct() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Product ID to update: ");
        String id = sc.nextLine().trim();

        Product product = productRepo.details(id);
        if (product == null) {
            System.out.println("Product does NOT EXIST.");
            return;
        }

        System.out.print("Enter new name (leave blank to keep current): ");
        String newName = sc.nextLine().trim();
        if (!newName.isEmpty()) {
            product.setName(newName);
        }

        System.out.print("Enter new Brand ID (leave blank to keep current): ");
        String newBrandId = sc.nextLine().trim();
        if (!newBrandId.isEmpty() && productRepo.isValidBrandId(newBrandId)) {
            product.setBrandId(newBrandId);
        }

        System.out.print("Enter new Category ID (leave blank to keep current): ");
        String newCategoryId = sc.nextLine().trim();
        if (!newCategoryId.isEmpty() && productRepo.isValidCategoryId(newCategoryId)) {
            product.setCategoryId(newCategoryId);
        }

        System.out.print("Enter new Model Year (leave blank to keep current): ");
        String newModelYearStr = sc.nextLine().trim();
        if (!newModelYearStr.isEmpty()) {
            try {
                int newModelYear = Integer.parseInt(newModelYearStr);
                product.setModelYear(newModelYear);
            } catch (NumberFormatException e) {
                System.out.println("Invalid year. Skipping year update.");
            }
        }

        System.out.print("Enter new List Price (leave blank to keep current): ");
        String newPriceStr = sc.nextLine().trim();
        if (!newPriceStr.isEmpty()) {
            try {
                double newPrice = Double.parseDouble(newPriceStr);
                product.setListPrice(newPrice);
            } catch (NumberFormatException e) {
                System.out.println("Invalid price. Skipping price update.");
            }
        }

        productRepo.update(product);
        System.out.println("Product updated successfully.");
    }

    public void deleteProduct() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Product ID to delete: ");
        String id = sc.nextLine().trim();

        if (productRepo.details(id) == null) {
            System.out.println("Product does NOT EXIST.");
        } else {
            System.out.print("Are you sure you want to delete this product? (Y/N): ");
            String confirmation = sc.nextLine().trim().toUpperCase();
            if (confirmation.equals("Y")) {
                productRepo.delete(id);
                System.out.println("Product deleted successfully.");
            } else {
                System.out.println("Deletion canceled.");
            }
        }
         deletedProductIds.push(id); // Lưu ID vào stack
    }

    public void saveToFile() {
        if (productRepo.saveToFile()) {
            System.out.println("Product list saved to file successfully.");
        } else {
            System.out.println("Error saving product list to file.");
        }
    }

    public void printAllProducts() {

//        HashMap<String, Product> products = productRepo.read();
        List<Product> display  = new ArrayList<>();
        display = productRepo.readFromFile();
        
        if (display.isEmpty()) {
            System.out.println("No products available.");
        } else {
            display.sort((p1, p2) -> Double.compare(p1.getListPrice(), p2.getListPrice()));

            for (Product p : display) {
                System.out.println(p);
            }
        }
    }

}
