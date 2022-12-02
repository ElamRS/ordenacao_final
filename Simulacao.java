import java.util.Random;

//   Tempo Estimado de Execução: 10 horas

/*
  *  - pouco eficientes (BubleSort, SelectSort e InsertionSort)
  *	 - mais eficientes (ShellSort, MergeSort e QuickSort)
  *
  *	 Todos os algoritmos devem ser executados pelas mesmas sequencias de vetores para que as comparações sejam válidas.
  *	 Para cada algoritmo de ordenação o vetor de entrada terá os seguintes tamanhos: 10000, 30000, 90000, 270000, 810000, 2430000, 65610000. 
  *	 Obs: Não se deve executar os algoritmos pouco eficientes para os três últimos tamanhos de vetor (810.000, 2.430.000, 65.610.000).
  *
  *	 Sementes: 13, 21, 34, 55, 89, 144.
  *
  *  Deverá ser rodado o mesmo algoritmo para cada semente 
  *  e para cada um dos tamanhos do vetor no mínimo 10 vezes e ser tirada a média de tempo de execução.
*/

public class Simulacao {
	// Método main, responsável por coordenar a execução dos algoritmos e vetores a serem ordenados
	public static void main(String[] args) {
		// Vetor 2D que irá armazenar e relacionar os tamanhos de vetores com o tempo médio 
		// demorado por cada algoritmo para ordenar um vetor com este número de elementos
		double[][] exec_time_greatest = new double[7][6];
		
		// Vetor que contém os valores das seeds
		int[] seeds = {13, 21, 34, 55, 89, 144};
		// Vetor que contém os tamanhos dos vetores que serão ordenados
		int[] tamanhos = {10000, 30000, 90000, 270000, 810000, 2430000, 65610000}; // 10000, 30000, 90000, 270000, 810000, 2430000, 65610000
		// Laço for que irá fazer com que seja rodada uma simulação para cada um dos 7 tamanhos de vetores pedidos
		for (int i=0; i<tamanhos.length; ++i) {
			exec_time_greatest[i] = rodarSimulacao(seeds, tamanhos[i]); // A cada chamada são passadas as seeds e o i-ésimo tamanho
			// O vetor 2D recebe os tempos médio de ordenação dos vetores para cada tamanho n
			exibirTempo(exec_time_greatest, tamanhos, i);
			// A idéia é que a medida que os algoritmos forem rodados para um certo tamanho de vetor e o tempo de execução for calculado, ele já ser exibido, impedindo que o console fique horas e horas sem nenhum output
		}
	}
	
	// Método que irá criar os vetores de mesmo tamanho para cada uma das sementes
	public static double[] rodarSimulacao(int[] seeds, int n) {
		// Vetor de double que irá armazenar a média dos tempos que cada um dos 6 algoritmos
		// levou para ordenar cada vetor de tamanho n
		double[] exec_time_outer = new double[6];
		
		// Este laço for irá iterar sobre o vetor com as seeds e garantir que será gerado, para cada seed, um vetor de tamanho n
		for (int i=0; i<seeds.length; ++i) {
			
			// Instanciamento do objeto random com a seed correspondente
			Random random = new Random(seeds[i]);
			
			// Declaração do vetor de inteiros que irá receber n elementos
			int[] vetor = new int[n];
			
			// Laço for para popular este vetor com números inteiros aleatórios (pela seed) entre 0 e 10^6
			for (int j=0; j<n; ++j) {
				vetor[j] = random.nextInt(1000000);
			}
			
			// Chamada do método que irá rodar os algoritmos de ordenação para o vetor criado
			double[] exec_time_inner = rodarOrdenacao(vetor);
			
			// Laço for para somar os tempos de execução no vetor maior
			for (int j=0; j<6; ++j) {
				exec_time_outer[j] += exec_time_inner[j]; // O tempo de execução médio de cada algoritmo para cada combinação de tamanho + seed é armazenado no vetor maior
			}
		}
		// Laço for para tirar a média de tempo de execução no vetor maior
		for (int k=0; k<6; ++k) {
			exec_time_outer[k] /= 6; 
			// Como são 6 seeds e o valor atual é a soma dos tempos de ordenação 
			// médios para cada seed, divide-se por 6 para que o tempo armazenado no vetor maior
			// seja a média do tempo de ordenação para cada tamanho de vetor.
		}
		
		// Retorna o vetor com o tempo médio que cada algoritmo levou para ordenar cada um dos vetores de tamanho n
		return exec_time_outer;
	}
	
	// Método responsável por rodar os 6 métodos de ordenação presentes na classe SortingAlgs no vetor passado 
	// e por retornar um array de doubles com a média do tempo de execução para cada algoritmo sobre um vetor com um tamnho n e seed específica 
	public static double[] rodarOrdenacao(int[] v) {
		/*
		 *  Os algoritmos serão representados pelos seguintes números, na ordem em que estão na classe Sorting Algs
		 *		0 - Bubble Sort				3 - Shell Sort
		 *		1 - Selection Sort			4 - Merge Sort
		 *		2 - Insertion Sort			5 - Quick Sort
		 *
		*/
		
		double[] exec_time_inner = new double[6]; 
		
		// Foi pedido que cada combinação de tamanho de vetor e seed seja ordenado ao menos 10 vezes por cada algoritmo
		for (int i=0; i<6; ++i) {
			// Como os três primeiros algoritmos não devem ser rodados para os 3 maiores vetores, esta condicional existe
			if (i<3 && v.length>=810000) {
				exec_time_inner[i] = 0.0;
			} else {
				exec_time_inner[i] = calc_tempo(v, i, 10); // parâmetros: v (vetor passado), i (indica o algoritmo que será rodado, inteiro (indica o número de vezes que cada algoritmo será rodado para cada vetor)
			}
		}
		// Retorna o tempo de execução médio
		return exec_time_inner;
	}
	
