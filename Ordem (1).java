public class Ordem{
    private int[] ordens;

    Ordem(){
        //cria o vetor com os numeros das ordens a serem executadas
		ordens = new int[6];
		this.setOrdem(0, 3);
		this.setOrdem(1, 5);
		this.setOrdem(2, 7);
		this.setOrdem(3, 9);
		this.setOrdem(4, 11);
		this.setOrdem(5, 13);
    }

    public int getOrdem(int ordem){
		return this.ordens[ordem];
	}	
	
	public void setOrdem(int ordem, int novoValor){
		this.ordens[ordem] = novoValor;
	}
}