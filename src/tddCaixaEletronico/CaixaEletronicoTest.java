package tddCaixaEletronico;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import testUtils.FalhaHardware;
import testUtils.HardwareMock;
import testUtils.ServicoRemotoMock;

class CaixaEletronicoTest {

	@Nested
	@DisplayName("casos de teste para o login")
	class Logar {
		@Test
		void deveriaLogarNaContaCorrenteComSucesso() {
			HardwareMock hardware = new HardwareMock();
			hardware.configurarNumeroDaContaCartao("6269013591221186");
			ServicoRemoto servico = new ServicoRemotoMock();
			CaixaEletronico caixa = new CaixaEletronico(hardware, servico);
			
			String mensagemExibida = caixa.logar();
			assertEquals("Usuário Autenticado", mensagemExibida);
		}
		
		@Test
		void deveriaLancarExcecaoPorFalhaNoHardwareAoTentarLerOCartao() {
			HardwareMock hardware = new HardwareMock();
			hardware.configurarNumeroDaContaCartao("6269013591221186");
			hardware.configurarFalhaDeFuncionamento(FalhaHardware.LEITURA_CARTAO);
			ServicoRemoto servico = new ServicoRemotoMock();
			CaixaEletronico caixa = new CaixaEletronico(hardware, servico);
			
			HardwareException thrown = assertThrows(HardwareException.class, () -> {
				caixa.logar();
	        });
	        assertEquals("Falha de funcionamento do hardware: Erro na leitura do cartão", thrown.getMessage());
		}
	}

}
