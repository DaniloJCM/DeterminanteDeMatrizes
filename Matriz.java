import java.util.Random;

public class Matriz{
	private int[][] mat;
	private int tamLinha, tamColuna;

	Matriz(int numIinhas, int numJolunas){
		//cria a matriz
		mat = new int[numIinhas][numJolunas];
		this.setTamanhoLinha(numIinhas);	
		this.setTamanhoColuna(numJolunas);
	}

	public int getElemento(int indiceI,int indiceJ){
		return this.mat[indiceI][indiceJ];
	}	
	
	public void setElemento(int indiceI,int indiceJ, int novoValor){
		this.mat[indiceI][indiceJ] = novoValor;
	}

	public int getTamanhoLinha(){
		return this.tamLinha;
	}

	private void setTamanhoLinha(int novoValor){
		this.tamLinha = novoValor;
	}
	
	public int getTamanhoColuna(){
		return this.tamColuna;
	}	

	private void setTamanhoColuna(int novoValor){
		this.tamColuna = novoValor;
	}

	public void imprime(){
		int contI, contJ;

		//percorre a linha e a coluna para imprimir matriz
		for(contI = 0; contI < this.getTamanhoLinha(); contI++){
			System.out.println();
			for(contJ = 0; contJ < this.getTamanhoColuna(); contJ++){
				System.out.print(this.getElemento(contI, contJ) + " ");
			}
		}
		System.out.println();	
	}

	public void inicializaRandomico(){
		int contI, contJ, novoValor, ordem;
		Random gerador = new Random();
		ordem = this.getTamanhoLinha();

		//atribui valores randomicos às posições
		for(contI = 0; contI < this.getTamanhoLinha(); contI++){
			for(contJ = 0; contJ < this.getTamanhoColuna(); contJ++){
				novoValor = gerador.nextInt(ordem);
				this.setElemento(contI, contJ, novoValor);
			}
		}
	}

	// caso matriz nao quadrada, retorna -1
	public int retorneOrdem(){
		int numI, numJ, ordem;

		numI = this.getTamanhoLinha();
		numJ = this.getTamanhoColuna();
		ordem = -1;

		if(numI == numJ){
			ordem = numI;
		}

		return ordem;
	}	

	//determiante caso a matriz seja 1x1
	private int detOrdem1(Matriz mat){
		return mat.getElemento(0, 0);
	}

	//determinante caso a matriz seja 2x2
	private int detOrdem2(Matriz mat){
		int diagonalP, diagonalI;

		diagonalP = mat.getElemento(0, 0) * mat.getElemento(1, 1);		
		diagonalI = mat.getElemento(1, 0) * mat.getElemento(0, 1);		

		return (diagonalP - diagonalI);
	}

	//confere se tem que subtrair ou somar no calculo do determinante
	private int calculaSinal(int indiceL, int indiceC){
		int sinal;

		sinal = -1;

		if( ((indiceL + indiceC)% 2) == 0 ){
			sinal = 1;
		}

		return sinal;		
	}

	public void copiaMatrizMaiorParaMenor(Matriz maior, Matriz menor, int linhaProibida, int colunaProibida){
		int contIMaior, contJMaior, contIMenor, contJMenor, numI, numJ;
		numI = menor.getTamanhoLinha();
		numJ = menor.getTamanhoColuna();

		contIMaior = 0;
		for(contIMenor = 0; contIMenor < numI; contIMenor++){
			if(contIMaior == linhaProibida){
				contIMaior++;
			}
			contJMaior = 0;
			for(contJMenor = 0; contJMenor < numJ; contJMenor++){
				int temp;
				
				if(contJMaior == colunaProibida){
					contJMaior++;
				}
				temp = maior.getElemento(contIMaior,contJMaior);
				menor.setElemento(contIMenor,contJMenor,temp);
				contJMaior++;
			}
			contIMaior++;
		}
	}

