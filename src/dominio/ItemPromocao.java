package dominio;


public class ItemPromocao {
	
	/* Produto com valor promocional.
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
	
	private Produto produto;
	
	private double precoNovo;
	
	
	public ItemPromocao(Produto produto, double descontoPorcentagem, double precoPrefixado) {
		// Novo Item recebe um novo valor prefixado. Exemplo: R$ 0,01
		
		if(descontoPorcentagem != 0){
			throw new IllegalArgumentException(" argumento 'descontoPorcetagem' precisa ser '0' "
					+ "quando o novo preço do item for prefixado pelo argumento 'precoPrefixado'");
		}
		
		if(precoPrefixado < 0 || precoPrefixado > produto.getPreco()){
			throw new IllegalArgumentException(
					"'precoPrefixado' precisa ser um valor positivo, "
					+ "menor ou igual ao valor original do produto. "
					+ "[precoPrefixado == " + precoPrefixado + "]");			
		}
		
		this.produto = produto;
		this.precoNovo = precoPrefixado;	
	}
	
	public ItemPromocao(Produto produto, double descontoPorcentagem) {
		// Porcentagem de desconto no valor do item.
		
		if(descontoPorcentagem < 0 || descontoPorcentagem > 1){
			throw new IllegalArgumentException(
					"'descontoPorcentagem' precisa ser um valor positivo entre 0.0 e 1.0 "
					+ "[desconto == " + descontoPorcentagem + "]");			
		}
		
		this.produto = produto;
		this.precoNovo = this.calcNovoValorComDescontoPorPorcentagem(descontoPorcentagem);
	}
	
	
	private double calcNovoValorComDescontoPorPorcentagem(double descontoPorcentagem){
		return this.produto.getPreco() * (1 - descontoPorcentagem);
	}
	
	
	public double getprecoNovo() {
		return this.precoNovo;
	}

	public double getDescontoValor() {		
		return this.produto.getPreco() - this.precoNovo;
	}	
	
	public Produto getProduto(){
		return this.produto;
	}
	
}