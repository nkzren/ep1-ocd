import java.util.Scanner;

/**
 * @author Renan Nakazawa
 * @nUsp 10723836
 * @description Classe que contém o método main do EP
 */
public class EP1OCD {

	public static void main(String[] args) {
		System.out.println("Iniciando multiplicador binário\n");
		System.out.println("Instruções: O usuário entra com 2 números binários a serem multiplicados\n"
				+ "Os números então são exibidos na forma inteira e seu resultado é apresentado\n"
				+ "nas formas inteira e binária. Números negativos devem ser em complemento de 2\n");
		System.out.println("Digite dois números inteiros para iniciar. (Máximo: 16 bits)");

		Booth booth = new Booth();

		Scanner s = new Scanner(System.in);
		int[] num1 = new int[33];
		int[] num2 = new int[33];
		String input1;
		String input2;
		try {
			System.out.print("Primeiro Número: ");
			while (!s.hasNextInt()) {
				System.out.println("Entrada Inválida! Digite novamente");
				s.next();
			}
			input1 = s.next();

			System.out.print("Segundo Número: ");
			while (!s.hasNextInt()) {
				System.out.println("Entrada Inválida! Digite novamente");
				s.next();
			}
			input2 = s.next();

			for (int i = num1.length - input1.length(); i < num1.length; i++) {
				num1[i] = input1.charAt(i - num1.length + input1.length()) - 48;
			}
			for (int i = num2.length - input2.length(); i < num2.length; i++) {
				num2[i] = input2.charAt(i - num2.length + input2.length()) - 48;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		s.close();
	}

}
