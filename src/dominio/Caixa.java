package dominio;

import java.util.Date;
import java.util.List;

public class Caixa {
	
	private Venda vendaCorrente;
	RepositorioVenda repositorioVenda;
	RepositorioProduto repositorioProduto;
	RepositorioPromocao repositorioPromocao;
	
	public void abrirVenda(){
		if(vendaCorrente != null)
			throw new IllegalStateException("J� existe uma venda em curso");
		
		vendaCorrente = new Venda(new Date());
	}
	
	public double calcularDesconto(){
		
		double descontoTotal = 0.0;
		
		for (Promocao promocao: this.repositorioPromocao.getAll()){
			descontoTotal += promocao.getDescontoTotal() * promocao.quantasVezesEhAplicavel(this.getItens());
		}
		
		return descontoTotal;
	}
	
	public double getTotalSemDesconto(){
		return vendaCorrente.getTotalBruto();
	}
	
	public void encerrarVenda(){
		double desconto = calcularDesconto();
		vendaCorrente.setDesconto(desconto);
		repositorioVenda.salvar(vendaCorrente);
		vendaCorrente = null;
	}
	
	public void adicionarItem(String codigoProduto, double quantidade){
		if(vendaCorrente == null)
			throw new IllegalStateException("N�o h� venda corrente");
		Produto produto = repositorioProduto.getPorCodigo(codigoProduto);
		if(produto == null)
			throw new IllegalArgumentException("Produto n�o encontrado");
		vendaCorrente.adicionarItem(produto, quantidade);
	}
	
	public List<ItemVenda> getItens(){
		if(vendaCorrente == null)
			throw new IllegalStateException("N�o h� venda corrente");
		return vendaCorrente.getItens();
	}
}
