/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package appagenda;
 
import entidades.Provincia;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
 
/**
 *
 * @author jose
 */
// Clase creada para comprobar el contenido de la base de datos desde Java
public class ConsultaProvincias {
 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("AppAgendaPU");
        EntityManager em = emf.createEntityManager();
 
        // Lista completa de las provincias
        Query queryProvincias = em.createNamedQuery("Provincia.findAll");
        // Se almacenan los datos de la consulta anterior
        List<Provincia> listProvincias = queryProvincias.getResultList();
        // Consultamos el dato del nombre de las provincias (for each)
        for (Provincia provincia : listProvincias) {
            System.out.println(provincia.getNombre());
        }
 
        // Obtener la provincia con el nombre Cádiz
        Query queryProvinciaCadiz = em.createNamedQuery("Provincia.findByNombre");
        queryProvinciaCadiz.setParameter("nombre", "Cádiz");
        List<Provincia> listProvinciasCadiz = queryProvinciaCadiz.getResultList();
        for (Provincia provinciaCadiz : listProvinciasCadiz) {
            System.out.println(provinciaCadiz.getId() + ":");
            System.out.println(provinciaCadiz.getNombre());
        }
 
        // Obtener la provincia con ID 2
        Provincia provinciaId2 = em.find(Provincia.class, 2);
        if (provinciaId2 != null) {
            System.out.print(provinciaId2.getId() + ":");
            System.out.println(provinciaId2.getNombre());
        } else {
            System.out.println("No hay ninguna provincia con ID=2");
        }
 
        // Añadir CA al código de pronvincia que sea Cádiz
        em.getTransaction().begin();
        for (Provincia provinciaCadiz : listProvinciasCadiz) {
            provinciaCadiz.setCodigo("CA");
            em.merge(provinciaCadiz);
        }
        em.getTransaction().commit();
 
        // Eliminar provincia con ID 15
        Provincia provinciaId15 = em.find(Provincia.class, 15);
        em.getTransaction().begin();
        if (provinciaId15 != null) {
            em.remove(provinciaId15);
        } else {
            System.out.println("No hay ninguna provincia con ID=15");
        }
        em.getTransaction().commit();
 
    }
 
}