	// Método responsável por rodar 10 vezes um mesmo algoritmos sobre um mesmo vetor e retornar a média do tempo dessas ordenações
	public static double calc_tempo(int[] v, int a, int vezes) {
		int[] auxiliar; // Para evitar que uma vez ordenado, o vetor contamine as demais execuções por facilitar o trabalho dos algoritmos, é criado um vetor clone
		
		long soma = 0; // A variável soma recebe a soma de todos os tempos que os algoritmos levaram para ordenar
		long inicio, fim; // A variável inicio armazena o tempo do sistema quando o algoritmo é chamado, e a fim quando termina
		double media; // Como cada algoritmo será rodado 10 vezes, a média será igual a soma / 10
		
		// É analisado o valor do parâmetro a, que indica o algoritmo a ser rodado (só será rodado 1 algoritmo) 'vezes' vezes. 
		switch (a) {
			case 0: // Bubble Sort
				for (int i=0; i<vezes; ++i) {
					auxiliar = v.clone(); // O vetor auxiliar recebe uma cópia do vetor v no inicio de cada execução
					inicio = System.nanoTime();
					SortingAlgs.bubbleSort(auxiliar);
					fim = System.nanoTime();
					soma += (fim-inicio);
				}
				break;
			case 1: // Selection Sort
				for (int i=0; i<vezes; ++i) {
					auxiliar = v.clone(); // O vetor auxiliar recebe uma cópia do vetor v no inicio de cada execução
					inicio = System.nanoTime();
					SortingAlgs.selectionSort(auxiliar);
					fim = System.nanoTime();
					soma += (fim-inicio);
				}
				break;
			case 2: // Insertion Sort
				for (int i=0; i<vezes; ++i) {
					auxiliar = v.clone(); // O vetor auxiliar recebe uma cópia do vetor v no inicio de cada execução
					inicio = System.nanoTime();
					SortingAlgs.insertionSort(auxiliar);
					fim = System.nanoTime(); 
					soma += (fim-inicio);
				}
				break;
			case 3: // Shell Sort
				for (int i=0; i<vezes; ++i) {
					auxiliar = v.clone(); // O vetor auxiliar recebe uma cópia do vetor v no inicio de cada execução
					inicio = System.nanoTime();
					SortingAlgs.shellSort(auxiliar);
					fim = System.nanoTime();
					soma += (fim-inicio);
				}
				break;
			case 4: // Merge Sort
				for (int i=0; i<vezes; ++i) {
					auxiliar = v.clone(); // O vetor auxiliar recebe uma cópia do vetor v no inicio de cada execução
					inicio = System.nanoTime();
					SortingAlgs.mergeSort(auxiliar);
					fim = System.nanoTime();
					soma += (fim-inicio);
				}
				break;
			case 5: // Quick Sort
				for (int i=0; i<vezes; ++i) {
					auxiliar = v.clone(); // O vetor auxiliar recebe uma cópia do vetor v no inicio de cada execução
					inicio = System.nanoTime();
					SortingAlgs.quickSort(auxiliar, 0, auxiliar.length-1);
					fim = System.nanoTime();
					soma += (fim-inicio);
				}
				break;
			default:
				break;
		}
		media = (double) soma / (Math.pow(10, 9)*vezes); // Divide-se por 'vezes' pois a variável soma armazena a soma de "vezes" execuções e divide-se por 10^9 por ser em nanossegundo
		return media;
	}
	
	// Método responsável por exibir os tempos de ordenação médios de cada algoritmo para cada tamanho de vetor
	public static void exibirTempo(double[][] exec_time_greatest, int[] tam, int i) { // É passado o vetor do jeito em que está, junto com o vetor dos tamanhos e o índice que indica qual tamanho de vetor foi completamente ordenado por todos os algoritmos
		String algoritmo;
		// Laço for exterior para iterar sobre os tamanhos do vetores
			System.out.printf("Tempo de execucao medio dos algoritmos para vetores de %d elementos. \n\n", tam[i]);
			// Laço for interior para iterar sobre cada tempo de ordenação médio
			for (int j=0; j<6; ++j) {
				algoritmo = nomeAlgoritmo(j);
				System.out.printf(" %s: %.5f segundos.\n", algoritmo, exec_time_greatest[i][j]);
			}
			System.out.println();
	}
	
	// Método que fornece o nome do algoritmo a partir do seu índice
	public static String nomeAlgoritmo(int index) {
		switch(index) {
			case 0:
				return "Bubble Sort";
			case 1:
				return "Selection Sort";
			case 2:
				return "Insertion Sort";
			case 3:
				return "Shell Sort";
			case 4:
				return "Merge Sort";
			case 5:
				return "Quick Sort";
			default:
				return "Algoritmo";
		}
	}
}
