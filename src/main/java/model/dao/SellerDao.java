package model.dao;


import model.entities.Department;
import model.entities.Seller;

import java.util.List;

public interface SellerDao {

    void insert(Seller dep);

    void update(Seller dep);

    void deleteById(Integer id);

    Seller findById(Integer Id);

    List<Seller> findByDepartment(Department dep);

    List<Seller> findAll();
}