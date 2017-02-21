package dominio;

public interface RepositorioProduto {
	
	//Deve retornar nulo caso o produto não seja encontrado
	Produto getPorCodigo(String codigoProduto);
	
}
