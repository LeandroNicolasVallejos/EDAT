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
import lineales.dinamicas.*;

public class Grafo {

    //TDA Grafo (implementado para la red de Aeropuertos)
    private NodoVert inicio;

    public Grafo() {
        inicio = null;
    }

    public Object getElem(Object cod) {
        Object dev = null;
        NodoVert aux = ubicarVertice(cod);
        if (aux != null) {
            dev = aux.getElem();
        }
        return dev;
    }

    public boolean insertarVertice(Object elem) {
        boolean exito = false;
        NodoVert aux = this.ubicarVertice(elem);
        if (aux == null) {
            this.inicio = new NodoVert(elem, this.inicio);
            exito = true;
        }
        return exito;
    }

    private Object[] caminoMasCortoPorMinutosAux(NodoVert n, Object dest, Object[] ret, Lista l, int minutos) {
        if (n != null) {
            //si vertice n es igual al destino, hay camino
            if (n.getElem().equals(dest)) {
                Lista lis = (Lista) ret[0];
                if (((lis.esVacia()) || (int) ret[1] > minutos)) {
                    if (!l.esVacia()) {
                        lis = l.clone();
                    }
                    lis.insertar(dest, lis.longitud() + 1);
                    ret[0] = lis;
                    ret[1] = minutos;
                }
            } else {
                l.insertar(((Aeropuerto) n.getElem()).getCodigoAero(), l.longitud() + 1);
                NodoAdy ady = n.getPrimerAdy();
                while (ady != null) {
                    if (l.localizar(((Aeropuerto) ady.getVertice().getElem()).getCodigoAero()) < 0) {
                        ret = caminoMasCortoPorMinutosAux(ady.getVertice(), dest, ret, l, minutos + ady.getEtiqueta());
                    }
                    ady = ady.getSigAdyacente();
                }
                l.eliminar(l.longitud());
            }
        }
        return ret;
    }

    public Lista caminoMasCortoPorMinutos(Object origen, Object destino) {
        //Calcula el camino de un Aeropuerto a otro segun la menor cantidad de minutos de vuelo posible
        Object[] ret = new Object[2];
        ret[0] = new Lista();
        ret[1] = 0;
        NodoVert ori = ubicarVertice(origen);
        if (ori != null) {
            NodoVert dest = ubicarVertice(destino);
            if (dest != null) {
                ret = caminoMasCortoPorMinutosAux(ori, destino, ret, new Lista(), 0);
            }
        }
        Lista recorrido = (Lista) ret[0];
        //Los minutos usados en total van al final de la Lista retornada
        recorrido.insertar(ret[1], recorrido.longitud() + 1);
        return recorrido;
    }

    public Lista caminoCantMaxima(Object origen, Object destino, int cantidad) {
        //Calcula si es posible ir (en un maximo de vuelos dado) de un Aeropuerto a otro
        Object[] ret = new Object[2];
        ret[0] = new Lista();
        ret[1] = cantidad;
        NodoVert ori = ubicarVertice(origen);
        if (ori != null) {
            NodoVert dest = ubicarVertice(destino);
            if (dest != null) {
                ret = caminoCantMaximaAux(ori, destino, ret, new Lista(), 0);
            }
        }
        Lista recorrido = (Lista) ret[0];
        //La cantidad de vuelos usada es indicada al final de la Lista a retornar
        if (!recorrido.esVacia()) {
            recorrido.insertar(ret[1], recorrido.longitud() + 1);
        }
        return recorrido;
    }

    private Object[] caminoCantMaximaAux(NodoVert n, Object dest, Object[] ret, Lista l, int veces) {
        int x = (int) ret[1];
        if (n != null && x >= veces) { //Se controla la cantidad de veces maximas
            //si vertice n es igual al destino, hay camino
            if (n.getElem().equals(dest)) {
                Lista lis = (Lista) ret[0];
                if (((lis.esVacia()) || (int) ret[1] > veces)) {
                    if (!l.esVacia()) {
                        lis = l.clone();
                    }
                    lis.insertar(dest, lis.longitud() + 1);
                    ret[0] = lis;
                    ret[1] = veces;
                }
            } else {
                l.insertar(((Aeropuerto) n.getElem()).getCodigoAero(), l.longitud() + 1);
                NodoAdy ady = n.getPrimerAdy();
                while (ady != null) {
                    if (l.localizar(((Aeropuerto) ady.getVertice().getElem()).getCodigoAero()) < 0) {
                        ret = caminoCantMaximaAux(ady.getVertice(), dest, ret, l, veces + 1);
                    }
                    ady = ady.getSigAdyacente();
                }
                l.eliminar(l.longitud());
            }
        }
        return ret;
    }

