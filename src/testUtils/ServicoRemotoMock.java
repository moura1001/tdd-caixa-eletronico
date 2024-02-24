package testUtils;

import tddCaixaEletronico.ContaCorrente;
import tddCaixaEletronico.ServicoRemoto;
import tddCaixaEletronico.ServicoRemotoException;

public class ServicoRemotoMock implements ServicoRemoto {

	private FalhaServicoRemoto falhaFuncionamento;
	private ContaCorrente contaCorrente;

	@Override
	public ContaCorrente recuperarConta() throws ServicoRemotoException {
		if(FalhaServicoRemoto.RECUPERAR_CONTA.equals(falhaFuncionamento))
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
		if(FalhaServicoRemoto.REALIZAR_DEPOSITO.equals(falhaFuncionamento))
			throw new ServicoRemotoException(falhaFuncionamento.causaDaFalha());
		
		contaCorrente = conta;
	}

}
