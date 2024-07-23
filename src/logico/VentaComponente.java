package logico;

import java.io.Serializable;

public class VentaComponente implements Serializable {
    private static final long serialVersionUID = 1L;

	private Componente comp;
	private int qty;
	
	public VentaComponente(Componente comp, int qty) {
		super();
		this.comp = comp;
		this.qty = qty;
	}
	
	public double getSubtotal() {
        return comp.getPrecio() * qty;
    }

	public Componente getComp() {
		return comp;
	}

	public void setComp(Componente comp) {
		this.comp = comp;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}
	
	public VentaComponente clone() {
        return new VentaComponente(this.comp, this.qty);
    }
	
	public String getDetalle() {
        return qty + " x " + comp.getNombre() + " @ $" + String.format("%.2f", getSubtotal());
    }

}
