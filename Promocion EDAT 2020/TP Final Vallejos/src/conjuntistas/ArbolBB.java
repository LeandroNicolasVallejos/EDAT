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
import lineales.dinamicas.Lista;

public class ArbolBB {

    private NodoABB raiz;

    public boolean insertar(Comparable elemento) {
        //Metodo publico para insertar un elemento en el ArbolBB, llama al privado insertarAux
        boolean exito = true;
        if (this.raiz == null) {
            this.raiz = new NodoABB(elemento);
        } else {
            exito = insertarAux(this.raiz, elemento);
        }
        return exito;
    }

    public boolean eliminar(Comparable elem) {
        //Metodo publico para eliminar un elemento del ArbolBB, llama al privado eliminarAux
        boolean n = false;
        if (pertenece(elem)) {
            n = eliminarAux(this.raiz, elem);
        }
        return n;
    }

    public boolean pertenece(Comparable buscado) {
        //Metodo publico para buscar si un elemento dado pertenece al ArbolBB, llama al privado perteneceAux
        boolean encontrado = false;
        if (perteneceAux(this.raiz, buscado) != null) {
            encontrado = true;
        }
        return encontrado;
    }

    private Comparable perteneceAux(NodoABB nodo, Comparable busca) {
        //Llamado por el publico pertenece
        Comparable elem = null;
        if (nodo != null) { //Cuando lleguemos a nodo nulo, se termina la operacion
            if (nodo.getElem().compareTo(busca) == 0) { //Si el elemento se encontró, devolvemos elem;
                elem = nodo.getElem();
            } else {
                if (nodo.getElem().compareTo(busca) > 0) { //Si el elemento en el nodo es mayor al buscado, vamos por el subarbol izq
                    elem = perteneceAux(nodo.getIzquierdo(), busca);
                } else { //El elemento en el nodo es menor al buscado, vamos por subarbol derecho
                    elem = perteneceAux(nodo.getDerecho(), busca);
                }
            }
        }
        return elem;
    }

    public boolean esVacio() {
        //Para saber si el arbol esta vacio
        return (this.raiz == null);
    }

    public void vaciar() {
        //Para vaciar el arbol
        this.raiz = null;
    }

    public NodoABB padre(Comparable aBuscar) {
        //Metodo publico para llamar al privado padreAux(NodoABB nodo, int aBuscar)
        //Busca al padre del objeto "aBuscar" y devuelve el nodo padre
        NodoABB padre = null;
        if (!esVacio() && this.raiz.getElem() != aBuscar) {
            padre = padreAux(this.raiz, aBuscar);
        }
        return padre;
    }

    public Lista listarPreorden() {
        //Metodo publico para llamar al privado listarPreordenAux(NodoABB nodo, Lista lis)
        Lista preorden = new Lista();
        listarPreordenAux(this.raiz, preorden);
        return preorden;
    }

    public Lista listarInorden() {
        //Metodo publico para llamar al privado listarInordenAux(NodoABB nodo, Lista lis)
        Lista inorden = new Lista();
        listarInordenAux(this.raiz, inorden);
        return inorden;
    }

    public Lista listarPosorden() {
        //Metodo publico para llamar al privado listarPosordenAux(NodoABB nodo, Lista lis)
        Lista posorden = new Lista();
        listarPosordenAux(this.raiz, posorden);
        return posorden;
    }

    public Lista listarRango(Comparable elemMinimo, Comparable elemMaximo) {
        //Metodo publico para llamar al privado listarRangoAux. Lista los elementos del arbol que están dentro de un rango dado
        Lista dev = new Lista();
        if ((elemMinimo.compareTo(elemMaximo) <= 0)) {
            listarRangoAux(this.raiz, dev, elemMinimo, elemMaximo);
        }
        return dev;
    }

