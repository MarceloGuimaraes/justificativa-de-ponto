package com.util;

public class testMessage {

	public static void main(String[] args) {
		String teste = Message.getBundleMessage("pagina.nao.encontrada");
		System.out.println(teste);
	}
}
