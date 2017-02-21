package dominio;

import java.util.List;

public interface RepositorioPromocao {
	
	//Deve retornar nulo caso o produto n�o seja encontrado
	Promocao getPorCodigo(String codigoPromocao);
	
	List<Promocao> getAll();	
}
