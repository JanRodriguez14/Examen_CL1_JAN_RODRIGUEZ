package cl1.hibernate.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import cl1.hibernate.model.Tema;
import cl1.hibernate.util.HibernateUtil;

public class TemaDao {

	public TemaDao() {
		// TODO Auto-generated constructor stub
	}

	
	//Guardar un tema
	public void guardarTema(Tema tema) {
		Transaction transaction = null;
		try(Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.save(tema);
			transaction.commit();
			
		} catch (Exception e) {
			// TODO: handle exception
			
			if(transaction!=null) {
				transaction.rollback();
			}
		}
	}
	
	//Obtener todos los temas
	public List<Tema> obtTodosTemas() {
		Transaction transaction = null;
		List<Tema> listaTemas = null;
		
		try(Session session = HibernateUtil.getSessionFactory().openSession()) {
			//Iniciar la transaccion
			transaction = session.beginTransaction();
			
			//Obtener la lista de temas
			listaTemas = session.createQuery("from Tema").list();
			transaction.commit();
				
		} catch (Exception e) {
			// TODO: handle exception
				
			if(transaction!=null) {
				transaction.rollback();
			}
		}
		
		return listaTemas;
	}
}
