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
		servicoRemoto.persistirConta(contaLogada);
		return "Depósito recebido com sucesso";
	}

}