    public int calculaMinutos(String sal, String lleg) {
        //Calcula la cantidad de minutos que hay entre dos horarios, para las etiquetas del grafo
        int tiempoTotal;
        String[] partes1 = sal.split(":");
        String[] partes2 = lleg.split(":");
        int min1 = (Integer.parseInt(partes1[0]) * 60 + (Integer.parseInt(partes1[1])));
        int min2 = (Integer.parseInt(partes2[0]) * 60 + (Integer.parseInt(partes2[1])));
        if (min2 < min1) {
            min2 = min2 + (24 * 60);
        }
        tiempoTotal = min2 - min1;
        return tiempoTotal;
    }

    private void insertarArcoAux(NodoVert origen, NodoVert destino, int etiqueta) {
        //Privado auxiliar para insertar un arco
        if (origen.getPrimerAdy() == null) {
            //no tiene arcos, se crea uno nuevo como primer adyacente
            origen.setPrimerAdy(new NodoAdy(destino, etiqueta));
        } else {
            NodoAdy aux = origen.getPrimerAdy();
            //se busca al ultimo nodo adyacente y se le conecta el nuevo nodo
            while (aux.getSigAdyacente() != null) {
                aux = aux.getSigAdyacente();
            }
            aux.setSigAdyacente((new NodoAdy(destino, etiqueta)));
        }
        //Parte 2: Se enlaza destino con origen (Agregado en correccion)
        if (destino.getPrimerAdy() == null) {
            //Destino no tiene arcos, creamos uno nuevo, sera primer adyacente
            destino.setPrimerAdy(new NodoAdy(origen, etiqueta));
        } else {
            NodoAdy aux2 = destino.getPrimerAdy();
            //Buscamos el ultimo adyacente de destino
            while (aux2.getSigAdyacente() != null) {
                aux2 = aux2.getSigAdyacente();
            }
            aux2.setSigAdyacente(new NodoAdy(origen, etiqueta));
        }
    }

    public boolean insertarArco(Object ori, Object des, int etiqueta) {
        //Publico, crea un arco entre dos Aeropuertos
        boolean exito = false;
        //Checkea si existen tanto origen como destino, caso oontrario devuelve falso
        NodoVert origen = ubicarVertice(ori);
        if (origen != null) {
            NodoVert destino = ubicarVertice(des);
            if (destino != null) {
                insertarArcoAux(origen, destino, etiqueta);
                exito = true;
            }
        }
        return exito;
    }

    public boolean eliminarArco(Object ori, Object des) {
        boolean exito = false;
        //Checkea si existen tanto origen como destino, caso oontrario devuelve falso
        NodoVert origen = ubicarVertice(ori);
        if (origen != null) {
            NodoVert destino = ubicarVertice(des);
            if (destino != null) {
                eliminarArcoAux(origen, destino);
                eliminarArcoAux(destino, origen); //Agregado correccion
                exito = true;
            }
        }
        return exito;
    }

    public boolean existeArco(Object aer1, Object aer2) {
        //Metodo publico para determinar si existe un arco entre dos aeropuertos dados
        NodoVert nodo = ubicarVertice(aer1); //Se busca el vertice donde está guardado aer1
        NodoAdy aux = nodo.getPrimerAdy();
        while (aux != null && !(aux.getVertice().getElem().equals(aer2))) {
            aux = aux.getSigAdyacente();
        }
        return (aux != null);
    }

