package dominio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Venda {
	
	private Date data;
	private double desconto;
	private List<ItemVenda> itens = new ArrayList<>();
	
	public Venda(Date data) {
		this.data = data;
	}

	public double getDesconto() {
		return desconto;
	}

	public void setDesconto(double desconto) {
		this.desconto = desconto;
	}

	public Date getData() {
		return data;
	}
	
	public List<ItemVenda> getItens(){
		return new ArrayList<>(itens);
	}
	
	public ItemVenda adicionarItem(Produto p, double quantidade){
		ItemVenda item = new ItemVenda(p, quantidade);
		itens.add(item);
		return item;
	}
	
	public double getTotalBruto(){
		double total = 0.0;
		for(ItemVenda item : itens){
			total += item.getSubtotal();
		}
		return total;
	}
	
	public double getTotalLiquido(){
		return getTotalBruto() - desconto;
	}
}
