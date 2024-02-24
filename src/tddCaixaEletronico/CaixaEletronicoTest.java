package tddCaixaEletronico;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import testUtils.FalhaHardware;
import testUtils.FalhaServicoRemoto;
import testUtils.HardwareMock;
import testUtils.ServicoRemotoMock;

class CaixaEletronicoTest {
	
	private HardwareMock hardware;
	private ServicoRemotoMock servicoRemoto;
	private CaixaEletronico caixa;
	
	@BeforeEach
	void init() {
		hardware = new HardwareMock();
		hardware.configurarNumeroDaContaCartao("6269013591221186");
		servicoRemoto = new ServicoRemotoMock();
		caixa = new CaixaEletronico(hardware, servicoRemoto);
	}

	@Nested
	@DisplayName("casos de teste para o login")
	class Logar {
		@Test
		void deveriaLogarNaContaCorrenteComSucesso() {
			String mensagemExibida = caixa.logar();
			assertEquals("Usuário Autenticado", mensagemExibida);
		}
		
		@Test
		void deveriaLancarExcecaoPorFalhaNoHardwareAoTentarLerOCartao() {
			hardware.configurarFalhaDeFuncionamento(FalhaHardware.LEITURA_CARTAO);
			
			HardwareException thrown = assertThrows(HardwareException.class, () -> {
				caixa.logar();
	        });
	        assertEquals("Falha de funcionamento do hardware: Erro na leitura do cartão", thrown.getMessage());
		}
		
		@Test
		void deveriaLancarExcecaoPorMauFuncionamentoNoServicoRemotoAoTentarRecuperarConta() {
			servicoRemoto.configurarFalhaDeFuncionamento(FalhaServicoRemoto.RECUPERAR_CONTA);
			
			ServicoRemotoException thrown = assertThrows(ServicoRemotoException.class, () -> {
				caixa.logar();
	        });
	        assertEquals("Falha de comunicação com o serviço remoto: Erro ao tentar recuperar informações da conta", thrown.getMessage());
		}
	}

}
