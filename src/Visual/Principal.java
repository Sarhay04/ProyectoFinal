package Visual;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import logico.Tienda;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Principal {

    private JFrame frame;
    private Tienda tienda;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Principal window = new Principal();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public Principal() {
        tienda = cargarTienda();
        Tienda.tienda = tienda; 
        initialize();
    }
    private void initialize() {
        frame = new JFrame();
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                guardarTienda();
            }
        });
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        frame.getContentPane().setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu MenuComponentes = new JMenu("Componentes");
        menuBar.add(MenuComponentes);

        JMenuItem AgregarComponentes = new JMenuItem("Agregar Componentes");
        AgregarComponentes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AgregarComponentes AgregarComponentes = new AgregarComponentes(null);
                AgregarComponentes.setModal(true);
                AgregarComponentes.setLocationRelativeTo(null);
                AgregarComponentes.setVisible(true);
            }
        });
        MenuComponentes.add(AgregarComponentes);

        JMenuItem ListarComponentes = new JMenuItem("Listar Componentes");
        ListarComponentes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               ListarComponentes ListarComponentes = new ListarComponentes(false);
               ListarComponentes.setModal(true);
               ListarComponentes.setLocationRelativeTo(null);
               ListarComponentes.setVisible(true);
            }
        });
        MenuComponentes.add(ListarComponentes);
        
        JMenu mnClientes = new JMenu("Clientes");
        menuBar.add(mnClientes);
        
        JMenuItem AgregarClt = new JMenuItem("Agregar Cliente");
        AgregarClt.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		RegCliente regclt = new RegCliente(null);
        		regclt.setModal(true);
        		regclt.setLocationRelativeTo(null);
        		regclt.setVisible(true);
        	}
        });
        mnClientes.add(AgregarClt);
        
        JMenuItem ListarClt = new JMenuItem("Listar Clientes");
        ListarClt.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		ListarCliente listclt = new ListarCliente();
        		listclt.setModal(true);
        		listclt.setLocationRelativeTo(null);
        		listclt.setVisible(true);
        	}
        });
        mnClientes.add(ListarClt);
        
        JMenu mnNewMenu = new JMenu("Ventas");
        menuBar.add(mnNewMenu);
        
        JMenuItem mntmNewMenuItem = new JMenuItem("Realizar Venta");
        mntmNewMenuItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		RealizarVenta.misVentas.clear();
        		RealizarVenta vt = new RealizarVenta();
        		vt.setModal(true);
        		vt.setLocationRelativeTo(null);
        		vt.setVisible(true);
        	}
        });
        mnNewMenu.add(mntmNewMenuItem);
        
        JMenuItem mntmNewMenuItem_1 = new JMenuItem("Listado de Ventas");
        mntmNewMenuItem_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		ListarVentas vta = new ListarVentas();
        		vta.setModal(true);
        		vta.setLocationRelativeTo(null);
        		vta.setVisible(true);
        	}
        });
        mnNewMenu.add(mntmNewMenuItem_1);
        
        JMenu MenuAdministracion = new JMenu("Administracion");
        menuBar.add(MenuAdministracion);
        
        JMenu mnNewMenu_6 = new JMenu("Usuarios");
        MenuAdministracion.add(mnNewMenu_6);
        
        JMenuItem mntmNewMenuItem_7 = new JMenuItem("Registrar Usuarios");
        mntmNewMenuItem_7.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		RegistrarUsuario regusuario = new RegistrarUsuario(null);
				regusuario.setModal(true);
				regusuario.setLocationRelativeTo(null);
				regusuario.setVisible(true);
        	}
        });
        mnNewMenu_6.add(mntmNewMenuItem_7);
        
        JMenuItem mntmNewMenuItem_3 = new JMenuItem("Listar Usuario");
        mntmNewMenuItem_3.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		ListarUsuarios listusuario = new ListarUsuarios();
				listusuario.setModal(true);
				listusuario.setLocationRelativeTo(null);
				listusuario.setVisible(true);
        	}
        });
        mnNewMenu_6.add(mntmNewMenuItem_3);
        
        JMenu mnNewMenu_7 = new JMenu("Respaldo");
        MenuAdministracion.add(mnNewMenu_7);
        
        JMenuItem mntmNewMenuItem_5 = new JMenuItem("Realizar Backup");
        mnNewMenu_7.add(mntmNewMenuItem_5);
        
        frame.validate();
    }

    private Tienda cargarTienda() {
        FileInputStream tiendaInput = null;
        ObjectInputStream tiendaRead = null;
        Tienda tienda = null;

        try {
            tiendaInput = new FileInputStream("Tienda.dat");
            tiendaRead = new ObjectInputStream(tiendaInput);
            tienda = (Tienda) tiendaRead.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado, se creara una nueva instancia de Tienda.");
            tienda = Tienda.getInstance(); 
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (tiendaRead != null) {
                    tiendaRead.close();
                }
                if (tiendaInput != null) {
                    tiendaInput.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tienda;
    }

    private void guardarTienda() {
        FileOutputStream tienda2 = null;
        ObjectOutputStream tiendaWrite = null;

        try {
            tienda2 = new FileOutputStream("Tienda.dat");
            tiendaWrite = new ObjectOutputStream(tienda2);
            tiendaWrite.writeObject(Tienda.getInstance());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (tiendaWrite != null) {
                    tiendaWrite.close();
                }
                if (tienda2 != null) {
                    tienda2.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
