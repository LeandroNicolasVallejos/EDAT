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
import lineales.dinamicas.Lista;

public class Viaje {

    private final String fecha;
    private int asientosTotal;
    private int asientosVendidos;
    private Lista listaPasajes;

    public Viaje(String fech) {
        this.fecha = fech;
        this.asientosTotal = 200;
        this.asientosVendidos = 0;
        this.listaPasajes = new Lista();
    }

    public Lista getListaPasajes() {
        return this.listaPasajes;
    }

    @Override
    public String toString() {
        return listaPasajes.toString();
    }

    public boolean viajar() {
        //Cambia los estados de los pasajes de este viaje a "volado"
        boolean exito = false;
        Lista aux = this.listaPasajes;
        if (aux != null) {
            for (int i = 1; i <= this.listaPasajes.longitud(); i++) {
                Pasaje pasAux = (Pasaje) aux.recuperar(i);
                pasAux.cambiarEstado("volado");
            }
            exito = true;
        }
        return exito;
    }

    public boolean cancelar() {
        //Cambia los estados de los pasajes de este viaje a "cancelado"
        boolean exito = false;
        Lista aux = this.listaPasajes;
        if (aux != null) {
            for (int i = 1; i <= this.listaPasajes.longitud(); i++) {
                Pasaje pasAux = (Pasaje) aux.recuperar(i);
                pasAux.cambiarEstado("cancelado");
            }
            exito = true;
        }
        return exito;
    }

    public boolean borrarPasajeAlViaje(Pasaje pas) {
        int locacion = this.listaPasajes.localizar(pas);
        return this.listaPasajes.eliminar(locacion);
    }

    public boolean agregarPasajeAlViaje(Pasaje pas) {
        return this.listaPasajes.insertar(pas, this.listaPasajes.longitud() + 1);
    }

    public boolean checkAsientoDisponible(String codVuelo, int numAsiento) {
        Pasaje pas = new Pasaje(codVuelo, this.fecha, numAsiento);
        int posicion = this.listaPasajes.localizar(pas);
        return posicion == -1; //Verdadero si el asiento esta disponible
    }

    public boolean tieneLugar() {
        return (this.asientosTotal > this.asientosVendidos);
    }

    public boolean aumentarVendidos(int nuevosVendidos) {
        boolean exito = false;
        int aux = this.asientosVendidos + nuevosVendidos;
        if (this.asientosTotal >= aux) {
            this.asientosVendidos = aux;
            exito = true;
        }
        return exito;
    }

    public boolean reducirVendidos(int nuevosEliminados) {
        boolean exito = false;
        int aux = this.asientosVendidos - nuevosEliminados;
        if (aux >= 0) {
            this.asientosVendidos = aux;
            exito = true;
        }
        return exito;
    }

    public String getFecha() {
        return this.fecha;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.fecha);
        hash = 53 * hash + this.asientosTotal;
        hash = 53 * hash + this.asientosVendidos;
        return hash;
    }

    @Override
    public boolean equals(Object fecha) {
        return (this.fecha.equals(fecha));
    }

    public int getAsientosTotal() {
        return asientosTotal;
    }

    public void setAsientosTotal(int asientosTotal) {
        this.asientosTotal = asientosTotal;
    }

    public int getAsientosVendidos() {
        return asientosVendidos;
    }

    public void setAsientosVendidos(int asientosVendidos) {
        this.asientosVendidos = asientosVendidos;
    }
}
