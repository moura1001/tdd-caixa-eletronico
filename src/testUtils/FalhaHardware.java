package testUtils;

public enum FalhaHardware {
	LEITURA_CARTAO("Falha de funcionamento do hardware: Erro na leitura do cartão"),
	LEITURA_ENVELOPE_DEPOSITO("Falha de funcionamento do hardware: Erro na leitura do envelope de depósito");
	
	private String causaDaFalha;
	
	private FalhaHardware(String causaDaFalha) {
		this.causaDaFalha = causaDaFalha;
	}

	public String causaDaFalha() {
		return causaDaFalha;
	}
}
