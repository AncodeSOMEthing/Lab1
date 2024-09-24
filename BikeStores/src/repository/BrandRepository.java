/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import java.util.HashMap;
import model.Brand;

/**
 *
 * @author se180663
 */
public class BrandRepository extends HashMap<String, Brand> implements ICrud<String, Brand> {
    
    @Override
    public int create(Brand newItem) {
        try {
            this.put(newItem.getId(), newItem);
            return 1;//insert ok
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return 0;// Insert err
        }

    }
//    public void docFileNha(){
//        RandW rw 
//    }
    @Override
    public HashMap<String, Brand> read() {
        return this;
    }

    @Override
    public Brand details(String id) {
        return this.get(id);
    }

    @Override
    public int update(Brand editItem) {
        try {
            Brand oldItem = this.get(editItem.getId());
            oldItem.setName(editItem.getName());
            oldItem.setCountry(editItem.getCountry());

            return 1;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }

    }

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

//    public void readDataFromFile(String _Brandtxt) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
}
