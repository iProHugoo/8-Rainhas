package oitoRainhas;

import java.util.Arrays;
import java.util.Random;

public class oitoRainhas {

	public int random() {
		// Construtor 'Random' cria um gerador de números
		Random r = new Random();
		// Método 'nextInt(8)' gera um número entre 0 e 7
		int random = r.nextInt(8);
		return random;
	}

	public void geradorAleatorio(int[][] tabuleiro, int[] estado) {
		// Percorre pelas colunas
		for (int i = 0; i < 8; i++) {
			// Obtendo um índice de linha aletoriamente
			estado[i] = random();
			// Insere a rainha no lugar obtido no tabuleiro
			tabuleiro[estado[i]][i] = 1;
		}
	}

	// Função que imprime o tabuleiro
	public void imprimeTabuleiro(int[][] tabuleiro) {

		for (int i = 0; i < 8; i++) {
			System.out.println("+-----+-----+-----+-----+-----+-----+-----+-----+");
			for (int j = 0; j < 8; j++) {
				if (tabuleiro[i][j] == 1) {
					System.out.print("|  R  ");
				}
				if (tabuleiro[i][j] == 0) {
					System.out.print("|     ");
				}
				if (j == 7) {
					System.out.print("|");
				}
			}
			System.out.print("\n");
		}
		System.out.println("+-----+-----+-----+-----+-----+-----+-----+-----+");
	}

