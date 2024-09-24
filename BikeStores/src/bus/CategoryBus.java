package bus;

import java.util.Map;
import java.util.Scanner;
import model.Category;
import repository.CategoryRepository;

public class CategoryBus {
    private CategoryRepository categoryRepo;

    public CategoryBus() {
    }
    
    public CategoryBus(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    public void createCategory() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Category ID: ");
        String id = sc.nextLine().trim();

        System.out.print("Enter Category Name: ");
        String name = sc.nextLine().trim();

        if (id.isEmpty() || name.isEmpty()) {
            System.out.println("Category ID and Name cannot be empty.");
            return;
        }

        Category newCategory = new Category(id, name);
        categoryRepo.create(newCategory);
        System.out.println("Category created successfully!");
    }

    public void showCategoryList() {
        for (Map.Entry<String, Category> entry : categoryRepo.entrySet()) {
            Category value = entry.getValue();
            System.out.println(value);
        }
    }

    // Other necessary methods like searching or deleting categories can be added
}
