package Ordenação;

import java.util.Random;

public class CalcMovComp {
	// Método para estimar a quantidade de movimentações de um determinado método
	public static void main(String[] args) {
		// Vetor 2D que armazena as comparações
		long[][] comp_mov = new long[6][2];
		
		Random random = new Random(); // Declaração do objeto random
		
		int tamanho = 65610000; // 10000, 30000, 90000, 270000, 810000, 2430000, 65610000
		
		int[] vetor = new int[tamanho]; // Declaração do Vetor
		int[] clone = new int[tamanho];
		// Popular o vetor com os valores
		for (int i=0; i<vetor.length; ++i) {
			vetor[i] = random.nextInt(1000000);
		}
		// Obter as comparações
		for (int i=0; i<6; ++i) {
			clone = vetor.clone();
			if (i<3 && clone.length>=810000) {
				continue;
			}
			switch(i) {
					case 0:
						comp_mov[i] = bubbleSort(clone);
						break;
					case 1:
						comp_mov[i] = selectionSort(clone);
						break;
					case 2:
						comp_mov[i] = insertionSort(clone);
						break;
					case 3:
						comp_mov[i] = shellSort(clone);
						break;
					case 4:
						comp_mov[i] = mergeSort(clone);
						break;
					case 5:
						comp_mov[i] = quickSort(clone, 0, clone.length-1);
						break;
					default:
						break;
			}
		}
		
		System.out.println("Desempenho dos algoritmos de ordenacao para um vetor aleatorio de " + tamanho + " elementos.\n");
		
		// Exibir as comparações
		for (int i=0; i<6; ++i) {
			System.out.println(" Comparacoes e Movimentacoes do " + nomeAlgoritmo(i));
			System.out.println("  Comparacoes: " + comp_mov[i][0] + " comparacoes.");
			System.out.println("  Movimentacoes: " + comp_mov[i][1] + " movimentos.\n");
		}
	}
	
	// 1 - Algoritmo BubbleSort
	public static long[] bubbleSort(int[] A) {
		long[] comp_mov = new long[2]; // comp_mov[0] armazena as comparações e comp_mov[1] as movimentações
		
		// Variáveis comp e flag que armazenam, respectivamente, o número de comparações e de movimentações realizadas
		int flag = 0; // flag para armazenar quantas trocas de posição ocorreram (o Bubble Sort já possuí a variável que armazena o número de movimentações)
		// Primeiro laço de iteração que irá determinar o elemento que será comparado com os demais
		for (int i=0; i<A.length; ++i) {
			flag = 0; // Renovação da flag para que o valor não seja contaminado com execuções anteriores
			// Segundo laço de iteração para comparar o i-ésimo termo com os demais
			for (int j=i+1; j<A.length; ++j) {
				comp_mov[0]++; // A variável de comparações é incrementada de mais um para todas as comparações
				// Caso o i-ésimo termo seja maior que o j-ésimo termo, eles trocam de lugar
				if (A[i]>A[j]) {
					int aux = A[j];
					A[j] = A[i];
					A[i] = aux;
					flag++; // A flag de troca de posições aumenta em 1 o seu valor
					comp_mov[1]++; // Mov recebe mais 1
				} 
			}
			// Caso não haja troca de posição, isso quer dizer que o vetor já está ordenado
			if (flag==0) {break;}
		}
		
		return comp_mov; // O vetor com as quantidades de comparações e movimentações realizadas é retornado
	}
	
	// 2 - Algoritmo Selection Sort
	public static long[] selectionSort(int[] A) {
		long[] comp_mov = new long[2]; // comp_mov[0] armazena as comparações e comp_mov[1] as movimentações
		
		// Laço for para iterar por todo o vetor
		for (int i=0; i<A.length; ++i) {
			// A variável min, que armazena o menor índice do subarray, recebe o valor de i
			int min = i;
			/* 
			 * Laço for para iterar desde o elemento imediatamente à direita até o fim do vetor em busca de um elemento
			 * que seja menor que o elemento no menor índice.
			*/
			for (int j=i+1; j<A.length; ++j) {
				comp_mov[0]++; // comp incrementada de 1 qualquer que seja o valor lógico do If
				/*
				 *  Caso seja encontrado um elemento menor que o na posição min, estes trocam de posição entre si.
				 *  Ou seja, os menores elementos vão sendo posto à esquerda.
				*/
				if (A[j]<A[min]) {
					int aux = A[j];
					A[j] = A[min];
					A[min] = aux;
					comp_mov[1]++; // Mov recebe mais 1
				}
			}
		}
		
		return comp_mov; // O vetor com as quantidades de comparações e movimentações realizadas é retornado
	}
	
