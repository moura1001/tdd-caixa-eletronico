package tddCaixaEletronico;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import testUtils.FalhaHardware;
import testUtils.FalhaServicoRemoto;
import testUtils.HardwareMock;
import testUtils.ServicoRemotoMock;

class CaixaEletronicoTest {
	
	private static final String NUMERO_DA_CONTA = "6269013591221186";
	private HardwareMock hardware;
	private ServicoRemotoMock servicoRemoto;
	private CaixaEletronico caixaEletronico;
	
	@BeforeEach
	void init() {
		hardware = new HardwareMock();
		hardware.configurarNumeroDaContaCartao(NUMERO_DA_CONTA);
		servicoRemoto = new ServicoRemotoMock();
		caixaEletronico = new CaixaEletronico(hardware, servicoRemoto);
	}

	@Nested
	@DisplayName("casos de teste para o login")
	class Logar {
		@Test
		void deveriaLogarNaContaCorrenteComSucesso() {
			String mensagemExibida = caixaEletronico.logar();
			assertEquals("Usuário Autenticado", mensagemExibida);
		}
		
		@Test
		void deveriaLancarExcecaoPorFalhaNoHardwareAoTentarLerOCartao() {
			hardware.configurarFalhaDeFuncionamento(FalhaHardware.LEITURA_CARTAO);
			
			HardwareException thrown = assertThrows(HardwareException.class, () -> {
				caixaEletronico.logar();
	        });
	        assertEquals("Falha de funcionamento do hardware: Erro na leitura do cartão", thrown.getMessage());
		}
		
		@Test
		void deveriaLancarExcecaoPorMauFuncionamentoNoServicoRemotoAoTentarRecuperarConta() {
			servicoRemoto.configurarFalhaDeFuncionamento(FalhaServicoRemoto.RECUPERAR_CONTA);
			
			ServicoRemotoException thrown = assertThrows(ServicoRemotoException.class, () -> {
				caixaEletronico.logar();
	        });
	        assertEquals("Falha de comunicação com o serviço remoto: Erro ao tentar recuperar informações da conta", thrown.getMessage());
		}
	}
	
	@Nested
	@DisplayName("casos de teste para consulta do saldo")
	class Saldo {
		@Test
		void deveriaConsultarOSaldoDaContaCorrenteComSucesso() {
			servicoRemoto.configurarContaCorrente(new ContaCorrente(NUMERO_DA_CONTA, BigDecimal.valueOf(10_000_000.0)));
			
			caixaEletronico.logar();
			String mensagemExibida = caixaEletronico.saldo();
			assertEquals("O saldo é R$10.000.000,00", mensagemExibida);
		}
	}

}
