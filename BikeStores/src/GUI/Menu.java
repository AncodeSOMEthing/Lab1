/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.util.Vector;

/**
 *
 * @author se180663
 */
public class Menu extends Vector<String>{
//    public Menu(){
//        super();
//    }
//    public void displayMenu(){
//        for (String item : this) {
//            System.out.println(item);
//        }
//    }
    public static void showmenu () {
    System.out.println("===== Product Management System =====");
            System.out.println("1. Create Product");
            System.out.println("2. Search Product by Name");
            System.out.println("3. Update Product");
            System.out.println("4. Delete Product");
            System.out.println("5. Print All Products");
            System.out.println("6. Save Products to File");
            System.out.println("7.Exit");
            System.out.print("Enter your choice: ");
}
}