	// 3 - Algoritmo InsertionSort
	public static long[] insertionSort(int[] A) {
		long[] comp_mov = new long[2]; // comp_mov[0] armazena as comparações e comp_mov[1] as movimentações
		
		int chave; // Variável chave, que irá armazenar o valor do i-ésimo elemento do vetor
		// Laço for que percorre o vetor do segundo índice até seu fim
		for (int i = 1; i<A.length; ++i) {
			// O valor do elemento no índice i é armazenado na variável chave;
			chave = A[i];
			// A variável j é instanciada e recebe o valor imediatamente menor que o de i
			int j = i - 1;
			/* 
			 * Um laço de repetição while é rodado enquanto o valor de j for maior ou igual a 0
			 * e o valor do j-ésimo elemento for maior que o valor do i-ésimo elemento.
			 * 
			 * Ou seja, o subarray será percorrido da direita para a esquerda em busca de um valor à esquerda que
			 * seja maior que a chave (último elemento adicionado ao subarray). Caso exista tal elemento, o índice
			 * imediatamente à direita receberá o valor deste j-ésimo elemento.
			*/
			while ((j>=0) && (A[j]>chave)) {
				comp_mov[0] +=2; // As comparações só ocorrem dentro do sub vetor e são feitas duas
				A[j+1] = A[j];
				j--; // O valor de j é decrescido em 1
				comp_mov[1]++; // Para movimentação de elementos, mov++
			}
			/*
			 *  O elemento no índice imediatamente à direita do primeiro elemento encontrado que seja menor que a chave
			 *  Recebe o valor da chave, para justamente manter o vetor em ordem crescente.
			 *  Caso a chave seja o menor elemento do vetor, esta chave ocupará o índice [0] do vetor.
			*/
			A[j+1] = chave;
			comp_mov[1]++; // Mesma lógica
		}
		return comp_mov; // O vetor com as quantidades de comparações e movimentações realizadas é retornado
	}
	
	// 4 - Algoritmo Shell Sort
	public static long[] shellSort(int[] A) {
		long[] comp_mov = new long[2]; // comp_mov[0] armazena as comparações e comp_mov[1] as movimentações
		
		// A variável n armazena o tamanho do vetor
		int n = A.length;
		// Nesta implementação utilizaremos a formula de Knuth como forma de determinar o tamanho do intervalo
		int intervalo = 1; // o intervalo é iniciado com valor igual a 1 para que a fórmula possa ser aplicada
		// O valor do intervalo será incrementado pelo seu triplo adicionado de 1, enquanto este for menor que n
		do {
			intervalo = intervalo * 3 + 1; // intervalo = {1, 4, 13, 40, 121, 364, ...}
		} while (intervalo < n); // Apesar do while rodar enquanto o intervalo for menor que n, este sempre sai com um valor maior que o tamanho do vetor
		
		// Após o intervalo ter sido definido, o algoritmo Shell Sort propriamente dito é rodado
		do {
			intervalo = intervalo / 3; // O intervalo é divido por 3 de modo a obter o maior valor da sequência menor do que o tamanho n
			// O vetor é dividido logicamente em sub vetores composto de elementos separados por um intervalo e para cada um deles é aplicado o insertion sort
			for (int i=intervalo; i<n; ++i) {
				// Mesma lógica do Insertion Sort, só que implementado sobre o Shell Sort
				int chave = A[i];
				int j = i;
				while (j >= intervalo && A[j-intervalo] > chave) {
					comp_mov[0]++; // São feitas duas comparações de uma vez
					A[j] = A[j - intervalo];
					j = j - intervalo; // j é decrementado pelo valor do intervalo
					comp_mov[1]++; // Para cada troca há um incremento
				}
				A[j] = chave; // O valor chave passa a ocupar o índice imediatamente a direita do primeiro valor menor que a chave encontrado
				comp_mov[1]++;
			}
		} while (intervalo != 1);
		
		return comp_mov; // O vetor com as quantidades de comparações e movimentações realizadas é retornado
		
		// Código retirado do livro Projeto de Algoritmos - Capítulo 4: Ordenação.
		// Disponível em: https://www2.dcc.ufmg.br/livros/algoritmos-java/cap4/transp/completo1/cap4.pdf, pág. 26. 
	}
	
