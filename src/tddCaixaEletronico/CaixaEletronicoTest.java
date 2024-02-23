package tddCaixaEletronico;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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
	}

}
