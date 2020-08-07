package model.impl;

import DBException.DBException;
import db.DB;
import model.Instantiate;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import javax.naming.ldap.PagedResultsResponseControl;
import javax.xml.transform.Result;
import java.awt.event.PaintEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn = null;

    public SellerDaoJDBC(Connection conn){
        this.conn = conn;
    }

    @Override
    public void insert(Seller sell) {
        PreparedStatement st = null;

        try{
            st = conn.prepareStatement("INSERT INTO seller "
                    + "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
                    + "VALUES "
                    + "(?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1, sell.getName());
            st.setString(2, sell.getEmail());
            st.setDate(3, new java.sql.Date(sell.getBirthDate().getTime()));
            st.setDouble(4, sell.getBaseSalary());
            st.setInt(5, sell.getDepartment().getId());

            int rowsAffected = st.executeUpdate();

            if(rowsAffected > 0){
                ResultSet rs = st.getGeneratedKeys();
                if(rs.next()){
                    int id = rs.getInt(1);
                    sell.setId(id);
                }
                DB.closeResultSet(rs);
            }
            else{
                throw new DBException("Error inserting seller!");
            }
        }
        catch(SQLException e){
            throw new DBException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Seller sell) {

        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                    "UPDATE seller "
                            + "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
                            + "WHERE Id = ?");

            st.setString(1, sell.getName());
            st.setString(2, sell.getEmail());
            st.setDate(3, new java.sql.Date(sell.getBirthDate().getTime()));
            st.setDouble(4, sell.getBaseSalary());
            st.setInt(5, sell.getDepartment().getId());
            st.setInt(6, sell.getId());
        }
        catch(SQLException e){
            throw new DBException(e.getMessage());
        }
        finally{
            DB.closeStatement(st);
        }

    }

    @Override
    public void deleteById(Integer id) {

        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("DELETE FROM seller WHERE Id = ?");
            st.setInt(1, id);

            st.executeUpdate();
        }
        catch(SQLException e){
            throw new DBException(e.getMessage());
        }
        finally{
            DB.closeStatement(st);
        }
    }

    @Override
    public Seller findById(Integer Id) {
        ResultSet rs = null;
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement("SELECT seller.*, department.Name as DepName"
            + " FROM seller INNER JOIN department "
            + " ON seller.DepartmentId = department.Id"
            + " WHERE seller.Id = ?");

            st.setInt(1, Id);
            rs = st.executeQuery();
            if(rs.next()){
                Department dep = Instantiate.instantiateDepartment(rs);
                Seller sell = Instantiate.instantiateSeller(rs, dep);

                return sell;
            }
            return null;
        }
        catch(SQLException e){
            throw new DBException(e.getMessage());
        }
        finally{
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }




    @Override
    public List<Seller> findAll() {

        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("SELECT seller.*,department.Name as DepName "
                    + "FROM seller INNER JOIN department "
                    + "ON seller.DepartmentId = department.Id "
                    + "ORDER BY Name");

            rs = st.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while(rs.next()){
                Department dep = map.get(rs.getInt("DepartmentId"));

                if(dep ==null){
                    dep = Instantiate.instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }

                Seller sell = Instantiate.instantiateSeller(rs, dep);
                list.add(sell);

            }
            return list;
        }
        catch(SQLException e){
            throw new DBException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Seller> findByDepartment(Department depart) {
        ResultSet rs = null;
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("SELECT seller.*, department.Name as DepName"
                    + " FROM seller INNER JOIN department"
                    + " ON seller.DepartmentId = department.Id"
                    + " WHERE DepartmentId = ?"
                    + " ORDER BY Name"
            );
            st.setInt(1, depart.getId());

            rs = st.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();
            while(rs.next()){
                Department dep = map.get(rs.getInt("DepartmentId"));
                if(dep == null){
                    dep = Instantiate.instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }


                Seller seller = Instantiate.instantiateSeller(rs, dep);
                list.add(seller);

            }
            return list;
        }
        catch(SQLException e){
            throw new DBException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}
