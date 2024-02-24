package tddCaixaEletronico;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class ContaCorrente {

	private static final DecimalFormat FORMATADOR_MOEDA = new DecimalFormat("#,###.00");
	private String numeroDaConta;
	private BigDecimal saldo;
	private BigDecimal saldoAConfirmar;

	public ContaCorrente(String numeroDaConta, BigDecimal saldoInicial) {
		this.numeroDaConta = numeroDaConta;
		saldo = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);
		saldo = saldo.add(saldoInicial);
		saldoAConfirmar = saldo;
	}

	public String consultarSaldo() {
		return FORMATADOR_MOEDA.format(saldo);
	}

	public void depositar(BigDecimal valorDeposito) {
		saldoAConfirmar = saldo.add(valorDeposito);		
	}
	
	public void reverterOperacao() {
		if(!saldoAConfirmar.equals(saldo))
			saldoAConfirmar = saldo;
	}
	
	public void confirmarOperacao() {
		if(!saldoAConfirmar.equals(saldo))
			saldo = saldoAConfirmar;
	}

}