    private void listarRangoAux(NodoABB nodo, Lista lis, Comparable min, Comparable max) {
        //Llamado por el publico listarRango
        //Metodo de listarInorden modificado para agregar a la lista solamente los elementos que se encuentren en el rango
        if (nodo != null) {
            listarRangoAux(nodo.getIzquierdo(), lis, min, max);
            if ((nodo.getElem().compareTo(min) >= 0) && (nodo.getElem().compareTo(max) <= 0)) {
                lis.insertar(nodo.getElem(), lis.longitud() + 1);
                listarRangoAux(nodo.getDerecho(), lis, min, max);
            }
        }
    }

    public boolean eliminarMinimo() { //Busca y borra el minimo elemento del arbol
        boolean devuelve = false;
        if (!esVacio()) { //Si el arbol no está vacío entonces siempre podremos eliminar algo y dará true en todos los casos
            eliminarMinimoAux(this.raiz);
            devuelve = true;
        }
        return devuelve;
    }

    private void eliminarMinimoAux(NodoABB nodo) {
        //Llamado por el publico eliminarMinimo
        NodoABB hijo;
        NodoABB aux;
        if (nodo != null) {
            if (nodo.getIzquierdo() != null) { //Si el nodo visitado tiene hijo izq
                hijo = nodo.getIzquierdo();
                if (hijo.getIzquierdo() != null) { //Si el hijo tiene otro hijo izq entonces seguimos evaluando
                    eliminarMinimoAux(hijo);
                } else {
                    if (hijo.getDerecho() != null) {
                        aux = hijo.getDerecho();
                        nodo.setIzquierdo(aux);
                    } else {
                        nodo.setIzquierdo(null);
                    }
                }
            } else if (this.raiz == nodo) { //Caso especial donde borro la raiz
                if (this.raiz.getDerecho() != null) { //Si la raiz tiene hijo derecho, este será la nueva raiz
                    this.raiz = this.raiz.getDerecho();
                } else { //Si la raiz no tiene hijo derecho entonces al borrar la raiz el arbol queda vacio
                    this.raiz = null;
                }
            }
        }
    }

    public boolean eliminarMaximo() { //Busca y borra el maximo elemento del arbol
        boolean devuelve = false;
        if (!esVacio()) { //Si el arbol no está vacío entonces siempre podremos eliminar algo y dará true en todos los casos
            eliminarMaximoAux(this.raiz);
            devuelve = true;
        }
        return devuelve;
    }

    private void eliminarMaximoAux(NodoABB nodo) {
        //Llamado por el publico eliminarMaximo
        NodoABB hijo;
        NodoABB aux;
        if (nodo != null) {
            if (nodo.getDerecho() != null) { //Si el nodo visitado tiene hijo derecho
                hijo = nodo.getDerecho();
                if (hijo.getDerecho() != null) { //Si el hijo tiene otro hijo derecho entonces seguimos la recursividad
                    eliminarMaximoAux(hijo);
                } else {//Si el hijo de nodo no tiene otro hijo derecho entonces este es el maximo, anulamos el hijo de nodo
                    if (hijo.getIzquierdo() != null) {
                        aux = hijo.getIzquierdo();
                        nodo.setDerecho(aux);
                    } else {
                        nodo.setDerecho(null);
                    }
                }
            } else if (this.raiz == nodo) { //Caso especial donde la raiz es el maximo a eliminar
                if (this.raiz.getIzquierdo() != null) { //Si la raiz tiene hijo izquierdo, este será la nueva raiz
                    this.raiz = this.raiz.getIzquierdo();
                } else { //Si la raiz no tiene hijo izquierdo entonces al borrar la raiz el arbol queda vacio
                    this.raiz = null;
                }
            }
        }
    }

    public int amplitudSubarbol(Comparable elemento) {
        NodoABB buscado;
        int resu = -1;
        buscado = busca(this.raiz, elemento);
        if (!esVacio() && buscado != null) {
            resu = amplitudAux(buscado);
        }
        return resu;
    }

    private int amplitudAux(NodoABB raizSub) {
        int dif;
        Comparable min, max;
        min = minimoAux(raizSub);
        max = maximoAux(raizSub);
        dif = (int) max - (int) min;
        return dif;
    }

