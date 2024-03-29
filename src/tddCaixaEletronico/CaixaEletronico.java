package tddCaixaEletronico;

import java.math.BigDecimal;

public class CaixaEletronico {
	
	private Hardware hardware;
	private ServicoRemoto servicoRemoto;
	private ContaCorrente contaLogada;

	public CaixaEletronico(Hardware hardware, ServicoRemoto servico) {
		this.hardware = hardware;
		this.servicoRemoto = servico;
	}

	public String logar() {
		if(consegueLerCartao()) {
			contaLogada = servicoRemoto.recuperarConta();
			
			return "Usuário Autenticado";
		}
		
		return null;
	}

	private boolean consegueLerCartao() {
		String numeroCartaoLido = hardware.pegarNumeroDaContaCartao();
		return numeroCartaoLido != null && !numeroCartaoLido.isBlank();
	}

	public String saldo() {
		if(contaLogada != null)
			return "O saldo é R$" + contaLogada.consultarSaldo();
		
		return null;
	}

	public String depositar() {
		BigDecimal valorDeposito = hardware.lerEnvelope();
		contaLogada.depositar(valorDeposito);
		try {
			servicoRemoto.persistirConta(contaLogada);
		} catch (ServicoRemotoException e) {
			contaLogada.reverterOperacao();
			throw e;
		}
		contaLogada.confirmarOperacao();
		return "Depósito recebido com sucesso";
	}

	public String sacar() {
		BigDecimal valorSaque = hardware.processarValorParaSaque();
		if(!contaLogada.sacar(valorSaque))
			return "Saldo insuficiente";
		
		try {
			servicoRemoto.persistirConta(contaLogada);
			hardware.entregarDinheiro();
		} catch (HardwareException e) {
			contaLogada.reverterOperacao();
			servicoRemoto.persistirConta(contaLogada);
			throw e;
		} catch (ServicoRemotoException e) {
			contaLogada.reverterOperacao();
			throw e;
		}
		contaLogada.confirmarOperacao();
		return "Retire seu dinheiro";
	}

}
