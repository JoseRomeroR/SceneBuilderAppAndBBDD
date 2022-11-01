package appagenda;

import entidades.Provincia;
import javax.persistence.EntityManager;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;


public class AppAgenda {
    

    public static void main(String[] args) {
        
      EntityManagerFactory emf=
       Persistence.createEntityManagerFactory("AppAgendaPU");
     EntityManager em=emf.createEntityManager();

      Provincia provinciaCadiz = new Provincia();
      provinciaCadiz.setNombre("Cadiz");
        
       Provincia provinciaSevilla = new Provincia();
       provinciaSevilla.setNombre("Sevilla");
       
        
       em.getTransaction().begin(); // Inicia transaccion , se puede utilizar operaciones con la base de datos insertar , modificar o borrar
        
       em.persist(provinciaCadiz);
       em.persist(provinciaSevilla);

      em.getTransaction().commit(); // cunado se realizen las moficiaciones se hace un commit para conirmar 
        
      //em.getTransaction().rollback(); // si peta , vuelve 
      
     


        
        em.close();
        emf.close();
        try{
            DriverManager.getConnection("jdbc:derby:BDAgenda;shutdown=true");
        } catch (SQLException ex){
        }
        

    }
    
}
