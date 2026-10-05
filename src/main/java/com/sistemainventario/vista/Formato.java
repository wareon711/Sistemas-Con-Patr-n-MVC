package com.sistemainventario.vista;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Formato {

    public static String moneda(double valor) {
        DecimalFormatSymbols simbolos = new DecimalFormatSymbols(Locale.US);
        simbolos.setGroupingSeparator(',');
        simbolos.setDecimalSeparator('.');
        DecimalFormat formato = new DecimalFormat("#,##0.00", simbolos);
        return "$" + formato.format(valor);
    }
}
