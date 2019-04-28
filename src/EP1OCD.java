import java.util.Scanner;

/**
 * @author 	Renan Nakazawa
 * @nUsp	10723836
 * @description Classe principal do EP
 */
public class EP1OCD {
	
	public static void main(String[] args) {
		System.out.println("Iniciando multiplicador binário");
		System.out.println("Digite dois números inteiros para iniciar. (Módulo máximo: 16384)");
		Scanner s;
		s = new Scanner(System.in);
		Integer num1 = null; 
		Integer num2 = null;
		try {
			do {
				System.out.print("Primeiro Número: ");
				if (s.hasNextInt()) {
					num1 = s.nextInt();					
				}
				System.out.print("Segundo Número: ");
				if (s.hasNextInt()) {
					num2 = s.nextInt();					
				}
			} while (!validarNumero(num1) || !validarNumero(num2));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Booth booth = new Booth();
		
		s.close();
	}
	
	public static boolean validarNumero(Integer num) {
		if (null == num || num < -16384 || num > 16384) {
			System.out.println("Número inválido inserido. Digite novamente");
			return false;
		}
		return true;
	}
	
}
