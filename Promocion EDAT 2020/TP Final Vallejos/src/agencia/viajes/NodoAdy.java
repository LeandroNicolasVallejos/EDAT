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
public class NodoAdy {

    //Nodo adyacente para usar en el TDA Grafo
    private NodoVert vertice;
    private NodoAdy sigAdyacente;
    private int etiqueta; //La etiqueta es el tiempo en minutos del camino de un aeropuerto a otro

    public NodoAdy(NodoVert vert, int tiempo) {
        vertice = vert;
        etiqueta = tiempo;
        sigAdyacente = null;
    }

    public NodoVert getVertice() {
        return vertice;
    }

    public void setVertice(NodoVert vertice) {
        this.vertice = vertice;
    }

    public NodoAdy getSigAdyacente() {
        return sigAdyacente;
    }

    public void setSigAdyacente(NodoAdy sigAdyacente) {
        this.sigAdyacente = sigAdyacente;
    }

    public int getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(int etiqueta) {
        this.etiqueta = etiqueta;
    }
}
