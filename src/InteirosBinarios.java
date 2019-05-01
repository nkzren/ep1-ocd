import java.util.Scanner;

public class InteirosBinarios{
	
/* METODOS */
	
	/* Recebe um numero inteiro como parametro, retornando um vetor cujos indices contem cada bit do numero */
		
	public static int[] extraiDigitos(int num, ExecutaBinarios obj){
		int[] resp = new int[obj.qtdeBits];
		
		for (int i = obj.qtdeBits - 1; num != 0; i--){
			resp[i] = num % 10;
			num /= 10;
		}
		
		return resp;
	}

	/* Retorna um vetor que representa a soma de dois numeros binarios, gerenciando a chamada ao metodo que de fato efetua
		o calculo da soma. Verifica-se o sinal dos mesmos, de modo a orientar o uso dos complementos de 2, tanto nos
		parametros quanto na resposta. Retorna um vetor contendo -1 caso haja Overflow */
		
	public static int[] somaBinarios(int[] binario1, int[] binario2, ExecutaBinarios obj){
		if (igualAZero(binario1)){
			obj.bitSinalResp = obj.bitSinal2;
			return binario2;
		}
		
		if (igualAZero(binario2)){
			obj.bitSinalResp = obj.bitSinal1;
			return binario1;
		}
	
		int[] resp = new int[binario1.length];
		int maior = identificaMaior(binario1, binario2);
		
		if (maior == 1)
			obj.bitSinalResp = obj.bitSinal1;
		
		else
			obj.bitSinalResp = obj.bitSinal2;
		
		if (obj.bitSinal1 != obj.bitSinal2){
			if (obj.bitSinal1 == 0)
				resp = calculoSoma(binario1, complementoDe2(binario2));
				
			else
				resp = calculoSoma(complementoDe2(binario1), binario2);
				
			if (obj.bitSinalResp == 1)
				resp = complementoDe2(resp);
		}
		else{
			if (binario1[0] == 1 && binario2[0] == 1)
				obj.OVERFLOW = true;
			
			else if (2 == identificaMaior(complementoDe1(binario1), binario2))
				obj.OVERFLOW = true;
			
			else if (obj.bitSinal1 == 0)
				resp = calculoSoma(binario1, binario2);

			else{ 
				resp = calculoSoma(complementoDe2(binario1), complementoDe2(binario2));
				resp = complementoDe2(resp);
			}
		}
		
		return resp;
	}
	
	/* Retorna como resposta um vetor com o calculo da soma devidamente efetuado, recebendo como parametros dois numeros
		Um auxiliar e utilizado, que armazena os "vai um" */
		
	public static int[] calculoSoma(int[] binario1, int[] binario2){
		int aux = 0;
		int[] resp = new int[binario1.length];
		
		for (int i = resp.length - 1; i >= 0; i--){
			resp[i] = aux + binario1[i] + binario2[i];
			
			if (resp[i] > 1){
				resp[i] -= 2;
				aux = 1;
			}
			
			else
				aux = 0;
		}
		
		return resp;
	}
	
	/* Recebe um numero por parametro e retorna um vetor que representa seu complemento de 1, substituindo cada 1 por 0
		e cada 0 por um */
		
	public static int[] complementoDe1(int[] num){
		int[] resp = new int[num.length];
		
		for (int i = 0; i < num.length; i++){
			if (num[i] == 0)
				resp[i] = 1;
			
			else
				resp[i] = 0;
		}
		
		return resp;
	}
	
	/* Recebe um vetor que representa um numero binario como parametro e retorna um vetor correspondente ao seu complemento
		de 2. Extrai o complemento de 1 do mesmo e, por fim, adiciona 1 ao bit menos significativo,	utilizando um metodo 
		auxiliar que economiza processamento se comparado com o padrao utilizado para somar dois numeros quaisquer */
		
