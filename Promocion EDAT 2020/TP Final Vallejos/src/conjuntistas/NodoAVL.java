/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conjuntistas;

/**
 *
 * @author leandro.vallejos
 */
public class NodoAVL {

    private Comparable clave;
    private Object elem;
    private NodoAVL izq;
    private NodoAVL der;
    private int altura;

    public NodoAVL(Comparable clave, Object elem) {
        this.clave = clave;
        this.elem = elem;
        this.izq = null;
        this.der = null;
        this.altura = 0;
    }

    public NodoAVL(Comparable clave, Object elem, NodoAVL i, NodoAVL d) {
        this.clave = clave;
        this.elem = elem;
        this.izq = i;
        this.der = d;
        this.altura = 0;
    }

    public NodoAVL() {
        this.elem = null;
        this.clave = null;
        this.izq = null;
        this.der = null;
        this.altura = 0;
    }

    public Comparable getClave() {
        return clave;
    }

    public Object getElem() {
        return elem;
    }

    public void setElem(Object elem) {
        this.elem = elem;
    }

    public void setElem(Comparable e) {
        this.elem = e;
    }

    public NodoAVL getIzquierdo() {
        return izq;
    }

    public void setIzquierdo(NodoAVL i) {
        this.izq = i;
    }

    public NodoAVL getDerecho() {
        return der;
    }

    public void setDerecho(NodoAVL d) {
        this.der = d;
    }

    public int getAltura() {
        return altura;
    }

    public void calcularAltura() {
        if (this.izq == null && this.der == null) {
            this.altura = 0;
        } else {
            if (this.izq != null && this.der == null) {
                this.altura = 1 + this.izq.getAltura();
            } else {
                if (this.izq == null && this.der != null) {
                    this.altura = 1 + this.der.getAltura();
                } else {
                    this.altura = 1 + Math.max(this.izq.getAltura(), this.der.getAltura());
                }
            }
        }

    }
}
