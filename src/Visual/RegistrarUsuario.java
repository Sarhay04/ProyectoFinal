package Visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logico.Tienda;
import logico.User;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.EtchedBorder;

public class RegistrarUsuario extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtusuario;
	private JTextField txtpassword;
	private User user;
	private JComboBox<String> cmbtipo;

	public RegistrarUsuario(User miusuario) {
		Tienda.getInstance();
		setResizable(false);
		user = miusuario;
		if (user == null) {
			setTitle("Registrar Usuario");
		} else {
			setTitle("Modificar Usuario");
		}
		setBounds(100, 100, 333, 207);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		contentPanel.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JLabel lblusuario = new JLabel("Usuario:");
		lblusuario.setBounds(24, 13, 56, 16);
		panel.add(lblusuario);

		txtusuario = new JTextField();
		txtusuario.setBounds(104, 10, 166, 22);
		panel.add(txtusuario);
		txtusuario.setColumns(10);

		JLabel lblpass = new JLabel("Contraseña:");
		lblpass.setBounds(12, 45, 75, 16);
		panel.add(lblpass);

		txtpassword = new JTextField();
		txtpassword.setColumns(10);
		txtpassword.setBounds(104, 42, 166, 22);
		panel.add(txtpassword);

		cmbtipo = new JComboBox<>();
		cmbtipo.setModel(new DefaultComboBoxModel<>(
				new String[] { "<Seleccionar>", "Basico", "Privilegiado", "Administrador" }));
		cmbtipo.setBounds(104, 77, 166, 22);
		panel.add(cmbtipo);

		JLabel lblTipo = new JLabel("Acceso:");
		lblTipo.setBounds(24, 80, 56, 16);
		panel.add(lblTipo);

		JPanel buttonPane = new JPanel();
		buttonPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton btnregistrar = new JButton("Registrar");
		if (user != null) {
			btnregistrar.setText("Modificar");
		}
		btnregistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (checkfields()) {
					if (user == null) {
						registrarUsuario();
					} else {
						modificarUsuario();
						JOptionPane.showMessageDialog(null, "Operacion satisfactoria", "Registro",
								JOptionPane.INFORMATION_MESSAGE);
						dispose();
					}
				}
			}

		});
		btnregistrar.setActionCommand("OK");
		buttonPane.add(btnregistrar);

		JButton btncancelar = new JButton("Cancelar");
		btncancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btncancelar.setActionCommand("Cancel");
		buttonPane.add(btncancelar);

		cargarUsuario();
	}

	private void modificarUsuario() {
		user.setUsuario(txtusuario.getText());
		user.setPassword(txtpassword.getText());
		user.setTipo(cmbtipo.getSelectedItem().toString());
	}

	private void cargarUsuario() {
		if (user != null) {
			txtusuario.setText(user.getUsuario());
			txtpassword.setText(user.getPassword());
			cmbtipo.setSelectedItem(user.getTipo());
		}
	}

	private void registrarUsuario() {
		    User aux = new User(cmbtipo.getSelectedItem().toString(), txtusuario.getText(), txtpassword.getText());
			Tienda.getInstance().agregarUsuario(aux);
			JOptionPane.showMessageDialog(null, "Operacion satisfactoria", "Registro", JOptionPane.INFORMATION_MESSAGE);
			Clean();
	}

	private boolean checkfields() {
		if (txtusuario.getText().isEmpty() || txtpassword.getText().isEmpty() || cmbtipo.getSelectedIndex() == 0) {
			JOptionPane.showMessageDialog(null, "Debe completar todos los campos obligatorios", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	private void Clean() {
		txtusuario.setText("");
		txtpassword.setText("");
		cmbtipo.setSelectedIndex(0);
	}
}
