/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lineales.dinamicas;

/**
 *
 * @author leandro.vallejos
 */
public class Lista {

    private Nodo cabecera;
    private int longitud;

    public Lista() { //constructor de Lista
        this.cabecera = null;
        this.longitud = 0;
    }

    public boolean insertar(Object nuevoElem, int pos) {
        //inserta el elemento nuevo en la posicion pos
        //detecta y reporta error posicion invalida
        boolean exito = true;
        if (pos < 1 || pos > this.longitud + 1) {
            exito = false;
        } else {
            if (pos == 1) { //Crea un nuevo nodo y se enlaza en la cabecera
                this.cabecera = new Nodo(nuevoElem, this.cabecera);
            } else { //avanza hasta el elemento en posicion pos-1
                Nodo aux = this.cabecera;
                int i = 1;
                while (i < pos - 1) {
                    aux = aux.getEnlace();
                    i++;
                }
                //Crea el nodo y lo enlaza
                Nodo nuevo = new Nodo(nuevoElem, aux.getEnlace());
                aux.setEnlace(nuevo);
            }
            this.longitud = this.longitud + 1; //Aumenta la longitud de la lista en 1 porque se agrego un nodo nuevo
        }
        //Nunca hay error de lista llena, entonces devuelve true
        return exito;
    }

    public int longitud() {
        return this.longitud;
    }

    public boolean esVacia() {
        //Verifica si la lista está vacía
        boolean si = false;
        if (this.cabecera == null) {
            si = true;
        }
        return si;
    }

    public boolean eliminar(int pos) {
        //elimina el elemento en la posicion pos de la lista
        boolean exito = false;
        int longi = longitud();
        if (!esVacia() && 1 <= pos && pos <= longi) { //condiciones para eliminado exitoso
            exito = true;
            if (pos == 1) { //para borrar posicion 1
                if (longi == 1) {
                    this.cabecera = null;
                } else {
                    this.cabecera = this.cabecera.getEnlace();
                }
            } else { // para borrar posicion distinta a 1
                Nodo aux = this.cabecera;
                int i = 1;
                while (i < pos - 1) {
                    aux = aux.getEnlace();
                    i++;
                }
                aux.setEnlace(aux.getEnlace().getEnlace());
            }
            this.longitud = this.longitud - 1; //Se actualiza la longitud de la lista -1 pues se borro un nodo
        }
        return exito;
    }

    public Object recuperar(int pos) {
        //devuelve el elemento de la posicion pos de la lista
        int i = 1;
        Object retorna;
        Nodo aux = this.cabecera;
        if (pos <= this.longitud && pos > 0) { //si la posicion es valida va hasta la posicion pos y devuelve el elem
            while (i < pos) {
                aux = aux.getEnlace();
                i++;
            }
            retorna = aux.getElem();
        } else {
            retorna = null; //si la posicion es invalida devuelve nulo
        }
        return retorna;
    }

    public int localizar(Object elem) {
        //localiza un elemento elem en la lista y deuvelve su primera posicion
        int pos = -1;
        int contador = 1;
        Nodo aux = this.cabecera;
        if (aux != null && aux.getElem().equals(elem)) { //si está en la primera posicion devuelve 1
            pos = contador;
        } else { //si no esta en la primera posicion la busca, si la encuentra, devuelve contador, sino devuelve -1
            while (contador <= this.longitud && aux != null) {
                if (aux.getElem().equals(elem)) {
                    pos = contador;
                    break;
                } else {
                    contador++;
                    aux = aux.getEnlace();
                }
            }
        }
        return pos;
    }

    public void vaciar() {
        //vacia la Lista
        this.cabecera = null;
        this.longitud = 0;
    }

