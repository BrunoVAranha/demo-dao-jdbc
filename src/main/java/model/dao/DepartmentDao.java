package model.dao;

import model.entities.Department;

import java.util.List;

public interface DepartmentDao {

    void insert(Department dep);

    void update(Department dep);

    void deleteById(Integer id);

    Department findById(Integer Id);

    List<Department> findAll();
}
