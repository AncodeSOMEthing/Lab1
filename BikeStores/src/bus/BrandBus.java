package bus;

import model.Brand;
import repository.BrandRepository;
import java.util.Scanner;

public class BrandBus {
    private BrandRepository brandRepo;

    public BrandBus() {
    }
    
    public BrandBus(BrandRepository brandRepo) {
        this.brandRepo = brandRepo;
    }

    public void createBrand() {
        Scanner sc = new Scanner(System.in);
        String id, name, country;

        System.out.print("Enter Brand ID: ");
        id = sc.nextLine().trim();
        System.out.print("Enter Brand Name: ");
        name = sc.nextLine().trim();
        System.out.print("Enter Country: ");
        country = sc.nextLine().trim();

        Brand brand = new Brand(id, name, country);
        brandRepo.create(brand);
        System.out.println("Brand created successfully.");
    }

    public void showAllBrands() {
        for (Brand b : brandRepo.read().values()) {
            System.out.println(b);
        }
    }

    
}
