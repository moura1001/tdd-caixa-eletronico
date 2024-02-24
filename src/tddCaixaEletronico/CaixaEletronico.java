package tddCaixaEletronico;

public class CaixaEletronico {
	
	private Hardware hardware;
	private ServicoRemoto servicoRemoto;

	public CaixaEletronico(Hardware hardware, ServicoRemoto servico) {
		this.hardware = hardware;
		this.servicoRemoto = servico;
	}

	public String logar() {
		if(consegueLerCartao()) {
			servicoRemoto.recuperarConta();
			
			return "Usuário Autenticado";
		}
		
		return null;
	}

	private boolean consegueLerCartao() {
		String numeroCartaoLido = hardware.pegarNumeroDaContaCartao();
		return numeroCartaoLido != null && !numeroCartaoLido.isBlank();
	}

}
