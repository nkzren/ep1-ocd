package ep;
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
	public int[] binario1Negativo;
	public int[] binario2;
	public int[] produtoBinario;
	
	
	public void multiplicar(int n1, int n2) {
		
	}

	/**
	 * @description Soma dois números binários e armazena o resultado em bin1
	 * @param bin1 array com o primeiro binário. O resultado da soma é armazenado
	 *             aqui
	 * @param bin2 array com o segundo binário.
	 * @returns void
	 * @throws IllegalArgumentException se os arrays representando os binários não
	 *                                  tiverem o mesmo tamanho (Erro na conversão)
	 */
	public void add(int[] bin1, int[] bin2) throws IllegalArgumentException {
		if (bin1.length != bin2.length) {
			throw new IllegalArgumentException("Erro na conversão dos binários");
		}

		int aux = 0;
		for (int i = bin1.length; i >= 0; i--) {
			int temp = bin1[i] + bin2[i] + aux;
			bin1[i] = temp % 2;
			aux = temp / 2;
		}

	}

	/**
	 * @description Converte um binário em um decimal (foi utilizado long porque não
	 *              cabia no int para a quantidade de bits utilizada)
	 * @param binario Número binário em forma de array de int
	 * @returns long
	 */
	public long toDecimal(int[] binario) {
		long decimal = 0;
		int aux = 1;
		for (int i = binario.length - 1; i >= 0; i--) {
			decimal += binario[i] * aux;
			aux *= 2;
		}
		if (decimal > 1073741824) {
			decimal = -(4294967296l - decimal);
		}
		return decimal;
	}

	/**
	 * @description Converte um número inteiro para um array de int na forma
	 *              binária. Números negativos são convertidos em complemento de 2
	 * @param inteiro Número inteiro
	 * @returns int[]
	 */
	public int[] toBinario(int inteiro) {
		int[] binario = new int[16];
		int index = 15;
		int num = inteiro;

		// Converte para complemento de 2
		if (inteiro < 0) {
			num = (int) Math.pow(2, binario.length) + inteiro;
		}

		while (num != 0) {
			binario[index--] = num % 2;
			num /= 2;
		}

		return binario;
	}

	/**
	 * @description Desloca o binário para a direita
	 * @param binario Número binário em forma de array de int
	 */
	public void rightShift(int[] binario) {
		for (int i = binario.length - 1; i >= 1; i--) {
			binario[i] = binario[i - 1];
		}
	}

	/**
	 * @description Imprime um binário no console, acompanhado do caracter do segundo parâmetro
	 * @param binario Número binário em forma de array de int
	 * @param tipo Caracter para demonstrar o tipo de binário
	 */
	public void imprimeBinario(int[] binario, char tipo) {

	}
	
	/**
	 * @description Função que valida um número dado
	 * @returns boolean
	 */
	public boolean isNumeroInvalido(int[] num) {
		if (null == num || toDecimal(num) < -32767 || toDecimal(num) > 32767) {
			System.out.println("Número inválido inserido. Digite novamente");
			return true;
		}
		return false;
	}

}