    public Comparable minimoElem() { //Devuelve el menor elemento del arbol
        //Llama al privado minimoAux
        Comparable devuelve;
        devuelve = minimoAux(this.raiz);
        return devuelve;
    }

    private Comparable minimoAux(NodoABB nodo) {
        //Llamado por el publico minimoElem
        Comparable elem = null;
        if (nodo != null) {
            if (nodo.getIzquierdo() != null) {
                elem = minimoAux(nodo.getIzquierdo());
            } else {
                elem = nodo.getElem();
            }
        }
        return elem;
    }

    public Comparable maximoElem() { //Devuelve el mayor elemento del arbol
        //Llama al privado maximoAux
        Comparable devuelve;
        devuelve = maximoAux(this.raiz);
        return devuelve;
    }

    private Comparable maximoAux(NodoABB nodo) {
        //Llamado por el publico maximoElem
        Comparable elem = null;
        if (nodo != null) {
            if (nodo.getDerecho() != null) {
                elem = maximoAux(nodo.getDerecho());
            } else {
                elem = nodo.getElem();
            }
        }
        return elem;
    }

    //----------------------------------------Metodos privados de ArbolBB----------------------------------------------------
    private void listarPreordenAux(NodoABB nodo, Lista lis) {
        //Metodo privado llamado por listarPreorden()
        if (nodo != null) {
            lis.insertar(nodo.getElem(), lis.longitud() + 1);
            listarPreordenAux(nodo.getIzquierdo(), lis);
            listarPreordenAux(nodo.getDerecho(), lis);
        }
    }

    private void listarPosordenAux(NodoABB nodo, Lista lis) {
        //Metodo privado llamado por listarPosorden()
        if (nodo != null) {
            listarPosordenAux(nodo.getIzquierdo(), lis);
            listarPosordenAux(nodo.getDerecho(), lis);
            lis.insertar(nodo.getElem(), lis.longitud() + 1);
        }
    }

    private void listarInordenAux(NodoABB nodo, Lista lis) {
        //Metodo privado llamado por listarInorden()
        if (nodo != null) {
            listarInordenAux(nodo.getIzquierdo(), lis);
            lis.insertar(nodo.getElem(), lis.longitud() + 1);
            listarInordenAux(nodo.getDerecho(), lis);
        }
    }

    private boolean eliminarAux(NodoABB nodo, Comparable elem) {
        boolean n = false;
        if (nodo != null) {
            if (elem.compareTo(nodo.getElem()) < 0) { //Si elem es menor a la raiz, verifica al subarbol izq
                if (nodo.getIzquierdo().getElem() == (elem)) { //Desde la raiz, verificamos si elem es igual al hijo izq
                    casos(nodo.getIzquierdo(), nodo, 'i'); //Si es igual, lo elimina segun los casos y devuelve true.
                    n = true;
                } else {
                    n = eliminarAux(nodo.getIzquierdo(), elem); //Si no es igual, entra al subarbol izq.
                }
            } else {  //Si elem es mayor a la raiz, verifica el subarbol der
                if (elem.compareTo(nodo.getElem()) > 0) {
                    if (nodo.getDerecho().getElem() == (elem)) { //Desde la raiz, verifica si elem es igual al hijo der
                        casos(nodo.getDerecho(), nodo, 'd'); //Si es igual, lo elimina segun los casos y devuelve true.
                        n = true;
                    } else {
                        n = eliminarAux(nodo.getDerecho(), elem); //Si no es igual, entra al subarbol der.
                    }
                } else { //Caso especial si se borra la raiz.
                    casos(nodo, null, 'd');
                    n = true;
                }
            }
        }
        return n;
    }

