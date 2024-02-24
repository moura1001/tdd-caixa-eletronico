package testUtils;

import java.math.BigDecimal;

import tddCaixaEletronico.Hardware;
import tddCaixaEletronico.HardwareException;

public class HardwareMock implements Hardware {
	
	private String numeroCartao;
	private FalhaHardware falhaFuncionamento;
	private BigDecimal valorDeposito;
	private BigDecimal valorSaque;

	@Override
	public String pegarNumeroDaContaCartao() throws HardwareException {
		if(FalhaHardware.LEITURA_CARTAO.equals(falhaFuncionamento))
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
		if(FalhaHardware.LEITURA_ENVELOPE_DEPOSITO.equals(falhaFuncionamento))
			throw new HardwareException(falhaFuncionamento.causaDaFalha());
		return valorDeposito;
	}

	public void configurarEnvelopeDeDeposito(BigDecimal valorDeposito) {
		this.valorDeposito = valorDeposito;
	}

	@Override
	public BigDecimal processarValorParaSaque() {
		return valorSaque;
	}

	public void configurarValorParaSaque(BigDecimal valorSaque) {
		this.valorSaque = valorSaque;
	}

	@Override
	public void entregarDinheiro() throws HardwareException {
		if(FalhaHardware.ENTREGA_DINHEIRO_SAQUE.equals(falhaFuncionamento))
			throw new HardwareException(falhaFuncionamento.causaDaFalha());
	}

}
