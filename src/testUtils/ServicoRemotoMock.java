package testUtils;

import tddCaixaEletronico.ContaCorrente;
import tddCaixaEletronico.ServicoRemoto;
import tddCaixaEletronico.ServicoRemotoException;

public class ServicoRemotoMock implements ServicoRemoto {

	private FalhaServicoRemoto falhaFuncionamento;
	private ContaCorrente contaCorrente;

	@Override
	public ContaCorrente recuperarConta() throws ServicoRemotoException {
		if(falhaFuncionamento != null)
			throw new ServicoRemotoException(falhaFuncionamento.causaDaFalha());
		
		return contaCorrente;
	}

	public void configurarFalhaDeFuncionamento(FalhaServicoRemoto falhaFuncionamento) {
		this.falhaFuncionamento = falhaFuncionamento;
	}

	public void configurarContaCorrente(ContaCorrente contaCorrente) {
		this.contaCorrente = contaCorrente;
	}

	@Override
	public void persistirConta(ContaCorrente conta) throws ServicoRemotoException {
		contaCorrente = conta;
	}

}
