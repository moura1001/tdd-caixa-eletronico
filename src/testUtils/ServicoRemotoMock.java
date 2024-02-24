package testUtils;

import tddCaixaEletronico.ServicoRemoto;
import tddCaixaEletronico.ServicoRemotoException;

public class ServicoRemotoMock implements ServicoRemoto {

	private FalhaServicoRemoto falhaFuncionamento;

	@Override
	public void recuperarConta() throws ServicoRemotoException {
		if(falhaFuncionamento != null)
			throw new ServicoRemotoException(falhaFuncionamento.causaDaFalha());
	}

	public void configurarFalhaDeFuncionamento(FalhaServicoRemoto falhaFuncionamento) {
		this.falhaFuncionamento = falhaFuncionamento;
	}

}
