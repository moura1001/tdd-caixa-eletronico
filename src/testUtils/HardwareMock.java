package testUtils;

import java.math.BigDecimal;

import tddCaixaEletronico.Hardware;
import tddCaixaEletronico.HardwareException;

public class HardwareMock implements Hardware {
	
	private String numeroCartao;
	private FalhaHardware falhaFuncionamento;
	private BigDecimal valorDeposito;

	@Override
	public String pegarNumeroDaContaCartao() throws HardwareException {
		if(falhaFuncionamento != null)
			throw new HardwareException(falhaFuncionamento.causaDaFalha());
		return numeroCartao;
	}

	public void configurarNumeroDaContaCartao(String numeroCartao) {
		this.numeroCartao = numeroCartao;
	}

	public void configurarFalhaDeFuncionamento(FalhaHardware falhaFuncionamento) {
		this.falhaFuncionamento = falhaFuncionamento;
	}

	@Override
	public BigDecimal lerEnvelope() throws HardwareException {
		return valorDeposito;
	}

	public void configurarEnvelopeDeDeposito(BigDecimal valorDeposito) {
		this.valorDeposito = valorDeposito;
	}

}