    private void casos(NodoABB nodo, NodoABB padre, char pos) {
        if (nodo.getIzquierdo() == null && nodo.getDerecho() == null) {         //Caso 1 (el nodo a eliminar NO tiene hijos.)
            if (pos == 'i') {
                padre.setIzquierdo(null); //Todas las condiciones de "pos == 'i', son para saber donde enlazar (en el padre) al nodo que reemplaza al eliminado.
            } else {
                padre.setDerecho(null);
            }
        } else {
            if ((nodo.getDerecho() == null && nodo.getIzquierdo() != null)) {   //Caso 2 (el nodo a eliminar tiene hijo izq)
                if (pos == 'i') {
                    padre.setIzquierdo(nodo.getIzquierdo());
                } else {
                    padre.setDerecho(nodo.getIzquierdo());
                }
            } else {
                if (nodo.getDerecho() != null && nodo.getIzquierdo() == null) { //Caso 2 (el nodo a eliminar tiene hijo der)
                    if (pos == 'i') {
                        padre.setIzquierdo(nodo.getDerecho());
                    } else {
                        padre.setDerecho(nodo.getDerecho());
                    }
                } else {                                                        //Caso 3 (el nodo a eliminar tiene ambos hijos.)
                    NodoABB aux = nodo;
                    NodoABB p = nodo; //aux es el nodo a eliminar, padre es el padre del nodo a eliminar (puede ser nulo si es la raiz).
                    nodo = nodo.getIzquierdo();
                    pos = 'i';      //indicamos que es HI de su padre.
                    while (nodo.getDerecho() != null) {    //Se busca al mayor elemento del subarbol izquierdo.
                        p = nodo;                          //p es el padre del nodo que hay que subir (se guarda para luego eliminar ese nodo del fondo).
                        nodo = nodo.getDerecho();
                        pos = 'd';  //indicamos que es HD de su padre.
                    }
                    if (padre == null) { //para cuando se quiere borrar la raiz.
                        casos(nodo, p, pos);
                        this.raiz.setElem(nodo.getElem());
                        this.raiz.setIzquierdo(aux.getIzquierdo());
                        this.raiz.setDerecho(aux.getDerecho());
                    } else {
                        aux.setElem(nodo.getElem());
                        casos(nodo, p, pos);
                    }
                }
            }
        }
    }

    private NodoABB padreAux(NodoABB nodo, Comparable aBuscar) {
        //Metodo privado llamado por padre(Comparable aBuscar)
        NodoABB devuelve = null;
        if (nodo != null) {
            if ((nodo.getIzquierdo() != null && nodo.getIzquierdo().getElem() == (aBuscar)) || (nodo.getDerecho() != null && nodo.getDerecho().getElem() == (aBuscar))) {
                devuelve = nodo;
            } else {
                devuelve = padreAux(nodo.getIzquierdo(), aBuscar);
                if (devuelve == null) {
                    devuelve = padreAux(nodo.getDerecho(), aBuscar);
                }
            }
        }
        return devuelve;
    }

    private boolean insertarAux(NodoABB n, Comparable elemento) {
        //Precondicion: n no es nulo
        boolean exito = true;
        if (elemento.compareTo(n.getElem()) == 0) {
            //Reportar error: elemento repetido
            exito = false;
        } else {
            if (elemento.compareTo(n.getElem()) < 0) {
                //elemento es menor que n.getElem()
                //si tiene HI baja a la zquierda, si no, agrega elemento
                if (n.getIzquierdo() != null) {
                    exito = insertarAux(n.getIzquierdo(), elemento);
                } else {
                    n.setIzquierdo(new NodoABB(elemento));
                }
            } else {
                //elemento es mayor que n.getElem()
                //si tiene HD baja a la derecha, si no, agrega elemento
                if (n.getDerecho() != null) {
                    exito = insertarAux(n.getDerecho(), elemento);
                } else {
                    n.setDerecho(new NodoABB(elemento));
                }
            }
        }
        return exito;
    }

