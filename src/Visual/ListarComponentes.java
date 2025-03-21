package Visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import logico.Componente;
import logico.DiscoDuro;
import logico.MemoriaRAM;
import logico.Microprocesador;
import logico.TarjetaMadre;
import logico.Tienda;
import logico.User;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ListarComponentes extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JTable table;
    private JComboBox<String> cmbTipo;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton cancelButton;
    private Componente componenteSeleccionado;
    private boolean mode;
    private User user;

    public ListarComponentes(User user, boolean mode) {
        this.user = user;
        this.mode = mode;
        
        setTitle("Listar Componentes");
        setBounds(100, 100, 831, 532);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(12, 13, 789, 424);
        contentPanel.add(panel);
        panel.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(12, 56, 765, 355);
        panel.add(scrollPane);

        table = new JTable();
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = table.getSelectedRow();
                if (index >= 0) {
                    btnModificar.setEnabled(true);
                    btnEliminar.setEnabled(true);
                    String numeroDeSerie = table.getValueAt(index, 4).toString();
                    componenteSeleccionado = Tienda.getInstance().buscarComponente(numeroDeSerie);
                }
            }
        });
        scrollPane.setViewportView(table);
        Font newFont = new Font("Tahoma", Font.PLAIN, 18);
        table.setFont(newFont);
        table.setRowHeight(25);

        JLabel lblTipo = new JLabel("Tipo:");
        lblTipo.setBounds(12, 27, 56, 16);
        panel.add(lblTipo);

        cmbTipo = new JComboBox<>();
        cmbTipo.setModel(new DefaultComboBoxModel<>(new String[] {"Todos", "Disco Duro", "Memoria RAM", "Microprocesador", "Tarjeta Madre"}));
        cmbTipo.setBounds(55, 24, 130, 22);
        cmbTipo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ListarComponentesporTipo();
            }
        });
        panel.add(cmbTipo);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        btnModificar = new JButton("Modificar");
        btnModificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (componenteSeleccionado != null) {
                    AgregarComponentes modificar = new AgregarComponentes(componenteSeleccionado);
                    modificar.setModal(true);
                    modificar.setVisible(true);
                    ListarComponentesporTipo();
                }
            }
        });
        btnModificar.setEnabled(false);
        btnModificar.setActionCommand("OK");
        buttonPane.add(btnModificar);
        getRootPane().setDefaultButton(btnModificar);

        btnEliminar = new JButton(mode ? "Añadir" : "Eliminar");
        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (mode) {
                    RealizarVenta.getProducto(componenteSeleccionado);
                } else if (componenteSeleccionado != null) {
                    int option = JOptionPane.showConfirmDialog(null,
                            "¿Estás seguro(a) que desea eliminar el componente con número de serie: "
                                    + componenteSeleccionado.getNumeroDeSerie() + "?",
                            "Confirmación", JOptionPane.OK_CANCEL_OPTION);
                    if (option == JOptionPane.OK_OPTION) {
                        Tienda.getInstance().eliminarComponente(componenteSeleccionado.getNumeroDeSerie());
                        btnEliminar.setEnabled(false);
                        btnModificar.setEnabled(false);
                        ListarComponentesporTipo();
                    }
                }
            }
        });
        btnEliminar.setEnabled(false);
        buttonPane.add(btnEliminar);

        cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);

        ListarComponentesporTipo();
        configureButtonsByUserType();
    }

    private void configureButtonsByUserType() {
        if (user != null) {
            switch (user.getTipo()) {
                case "Basico":
                    btnModificar.setVisible(false);
                    btnEliminar.setVisible(mode);
                    break;
                case "Privilegiado":
                    btnModificar.setVisible(true);
                    btnEliminar.setVisible(mode);
                    break;
                case "Administrador":
                    btnModificar.setVisible(true);
                    btnEliminar.setVisible(true);
                    break;
            }
        }
    }

    private void ListarComponentesporTipo() {
        String tipoSeleccionado = cmbTipo.getSelectedItem().toString();
        ArrayList<Componente> componentes = Tienda.getInstance().getInventario();

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Nombre");
        model.addColumn("Modelo");
        model.addColumn("Precio");
        model.addColumn("Cantidad");
        model.addColumn("N-Serie");

        if (tipoSeleccionado.equals("Todos")) {
            for (Componente componente : componentes) {
                model.addRow(new Object[] {
                    componente.getNombre(),
                    componente.getModelo(),
                    componente.getPrecio(),
                    componente.getCantidadDisponible(),
                    componente.getNumeroDeSerie(),
                    componente.getClass().getSimpleName()
                });
            }
        } else {
            switch (tipoSeleccionado) {
                case "Disco Duro":
                    model.addColumn("Capacidad");
                    model.addColumn("Tipo de Conexión");
                    for (Componente componente : componentes) {
                        if (componente instanceof DiscoDuro) {
                            DiscoDuro disco = (DiscoDuro) componente;
                            model.addRow(new Object[] {
                                disco.getNombre(),
                                disco.getModelo(),
                                disco.getPrecio(),
                                disco.getCantidadDisponible(),
                                disco.getNumeroDeSerie(),
                                disco.getCapacidadAlmacenamiento(),
                                disco.getTipoConexion()
                            });
                        }
                    }
                    break;
                case "Memoria RAM":
                    model.addColumn("Cantidad de Memoria");
                    model.addColumn("Tipo de Memoria");
                    for (Componente componente : componentes) {
                        if (componente instanceof MemoriaRAM) {
                            MemoriaRAM ram = (MemoriaRAM) componente;
                            model.addRow(new Object[] {
                                ram.getNombre(),
                                ram.getModelo(),
                                ram.getPrecio(),
                                ram.getCantidadDisponible(),
                                ram.getNumeroDeSerie(),
                                ram.getCantidadMemoria(),
                                ram.getTipoMemoria()
                            });
                        }
                    }
                    break;
                case "Microprocesador":
                    model.addColumn("Tipo de Conexión");
                    model.addColumn("Velocidad");
                    for (Componente componente : componentes) {
                        if (componente instanceof Microprocesador) {
                            Microprocesador micro = (Microprocesador) componente;
                            model.addRow(new Object[] {
                                micro.getNombre(),
                                micro.getModelo(),
                                micro.getPrecio(),
                                micro.getCantidadDisponible(),
                                micro.getNumeroDeSerie(),
                                micro.getTipoConexion(),
                                micro.getVelocidadProcesamiento()
                            });
                        }
                    }
                    break;
                case "Tarjeta Madre":
                    model.addColumn("Tipo de Conector Micro");
                    model.addColumn("Tipo de Memoria RAM");
                    model.addColumn("Conexiones para Discos");
                    for (Componente componente : componentes) {
                        if (componente instanceof TarjetaMadre) {
                            TarjetaMadre madre = (TarjetaMadre) componente;
                            model.addRow(new Object[] {
                                madre.getNombre(),
                                madre.getModelo(),
                                madre.getPrecio(),
                                madre.getCantidadDisponible(),
                                madre.getNumeroDeSerie(),
                                madre.getTipoConectorMicro(),
                                madre.getTipoMemoriaRAM(),
                                madre.getConexionesDiscosDuros().toString()
                            });
                        }
                    }
                    break;
            }
        }

        table.setModel(model);
    }
}
