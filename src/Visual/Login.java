package Visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import logico.Tienda;
import logico.User;
import logico.Venta;

public class Login extends JFrame {

    private JPanel contentPane;
    private JTextField txtusuario;
    private JPasswordField txtpass;
    private static User loginUser; 

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                FileInputStream tienda;
                FileOutputStream tienda2;
                ObjectInputStream tiendaRead;
                ObjectOutputStream tiendaWrite;
                try {
                    tienda = new FileInputStream("Tienda.dat");
                    tiendaRead = new ObjectInputStream(tienda);
                    Tienda temp = (Tienda) tiendaRead.readObject();
                    Tienda.setTienda(temp);
                    tienda.close();
                    tiendaRead.close();
                } catch (FileNotFoundException e) {
                    try {
                        tienda2 = new FileOutputStream("Tienda.dat");
                        tiendaWrite = new ObjectOutputStream(tienda2);
                        User admin = new User("Administrador", "Admin", "Admin");
                        Tienda.getInstance().agregarUsuario(admin);
                        tiendaWrite.writeObject(Tienda.getInstance());
                        tienda2.close();
                        tiendaWrite.close();
                    } catch (FileNotFoundException e1) {

                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Login frame = new Login();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Login() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 331, 211);
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        setLocationRelativeTo(null);
        setResizable(false);
        contentPane.setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        contentPane.add(panel, BorderLayout.CENTER);
        panel.setLayout(null);

        txtusuario = new JTextField();
        txtusuario.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (txtusuario.getText().equalsIgnoreCase("Nombre de usuario...")) {
                    txtusuario.setText("");
                    txtusuario.setForeground(Color.black);
                }
                if (String.valueOf(txtpass.getPassword()).isEmpty()) {
                    txtpass.setText("*********");
                    txtpass.setForeground(Color.gray);
                }
            }
        });
        txtusuario.setForeground(Color.BLACK);
        txtusuario.setBounds(109, 25, 179, 22);
        panel.add(txtusuario);
        txtusuario.setColumns(10);

        JLabel lblusuario = new JLabel("Usuario");
        lblusuario.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblusuario.setBounds(28, 25, 73, 16);
        panel.add(lblusuario);

        txtpass = new JPasswordField();
        txtpass.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (String.valueOf(txtpass.getPassword()).equals("*********")) {
                    txtpass.setText("");
                    txtpass.setForeground(Color.black);
                }
                if (txtusuario.getText().isEmpty()) {
                    txtusuario.setText("Nombre de usuario...");
                    txtusuario.setForeground(Color.gray);
                }
            }
        });
        txtpass.setText("*********");
        txtpass.setForeground(Color.LIGHT_GRAY);
        txtpass.setBounds(109, 66, 179, 22);
        txtpass.setColumns(10);
        panel.add(txtpass);

        JLabel lblPassword = new JLabel("Contraseña");
        lblPassword.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblPassword.setBounds(16, 66, 85, 16);
        panel.add(lblPassword);
        
        JButton btnNewButton = new JButton("Iniciar Sesión");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loginUser = Tienda.getInstance().confirmLoginInfo(txtusuario.getText(), String.valueOf(txtpass.getPassword()));
                if (loginUser != null) {
                    Principal frame = new Principal();
                    dispose();
                    frame.setVisible(true);
                    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                } else {
                    JOptionPane.showMessageDialog(null, "El usuario ingresado no es válido", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnNewButton.setBounds(97, 113, 115, 21);
        panel.add(btnNewButton);

        loadCodes();
    }

    public static User getLoginUser() {
        return loginUser;
    }

    public void loadCodes() {
        Venta.setCodigoVenta(Tienda.getInstance().getMisVentas().size() + 1);
    }
}
