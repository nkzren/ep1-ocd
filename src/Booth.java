/**
 * @author Renan Nakazawa
 * @nUsp 10723836
 * @description Classe que contém a implementação do algoritmo de Booth
 * @references https://www.sanfoundry.com/java-program-booth-algorithm/
 *             https://en.wikipedia.org/wiki/Booth%27s_multiplication_algorithm
 *             https://www.geeksforgeeks.org/booths-multiplication-algorithm/
 */
public class Booth {
	public int[] binario1;
	public int[] binario2;
	public int[] produtoBinario;

	public int toDecimal(int[] binario) {
		int decimal = 0;
		int aux = 1;
		for (int i = binario.length - 1; i > 0; i++) {
			decimal += aux * binario[i];
		}
		return 0;
	}

	public int[] toBinario(int inteiro) {
        int[] binario = new int[16];
        int index = 15;
        int num = inteiro;
        
        if (inteiro < 0) {
        	num = (int) Math.pow(2, binario.length) + inteiro;
        }

        while (num != 0) {
            binario[index--] = num % 2;
            num /= 2;
        }

        return binario;
	}

	public void rightShift(int[] binario) {
        for (int i = binario.length; i >= 1; i--) {
        	binario[i] = binario[i - 1];                	
        }
	}
	
	public void imprime(int[] binario, char tipo) {
		
	}
	
	

}
