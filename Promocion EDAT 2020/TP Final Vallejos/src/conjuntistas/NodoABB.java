package conjuntistas;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author leandro.vallejos
 */
public class NodoABB {

    private Comparable elem;
    private NodoABB izquierdo;
    private NodoABB derecho;

    //Constructor de NodoABB
    public NodoABB(Comparable elem) { //Constructor con solo el objeto
        this.elem = elem;
    }

    public NodoABB(Comparable elem, NodoABB izquierdo, NodoABB derecho) { //Constructor con todos los datos
        this.elem = elem;
        this.izquierdo = izquierdo;
        this.derecho = derecho;
    }

    //Observadores
    public Comparable getElem() {
        return this.elem;
    }

    public NodoABB getIzquierdo() {
        return this.izquierdo;
    }

    public NodoABB getDerecho() {
        return this.derecho;
    }

    //Modificadores
    public void setElem(Comparable unElem) {
        this.elem = unElem;
    }

    public void setIzquierdo(NodoABB unIzq) {
        this.izquierdo = unIzq;
    }

    public void setDerecho(NodoABB unDer) {
        this.derecho = unDer;
    }
}
