import java.text.NumberFormat;

public class Main{
		
	public static void main(String[] args){
		Matriz mat1;
		Ordem ordens;
		int cont, ordem;
		ordens = new Ordem();//recebe vetores com as ordens

		//executa todas as ordens de Matriz
		for(cont = 0; cont < 6; cont++){
			int repeticao;
			float mediaBase, mediaOtimizacao1, mediaOtimizacao2, milissegundos;
			ordem = ordens.getOrdem(cont);
			mediaBase = 0;
			mediaOtimizacao1 = 0;
			mediaOtimizacao2 = 0;

			//repete o código três vezes
			for(repeticao = 0; repeticao < 3; repeticao++){
				int otimizacoes;
				
				System.out.println("Matriz de ordem " + ordem + " repeticao " + (repeticao + 1));

				mat1 = new Matriz(ordem, ordem);
				mat1.inicializaRandomico();
				
				mat1.imprime();
				System.out.println();
				
				//calcula cada otimização
				for(otimizacoes = 0; otimizacoes < 3; otimizacoes++){
					int det;
					long inicio, fim, resultado;

					inicio = System.nanoTime();
					//Confere qual a otimização atual e diz o tempo
					if(otimizacoes == 0){
						det = mat1.determinante();
						System.out.println("Metodo base:");
						fim = System.nanoTime();
						resultado = fim - inicio;
						mediaBase = mediaBase + resultado;
					}
					else if(otimizacoes == 1){
						det = mat1.determinanteOtimizacao1();
						System.out.println("Primeira Otimizacao:");
						fim = System.nanoTime();
						resultado = fim - inicio;
						mediaOtimizacao1 = mediaOtimizacao1 + resultado;
					}
					else{
						det = mat1.determinanteOtimizacao2();
						System.out.println("Segunda Otimizacao:");
						fim = System.nanoTime();
						resultado = fim - inicio;
						mediaOtimizacao2 = mediaOtimizacao2 + resultado;
					}

					System.out.println("O determinante eh " + det);

					//Imprime valor com as unidades de medida
					System.out.println("Resolvido em " + NumberFormat.getIntegerInstance().format(resultado) + " nanossegundos");
					
					if(resultado >= 2000000){
						milissegundos = (float)(resultado / 1000000);
						System.out.println("o que eh " + NumberFormat.getIntegerInstance().format(milissegundos) + " milissegundos!");
					}

					System.out.println();
				}
			}

			//calcula media dos tempos

			System.out.println("Media dos tempos para a matriz de ordem " + ordem);
			System.out.println();

			//Método Base
			mediaBase = (float)(mediaBase / 3);

			System.out.println("Para o Metodo Base, a media foi de " + NumberFormat.getIntegerInstance().format(mediaBase) + " nanossegundos");
			if(mediaBase >= 2000000){
				mediaBase = (float)(mediaBase / 1000000);
				System.out.println("o que eh " + NumberFormat.getIntegerInstance().format(mediaBase) + " milissegundos!");
				System.out.println();
			}

			//Otimização 1
			mediaOtimizacao1 = (float)(mediaOtimizacao1 / 3);

			System.out.println("Para a Primeira Otimizacao, a media foi de " + NumberFormat.getIntegerInstance().format(mediaOtimizacao1) + " nanossegundos");

			if(mediaOtimizacao1 >= 2000000){
				mediaOtimizacao1 = (float)(mediaOtimizacao1 / 1000000);
				System.out.println("o que eh " + NumberFormat.getIntegerInstance().format(mediaOtimizacao1) + " milissegundos!");
				System.out.println();
			}
			

			//Otimização 2
			mediaOtimizacao2 = (float)(mediaOtimizacao2 / 3);

			System.out.println("Para a Segunda Otimizacao, a media foi de " + NumberFormat.getIntegerInstance().format(mediaOtimizacao2) + " nanossegundos");

			if(mediaOtimizacao2 >= 2000000){
				mediaOtimizacao2 = (int)(mediaOtimizacao2 / 1000000);
				System.out.println("o que eh " + NumberFormat.getIntegerInstance().format(mediaOtimizacao2) + " milissegundos!");
				System.out.println();
			}

			System.out.println();
		}
	}
}