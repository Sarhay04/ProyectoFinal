package Visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import logico.Cliente;
import logico.Tienda;
import logico.User;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ListarCliente extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTable table;
    private JButton btnModificar;
    private Cliente clt = null;
    private JButton btnEliminar;
    private User user; 

 
    public ListarCliente(User user) { 
        this.user = user; 
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
                    if (user.getTipo().equals("Administrador")) {
                        btnEliminar.setEnabled(true);
                    }
                    clt = Tienda.getInstance().getClienteByCedula(table.getValueAt(index, 0).toString());
                }
            }
        });
        Font newFont = new Font("Tahoma", Font.PLAIN, 18);
        table.setFont(newFont);
        table.setRowHeight(25);
        scrollPane.setViewportView(table);
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            
            btnModificar = new JButton("Modificar");
            btnModificar.setEnabled(false);
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
            buttonPane.add(btnModificar);
            
            btnEliminar = new JButton("Eliminar");
            btnEliminar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if(clt != null && Tienda.getInstance().checkClienteDelete(clt)) {
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

        configureButtonsByUserType();
        
        cargarClientes();
    }

    private void configureButtonsByUserType() {
        if (user != null) {
            switch (user.getTipo()) {
                case "Basico":
                    btnModificar.setVisible(false);
                    btnEliminar.setVisible(false);
                    break;
                case "Privilegiado":
                    btnEliminar.setVisible(false);
                    break;
                case "Administrador":
                    btnModificar.setVisible(true);
                    btnEliminar.setVisible(true);
                    break;
            }
        }
    }

    private void cargarClientes() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Cedula");
        model.addColumn("Nombre");
        model.addColumn("Telefono");

        for (Cliente clt : Tienda.getInstance().getMisClientes()) {
            model.addRow(new Object[] { clt.getCedula(), clt.getNombre(), clt.getTelefono() });
        }
        table.setModel(model);
    }
}
