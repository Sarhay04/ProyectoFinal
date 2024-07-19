package Visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import logico.Cliente;
import logico.Tienda;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ListarCliente extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private JButton btnModificar;
	private Cliente clt = null;
	private JButton btnEliminar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ListarCliente dialog = new ListarCliente();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ListarCliente() {
		setTitle("Clientes");
		setBounds(100, 100, 679, 591);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(null), "Listado de Clientes", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 30, 645, 473);
		contentPanel.add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 24, 625, 439);
		panel.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = table.getSelectedRow();
				if (index >= 0) {
					btnModificar.setEnabled(true);
					btnEliminar.setEnabled(true);
					clt = Tienda.getInstance().getClienteByCedula(table.getValueAt(index, 0).toString());
				}

			}
		});
		scrollPane.setViewportView(table);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			
			btnModificar = new JButton("Modificar");
			btnModificar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					RegCliente modificar = new RegCliente(clt);
					modificar.setModal(true);
					modificar.setVisible(true);
					btnEliminar.setEnabled(false);
					btnModificar.setEnabled(false);
					cargarClientes();
				}
			});
			
			btnEliminar = new JButton("Eliminar");
			btnEliminar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(clt != null && Tienda.getInstance().checkClienteDelete(clt))
					{
						int option = JOptionPane.showConfirmDialog(null,
	                             "¿Estás seguro(a) que desea eliminar el Cliente de cedula: "
	                                     + clt.getCedula() + "?",
	                             "Confirmación", JOptionPane.OK_CANCEL_OPTION);
	                     if (option == JOptionPane.OK_OPTION) {
	                    	 Tienda.getInstance().borrarCliente(clt);
	 						btnEliminar.setEnabled(false);
	 						btnModificar.setEnabled(false);
	 						cargarClientes();
	 						JOptionPane.showMessageDialog(null, "El Cliente ha sido eliminado!", "Eliminacion",
	 								JOptionPane.INFORMATION_MESSAGE);
	                     }
						
					}
				}
			});
			btnEliminar.setEnabled(false);
			buttonPane.add(btnEliminar);
			btnModificar.setEnabled(false);
			buttonPane.add(btnModificar);
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		cargarClientes();
	}
	
	private void cargarClientes() {

		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Cedula");
		model.addColumn("Nombre");
		model.addColumn("Telefono");

		for (Cliente clt : Tienda.getInstance().getMisClientes()) {
			model.addRow(new Object[] { clt.getCedula(),clt.getNombre(),clt.getTelefono()});
		}
		table.setModel(model);
	}
}
