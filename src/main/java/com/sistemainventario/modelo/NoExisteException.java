package com.sistemainventario.modelo;

public class NoExisteException extends Exception {

    public NoExisteException(String mensaje) {
        super(mensaje);
    }
}