	//calcula determinante para matriz NxN
	private int detOrdemN(Matriz mat){
		int resposta, contI, contJ, numI, numJ;
		Matriz matmenor;
		numI = mat.getTamanhoLinha();
		numJ = mat.getTamanhoColuna();
		
		resposta = 0;
		contI = 0;
		for(contJ = 0; contJ < mat.getTamanhoColuna(); contJ++){
			int sinal, cofator, detTemp;
			cofator = mat.getElemento(contI, contJ);
			sinal = mat.calculaSinal(contI, contJ);
			matmenor = new Matriz((numI - 1), (numJ - 1));
			mat.copiaMatrizMaiorParaMenor(mat, matmenor, contI, contJ);
			detTemp = matmenor.determinante();
			resposta = resposta + (cofator * sinal * detTemp);
		}
		return (resposta);
	}

	//calcula determinante
	public int determinante(){
		int ordem, det;

		ordem = this.retorneOrdem();
		det = 0;

		if(ordem > 0){
			switch (ordem) {
				//se a ordem for 1, executa método da 1x1
			    case 1:  det = this.detOrdem1(this);
				     break;
				//se a ordem for 2, executa método da 2x2
			    case 2:  det = this.detOrdem2(this);;
				     break;
				//se não for nenhuma das anteriores, executa método NxN
			    default: det = this.detOrdemN(this);;
				     break;
			}
			
		}
		else{
			System.out.println("Matriz nao eh quadrada!! retornando 0");
		}

		return det;
	}

	//determinante de NxN com linha ou coluna com mais zeros
	private int detOrdemNOtimizacao1(Matriz mat){
		int resposta, contI, contJ, numI, numJ, linhaAtual, colunaAtual, linha, coluna, indexLinha, indexColuna;
		Matriz matmenor;
		numI = mat.getTamanhoLinha();
		numJ = mat.getTamanhoColuna();
		
		resposta = 0;
		linha = 0;
		coluna = 0;
		indexLinha = 0;
		indexColuna = 0;

		//analisa qual linha tem mais zeros
		for(contI = 0; contI < mat.getTamanhoLinha(); contI++){
			linhaAtual = quantosZerosNaLinha(contI);
			if(linhaAtual > linha){
				linha = linhaAtual;
				indexLinha = contI;
			}
		}

		//analisa qual coluna tem mais zeros
		for(contJ = 0; contJ < mat.getTamanhoColuna(); contJ++){
			colunaAtual = quantosZerosNaColuna(contJ);
			if(colunaAtual > coluna){
				coluna = colunaAtual;
				indexColuna = contJ;
			}
		}	

		if(linha >= coluna){
			//percorre as colunas dessa linha (com mais zeros) e calcula o determinante
			for(contJ = 0; contJ < mat.getTamanhoColuna(); contJ++){
				int sinal, cofator, detTemp;

				cofator = mat.getElemento(indexLinha, contJ);
				//se cofator for 0, nem analisa a linha
				if(cofator != 0){
					sinal = mat.calculaSinal(indexLinha, contJ);
					matmenor = new Matriz((numI - 1), (numJ - 1));
					mat.copiaMatrizMaiorParaMenor(mat, matmenor, indexLinha, contJ);
					detTemp = matmenor.determinanteOtimizacao1();
					resposta = resposta + (cofator * sinal * detTemp);
				}
			}
		}
		else{
			//percorre as linhas dessa coluna (com mais zeros) e calcula o determinante
			for(contI = 0; contI < mat.getTamanhoLinha(); contI++){
				int sinal, cofator, detTemp;

				cofator = mat.getElemento(contI, indexColuna);
				//se cofator for 0, nem analisa a linha
				if(cofator != 0){
					sinal = mat.calculaSinal(contI, indexColuna);
					matmenor = new Matriz((numI - 1), (numJ - 1));
					mat.copiaMatrizMaiorParaMenor(mat, matmenor, contI, indexColuna);
					detTemp = matmenor.determinanteOtimizacao1();
					resposta = resposta + (cofator * sinal * detTemp);
				}
			}
		}

		return (resposta);
	}

