/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conjuntistas;

import lineales.dinamicas.Lista;

/**
 *
 * @author leandro.vallejos
 */
public class ArbolAVL {

    private NodoAVL raiz;

    public ArbolAVL() {
        this.raiz = null;
    }

    public boolean insertar(Object obj, Comparable i) {
        //Metodo publico para insertar un Objeto junto a su Clave en el Arbol
        //Siempre devuelve true
        boolean exito = true;
        if (this.raiz == null) {
            this.raiz = new NodoAVL(i, obj);
            this.raiz.calcularAltura();
        } else {
            this.raiz = insertarAux(this.raiz, i, obj);
        }
        return exito;
    }

    private NodoAVL insertarAux(NodoAVL n, Comparable clave, Object obj) {
        //Privado usado por insertar
        if (n != null && (n.getClave().compareTo(clave) != 0)) {
            if (clave.compareTo(n.getClave()) < 0) {
                if (n.getIzquierdo() != null) {
                    n.setIzquierdo(insertarAux(n.getIzquierdo(), clave, obj));
                    n.calcularAltura();
                    n = chequearBalance(n);
                } else {
                    NodoAVL aux = new NodoAVL(clave, obj);
                    n.setIzquierdo(aux);
                    aux.calcularAltura();
                    n.calcularAltura();
                }
            } else {
                if (n.getDerecho() != null) {
                    n.setDerecho(insertarAux(n.getDerecho(), clave, obj));
                    n.calcularAltura();
                    n = chequearBalance(n);
                } else {
                    NodoAVL aux = new NodoAVL(clave, obj);
                    n.setDerecho(aux);
                    aux.calcularAltura();
                    n.calcularAltura();
                }
            }
        }
        return n;
    }

    public boolean eliminar(Comparable i) {
        //Metodo publico para eliminar un elemento del Arbol, buscado con su clave
        boolean exito = false;
        if (this.raiz != null) {
            exito = eliminarAux(this.raiz, this.raiz, i);
            if (this.raiz != null) {
                this.raiz = chequearBalance(this.raiz);
            }
        }
        return exito;
    }

    private boolean eliminarAux(NodoAVL hijo, NodoAVL padre, Comparable buscado) {
        //Privado usado por eliminar
        boolean exito;
        if (hijo != null) {
            if (hijo.getClave().compareTo(buscado) == 0) {// Si se encuentra el elemento se elimina segun su caso
                eliminarSegunCaso(hijo, padre);
                exito = true;
            } else {
                if (hijo.getClave().compareTo(buscado) > 0) //Si no lo encuentra busca por otro camino
                {
                    exito = eliminarAux(hijo.getIzquierdo(), hijo, buscado);
                } else {
                    exito = eliminarAux(hijo.getDerecho(), hijo, buscado);
                }
                if (exito) {
                    hijo.calcularAltura();
                    if (padre.equals(hijo)) {
                        this.raiz = chequearBalance(this.raiz);
                    } else {
                        if (padre.getIzquierdo().equals(hijo)) {
                            padre.setIzquierdo(chequearBalance(hijo));
                        } else {
                            padre.setDerecho(chequearBalance(hijo));
                        }
                    }

                }

            }
        } else {
            exito = false;
        }
        return exito;
    }

    private void eliminarSegunCaso(NodoAVL hijo, NodoAVL padre) {
        if (hijo.getIzquierdo() != null && hijo.getDerecho() != null) {//si el elemento a eliminar tiene 2 hijos ira al caso 3
            eliminarCasoTres(hijo, padre);
            padre.calcularAltura();
        } else {
            if (hijo.getIzquierdo() == null && hijo.getDerecho() == null) { //No tiene hijos
                eliminarCasoUno(hijo, padre);
                padre.calcularAltura();
            } else {
                eliminarCasoDos(hijo, padre);//si tiene al menos un hijo ira al caso 2
                padre.calcularAltura();
            }
        }
    }

    private void eliminarCasoUno(NodoAVL hijo, NodoAVL padre) {
        if (hijo.equals(padre)) { //si hay que eliminar la raiz en el caso uno;
            this.raiz = null;
        } else {
            if (padre.getIzquierdo() != null && padre.getIzquierdo().equals(hijo)) //verificar que hijo es el que se quiere eliminar
            {
                padre.setIzquierdo(null);
            } else {
                padre.setDerecho(null);
            }
        }
    }