    @Override
    public Lista clone() {
        //para clonar una lista
        Lista clonada = new Lista();
        if (this.cabecera != null) { //Si no es vacia copio primero la cabecera, luego revisa si hay mas nodos
            clonada.cabecera = new Nodo(this.cabecera.getElem(), null);
            if (this.cabecera.getEnlace() != null) { //Si tiene mas de un nodo...
                Nodo aux1, aux2;
                Object ob;
                aux1 = this.cabecera.getEnlace();
                aux2 = clonada.cabecera;
                while (aux1 != null) {
                    Nodo nuevo = new Nodo(aux1.getElem(), null);
                    aux2.setEnlace(nuevo);
                    aux2 = nuevo;
                    aux1 = aux1.getEnlace();
                }
            }
            clonada.longitud = this.longitud;
        }
        return clonada;
    }

    public String toStringReves() {
        //Para imprimir una Lista agregando al elemento siempre al principio
        String texto = "";
        int longi = longitud();
        if (esVacia()) {
            texto = "Lista vacia.";
        } else {
            for (int i = 1; i <= longi; i++) {
                texto =  recuperar(i) + "\n" +texto;
            }
        }
        return texto;
    }
    
    @Override
    public String toString() {
        //Para imprimir una Lista agregando al elemento siempre al final
        String texto = "";
        int longi = longitud();
        if (esVacia()) {
            texto = "Lista vacia.";
        } else {
            for (int i = 1; i <= longi; i++) {
                texto =  texto + "\n" + recuperar(i);
            }
        }
        return texto;
    }

    //------------------------------------- EJERCICIOS PARCIAL -----------------------
    public Lista obtenerMultiplos(int num) {
        Lista listaMul = new Lista();
        Nodo aux = this.cabecera;
        Nodo auxNuevo = listaMul.cabecera;
        int pos = 1;
        while (aux != null) {
            if ((pos % num) == 0) {
                if (listaMul.cabecera == null) {//Caso base, la listaMul esta vacia, copio la cabecera
                    listaMul.cabecera = new Nodo(aux.getElem(), null);
                    auxNuevo = listaMul.cabecera;
                } else { //Caso generico, la cabecera de listaMul ya no está vacía

                    auxNuevo.setEnlace(new Nodo(aux.getElem(), null));
                    auxNuevo = auxNuevo.getEnlace();
                }
                listaMul.longitud++;
            }
            aux = aux.getEnlace();
            pos++;
        }
        return listaMul;
    }

    public void eliminarApariciones(Object x) {
        Nodo aux = this.cabecera;
        Nodo aux2;
        while (aux != null) {
            //Caso base, hay que eliminar el elemento de la cabecera
            if (this.cabecera.getElem().equals(x)) {
                this.cabecera = this.cabecera.getEnlace();
                this.longitud--;
                aux = this.cabecera;

            } //Caso generico, la cabecera no se debe eliminar
            else {
                //Si coincide el nodo con el elem a eliminar, reenlazo los nodos anterior y siguiente
                if (aux.getEnlace().getElem().equals(x)) {
                    aux2 = aux.getEnlace();
                    aux.setEnlace(aux2.getEnlace()); //Enlazo el NODO ANTERIOR al NODO A BORRAR junto con el NODO SIGUIENTE al NODO A BORRAR
                    this.longitud--;
                    aux = aux.getEnlace();
                } else {
                    aux = aux.getEnlace();
                }

            }
        }
    }

//    public boolean eliminarPrimeraAparicion(Object x) {
//        boolean exito = false;
//        Nodo aux = this.cabecera;
//        Nodo aux2;
//        while (aux != null) {
//            //Caso base, hay que eliminar el elemento de la cabecera
//            if (this.cabecera.getElem().equals(x)) {
//                this.cabecera = this.cabecera.getEnlace();
//                this.longitud--;
//                exito = true;
//
//            } //Caso generico, la cabecera no se debe eliminar
//            else {
//                //Si coincide el nodo con el elem a eliminar, reenlazo los nodos anterior y siguiente
//                if (aux.getEnlace().getElem().equals(x)) {
//                    aux2 = aux.getEnlace();
//                    aux.setEnlace(aux2.getEnlace()); //Enlazo el NODO ANTERIOR al NODO A BORRAR junto con el NODO SIGUIENTE al NODO A BORRAR
//                    this.longitud--;
//                    exito = true;
//                } else {
//                    aux = aux.getEnlace();
//                }
//
//            }
//        }
//        return exito;
//    }

}
