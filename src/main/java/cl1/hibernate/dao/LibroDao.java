package cl1.hibernate.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import cl1.hibernate.model.Libro;
import cl1.hibernate.util.HibernateUtil;

public class LibroDao {

	public LibroDao() {
		// TODO Auto-generated constructor stub
		
		
	}
	
	//Guardar un libro
	public void guardarLibro(Libro libro) {
		Transaction transaction = null;
		
		try(Session session = HibernateUtil.getSessionFactory().openSession()) {
			//Iniciar la transaccion
			transaction = session.beginTransaction();
			session.save(libro);
			transaction.commit();
			
		} catch (Exception e) {
			// TODO: handle exception
			
			if(transaction!=null) {
				transaction.rollback();
			}
		}
	}
	
	
	//Obtener todos los estudiantes
	public List<Libro> obtTodosLibros(){
		Transaction transaction = null;
		List<Libro> listaLibros = null;
		
		try(Session session = HibernateUtil.getSessionFactory().openSession()) {
			//Iniciar la transaccion
			transaction = session.beginTransaction();

			//Obtener la lista de estudiantes
			listaLibros = session.createQuery("from Libro").list();
			transaction.commit();
			
		} catch (Exception e) {
			// TODO: handle exception
			
			if(transaction!=null) {
				transaction.rollback();
			}
		}
		
		return listaLibros;
	}
	
	
	//Obtener un libro por id
	public Libro obtLibroPorId(int id) {
		Transaction transaction = null;
		Libro libro = null;
				
		try(Session session = HibernateUtil.getSessionFactory().openSession()) {
			//Iniciar la transaccion
			transaction = session.beginTransaction();
				
			//Obtener el objeto Libro a partir del id
			libro = session.get(Libro.class, id);
			transaction.commit();
					
		} catch (Exception e) {
			// TODO: handle exception
					
			if(transaction!=null) {
				transaction.rollback();
			}
		}
		
		return libro;
	}
	
	
	//Actualizar un libro
	public void actualizarLibro(Libro libro) {
		Transaction transaction = null;
			
		try(Session session = HibernateUtil.getSessionFactory().openSession()) {
			//Iniciar la transaccion
			transaction = session.beginTransaction();
			session.saveOrUpdate(libro);
			transaction.commit();
				
		} catch (Exception e) {
			// TODO: handle exception
				
			if(transaction!=null) {
				transaction.rollback();
			}
		}
	}
	
	
	//Eliminar un libro
	public void eliminarLibro(int id) {
		Transaction transaction = null;
		Libro libro = null;
			
		try(Session session = HibernateUtil.getSessionFactory().openSession()) {
			//Iniciar la transaccion
			transaction = session.beginTransaction();
			
			//Obtener el objeto Libro a partir del id
			libro = session.get(Libro.class, id);
			session.delete(libro);
			transaction.commit();
				
		} catch (Exception e) {
			// TODO: handle exception
				
			if(transaction!=null) {
				transaction.rollback();
			}
		}
	}

}
