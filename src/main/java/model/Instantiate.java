package model;

import DBException.DBException;
import model.entities.Department;
import model.entities.Seller;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Instantiate {

    public static Department instantiateDepartment(ResultSet rs){
        try{
            return new Department( rs.getInt("DepartmentId"),
                    rs.getString("DepName"));
        }
        catch(SQLException e){
            throw new DBException(e.getMessage());
        }
    }

    public static Seller instantiateSeller(ResultSet rs, Department dep){

        try{
            return new Seller( rs.getInt("Id"),
                    (rs.getString("Name")),
                    (rs.getString("Email")),
                    (rs.getDate("BirthDate")),
                    (rs.getDouble("BaseSalary")),
                    dep);
        }

        catch(SQLException e){
            throw new DBException(e.getMessage());
        }
    }
}
