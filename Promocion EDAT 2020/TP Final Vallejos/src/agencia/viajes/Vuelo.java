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
public class Vuelo {

    final private String codigo;
    private String aeroOrigen;
    private String aeroDestino;
    private String horaEnt;
    private String horaSal;

    public Vuelo(String cod) {
        //Constructor de Vuelo con su clave unica
        codigo = cod;
    }

    public Vuelo(String cod, String aeroOri, String aeroDes, String horaSa, String horaEn) {
        //Constructor de Vuelo con todos los campos
        codigo = cod;
        aeroOrigen = aeroOri;
        aeroDestino = aeroDes;
        horaEnt = horaEn;
        horaSal = horaSa;
    }
    
    
    @Override
    public String toString() {
        return "Vuelo " + this.codigo + " con origen " + this.aeroOrigen + " y destino " + this.aeroDestino + ", sale " + this.horaSal + " y llega " + this.horaEnt;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getAeroOrigen() {
        return aeroOrigen;
    }

    public void setAeroOrigen(String aeroOrigen) {
        this.aeroOrigen = aeroOrigen;
    }

    public String getAeroDestino() {
        return aeroDestino;
    }

    public void setAeroDestino(String aeroDestino) {
        this.aeroDestino = aeroDestino;
    }

    public String getHoraEnt() {
        return horaEnt;
    }

    public void setHoraEnt(String horaEnt) {
        this.horaEnt = horaEnt;
    }

    public String getHoraSal() {
        return horaSal;
    }

    public void setHoraSal(String horaSal) {
        this.horaSal = horaSal;
    }
}
