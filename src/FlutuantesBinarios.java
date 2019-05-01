import java.util.Scanner;

public class FlutuantesBinarios{
	
/* METODOS */

	/* Recebe um numero em ponto flutuante como parametro, fragmentado em mantissa e expoente. Esses numeros sao
		agrupados em formato de vetor de vetores, tendo como indice 0 o valor referente ao expoente e indice
		1 a mantissa. 
		Formato do numero: .10101010101 * 10 E 10101010 
		Saida: resp = {{expoente}, {mantissa}} */
		
	public static int[][] extraiDigitos(int mantissa, int expoente){
		int[][] resp = new int[2][];
		resp[0] = new int[8];
		resp[1] = new int[23];
		
		for (int i = resp[1].length; mantissa != 0; i--){
			resp[1][i] = mantissa % 10;
			mantissa /= 10;
		}
		
		for (int i = resp[0].length; expoente != 0; i--){
			resp[0][i] = expoente % 10;
			expoente /= 10;
		}
		
		return resp;
	}
	
	/* Retorna um vetor que representa a soma de dois numeros binarios, gerenciando a chamada ao metodo que de fato efetua
		o calculo da soma. Verifica-se o sinal dos mesmos, de modo a orientar o uso dos complementos de 2, tanto nos
		parametros quanto na resposta. Retorna um vetor contendo -1 caso haja Overflow */
		
	public static int[][] somaBinarios(int[][] binario1, int[][] binario2, ExecutaBinarios obj){
		if (igualAZero(binario1[1])){
			obj.bitSinalResp = obj.bitSinal2;
			return binario2;
		}
		
		if (igualAZero(binario2[1])){
			obj.bitSinalResp = obj.bitSinal1;
			return binario1;
		}
	
		int[][] resp = new int[2][];
		resp[0] = new int[8];
		resp[1] = new int[23];
		
		int[][] copiaNum1 = new int[2][];
		resp[0] = new int[8];
		resp[1] = new int[23];
		
		int[][] copiaNum2 = new int[2][];
		resp[0] = new int[8];
		resp[1] = new int[23];
		
		for (int i = 0; i < binario1.length; i++){
			for (int j = 0; j < binario1[i].length; j++){
				copiaNum1[i][j] = binario1[i][j];
				copiaNum2[i][j] = binario2[i][j];
			}
		}
		
		//TRATAR EXCESSO DO EXPOENTE
		//VERIFICAR RIGHT SHIFT COM ZERO A ESQUERDA
		
		int maior = identificaMaior(binario1[0], binario2[0]);
		
		if (maior == 1)
			obj.bitSinalResp = obj.bitSinal1;
		
		else
			obj.bitSinalResp = obj.bitSinal2;
	
		while (maior != 0){
			if (maior == 1){
				soma1(copiaNum2[0]);
				rightShift(copiaNum2[1]);
			}
			
			else{
				soma1(copiaNum1[0]);
				rightShift(copiaNum1[1]);
			}
			
			if (igualAZero(copiaNum1[1])){
				obj.bitSinalResp = obj.bitSinal2;
				return binario2;
			}
			
			if (igualAZero(copiaNum2[1])){
				obj.bitSinalResp = obj.bitSinal1;
				return binario1;
			}
			
			maior = identificaMaior(copiaNum1[0], copiaNum2[0]);
		}
		
		resp[1] = InteirosBinarios.somaBinarios(copiaNum1[1], copiaNum2[1], obj);

		for (int i = 0; i < copiaNum1[0].length; i++)
			resp[0][i] = copiaNum1[0][i];
		
		if (obj.OVERFLOW){
			obj.OVERFLOW = false;
			rightShift(resp[1]);
			soma1(resp[0]);
			
			if (igualAZero(resp[0])){
				obj.OVERFLOW = true;
				return resp;
			}
		}
		
		else{
			int auxSinal1 = obj.bitSinal1;
			int auxSinal2 = obj.bitSinal2;
			obj.bitSinal1 = 0;
			obj.bitSinal2 = 0;
			int[] auxSubtracao = new int[8];
			auxSubtracao[7] = 1;
			
			while (resp[1][0] != 1 && obj.UNDERFLOW == false){
				leftShift(resp[1]);
				resp[0] = InteirosBinarios.subtraiBinarios(resp[0], auxSubtracao, obj);
				
				if (igualAZero(resp[0])) {
					obj.UNDERFLOW = true;
				}
			}
			
			if (obj.UNDERFLOW)
				return resp;
			
			//TRATAR EXCESSO DO EXPOENTE
		}
		
		return resp;
	}
	
