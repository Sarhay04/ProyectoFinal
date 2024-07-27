package logico;

import java.time.LocalDate;
import java.util.ArrayList;
import java.io.Serializable;

public class Venta implements Serializable {
    private static final long serialVersionUID = 1L;
	
	private String codigo;
	private ArrayList<VentaComponente> misComps;
	private Cliente miCliente;
	private double total;
	private LocalDate fecha;
	public static int codigoVenta = 1;
	
	public Venta(String codigo, ArrayList<VentaComponente> misComps, Cliente miCliente, double total, LocalDate fecha) {
		super();
		this.codigo = codigo;
		this.misComps = new ArrayList<>();
	        for (VentaComponente vc : new ArrayList<>(misComps)) {
	            this.misComps.add(vc.clone());
	        }
		this.miCliente = miCliente;
		this.total = total;
		this.fecha = fecha;
	}
	
	public void addComponente(Componente componente, int cantidad) {
		VentaComponente ventaComp = new VentaComponente(componente, cantidad);
        misComps.add(ventaComp);
        total += componente.precio * cantidad;
    }
	
	public String generarFactura() {
        StringBuilder factura = new StringBuilder();
        factura.append("Código de Venta: ").append(codigo).append("\n");
        factura.append("Fecha: ").append(fecha).append("\n\n");
        factura.append("Cliente: ").append(miCliente.getNombre()).append("\n\n");
        factura.append("Detalles de la Venta:\n");
        factura.append(misComps.size());

        for (VentaComponente vc : misComps) {
        	factura.append(vc.getDetalle()).append("\n");
        }

        factura.append("\nTotal: $").append(String.format("%.2f", total)).append("\n");

        return factura.toString();
    }
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public ArrayList<VentaComponente> getMisComps() {
		return misComps;
	}
	public void setMisComps(ArrayList<VentaComponente> misComps) {
		this.misComps = misComps;
	}
	public Cliente getMiCliente() {
		return miCliente;
	}
	public void setMiCliente(Cliente miCliente) {
		this.miCliente = miCliente;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public LocalDate getFecha() {
		return fecha;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public static int getCodigoVenta() {
		return codigoVenta;
	}

	public static void setCodigoVenta(int codigoVenta) {
		Venta.codigoVenta = codigoVenta;
	}
	

}
