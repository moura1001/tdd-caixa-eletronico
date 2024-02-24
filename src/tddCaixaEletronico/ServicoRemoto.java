package tddCaixaEletronico;

public interface ServicoRemoto {
	
	ContaCorrente recuperarConta() throws ServicoRemotoException;
}
