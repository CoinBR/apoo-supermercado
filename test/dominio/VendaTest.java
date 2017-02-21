package dominio;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class VendaTest {
	
	@Test
	public void testGetTotalBruto(){
		Produto p1 = new Produto("1", "p1", 5.50);
		double q1 = 3.0;
		Produto p2 = new Produto("2", "p2", 2.20);
		double q2 = 1.0;
		Venda v = new Venda(new Date());
		v.adicionarItem(p1, q1);
		v.adicionarItem(p2, q2);
		
		double totalObtido = v.getTotalBruto();
		double totalCorreto = q1 * p1.getPreco() + q2 * p2.getPreco();
		
		Assert.assertEquals(totalCorreto, totalObtido, 0.0001);
	}
	
	@Test
	public void testGetTotalLiquido(){
        double desconto = 2.30;
        Produto p1 = new Produto("1", "Produto 1", 5.50);
        double q1 = 3.0;
        Produto p2 = new Produto("2", "Produto 2", 2.20);
        double q2 = 1.0;
        Venda v = new Venda(new Date());
        v.adicionarItem(p1, q1);
        v.adicionarItem(p2, q2);
        v.setDesconto(desconto);

        double totalObtido = v.getTotalLiquido();
        double totalCorreto = q1 * p1.getPreco() + q2 * p2.getPreco() - desconto;

        Assert.assertEquals(totalCorreto, totalObtido, 0.0001);
    }
}
