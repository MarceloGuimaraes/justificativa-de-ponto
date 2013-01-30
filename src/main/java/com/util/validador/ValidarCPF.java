package com.util.validador;

/*
     * IFPA - Instituto Federal de Educacao, Ciencia e Tecnologia do Pare - Pelo de Tucume
     * Tecnologia de Anelise e Desenvolvimento de Sistemas
     * TAC - Trabalho Academico de Concluseo
     */


/**
 * @author Anderson Marques Neto
 * Matrecula: 200879217 - Turma: C791UE (A) - E-mail: [url="mailto:andersonneto@msn.com"]andersonneto@msn.com[/url]
 */
public class ValidarCPF {

    /** Realiza a validacao do CPF.
     *
     * @param   strCPF nemero de CPF a ser validado
     * @return  true se o CPF e velido e false se neo e velido
     */
    public boolean CPF(String strCpf) {
        if (strCpf.equals("")) {
            return false;
        }
        int d1, d2;
        int digito1, digito2, resto;
        int digitoCPF;
        String nDigResult;

        d1 = d2 = 0;
        digito1 = digito2 = resto = 0;

        for (int nCount = 1; nCount < strCpf.length() - 1; nCount++) {
            digitoCPF = Integer.valueOf(strCpf.substring(nCount - 1, nCount)).intValue();

            //multiplique a ultima casa por 2 a seguinte por 3 a seguinte por 4 e assim por diante.
            d1 = d1 + (11 - nCount) * digitoCPF;

            //para o segundo digito repita o procedimento incluindo o primeiro digito calculado no passo anterior.
            d2 = d2 + (12 - nCount) * digitoCPF;
        }

        //Primeiro resto da diviseo por 11.
        resto = (d1 % 11);

        //Se o resultado for 0 ou 1 o digito e 0 caso contrerio o digito e 11 menos o resultado anterior.
        if (resto < 2) {
            digito1 = 0;
        } else {
            digito1 = 11 - resto;
        }

        d2 += 2 * digito1;

        //Segundo resto da diviseo por 11.
        resto = (d2 % 11);

        //Se o resultado for 0 ou 1 o digito e 0 caso contrerio o digito e 11 menos o resultado anterior.
        if (resto < 2) {
            digito2 = 0;
        } else {
            digito2 = 11 - resto;
        }

        //Digito verificador do CPF que este sendo validado.
        String nDigVerific = strCpf.substring(strCpf.length() - 2, strCpf.length());

        //Concatenando o primeiro resto com o segundo.
        nDigResult = String.valueOf(digito1) + String.valueOf(digito2);

        //comparar o digito verificador do cpf com o primeiro resto + o segundo resto.
        return nDigVerific.equals(nDigResult);
    }
    /* Use este trecho para testar a classe
         public static void main(String[] args) {
         System.out.println( CPF("04624193806") );
         }
          */
}
