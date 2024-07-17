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

public class AgregarComponentes extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JTextField txtmarca;
    private JTextField txtmodelo;
    private JTextField txtprecio;
    private JTextField txtnumeroserie;
    private JSpinner spnCantidad;
    private JSpinner spnTipo;

    // Panels for extra options
    private JPanel panelDiscoDuro;
    private JPanel panelMemoriaRAM;
    private JPanel panelMicroprocesador;
    private JPanel panelTarjetaMadre;

    // Extra option fields
    private JTextField txtCapacidadDisco;
    private JTextField txtTipoConexionDisco;
    
    private JTextField txtCantidadMemoriaRAM;
    private JTextField txtTipoMemoriaRAM;
    
    private JTextField txtTipoConexionMicro;
    private JTextField txtVelocidadMicro;
    
    private JTextField txtTipoConectorMicro;
    private JTextField txtTipoMemoriaTM;
    private JTextField txtConexionesDiscosTM;

    /**
     * Create the dialog.
     */
    public AgregarComponentes() {
        setBounds(100, 100, 659, 312);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JLabel lblMarca = new JLabel("Marca:");
        lblMarca.setBounds(10, 10, 80, 25);
        contentPanel.add(lblMarca);

        txtmarca = new JTextField();
        txtmarca.setBounds(77, 10, 210, 25);
        contentPanel.add(txtmarca);
        txtmarca.setColumns(10);

        JLabel lblModelo = new JLabel("Modelo:");
        lblModelo.setBounds(321, 10, 80, 25);
        contentPanel.add(lblModelo);

        txtmodelo = new JTextField();
        txtmodelo.setBounds(393, 10, 210, 25);
        contentPanel.add(txtmodelo);
        txtmodelo.setColumns(10);

        JLabel lblPrecio = new JLabel("Precio:");
        lblPrecio.setBounds(10, 48, 80, 25);
        contentPanel.add(lblPrecio);

        txtprecio = new JTextField();
        txtprecio.setBounds(77, 48, 210, 25);
        contentPanel.add(txtprecio);
        txtprecio.setColumns(10);

        JLabel lblCantidad = new JLabel("Cantidad:");
        lblCantidad.setBounds(321, 48, 80, 25);
        contentPanel.add(lblCantidad);

        spnCantidad = new JSpinner();
        spnCantidad.setModel(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1)); // Valores de 1 a infinito
        spnCantidad.setBounds(393, 48, 210, 25);
        contentPanel.add(spnCantidad);

        JLabel lblNumeroSerie = new JLabel("Número de Serie:");
        lblNumeroSerie.setBounds(10, 94, 116, 25);
        contentPanel.add(lblNumeroSerie);

        txtnumeroserie = new JTextField();
        txtnumeroserie.setBounds(122, 94, 165, 25);
        contentPanel.add(txtnumeroserie);
        txtnumeroserie.setColumns(10);
        
        JLabel lblTipo = new JLabel("Tipo:");
        lblTipo.setBounds(321, 94, 80, 25);
        contentPanel.add(lblTipo);
        
        spnTipo = new JSpinner();
        spnTipo.setModel(new SpinnerListModel(new String[] {"<Seleccionar>", "Disco Duro", "Memoria RAM", "Microprocesador", "Tarjeta Madre"}));
        spnTipo.setBounds(393, 94, 210, 25);
        contentPanel.add(spnTipo);
        spnTipo.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                mostrarPanelOpciones((String) spnTipo.getValue());
            }
        });

        inicializarPanelesOpciones();

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (verificarCamposLlenos()) {
                    String marca = txtmarca.getText();
                    String modelo = txtmodelo.getText();
                    double precio = Double.parseDouble(txtprecio.getText());
                    int cantidad = (int) spnCantidad.getValue();
                    String numeroSerie = txtnumeroserie.getText();
                    String tipo = (String) spnTipo.getValue();

                    Componente componente = null;
                    switch (tipo) {
                        case "Disco Duro":
                            int capacidadDisco = Integer.parseInt(txtCapacidadDisco.getText());
                            String tipoConexionDisco = txtTipoConexionDisco.getText();
                            componente = new DiscoDuro(marca, modelo, precio, cantidad, numeroSerie, capacidadDisco, tipoConexionDisco);
                            break;
                        case "Memoria RAM":
                            int cantidadMemoriaRAM = Integer.parseInt(txtCantidadMemoriaRAM.getText());
                            String tipoMemoriaRAM = txtTipoMemoriaRAM.getText();
                            componente = new MemoriaRAM(marca, modelo, precio, cantidad, numeroSerie, cantidadMemoriaRAM, tipoMemoriaRAM);
                            break;
                        case "Microprocesador":
                            String tipoConexionMicro = txtTipoConexionMicro.getText();
                            double velocidadMicro = Double.parseDouble(txtVelocidadMicro.getText());
                            componente = new Microprocesador(marca, modelo, precio, cantidad, numeroSerie, tipoConexionMicro, velocidadMicro);
                            break;
                        case "Tarjeta Madre":
                            String tipoConectorMicro = txtTipoConectorMicro.getText();
                            String tipoMemoriaTM = txtTipoMemoriaTM.getText();
                            ArrayList<String> conexionesDiscosTM = new ArrayList<>(Arrays.asList(txtConexionesDiscosTM.getText().split(",")));
                            componente = new TarjetaMadre(marca, modelo, precio, cantidad, numeroSerie, tipoConectorMicro, tipoMemoriaTM, conexionesDiscosTM);
                            break;
                    }

                    if (componente != null) {
                        Tienda.getInstance().agregarComponente(componente);
                        JOptionPane.showMessageDialog(null, "Componente registrado exitosamente.", "Registro exitoso", JOptionPane.INFORMATION_MESSAGE);
                    }
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
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

    private void inicializarPanelesOpciones() {
        panelDiscoDuro = new JPanel();
        panelDiscoDuro.setBounds(10, 130, 610, 60);
        panelDiscoDuro.setVisible(false);
        contentPanel.add(panelDiscoDuro);
        panelDiscoDuro.setLayout(null);

        JLabel lblCapacidadDisco = new JLabel("Capacidad (GB):");
        lblCapacidadDisco.setBounds(10, 10, 120, 25);
        panelDiscoDuro.add(lblCapacidadDisco);

        txtCapacidadDisco = new JTextField();
        txtCapacidadDisco.setBounds(140, 10, 100, 25);
        panelDiscoDuro.add(txtCapacidadDisco);
        txtCapacidadDisco.setColumns(10);

        JLabel lblTipoConexionDisco = new JLabel("Tipo de Conexión:");
        lblTipoConexionDisco.setBounds(270, 10, 120, 25);
        panelDiscoDuro.add(lblTipoConexionDisco);

        txtTipoConexionDisco = new JTextField();
        txtTipoConexionDisco.setBounds(400, 10, 100, 25);
        panelDiscoDuro.add(txtTipoConexionDisco);
        txtTipoConexionDisco.setColumns(10);

        panelMemoriaRAM = new JPanel();
        panelMemoriaRAM.setBounds(10, 130, 610, 60);
        panelMemoriaRAM.setVisible(false);
        contentPanel.add(panelMemoriaRAM);
        panelMemoriaRAM.setLayout(null);

        JLabel lblCantidadMemoriaRAM = new JLabel("Cantidad de Memoria (GB):");
        lblCantidadMemoriaRAM.setBounds(10, 10, 160, 25);
        panelMemoriaRAM.add(lblCantidadMemoriaRAM);

        txtCantidadMemoriaRAM = new JTextField();
        txtCantidadMemoriaRAM.setBounds(180, 10, 100, 25);
        panelMemoriaRAM.add(txtCantidadMemoriaRAM);
        txtCantidadMemoriaRAM.setColumns(10);

        JLabel lblTipoMemoriaRAM = new JLabel("Tipo de Memoria:");
        lblTipoMemoriaRAM.setBounds(300, 10, 100, 25);
        panelMemoriaRAM.add(lblTipoMemoriaRAM);

        txtTipoMemoriaRAM = new JTextField();
        txtTipoMemoriaRAM.setBounds(410, 10, 100, 25);
        panelMemoriaRAM.add(txtTipoMemoriaRAM);
        txtTipoMemoriaRAM.setColumns(10);

        panelMicroprocesador = new JPanel();
        panelMicroprocesador.setBounds(10, 130, 610, 60);
        panelMicroprocesador.setVisible(false);
        contentPanel.add(panelMicroprocesador);
        panelMicroprocesador.setLayout(null);

        JLabel lblTipoConexionMicro = new JLabel("Tipo de Conexión:");
        lblTipoConexionMicro.setBounds(10, 10, 120, 25);
        panelMicroprocesador.add(lblTipoConexionMicro);

        txtTipoConexionMicro = new JTextField();
        txtTipoConexionMicro.setBounds(140, 10, 100, 25);
        panelMicroprocesador.add(txtTipoConexionMicro);
        txtTipoConexionMicro.setColumns(10);

        JLabel lblVelocidadMicro = new JLabel("Velocidad (GHz):");
        lblVelocidadMicro.setBounds(270, 10, 120, 25);
        panelMicroprocesador.add(lblVelocidadMicro);

        txtVelocidadMicro = new JTextField();
        txtVelocidadMicro.setBounds(400, 10, 100, 25);
        panelMicroprocesador.add(txtVelocidadMicro);
        txtVelocidadMicro.setColumns(10);

        panelTarjetaMadre = new JPanel();
        panelTarjetaMadre.setBounds(10, 130, 610, 90);
        panelTarjetaMadre.setVisible(false);
        contentPanel.add(panelTarjetaMadre);
        panelTarjetaMadre.setLayout(null);

        JLabel lblTipoConectorMicro = new JLabel("Tipo de Conector Micro:");
        lblTipoConectorMicro.setBounds(10, 10, 150, 25);
        panelTarjetaMadre.add(lblTipoConectorMicro);

        txtTipoConectorMicro = new JTextField();
        txtTipoConectorMicro.setBounds(170, 10, 100, 25);
        panelTarjetaMadre.add(txtTipoConectorMicro);
        txtTipoConectorMicro.setColumns(10);

        JLabel lblTipoMemoriaTM = new JLabel("Tipo de Memoria RAM:");
        lblTipoMemoriaTM.setBounds(10, 45, 150, 25);
        panelTarjetaMadre.add(lblTipoMemoriaTM);

        txtTipoMemoriaTM = new JTextField();
        txtTipoMemoriaTM.setBounds(170, 45, 100, 25);
        panelTarjetaMadre.add(txtTipoMemoriaTM);
        txtTipoMemoriaTM.setColumns(10);

        JLabel lblConexionesDiscosTM = new JLabel("Conexiones para Discos:");
        lblConexionesDiscosTM.setBounds(300, 10, 150, 25);
        panelTarjetaMadre.add(lblConexionesDiscosTM);

        txtConexionesDiscosTM = new JTextField();
        txtConexionesDiscosTM.setBounds(460, 10, 140, 25);
        panelTarjetaMadre.add(txtConexionesDiscosTM);
        txtConexionesDiscosTM.setColumns(10);
    }

    private void mostrarPanelOpciones(String tipo) {
        panelDiscoDuro.setVisible(false);
        panelMemoriaRAM.setVisible(false);
        panelMicroprocesador.setVisible(false);
        panelTarjetaMadre.setVisible(false);

        switch (tipo) {
            case "Disco Duro":
                panelDiscoDuro.setVisible(true);
                break;
            case "Memoria RAM":
                panelMemoriaRAM.setVisible(true);
                break;
            case "Microprocesador":
                panelMicroprocesador.setVisible(true);
                break;
            case "Tarjeta Madre":
                panelTarjetaMadre.setVisible(true);
                break;
        }
    }

    private boolean verificarCamposLlenos() {
        if (txtmarca.getText().isEmpty() || txtmodelo.getText().isEmpty() || txtprecio.getText().isEmpty() || 
            txtnumeroserie.getText().isEmpty() || spnTipo.getValue().equals("<Seleccionar>")) {
            return false;
        }

        String tipo = (String) spnTipo.getValue();
        switch (tipo) {
            case "Disco Duro":
                return !txtCapacidadDisco.getText().isEmpty() && !txtTipoConexionDisco.getText().isEmpty();
            case "Memoria RAM":
                return !txtCantidadMemoriaRAM.getText().isEmpty() && !txtTipoMemoriaRAM.getText().isEmpty();
            case "Microprocesador":
                return !txtTipoConexionMicro.getText().isEmpty() && !txtVelocidadMicro.getText().isEmpty();
            case "Tarjeta Madre":
                return !txtTipoConectorMicro.getText().isEmpty() && !txtTipoMemoriaTM.getText().isEmpty() && !txtConexionesDiscosTM.getText().isEmpty();
            default:
                return false;
        }
    }
}
