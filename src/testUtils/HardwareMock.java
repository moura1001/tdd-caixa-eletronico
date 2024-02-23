package testUtils;

import tddCaixaEletronico.Hardware;

public class HardwareMock implements Hardware {
	
	private String numeroCartao;

	@Override
	public String pegarNumeroDaContaCartao() {
		return numeroCartao;
	}

	public void configurarNumeroDaContaCartao(String numeroCartao) {
		this.numeroCartao = numeroCartao;
	}

}
