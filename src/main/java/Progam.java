import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;

public class Progam {

    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();
        DepartmentDao depDao = DaoFactory.createDepartmentDao();

        Seller seller = sellerDao.findById(3);
        System.out.println(seller);


//        Department depart = new Department(2, null);
//        List<Seller> list = sellerDao.findByDepartment(depart);
//
//        for (Seller sell : list){
//            System.out.println(sell);
//        }

//

        //Seller sell = new Seller(null, "Greg", "greg@gmail.com", new Date(), 2000.00, new Department(4, null));
        //sellerDao.insert(sell);


        sellerDao.deleteById(9);
        sellerDao.deleteById(9);
        List<Seller> listAll = sellerDao.findAll();

        for (Seller selle : listAll){
            System.out.println(selle);
        }


    }
}
