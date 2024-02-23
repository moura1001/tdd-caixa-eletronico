package tddCaixaEletronico;

public class CaixaEletronico {
	
	private Hardware hardware;
	private ServicoRemoto servico;

	public CaixaEletronico(Hardware hardware, ServicoRemoto servico) {
		this.hardware = hardware;
		this.servico = servico;
	}

	public String logar() {
		if(consegueLerCartao())
			return "Usuário Autenticado";
		return null;
	}

	private boolean consegueLerCartao() {
		String numeroCartaoLido = hardware.pegarNumeroDaContaCartao();
		return numeroCartaoLido != null && !numeroCartaoLido.isBlank();
	}

}
