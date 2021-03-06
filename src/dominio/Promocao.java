package dominio;

import java.util.ArrayList;
import java.util.List;


public class Promocao {
	
	/* Promoção, definida por uma lista de "ItemPromocao". Os quais são produtos com ou sem desconto.
	 * 
	 * Após comprar alguns itens em seu valor normal, o cliente conseguirá descontos em produtos posteriores.
	 * 
	 * Tais descontos podem ser dados como:
	 * 		Porcentagem do valor dessa instância do produto (ex: 50 %)
	 * 		Prefixando o valor dessa instância do produto (ex: R$ 0,01)
	 * 
	 * Exemplos:
	 * 
	 * (Ao comprar 2 Fraldas, a segunda sai por 50% do valor)
	 * new Promocao();
	 * Promocao.AddItem()(new ItemPromocao(fralda, 0));
	 * Promocao.AddItem()(new ItemPromocao(fralda, 0.5));
	 * 
	 * (Ao comprar 3 sabonetes, o cliente pode levar uma duzia de ovos por R$ 0,01
     * new Promocao();
	 * Promocao.AddItem()(new ItemPromocao(fralda, 0), 3);
	 * Promocao.AddItem()(new ItemPromocao(fralda, 0, 0.01));
	 */
	
	private List<ItemPromocao> itens = new ArrayList<>();
	
	public Promocao() {
	}

	public void addItem(ItemPromocao item){
		itens.add(item);
	}
	

	public void addItem(ItemPromocao item, int quantidade){
		for(int i = 0; i < quantidade; i++){
			itens.add(item);
		}
	}	

	public void addItem(ItemPromocao item, double quantidade){
		for(double i = 0; i < quantidade; i++){
			itens.add(item);
		}
	}
	
	public int quantasVezesEhAplicavel(List<ItemVenda> itensVenda){
		// Verifica o número de vezes que o desconto pode ser aplicado em uma lista de compra.
		// Retorna 0, caso o cliente não tenho comprado os itens necessários para ativar a promoção
		
		
		// Normaliza Lista de Produtos da Venda, para facilitar compracao
		List<Produto> listaVendaNormalizada = new ArrayList<Produto>(); 
		
		for (ItemVenda item: itensVenda){
			for(int i = 0; i < item.getQuantidade(); i++){
				listaVendaNormalizada.add(item.getProduto());
			}
		}
		
			
		
		int nVezesAplicavel = 0;
		int nItensEncontrados = 0;
		
		int nItensPromocao = this.getItems().size();				
		List<ItemPromocao> listaItensPromocao = new ArrayList<ItemPromocao>(this.getItems());
		
		while(!listaVendaNormalizada.isEmpty() && !listaItensPromocao.isEmpty()){

			Produto nextProd = listaItensPromocao.get(0).getProduto();
			listaItensPromocao.remove(0);

			
			
			if (listaVendaNormalizada.contains(nextProd)){
				nItensEncontrados++;
				listaVendaNormalizada.remove(nextProd);
				
				if (nItensEncontrados == nItensPromocao){
					nVezesAplicavel++;
					nItensEncontrados = 0;
					listaItensPromocao = new ArrayList<ItemPromocao>(this.getItems());
				}
			}
			
		}
		
		return nVezesAplicavel;	
	}


	public double getDescontoTotal(){
		double total = 0.0;
		
		for(ItemPromocao item: this.itens){
			total += item.getDescontoValor();
		}
		
		return total;
	}
	
	public List<ItemPromocao> getItems(){
		return this.itens;
	}
}