	// 5 - Algoritmo Merge Sort
	public static long[] mergeSort(int[] A) {
		long[] comp_mov = new long[2]; // comp_mov[0] armazena as comparações e comp_mov[1] as movimentações
		
		// A variável n armazena o tamanho do vetor passado
		int n = A.length;
		
		// Checa se o vetor possuí apenas um único elemento ou se é um vetor vazio, em ambos os casos o vetor já esta ordenado
		if (n<2) {comp_mov[0]++; return comp_mov;}
		// obtém o valor do meio do vetor, a partir do qual irá realizar a divisão dos sub-vetores
		int meio = n/2;
		// Declaração do vetor da esquerda
		int[] esquerda = new int[meio];
		// Declaração do vetor da direita
		int[] direita = new int[n-meio];
		// Laço for para popular o vetor da esquerda com os elementos do vetor A de 0 até meio
		for (int i=0; i<meio; ++i) {
			esquerda[i] = A[i];
		}
		// Laço for para popular o vetor da direita com os elementos do vetor A de meio até N
		for (int i=meio; i<n; ++i) {
			direita[i-meio] = A[i]; // (i-meio) é utilizado pois o vetor direita[] possuí índices que vão de 0 até meio-1
		}
		// As chamadas abaixo continuarão a acontecer até que os sub-vetores contenham apenas 1 elemento cada, quando será interrompida pela estrutura condicional no início do método
		
		// Chamada recursiva do método mergeSort() para o vetor da esquerda, de modo que ele seja dividido em outros sub-vetores
		mergeSort(esquerda);
		// Chamada recursiva do método mergeSort() para o vetor da direita
		mergeSort(direita);
		
		/* Estas chamadas recursivas conseguem dar conta de passar por todo o array por conta da forma como são armazenadas na memória, 
		 * em forma de pilha, nas quais as chamadas são empilhadas uma em cima da outra e executadas da última para a primeira, Last in First Out
		*/
		
		// Após o vetor ter sido quebrado em arrays de 1 elemento cada, o método merge() é chamado para "voltar" juntando os arrays, ao mesmo tempo em que os ordena
		comp_mov = merge(A, esquerda, direita);
		return comp_mov; // Retorna-se o vetor com o número de comparações e movimentos realizados, para que não se imprima novos valores na tela cada vez que o método merge termine
	}
	