	//calcula determinante com novo método da otimizacao 1
	public int determinanteOtimizacao1(){
		int ordem, det;

		ordem = this.retorneOrdem();
		det = 0;

		if(ordem > 0){
			switch (ordem) {
				//se a ordem for 1, executa método da 1x1
			    case 1:  det = this.detOrdem1(this);
				     break;
				//se a ordem for 2, executa método da 2x2
			    case 2:  det = this.detOrdem2(this);;
				     break;
				//se não for nenhuma das anteriores, executa método NxN  com otimização 1
			    default: det = this.detOrdemNOtimizacao1(this);;
				     break;
			}
		}
		else{
			System.out.println("Matriz nao eh quadrada!! retornando 0");
		}

		return det;
	}

	private int quantosZerosNaLinha(int linha){
		int contJ, quantidadeAtual;
		quantidadeAtual = 0;

		//percorre colunas dessa linha e soma à quantidade de zeros
		for(contJ = 0; contJ < this.getTamanhoColuna(); contJ++){
			if(this.getElemento(linha, contJ) == 0){
				quantidadeAtual++;
			}
		}

		return quantidadeAtual;
	}

	private int quantosZerosNaColuna(int coluna){
		int contI, quantidadeAtual;
		quantidadeAtual = 0;

		//percorre linhas dessa coluna e soma à quantidade de zeros
		for(contI = 0; contI < this.getTamanhoLinha(); contI++){
			if(this.getElemento(contI, coluna) == 0){
				quantidadeAtual++;
			}
		}

		return quantidadeAtual;
	}

	//determinante de NxN com linha ou coluna com mais zeros e análise de linhas e colunas iguais
	private int detOrdemNOtimizacao2(Matriz mat){
		int resposta, numI, numJ, linhaAtual, linhaOutra, colunaAtual;
		Matriz matmenor;
		boolean ehProporcional = false;

		resposta = 0;
		numI = mat.getTamanhoLinha();
		numJ = mat.getTamanhoColuna();
		linhaAtual = 0;
		linhaOutra = 1;

		colunaAtual = 0;

		//confere até a penultima linha
		while(((linhaAtual + 1) < this.getTamanhoLinha()) && !ehProporcional){
			linhaOutra = linhaAtual + 1;
			//e compara com a última linha
			while((linhaOutra < this.getTamanhoLinha()) && !ehProporcional){
				ehProporcional = this.linhaEhProporcionalCom(linhaAtual, linhaOutra);
				linhaOutra++;
			}
			linhaAtual++;
		}

		//se não há linhas ou colunas proporcionais, calcula determinante
		if(!ehProporcional){
			int linha, coluna, indexLinha, indexColuna, contI, contJ;
			linha = 0;
			coluna = 0;
			indexLinha = 0;
			indexColuna = 0;

			//confere linha com mais zeros
			for(contI = 0; contI < mat.getTamanhoLinha(); contI++){
				linhaAtual = quantosZerosNaLinha(contI);
				if(linhaAtual > linha){
					linha = linhaAtual;
					indexLinha = contI;
				}
			}
			
			//confere coluna com mais zeros
			for(contJ = 0; contJ < mat.getTamanhoColuna(); contJ++){
				colunaAtual = quantosZerosNaColuna(contJ);
				if(colunaAtual > coluna){
					coluna = colunaAtual;
					indexColuna = contJ;
				}
			}	

			if(linha >= coluna){
				//percorre as colunas dessa linha (com mais zeros) e calcula o determinante
				for(contJ = 0; contJ < mat.getTamanhoColuna(); contJ++){
					int cofator;
					cofator = mat.getElemento(indexLinha, contJ);
					//se cofator for 0, nem analisa a linha
					if(cofator != 0){
						int sinal, detTemp;
						sinal = this.calculaSinal(indexLinha, contJ);
						matmenor = new Matriz((numI - 1), (numJ - 1));
						this.copiaMatrizMaiorParaMenor(mat, matmenor, indexLinha, contJ);
						detTemp = matmenor.determinanteOtimizacao2();
						resposta = resposta + (cofator * sinal * detTemp);
					}
				}
			}
			else{
				for(contI = 0; contI < mat.getTamanhoLinha(); contI++){
					int cofator = mat.getElemento(contI, indexColuna);
					//se cofator for 0, nem analisa a coluna
					if(cofator != 0){
						int sinal, detTemp;
						sinal = this.calculaSinal(contI, indexColuna);
						matmenor = new Matriz((numI - 1), (numJ - 1));
						this.copiaMatrizMaiorParaMenor(mat, matmenor, contI, indexColuna);
						detTemp = matmenor.determinanteOtimizacao2();
						resposta = resposta + (cofator * sinal * detTemp);
					}
				}
			}
		}

		return (resposta);
	}

