package tddCaixaEletronico;

import java.math.BigDecimal;

public interface Hardware {
	
	String pegarNumeroDaContaCartao() throws HardwareException;

	BigDecimal lerEnvelope() throws HardwareException;
	
	BigDecimal processarValorParaSaque();
}
