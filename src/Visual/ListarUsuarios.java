package Visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import logico.Tienda;
import logico.User;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ListarUsuarios extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private JComboBox cmbtipo;
	private JButton btneliminar;
	private JButton btnmodificar;
	private User miusuario = null;

	public ListarUsuarios() {
		setResizable(false);
		setTitle("Usuarios");
		setBounds(100, 100, 727, 464);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(
				new TitledBorder(null, "Listado de Usuarios", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(12, 13, 685, 356);
		contentPanel.add(panel);
		panel.setLayout(null);

		JLabel label = new JLabel("Tipo:");
		label.setBounds(12, 28, 56, 16);
		panel.add(label);

		cmbtipo = new JComboBox();
		cmbtipo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListarUsuariosPorTipo();
			}
		});
		cmbtipo.setModel(
				new DefaultComboBoxModel(new String[] { "<Todos>", "Administrador", "Privilegiado", "Basico" }));
		cmbtipo.setBounds(51, 25, 165, 22);
		panel.add(cmbtipo);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 59, 661, 284);
		panel.add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = table.getSelectedRow();
				if (index >= 0) {
					btnmodificar.setEnabled(true);
					btneliminar.setEnabled(true);
					miusuario = Tienda.getInstance().getUsuariobyTipo(table.getValueAt(index, 0).toString());
				}

			}
		});
		Font newFont = new Font("Tahoma", Font.PLAIN, 18);
        table.setFont(newFont);
        table.setRowHeight(25);
		scrollPane.setViewportView(table);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btneliminar = new JButton("Eliminar");
				btneliminar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (miusuario != null) {
							int option = JOptionPane.showConfirmDialog(null,
									"Estas seguro(a) que desea eliminar el usuario : " + miusuario.getUsuario(),
									"Confirmacion", JOptionPane.OK_CANCEL_OPTION);
							if (option == JOptionPane.OK_OPTION) {
								Tienda.getInstance().EliminarUser(miusuario);
								btneliminar.setEnabled(false);
								btnmodificar.setEnabled(false);
								ListarUsuariosPorTipo();
							}
						}
					}
				});
				btneliminar.setEnabled(false);
				buttonPane.add(btneliminar);
			}
			{
				btnmodificar = new JButton("Modificar");
				btnmodificar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						RegistrarUsuario modificar = new RegistrarUsuario(miusuario);
						modificar.setModal(true);
						modificar.setVisible(true);
						ListarUsuariosPorTipo();
					}
				});
				btnmodificar.setEnabled(false);
				btnmodificar.setActionCommand("OK");
				buttonPane.add(btnmodificar);
				getRootPane().setDefaultButton(btnmodificar);
			}
			{
				JButton btncancelar = new JButton("Cancelar");
				btncancelar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				btncancelar.setActionCommand("Cancel");
				buttonPane.add(btncancelar);
			}
		}
		ListarUsuariosPorTipo();
	}

	private void ListarUsuariosPorTipo() {
		String tipoSeleccionado = cmbtipo.getSelectedItem().toString();
		ArrayList<User> users = Tienda.getInstance().getUsuariosbyTipo(tipoSeleccionado);

		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Usuario");
		model.addColumn("Password");
		model.addColumn("Acceso");

		for (User user : users) {
			model.addRow(new Object[] { user.getUsuario(), user.getPassword(), user.getTipo(), });
		}

		table.setModel(model);
	}
}
