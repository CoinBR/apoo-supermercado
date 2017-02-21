package dominio;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class PromocaoTest {
	
	private double delta = 0.0005;
	
	@Test
	public void testAddAndGet1Produto(){
		Produto prod = new Produto("1", "Abaju", 10.00);
        ItemPromocao item = new ItemPromocao(prod, 0);		   
        Promocao promo = new Promocao();
        
        promo.addItem(item);
        
        Assert.assertEquals(prod, promo.getItems().get(0).getProduto());
    }	
	
	@Test
	public void testAddAndGet3Itens(){
		Produto abaju = new Produto("1", "Abaju", 10.00);
		Produto lampada = new Produto("2", "Lampada para Abaju", 3.00);
		
        ItemPromocao itemAbaju = new ItemPromocao(abaju, 0);		   
        ItemPromocao itemLampada = new ItemPromocao(lampada, 0, 0.01);
        
                
        Promocao promo = new Promocao();
        promo.addItem(itemAbaju, 2);
        promo.addItem(itemLampada);
                
        Assert.assertEquals(itemAbaju, promo.getItems().get(0));
        Assert.assertEquals(itemAbaju, promo.getItems().get(1));
        Assert.assertEquals(itemLampada, promo.getItems().get(2));
        
        Assert.assertEquals(3, promo.getItems().size());
    }
	
	@Test
	public void testGetTotalDescontoCom1ItemComDesconto(){
		Produto abaju = new Produto("1", "Abaju", 10.00);
		Produto lampada = new Produto("2", "Lampada para Abaju", 3.00);
		
        ItemPromocao itemAbaju = new ItemPromocao(abaju, 0);		   
        ItemPromocao itemLampada = new ItemPromocao(lampada, 0, 0.01);
        
                
        Promocao promo = new Promocao();
        promo.addItem(itemAbaju, 2);
        promo.addItem(itemLampada);
        
        Assert.assertEquals(2.99, promo.getDescontoTotal(), delta);
	}
	
	@Test
	public void testGetTotalDescontoCom3ItensComDesconto(){
		Produto abaju = new Produto("1", "Abaju", 10.00);
		Produto lampada = new Produto("2", "Lampada para Abaju", 3.00);
		
        ItemPromocao itemAbaju = new ItemPromocao(abaju, 0.5);		   
        ItemPromocao itemLampada = new ItemPromocao(lampada, 0, 0.01);
        
                
        Promocao promo = new Promocao();
        promo.addItem(itemAbaju, 2);
        promo.addItem(itemLampada);

        
        Assert.assertEquals(12.99, promo.getDescontoTotal(), delta);
	}	
	

	@Test
	public void testSeDescontoEhAplicavel0VezesAVenda(){
		Produto abaju = new Produto("1", "Abaju", 10.00);
		Produto lampada = new Produto("2", "Lampada para Abaju", 3.00);
		
        ItemPromocao itemAbaju = new ItemPromocao(abaju, 0.5);		   
        ItemPromocao itemLampada = new ItemPromocao(lampada, 0, 0.01);
        
        Promocao promo = new Promocao();
        promo.addItem(itemAbaju, 2);
        promo.addItem(itemLampada);        
        
                
        Venda venda = new Venda(new Date());
        venda.adicionarItem(abaju, 1);
        venda.adicionarItem(lampada, 1);   
        
        
        Assert.assertEquals(0, promo.quantasVezesEhAplicavel(venda.getItens()), delta);
	}	

	@Test
	public void testSeDescontoEhAplicavel1VezesAVenda(){
		Produto abaju = new Produto("1", "Abaju", 10.00);
		Produto lampada = new Produto("2", "Lampada para Abaju", 3.00);
		
        ItemPromocao itemAbaju = new ItemPromocao(abaju, 0.5);		   
        ItemPromocao itemLampada = new ItemPromocao(lampada, 0, 0.01);
        
        Promocao promo = new Promocao();
        promo.addItem(itemAbaju, 2);
        promo.addItem(itemLampada);        
        
                
        Venda venda = new Venda(new Date());
        venda.adicionarItem(abaju, 2);
        venda.adicionarItem(lampada, 1);
          
               
        Assert.assertEquals(1, promo.quantasVezesEhAplicavel(venda.getItens()), delta);
	}	
	

	@Test
	public void testSeDescontoEhAplicavel30VezesAVenda(){
		Produto abaju = new Produto("1", "Abaju", 10.00);
		Produto lampada = new Produto("2", "Lampada para Abaju", 3.00);
		
        ItemPromocao itemAbaju = new ItemPromocao(abaju, 0.5);		   
        ItemPromocao itemLampada = new ItemPromocao(lampada, 0, 0.01);
        
        Promocao promo = new Promocao();
        promo.addItem(itemAbaju, 2);
        promo.addItem(itemLampada);        
        
                
        Venda venda = new Venda(new Date());
        venda.adicionarItem(abaju, 60);
        venda.adicionarItem(lampada, 30);
          
               
        Assert.assertEquals(30, promo.quantasVezesEhAplicavel(venda.getItens()), delta);
	}	
	
	
	
}
