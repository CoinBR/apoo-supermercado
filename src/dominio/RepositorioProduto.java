package dominio;

public interface RepositorioProduto {
	
	//Deve retornar nulo caso o produto n�o seja encontrado
	Produto getPorCodigo(String codigoProduto);
	
}
