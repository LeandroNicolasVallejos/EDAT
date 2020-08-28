/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agencia.viajes;

import java.util.Objects;

/**
 *
 * @author leandro.vallejos
 */
public class ClaveCliente implements Comparable {

    private String tipo;
    private String num;

    public ClaveCliente(String tip, String nume) {
        tipo = tip;
        num = nume;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.tipo);
        hash = 89 * hash + Objects.hashCode(this.num);
        return hash;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return tipo + num;
    }

    @Override
    public boolean equals(Object obj) {
        return this.compareTo(obj) == 0;
    }

    @Override
    public int compareTo(Object o) {
        ClaveCliente clave = (ClaveCliente) o;
        int condicion = this.tipo.compareTo(clave.tipo);
        if (condicion == 0) {
            condicion = this.num.compareTo(clave.num);
        }
        return condicion;
    }

}
