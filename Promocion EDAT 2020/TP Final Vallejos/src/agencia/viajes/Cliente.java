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
public class Cliente {

    final private ClaveCliente clave;
    private String apellidos;
    private String nombres;
    private String fechaNac;
    private String numTel;
    private String domicilio;

    public Cliente(ClaveCliente clav) {
        //Constructor sólo con la clave de Cliente
        clave = clav;
    }

    public Cliente(String tipo, String num, String ape, String nom, String fechaN, String tel, String dom) {
        //Constructor completo con todos los campos
        clave = new ClaveCliente(tipo, num);
        apellidos = ape;
        nombres = nom;
        fechaNac = fechaN;
        numTel = tel;
        domicilio = dom;
    }

    @Override
    public String toString() {
        String dev = "Cliente " + clave.toString() + ", llamado " + nombres + " " + apellidos;
//        dev = dev + "\n" + "	Nacio el " + fechaNac + ", telefono " + numTel + " y vive en " + domicilio;
        return dev;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.clave);
        return hash;
    }

    @Override
    public boolean equals(Object cla) {
        ClaveCliente clave1 = (ClaveCliente) cla;
        return (this.clave.compareTo(clave1) == 0);
    }

    public ClaveCliente getClave() {
        return clave;
    }

    public String getTipoDNI() {
        return clave.getTipo();
    }

    public String getNumDNI() {
        return clave.getNum();
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getNumTel() {
        return numTel;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }
}
