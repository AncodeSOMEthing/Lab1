/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import java.util.HashMap;
import model.Category;

/**
 *
 * @author se180663
 */
public class CategoryRepository extends HashMap<String, Category> implements ICrud<String, Category>{

    @Override
    public int create(Category newItem) {
           try {
            this.put(newItem.getId(), newItem);
            return 1;//insert ok
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return 0;// Insert err
        } 
    }

    @Override
    public HashMap<String, Category> read() {
        return this;
    }

    @Override
    public Category details(String id) {
        return this.get(id);
    }

    @Override
    public int update(Category editItem) {
        try {
            Category oldItem = this.get(editItem.getId());
            oldItem = editItem;
            return 1;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }}

    @Override
    public int delete(String id) {
          try {
            this.remove(id);
            return 1;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }

    }
    
}
    

