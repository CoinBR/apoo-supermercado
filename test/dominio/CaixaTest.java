package dominio;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class CaixaTest {
	
	private double delta = 0.0005;
	
	@Test(expected = IllegalStateException.class)
	public void testAbrirVenda_1(){
		Caixa c = new Caixa();
		c.abrirVenda();
		c.abrirVenda(); //gerar exce��o
	}
	
	@Test
	public void testEncerrarVenda_1(){
		RepositorioVenda repositorioMock = Mockito.mock(RepositorioVenda.class);
		RepositorioPromocao repositorioPromocaoMock = Mockito.mock(RepositorioPromocao.class);
		Caixa c = new Caixa();
		c.repositorioVenda = repositorioMock;
		c.repositorioPromocao = repositorioPromocaoMock;
		c.abrirVenda();
		c.encerrarVenda();
		c.abrirVenda(); //n�o deve gerar exce��o
	}
	
	@Test(expected = IllegalArgumentException.class)
    public void testAdicionarItem_1(){
        RepositorioProduto repositorioMock = Mockito.mock(RepositorioProduto.class);
        Caixa c = new Caixa();
        c.repositorioProduto = repositorioMock;
        c.abrirVenda();
        c.adicionarItem("2213", 1.0);
    }
	
	@Test
	public void testAdicionarItem_2(){
        String codigoProduto = "1123";
        Produto p = new Produto(codigoProduto, "Produto", 12.80);
        RepositorioProduto repositorioMock = Mockito.mock(RepositorioProduto.class);
        Mockito.when(repositorioMock.getPorCodigo(codigoProduto)).thenReturn(p);
        Caixa c = new Caixa();
        c.repositorioProduto = repositorioMock;
        c.abrirVenda();
        c.adicionarItem(codigoProduto, 1.0);

        List<ItemVenda> itens = c.getItens();

        Assert.assertEquals(1, itens.size());
        Assert.assertEquals(p, itens.get(0).getProduto());
    }
	
	
	
	
	/******************************************************************************************/
	
	
	
	
	
	// Abaixo testaremos o metodo que calcula o desconto da venda baseado nas promocoes aplicaveis.
    // Testaremos os exemplos exigidos para o trabalho de APOO, e mais alguns.
	
	
	
	@Test
	public void testVendaSemDesconto(){
		// Itens da Venda não serão suficientes para ativar nenhuma promoção
		
		
		// ------------------------ Produtos -----------------------------
		
		String codigoFralda = "50";
		String codigoPeitoFrango = "123";
		String codigoDuziaOvos = "321";
		String codigoPneu = "1234";
		
        Produto fralda = new Produto(codigoFralda, "Pacote de Fraldas", 40.00);     
        Produto peitoFrango = new Produto(codigoPeitoFrango, "Filé de Peito de Frango", 10.00);        
        Produto duziaOvos = new Produto(codigoDuziaOvos, "Duzia de Ovos Brancos", 7.00);
        Produto pneu = new Produto(codigoPneu, "Pneu Aro 14", 200.00);  
        
        RepositorioProduto repositorioProdutoMock = Mockito.mock(RepositorioProduto.class);
        
        Mockito.when(repositorioProdutoMock.getPorCodigo(codigoFralda)).thenReturn(fralda);
        Mockito.when(repositorioProdutoMock.getPorCodigo(codigoPeitoFrango)).thenReturn(peitoFrango);
        Mockito.when(repositorioProdutoMock.getPorCodigo(codigoDuziaOvos)).thenReturn(duziaOvos);
        Mockito.when(repositorioProdutoMock.getPorCodigo(codigoPneu)).thenReturn(pneu);
        
        
        
        // --------------------- Itens Promocao ---------------------------        
        
        List<Promocao> listaPromocoes = new ArrayList<Promocao>();
        
        // Comprando uma fralda, leva outra pela metade do preco
        Promocao promo2Fraldas = new Promocao();    
        promo2Fraldas.addItem(new ItemPromocao(fralda, 0));   // 1st Fralda com Valor Integral
        promo2Fraldas.addItem(new ItemPromocao(fralda, 0.5)); // 2nd Fralda com 50% de Desconto
        listaPromocoes.add(promo2Fraldas);
        
        // Comprando 3 peitos de frango leva uma duzia de ovos por mais R$ 0,01
        Promocao promoFrangosOvos = new Promocao(); 
        promoFrangosOvos.addItem(new ItemPromocao(peitoFrango, 0), 3); // 3 Frangos Com Valor Integral
        promoFrangosOvos.addItem(new ItemPromocao(duziaOvos, 0, 0.01)); // 1 duzia de ovos por R$ 0,01
        listaPromocoes.add(promoFrangosOvos);
            
        // Comprando 4 Pneus, leva de graca mais um, para step
        Promocao promo5Pneus = new Promocao(); 
        promo5Pneus.addItem(new ItemPromocao(pneu, 0), 4); // 4 Pneus Com Valor Integral
        promo5Pneus.addItem(new ItemPromocao(pneu, 1));    // 1 Pneu Gratis (para step)
        listaPromocoes.add(promo5Pneus);
        
        RepositorioPromocao repositorioPromocaoMock = Mockito.mock(RepositorioPromocao.class);
        Mockito.when(repositorioPromocaoMock.getAll()).thenReturn(listaPromocoes);
        
   
        
        
        
        // -------------------    Itens Comprados -----------------------------
        
        Caixa caixa = new Caixa();        
        caixa.repositorioProduto = repositorioProdutoMock;
        caixa.repositorioPromocao = repositorioPromocaoMock;
        caixa.repositorioVenda = Mockito.mock(RepositorioVenda.class);
        caixa.abrirVenda();
        
        caixa.adicionarItem(codigoFralda, 1.0);
        caixa.adicionarItem(codigoPeitoFrango, 2.0);
        caixa.adicionarItem(codigoDuziaOvos, 1.0);
        caixa.adicionarItem(codigoPneu, 3.0);     
        
        Assert.assertEquals(0, caixa.calcularDesconto(), delta);
        caixa.encerrarVenda();        
	}
	
	
	
	@Test
	public void testVendaComDescontoDeMetadeDoSegundoItem(){
		// Os Itens da venda serão suficiente para ativar a(s) seguinte(s) promoção(es):
		// Comprando uma Fralda, leva outra pela metade do preço
		
		
		// ------------------------ Produtos -----------------------------
		
		String codigoFralda = "50";
		String codigoPeitoFrango = "123";
		String codigoDuziaOvos = "321";
		String codigoPneu = "1234";
		
        Produto fralda = new Produto(codigoFralda, "Pacote de Fraldas", 40.00);     
        Produto peitoFrango = new Produto(codigoPeitoFrango, "Filé de Peito de Frango", 10.00);        
        Produto duziaOvos = new Produto(codigoDuziaOvos, "Duzia de Ovos Brancos", 7.00);
        Produto pneu = new Produto(codigoPneu, "Pneu Aro 14", 200.00);  
        
        RepositorioProduto repositorioProdutoMock = Mockito.mock(RepositorioProduto.class);
        
        Mockito.when(repositorioProdutoMock.getPorCodigo(codigoFralda)).thenReturn(fralda);
        Mockito.when(repositorioProdutoMock.getPorCodigo(codigoPeitoFrango)).thenReturn(peitoFrango);
        Mockito.when(repositorioProdutoMock.getPorCodigo(codigoDuziaOvos)).thenReturn(duziaOvos);
        Mockito.when(repositorioProdutoMock.getPorCodigo(codigoPneu)).thenReturn(pneu);
        
        
        
        // --------------------- Itens Promocao ---------------------------        
        
        List<Promocao> listaPromocoes = new ArrayList<Promocao>();
        
        // Comprando uma fralda, leva outra pela metade do preco
        Promocao promo2Fraldas = new Promocao();    
        promo2Fraldas.addItem(new ItemPromocao(fralda, 0));   // 1st Fralda com Valor Integral
        promo2Fraldas.addItem(new ItemPromocao(fralda, 0.5)); // 2nd Fralda com 50% de Desconto
        listaPromocoes.add(promo2Fraldas);
        
        // Comprando 3 peitos de frango leva uma duzia de ovos por mais R$ 0,01
        Promocao promoFrangosOvos = new Promocao(); 
        promoFrangosOvos.addItem(new ItemPromocao(peitoFrango, 0), 3); // 3 Frangos Com Valor Integral
        promoFrangosOvos.addItem(new ItemPromocao(duziaOvos, 0, 0.01)); // 1 duzia de ovos por R$ 0,01
        listaPromocoes.add(promoFrangosOvos);
            
        // Comprando 4 Pneus, leva de graca mais um, para step
        Promocao promo5Pneus = new Promocao(); 
        promo5Pneus.addItem(new ItemPromocao(pneu, 0), 4); // 4 Pneus Com Valor Integral
        promo5Pneus.addItem(new ItemPromocao(pneu, 1));    // 1 Pneu Gratis (para step)
        listaPromocoes.add(promo5Pneus);
        
        RepositorioPromocao repositorioPromocaoMock = Mockito.mock(RepositorioPromocao.class);
        Mockito.when(repositorioPromocaoMock.getAll()).thenReturn(listaPromocoes);
        
   
        
        
        
        // -------------------    Itens Comprados -----------------------------
        
        Caixa caixa = new Caixa();        
        caixa.repositorioProduto = repositorioProdutoMock;
        caixa.repositorioPromocao = repositorioPromocaoMock;
        caixa.repositorioVenda = Mockito.mock(RepositorioVenda.class);
        caixa.abrirVenda();
        
        caixa.adicionarItem(codigoFralda, 2.0);     
        
        Assert.assertEquals(fralda.getPreco() * 0.5, caixa.calcularDesconto(), delta);
        caixa.encerrarVenda();        
	}	
	
	
	@Test
	public void testVendaComDescontoEmItemDiferente(){
		// Os Itens da venda serão suficiente para ativar a(s) seguinte(s) promoção(es):
		// Comprando uma 3 peitos de frango leva uma duzia de ovos por R$ 0,01
		
		
		// ------------------------ Produtos -----------------------------
		
		String codigoFralda = "50";
		String codigoPeitoFrango = "123";
		String codigoDuziaOvos = "321";
		String codigoPneu = "1234";
		
        Produto fralda = new Produto(codigoFralda, "Pacote de Fraldas", 40.00);     
        Produto peitoFrango = new Produto(codigoPeitoFrango, "Filé de Peito de Frango", 10.00);        
        Produto duziaOvos = new Produto(codigoDuziaOvos, "Duzia de Ovos Brancos", 7.00);
        Produto pneu = new Produto(codigoPneu, "Pneu Aro 14", 200.00);  
        
        RepositorioProduto repositorioProdutoMock = Mockito.mock(RepositorioProduto.class);
        
        Mockito.when(repositorioProdutoMock.getPorCodigo(codigoFralda)).thenReturn(fralda);
        Mockito.when(repositorioProdutoMock.getPorCodigo(codigoPeitoFrango)).thenReturn(peitoFrango);
        Mockito.when(repositorioProdutoMock.getPorCodigo(codigoDuziaOvos)).thenReturn(duziaOvos);
        Mockito.when(repositorioProdutoMock.getPorCodigo(codigoPneu)).thenReturn(pneu);
        
        
        
        // --------------------- Itens Promocao ---------------------------        
        
        List<Promocao> listaPromocoes = new ArrayList<Promocao>();
        
        // Comprando uma fralda, leva outra pela metade do preco
        Promocao promo2Fraldas = new Promocao();    
        promo2Fraldas.addItem(new ItemPromocao(fralda, 0));   // 1st Fralda com Valor Integral
        promo2Fraldas.addItem(new ItemPromocao(fralda, 0.5)); // 2nd Fralda com 50% de Desconto
        listaPromocoes.add(promo2Fraldas);
        
        // Comprando 3 peitos de frango leva uma duzia de ovos por mais R$ 0,01
        Promocao promoFrangosOvos = new Promocao(); 
        promoFrangosOvos.addItem(new ItemPromocao(peitoFrango, 0), 3); // 3 Frangos Com Valor Integral
        promoFrangosOvos.addItem(new ItemPromocao(duziaOvos, 0, 0.01)); // 1 duzia de ovos por R$ 0,01
        listaPromocoes.add(promoFrangosOvos);
            
        // Comprando 4 Pneus, leva de graca mais um, para step
        Promocao promo5Pneus = new Promocao(); 
        promo5Pneus.addItem(new ItemPromocao(pneu, 0), 4); // 4 Pneus Com Valor Integral
        promo5Pneus.addItem(new ItemPromocao(pneu, 1));    // 1 Pneu Gratis (para step)
        listaPromocoes.add(promo5Pneus);
        
        RepositorioPromocao repositorioPromocaoMock = Mockito.mock(RepositorioPromocao.class);
        Mockito.when(repositorioPromocaoMock.getAll()).thenReturn(listaPromocoes);
        
   
        
        
        
        // -------------------    Itens Comprados -----------------------------
        
        Caixa caixa = new Caixa();        
        caixa.repositorioProduto = repositorioProdutoMock;
        caixa.repositorioPromocao = repositorioPromocaoMock;
        caixa.repositorioVenda = Mockito.mock(RepositorioVenda.class);
        caixa.abrirVenda();
        

        caixa.adicionarItem(codigoPeitoFrango, 3.0);
        caixa.adicionarItem(codigoDuziaOvos, 1.0);    
        
        Assert.assertEquals(duziaOvos.getPreco() - 0.01, caixa.calcularDesconto(), delta);
        caixa.encerrarVenda();        
	}
	
	
	
	
	@Test
	public void testVendaComDescontoNoQuintoItem(){
		// Os Itens da venda serão suficiente para ativar a(s) seguinte(s) promoção(es):
		// Comprando 4 Pneus leva mais um de graça (para step)
		
		
		// ------------------------ Produtos -----------------------------
		
		String codigoFralda = "50";
		String codigoPeitoFrango = "123";
		String codigoDuziaOvos = "321";
		String codigoPneu = "1234";
		
        Produto fralda = new Produto(codigoFralda, "Pacote de Fraldas", 40.00);     
        Produto peitoFrango = new Produto(codigoPeitoFrango, "Filé de Peito de Frango", 10.00);        
        Produto duziaOvos = new Produto(codigoDuziaOvos, "Duzia de Ovos Brancos", 7.00);
        Produto pneu = new Produto(codigoPneu, "Pneu Aro 14", 200.00);  
        
        RepositorioProduto repositorioProdutoMock = Mockito.mock(RepositorioProduto.class);
        
        Mockito.when(repositorioProdutoMock.getPorCodigo(codigoFralda)).thenReturn(fralda);
        Mockito.when(repositorioProdutoMock.getPorCodigo(codigoPeitoFrango)).thenReturn(peitoFrango);
        Mockito.when(repositorioProdutoMock.getPorCodigo(codigoDuziaOvos)).thenReturn(duziaOvos);
        Mockito.when(repositorioProdutoMock.getPorCodigo(codigoPneu)).thenReturn(pneu);
        
        
        
        // --------------------- Itens Promocao ---------------------------        
        
        List<Promocao> listaPromocoes = new ArrayList<Promocao>();
        
        // Comprando uma fralda, leva outra pela metade do preco
        Promocao promo2Fraldas = new Promocao();    
        promo2Fraldas.addItem(new ItemPromocao(fralda, 0));   // 1st Fralda com Valor Integral
        promo2Fraldas.addItem(new ItemPromocao(fralda, 0.5)); // 2nd Fralda com 50% de Desconto
        listaPromocoes.add(promo2Fraldas);
        
        // Comprando 3 peitos de frango leva uma duzia de ovos por mais R$ 0,01
        Promocao promoFrangosOvos = new Promocao(); 
        promoFrangosOvos.addItem(new ItemPromocao(peitoFrango, 0), 3); // 3 Frangos Com Valor Integral
        promoFrangosOvos.addItem(new ItemPromocao(duziaOvos, 0, 0.01)); // 1 duzia de ovos por R$ 0,01
        listaPromocoes.add(promoFrangosOvos);
            
        // Comprando 4 Pneus, leva de graca mais um, para step
        Promocao promo5Pneus = new Promocao(); 
        promo5Pneus.addItem(new ItemPromocao(pneu, 0), 4); // 4 Pneus Com Valor Integral
        promo5Pneus.addItem(new ItemPromocao(pneu, 1));    // 1 Pneu Gratis (para step)
        listaPromocoes.add(promo5Pneus);
        
        RepositorioPromocao repositorioPromocaoMock = Mockito.mock(RepositorioPromocao.class);
        Mockito.when(repositorioPromocaoMock.getAll()).thenReturn(listaPromocoes);
        
   
        
        
        
        // -------------------    Itens Comprados -----------------------------
        
        Caixa caixa = new Caixa();        
        caixa.repositorioProduto = repositorioProdutoMock;
        caixa.repositorioPromocao = repositorioPromocaoMock;
        caixa.repositorioVenda = Mockito.mock(RepositorioVenda.class);
        caixa.abrirVenda();
        

        caixa.adicionarItem(codigoPneu, 5.0);   
        
        Assert.assertEquals(pneu.getPreco(), caixa.calcularDesconto(), delta);
        caixa.encerrarVenda();        
	}
	
	
	
	
	@Test
	public void testVendaCom3PromocoesSimultaneasAplicadasVariasVezes(){
		// Os Itens da venda serão suficiente para ativar a(s) seguinte(s) promoção(es):
		//
        // (1x) Comprando uma fralda, leva outra pela metade do preco
		// (20x) Comprando uma 3 peitos de frango leva uma duzia de ovos por R$ 0,01
		// (300x) Comprando 4 Pneus leva mais um de graça (para step)
		
		
		// ------------------------ Produtos -----------------------------
		
		String codigoFralda = "50";
		String codigoPeitoFrango = "123";
		String codigoDuziaOvos = "321";
		String codigoPneu = "1234";
		
        Produto fralda = new Produto(codigoFralda, "Pacote de Fraldas", 40.00);     
        Produto peitoFrango = new Produto(codigoPeitoFrango, "Filé de Peito de Frango", 10.00);        
        Produto duziaOvos = new Produto(codigoDuziaOvos, "Duzia de Ovos Brancos", 7.00);
        Produto pneu = new Produto(codigoPneu, "Pneu Aro 14", 200.00);  
        
        RepositorioProduto repositorioProdutoMock = Mockito.mock(RepositorioProduto.class);
        
        Mockito.when(repositorioProdutoMock.getPorCodigo(codigoFralda)).thenReturn(fralda);
        Mockito.when(repositorioProdutoMock.getPorCodigo(codigoPeitoFrango)).thenReturn(peitoFrango);
        Mockito.when(repositorioProdutoMock.getPorCodigo(codigoDuziaOvos)).thenReturn(duziaOvos);
        Mockito.when(repositorioProdutoMock.getPorCodigo(codigoPneu)).thenReturn(pneu);
        
        
        
        // --------------------- Itens Promocao ---------------------------        
        
        List<Promocao> listaPromocoes = new ArrayList<Promocao>();
        
        // Comprando uma fralda, leva outra pela metade do preco
        Promocao promo2Fraldas = new Promocao();    
        promo2Fraldas.addItem(new ItemPromocao(fralda, 0));   // 1st Fralda com Valor Integral
        promo2Fraldas.addItem(new ItemPromocao(fralda, 0.5)); // 2nd Fralda com 50% de Desconto
        listaPromocoes.add(promo2Fraldas);
        
        // Comprando 3 peitos de frango leva uma duzia de ovos por mais R$ 0,01
        Promocao promoFrangosOvos = new Promocao(); 
        promoFrangosOvos.addItem(new ItemPromocao(peitoFrango, 0), 3); // 3 Frangos Com Valor Integral
        promoFrangosOvos.addItem(new ItemPromocao(duziaOvos, 0, 0.01)); // 1 duzia de ovos por R$ 0,01
        listaPromocoes.add(promoFrangosOvos);
            
        // Comprando 4 Pneus, leva de graca mais um, para step
        Promocao promo5Pneus = new Promocao(); 
        promo5Pneus.addItem(new ItemPromocao(pneu, 0), 4); // 4 Pneus Com Valor Integral
        promo5Pneus.addItem(new ItemPromocao(pneu, 1));    // 1 Pneu Gratis (para step)
        listaPromocoes.add(promo5Pneus);
        
        RepositorioPromocao repositorioPromocaoMock = Mockito.mock(RepositorioPromocao.class);
        Mockito.when(repositorioPromocaoMock.getAll()).thenReturn(listaPromocoes);
        
   
        
        
        
        // -------------------    Itens Comprados -----------------------------
        
        Caixa caixa = new Caixa();        
        caixa.repositorioProduto = repositorioProdutoMock;
        caixa.repositorioPromocao = repositorioPromocaoMock;
        caixa.repositorioVenda = Mockito.mock(RepositorioVenda.class);
        caixa.abrirVenda();
              
        caixa.adicionarItem(codigoFralda, 2.0 * 1);
        caixa.adicionarItem(codigoPeitoFrango, 3.0 * 20);
        caixa.adicionarItem(codigoDuziaOvos, 1.0 * 20);
        caixa.adicionarItem(codigoPneu, 5.0 * 300);  
        
        double descontoEsperado = 0.0;
        descontoEsperado = fralda.getPreco() * 0.5 * 1;
        descontoEsperado += (duziaOvos.getPreco() - 0.01) * 20;
        descontoEsperado += pneu.getPreco() * 300;
        
        Assert.assertEquals(descontoEsperado, caixa.calcularDesconto(), delta);
        caixa.encerrarVenda();        
	}
	
}
