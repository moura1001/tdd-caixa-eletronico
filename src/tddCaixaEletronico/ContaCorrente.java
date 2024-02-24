package tddCaixaEletronico;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class ContaCorrente {

	private static final DecimalFormat FORMATADOR_MOEDA = new DecimalFormat("#,###.00");
	private String numeroDaConta;
	private BigDecimal saldo;

	public ContaCorrente(String numeroDaConta, BigDecimal saldoInicial) {
		this.numeroDaConta = numeroDaConta;
		saldo = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);
		saldo = saldo.add(saldoInicial);
	}

	public String consultarSaldo() {
		return FORMATADOR_MOEDA.format(saldo);
	}

	public void depositar(BigDecimal valorDeposito) {
		saldo = saldo.add(valorDeposito);		
	}

}
