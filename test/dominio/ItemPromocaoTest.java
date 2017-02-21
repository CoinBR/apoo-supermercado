package dominio;

import org.junit.Assert;
import org.junit.Test;

public class ItemPromocaoTest {
	
	private double delta = 0.0005;
	
	@Test
	public void testSemDesconto(){
        Produto colchaoOriginal = new Produto("1", "Colchao Inflavel", 100.00);
        ItemPromocao colchaoSemDesconto = new ItemPromocao(colchaoOriginal, 0);
        
        Assert.assertEquals(colchaoOriginal.getPreco(), colchaoSemDesconto.getprecoNovo(), delta);
        Assert.assertEquals(0.00, colchaoSemDesconto.getDescontoValor(), delta);
    }	
	
	@Test
	public void testMetadeDesconto(){
        Produto colchaoOriginal = new Produto("1", "Colchao Inflavel", 100.00);
        ItemPromocao colchaoSemDesconto = new ItemPromocao(colchaoOriginal, 0.5);
        
        Assert.assertEquals(50.00, colchaoSemDesconto.getprecoNovo(), delta);
        Assert.assertEquals(50.00, colchaoSemDesconto.getDescontoValor(), delta);
    }	
	
	@Test
	public void test30PorCentoDesconto(){
        Produto colchaoOriginal = new Produto("1", "Colchao Inflavel", 100.00);
        ItemPromocao colchaoSemDesconto = new ItemPromocao(colchaoOriginal, 0.3);
        
        Assert.assertEquals(70.00, colchaoSemDesconto.getprecoNovo(), delta);
        Assert.assertEquals(30.00, colchaoSemDesconto.getDescontoValor(), delta);
    }
	
	
	
	@Test
	public void testValorPrefixado0_01(){
        Produto colchaoOriginal = new Produto("1", "Colchao Inflavel", 100.00);
        ItemPromocao colchaoSemDesconto = new ItemPromocao(colchaoOriginal, 0, 0.01);
        
        Assert.assertEquals(0.01, colchaoSemDesconto.getprecoNovo(), delta);
        Assert.assertEquals(99.99, colchaoSemDesconto.getDescontoValor(), delta);
    }	
	
	@Test
	public void testValorPrefixado70_75(){
        Produto colchaoOriginal = new Produto("1", "Colchao Inflavel", 100.00);
        ItemPromocao colchaoSemDesconto = new ItemPromocao(colchaoOriginal, 0, 70.75);
        
        Assert.assertEquals(70.75, colchaoSemDesconto.getprecoNovo(), delta);
        Assert.assertEquals(29.25, colchaoSemDesconto.getDescontoValor(), delta);
    }
	
	
	// Teste o levantamento de erros por argumentos invalidos:
	
	@Test(expected = IllegalArgumentException.class)
	public void testErroPorcentagemEValorPrefixadoJuntos(){
        Produto colchaoOriginal = new Produto("1", "Colchao Inflavel", 100.00);
        new ItemPromocao(colchaoOriginal, 0.5, 0.01);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testErroPorcentagemAbaixoDeZero(){
        Produto colchaoOriginal = new Produto("1", "Colchao Inflavel", 100.00);
        new ItemPromocao(colchaoOriginal, -0.0000001);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testErroPorcentagemAcimaDe100PorCento(){
        Produto colchaoOriginal = new Produto("1", "Colchao Inflavel", 100.00);
        new ItemPromocao(colchaoOriginal, 1.000001);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testErroValorPrefixadoAbaixoDeZero(){
        Produto colchaoOriginal = new Produto("1", "Colchao Inflavel", 100.00);
        new ItemPromocao(colchaoOriginal, 0, -0.000001);
	}		
	
	@Test(expected = IllegalArgumentException.class)
	public void testErroValorPrefixadoAcimaDoValorOriginalDoProduto(){
        Produto colchaoOriginal = new Produto("1", "Colchao Inflavel", 100.00);
        new ItemPromocao(colchaoOriginal, 0, 100.01);
	}		

	
}
