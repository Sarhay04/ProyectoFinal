package Visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import logico.Componente;
import logico.DiscoDuro;
import logico.MemoriaRAM;
import logico.Microprocesador;
import logico.TarjetaMadre;
import logico.Tienda;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.border.EtchedBorder;

public class AgregarComponentes extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtmarca;
	private JTextField txtmodelo;
	private JTextField txtprecio;
	private JTextField txtnumeroserie;
	private JSpinner spnCantidad;
	private JSpinner spnTipo;
	private Componente componente;

	private JPanel panelDiscoDuro;
	private JPanel panelMemoriaRAM;
	private JPanel panelMicroprocesador;
	private JPanel panelTarjetaMadre;
	private JTextField txtVelocidadMicro;
	private JTextField txtConexionesDiscosTM;
	private JTextField txtNombre;
	private JComboBox comboBoxCap;
	private JComboBox comboBoxConexion;
	private JComboBox comboBoxMR;
	private JComboBox comboBoxMT;
	private JComboBox comboBoxC;
	private JComboBox mcm;
	private JComboBox mrm;

	public AgregarComponentes(Componente componenteSeleccionado) {
		setResizable(false);
		this.componente = componenteSeleccionado;

		if (componente == null) {
			setTitle("Registrar Componente");
		} else {
			setTitle("Modificar Componente");
		}

		setBounds(100, 100, 659, 357);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblMarca = new JLabel("Marca:");
		lblMarca.setBounds(338, 14, 80, 25);
		contentPanel.add(lblMarca);

		txtmarca = new JTextField();
		txtmarca.setBounds(410, 14, 210, 25);
		contentPanel.add(txtmarca);
		txtmarca.setColumns(10);

		JLabel lblModelo = new JLabel("Modelo:");
		lblModelo.setBounds(26, 52, 80, 25);
		contentPanel.add(lblModelo);

		txtmodelo = new JTextField();
		txtmodelo.setBounds(93, 52, 210, 25);
		contentPanel.add(txtmodelo);
		txtmodelo.setColumns(10);

		JLabel lblPrecio = new JLabel("Precio:");
		lblPrecio.setBounds(343, 52, 80, 25);
		contentPanel.add(lblPrecio);

		txtprecio = new JTextField();
		txtprecio.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				String text = txtprecio.getText();

				if (!Character.isDigit(c) && c != '.' && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
					e.consume();
				}

				if (c == '.' && text.contains(".")) {
					e.consume();
				}

				if (text.contains(".")) {
					int index = text.indexOf('.');
					if (text.length() - index > 2) {
						e.consume();
					}
				}
			}
		});
		txtprecio.setBounds(410, 52, 210, 25);
		contentPanel.add(txtprecio);
		txtprecio.setColumns(10);

		JLabel lblCantidad = new JLabel("Cantidad:");
		lblCantidad.setBounds(336, 93, 80, 25);
		contentPanel.add(lblCantidad);

		spnCantidad = new JSpinner();
		spnCantidad.setModel(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
		spnCantidad.setBounds(408, 93, 210, 25);
		contentPanel.add(spnCantidad);

		JLabel lblNumeroSerie = new JLabel("Número de Serie:");
		lblNumeroSerie.setBounds(24, 93, 116, 25);
		contentPanel.add(lblNumeroSerie);

		txtnumeroserie = new JTextField();
		txtnumeroserie.setBounds(138, 93, 165, 25);
		contentPanel.add(txtnumeroserie);
		txtnumeroserie.setColumns(10);

		JLabel lblTipo = new JLabel("Tipo:");
		lblTipo.setBounds(26, 138, 80, 25);
		contentPanel.add(lblTipo);

		spnTipo = new JSpinner();
		spnTipo.setModel(new SpinnerListModel(
				new String[] { "<Seleccionar>", "Disco Duro", "Memoria RAM", "Microprocesador", "Tarjeta Madre" }));
		spnTipo.setBounds(93, 133, 210, 25);
		contentPanel.add(spnTipo);
		spnTipo.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				mostrarPanelOpciones((String) spnTipo.getValue());
			}
		});

		inicializarPanelesOpciones();

		JPanel buttonPane = new JPanel();
		buttonPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton btnRegistrar = new JButton();
		if (componente == null) {
			btnRegistrar.setText("Registrar");
		} else {
			btnRegistrar.setText("Modificar");
		}
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (verificarCamposLlenos()) {
					String marca = txtmarca.getText();
					String modelo = txtmodelo.getText();
					String nombre = txtNombre.getText();
					double precio = Double.parseDouble(txtprecio.getText());
					int cantidad = (int) spnCantidad.getValue();
					String numeroSerie = txtnumeroserie.getText();
					String tipo = (String) spnTipo.getValue();

					if (componente == null && !existsByNumeroDeSerie(txtnumeroserie.getText())) {
						switch (tipo) {
						case "Disco Duro":
							int capacidadDisco = Integer.parseInt(comboBoxCap.getSelectedItem().toString());
							String tipoConexionDisco = comboBoxConexion.getSelectedItem().toString();
							componente = new DiscoDuro(nombre, marca, modelo, precio, cantidad, numeroSerie,
									capacidadDisco, tipoConexionDisco);
							break;
						case "Memoria RAM":
							int cantidadMemoriaRAM = Integer.parseInt(comboBoxMR.getSelectedItem().toString());
							String tipoMemoriaRAM = comboBoxMT.getSelectedItem().toString();
							componente = new MemoriaRAM(nombre, marca, modelo, precio, cantidad, numeroSerie,
									cantidadMemoriaRAM, tipoMemoriaRAM);
							break;
						case "Microprocesador":
							String tipoConexionMicro = comboBoxC.getSelectedItem().toString();
							double velocidadMicro = Double.parseDouble(txtVelocidadMicro.getText());
							componente = new Microprocesador(nombre, marca, modelo, precio, cantidad, numeroSerie,
									tipoConexionMicro, velocidadMicro);
							break;
						case "Tarjeta Madre":
							String tipoConectorMicro = mcm.getSelectedItem().toString();
							String tipoMemoriaTM = mrm.getSelectedItem().toString();
							ArrayList<String> conexionesDiscosTM = new ArrayList<>(
									Arrays.asList(txtConexionesDiscosTM.getText().split(",")));
							componente = new TarjetaMadre(nombre, marca, modelo, precio, cantidad, numeroSerie,
									tipoConectorMicro, tipoMemoriaTM, conexionesDiscosTM);
							break;
						}
						Tienda.getInstance().agregarComponente(componente);
						JOptionPane.showMessageDialog(null, "Componente registrado exitosamente.", "Registro exitoso",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						componente.setMarca(marca);
						componente.setModelo(modelo);
						componente.setPrecio(precio);
						componente.setCantidadDisponible(cantidad);
						componente.setNumeroDeSerie(numeroSerie);

						if (componente instanceof DiscoDuro) {
							DiscoDuro disco = (DiscoDuro) componente;
							disco.setCapacidadAlmacenamiento(
									Integer.parseInt(comboBoxCap.getSelectedItem().toString()));
							disco.setTipoConexion(comboBoxConexion.getSelectedItem().toString());
						} else if (componente instanceof MemoriaRAM) {
							MemoriaRAM ram = (MemoriaRAM) componente;
							ram.setCantidadMemoria(Integer.parseInt(comboBoxCap.getSelectedItem().toString()));
							ram.setTipoMemoria(comboBoxMT.getSelectedItem().toString());
						} else if (componente instanceof Microprocesador) {
							Microprocesador micro = (Microprocesador) componente;
							micro.setTipoConexion(comboBoxC.getSelectedItem().toString());
							micro.setVelocidadProcesamiento(Double.parseDouble(txtVelocidadMicro.getText()));
						} else if (componente instanceof TarjetaMadre) {
							TarjetaMadre madre = (TarjetaMadre) componente;
							madre.setTipoConectorMicro(mcm.getSelectedItem().toString());
							madre.setTipoMemoriaRAM(mrm.getSelectedItem().toString());
							madre.setConexionesDiscosDuros(
									new ArrayList<>(Arrays.asList(txtConexionesDiscosTM.getText().split(","))));
						}
						Tienda.getInstance().actualizarComponente(componente.getNumeroDeSerie(), componente);
						JOptionPane.showMessageDialog(null, "Componente modificado exitosamente.",
								"Modificación exitosa", JOptionPane.INFORMATION_MESSAGE);
					}
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Campos incompletos",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnRegistrar.setActionCommand("OK");
		buttonPane.add(btnRegistrar);
		getRootPane().setDefaultButton(btnRegistrar);

		JButton cancelButton = new JButton("Cancelar");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
	}

	public static boolean existsByNumeroDeSerie(String numeroDeSerie) {
		for (Componente componente : Tienda.getInstance().getInventario()) {
			if (componente.getNumeroDeSerie().equals(numeroDeSerie)) {
				JOptionPane.showMessageDialog(null, "El Numero de Serie ya esta registrado.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return true;
			}
		}
		return false;
	}

	private void inicializarPanelesOpciones() {

		panelTarjetaMadre = new JPanel();
		panelTarjetaMadre.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelTarjetaMadre.setBounds(10, 418, 610, 90);
		panelTarjetaMadre.setVisible(false);
		contentPanel.add(panelTarjetaMadre);
		panelTarjetaMadre.setLayout(null);

		JLabel lblTipoConectorMicro = new JLabel("Tipo de Conector Micro:");
		lblTipoConectorMicro.setBounds(10, 10, 176, 25);
		panelTarjetaMadre.add(lblTipoConectorMicro);

		JLabel lblTipoMemoriaTM = new JLabel("Tipo de Memoria RAM:");
		lblTipoMemoriaTM.setBounds(10, 45, 188, 25);
		panelTarjetaMadre.add(lblTipoMemoriaTM);

		JLabel lblConexionesDiscosTM = new JLabel("Conexiones para Discos:");
		lblConexionesDiscosTM.setBounds(289, 10, 150, 25);
		panelTarjetaMadre.add(lblConexionesDiscosTM);

		txtConexionesDiscosTM = new JTextField();
		txtConexionesDiscosTM.setBounds(432, 10, 168, 25);
		panelTarjetaMadre.add(txtConexionesDiscosTM);
		txtConexionesDiscosTM.setColumns(10);
		
		mcm = new JComboBox();
		mcm.setModel(new DefaultComboBoxModel(new String[] {"LGA", "PGA", "ARM"}));
		mcm.setBounds(158, 12, 108, 21);
		panelTarjetaMadre.add(mcm);
		
		mrm = new JComboBox();
		mrm.setModel(new DefaultComboBoxModel(new String[] {"DRAM", "SRAM", "RDRAM", "HBM"}));
		mrm.setBounds(158, 47, 108, 21);
		panelTarjetaMadre.add(mrm);

		JLabel lblNewLabel = new JLabel("Nombre:");
		lblNewLabel.setBounds(26, 20, 69, 13);
		contentPanel.add(lblNewLabel);

		txtNombre = new JTextField();
		txtNombre.setColumns(10);
		txtNombre.setBounds(93, 14, 210, 25);
		contentPanel.add(txtNombre);

		panelMicroprocesador = new JPanel();
		panelMicroprocesador.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelMicroprocesador.setBounds(10, 348, 610, 48);
		contentPanel.add(panelMicroprocesador);
		panelMicroprocesador.setVisible(false);
		panelMicroprocesador.setLayout(null);

		JLabel lblTipoConexionMicro = new JLabel("Tipo de Conexión:");
		lblTipoConexionMicro.setBounds(10, 10, 120, 25);
		panelMicroprocesador.add(lblTipoConexionMicro);

		JLabel lblVelocidadMicro = new JLabel("Velocidad (GHz):");
		lblVelocidadMicro.setBounds(289, 10, 120, 25);
		panelMicroprocesador.add(lblVelocidadMicro);

		txtVelocidadMicro = new JTextField();
		txtVelocidadMicro.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
                String text = txtVelocidadMicro.getText();
                
                if (!Character.isDigit(c) && c != '.' && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
                    e.consume();  
                }
                
                if (c == '.' && text.contains(".")) {
                    e.consume();  
                }
                
           
                if (text.contains(".")) {
                    int index = text.indexOf('.');
                    if (text.length() - index > 2) {
                        e.consume();  
                    }
                }
			}
		});

		txtVelocidadMicro.setBounds(416, 10, 104, 25);
		panelMicroprocesador.add(txtVelocidadMicro);
		txtVelocidadMicro.setColumns(10);

		comboBoxC = new JComboBox();
		comboBoxC.setModel(new DefaultComboBoxModel(new String[] { "Address Bus", "Data Bus", "Control Bus" }));
		comboBoxC.setBounds(140, 12, 120, 21);
		panelMicroprocesador.add(comboBoxC);

		panelMemoriaRAM = new JPanel();
		panelMemoriaRAM.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelMemoriaRAM.setBounds(10, 278, 610, 48);
		contentPanel.add(panelMemoriaRAM);
		panelMemoriaRAM.setVisible(false);
		panelMemoriaRAM.setLayout(null);

		JLabel lblCantidadMemoriaRAM = new JLabel("Cantidad de Memoria (GB):");
		lblCantidadMemoriaRAM.setBounds(10, 10, 160, 25);
		panelMemoriaRAM.add(lblCantidadMemoriaRAM);

		JLabel lblTipoMemoriaRAM = new JLabel("Tipo de Memoria:");
		lblTipoMemoriaRAM.setBounds(303, 10, 100, 25);
		panelMemoriaRAM.add(lblTipoMemoriaRAM);

		comboBoxMR = new JComboBox();
		comboBoxMR.setModel(new DefaultComboBoxModel(new String[] { "4", "8", "16", "32", "64" }));
		comboBoxMR.setBounds(167, 12, 100, 21);
		panelMemoriaRAM.add(comboBoxMR);

		comboBoxMT = new JComboBox();
		comboBoxMT.setModel(new DefaultComboBoxModel(new String[] {"DRAM", "SRAM", "RDRAM", "HBM"}));
		comboBoxMT.setBounds(420, 12, 104, 21);
		panelMemoriaRAM.add(comboBoxMT);
		panelDiscoDuro = new JPanel();
		panelDiscoDuro.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelDiscoDuro.setBounds(10, 173, 610, 48);
		contentPanel.add(panelDiscoDuro);
		panelDiscoDuro.setVisible(false);
		panelDiscoDuro.setLayout(null);

		JLabel lblCapacidadDisco = new JLabel("Capacidad (GB):");
		lblCapacidadDisco.setBounds(10, 10, 120, 25);
		panelDiscoDuro.add(lblCapacidadDisco);

		JLabel lblTipoConexionDisco = new JLabel("Tipo de Conexión:");
		lblTipoConexionDisco.setBounds(289, 10, 120, 25);
		panelDiscoDuro.add(lblTipoConexionDisco);

		comboBoxCap = new JComboBox();
		comboBoxCap.setModel(new DefaultComboBoxModel(new String[] { "100", "200", "300", "500", "1000" }));
		comboBoxCap.setBounds(122, 12, 131, 21);
		panelDiscoDuro.add(comboBoxCap);

		comboBoxConexion = new JComboBox();
		comboBoxConexion.setModel(
				new DefaultComboBoxModel(new String[] { "SATA", "PATA", "SCSI", "SAS", "eSATA", "Thunderbolt" }));
		comboBoxConexion.setBounds(403, 12, 104, 21);
		panelDiscoDuro.add(comboBoxConexion);

		if (componente != null) {
			llenarCampos();
		}
	}

	private void mostrarPanelOpciones(String tipo) {
		panelDiscoDuro.setVisible(false);
		panelMemoriaRAM.setVisible(false);
		panelMicroprocesador.setVisible(false);
		panelTarjetaMadre.setVisible(false);

		switch (tipo) {
		case "Disco Duro":
			panelDiscoDuro.setBounds(10, 173, 610, 48);
			panelDiscoDuro.setVisible(true);
			break;
		case "Memoria RAM":
			panelMemoriaRAM.setVisible(true);
			panelMemoriaRAM.setBounds(10, 173, 610, 48);
			break;
		case "Microprocesador":
			panelMicroprocesador.setVisible(true);
			panelMicroprocesador.setBounds(10, 173, 610, 48);
			break;
		case "Tarjeta Madre":
			panelTarjetaMadre.setVisible(true);
			panelTarjetaMadre.setBounds(10, 173, 610, 90);
			break;
		}
	}

	private boolean verificarCamposLlenos() {
		if (txtNombre.getText().isEmpty() || txtmarca.getText().isEmpty() || txtmodelo.getText().isEmpty()
				|| txtprecio.getText().isEmpty() || txtnumeroserie.getText().isEmpty()
				|| spnTipo.getValue().equals("<Seleccionar>")) {
			return false;
		}

		String tipo = (String) spnTipo.getValue();
		switch (tipo) {
		case "Disco Duro":
			return true;
		case "Memoria RAM":
			return true;
		case "Microprocesador":
			return !txtVelocidadMicro.getText().isEmpty();
		case "Tarjeta Madre":
			return !txtConexionesDiscosTM.getText().isEmpty();
		default:
			return false;
		}
	}

	private void llenarCampos() {
		txtNombre.setText(componente.getNombre());
		txtmarca.setText(componente.getMarca());
		txtmodelo.setText(componente.getModelo());
		txtprecio.setText(String.valueOf(componente.getPrecio()));
		spnCantidad.setValue(componente.getCantidadDisponible());
		txtnumeroserie.setText(componente.getNumeroDeSerie());

		if (componente instanceof DiscoDuro) {
			spnTipo.setValue("Disco Duro");
			DiscoDuro disco = (DiscoDuro) componente;
			comboBoxCap.setSelectedItem(String.valueOf(disco.getCapacidadAlmacenamiento()));
			comboBoxConexion.setSelectedItem(disco.getTipoConexion());
		} else if (componente instanceof MemoriaRAM) {
			spnTipo.setValue("Memoria RAM");
			MemoriaRAM ram = (MemoriaRAM) componente;
			comboBoxMR.setSelectedItem(String.valueOf(ram.getCantidadMemoria()));
			comboBoxMT.setSelectedItem(ram.getTipoMemoria());
		} else if (componente instanceof Microprocesador) {
			spnTipo.setValue("Microprocesador");
			Microprocesador micro = (Microprocesador) componente;
			comboBoxC.setSelectedItem(micro.getTipoConexion());;
			txtVelocidadMicro.setText(String.valueOf(micro.getVelocidadProcesamiento()));
		} else if (componente instanceof TarjetaMadre) {
			spnTipo.setValue("Tarjeta Madre");
			TarjetaMadre madre = (TarjetaMadre) componente;
			mcm.setSelectedItem(madre.getTipoConectorMicro());;
			mrm.setSelectedItem(madre.getTipoMemoriaRAM());;
			txtConexionesDiscosTM.setText(String.join(",", madre.getConexionesDiscosDuros()));
		}
	}
}
