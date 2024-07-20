package logico;

import java.time.LocalDate;
import java.util.ArrayList;

public class Venta {
	
	private String codigo;
	private ArrayList<VentaComponente> misComps;
	private Cliente miCliente;
	private double total;
	private LocalDate fecha;
	public static int codigoVenta = 1;
	
	public Venta(String codigo, ArrayList<VentaComponente> misComps, Cliente miCliente, double total, LocalDate fecha) {
		super();
		this.codigo = codigo;
		this.misComps = misComps;
		this.miCliente = miCliente;
		this.total = total;
		this.fecha = fecha;
	}
	
	public void addComponente(Componente componente, int cantidad) {
		VentaComponente ventaComp = new VentaComponente(componente, cantidad);
        misComps.add(ventaComp);
        total += componente.precio * cantidad;
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
	

}