	public static int[] complementoDe2(int[] num){
		int[] resp = complementoDe1(num);
		
		soma1(resp);
		
		return resp;
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
	
	/* Subtrai o numero 2 do numero 1, retornando a resposta. Para isso, inverte o bit de sinal do segundo numero e realiza 
		uma operacao de soma com os numeros, uma vez que modulo de soma trata os complementos de 2 necessarios. Antes de
		retornar, inverte novamente o sinal do numero 2 para prevenir erros futuros */
	
	public static int[] subtraiBinarios(int[] num1, int[] num2, ExecutaBinarios obj){
		if (igualAZero(num1)){
			obj.bitSinalResp = inverteSinal(obj.bitSinal2);
			return num2;
		}
		
		if (igualAZero(num2)){
			obj.bitSinalResp = obj.bitSinal1;
			return num1;
		}
		
		int[] resp;
		
		obj.bitSinal2 = inverteSinal(obj.bitSinal2);
		
		resp = somaBinarios(num1, num2, obj);
		
		obj.bitSinal2 = inverteSinal(obj.bitSinal2);
		
		return resp;
	}
	
	/* Recebe um numero por parametro e inverte o bit de sinal do mesmo*/
	
	public static int inverteSinal(int sinal){
		if (sinal == 0)
			sinal = 1;
		
		else
			sinal = 0;
		
		return sinal;
	}

	/* Imprime um binario que pode ser resposta da soma, subtracao ou multiplicacao entre dois numeros, tendo o seguinte
		layout: bit de sinal -> espaco -> bits significativos -> (+/- valor em decimal) */
		
	public static void imprimeResposta(int[] resp, ExecutaBinarios obj){
		System.out.print(obj.bitSinalResp + " ");
		
		for (int i = 0; i < resp.length; i++)
			System.out.print(resp[i]);
		
		if (obj.bitSinalResp == 0)
			System.out.print(" (+");
		
		else
			System.out.print(" (-");
		
		imprimeBinarioParaDecimal(resp);
		
		System.out.println(")");
	}
	
	/* Imprime um numero inteiro convertido de binario para decimal, a fim de simplificar a visualizacao das respostas */
	
	public static void imprimeBinarioParaDecimal(int[] numero){
		int resp = 0;
		int expoente = 0;
		
		for (int i = numero.length - 1; i >= 0; i--){
			if (numero[i] == 1)
				resp += (int)Math.pow(2, expoente);
			expoente++;
		}

		System.out.print(resp);
	}
	
	/* Implementacao do algoritmo de Booth para a multiplicacao. Tomado como exemplo a explicacao do Wikipedia, utilizamos 3 
		vetores auxiliares e um	para a armazenar o Produto. Os auxiliares sao:
			Adicao: inicializado com o valor do numero 1 e que no caso 01 e somado com o Produto
			ComplementoNum1: que recebe o resultado do complemento de 2 do numero 1
			Subtracao: inicializado com o valor do de complementoNum1 e que no caso 10 e somado com o Produto
		Um contador e criado para estabelecer a quantidade de ciclos necessarios para se obter a resposta. A cada iteracao
		verificam-se os ultimos dois bits de Produto. Se os bits forem iguais nada ocorre, nos demais casos o uso se dara
		conforme especificado acima. Ao fim de toda iteracao um Right Shift ocorre. Finalmente, o ultimo bit do Produto e
		descartado e a resposta esta pronta, sendo esse vetor o retornado pelo metodo */
		
	public static int[] multiplicaBinarios(int[] num1, int[] num2, ExecutaBinarios obj){
		if (igualAZero(num1))
			return num1;
		
		if (igualAZero(num2))
			return num2;
		
		int[] adicao = new int[num1.length * 2 + 1];
		int[] complementoNum1 = complementoDe2(num1);
		int[] subtracao = new int[num1.length * 2 + 1];
		int[] produto = new int[num1.length * 2 + 1];
		
		for (int i = 0; i < num1.length; i++){
			adicao[i] = num1[i];
			subtracao[i] = complementoNum1[i];
		}
		
		int iteradorProduto = produto.length - 2;
		int iteradorNum2 = num2.length - 1;
		
		while (iteradorNum2 >= 0){
			produto[iteradorProduto] = num2[iteradorNum2];
			
			iteradorNum2--;
			iteradorProduto--;
		}
		
		for (int contador = num1.length; contador > 0; contador--){
			if (produto[produto.length - 2] == 0 && produto[produto.length - 1] == 1)
				produto = calculoSoma(produto, adicao);
		
			else if (produto[produto.length - 2] == 1 && produto[produto.length - 1] == 0)
				produto = calculoSoma(produto, subtracao);
			
			rightShift(produto);
		}
	
		produto = descartaUltimoBit(produto);
		
		if ((obj.bitSinal1 == 1 || obj.bitSinal2 == 1) && !(obj.bitSinal1 == 1 && obj.bitSinal2 == 1))
			obj.bitSinalResp = 1;
		
		else
			obj.bitSinalResp = 0;
	
		return produto;
	}
	
	/* Recebe um binario por parametro e retorna o resultado de um Right Shift com o mesmo, preservando sempre o primeiro bit,
		que no algoritmo de Booth na multiplicacao e um bit de sinal */
		
	public static void rightShift(int[] numero){
		for (int i = numero.length - 1; i > 0; i--)	
			numero[i] = numero[i - 1];
	}
	
	/* Recebe um binario por parametro e retorna uma copia desse numero somente descartando o ultimo bit. Usado para
		finalizar o procedimento do algoritmo de Booth para a multiplicacao */
	
	public static int[] descartaUltimoBit(int[] numero){
		int[] resp = new int[numero.length - 1];
		
		for (int i = 0; i < resp.length; i++)
			resp[i] = numero[i];
		
		return resp;
	}
	
	/* Recebe por parametro dois binarios, sendo o primeiro o dividendo e o segundo o divisor, retornando um vetor com
		duas subdivisoes, sendo a primeira metade o valor do quociente e a segunda o resto da divisao. 
		A implementacao desse metodo foi inspirada no algoritmo descrito no livro 
		"Arquitetura e Organizacao de Computadores" - Stallings W. */
		
	public static int[] divideBinarios(int[] dividendo, int[] divisor, ExecutaBinarios obj){
		int[] quociente;
		
		if (igualAZero(divisor)){
			System.out.println("ERRO: impossivel dividir por ZERO");
			
			quociente = new int[1];
			quociente[0] = -1;
		
			return quociente;
		}
		
		if (igualAZero(dividendo)){
			quociente = new int[2];
			
			return quociente;
		}
		
		if ((obj.bitSinal1 == 0 && obj.bitSinal2 == 0) || (obj.bitSinal1 == 1 && obj.bitSinal2 == 1))
			obj.bitSinalResp = 0;
		
		else
			obj.bitSinalResp = 1;
		
		quociente = new int[2 * dividendo.length];
		
		int maior = identificaMaior(dividendo, divisor);
		
		if (maior == 0){
			quociente[quociente.length - 1] = 1;
			
			return quociente;
		}
		
		int[] resto = new int[dividendo.length];
		
		if (obj.bitSinal1 == 1){
			dividendo = complementoDe2(dividendo);
		
			for (int i = 0; i < resto.length; i++)
				resto[i] = 1;
		}
		
		int iteradorDividendo = dividendo.length - 1;
		int iteradorQuociente = quociente.length - 1;
		int iteradorResto = 0;
		
		while (iteradorDividendo >= 0){
			quociente[iteradorQuociente] = dividendo[iteradorDividendo];
			quociente[iteradorResto] = resto[iteradorResto];
			
			iteradorDividendo--;
			iteradorQuociente--;
			iteradorResto++;
		}
		
		iteradorDividendo = 0;
		
		while (iteradorDividendo < dividendo.length){
			leftShift(quociente);
			
			copiaMetadeQuociente(quociente, resto);
			
			if (obj.bitSinal1 == 0)
				resto = calculoSoma(resto, complementoDe2(divisor));
			
			else
				resto = calculoSoma(resto, divisor);
			
			if (obj.bitSinal1 == resto[0]){
				quociente[quociente.length - 1] = 1;
				atualizaQuociente(quociente, resto);
			}
			
			iteradorDividendo++;
		}
		
		if (obj.bitSinal1 == 1){
			copiaMetadeQuociente(quociente, resto);
			
			resto = complementoDe2(resto);
			
			atualizaQuociente(quociente, resto);
		}
		
		return quociente;
	}
	
	/* Verifica se o conteudo do numero passado por parametro e igual a zero, retornando true se for e false caso contrario */
	
	public static boolean igualAZero(int[] numero){
		for (int i = 0; i < numero.length; i++){
			if (numero[i] != 0)
				return false;
		}
		
		return true;
	}
	
	/* Recebe um numero por parametro e realiza um Left Shift com o mesmo */
	
	public static void leftShift(int[] numero){
		for (int i = 1; i < numero.length; i++)
			numero[i - 1] = numero[i];
		
		numero[numero.length - 1] = 0;
	}
	
	/* Recebe dois numeros por parametro e copia metade do vetor do primeiro no segundo */
	
	public static void copiaMetadeQuociente(int[] quociente, int[] resto){
		for (int i = 0; i < quociente.length / 2; i++)
			resto[i] = quociente[i];
	}
	
	/* Recebe dois numeros por parametro e atualiza o primeiro com os valores do segundo */
	
	public static void atualizaQuociente(int[] quociente, int[] resto){
		for (int i = 0; i < resto.length; i++)
			quociente[i] = resto[i];
	}
	
	/* Imprime o resultado de uma divisao informando o quociente, o resto e, por fim, especificando se o valor foi 
		positivo ou negativo */
		
	public static void imprimeRespostaDivisao(int[] numero, ExecutaBinarios obj){
		if (numero[0] == -1)
			return;
		
		if (igualAZero(numero)){
			System.out.println("(n1 / n2) = 0\n(n1 % n2) = 0");
			return;
		}
		
		int[] resto = new int [numero.length / 2];
		int[] quociente = new int[numero.length / 2];
		
		for (int i = 0; i < resto.length; i++)
			resto[i] = numero[i];
		
		for (int i = 0; i < quociente.length; i++)
			quociente[i] = numero[i + quociente.length];
				
		System.out.print("(n1 / n2) = ");
		
		for (int bit : quociente)
			System.out.print(bit);
		
		System.out.print(" (");
		imprimeBinarioParaDecimal(quociente);
		System.out.println(")");
	
		System.out.print("(n1 % n2) = ");
		
		for (int bit : resto)
			System.out.print(bit);
		
		System.out.print(" (");
		imprimeBinarioParaDecimal(resto);
		System.out.println(")");
		
		if (obj.bitSinalResp == 0)
			System.out.println("Divisao Positiva(+)");
		
		else
			System.out.println("Divisao Negativa(-)");
	}
	
	public static void imprimeMenuInteiros(){
		
		System.out.println("\n/* ----------------------- MENU INTEIROS ---------------------------*/\n");
		System.out.println("Esse modulo funciona da seguinte maneira:");
		System.out.println("\t1) Informe a quantidade de bits total que deseja trabalhar \n\t   (sem considerar bit de sinal);");
		System.out.println("\t2) Informe qual o bit de sinal desejado (0 = positivo / 1 = negativo);");
		System.out.println("\t3) Informe um numero ja em formato binario;");
		System.out.println("\t4) Repita os procedimentos anteriores (2 e 3) para entrar com um segundo numero.");
		System.out.println("\nO programa entao efetuara as quatro operacoes com esses dois numeros no seguinte formato:");
		System.out.println("\t1) (Numero 1 + Numero 2)");
		System.out.println("\t2) (Numero 1 - Numero 2)");
		System.out.println("\t3) (Numero 1 * Numero 2)");
		System.out.println("\t4) (Numero 1 / Numero 2)");
		System.out.println("\t5) (Numero 1 % Numero 2)");
		System.out.println("\t6) Divisao Positiva / Negativa (+ / -)");
	}
	
	public static void Principal(Scanner scanner, ExecutaBinarios obj){
		imprimeMenuInteiros();
		System.out.println("\n /* ------  Quantidade maxima de bits ------*/");
		obj.qtdeBits = scanner.nextInt();
		System.out.println("\n /* ------ Primeiro numero ------*/");
		System.out.println(" Sinal: ");
		obj.bitSinal1 = scanner.nextInt();
		System.out.println(" Binario: ");
		int num1 = scanner.nextInt();
		
		System.out.println("\n /* ------ Segundo numero ------*/");
		System.out.println(" Sinal: ");
		obj.bitSinal2 = scanner.nextInt();
		System.out.println(" Binario: ");
		int num2 = scanner.nextInt();
		
		int[] num1Binario = extraiDigitos(num1, obj);
		int[] num2Binario = extraiDigitos(num2, obj);
	
		int[] somaDosBinarios = somaBinarios(num1Binario, num2Binario, obj);
		
		if (obj.OVERFLOW){
			System.out.println("\n/* ------ OVERFLOW NA SOMA! ------*/");
			System.out.println("Parada do programa!");
			obj.OVERFLOW = false;
			return;
		}
		
		System.out.println("\n /* ------ Resultados das operacoes ------*/");
		
		System.out.print("\n(n1 + n2) : ");
		imprimeResposta(somaDosBinarios, obj);
			
		int[] subtracaoDosBinarios = subtraiBinarios(num1Binario, num2Binario, obj);
		
		if (obj.OVERFLOW){
			System.out.println("\n/* ------ OVERFLOW NA SUBTRACAO! ------*/");
			System.out.println("Parada do programa!");
			obj.OVERFLOW = false;
			return;
		}
		
		System.out.print("(n1 - n2) : ");
		imprimeResposta(subtracaoDosBinarios, obj);
		
		int[] multiplicacaoDosBinarios = multiplicaBinarios(num1Binario, num2Binario, obj);
		
		System.out.print("(n1 * n2) : ");
		imprimeResposta(multiplicacaoDosBinarios, obj);
		
		int[] divisaoDosBinarios = divideBinarios(num1Binario, num2Binario, obj);
		
		imprimeRespostaDivisao(divisaoDosBinarios, obj);
	}
}