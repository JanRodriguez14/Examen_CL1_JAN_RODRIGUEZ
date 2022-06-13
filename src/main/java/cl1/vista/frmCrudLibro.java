package cl1.vista;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.protocol.x.XProtocolError;

import cl1.hibernate.dao.LibroDao;
import cl1.hibernate.dao.TemaDao;
import cl1.hibernate.model.Libro;
import cl1.hibernate.model.Tema;

public class frmCrudLibro extends JFrame implements ActionListener, MouseListener{

	//Declarar los controles para el CRUD
	private JPanel contentPane;
	private JTextField txtTitulo;
	private JTextField txtPrecio;
	private JTextField txtCantidad;
	private JTextField txtOrigen;
	private JButton btnRegistrar;
	private JButton btnActualizar;
	private JButton btnEliminar;
	private JTable table;
	private JComboBox<Tema> cboTema;
	List<Tema> temas = null;
	
	//Declarar e instanciar el objeto DAO Libro
	LibroDao daoLibro = new LibroDao();
	
	//El id del Libro que se selecciona en el JTable
	int idSeleccionado = -1;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frmCrudLibro frame = new frmCrudLibro();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public frmCrudLibro() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 460);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JLabel lblMttoLibro = new JLabel("Mantenimiento de Libros");
		lblMttoLibro.setHorizontalAlignment(SwingConstants.CENTER);
		lblMttoLibro.setForeground(Color.RED);
		lblMttoLibro.setFont(new Font("Tahoma", Font.BOLD, 19));
		lblMttoLibro.setBounds(10, 11, 414, 35);
		contentPane.add(lblMttoLibro);

		JLabel lblTitulo = new JLabel("Titulo");
		lblTitulo.setBounds(40, 57, 84, 26);
		contentPane.add(lblTitulo);

		JLabel lblPrecio = new JLabel("Precio");
		lblPrecio.setBounds(40, 88, 84, 26);
		contentPane.add(lblPrecio);

		JLabel lblCantidad = new JLabel("Cantidad");
		lblCantidad.setBounds(40, 119, 84, 26);
		contentPane.add(lblCantidad);
		
		JLabel lblOrigen = new JLabel("Origen");
		lblOrigen.setBounds(40, 150, 84, 26);
		contentPane.add(lblOrigen);
		
		JLabel lblTema = new JLabel("Tema");
		lblTema.setBounds(40, 181, 84, 26);
		contentPane.add(lblTema);

		//Atributos

		txtTitulo = new JTextField();
		txtTitulo.setBounds(152, 60,211, 20);
		contentPane.add(txtTitulo);
		txtTitulo.setColumns(10);

		txtPrecio = new JTextField();
		txtPrecio.setBounds(152, 91,211, 20);
		contentPane.add(txtPrecio);
		txtPrecio.setColumns(10);

		txtCantidad = new JTextField();
		txtCantidad.setBounds(152, 122,211, 20);
		contentPane.add(txtCantidad);
		txtCantidad.setColumns(10);
		
		txtOrigen = new JTextField();
		txtOrigen.setBounds(152, 153,211, 20);
		contentPane.add(txtOrigen);
		txtOrigen.setColumns(10);
		
		//Agregando combo de carrera
		cboTema = new JComboBox();
		cboTema.setBounds(152, 181, 211, 20);
		contentPane.add(cboTema);

		//Botones

		btnRegistrar = new JButton("Registrar");
		btnRegistrar.addActionListener(this);
		btnRegistrar.setBounds(10, 212, 114, 23);
		contentPane.add(btnRegistrar);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 243, 424, 184);
		contentPane.add(scrollPane);
		
		//Configurando el JTable
		table = new JTable();
		table.addMouseListener(this);
		table.setModel(new DefaultTableModel(
				new Object[][] {
					
				},
				new String [] {
						"Id", "Titulo", "Precio", "CantEjemplares", "Origen", "Tema"
				}
				));
		scrollPane.setViewportView(table);
		
		//Llamar al metodo que carga el combo temas
		cargaComboTema();
		
		btnActualizar = new JButton("Actualizar");
		btnActualizar.addActionListener(this);
		btnActualizar.setBounds(310, 212, 114, 23);
		contentPane.add(btnActualizar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(this);
		btnEliminar.setBounds(159, 212, 114, 23);
		contentPane.add(btnEliminar);
		
		//Llamar al metodo que liste libros
		listaLibros();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==btnRegistrar) {
			//Llamar al metodo para registrar el libro
			do_btnRegistrar_actionPerformed(e);
		}
		
		if(e.getSource()==btnActualizar) {
			//Llamar al metodo para registrar el libro
			do_btnActualizar_actionPerformed(e);
		}
		
		if(e.getSource()==btnEliminar) {
			//Llamar al metodo para registrar el libro
			do_btnEliminar_actionPerformed(e);
		}
	}
	
	protected void do_btnRegistrar_actionPerformed(ActionEvent e) {
		//Definir variables para leer los datos de los controles del GUI
		int cantidad = Integer.parseInt(txtCantidad.getText());
		String origen = txtOrigen.getText().trim();
		Double precio = Double.parseDouble(txtPrecio.getText());
		String titulo = txtTitulo.getText().trim();
		Tema tema = (Tema)cboTema.getSelectedItem();
		
		
		Libro libro = new Libro();
		libro.setTitulo(titulo);
		libro.setPrecio(precio);
		libro.setCantEjemplares(cantidad);
		libro.setOrigen(origen);
		libro.setTema(tema);

		//Llamar a la capa DAO
		daoLibro.guardarLibro(libro);
		System.out.println("Registro OK");
		
		//Llamar al metodo de lista de libros
		listaLibros();
		
		//Llamar al metodo de limpiar botones
		limpiarControles();
	}
	
	protected void do_btnActualizar_actionPerformed(ActionEvent e) {
		//Definir variables para leer los datos de los controles del GUI
		int cantidad = Integer.parseInt(txtCantidad.getText());
		String origen = txtOrigen.getText().trim();
		Double precio = Double.parseDouble(txtPrecio.getText());
		String titulo = txtTitulo.getText().trim();
		Tema tema = (Tema)cboTema.getSelectedItem();		
				
				
		Libro libro = new Libro();
		libro.setIdlibro(idSeleccionado);
		libro.setTitulo(titulo);
		libro.setPrecio(precio);
		libro.setCantEjemplares(cantidad);
		libro.setOrigen(origen);
		libro.setTema(tema);

		//Llamar a la capa DAO
		daoLibro.actualizarLibro(libro);
		System.out.println("Actualizar OK");
				
		//Llamar al metodo de lista de libros
		listaLibros();
				
		//Llamar al metodo de limpiar botones
		limpiarControles();
	}
	
	protected void do_btnEliminar_actionPerformed(ActionEvent e) {
		daoLibro.eliminarLibro(idSeleccionado);
		listaLibros();
		limpiarControles();
	}
	
	//Muestra todos los registros de los estudiantes de la BD en el JTable
	private void listaLibros() {
		//1.Traemos la data de la BD
		List<Libro> data = daoLibro.obtTodosLibros();
		//2.Limpiar el JTable
		DefaultTableModel dtm = (DefaultTableModel)table.getModel();
		dtm.setRowCount(0);
		//3.Agregamos los objetos libros al dtm
		for(Libro e: data) {
			Object[] fila = {
					e.getIdlibro(),
					e.getTitulo(),
					e.getPrecio(),
					e.getCantEjemplares(),
					e.getOrigen(), 
					e.getTema().getNombre()
			};
			dtm.addRow(fila);
		}
		//4.Actualizamos el dtm al JTable
		dtm.fireTableDataChanged();
	}
	
	private void limpiarControles() {
		txtTitulo.setText("");
		txtPrecio.setText("");
		txtCantidad.setText("");
		txtOrigen.setText("");
		txtTitulo.requestFocus();
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==table) {
			//Llamar al metodo para registrar el libro
			do_table_mouseClicked(e);
		}
	}
	
	protected void  do_table_mouseClicked(MouseEvent e) {
		int fila = table.getSelectedRow();
		DefaultTableModel dtm = (DefaultTableModel)table.getModel();
		idSeleccionado = (int) dtm.getValueAt(fila, 0);
		String tit = (String) dtm.getValueAt(fila, 1);
		Double pre = (Double) dtm.getValueAt(fila, 2);
		Integer can = (int) dtm.getValueAt(fila, 3);
		String ori = (String) dtm.getValueAt(fila, 4);
		String tem = (String) dtm.getValueAt(fila, 5);
		
		txtTitulo.setText(tit);
		txtPrecio.setText(pre+"");
		txtCantidad.setText(can+"");
		txtOrigen.setText(ori);
		cboTema.setSelectedItem(obtTemaDeCombo(tem)); //Llamar al metodo para obtener el item
		
	}

	private Tema obtTemaDeCombo(String s) {
		Tema tema = null;
		for(Tema tem: temas) {
			if(tem.getNombre().equals(s)) {
				tema = tem;
			}
		}
		return tema;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private void cargaComboTema() {
		Tema c1 = new Tema("Poesia");
		Tema c2 = new Tema("Cuento");
		
		TemaDao temaDao = new TemaDao();
		
		temaDao.guardarTema(c1);
		temaDao.guardarTema(c2);
		
		temas = temaDao.obtTodosTemas();
		
		for(Tema c: temas) {
			cboTema.addItem(c);
		}
	}

}
