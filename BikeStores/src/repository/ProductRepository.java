package repository;

import model.Product;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class ProductRepository implements ICrud<String, Product> {
    private HashMap<String, Product> productMap;
    private final String productFile = "Product.txt";
    private final String brandFile = "Brand.txt"; 
    private final String categoryFile = "Category.txt"; 

    
    public ProductRepository() {
    this.productMap = new HashMap<>();
    loadFromFile(); // Đảm bảo rằng dữ liệu được tải từ file khi khởi tạo repository
}

    @Override
    public int create(Product newItem) {
        
        if (!productMap.containsKey(newItem.getId())) {
            productMap.put(newItem.getId(), newItem);
            return 1;
        }
        return 0;
    }

    @Override
    public HashMap<String, Product> read() {
        return productMap;
    }

    @Override
    public Product details(String id) {
        return productMap.get(id);
    }

    @Override
    public int update(Product editItem) {
        if (productMap.containsKey(editItem.getId())) {
            productMap.put(editItem.getId(), editItem);
            return 1;
        }
        return 0;
    }

    @Override
    public int delete(String id) {
        if (productMap.containsKey(id)) {
            productMap.remove(id);
            return 1;
        }
        return 0;
    }

    // 1. Kiểm tra Brand ID
    public boolean isValidBrandId(String brandId) {
        try (BufferedReader br = new BufferedReader(new FileReader(brandFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] brandInfo = line.split(","); // Giả định dữ liệu trong Brand.txt là dạng CSV
                if (brandInfo[0].equals(brandId)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 2. Kiểm tra Category ID
    public boolean isValidCategoryId(String categoryId) {
        try (BufferedReader br = new BufferedReader(new FileReader(categoryFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] categoryInfo = line.split(","); // Giả định dữ liệu trong Category.txt là dạng CSV
                if (categoryInfo[0].equals(categoryId)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 3. Tìm kiếm pro theo tên (tên chứa chuỗi nhập)
    public List<Product> findByName(String name) {
        List<Product> result = new ArrayList<>();
        for (Product product : productMap.values()) {
            if (product.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(product);
            }
        }
        return result;
    }

    // 4. Lưu danh sách sản phẩm vào file Product.txt
//    public boolean saveToFile() {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(productFile,true))) {
//            for (Product product : productMap.values()) {
//                writer.write(product.toString()); // Giả định phương thức toString() của Product trả về định dạng CSV
//                writer.newLine();
//            }
//            return true;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
    public boolean saveToFile() {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(productFile))) {
        for (Product product : productMap.values()) {
            writer.write(String.format("%s, %s, %s, %s, modelYear: %d, Price: %.2f", 
                product.getId(), product.getName(), product.getBrandId(), 
                product.getCategoryId(), product.getModelYear(), product.getListPrice()));
            writer.newLine();
        }
        return true;
    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }
}

    // 5. Đọc danh sách sản phẩm từ file Product.txt
    public List<Product> readFromFile() {
        List<Product> productList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(productFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
//                System.out.println(line);
                String[] productData = line.split(",");
                if (productData.length == 6) {
                    String id = productData[0].trim();
                    String name = productData[1].trim();
                    String brandId = productData[2].trim();
                    String categoryId = productData[3].trim();
                   int modelYear = Integer.parseInt(productData[4].split(":")[1].trim()); // Tách giá trị modelYear
                double listPrice = Double.parseDouble(productData[5].split(":")[1].trim()); // Tách giá trị Price

                    Product product = new Product(id, name, brandId, categoryId, modelYear, listPrice);
                    productList.add(product);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productList;
    }

    // Load dữ liệu từ file vào productMap (khi khởi tạo repository)
    public void loadFromFile() {
    List<Product> products = readFromFile(); // Đọc sản phẩm từ file
    productMap.clear(); // Xóa dữ liệu cũ trong productMap
    for (Product product : products) {
        productMap.put(product.getId(), product); // Cập nhật lại productMap với dữ liệu từ file
        System.out.println("Loaded product: " + product);
    }
}
    
}
