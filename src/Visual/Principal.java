package Visual;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import logico.Client;
import logico.Cliente;
import logico.Tienda;
import logico.User;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Principal extends JFrame {
    private Tienda tienda;

    public Principal() {
        tienda = cargarTienda();
        Tienda.tienda = tienda;
        initialize();
    }

    private void initialize() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                guardarTienda();
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu MenuComponentes = new JMenu("Componentes");
        menuBar.add(MenuComponentes);

        JMenuItem ListarComponentes = new JMenuItem("Listar Componentes");
        ListarComponentes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ListarComponentes listarComponentes = new ListarComponentes(Login.getLoginUser(), false);
                listarComponentes.setModal(true);
                listarComponentes.setLocationRelativeTo(null);
                listarComponentes.setVisible(true);
            }
        });
        MenuComponentes.add(ListarComponentes);
        
        JMenu mnClientes = new JMenu("Clientes");
        menuBar.add(mnClientes);
        
        JMenuItem ListarClt = new JMenuItem("Listar Clientes");
        ListarClt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ListarCliente listarClientes = new ListarCliente(Login.getLoginUser());
                listarClientes.setModal(true);
                listarClientes.setLocationRelativeTo(null);
                listarClientes.setVisible(true);
            }
        });
        mnClientes.add(ListarClt);

        JMenu mnNewMenu = new JMenu("Ventas");
        menuBar.add(mnNewMenu);
        
        JMenuItem mntmNewMenuItem_1 = new JMenuItem("Listado de Ventas");
        mntmNewMenuItem_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ListarVentas listarVentas = new ListarVentas(Login.getLoginUser());
                listarVentas.setModal(true);
                listarVentas.setLocationRelativeTo(null);
                listarVentas.setVisible(true);
            }
        });
        mnNewMenu.add(mntmNewMenuItem_1);
        
        User user = Login.getLoginUser();
        if (user != null) {
            if (user.getTipo().equals("Administrador")) {
                addAdminMenus(menuBar, MenuComponentes, mnClientes, mnNewMenu);
            } else if (user.getTipo().equals("Privilegiado")) {
                addPrivilegedMenus(MenuComponentes, mnClientes, mnNewMenu);
            }
        }
        
        validate();
        setVisible(true);
    }

    private void addAdminMenus(JMenuBar menuBar, JMenu MenuComponentes, JMenu mnClientes, JMenu mnNewMenu) {
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
                ListarUsuarios listarUsuarios = new ListarUsuarios();
                listarUsuarios.setModal(true);
                listarUsuarios.setLocationRelativeTo(null);
                listarUsuarios.setVisible(true);
            }
        });
        mnNewMenu_6.add(mntmNewMenuItem_3);
        
        JMenu mnNewMenu_7 = new JMenu("Respaldo");
        MenuAdministracion.add(mnNewMenu_7);
        
        JMenuItem mntmNewMenuItem_5 = new JMenuItem("Realizar Backup");
        mntmNewMenuItem_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(null, "Estas seguro(a) que desea hacer el respaldo ",
						"Confirmacion", JOptionPane.OK_CANCEL_OPTION);
				if (option == JOptionPane.OK_OPTION) {
					Client.enviarCopiaRespaldo();
				}
			}
		});
        mnNewMenu_7.add(mntmNewMenuItem_5);

        JMenuItem AgregarComponentes = new JMenuItem("Agregar Componentes");
        AgregarComponentes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AgregarComponentes agregarComponentes = new AgregarComponentes(null);
                agregarComponentes.setModal(true);
                agregarComponentes.setLocationRelativeTo(null);
                agregarComponentes.setVisible(true);
            }
        });
        MenuComponentes.add(AgregarComponentes);
        
        JMenuItem AgregarClt = new JMenuItem("Agregar Cliente");
        AgregarClt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RegCliente regCliente = new RegCliente(null);
                regCliente.setModal(true);
                regCliente.setLocationRelativeTo(null);
                regCliente.setVisible(true);
            }
        });
        mnClientes.add(AgregarClt);
        
        JMenuItem mntmNewMenuItem = new JMenuItem("Realizar Venta");
        mntmNewMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RealizarVenta realizarVenta = new RealizarVenta(Login.getLoginUser(), true);
                realizarVenta.setModal(true);
                realizarVenta.setLocationRelativeTo(null);
                realizarVenta.setVisible(true);
            }
        });
        mnNewMenu.add(mntmNewMenuItem);
    }

    private void addPrivilegedMenus(JMenu MenuComponentes, JMenu mnClientes, JMenu mnNewMenu) {
        JMenuItem AgregarComponentes = new JMenuItem("Agregar Componentes");
        AgregarComponentes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AgregarComponentes agregarComponentes = new AgregarComponentes(null);
                agregarComponentes.setModal(true);
                agregarComponentes.setLocationRelativeTo(null);
                agregarComponentes.setVisible(true);
            }
        });
        MenuComponentes.add(AgregarComponentes);
        
        JMenuItem AgregarClt = new JMenuItem("Agregar Cliente");
        AgregarClt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RegCliente regCliente = new RegCliente(null);
                regCliente.setModal(true);
                regCliente.setLocationRelativeTo(null);
                regCliente.setVisible(true);
            }
        });
        mnClientes.add(AgregarClt);
        
        JMenuItem mntmNewMenuItem = new JMenuItem("Realizar Venta");
        mntmNewMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RealizarVenta realizarVenta = new RealizarVenta(Login.getLoginUser(), true);
                realizarVenta.setModal(true);
                realizarVenta.setLocationRelativeTo(null);
                realizarVenta.setVisible(true);
            }
        });
        mnNewMenu.add(mntmNewMenuItem);
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
