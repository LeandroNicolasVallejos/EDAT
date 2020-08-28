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
public class NodoVert {

    //Nodo vertice para usar en el TDA Grafo
    private Object elem;
    private NodoVert sigVertice;
    private NodoAdy primerAdy;

    public NodoVert(Object el, NodoVert vertSig) {
        elem = el;
        sigVertice = vertSig;
        primerAdy = null;
    }

    public Object getElem() {
        return elem;
    }

    public void setElem(Aeropuerto elem) {
        this.elem = elem;
    }

    public NodoVert getSigVertice() {
        return sigVertice;
    }

    public void setSigVertice(NodoVert sigVertice) {
        this.sigVertice = sigVertice;
    }

    public NodoAdy getPrimerAdy() {
        return primerAdy;
    }

    public void setPrimerAdy(NodoAdy primerAdy) {
        this.primerAdy = primerAdy;
    }

}
