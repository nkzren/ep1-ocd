package ep;
import java.util.Scanner;

public class ExecutaBinarios{
	
/* ATRIBUTOS */

	public static boolean OVERFLOW = false;
	public static boolean UNDERFLOW = false;
	public static int bitSinalExp1 = 0;
	public static int bitSinalExp2 = 0;
	public static int bitSinal1 = 0;
	public static int bitSinal2 = 0;
	public static int bitSinalExpResp = 0;
	public static int bitSinalResp = 0;
	public static int qtdeBits = 0;
	
/* ----------------------- Main ---------------------------*/

	public static void main(String[] args){
		Scanner scanner = new Scanner(System.in);
		ExecutaBinarios obj;
		int reinicia = 10000;
		System.out.println("/* -------------------- Bem-vindo! ------------------------*/ \n");
		while(true){
			obj = new ExecutaBinarios();
			System.out.println("\n");
			System.out.println("/* ----------------------- MENU PRINCIPAL ---------------------------*/");
			System.out.println("\n");
			System.out.println("Calculo com inteiro	    = Digite 1");
			System.out.println("Calculo com ponto flutuante = Digite 2");
			System.out.println("Encerrar                    = Digite qualquer outro numero");
			reinicia = scanner.nextInt();
			if (reinicia == 1) InteirosBinarios.Principal(scanner, obj);
			else if (reinicia == 2) FlutuantesBinarios.Principal(scanner, obj);
			else break;
		}
	}
}