    private void eliminarCasoDos(NodoAVL hijo, NodoAVL padre) {
        if (hijo.equals(padre)) {      //si el elemento a eliminar es la raiz
            if (hijo.getIzquierdo() != null) {
                this.raiz = this.raiz.getIzquierdo();
            } else {
                this.raiz = this.raiz.getDerecho();
            }
        } else {
            if (padre.getIzquierdo().equals(hijo)) {// si el elemento a eliminar es HI
                if (hijo.getIzquierdo() != null) //si el hijo tiene hijo izquierdo, el padre se conectara con este hijo
                {
                    padre.setIzquierdo(hijo.getIzquierdo());
                } else //si el hijo tiene hijo derecho, el padre se conectara con este hijo
                {
                    padre.setIzquierdo(hijo.getDerecho());
                }
            } else {                             //si el elemento a eliminar es HD
                if (hijo.getIzquierdo() != null) //si el hijo tiene hijo izquierdo, el padre se conectara con este hijo
                {
                    padre.setDerecho(hijo.getIzquierdo());
                } else //si el hijo tiene hijo derecho, el padre se conectara con este hijo
                {
                    padre.setDerecho(hijo.getDerecho());
                }
            }
        }
    }

    private void eliminarCasoTres(NodoAVL hijo, NodoAVL padre) {
        //hijo sera el elemento a eliminar
        NodoAVL aux = hijo.getIzquierdo();
        NodoAVL p = null; // aux es el candidato y p es el padre del candidato
        while (aux.getDerecho() != null) {
            p = aux;
            aux = aux.getDerecho();
        }
        hijo.setElem(aux.getElem());                                        //se intercambia el valor del candidato con el eliminado
        if (p != null) {
            eliminarSegunCaso(aux, p);                                      // se elimina el auxiliar dependiendo su caso
            if (!hijo.getIzquierdo().equals(p)) {
                balancear(hijo.getIzquierdo(), hijo.getIzquierdo().getDerecho(), p);
            }
            hijo.calcularAltura();
            hijo.setIzquierdo(chequearBalance(hijo.getIzquierdo()));
        } else {
            eliminarSegunCaso(aux, hijo);                                   //se elimina el auxiliar en el caso de que no tenga hijo derecho
        }
    }

    private void balancear(NodoAVL padre, NodoAVL hijo, NodoAVL p) {
        //Balancea el subarbol derecho del elemento que se elimino
        if (hijo != null) {
            if (!hijo.equals(p)) {
                balancear(hijo, hijo.getDerecho(), p);
            }
            padre.setDerecho(chequearBalance(hijo));
            padre.calcularAltura();
        }
    }

    private NodoAVL chequearBalance(NodoAVL nodo) {
        int balancePadre = calcularBalance(nodo), balanceHijo;
        if (balancePadre > 1) {                                              // si el balance es >1 entonces hay que calcular el balance de su hijo izquierdo
            balanceHijo = calcularBalance(nodo.getIzquierdo());
            if (balanceHijo >= 0) {                                           // si el balance de su hijo es mayor a 0 es rotacion simple a la derehca
                nodo = rotarDerecha(nodo);
            } else {                                                         // sino rotacion doble izquierda - derecha     
                nodo.setIzquierdo(rotarIzquierda(nodo.getIzquierdo()));
                nodo = rotarDerecha(nodo);
            }
        } else {
            if (balancePadre < -1) {
                balanceHijo = calcularBalance(nodo.getDerecho());
                if (balanceHijo <= 0) {
                    nodo = rotarIzquierda(nodo);
                } else {
                    nodo.setDerecho(rotarDerecha(nodo.getDerecho()));
                    nodo = rotarIzquierda(nodo);
                }
            }
        }
        return nodo;
    }

