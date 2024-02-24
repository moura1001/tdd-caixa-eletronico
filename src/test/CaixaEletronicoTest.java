package test;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import tddCaixaEletronico.CaixaEletronico;
import tddCaixaEletronico.ContaCorrente;
import tddCaixaEletronico.HardwareException;
import tddCaixaEletronico.ServicoRemotoException;
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
	
	@Nested
	@DisplayName("casos de teste para depósito na conta")
	class Depositar {
		@Test
		void deveriaDepositarNaContaCorrenteComSucesso() {
			hardware.configurarEnvelopeDeDeposito(BigDecimal.valueOf(500));
			servicoRemoto.configurarContaCorrente(new ContaCorrente(NUMERO_DA_CONTA, BigDecimal.valueOf(1_000.0)));
			
			caixaEletronico.logar();
			String mensagemExibida = caixaEletronico.saldo();
			assertEquals("O saldo é R$1.000,00", mensagemExibida);
			
			mensagemExibida = caixaEletronico.depositar();
			assertEquals("Depósito recebido com sucesso", mensagemExibida);
			
			mensagemExibida = caixaEletronico.saldo();
			assertEquals("O saldo é R$1.500,00", mensagemExibida);
		}
		
		@Test
		void deveriaLancarExcecaoPorFalhaNoHardwareAoTentarLerOEnvelopeParaDeposito() {
			hardware.configurarFalhaDeFuncionamento(FalhaHardware.LEITURA_ENVELOPE_DEPOSITO);
			hardware.configurarEnvelopeDeDeposito(BigDecimal.valueOf(500));
			servicoRemoto.configurarContaCorrente(new ContaCorrente(NUMERO_DA_CONTA, BigDecimal.valueOf(1_000.0)));
			
			caixaEletronico.logar();
			String mensagemExibida = caixaEletronico.saldo();
			assertEquals("O saldo é R$1.000,00", mensagemExibida);
			
			HardwareException thrown = assertThrows(HardwareException.class, () -> {
				caixaEletronico.depositar();
	        });
	        assertEquals("Falha de funcionamento do hardware: Erro na leitura do envelope de depósito", thrown.getMessage());
			
			mensagemExibida = caixaEletronico.saldo();
			assertEquals("O saldo é R$1.000,00", mensagemExibida);
		}
		
		@Test
		void deveriaLancarExcecaoPorMauFuncionamentoNoServicoRemotoAoTentarRealizarDeposito() {
			hardware.configurarEnvelopeDeDeposito(BigDecimal.valueOf(500));
			servicoRemoto.configurarFalhaDeFuncionamento(FalhaServicoRemoto.REALIZAR_DEPOSITO);
			servicoRemoto.configurarContaCorrente(new ContaCorrente(NUMERO_DA_CONTA, BigDecimal.valueOf(1_000.0)));
			
			caixaEletronico.logar();
			String mensagemExibida = caixaEletronico.saldo();
			assertEquals("O saldo é R$1.000,00", mensagemExibida);
			
			ServicoRemotoException thrown = assertThrows(ServicoRemotoException.class, () -> {
				caixaEletronico.depositar();
	        });
	        assertEquals("Falha de comunicação com o serviço remoto: Erro ao tentar realizar depósito", thrown.getMessage());
			
			mensagemExibida = caixaEletronico.saldo();
			assertEquals("O saldo é R$1.000,00", mensagemExibida);
		}
	}
	
	@Nested
	@DisplayName("casos de teste para saque na conta")
	class Sacar {
		@Test
		void deveriaSacarDaContaCorrenteComSucesso() {
			hardware.configurarValorParaSaque(BigDecimal.valueOf(500));
			servicoRemoto.configurarContaCorrente(new ContaCorrente(NUMERO_DA_CONTA, BigDecimal.valueOf(1_000.0)));
			
			caixaEletronico.logar();
			String mensagemExibida = caixaEletronico.saldo();
			assertEquals("O saldo é R$1.000,00", mensagemExibida);
			
			mensagemExibida = caixaEletronico.sacar();
			assertEquals("Retire seu dinheiro", mensagemExibida);
			
			mensagemExibida = caixaEletronico.saldo();
			assertEquals("O saldo é R$500,00", mensagemExibida);
		}
		
		@Test
		void naoDeveriaSacarDaContaCorrenteCasoSaldoInsuficiente() {
			hardware.configurarValorParaSaque(BigDecimal.valueOf(1_500));
			servicoRemoto.configurarContaCorrente(new ContaCorrente(NUMERO_DA_CONTA, BigDecimal.valueOf(1_000.0)));
			
			caixaEletronico.logar();
			String mensagemExibida = caixaEletronico.saldo();
			assertEquals("O saldo é R$1.000,00", mensagemExibida);
			
			mensagemExibida = caixaEletronico.sacar();
			assertEquals("Saldo insuficiente", mensagemExibida);
			
			mensagemExibida = caixaEletronico.saldo();
			assertEquals("O saldo é R$1.000,00", mensagemExibida);
		}
		
		@Test
		void deveriaLancarExcecaoPorFalhaNoHardwareAoTentarEntregarDinheiroDoSaque() {
			hardware.configurarFalhaDeFuncionamento(FalhaHardware.ENTREGA_DINHEIRO_SAQUE);
			hardware.configurarValorParaSaque(BigDecimal.valueOf(500));
			servicoRemoto.configurarContaCorrente(new ContaCorrente(NUMERO_DA_CONTA, BigDecimal.valueOf(1_000.0)));
			
			caixaEletronico.logar();
			String mensagemExibida = caixaEletronico.saldo();
			assertEquals("O saldo é R$1.000,00", mensagemExibida);
			
			HardwareException thrown = assertThrows(HardwareException.class, () -> {
				caixaEletronico.sacar();
	        });
	        assertEquals("Falha de funcionamento do hardware: Erro na entrega do dinheiro", thrown.getMessage());
			
			mensagemExibida = caixaEletronico.saldo();
			assertEquals("O saldo é R$1.000,00", mensagemExibida);
		}
		
		@Test
		void deveriaLancarExcecaoPorMauFuncionamentoNoServicoRemotoAoTentarRealizarSaque() {
			hardware.configurarValorParaSaque(BigDecimal.valueOf(500));
			servicoRemoto.configurarFalhaDeFuncionamento(FalhaServicoRemoto.REALIZAR_SAQUE);
			servicoRemoto.configurarContaCorrente(new ContaCorrente(NUMERO_DA_CONTA, BigDecimal.valueOf(1_000.0)));
			
			caixaEletronico.logar();
			String mensagemExibida = caixaEletronico.saldo();
			assertEquals("O saldo é R$1.000,00", mensagemExibida);
			
			ServicoRemotoException thrown = assertThrows(ServicoRemotoException.class, () -> {
				caixaEletronico.sacar();
	        });
	        assertEquals("Falha de comunicação com o serviço remoto: Erro ao tentar realizar saque", thrown.getMessage());
			
			mensagemExibida = caixaEletronico.saldo();
			assertEquals("O saldo é R$1.000,00", mensagemExibida);
		}
	}

}
