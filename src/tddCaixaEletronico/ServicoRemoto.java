package tddCaixaEletronico;

public interface ServicoRemoto {
	
	ContaCorrente recuperarConta() throws ServicoRemotoException;

	void persistirConta(ContaCorrente conta) throws ServicoRemotoException;
}
