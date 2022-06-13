package cl1.hibernate;

import java.util.List;

import cl1.hibernate.dao.LibroDao;
import cl1.hibernate.dao.TemaDao;
import cl1.hibernate.model.Libro;
import cl1.hibernate.model.Tema;

public class TestCL1 {

	public TestCL1() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		TemaDao dao2 = new TemaDao();
		Tema tem1 = new Tema("Lectura");
		Tema tem2 = new Tema("Fabula");
		Tema tem3 = new Tema("Historia");
		
		
		dao2.guardarTema(tem1);
		dao2.guardarTema(tem2);
		dao2.guardarTema(tem3);
		
		//Llamar al metodo para obtener todos los temas
		List<Tema> listarTemas = dao2.obtTodosTemas();
		listarTemas.forEach(tem->System.out.println(tem.getIdtema()+"-"+tem.getNombre()));
		
		
		LibroDao dao = new LibroDao();
		Libro lib1 = new Libro("La Piedra Inca", 23.50, 10, "Lima");
		Libro lib2 = new Libro("La Biblia de los Caidos", 40.90, 18, "Lima");
		Libro lib3 = new Libro("Trilce", 27.90, 7, "Lima");
		
		dao.guardarLibro(lib1);
		dao.guardarLibro(lib2);
		dao.guardarLibro(lib3);
		
		//Llamar al metodo para obtener todos los libros
		List<Libro> listarLibros = dao.obtTodosLibros();
		listarLibros.forEach(lib->System.out.println(lib.getIdlibro()+"-"+lib.getTitulo()));

		
		//Modificar un objeto estudiante
		lib1.setTitulo("Los Heraldos Negros");
		dao.actualizarLibro(lib1);
		
		lib2.setTitulo("Fabla Salvaje");
		dao.actualizarLibro(lib2);
		
		lib3.setTitulo("Paco Yunque");
		dao.actualizarLibro(lib3);
		
		//Llamar al metodo para obtener todos los libros
		listarLibros = dao.obtTodosLibros();
		listarLibros.forEach(lib->System.out.println(lib.getIdlibro()+"-"+lib.getTitulo()));
		
		//Eliminar un libro
		dao.eliminarLibro(lib2.getIdlibro());
		
		//Llamar al metodo para obtener todos los libros
		listarLibros = dao.obtTodosLibros();
		listarLibros.forEach(lib->System.out.println(lib.getIdlibro()+"-"+lib.getTitulo()));
		
		

	}

}
