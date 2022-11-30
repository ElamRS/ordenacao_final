package Ordenação;

import java.util.Random;

public class SortingAlgs {
	public static void main(String[] args) {
		Random random = new Random();
		
		int v[] = new int[30]; 
		
		// popular o vetor
		for (int i=0; i<v.length; ++i) {v[i] = random.nextInt(10);}
		
		// Imprimir o vetor antes de ser ordenado
		//for (int i=0; i<v.length; ++i) {System.out.print(v[i] + " ");}
		
		// Ordenar o vetor
		shellSort(v);
		System.out.println();
		// Imprimir o vetor após ter sido ordenado
		//for (int i=0; i<v.length; ++i) {System.out.print(v[i] + " ");}
	}
	
	
	// 1 - Algoritmo BubbleSort
	public static int[] bubbleSort(int[] A) {
		int flag = 0; // flag para armazenar quantas trocas de posição ocorreram
		// Primeiro laço de iteração que irá determinar o elemento que será comparado com os demais
		for (int i=0; i<A.length; ++i) {
			flag = 0; // Renovação da flag para que o valor não seja contaminado com execuções anteriores
			// Segundo laço de iteração para comparar o i-ésimo termo com os demais
			for (int j=i+1; j<A.length; ++j) {
				// Caso o i-ésimo termo seja maior que o j-ésimo termo, eles trocam de lugar
				if (A[i]>A[j]) {
					int aux = A[j];
					A[j] = A[i];
					A[i] = aux;
					flag++; // A flag de troca de posições aumenta em 1 o seu valor
				}
			}
			// Caso não haja troca de posição, isso quer dizer que o vetor já está ordenado
			if (flag==0) {break;}
		}
		// Retorna o vetor A ordenado
		return A;
	}
	
	// 2 - Algoritmo Selection Sort
	public static int[] selectionSort(int[] A) {
		// Laço for para iterar por todo o vetor
		for (int i=0; i<A.length; ++i) {
			// A variável min, que armazena o menor índice do subarray, recebe o valor de i
			int min = i;
			/*
			 * Laço for para iterar desde o elemento imediatamente à direita até o fim do vetor em busca de um elemento
			 * que seja menor que o elemento no menor índice.
			*/
			for (int j=i+1; j<A.length; ++j) {
				/*
				 *  Caso seja encontrado um elemento menor que o na posição min, estes trocam de posição entre si.
				 *  Ou seja, os menores elementos vão sendo posto à esquerda.
				*/
				if (A[j]<A[min]) {
					int aux = A[j];
					A[j] = A[min];
					A[min] = aux;
				}
			}
		}
		// Retorna o vetor A ordenado
		return A;
	}
	
	// 3 - Algoritmo InsertionSort
	public static int[] insertionSort(int[] A) {
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
				A[j+1] = A[j];
				j--; // O valor de j é decrescido em 1
			}
			/*
			 *  O elemento no índice imediatamente à direita do primeiro elemento encontrado que seja menor que a chave
			 *  Recebe o valor da chave, para justamente manter o vetor em ordem crescente.
			 *  Caso a chave seja o menor elemento do vetor, esta chave ocupará o índice [0] do vetor.
			*/
			A[j+1] = chave;
		}
		// Retorna o vetor A ordenado
		return A;
	}
	
	// 4 - Algoritmo Shell Sort
	public static void shellSort(int[] A) {
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
			for (int i=intervalo; i<n; ++i) {
				int chave = A[i];
				int j = i;
				while (j >= intervalo && A[j-intervalo] > chave) {
					A[j] = A[j - intervalo];
					j = j - intervalo; // j é decrementado pelo valor do intervalo
				}
				A[j] = chave; // O valor chave passa a ocupar o índice imediatamente a direita do primeiro valor menor que a chave encontrado
			}
		} while (intervalo != 1);
		
		// (LÓGICA DO ALGORITMO) O vetor é dividido logicamente em sub vetores composto de elementos separados por um intervalo e para cada um deles é aplicado o insertion sort
		/* Segue um exemplo de execução abaixo:
		 * 	   [3, 4, 1, 5, 6, 2]  (vetor desordenado de 6 elementos e intervalo igual a 2)
		 * 
		 * Passo 1: Dividir em sub vetores com elementos separados pelo intervalo
		 * 	   [3', 4*, 1', 5*, 6', 2*]  --> ': [3, 1, 6] e *: [4, 5, 2]
		 * Passo 2: Aplicar o insertion sort em cada sub vetor
		 * 	   [1', 2*, 3', 4*, 6', 5*] | ': [3, 1, 6] -> [1, 3, 6] e *: [4, 5, 2] -> [2, 4, 5]  
		 * Passo 3: Dividir o vetor sub vetores com elementos separados por 1
		 * 	   [1', 2', 3', 4', 6', 5']  (sub vetor com o mesmo tamanho do vetor original)
		 * Passo 4: Aplicar o insertion sort no sub vetor
		 * 	   [1', 2', 3', 4', 5', 6']  --> Vetor ordenado!!!
		*/ 
		
		// Código retirado do livro Projeto de Algoritmos - Capítulo 4: Ordenação.
		// Disponível em: https://www2.dcc.ufmg.br/livros/algoritmos-java/cap4/transp/completo1/cap4.pdf, pág. 26. 
	}
	
	// 5 - Algoritmo Merge Sort
	public static void mergeSort(int[] A) {
		// A variável n armazena o tamanho do vetor passado
		int n = A.length;
		
		// Checa se o vetor possuí apenas um único elemento ou se é um vetor vazio, em ambos os casos o vetor já esta ordenado
		if (n<2) {return;}
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
		merge(A, esquerda, direita);
	}
	
	// 5.1 - Método auxiliar do mergeSort
	private static void merge(int A[], int[] esq, int[] dir) {
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
			// Caso o elemento da lista da esquerda for o menor, este é posto na lista e passa-se para o próximo elemento do vetor
			if (esq[i]<=dir[j]) {
				A[k] = esq[i];
				i++;
			} else { // Do contrário, é o elemento da direita que é posto e passa-se para o próximo elemento da lista direita
				A[k] = dir[j];
				j++;
			}
			k++; // incrementa-se o valor da variável que itera sobre o vetor principal
		}
		
		// Quando ocorrer dos vetores do lado esquerdo já terem todos sidos postos na lista ordenada, um laço while irá rodar adicionando os demais itens da lista à direita
		while (j<tamDir) {
			A[k] = dir[j];
			j++;
			k++; // Continua-se a incrementar a variável que itera sobre o vetor A
		}
		// Quando ocorrer dos vetores do lado direito já terem todos sidos postos na lista ordenada, um laço while irá rodar adicionando os demais itens da lista à esquerda
		while (i<tamEsq) {
			A[k] = esq[i];
			i++;
			k++; // Continua-se a incrementar a variável que itera sobre o vetor A
		}
	}
	
	// 6 - Algoritmo QuickSort
}