    private int calcularBalance(NodoAVL nodo) {
        int balance;
        if (nodo.getIzquierdo() != null && nodo.getDerecho() != null) {
            balance = nodo.getIzquierdo().getAltura() - nodo.getDerecho().getAltura();
        } else {
            if (nodo.getIzquierdo() != null) {
                balance = nodo.getIzquierdo().getAltura() - (-1);
            } else {
                if (nodo.getDerecho() != null) {
                    balance = -1 - nodo.getDerecho().getAltura();
                } else {
                    balance = 0;
                }
            }
        }
        return balance;
    }

    private NodoAVL rotarIzquierda(NodoAVL pivote) {
        NodoAVL HD = pivote.getDerecho();
        NodoAVL aux = HD.getIzquierdo();
        HD.setIzquierdo(pivote);
        pivote.setDerecho(aux);
        pivote.calcularAltura();
        HD.calcularAltura();
        return HD;
    }

    private NodoAVL rotarDerecha(NodoAVL pivote) {

        NodoAVL HI = pivote.getIzquierdo();
        NodoAVL aux = HI.getDerecho();
        HI.setDerecho(pivote);
        pivote.setIzquierdo(aux);
        pivote.calcularAltura();
        HI.calcularAltura();
        return HI;
    }

    public int altura(Comparable buscado) {
        NodoAVL nodo = obtenerNodo(this.raiz, buscado);
        return nodo.getAltura();
    }

    private NodoAVL obtenerNodo(NodoAVL nodo, Comparable buscado) {
        //Privado, busca un elemento con la clave ingresada por parametro
        //si lo encuentra devuelve al nodo que contiene la clave buscada.
        NodoAVL resultado = null;
        if (nodo != null) {
            if (nodo.getClave().compareTo(buscado) == 0) {
                resultado = nodo;    //Se encontro el elemento buscado
            } else {
                if (nodo.getClave().compareTo(buscado) > 0) {
                    //Si el codigo es menor al nodo visitado, va por la izquierda
                    resultado = obtenerNodo(nodo.getIzquierdo(), buscado);
                } //Si no, va por la derecha
                else {
                    resultado = obtenerNodo(nodo.getDerecho(), buscado);
                }
            }
        }
        return resultado;
    }

    public boolean pertenece(Comparable elem) {
        //Devuelve true si una clave indicada existe en el arbol
        boolean res = false;
        if (this.raiz != null) {
            res = perteneceAux(this.raiz, elem);
        }
        return res;
    }

    private boolean perteneceAux(NodoAVL n, Comparable elem) {
        //Privado usado por pertenece
        boolean res = false;
        if (n != null) {
            if (n.getClave().compareTo(elem) == 0) {
                res = true;
            } else {
                if (n.getClave().compareTo(elem) > 0) {
                    res = perteneceAux(n.getIzquierdo(), elem);
                } else {
                    res = perteneceAux(n.getDerecho(), elem);
                }
            }
        }
        return res;
    }

    public Lista listar() {
        Lista res = new Lista();
        if (this.raiz != null) {
            listarAux(this.raiz, res);
        }
        return res;
    }

    private void listarAux(NodoAVL n, Lista res) {
        if (n != null) {
            listarAux(n.getIzquierdo(), res);
            res.insertar(n.getElem(), res.longitud() + 1);
            listarAux(n.getDerecho(), res);
        }
    }

    public Object obtenerElemento(Comparable buscado) {
        //Publico, devuelve el elemento del nodo de la clave buscada, caso contrario devuelve null
        NodoAVL a = this.obtenerNodo(raiz, buscado);
        if (a == null) {
            return null;
        } else {
            return a.getElem();
        }
    }

    public Lista listarPreOrden() {
        Lista lista = new Lista();
        listarPreOrdenAux(this.raiz, lista);
        return lista;
    }

    private void listarPreOrdenAux(NodoAVL n, Lista l) {
        if (n != null) {
            l.insertar(n.getElem(), l.longitud() + 1);
            listarPreOrdenAux(n.getIzquierdo(), l);
            listarPreOrdenAux(n.getDerecho(), l);
        }
    }

    private Object minimoElemAux(NodoAVL a) {
        Object minimo;
        while (a.getIzquierdo() != null) {
            a = a.getIzquierdo();
        }
        minimo = a.getElem();
        return minimo;
    }

