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

    /**
     * Create the application.
     */
    public Principal() {
        tienda = Tienda.getInstance();
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
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
                AgregarComponentes AgregarComponentes = new AgregarComponentes();
                AgregarComponentes.setModal(true);
                AgregarComponentes.setLocationRelativeTo(null);
                AgregarComponentes.setVisible(true);
            }
        });
        MenuComponentes.add(AgregarComponentes);

        JMenuItem ListarComponentes = new JMenuItem("Listar Componentes");
        ListarComponentes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               ListarComponentes ListarComponentes = new ListarComponentes();
               ListarComponentes.setModal(true);
               ListarComponentes.setLocationRelativeTo(null);
               ListarComponentes.setVisible(true);
            }
        });
        MenuComponentes.add(ListarComponentes);
        
        frame.validate();
    }
}