	/* Verifica se o conteudo do numero passado por parametro e igual a zero, retornando true se for e false caso contrario */
	
	public static boolean igualAZero(int[] numero){
		for (int i = 0; i < numero.length; i++){
			if (numero[i] != 0)
				return false;
		}
		
		return true;
	}
	
	/* Recebe dois binarios por parametro e retorna um inteiro que identifica qual o maior deles, tendo a seguinte
		codificacao: 1 caso o primeiro binario seja o maior, 2 caso o segundo seja e 0 caso sejam numeros identicos. Esse
		metodo e invocado para tratamento dos casos em que a comparacao por quantidade de bits for insuficiente para 
		determinar qual binario e maior */
		
	public static int identificaMaior(int[] num1, int[] num2){
		int maior; 
		
		for (int i = 0; i < num1.length; i++){
			if (num1[i] > num2[i]){
				maior = 1;
				
				return maior;
			}
			
			else if(num2[i] > num1[i]){
				maior = 2;
				
				return maior;
			}
		}
		
		maior = 0;
		
		return maior;
	}
	
	/* Metodo auxiliar que adiciona um ao bit menos significativo do binario recebido por parametro, retornando-o com o
		resultado da alteracao */
		
	public static void soma1(int[] num){
		for (int i = num.length - 1; i >= 0; i--){
			num[i]++;
			if (num[i] > 1){
				num[i] = 0;
			}
			else break;
		}
	}
	
	/* Recebe um binario por parametro e retorna o resultado de um Right Shift com o mesmo, preservando sempre o primeiro bit,
		que no algoritmo de Booth na multiplicacao e um bit de sinal */
		
	public static void rightShift(int[] numero){
		for (int i = numero.length - 1; i > 0; i--)	
			numero[i] = numero[i - 1];
	}
	
	/* Recebe um numero por parametro e realiza um Left Shift com o mesmo */
	
	public static void leftShift(int[] numero){
		for (int i = 1; i < numero.length; i++)
			numero[i - 1] = numero[i];
		
		numero[numero.length - 1] = 0;
	}
	
	public static void Principal(Scanner scanner, ExecutaBinarios obj){
		// imprimeMenuInteiros();
		// System.out.println("\n /* ------  Quantidade maxima de bits ------*/");
		// obj.qtdeBits = scanner.nextInt();
		// System.out.println("\n /* ------ Primeiro numero ------*/");
		// System.out.println(" Sinal: ");
		// obj.bitSinal1 = scanner.nextInt();
		// System.out.println(" Binario: ");
		// int num1 = scanner.nextInt();
		
		// System.out.println("\n /* ------ Segundo numero ------*/");
		// System.out.println(" Sinal: ");
		// obj.bitSinal2 = scanner.nextInt();
		// System.out.println(" Binario: ");
		// int num2 = scanner.nextInt();
		
		// int[] num1Binario = extraiDigitos(num1, obj);
		// int[] num2Binario = extraiDigitos(num2, obj);
	
		// int[] somaDosBinarios = somaBinarios(num1Binario, num2Binario, obj);
		
		// if (obj.OVERFLOW){
			// System.out.println("\n/* ------ OVERFLOW NA SOMA! ------*/");
			// System.out.println("Parada do programa!");
			// obj.OVERFLOW = false;
			// return;
		// }
		
		// System.out.println("\n /* ------ Resultados das operacoes ------*/");
		
		// System.out.print("\n(n1 + n2) : ");
		// imprimeResposta(somaDosBinarios, obj);
			
		// int[] subtracaoDosBinarios = subtraiBinarios(num1Binario, num2Binario, obj);
		
		// if (obj.OVERFLOW){
			// System.out.println("\n/* ------ OVERFLOW NA SUBTRACAO! ------*/");
			// System.out.println("Parada do programa!");
			// obj.OVERFLOW = false;
			// return;
		// }
		
		// System.out.print("(n1 - n2) : ");
		// imprimeResposta(subtracaoDosBinarios, obj);
		
		// int[] multiplicacaoDosBinarios = multiplicaBinarios(num1Binario, num2Binario, obj);
		
		// System.out.print("(n1 * n2) : ");
		// imprimeResposta(multiplicacaoDosBinarios, obj);
		
		// int[] divisaoDosBinarios = divideBinarios(num1Binario, num2Binario, obj);
		
		// imprimeRespostaDivisao(divisaoDosBinarios, obj);
	}
}