	// Preenchendo o tabuleiro com os valores 'value'
	public void preencheTabuleiro(int[][] tabuleiro, int value) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				tabuleiro[i][j] = value;
			}
		}
	}

	// Cálculo objetivo do estado onde as rainhas se atacam
	// Função de avaliação heurística - o número de rainhas atacando umas as outras
	public int paresAtacando(int[][] tabuleiro, int[] estado) {
		/*
		 * Para cada rainha em uma coluna, verificamos se as outras rainhas se alinham
		 * com a rainha atual, e caso encontre alguma, há a incrementação da contagem da
		 * variável de ataque
		 */

		// Número de rainhas atacando umas as outras
		int atacando = 0;

		// Número de pares de rainhas se atacando
		int pares;

		// Variáveis para indexar uma determinada linha e coluna na matriz
		int linha;
		int coluna;

		for (int i = 0; i < 8; i++) {

			// Em cada coluna 'i', a rainha é colocada na linha 'estado[i]'

			// À esquerda da mesma linha
			linha = estado[i];
			coluna = i - 1;
			while (coluna >= 0 && tabuleiro[linha][coluna] != 1) {
				coluna--;
			}
			if (coluna >= 0 && tabuleiro[linha][coluna] == 1) {
				atacando++;
			}

			// À direita da mesma linha
			linha = estado[i];
			coluna = i + 1;
			while (coluna < 8 && tabuleiro[linha][coluna] != 1) {
				coluna++;
			}
			if (coluna < 8 && tabuleiro[linha][coluna] == 1) {
				atacando++;
			}

			// Na diagonal nordeste
			linha = estado[i] - 1;
			coluna = i + 1;
			while (coluna < 8 && linha >= 0 && tabuleiro[linha][coluna] != 1) {
				coluna++;
				linha--;
			}
			if (coluna < 8 && linha >= 0 && tabuleiro[linha][coluna] == 1) {
				atacando++;
			}

			// Na diagonal noroeste
			linha = estado[i] - 1;
			coluna = i - 1;
			while (coluna >= 0 && linha >= 0 && tabuleiro[linha][coluna] != 1) {
				coluna--;
				linha--;
			}
			if (coluna >= 0 && linha >= 0 && tabuleiro[linha][coluna] == 1) {
				atacando++;
			}

			// Na diagonal sudeste
			linha = estado[i] + 1;
			coluna = i + 1;
			while (coluna < 8 && linha < 8 && tabuleiro[linha][coluna] != 1) {
				coluna++;
				linha++;
			}
			if (coluna < 8 && linha < 8 && tabuleiro[linha][coluna] == 1) {
				atacando++;
			}

			// Na diagonal sudoeste
			linha = estado[i] + 1;
			coluna = i - 1;
			while (coluna >= 0 && linha < 8 && tabuleiro[linha][coluna] != 1) {
				coluna--;
				linha++;
			}
			if (coluna >= 0 && linha < 8 && tabuleiro[linha][coluna] == 1) {
				atacando++;
			}

		}
		pares = atacando / 2;
		return pares;
	}

	// Gera um novo tabuleiro com base no estado atual
	public void geraTabuleiro(int[][] tabuleiro, int[] estado) {
		preencheTabuleiro(tabuleiro, 0);
		for (int i = 0; i < 8; i++) {
			tabuleiro[estado[i]][i] = 1;
		}
	}

	// Copia o conteúdo do estado 2 para o estado 1
	public void copia(int[] estado1, int[] estado2) {
		for (int i = 0; i < 8; i++) {
			estado1[i] = estado2[i];
		}
	}

	// Obtém o vizinho do estado atual com o menor valor entre todos os vizinhos juntos com o estado atual
	public void obtemVizinho(int[][] tabuleiro, int[] estado) {

		// Declara e inicializa o tabuleiro e o estado ideal com o tabuleiro e o estado atual como ponto de partida
		int[][] tabuleiroIdeal = new int[8][8];
		int[] estadoIdeal = new int[8];

		copia(estadoIdeal, estado);
		geraTabuleiro(tabuleiroIdeal, estadoIdeal);

		// Inicialização do valor ideal do objetivo
		int objetivoIdeal = paresAtacando(tabuleiroIdeal, estadoIdeal);

		int[][] tabuleiroVizinho = new int[8][8];
		int[] estadoVizinho = new int[8];

		// Inicializando o tabuleiro e o estado temporário para fins computacionais
		copia(estadoVizinho, estado);
		geraTabuleiro(tabuleiroVizinho, estadoVizinho);

		// Percorre entre todos os vizinhos possíveis do tabuleiro
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {

				// Condição para ir ao estado atual
				if (j != estado[i]) {

					// Inicializando o vizinho temporário com o vizinho atual
					estadoVizinho[i] = j;
					tabuleiroVizinho[estadoVizinho[i]][i] = 1;
					tabuleiroVizinho[estado[i]][i] = 0;

					// Cálculo do valor objetivo do vizinho
					int temp = paresAtacando(tabuleiroVizinho, estadoVizinho);

					/*
					 * Comparação dos objetivos temporários e objetivos ideais dos vizinhos, e se os
					 * objetivos temporários forem inferiores aos objetivos ideais, deverá ser
					 * realizada a atualização correta
					 */
					if (temp <= objetivoIdeal) {
						objetivoIdeal = temp;
						copia(estadoIdeal, estadoVizinho);
						geraTabuleiro(tabuleiroIdeal, estadoIdeal);
					}

					// Reverter a configuração básica para percorrer novamente
					tabuleiroVizinho[estadoVizinho[i]][i] = 0;
					estadoVizinho[i] = estado[i];
					tabuleiroVizinho[estado[i]][i] = 1;
				}
			}
		}

		copia(estado, estadoIdeal);
		preencheTabuleiro(tabuleiro, 0);
		geraTabuleiro(tabuleiro, estado);
		/*
		 * System.out.println("##########################");
		 * imprimeTabuleiro(tabuleiro);
		 * System.out.println("##########################");
		 */

	}

	public void problemaDas8Rainhas(int[][] tabuleiro, int[] estado) {

		int[][] tabuleiroVizinho = new int[8][8];
		int[] estadoVizinho = new int[8];

		// Inicialização do tabuleiro e estado vizinho com o tabuleiro e estado atual
		copia(estadoVizinho, estado);
		geraTabuleiro(tabuleiroVizinho, estadoVizinho);

		boolean Continue = true;
		do {
			/*
			 * Uma vez que um vizinho se torna atual após a movimentação, copiamos o
			 * tabuleiro do viznho e indicamos para o tabuleiro e o estado atual
			 */

			copia(estado, estadoVizinho);
			geraTabuleiro(tabuleiro, estado);

			// Recuperação do vizinho ideal

			obtemVizinho(tabuleiroVizinho, estadoVizinho);

			// Verificação da condição se os dois tabuleiros são semelhantes
			if (Arrays.equals(estado, estadoVizinho)) {

				/*
				 * Se o estado vizinho e o estado atual são iguais, então não há vizinho ideal,
				 * e portanto o resultado é exibido
				 */

				imprimeTabuleiro(tabuleiro);

				// Alteração do 'Continue' para 'false' para sair de um loop
				// Loop será interrompido
				Continue = false;
			} else if (paresAtacando(tabuleiro, estado) == paresAtacando(tabuleiroVizinho, estadoVizinho)) {
				/*
				 * Se o estado vizinho e o estado atual não são iguais, mas os objetivos são os
				 * mesmos, então aproximamos de um local ideal. Em todo caso, terá que mudar de
				 * local aletoriamente para escapar dele
				 */

				// Vizinho aleatório
				estadoVizinho[random()] = random();
				geraTabuleiro(tabuleiroVizinho, estadoVizinho);
			}
		} while (Continue);
	}
}