	// 5.1 - Método auxiliar do mergeSort
	private static long[] merge(int A[], int[] esq, int[] dir) {
		long[] comp_mov = new long[2]; // comp_mov[0] armazena as comparações e comp_mov[1] as movimentações
		
		// As variáveis comp e troca irão armazenar o número de comparações e de trocas realizadas
		
		/* O Método utilizado para a ordenação pode ser entendido da seguinte maneira:
		 *  Imagine duas pilhas já ordenadas
		 *  		 2      1
		 *   		 3	    4
		 *   		 6      7
		 *  Iniciando pelo topo, pegamos o 1 e o 2, como o 1 é menor, na hora de construir uma nova lista colocaremos o 1 primeiro
		 *    [1, , , , , ]
		 *  Agora checa-se se o 2 é menor que o próximo elemento da segunda lista, o 4, ele é, então é adicionado a lista ordenada
		 *    [1, 2, , , , ]
		 * 	Movemos agora para o próximo elemento da sub-lista à esquerda, o 3, e o comparamos com o elemento 4. Como 3 é menor, ele é posto na lista
		 *    [1, 2, 3, , , ]
		 *  Partimos agora para o 6 e o comparamos com o 4, como o 4 é menor ele é que será adicionado a lista
		 *    [1, 2, 3, 4, , ]
		 *  Comparando o 6 com o 7, temos que o 6 é o menor, então ele vai ser posto na lista
		 *    [1, 2, 3, 4, 6, ]
		 *  Por fim, como o vetor da esquerda já foi todo adicionado, põe-se o 7 no vetor
		 *    [1, 2, 3, 4, 6, 7]
		*/	 
		
		// Obtem-se os tamanhos dos dois vetores
		int tamEsq = esq.length;
		int tamDir = dir.length;
		
		// Para a comparação citada nos comentários ao início do método ser realizada, são utilizadas 3 variáveis que 
		// irão percorrer, respectivamente, os vetores da esquerda, da direita e o principal
		int i = 0, j = 0, k = 0;
		// Laço while que irá rodar enquanto as variáveis i e j forem, respectivamente, menores que ou iguais aos seus últimos índices permitidos
		while (i<tamEsq && j<tamDir) {
			comp_mov[0] += 2; // Ocorreram duas comparações
			// Caso o elemento da lista da esquerda for o menor, este é posto na lista e passa-se para o próximo elemento do vetor
			if (esq[i]<=dir[j]) {
				comp_mov[0]++;
				A[k] = esq[i];
				i++;
				comp_mov[1]++;
			} else { // Do contrário, é o elemento da direita que é posto e passa-se para o próximo elemento da lista direita
				// A comparação já foi realizada no If
				A[k] = dir[j];
				j++;
				comp_mov[1]++;
			}
			k++; // incrementa-se o valor da variável que itera sobre o vetor principal
		}
		
		// Quando ocorrer dos vetores do lado esquerdo já terem todos sidos postos na lista ordenada, um laço while irá rodar adicionando os demais itens da lista à direita
		while (j<tamDir) {
			comp_mov[0]++; // Na verdade será realizada uma comparação a mais, pois o while não executará quando a condição for falsa
			A[k] = dir[j];
			j++;
			k++; // Continua-se a incrementar a variável que itera sobre o vetor A
			comp_mov[1]++;
		}
		// Quando ocorrer dos vetores do lado direito já terem todos sidos postos na lista ordenada, um laço while irá rodar adicionando os demais itens da lista à esquerda
		while (i<tamEsq) {
			comp_mov[0]++; // Na verdade será realizada uma comparação a mais, pois o while não executará quando a condição for falsa
			A[k] = esq[i];
			i++;
			k++; // Continua-se a incrementar a variável que itera sobre o vetor A
			comp_mov[1]++;
		}
		return comp_mov;
	}
	
	// 6 - Algoritmo QuickSort
	public static long[] quickSort(int[] vetor, int inicio, int fim){
		long[] comp_mov = new long[2]; // comp_mov[0] armazena as comparações e comp_mov[1] as movimentações
		if (inicio < fim) {
			comp_mov[0]++; // Incrementa-se em 1 o número de comparações
		    int posiPivo = separar(vetor, inicio, fim, comp_mov);  
		    quickSort(vetor, inicio, posiPivo);
		    quickSort(vetor, posiPivo + 1, fim);
	    }
		return comp_mov; // O vetor com as quantidades de comparações e movimentações realizadas é retornado
	}

	//Método para separar as listas até retornar pivô
	private static int separar (int a[], int inicio, int fim, long[] comp_mov) {
		// comp_mov[0] armazena as comparações e comp_mov[1] as movimentações
		
		int meio = (inicio + fim) / 2;
		int pivo = a[meio];
		int i = inicio - 1;
		int j = fim + 1;
		
		while (true) {
			comp_mov[0]++;
			do {
				i++;
				comp_mov[0]++;
			} while(a[i] < pivo);
			
			do {
				j--;
				comp_mov[0]++;
			} while(a[j] > pivo);
			
			if (i >= j) {
				comp_mov[0]++;
				return j;
			}
			trocar(a, i, j);
			comp_mov[1]++;
		}
	}

	//Método para trocar posições no vetor
	private static void trocar(int[] A, int i, int j) {
		int temp = A[i];
		A[i] = A[j];
		A[j] = temp;
	}
	
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