    private Object maximoElemAux(NodoAVL a) {
        Object max;
        while (a.getDerecho() != null) {
            a = a.getDerecho();
        }
        max = a.getElem();
        return max;
    }

    public Object minimoElem() {
        Object min = null;
        if (this.raiz != null) {
            min = minimoElemAux(this.raiz);
        }
        return min;

    }

    public Object maximoElem() {
        Object max = null;
        if (this.raiz != null) {
            max = maximoElemAux(this.raiz);
        }
        return max;
    }

    public Lista listarMayoresQue(Comparable elem) {
        Lista lis = new Lista();
        if (this.raiz != null) {
            listarMayoresQueAux(this.raiz, elem, lis);
        }
        return lis;
    }

    private void listarMayoresQueAux(NodoAVL n, Comparable elem, Lista l) {
        if (n != null) {
            if (n.getClave().compareTo(elem) < 0) {
                listarMayoresQueAux(n.getDerecho(), elem, l);
            } else {
                listarMayoresQueAux(n.getIzquierdo(), elem, l);
                l.insertar(n.getElem(), l.longitud() + 1);
                listarMayoresQueAux(n.getDerecho(), elem, l);
            }
        }
    }

    public Lista listarMenoresQue(Comparable elem) {
        Lista lis = new Lista();
        if (this.raiz != null) {
            listarMenoresQueAux(this.raiz, elem, lis);
        }
        return lis;
    }

    private void listarMenoresQueAux(NodoAVL n, Comparable elem, Lista l) {
        if (n != null) {
            if (n.getClave().compareTo(elem) > 0) {
                listarMenoresQueAux(n.getIzquierdo(), elem, l);
            } else {
                listarMenoresQueAux(n.getIzquierdo(), elem, l);
                l.insertar(n.getElem(), l.longitud() + 1);
                listarMenoresQueAux(n.getDerecho(), elem, l);
            }
        }
    }

    public Lista listarRango(Comparable elemMenor, Comparable elemMayor) {
        Lista lis = new Lista();
        if (this.raiz != null) {
            if (elemMenor.compareTo(elemMayor) <= 0) {
                listarRangoAux(this.raiz, elemMenor, elemMayor, lis);
            }
        }
        return lis;
    }

    private void listarRangoAux(NodoAVL n, Comparable elemMenor, Comparable elemMayor, Lista l) {
        if (n != null) {
            if (n.getClave().compareTo(elemMenor) < 0) {
                listarRangoAux(n.getDerecho(), elemMenor, elemMayor, l);
            } else {
                if (n.getClave().compareTo(elemMayor) > 0) {
                    listarRangoAux(n.getIzquierdo(), elemMenor, elemMayor, l);
                } else {
                    listarRangoAux(n.getIzquierdo(), elemMenor, elemMayor, l);
                    l.insertar(n.getClave(), l.longitud() + 1); //Cambiado n.getElem() por n.getClave()
                    listarRangoAux(n.getDerecho(), elemMenor, elemMayor, l);
                }
            }
        }
    }

    @Override
    public String toString() {
        return toStringCodigoAux(this.raiz);
    }

    private String toStringCodigoAux(NodoAVL i) {
        String s = "";
        if (i != null) {
            if (i.getIzquierdo() == null && i.getDerecho() == null) {
                s += i.getElem().toString() + "\n\tHI: - \n\tHD: -" + "\n";
            } else {
                if (i.getIzquierdo() == null) {
                    s += i.getElem().toString() + "\n\tHI: - \n\tHD: " + i.getDerecho().getElem().toString() + "\n";
                    s += toStringCodigoAux(i.getDerecho());
                } else {
                    if (i.getDerecho() == null) {
                        s += i.getElem().toString() + "\n\tHI: " + i.getIzquierdo().getElem().toString() + " \n\tHD: -" + "\n";
                        s = s + toStringCodigoAux(i.getIzquierdo());
                    } else {
                        s += i.getElem().toString() + "\n\tHI: " + i.getIzquierdo().getElem().toString() + "\n\tHD: " + i.getDerecho().getElem().toString() + "\n";
                        s += toStringCodigoAux(i.getIzquierdo());
                        s += toStringCodigoAux(i.getDerecho());
                    }
                }
            }
        }
        return s;
    }
}
