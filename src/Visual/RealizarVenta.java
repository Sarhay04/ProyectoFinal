package Visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import logico.Cliente;
import logico.Componente;
import logico.Tienda;
import logico.Venta;
import logico.VentaComponente;

import javax.swing.border.EtchedBorder;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Color;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class RealizarVenta extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField txtNombre;
	private JTextField txtTelefono;
	private static JTable table;
	private static JTextField txtTotal;
	private static JTextField txtProductos;
	private JTextField txtFecha;
	private JTextField txtCodigo;
	private Cliente clt;
	private JFormattedTextField txtCedula;
	private LocalDate fecha = LocalDate.now();
	static ArrayList<VentaComponente> misVentas= new ArrayList<VentaComponente>();
	private JButton btnEliminar;
	private Componente comp;
	private JTextField txtNserie;
	private JSpinner spnCantidad;
	private JButton btnActualizar;
	private int index;
	private static double total = 0;
	private static int prodt = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RealizarVenta dialog = new RealizarVenta();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public RealizarVenta() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				misVentas.clear();
			}
		});
		setTitle("Ventas");
		setBounds(100, 100, 828, 651);
		getContentPane().setLayout(null);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			buttonPane.setBounds(0, 575, 814, 39);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane);
			{
				JButton okButton = new JButton("Realizar");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(!misVentas.isEmpty() && clt != null)
						{
							Venta vta = new Venta("VENTA-"+Venta.codigoVenta, misVentas, clt, total, fecha);
							Tienda.getInstance().agregarVenta(vta);
							clearAll();
							JOptionPane.showMessageDialog(null, "La venta ha sido realizada!", "",
									JOptionPane.INFORMATION_MESSAGE);
						}else {
							JOptionPane.showMessageDialog(null, "Debe llenar todos los campos!", "Error",
									JOptionPane.ERROR_MESSAGE);
						}
						
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cerrar");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Cliente", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 10, 795, 120);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		txtCedula = new JFormattedTextField((createFormatter("###-#######-#")));
		txtCedula.setBounds(82, 23, 160, 20);
		panel.add(txtCedula);
		
		JLabel lblNewLabel = new JLabel("Cedula:");
		lblNewLabel.setBounds(13, 26, 59, 13);
		panel.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Buscar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					clt = Tienda.getInstance().getClienteByCedula(txtCedula.getText());	
					if(clt != null)
					{
						cargarCliente();
						JOptionPane.showMessageDialog(null, "El Cliente ha sido encontrado!", "",
								JOptionPane.INFORMATION_MESSAGE);
					}else {
						limpiarCliente();
						JOptionPane.showMessageDialog(null, "El Cliente no ha sido encontrado!", "Registro",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
		});
		btnNewButton.setBounds(268, 22, 230, 21);
		panel.add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("Nombre:");
		lblNewLabel_1.setBounds(13, 71, 59, 13);
		panel.add(lblNewLabel_1);
		
		txtNombre = new JTextField();
		txtNombre.setEditable(false);
		txtNombre.setBounds(82, 68, 160, 19);
		panel.add(txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("Telefono:");
		lblNewLabel_1_1.setBounds(268, 71, 59, 13);
		panel.add(lblNewLabel_1_1);
		
		txtTelefono = new JTextField();
		txtTelefono.setEditable(false);
		txtTelefono.setColumns(10);
		txtTelefono.setBounds(338, 68, 160, 19);
		panel.add(txtTelefono);
		
		JButton btnNewButton_1 = new JButton("Agregar Cliente");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegCliente regclt = new RegCliente(null);
        		regclt.setModal(true);
        		regclt.setLocationRelativeTo(null);
        		regclt.setVisible(true);
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton_1.setBounds(544, 23, 230, 64);
		panel.add(btnNewButton_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Productos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 140, 795, 414);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 21, 563, 383);
		panel_1.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int aindex = table.getSelectedRow();
                if (aindex >= 0) {
                	index = aindex;
                    btnEliminar.setEnabled(true);
                    btnActualizar.setEnabled(true);
                    spnCantidad.setValue(table.getValueAt(index, 4));
                    String numeroDeSerie = table.getValueAt(index, 0).toString();
                    comp = Tienda.getInstance().buscarComponente(numeroDeSerie);
                    txtNserie.setText(comp.getNumeroDeSerie());
                }else {
                	btnEliminar.setEnabled(false);
                	btnActualizar.setEnabled(false);
                	comp = null;
                }
			}
		});
		scrollPane.setViewportView(table);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_2.setBounds(583, 21, 202, 383);
		panel_1.add(panel_2);
		panel_2.setLayout(null);
		
		JButton btnAñadir = new JButton("Añadir");
		btnAñadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListarComponentes lC = new ListarComponentes(true);
				lC.setModal(true);
				lC.setLocationRelativeTo(null);
				lC.setVisible(true);
			}
		});
		btnAñadir.setBounds(10, 352, 182, 21);
		panel_2.add(btnAñadir);
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(null,
                        "¿Estás seguro(a) que desea eliminar el Producto del carrito?",
                        "Confirmación", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                  VentaComponente elim = null;
               	  for(VentaComponente aux: misVentas)
               	  {
               		  if(aux.getComp().equals(comp))
               		  {
               			 elim = aux;
               		  }
               	  }
               	  misVentas.remove(elim);
               	  cargarProductos();
                  actualizarInfo();
					JOptionPane.showMessageDialog(null, "El Producto ha sido eliminado!", "Eliminacion",
							JOptionPane.INFORMATION_MESSAGE);
					btnEliminar.setEnabled(false);
					btnActualizar.setEnabled(false);
					txtNserie.setText("");
					comp = null;
                }
			}
		});
		btnEliminar.setEnabled(false);
		btnEliminar.setBounds(10, 321, 182, 21);
		panel_2.add(btnEliminar);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Informacion General", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(10, 10, 182, 168);
		panel_2.add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblNewLabel_2_1 = new JLabel("Codigo:");
		lblNewLabel_2_1.setBounds(10, 29, 62, 13);
		panel_3.add(lblNewLabel_2_1);
		
		txtCodigo = new JTextField();
		txtCodigo.setBounds(76, 26, 96, 19);
		panel_3.add(txtCodigo);
		txtCodigo.setEditable(false);
		txtCodigo.setColumns(10);
		txtCodigo.setText("VENTA-"+Venta.codigoVenta);
		
		JLabel lblNewLabel_2 = new JLabel("Total:");
		lblNewLabel_2.setBounds(10, 64, 62, 13);
		panel_3.add(lblNewLabel_2);
		
		txtTotal = new JTextField();
		txtTotal.setBounds(76, 61, 96, 19);
		panel_3.add(txtTotal);
		txtTotal.setEditable(false);
		txtTotal.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Productos:");
		lblNewLabel_3.setBounds(10, 99, 62, 13);
		panel_3.add(lblNewLabel_3);
		
		txtProductos = new JTextField();
		txtProductos.setBounds(76, 96, 96, 19);
		panel_3.add(txtProductos);
		txtProductos.setEditable(false);
		txtProductos.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Fecha:");
		lblNewLabel_4.setBounds(10, 135, 62, 13);
		panel_3.add(lblNewLabel_4);
		
		txtFecha = new JTextField();
		txtFecha.setBounds(76, 132, 96, 19);
		panel_3.add(txtFecha);
		txtFecha.setEditable(false);
		txtFecha.setColumns(10);
		txtFecha.setText(fecha.format(formatter));
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Producto Seleccionado", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_4.setBounds(10, 188, 182, 123);
		panel_2.add(panel_4);
		panel_4.setLayout(null);
		
		JLabel lblNewLabel_5 = new JLabel("N-Serie:");
		lblNewLabel_5.setBounds(10, 29, 75, 13);
		panel_4.add(lblNewLabel_5);
		
		JLabel lblNewLabel_5_1 = new JLabel("Cantidad:");
		lblNewLabel_5_1.setBounds(10, 59, 75, 13);
		panel_4.add(lblNewLabel_5_1);
		
		txtNserie = new JTextField();
		txtNserie.setEditable(false);
		txtNserie.setBounds(76, 26, 96, 19);
		panel_4.add(txtNserie);
		txtNserie.setColumns(10);
		
		spnCantidad = new JSpinner();
		spnCantidad.setModel(new SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));
		spnCantidad.setBounds(76, 55, 96, 20);
		panel_4.add(spnCantidad);
		
		btnActualizar = new JButton("Actualizar");
		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if((Integer) spnCantidad.getValue() > comp.getCantidadDisponible())
				{
					JOptionPane.showMessageDialog(null, "La Cantidad ingresada es mayor a la del inventario!", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				VentaComponente vent = new VentaComponente(comp,(Integer) spnCantidad.getValue());
				misVentas.set(index,vent);
				comp = null;
				txtNserie.setText("");
				btnEliminar.setEnabled(false);
				btnActualizar.setEnabled(false);
				cargarProductos();
				actualizarInfo();
				JOptionPane.showMessageDialog(null, "El Carrito ha sido Actualizado!", "Actualizacion",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnActualizar.setEnabled(false);
		btnActualizar.setBounds(10, 92, 162, 21);
		panel_4.add(btnActualizar);
		cargarProductos();
		actualizarInfo();
	}
	
	protected MaskFormatter createFormatter(String s) {
		MaskFormatter formatter = null;
		try {
			formatter = new MaskFormatter(s);
		} catch (java.text.ParseException exc) {
			System.err.println("formatter is bad: " + exc.getMessage());
			System.exit(-1);
		}
		return formatter;
	}
	
	public void cargarCliente()
	{
		txtNombre.setText(clt.getNombre());
		txtTelefono.setText(clt.getTelefono());
	}
	
	private static void cargarProductos() {

		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Nuumero De Serie");
		model.addColumn("Marca");
		model.addColumn("Modelo");
		model.addColumn("Precio");
		model.addColumn("Cantidad");

		for (VentaComponente vc : misVentas) {
			model.addRow(new Object[] { vc.getComp().getNumeroDeSerie(),vc.getComp().getMarca(),
					vc.getComp().getModelo(),vc.getComp().getPrecio(),vc.getQty()});
		}
		table.setModel(model);
	}
	
	public void limpiarCliente()
	{
		txtNombre.setText("");
		txtTelefono.setText("");
		txtCedula.setText("");
	}
	
	public static void getProducto(Componente comp) {
		
		for (VentaComponente vc : misVentas) {
			if(vc.getComp().equals(comp)) {
				JOptionPane.showMessageDialog(null, "El Componente ya ha sido agregado!", "",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		}
		
		VentaComponente vC = new VentaComponente(comp,1);
		misVentas.add(vC);
		cargarProductos();
		actualizarInfo();
		JOptionPane.showMessageDialog(null, "El Componente ha sido agregado!", "",
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void actualizarInfo()
	{
		total = 0;
		prodt = 0;
		for(VentaComponente aux: misVentas)
		{
			total += aux.getComp().getPrecio() * aux.getQty();
			prodt++;
		}
		
		txtTotal.setText(String.valueOf(total));
		txtProductos.setText(String.valueOf(prodt));
	}
	
	public void clearAll()
	{
		limpiarCliente();
		clt = null;
		comp = null;
		misVentas.clear();
		cargarProductos();
		actualizarInfo();
		txtCodigo.setText("VENTA-"+Venta.codigoVenta);
		txtNserie.setText("");
		btnActualizar.setEnabled(false);
		btnEliminar.setEnabled(false);
		
	}
}
