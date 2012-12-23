package com.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Classe que implementa os padroes de criptografia.
 *
 * @author v0rtex
 *
 */


public class Criptografia {

	/**
     * @author v0rtex - hmiranda[at]0fx66.com
     *
     * Metodo que realiza o calculo de hash da senha antes de ser persistida no
     * banco de dados.
     *
     * Utiliza o padrao SHA-256
     *
     * @param senha - String para calcular o hash
     * @return Resultado do calculo de hash.
     *
     */
    public static String encodePassword(String senha) {

            StringBuffer sb = null;

            try {
                    MessageDigest md = MessageDigest.getInstance("SHA-256");
                    md.update(senha.getBytes());

                    byte byteDate[] = md.digest();

                    sb = new StringBuffer();

                    for(int i = 0; i < byteDate.length; i++) {
                            sb.append(Integer.toString((byteDate[i] & 0xff) + 0x100).substring(1));
                    }

            }
            catch (NoSuchAlgorithmException e) {
                    System.out.println("Erro ao realizar o calculo de hash.");

            }

            return sb.toString();
    }

	
}
