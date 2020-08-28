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
public class Aeropuerto {

    final private String codigoAero;
    private String ciudad;
    private String numTel;

    public Aeropuerto(String nomb, String ciu, String num) {
        codigoAero = nomb;
        ciudad = ciu;
        numTel = num;
    }

    public String getCodigoAero() {
        return codigoAero;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getNumTel() {
        return numTel;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }
    
    @Override
    public String toString(){
        String texto = "Aeropuerto "+this.codigoAero + " de "+this.ciudad+", telefono "+this.numTel;
        return texto;
    }

    @Override
    public boolean equals(Object comp) {
//        Aeropuerto aero = (Aeropuerto) comp;
        return (this.codigoAero.equals(comp));
    }
}