    private NodoABB obtenerNodo(NodoABB n, Comparable buscado) {
        //Metodo privado para buscar un elemento y devolver el nodo
        NodoABB resultado = null;
        if (n != null) {
            if (n.getElem().compareTo(buscado) == 0) { //Si el buscado es n, lo devuelve
                resultado = n;
            } else {
                if (buscado.compareTo(n.getElem()) < 0) {
                    //elemento buscado menor que el elem del nodo
                    //si tiene HI baja a la izq, si no, null
                    resultado = obtenerNodo(n.getIzquierdo(), buscado);
                } else {
                    //buscado es mayor que elem del nodo
                    //si tiene HD baja a la der, si no, null
                    resultado = obtenerNodo(n.getDerecho(), buscado);
                }
            }
        }
        return resultado;
    }

    @Override
    public String toString() {
        //Para imprimir el arbol, metodo publico que llama al privado toStringAux
        String cadena = "Arbol vacio \n ";
        if (!esVacio()) {
            cadena = toStringAux(this.raiz, "");
        }
        return cadena;
    }

    private String toStringAux(NodoABB nodo, String s) {
        //Devuelve cadena con el arbol dividido en padre e hijos. Recorrido preorden.
        if (nodo != null) {
            s = s + nodo.getElem() + " Es padre de: ";
            if (nodo.getIzquierdo() != null) {
                s = s + nodo.getIzquierdo().getElem() + " (HI) ";
            } else {
                s = s + "- (HI) ";
            }
            if (nodo.getDerecho() != null) {
                s = s + nodo.getDerecho().getElem() + " (HD) \n ";
            } else {
                s = s + "- (HD) \n ";
            }
            if (nodo.getIzquierdo() != null) {
                s = toStringAux(nodo.getIzquierdo(), s);
            }
            if (nodo.getDerecho() != null) {
                s = toStringAux(nodo.getDerecho(), s);
            }
        }
        return s;
    }

    @Override
    public ArbolBB clone() {
        //Para clonar el arbol. Llama al privado cloneAux
        ArbolBB clon = new ArbolBB();
        if (this.raiz != null) {
            clon.raiz = new NodoABB(this.raiz.getElem(), null, null);
            cloneAux(this.raiz, clon.raiz);
        }
        return clon;
    }

    private void cloneAux(NodoABB nodo, NodoABB clon) {
        //Clona el arbol.
        if (nodo != null) {
            if (nodo.getIzquierdo() != null) {
                clon.setIzquierdo(new NodoABB(nodo.getIzquierdo().getElem(), null, null));
                cloneAux(nodo.getIzquierdo(), clon.getIzquierdo());
            }
            if (nodo.getDerecho() != null) {
                clon.setDerecho(new NodoABB(nodo.getDerecho().getElem(), null, null));
                cloneAux(nodo.getDerecho(), clon.getDerecho());
            }
        }
    }

    public ArbolBB clonarParteInvertida(Comparable elem) {
        ArbolBB devuelve = new ArbolBB();
        NodoABB hola;
        hola = busca(this.raiz, elem);
        if (!esVacio() && hola != null) {
            devuelve.raiz = new NodoABB(hola.getElem(), null, null);
            clonarInvertidoAux(hola, devuelve.raiz);
        }
        return devuelve;
    }

    private NodoABB busca(NodoABB nodo, Comparable elem) {
        NodoABB resu = null;
        if (nodo != null) {
            if (nodo.getElem().compareTo(elem) == 0) {
                resu = nodo;
            } else if (nodo.getElem().compareTo(elem) > 0) {
                resu = busca(nodo.getIzquierdo(), elem);
            } else {
                resu = busca(nodo.getDerecho(), elem);
            }
        }
        return resu;
    }

    private void clonarInvertidoAux(NodoABB nodo, NodoABB n) {
        if (nodo != null) {
            if (nodo.getIzquierdo() != null) {
                n.setDerecho(new NodoABB(nodo.getIzquierdo().getElem(), null, null));
                clonarInvertidoAux(nodo.getIzquierdo(), n.getDerecho());
            }
            if (nodo.getDerecho() != null) {
                n.setIzquierdo(new NodoABB(nodo.getDerecho().getElem(), null, null));
                clonarInvertidoAux(nodo.getDerecho(), n.getIzquierdo());
            }
        }
    }

}
