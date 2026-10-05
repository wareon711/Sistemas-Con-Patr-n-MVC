package com.sistematareas.modelo;

public class NoExisteException extends Exception {

    public NoExisteException(String mensaje) {
        super(mensaje);
    }
}
