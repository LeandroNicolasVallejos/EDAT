/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agencia.viajes;

/**
 *
 * @author leandro.vallejos
 */
public class Pasaje {

    final private String codVuelo;
    private String fecha;
    private int numAsiento;
    private String estado;

    public Pasaje(String codVuelo, String fecha, int numAsiento) {
        this.codVuelo = codVuelo;
        this.fecha = fecha;
        this.numAsiento = numAsiento;
        this.estado = "pendiente";
    }

    public void setNumAsiento(int numAsiento) {
        this.numAsiento = numAsiento;
    }

    public boolean cambiarEstado(String estad) {
        boolean exito = false;
        if (estad.equalsIgnoreCase("cancelado") || (estad.equalsIgnoreCase("volado"))) {
            estado = estad;
            exito = true;
        }
        return exito;
    }

    @Override
    public String toString() {
        return "Pasaje para Vuelo " + this.codVuelo + ", fecha " + this.fecha + ", estado " + this.estado + " y numero de asiento " + this.numAsiento;
    }

    public String getCodVuelo() {
        return codVuelo;
    }

    public int getNumAsiento() {
        return numAsiento;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        boolean iguales;
        Pasaje pas = (Pasaje) obj;
        iguales = this.codVuelo.equals(pas.getCodVuelo());
        if (iguales) {
            iguales = this.fecha.equals(pas.getFecha());
            if (iguales) {
                iguales = (this.numAsiento == pas.getNumAsiento());
            }
        }
        return iguales;
    }
}
