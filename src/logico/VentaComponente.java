package logico;

public class VentaComponente {

	private Componente comp;
	private int qty;
	
	public VentaComponente(Componente comp, int qty) {
		super();
		this.comp = comp;
		this.qty = qty;
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

}