    private void eliminarVerticeAux(NodoVert buscado) {
        NodoVert aux;
        //Si el vertice buscado esta como inicio
        if (this.inicio.equals(buscado)) {
            if (this.inicio.getSigVertice() != null) {
                //Se asigna el siguiente del inicio como el nuevo inicio
                this.inicio = this.inicio.getSigVertice();
                //Se eliminan los arcos que tienen a vertice como destino
                aux = this.inicio;
                while (aux != null) {
                    eliminarArcoAux(aux, buscado);
                    aux = aux.getSigVertice();
                }
            } else {
                //Si el grafo tiene un solo vertice, el inicio sera nulo
                this.inicio = null;
            }
        } else { //El vertice buscado no es el inicio
            aux = this.inicio;
            //Se busca el vertice hasta encontrarlo y luego eliminarlo
            //De otra forma, retorna falso
            while (aux.getSigVertice() != null) {
                if (aux.getSigVertice().equals(buscado)) {
                    //Si el elemento a eliminar tiene un vertice conectado, el vertice anterior se conecta a este
                    if (aux.getSigVertice().getSigVertice() != null) {
                        aux.setSigVertice(aux.getSigVertice().getSigVertice());
                    } else {
                        //Sino, la conexion al proximo vertice es nula
                        aux.setSigVertice(null);
                        //Se eliminan los arcos que tienen a vertice como destino
                        eliminarArcoAux(aux, buscado);
                        //Se corta la repeticion, ya que no quedan datos por iterar
                        break;
                    }
                }
                //Se eliminan los arcos a el nodo borrado
                eliminarArcoAux(aux, buscado);
                aux = aux.getSigVertice();
            }
            eliminarArcoAux(aux, buscado);
        }
    }

    public boolean eliminarVertice(Object buscado) {
        boolean exito = false;
        //Se verifica si existe el nodo a borrar
        NodoVert borrado = ubicarVertice(buscado);
        if (borrado != null) {
            eliminarVerticeAux(borrado);
            exito = true;
        }
        return exito;
    }

    public boolean existeVertice(Object buscado) {
//        Aeropuerto busca = (Aeropuerto) buscado;
        return !(ubicarVertice(buscado) == null);
    }

    public boolean vacio() {
        return inicio == null;
    }

    public boolean eliminarArcoSegunEtiqueta(Object ori, Object des, int etiquet) {
        boolean exito = false;
        //Checkea si existen tanto origen como destino, caso oontrario devuelve falso
        NodoVert origen = ubicarVertice(ori);
        if (origen != null) {
            NodoVert destino = ubicarVertice(des);
            if (destino != null) {
                eliminarArcoSegunEtiquetaAux(origen, destino, etiquet);
                eliminarArcoSegunEtiquetaAux(destino, origen, etiquet); //Agregado correccion
                exito = true;
            }
        }
        return exito;
    }
    
    private void eliminarArcoSegunEtiquetaAux(NodoVert origen, NodoVert destino, int mins) {
        //Privado, para eliminar un Arco
        //considerando que puede haber mas de dos arcos 
        //Si el origen y el destino existen pero no estan conectados mediante un arco, no se hace nada
        NodoAdy aux = origen.getPrimerAdy();
        while (aux != null) {
            if (aux.getVertice().equals(destino) && aux.getEtiqueta() == mins) { //El primer adyacente es el nodo a eliminar
                if (aux.getSigAdyacente() != null) {
                    origen.setPrimerAdy(aux.getSigAdyacente());
                    aux = origen.getPrimerAdy();
                } else {
                    //En el caso de que sea el unico nodo en la lista, el primer adyacente del vertice queda nulo
                    origen.setPrimerAdy(null);
                    aux = null;
                }
            } else {
                if (aux.getSigAdyacente() != null && aux.getSigAdyacente().getVertice().equals(destino) && aux.getSigAdyacente().getEtiqueta() == mins) {
                    //si el siguiente al adyacente es el elemento a eliminar
                    if (aux.getSigAdyacente().getSigAdyacente() != null) {
                        //si existe nodo para enlazar se conecta a ese
                        aux.setSigAdyacente(aux.getSigAdyacente().getSigAdyacente());
                    } else {
                        //si no existe entonces el enlace al siguiente sera nulo
                        aux.setSigAdyacente(null);
                    }
                } else {
                    aux = aux.getSigAdyacente();
                }
            }
        }
    }

