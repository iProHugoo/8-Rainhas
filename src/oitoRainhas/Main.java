package oitoRainhas;

public class Main {

	public static void main(String[] args) {

		oitoRainhas problemaDas8Rainhas = new oitoRainhas();
		int[] estado = new int[8];
		int[][] tabuleiro = new int[8][8];

		// Gera uma situação inicial configurando o tabuleiro aletoriamente
		problemaDas8Rainhas.geradorAleatorio(tabuleiro, estado);
		System.out.println("_________________________________________________");
		System.out.println("Estado Inicial");
		System.out.println("-------------------------------------------------");
		// Exibição do tabuleiro gerado aletoriamente
		problemaDas8Rainhas.imprimeTabuleiro(tabuleiro);

		// Exibição da solução
		System.out.println("_________________________________________________");
		System.out.println("Estado Final");
		System.out.println("-------------------------------------------------");

		problemaDas8Rainhas.problemaDas8Rainhas(tabuleiro, estado);

	}

}