	//calcula determinante com novo método da otimizacao 2
	public int determinanteOtimizacao2(){
		int ordem, det;

		ordem = this.retorneOrdem();
		det = 0;

		if(ordem > 0){
			switch (ordem) {
				//se a ordem for 1, executa método da 1x1
			    case 1:  det = this.detOrdem1(this);
				     break;
				//se a ordem for 2, executa método da 2x2
			    case 2:  det = this.detOrdem2(this);;
				     break;
				//se não for nenhuma das anteriores, executa método NxN com otimização 2
			    default: det = this.detOrdemNOtimizacao2(this);;
				     break;
			}
		}
		else{
			System.out.println("Matriz nao eh quadrada!! retornando 0");
		}

		return det;
	}

	private boolean linhaEhProporcionalCom(int linha, int outraLinha){
		int valorLinha, valorOutro, proporcao, indexLinhaMaior, indexLinhaMenor, contJ;
		boolean ehNulo, analisaSeEhIgual, analisaSeEhProporcional;
		ehNulo = false;
		analisaSeEhIgual = false;
		analisaSeEhProporcional = false;
		contJ = 0;
		
		//se código prosseguir sem ter feito corretamente, tem que dar erro, por isso esses valores
		indexLinhaMaior = -1;
		indexLinhaMenor = -1;
		proporcao = 0; 

		//se um dos valores for zero, checa outras colunas para saber qual a proporção correta
		valorLinha = this.getElemento(linha, contJ);
		valorOutro = this.getElemento(outraLinha, contJ);

		if((valorLinha != valorOutro) && (valorLinha != 0) && (valorOutro != 0)){
			if(this.ehDivisivel(valorLinha, valorOutro)){
				proporcao = (valorLinha / valorOutro);
				indexLinhaMaior = linha;
				indexLinhaMenor = outraLinha;
				analisaSeEhProporcional = true;
			}
			else if(this.ehDivisivel(valorOutro, valorLinha)){
				proporcao = (valorOutro / valorLinha);
				indexLinhaMaior = outraLinha;
				indexLinhaMenor = linha;
				analisaSeEhProporcional = true;
			}
		}
		else{
			analisaSeEhIgual = true;
		}

		if(analisaSeEhProporcional){
			//checa as colunas e confere se tem proporção
			while((contJ < this.getTamanhoColuna()) && !ehNulo && analisaSeEhProporcional){
				int maior, menor;
				maior = this.getElemento(indexLinhaMaior, contJ);
				menor = this.getElemento(indexLinhaMenor, contJ);

				//menor vezes a proporção definida tem que dar maior para ser proporcional
				if(menor * proporcao != maior){
					analisaSeEhProporcional = false;
				}
				else if((contJ + 1) == this.getTamanhoColuna()){
					ehNulo = true;
				}

				contJ++;
			}
		}
		if(analisaSeEhIgual){
			while((contJ < this.getTamanhoColuna()) && !ehNulo && analisaSeEhIgual){
				int num1, num2;
				num1 = this.getElemento(linha, contJ);
				num2 = this.getElemento(outraLinha, contJ);

				//menor vezes a proporção definida tem que dar maior para ser proporcional
				if(num1 != num2){
					analisaSeEhIgual = false;
				}
				else if((contJ + 1) == this.getTamanhoColuna()){
					ehNulo = true;
				}

				contJ++;
			}
		}

		return ehNulo;
	}

	private boolean ehDivisivel(int numero1, int numero2){
		return (int)(numero1 / numero2) == (float)(numero1 / numero2);
	}
}