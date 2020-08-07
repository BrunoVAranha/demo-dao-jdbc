package model.impl;

import DBException.DBException;
import db.DB;
import model.Instantiate;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDao {

    private Connection conn = null;

    public DepartmentDaoJDBC(Connection conn){
        this.conn = conn;
    }

    @Override
    public void insert(Department dep) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("INSERT INTO department "
                    + "(Name) "
                    + "VALUES "
                    + "(?)");

            st.setString(1,dep.getName());


        }
        catch(SQLException e){
            throw new DBException(e.getMessage());
        }

    }

    @Override
    public void update(Department dep) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement(
                    "UPDATE department "
                            + "SET Name = ?");

            st.setString(1, dep.getName());
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

        try{
            st = conn.prepareStatement("DELETE FROM department WHERE Id = ?");
            st.setInt(1,id);

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
    public Department findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try{

            st = conn.prepareStatement("SELECT * FROM department WHERE Id = ?");

            st.setInt(1,id);

            if(rs.next()){
                Department dep = Instantiate.instantiateDepartment(rs);
                return dep;
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
    public List<Department> findAll() {
        return null;
    }
}
