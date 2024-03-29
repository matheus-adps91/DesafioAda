package com.desafio.ada.prospect.fila;

public class Fila {

    private static Fila INSTANCIA;
    private static class No {
        private Object pessoa;
        private No proximo;

        public No(Object cliente) {
            this.pessoa = cliente;
        }
    }

    private No inicio;
    private No fim;

    public static Fila obterInstancia() {
        if (INSTANCIA == null) {
            INSTANCIA = new Fila();
        }
        return INSTANCIA;
    }

    public boolean estaVazia() {
        return  inicio == null;
    }

    public void adicionar(Object pessoa) {
        No no = new No(pessoa);
        if (fim != null) {
            fim.proximo = no;
        }
        fim = no;
        if (inicio == null) {
            inicio = no;
        }
    }

    public Object remover() {
        Object cliente = inicio.pessoa;
        inicio = inicio.proximo;
        if (inicio == null) {
            fim = null;
        }
        return cliente;
    }

}