    private void eliminarArcoAux(NodoVert origen, NodoVert destino) {
        //Privado, para eliminar un Arco
        //considerando que puede haber mas de dos arcos 
        //Si el origen y el destino existen pero no estan conectados mediante un arco, no se hace nada
        NodoAdy aux = origen.getPrimerAdy();
        while (aux != null) {
            if (aux.getVertice().equals(destino)) {                //El primer adyacente es el nodo a eliminar
                if (aux.getSigAdyacente() != null) {
                    origen.setPrimerAdy(aux.getSigAdyacente());
                    aux = origen.getPrimerAdy();
                } else {
                    //En el caso de que sea el unico nodo en la lista, el primer adyacente del vertice queda nulo
                    origen.setPrimerAdy(null);
                    aux = null;
                }
            } else {
                if (aux.getSigAdyacente() != null && aux.getSigAdyacente().getVertice().equals(destino)) {
                    //si el siguiente al adyacente es el elemento a eliminar
                    if (aux.getSigAdyacente().getSigAdyacente() != null) {
                        //si existe nodo para enlazar se conecta a ese
                        aux.setSigAdyacente(aux.getSigAdyacente().getSigAdyacente());
                    } else {
                        //si no existe entonces el enlace al siguiente sera nulo
                        aux.setSigAdyacente(null);
                    }
                } else {
                    aux = aux.getSigAdyacente();
                }
            }
        }
    }

    private NodoVert ubicarAnteriorVert(Object hij) {
        //Busca un aeropuerto y devuelve el nodo anterior a éste en la lista de vertices.
//        Aeropuerto hijo = (Aeropuerto) hij;
        NodoVert aux = this.inicio;
        while (aux.getSigVertice() != null && aux.getSigVertice() != null && !aux.getSigVertice().getElem().equals(hij)) {
            aux = aux.getSigVertice();
        }
        return aux;
    }

    private NodoVert ubicarVertice(Object busca) {
        NodoVert resultado = this.inicio;
        while (resultado != null && resultado.getElem() != null && !resultado.getElem().equals(busca)) {
            resultado = resultado.getSigVertice();
        }
        return resultado;
    }

    private NodoVert ubicarVerticeDesde(Object busca, NodoVert partida) {
        NodoVert resultado = partida;
        while (resultado != null && resultado.getElem() != null && !resultado.getElem().equals(busca)) {
            resultado = resultado.getSigVertice();
        }
        return resultado;
    }

    private NodoVert ubicarVerticePrimero(Object busca, Object busca2) {
        //Devuelve el nodo del elemento que encuentre primero. Para mejorar la eficiencia del Grafo.
        NodoVert resultado = this.inicio;
        while (resultado != null && resultado.getElem() != null && (!resultado.getElem().equals(busca) || !resultado.getElem().equals(busca2))) {
            resultado = resultado.getSigVertice();
        }
        return resultado;
    }

    private NodoAdy ultimoAdyacente(NodoVert nodo) {
        //Recorre la lista de adyacentes de un vertice y devuelve el ultimo de la lista
        NodoAdy aux = nodo.getPrimerAdy();
        if (aux != null) {
            while (aux.getSigAdyacente() != null) {
                aux = aux.getSigAdyacente();
            }
        }
        return aux;
    }

    private String toStringAux() {
        String s = "";
        if (this.inicio == null) {
            s = "Grafo vacio";
        } else {
            NodoVert aux = this.inicio;
            NodoAdy aux2;
            while (aux != null) {
                //lee el vertice y luego lee sus abyacentes
                s += aux.getElem() + "   --->   ";
                aux2 = aux.getPrimerAdy();
                while (aux2 != null) {
                    s += " // " + (int) aux2.getEtiqueta() + "min  " + aux2.getVertice().getElem();
                    aux2 = aux2.getSigAdyacente();
                }
                s += "\n";
                aux = aux.getSigVertice();
            }
        }
        return s;
    }

    @Override
    public String toString() {
        //Devuelve la lista de adyacencia del grafo
        String s;
        s = toStringAux();
        return s;
    }

}
