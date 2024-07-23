package Visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import logico.Cliente;
import logico.Tienda;
import logico.Venta;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ListarVentas extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private Venta vta = null;
	private JButton btnAbrir;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ListarVentas dialog = new ListarVentas();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ListarVentas() {
		setTitle("Ventas");
		setBounds(100, 100, 679, 591);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(null), "Listado de Ventas", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
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
					btnAbrir.setEnabled(true);
					vta = Tienda.getInstance().getVentaByCodigo(table.getValueAt(index, 0).toString());
				}else {
					btnAbrir.setEnabled(false);
					vta = null;
				}

			}
		});
		scrollPane.setViewportView(table);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				
				btnAbrir = new JButton("Abrir");
				btnAbrir.setEnabled(false);
				btnAbrir.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						DetalleVenta dV = new DetalleVenta(vta);
						dV.setModal(true);
						dV.setLocationRelativeTo(null);
						dV.setVisible(true);
					}
				});
				buttonPane.add(btnAbrir);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		cargarVentas();
	}
	
	private void cargarVentas() {

		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Codigo");
		model.addColumn("Cliente");
		model.addColumn("Fecha");
		model.addColumn("Total");

		for (Venta vta : Tienda.getInstance().getMisVentas()) {
			model.addRow(new Object[] { vta.getCodigo(),vta.getMiCliente().getCedula(),
					vta.getFecha(),vta.getTotal()});
		}
		table.setModel(model);
	}
}
