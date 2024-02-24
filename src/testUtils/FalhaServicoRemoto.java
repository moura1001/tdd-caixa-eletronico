package testUtils;

public enum FalhaServicoRemoto {
	RECUPERAR_CONTA("Falha de comunicação com o serviço remoto: Erro ao tentar recuperar informações da conta");
	
	private String causaDaFalha;
	
	private FalhaServicoRemoto(String causaDaFalha) {
		this.causaDaFalha = causaDaFalha;
	}
	
	public String causaDaFalha() {
		return causaDaFalha;
	}
}
