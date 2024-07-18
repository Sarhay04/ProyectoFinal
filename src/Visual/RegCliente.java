package Visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import logico.Cliente;
import logico.Tienda;

import java.awt.Label;
import java.awt.Font;
import java.awt.TextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RegCliente extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JFormattedTextField txtCedula;
	private JTextArea txtDireccion;
	private JTextField txtNombre;
	private JFormattedTextField txtTelefono;
	private Cliente cliente = null;
	private JButton okButton;
	private JButton btnNewButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Cliente clt = new Cliente("402-1171231-2","Pepe","Mi casa","809-902-0977");
		try {
			RegCliente dialog = new RegCliente(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public RegCliente(Cliente miCliente) {
		setTitle("Cliente");
		setBounds(100, 100, 461, 342);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		txtCedula = new JFormattedTextField(createFormatter("###-#######-#"));
		txtCedula.setBounds(80, 11, 126, 20);
		contentPanel.add(txtCedula);
		
		JLabel lblNewLabel_1 = new JLabel("Cedula:");
		lblNewLabel_1.setBounds(17, 13, 46, 14);
		contentPanel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Nombre:");
		lblNewLabel_1_1.setBounds(223, 13, 58, 14);
		contentPanel.add(lblNewLabel_1_1);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(298, 11, 132, 20);
		contentPanel.add(txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblNewLabel_1_2 = new JLabel("Direccion:");
		lblNewLabel_1_2.setBounds(17, 90, 58, 14);
		contentPanel.add(lblNewLabel_1_2);
		
		txtDireccion = new JTextArea();
		txtDireccion.setBounds(17, 114, 413, 135);
		contentPanel.add(txtDireccion);
		
		txtTelefono = new JFormattedTextField(createFormatter("###-###-####"));
		txtTelefono.setBounds(80, 53, 126, 20);
		contentPanel.add(txtTelefono);
		
		JLabel lblNewLabel_1_3 = new JLabel("Telefono:");
		lblNewLabel_1_3.setBounds(17, 56, 84, 14);
		contentPanel.add(lblNewLabel_1_3);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			
			btnNewButton = new JButton("Limpiar");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					limpiar();
				}
			});
			buttonPane.add(btnNewButton);
			{
				okButton = new JButton("Registrar");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(checkFields())
						{
							if(cliente == null && checkCedula())
							{
								Cliente clt = new Cliente(txtCedula.getText(), txtNombre.getText(), txtDireccion.getText(), txtTelefono.getText());
								Tienda.getInstance().agregarCliente(clt);
								JOptionPane.showMessageDialog(null, "El Cliente ha sido registrado!", "Registro",
										JOptionPane.INFORMATION_MESSAGE);
								limpiar();
							}
							if(cliente != null){
								modificarCliente();
								JOptionPane.showMessageDialog(null, "El Cliente ha sido actualizado", "Registro",
										JOptionPane.INFORMATION_MESSAGE);
								dispose();
							}
							
						}
					}
				});
				buttonPane.add(okButton);
			}
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
		
		if(miCliente != null)
		{
			cliente = miCliente;
			setTitle("Modificar Cliente");
			cargarCliente();
		}
	}
	
	public void cargarCliente() {
		txtCedula.setEditable(false);
		txtCedula.setText(cliente.getCedula());
		txtDireccion.setText(cliente.getDireccion());
		txtNombre.setText(cliente.getNombre());
		txtTelefono.setText(cliente.getTelefono());
		okButton.setText("Actualizar");
		btnNewButton.setVisible(false);
	}
	
	public void modificarCliente() {
		cliente.setDireccion(txtDireccion.getText());
		cliente.setNombre(txtNombre.getText());
		cliente.setTelefono(txtTelefono.getText());
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
	
	public boolean checkFields()
	{
		if(!txtCedula.getText().equals("") && !txtNombre.getText().equals("") && !txtDireccion.getText().equals("") && !txtTelefono.getText().equals(""))
		{
			return true;
		}else {
			JOptionPane.showMessageDialog(null, "Debe llenar todos los campos!", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	
	public void limpiar()
	{
		txtCedula.setText("");
		txtDireccion.setText("");
		txtNombre.setText("");
		txtTelefono.setText("");
	}
	
	public boolean checkCedula()
	{
		for(Cliente aux: Tienda.getInstance().getMisClientes())
		{
			if(aux.getCedula().equals(txtCedula.getText()))
			{
				JOptionPane.showMessageDialog(null, "La cedula ya esta registrada!", "Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		return true;
	}
}
