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
import java.util.*;
import java.io.*;
import lineales.dinamicas.Lista;
import conjuntistas.ArbolAVL;
import conjuntistas.ArbolBB;

public class Sistema {

    public static void main(String[] args) throws java.io.IOException {
        boolean sigue = true;
        boolean subMenu = true;
        Grafo grafoAero = new Grafo();
        HashMap<ClaveCliente, Lista> mapClientes = new HashMap<>();
        HashMap<String, Lista> mapVuelo = new HashMap<>();
        HashMap<String, Lista> auxAeroyVuelos = new HashMap<>();
        ArbolAVL arbolVuelos = new ArbolAVL();
        ArbolAVL arbolClientes = new ArbolAVL();

        Scanner reader = new Scanner(System.in);
        int opcion, opcSubMenu;
        String archivo = "src/agencia/viajes/carga.txt";
        String s1, charQueNoSirve;
        BufferedReader buf = new BufferedReader(new FileReader(archivo));

        while (buf.ready()) {
            s1 = buf.readLine();
            char primera = s1.charAt(0);
            switch (primera) {
                case 'Y':
                    StringTokenizer st = new StringTokenizer(s1, ",:", false);
                    charQueNoSirve = st.nextToken();
                    agregarAeropuerto(grafoAero, auxAeroyVuelos, st.nextToken(), st.nextToken(), st.nextToken());
                    break;
                case 'V':
                    StringTokenizer st1 = new StringTokenizer(s1, ", ", false);
                    charQueNoSirve = st1.nextToken();
                    String codV = st1.nextToken();
                    boolean a = agregarVuelo(mapVuelo, auxAeroyVuelos, grafoAero, arbolVuelos, codV, st1.nextToken(), st1.nextToken(), st1.nextToken(), st1.nextToken());
                    break;
                case 'C':
                    StringTokenizer st2 = new StringTokenizer(s1, ",: ", false);
                    charQueNoSirve = st2.nextToken();
                    agregarCliente(mapClientes, arbolClientes, st2.nextToken(), st2.nextToken(), st2.nextToken(), st2.nextToken());
                    break;
                case 'P':
                    StringTokenizer st3 = new StringTokenizer(s1, ",: ", false);
                    charQueNoSirve = st3.nextToken();
                    agregarPasaje(arbolVuelos, arbolClientes, mapClientes, mapVuelo, st3.nextToken(), st3.nextToken(), Integer.parseInt(st3.nextToken()), st3.nextToken(), st3.nextToken());
                    break;
            }
        }

        while (sigue) {
            System.out.println("-----BIENVENIDO AL SISTEMA DE EDAT-VIAJES-----");
            System.out.println("-----POR FAVOR SELECCIONE UNA OPCIÓN-----");
            System.out.println("1. A-B-M de AEROPUERTOS");
            System.out.println("2. A-B-M de CLIENTES");
            System.out.println("3. A-B-M de VUELOS");
            System.out.println("4. A-B-M de PASAJES");
            System.out.println("------------------------");
            System.out.println("5. CONSULTAS sobre CLIENTES");
            System.out.println("6. CONSULTAS sobre VUELOS");
            System.out.println("7. CONSULTAS sobre TIEMPOS DE VIAJE");
            System.out.println("8. MOSTRAR MEJORES CLIENTES");
            System.out.println("9. MOSTRAR SISTEMA (DEBUG)");
            System.out.println("------------------------");
            System.out.println("0. TERMINAR EJECUCIÓN");

            opcion = reader.nextInt();
            String codigo, codVuelo, origen, destino, ciudad, numeroTel, tipoDNI, numDNI, nombs, apes, dir, nacimiento;
            String horaSa, horaLl, fecha;
            int numAsiento;
            boolean exito;

            switch (opcion) {
                case 1://-----------A-B-M AEROPUERTOS------------
                    subMenu = true;
                    while (subMenu) {
                        System.out.println("----------ALTAS/BAJAS/MODIFICACIONES DE AEROPUERTOS----------");
                        System.out.println("----------SELECCIONE UNA OPCION----------");
                        System.out.println("--------1. AÑADIR AEROPUERTO--------");
                        System.out.println("--------2. ELIMINAR AEROPUERTO--------");
                        System.out.println("--------3. MODIFICAR UN AEROPUERTO--------");
                        System.out.println("--------0. SALIR--------");
                        opcSubMenu = reader.nextInt();
                        switch (opcSubMenu) {
                            case 1:
                                System.out.println("Ingrese el código del nuevo aeropuerto (formato: ABC)");
                                codigo = reader.next();
                                if (!buscaAero(codigo, grafoAero)) { //El AERO no existia previamente
                                    System.out.println("Ingrese Ciudad");
                                    ciudad = reader.next();
                                    System.out.println("Ingrese numero de telefono (formato: 123-4567890)");
                                    numeroTel = reader.next();

                                    exito = agregarAeropuerto(grafoAero, auxAeroyVuelos, codigo, ciudad, numeroTel); //Se agrega el Aeropuerto
                                    if (exito) {
                                        escribir("El Aeropuerto de codigo " + codigo + " se agrego correctamente");
                                        System.out.println("El Aeropuerto de codigo " + codigo + " se agrego correctamente");
                                    }
                                } else {
                                    System.out.println("El Aeropuerto de codigo " + codigo + " ya existe.");
                                }
                                break;
                            case 2:
                                System.out.println("Ingrese el código del aeropuerto a borrar (formato: ABC)");
                                codigo = reader.next();
                                exito = grafoAero.eliminarVertice(codigo);
                                if (exito) {
                                    System.out.println("ADVERTENCIA: AL BORRAR EL AEROPUERTO " + codigo + " TAMBIEN BORRARA VUELOS HACIA Y DESDE EL AEROPUERTO, Y CANCELARA SUS PASAJES");
                                    System.out.println("¿DESEA CONTINUAR? s/n");
                                    String opci = reader.next();
                                    if (opci.equalsIgnoreCase("s")) {
                                        Lista aux4 = (Lista) auxAeroyVuelos.get(codigo);
                                        if (aux4 != null) {
                                            for (int i = 1; i <= aux4.longitud(); i++) {
                                                String aux = (String) aux4.recuperar(i);
                                                cancelarTodoUnVuelo((Lista) mapVuelo.get(aux));
                                                mapVuelo.remove(aux);
                                            }
                                            exito = true;
                                        }
                                        auxAeroyVuelos.remove(codigo);
                                        if (exito) {
                                            escribir("El aeropuerto de codigo " + codigo + " se borro exitosamente");
                                            System.out.println("El aeropuerto de codigo " + codigo + " se borro exitosamente");
                                        }
                                    } else {
                                        System.out.println("SE CANCELO EL BORRADO DEL AEROPUERTO " + codigo);
                                    }
                                } else {
                                    System.out.println("El aeropuerto de codigo " + codigo + " no fue encontrado");
                                }
                                break;
                            case 3:
                                System.out.println("Ingrese el código del aeropuerto a modificar (formato: ABC)");
                                codigo = reader.next();
                                Aeropuerto auxi = (Aeropuerto) grafoAero.getElem(codigo);
                                if (auxi != null) { //El Aeropuerto existe
                                    System.out.println("Ingrese la ciudad");
                                    ciudad = reader.next();
                                    System.out.println("Ingrese el numero de telefono (formato: 123-4567890)");
                                    numeroTel = reader.next();
                                    modAero(auxi, ciudad, numeroTel);
                                    escribir("MODIFICADO EXITOSAMENTE: " + auxi.toString());
                                    System.out.println("MODIFICADO EXITOSAMENTE: " + auxi.toString());
                                } else {
                                    System.out.println("El Aeropuerto " + codigo + " no existe");
                                }
                                break;
                            case 0:
                                subMenu = false;
                                break;
                            default:
                                System.out.println("Opcion invalida");
                                subMenu = false;
                                break;
                        }
                    }
                    break;
                case 2://-----------A-B-M CLIENTES------------
                    subMenu = true;
                    while (subMenu) {
                        System.out.println("----------ALTAS/BAJAS/MODIFICACIONES DE CLIENTES----------");
                        System.out.println("----------SELECCIONE UNA OPCION----------");
                        System.out.println("--------1. AÑADIR CLIENTE--------");
                        System.out.println("--------2. ELIMINAR CLIENTE--------");
                        System.out.println("--------3. MODIFICAR UN CLIENTE--------");
                        System.out.println("--------0. SALIR--------");
                        opcSubMenu = reader.nextInt();
                        switch (opcSubMenu) {
                            case 1:
                                System.out.println("Ingrese tipo documento");
                                tipoDNI = reader.next();
                                System.out.println("Ingrese numero documento (formato 11.111.111)");
                                numDNI = reader.next();
                                System.out.println("Ingrese nombres");
                                nombs = reader.next();
                                System.out.println("Ingrese apellidos");
                                apes = reader.next();
                                exito = agregarCliente(mapClientes, arbolClientes, tipoDNI, numDNI, nombs, apes);
                                if (exito) {
                                    escribir("Cliente " + tipoDNI + numDNI + " agregado con exito!");
                                    System.out.println("Cliente " + tipoDNI + numDNI + " agregado con exito!");
                                } else {
                                    System.out.println("El Cliente " + tipoDNI + numDNI + " no fue agregado");
                                }
                                break;
                            case 2:
                                System.out.println("Ingrese tipo documento");
                                tipoDNI = reader.next();
                                System.out.println("Ingrese numero documento (formato 11.111.111)");
                                numDNI = reader.next();
                                ClaveCliente cla = new ClaveCliente(tipoDNI, numDNI);
                                exito = arbolClientes.pertenece(cla);
                                if (exito) { //El cliente existe, lo borramos del arbol y borramos sus pasajes.
                                    exito = arbolClientes.eliminar(cla);
                                    mapClientes.remove(cla);
                                    if (exito) {
                                        escribir("Cliente " + tipoDNI + numDNI + " borrado con exito!");
                                        System.out.println("Cliente " + tipoDNI + numDNI + " borrado con exito!");
                                    }
                                } else {
                                    System.out.println("El cliente no existe, no se puede borrar");
                                }
                                break;
                            case 3:
                                System.out.println("Ingrese tipo documento");
                                tipoDNI = reader.next();
                                System.out.println("Ingrese numero documento (formato 11.111.111)");
                                numDNI = reader.next();
                                ClaveCliente aux = new ClaveCliente(tipoDNI, numDNI);
                                exito = arbolClientes.pertenece(aux);
                                if (exito) {
                                    boolean sig = true;
                                    while (sig) {
                                        System.out.println("¿Que desea modificar/agregar del cliente? Seleccione opcion");
                                        System.out.println("1. Nombres");
                                        System.out.println("2. Apellidos");
                                        System.out.println("3. Numero de telefono");
                                        System.out.println("4. Direccion");
                                        System.out.println("5. Fecha de nacimiento");
                                        System.out.println("0. Cancelar");
                                        int o = reader.nextInt();
                                        Cliente cli;
                                        switch (o) {
                                            case 1:
                                                System.out.println("Ingrese nuevo nombre");
                                                nombs = reader.next();
                                                cli = (Cliente) arbolClientes.obtenerElemento(aux);
                                                cli.setNombres(nombs);
                                                break;
                                            case 2:
                                                System.out.println("Ingrese nuevo apellido");
                                                apes = reader.next();
                                                cli = (Cliente) arbolClientes.obtenerElemento(aux);
                                                cli.setApellidos(apes);
                                                break;
                                            case 3:
                                                System.out.println("Ingrese nuevo num telefonico");
                                                numeroTel = reader.next();
                                                cli = (Cliente) arbolClientes.obtenerElemento(aux);
                                                cli.setNumTel(numeroTel);
                                                break;
                                            case 4:
                                                System.out.println("Ingrese nueva direccion");
                                                dir = reader.next();
                                                cli = (Cliente) arbolClientes.obtenerElemento(aux);
                                                cli.setDomicilio(dir);
                                                break;
                                            case 5:
                                                System.out.println("Ingrese nueva fecha de nacimiento");
                                                nacimiento = reader.next();
                                                cli = (Cliente) arbolClientes.obtenerElemento(aux);
                                                cli.setFechaNac(nacimiento);
                                                break;
                                            case 0:
                                                sig = false;
                                                break;
                                            default:
                                                System.out.println("Opcion invalida");
                                                break;
                                        }
                                    }
                                } else {
                                    System.out.println("ERROR: El cliente no existe");
                                }
                                break;
                            default:
                                System.out.println("Opcion invalida");
                                subMenu = false;
                                break;
                        }
                    }
                    break;
                case 3: //-----------A-B-M VUELOS------------
                    subMenu = true;
                    while (subMenu) {
                        System.out.println("----------ALTAS/BAJAS/MODIFICACIONES DE VUELOS----------");
                        System.out.println("----------SELECCIONE UNA OPCION----------");
                        System.out.println("--------1. AÑADIR VUELO--------");
                        System.out.println("--------2. ELIMINAR VUELO--------");
                        System.out.println("--------3. MODIFICAR UN VUELO--------");
                        System.out.println("--------4. REALIZAR UN VUELO--------");
                        System.out.println("--------0. SALIR--------");
                        opcSubMenu = reader.nextInt();
                        switch (opcSubMenu) {
                            case 1:
                                System.out.println("Ingrese el codigo de vuelo a añadir (formato: AB1234)");
                                codVuelo = reader.next();
                                if (!buscaVuelo(codVuelo, arbolVuelos)) { //Si el vuelo no existe, lo cargamos
                                    System.out.println("Ingrese codigo de aeropuerto de origen (formato: ABC)");
                                    origen = reader.next();
                                    System.out.println("Ingrese codigo de aeropuerto de destino (formato: ABC)");
                                    destino = reader.next();
                                    System.out.println("Ingrese hora de salida (formato: 00:00)");
                                    horaSa = reader.next();
                                    System.out.println("Ingrese hora de llegada (formato: 00:00)");
                                    horaLl = reader.next();
                                    boolean salioBien = agregarVuelo(mapVuelo, auxAeroyVuelos, grafoAero, arbolVuelos, codVuelo, origen, destino, horaSa, horaLl);
                                    if (salioBien) {
                                        escribir("El Vuelo de codigo " + codVuelo + " se ingresó correctamente al sistema");
                                        System.out.println("El Vuelo de codigo " + codVuelo + " se ingresó correctamente al sistema");
                                    } else {
                                        System.out.println("ERROR: El Vuelo no se cargo. Compruebe que ambos aeropuertos " + origen + " y " + destino + "existan");
                                    }
                                } else {
                                    System.out.println("ERROR: El Vuelo de codigo " + codVuelo + " ya existe en el sistema");
                                }
                                break;
                            case 2:
                                System.out.println("ADVERTENCIA: SE CANCELARAN LOS PASAJES DEL VUELO QUE ELIMINE");
                                System.out.println("¿DESEA CONTINUAR? s/n");
                                String opcion1 = reader.next();
                                if (opcion1.equalsIgnoreCase("s")) {
                                    System.out.println("Ingrese el codigo de vuelo a borrar(formato: AB1234)");
                                    codVuelo = reader.next();
                                    Vuelo aux;
                                    if ((aux = (Vuelo) arbolVuelos.obtenerElemento(codVuelo)) != null) { //El vuelo existia.
                                        int etique = grafoAero.calculaMinutos(aux.getHoraSal(), aux.getHoraEnt());
                                        String codSalida = aux.getAeroOrigen();
                                        String codLlegada = aux.getAeroDestino();
                                        boolean salioBien = arbolVuelos.eliminar(codVuelo); //Se borra el vuelo del arbol de vuelos
                                        Lista lisAux = (Lista) mapVuelo.get(codVuelo);
                                        cancelarTodoUnVuelo(lisAux); //Se cancelan todos los pasajes que hayan, los clientes lo veran como "cancelado"
                                        mapVuelo.remove(codVuelo); //Se borra el vuelo del HashMap y su lista de vuelos de cada dia
                                        if (salioBien) { //Si se borro el vuelo del arbol, borramos el arco que se creó al crear el vuelo (ida y vuelta)
                                            grafoAero.eliminarArcoSegunEtiqueta(codSalida, codLlegada, etique);
                                            escribir("Borrado Vuelo " + codVuelo + " y el arco que recorria");
                                            System.out.println("El Vuelo (y el arco que este recorria) se borro correctamente!");
                                        } else {
                                            System.out.println("El vuelo se borro pero no el arco");
                                        }
                                    } else {
                                        System.out.println("ERROR: El Vuelo de codigo " + codVuelo + " no existe");
                                    }
                                } else {
                                    System.out.println("ELIMINACION DE VUELO CANCELADA");
                                }
                                break;
                            case 3:
                                System.out.println("Ingrese el codigo del Vuelo a modificar(formato: AB1234)");
                                codVuelo = reader.next();
                                Vuelo aux2;
                                if ((aux2 = (Vuelo) arbolVuelos.obtenerElemento(codVuelo)) != null) {
                                    int etique = grafoAero.calculaMinutos(aux2.getHoraSal(), aux2.getHoraEnt());
                                    grafoAero.eliminarArcoSegunEtiqueta(aux2.getAeroOrigen(), aux2.getAeroDestino(), etique);
                                    //Una vez borrado el viejo arco, pedimos los nuevos datos.
                                    System.out.println("Ingrese nuevo Aeropuerto de origen");
                                    origen = reader.next();
                                    System.out.println("Ingrese nuevo Aeropuerto de destino");
                                    destino = reader.next();
                                    aux2.setAeroOrigen(origen);
                                    aux2.setAeroDestino(destino);
                                    System.out.println("Ingrese nuevo horario de salida");
                                    horaSa = reader.next();
                                    System.out.println("Ingrese nuevo horario de llegada");
                                    horaLl = reader.next();
                                    aux2.setHoraSal(horaSa);
                                    aux2.setHoraEnt(horaLl);
                                    //Se crea el arco nuevo con los nuevos datos del vuelo.
                                    grafoAero.insertarArco(origen, destino, grafoAero.calculaMinutos(horaSa, horaLl));
                                    escribir("Vuelo " + codVuelo + " modificado correctamente");
                                    System.out.println("El Vuelo (y el arco del Vuelo) se modificaron correctamente!");
                                } else {
                                    System.out.println("ERROR: Ese Vuelo no existe!");
                                }
                                break;
                            case 4:
                                System.out.println("Ingrese el codigo de Vuelo a despegar (Formato AB1234)");
                                codVuelo = reader.next();
                                if (mapVuelo.containsKey(codVuelo)) { //El Vuelo existe
                                    System.out.println("     Ingrese la fecha del Viaje (formato yyyymmdd)");
                                    fecha = reader.next();
                                    int posi = mapVuelo.get(codVuelo).localizar(fecha);
                                    if (posi != -1) {//El Viaje de ese día existe. Lo hacemos despegar
                                        ((Viaje) mapVuelo.get(codVuelo).recuperar(posi)).viajar();
                                        escribir("El Viaje de " + codVuelo + " fecha " + fecha + " despego");
                                        System.out.println("          EXITO! El Viaje despegó correctamente");
                                    } else {
                                        System.out.println("ERROR: No hay Viaje de este Vuelo para la fecha dada");
                                    }
                                } else {
                                    System.out.println("     ERROR: El vuelo de codigo " + codVuelo + " no existe");
                                }
                                break;
                            case 0:
                                subMenu = false;
                                break;
                        }
                    }
                    break;
                case 4://-----------A-B-M PASAJES------------
                    subMenu = true;
                    while (subMenu) {
                        System.out.println("----------ALTAS/BAJAS/MODIFICACIONES DE PASAJES----------");
                        System.out.println("----------SELECCIONE UNA OPCION----------");
                        System.out.println("--------1. AÑADIR PASAJE--------");
                        System.out.println("--------2. ELIMINAR PASAJE--------");
                        System.out.println("--------3. MODIFICAR UN PASAJE--------");
                        System.out.println("--------0. SALIR--------");
                        opcSubMenu = reader.nextInt();
                        switch (opcSubMenu) {
                            case 1:
                                System.out.println("Ingrese el codigo de Vuelo del Pasaje");
                                codVuelo = reader.next();
                                if (mapVuelo.get(codVuelo) != null) {
                                    System.out.println("Ingrese numero de asiento");
                                    numAsiento = reader.nextInt();
                                    System.out.println("Ingrese tipo de DNI de cliente");
                                    tipoDNI = reader.next();
                                    System.out.println("Ingrese numero de DNI de cliente");
                                    numDNI = reader.next();
                                    System.out.println("Ingrese Fecha: (formato: aaaammdd/yyyymmdd)");
                                    fecha = reader.next();
                                    boolean s = agregarPasaje(arbolVuelos, arbolClientes, mapClientes, mapVuelo, codVuelo, fecha, numAsiento, tipoDNI, numDNI);
                                    if (s) {//Si se agrego el pasaje
                                        escribir("El pasaje para el Vuelo " + codVuelo + ", asiento " + numAsiento + " y cliente " + tipoDNI + numDNI + " fue agregado con exito!");
                                        System.out.println("El pasaje para el Vuelo " + codVuelo + ", asiento " + numAsiento + " y cliente " + tipoDNI + numDNI + " fue agregado con exito!");
                                    } else {
                                        System.out.println("El pasaje para el Vuelo " + codVuelo + " NO fue agregado al sistema. Compruebe los datos ingresados.");
                                    }
                                } else {
                                    System.out.println("ERROR: El Vuelo no existe o fue previamente borrado");
                                }
                                break;
                            case 2:
                                System.out.println("Ingrese el codigo de Vuelo del Pasaje");
                                codVuelo = reader.next();
                                if (mapVuelo.get(codVuelo) != null) {
                                    System.out.println("Ingrese numero de asiento");
                                    numAsiento = reader.nextInt();
                                    System.out.println("Ingrese tipo de DNI de cliente");
                                    tipoDNI = reader.next();
                                    System.out.println("Ingrese numero de DNI de cliente");
                                    numDNI = reader.next();
                                    System.out.println("Ingrese Fecha: (formato: aaaammdd/yyyymmdd)");
                                    fecha = reader.next();
                                    boolean s = eliminarPasaje(arbolVuelos, arbolClientes, mapClientes, mapVuelo, codVuelo, fecha, numAsiento, tipoDNI, numDNI);
                                    if (s) {
                                        escribir("Pasaje de " + codVuelo + " asiento " + numAsiento + " fecha" + fecha + " fue borrado");
                                        System.out.println("       EXITO: El Pasaje se borró exitosamente!");
                                    } else {
                                        System.out.println("       ERROR: El Pasaje no se eliminó del sistema!");
                                    }
                                } else {
                                    System.out.println("ERROR: El Vuelo no existe o fue previamente borrado");
                                }
                                break;
                            case 3:
                                System.out.println("Ingrese tipo de DNI de cliente");
                                tipoDNI = reader.next();
                                System.out.println("Ingrese numero de DNI de cliente");
                                numDNI = reader.next();
                                ClaveCliente clav = new ClaveCliente(tipoDNI, numDNI);
                                boolean s = arbolClientes.pertenece(clav);
                                if (s) { //El cliente existe.
                                    System.out.println("Ingrese el codigo de Vuelo del Pasaje");
                                    codVuelo = reader.next();
                                    System.out.println("Ingrese numero de asiento");
                                    numAsiento = reader.nextInt();
                                    System.out.println("Ingrese Fecha: (formato: aaaammdd/yyyymmdd)");
                                    fecha = reader.next();
                                    Pasaje pasajeAux = new Pasaje(codVuelo, fecha, numAsiento);

                                    Lista lisAuxDelVuelo = ((Lista) mapVuelo.get(codVuelo));
                                    int posiEnVuelo = lisAuxDelVuelo.localizar(fecha);

                                    int posiEnCliente = mapClientes.get(clav).localizar(pasajeAux);
                                    if (posiEnVuelo != -1 && posiEnCliente != -1) { //El pasaje buscado existe en la lista de pasajes del cliente
                                        Viaje viajeAux = (Viaje) lisAuxDelVuelo.recuperar(posiEnVuelo);
                                        int posiEnViaje = viajeAux.getListaPasajes().localizar(pasajeAux);
                                        pasajeAux = (Pasaje) mapClientes.get(clav).recuperar(posiEnCliente);
                                        boolean si = true;
                                        while (si) {
                                            System.out.println("     1. Cambiar la fecha del Pasaje.");
                                            System.out.println("     2. Cambiar el numero de asiento.");
                                            System.out.println("     0. Cancelar.");
                                            int opcion1 = reader.nextInt();
                                            switch (opcion1) {
                                                case 1:
                                                    System.out.println("          Ingrese la nueva fecha");
                                                    String fec = reader.next();
                                                    int x = lisAuxDelVuelo.localizar(fec);
                                                    if (x != -1) { //El Viaje para este día ya existía en la lista del Vuelo codVuelo
                                                        Viaje aux2 = (Viaje) lisAuxDelVuelo.recuperar(x);
                                                        if (aux2.checkAsientoDisponible(codVuelo, numAsiento)) { //El asiento está disponible para la nueva fecha
                                                            pasajeAux.setFecha(fec);
                                                            ((Viaje) lisAuxDelVuelo.recuperar(x)).agregarPasajeAlViaje(pasajeAux);
                                                            ((Viaje) lisAuxDelVuelo.recuperar(posiEnVuelo)).borrarPasajeAlViaje(pasajeAux);
                                                        } else {
                                                            System.out.println("              ERROR: El asiento no está disponible para la nueva fecha");
                                                        }
                                                    } else {
                                                        int longitud = lisAuxDelVuelo.longitud() + 1;
                                                        lisAuxDelVuelo.insertar(new Viaje(fec), longitud);
                                                        pasajeAux.setFecha(fec);
                                                        ((Viaje) lisAuxDelVuelo.recuperar(longitud)).agregarPasajeAlViaje(pasajeAux);
                                                        ((Viaje) lisAuxDelVuelo.recuperar(posiEnVuelo)).borrarPasajeAlViaje(pasajeAux);
                                                    }
                                                    break;
                                                case 2:
                                                    System.out.println("          Ingrese el nuevo numero de asiento");
                                                    int asiento = reader.nextInt();
                                                    if (viajeAux.checkAsientoDisponible(codVuelo, asiento)) {
                                                        ((Pasaje) viajeAux.getListaPasajes().recuperar(posiEnViaje)).setNumAsiento(asiento);
                                                        System.out.println("              El numero de asiento del Pasaje cambió correctamente!");
                                                    } else {
                                                        System.out.println("              ERROR: El nuevo numero de asiento del Pasaje ya esta ocupado");
                                                    }
                                                    break;
                                                case 0:
                                                    si = false;
                                                    break;
                                            }
                                        }
                                    } else {
                                        System.out.println("          ERROR: El Pasaje no existe dentro de la lista del cliente!");
                                    }
                                } else {
                                    System.out.println("     ERROR: El Cliente no existe!");
                                }
                                break;
                            case 0:
                                subMenu = false;
                                break;
                        }
                    }
                    break;
                case 5:
                    subMenu = true;
                    while (subMenu) {
                        System.out.println("          -----SELECCIONE UNA OPCION-----");
                        System.out.println("          ----- 1. Mostrar info y Pasajes de un Cliente-----");
                        System.out.println("          ----- 2. Mostrar ciudades visitadas de un Cliente-----");
                        System.out.println("          ----- 0. SALIR DE SUBMENU-----");
                        opcSubMenu = reader.nextInt();
                        switch (opcSubMenu) {
                            case 1:
                                System.out.println("Ingrese tipo de DNI de Cliente");
                                tipoDNI = reader.next();
                                System.out.println("Ingrese numero de DNI de Cliente");
                                numDNI = reader.next();
                                ClaveCliente aux = new ClaveCliente(tipoDNI, numDNI);
                                Cliente cli = (Cliente) arbolClientes.obtenerElemento(aux);
                                if (cli != null) { //El cliente existe
                                    System.out.println(cli.toString());
                                    System.out.println("Lista de Pasajes:");
                                    System.out.println(textoPasajesDeCliente(cli, mapClientes));
                                } else {
                                    System.out.println("     ERROR: El Cliente no existe");
                                }
                                break;
                            case 2:
                                System.out.println("Ingrese tipo de DNI de Cliente");
                                tipoDNI = reader.next();
                                System.out.println("Ingrese numero de DNI de Cliente");
                                numDNI = reader.next();
                                ClaveCliente cla = new ClaveCliente(tipoDNI, numDNI);
                                System.out.println("Las ciudades que visitó el cliente " + cla.toString() + " son:");
                                System.out.println(textoCiudadesDeCliente(cla, mapClientes, arbolVuelos, grafoAero));
                                break;
                            case 0:
                                subMenu = false;
                                break;
                        }
                    }
                    break;
                case 6:
                    subMenu = true;
                    while (subMenu) {
                        System.out.println("          -----SELECCIONE UNA OPCION-----");
                        System.out.println("          ----- 1. Mostrar info de un Vuelo-----");
                        System.out.println("          ----- 2. Mostrar rango entre dos Vuelos-----");
                        System.out.println("          ----- 0. SALIR DE SUBMENU-----");
                        opcSubMenu = reader.nextInt();
                        switch (opcSubMenu) {
                            case 1:
                                System.out.println("Ingrese codigo de Vuelo (formato AB1234)");
                                codVuelo = reader.next();
                                Lista l = mapVuelo.get(codVuelo);
                                if (l != null) {
                                    System.out.println("Ingrese una fecha (formato aaaammdd/yyyymmdd)");
                                    fecha = reader.next();
                                    int i = l.localizar(fecha);
                                    if (i != -1) {
                                        Viaje viAux = (Viaje) l.recuperar(i);
                                        Vuelo aux = (Vuelo) arbolVuelos.obtenerElemento(codVuelo);
                                        System.out.println(aux.toString()); //Se imprime la info del Vuelo en general
                                        System.out.println("La lista de pasajes para este vuelo y fecha es: ");
                                        System.out.println(viAux.toString());
                                    }
                                } else {
                                    System.out.println("ERROR: El vuelo de codigo " + codVuelo + " NO existe");
                                }
                                break;
                            case 2:
                                System.out.println("Ingrese el primer Vuelo para el rango");
                                codVuelo = reader.next();
                                System.out.println("Ingrese el segundo Vuelo para el rango");
                                String codVuelo2 = reader.next();
                                System.out.println("Entre " + codVuelo + " y " + codVuelo2 + " los codigos de Vuelo son:");
                                System.out.println(arbolVuelos.listarRango(codVuelo, codVuelo2).toString());
                                break;
                            case 0:
                                subMenu = false;
                                break;
                        }
                    }
                    break;
                case 7:
                    subMenu = true;
                    while (subMenu) {
                        System.out.println("          -----SELECCIONE UNA OPCION-----");
                        System.out.println("          ----- 1. Llegar a un Aeropuerto en un maximo de Vuelos dado-----");
                        System.out.println("          ----- 2. Obtener camino minimo entre dos Aeropuertos-----");
                        System.out.println("          ----- 0. SALIR DE SUBMENU-----");
                        opcSubMenu = reader.nextInt();
                        switch (opcSubMenu) {
                            case 1:
                                System.out.println("Ingrese el Aeropuerto de inicio (formato: ABC)");
                                origen = reader.next();
                                System.out.println("Ingrese el Aeropuerto de destino (formato: ABC)");
                                destino = reader.next();
                                System.out.println("Ingrese la cantidad de vuelos maxima");
                                int canti = reader.nextInt();
                                System.out.println("El recorrido es: (Al final de la lista se muestra la cantidad de vuelos)");
                                Lista liCam = grafoAero.caminoCantMaxima(origen, destino, canti);
                                if (liCam.esVacia()) {
                                    System.out.println("No es posible llegar de un aeropuerto a otro con " + canti + " vuelos");
                                } else {
                                    System.out.println(liCam.toString());
                                }
                                break;
                            case 2:
                                System.out.println("Ingrese el Aeropuerto de inicio (formato: ABC)");
                                origen = reader.next();
                                System.out.println("Ingrese el Aeropuerto de destino (formato: ABC)");
                                destino = reader.next();
                                System.out.println("El recorrido es: (Al final de la lista se muestran los minutos totales)");
                                System.out.println(grafoAero.caminoMasCortoPorMinutos(origen, destino).toString());
                                break;
                            case 0:
                                subMenu = false;
                                break;
                        }
                    }
                    break;
                case 8:
                    System.out.println("   Lista de Clientes desde el que compró mas Pasajes al que menos:");
                    System.out.println(ordenarClientesPorCompras(arbolClientes, mapClientes).listarInorden().toStringReves());
                    break;
                case 9:
                    System.out.println("     ---ARBOL DE CLIENTES---");
                    System.out.println(arbolClientes.toString());
                    System.out.println("     ---ARBOL DE VUELOS---");
                    System.out.println(arbolVuelos.toString());
                    System.out.println("     ---MAPA DE VUELOS---");
                    System.out.println(mapVuelo.keySet().toString());
                    System.out.println("     ---GRAFO DE AEROPUERTOS---");
                    System.out.println(grafoAero.toString());
                    break;
                case 0:
                    sigue = false;
                    escribir("     ----- EJECUCION FINALIZADA -----     ");
                    break;
            }
        }
    }

    private static void escribir(String cadena) {
        //Método que escribe en el archivo de texto la cadena enviada por parámetro
        File archivo = new File("src\\agencia\\viajes\\LOG.txt");
        try {
            FileWriter escribirArchivo = new FileWriter(archivo, true);
            try (BufferedWriter buffer = new BufferedWriter(escribirArchivo)) {
                buffer.write(cadena);
                buffer.newLine();
            }
        } catch (IOException ex) {
        }
    }

    public static boolean cancelarTodoUnVuelo(Lista lis) {
        //cancela todos los pasajes de un vuelo, para que al cliente le salgan cancelados
        boolean exito = false;
        if (lis != null) {
            for (int i = 1; i <= lis.longitud(); i++) {
                Viaje vi = (Viaje) lis.recuperar(i);
                vi.cancelar();
            }
            exito = true;
        }
        return exito;
    }

    private static ArbolBB ordenarClientesPorCompras(ArbolAVL arbCli, HashMap mapaCli) {
        Lista aux = arbCli.listarPreOrden();
        ArbolBB arb = new ArbolBB();
        while (!aux.esVacia()) {
            Cliente cli = (Cliente) aux.recuperar(1);
            ClaveCliente clave = cli.getClave();
            if (mapaCli.containsKey(clave)) {
                Lista aux2 = (Lista) mapaCli.get(clave);
                if (aux2 != null) {
                    int cantUnCli = (aux2.longitud()); //Obtiene la cantidad de pasajes comprados para UN cliente
                    if (cantUnCli > 0) {
                        arb.insertar("     " + cantUnCli + " comprados --> Cliente: " + clave.toString());
                    }
                }
            }
            aux.eliminar(1);
        }
        return arb;
    }

    private static String textoCiudadesDeCliente(ClaveCliente clav, HashMap mapCli, ArbolAVL arbolVuelos, Grafo grafoAeros) {
        //Para pasar a texto una lista de ciudades que un cliente visitó
        String texto = "";
        String tAux = "";
        Vuelo auxV;
        Lista aux = ((Lista) mapCli.get(clav)); //Lista de Pasajes del Cliente ingresado
        if (aux != null) {
            int longi = aux.longitud(); //Longitud de la lista de Pasajes del Cliente ingresado
            if (aux.esVacia()) {
                texto = "El cliente no ha volado a otras ciudades";
            } else {
                for (int i = 1; i <= longi; i++) {
                    Pasaje pas = (Pasaje) aux.recuperar(i); //Pasaje auxiliar sacado de la Lista de Pasajes del Cliente
                    if ((pas.getEstado().equalsIgnoreCase("volado")) && !(pas.getCodVuelo().equalsIgnoreCase(tAux))) {
                        auxV = (Vuelo) arbolVuelos.obtenerElemento(pas.getCodVuelo()); //Vuelo cuyo codVuelo es referenciado en el Pasaje visitado
                        tAux = pas.getCodVuelo();
                        texto = texto + "\n" + i + ") " + ((Aeropuerto) grafoAeros.getElem(auxV.getAeroDestino())).getCiudad();
                    }
                }
            }
        } else {
            System.out.println("ERROR: El Cliente no existe");
        }
        return texto;
    }

    private static String textoPasajesDeCliente(Cliente cliente, HashMap mapCli) {
        //Para pasar a texto la lista de Pasajes de un Cliente
        String texto = "";
        Lista aux = ((Lista) mapCli.get(cliente.getClave()));
        int longi = aux.longitud();
        if (aux.esVacia()) {
            texto = "Lista de Pasajes vacia.";
        } else {
            for (int i = 1; i <= longi; i++) {
                texto = texto + "\n" + aux.recuperar(i).toString();
            }
        }
        return texto;
    }

    private static boolean agregarAeropuerto(Grafo mapa, HashMap auxVuelos, String cod, String ciud, String tel) {
        Aeropuerto aer = new Aeropuerto(cod, ciud, tel);
        boolean exito = mapa.insertarVertice(aer);
        if (!exito) {
            System.out.println("El Aeropuerto " + cod + " NO se ingresó al sistema");
        } else {
            auxVuelos.put(cod, new Lista());
            escribir("Aeropuerto " + cod + " agregado al sistema");
        }
        return exito;
    }

    private static void modAero(Aeropuerto aero, String ciudad, String telef) {
        aero.setCiudad(ciudad);
        aero.setNumTel(telef);
    }

    private static boolean buscaAero(String codigo, Grafo mapa) {
        //Busca un aeropuerto por su codigo en el grafo de aeropuertos
        boolean encontrado = mapa.existeVertice(codigo);
        return encontrado;
    }

    private static boolean buscaVuelo(String codVuelo, ArbolAVL arbVue) {
        //Busca un vuelo por su codigo en el arbol AVL de vuelos.
        return arbVue.pertenece(codVuelo);
    }

    private static boolean agregarVuelo(HashMap mapVuelo, HashMap auxAeroVuelo, Grafo grafoAero, ArbolAVL arb, String codVuelo, String orig, String dest, String hoSal, String hoLl) {
        Vuelo vuelo = new Vuelo(codVuelo, orig, dest, hoSal, hoLl);
        boolean sigue = arb.insertar(vuelo, codVuelo);
        if (sigue) { //Si se inserto entonces creamos un arco entre los dos aeropuertos del vuelo
            sigue = grafoAero.insertarArco(orig, dest, grafoAero.calculaMinutos(hoSal, hoLl));
            if (sigue) {
                crearListaParaVuelo(mapVuelo, codVuelo);
                Lista aux = (Lista) auxAeroVuelo.get(orig);
                Lista aux2 = (Lista) auxAeroVuelo.get(dest);
                aux.insertar(codVuelo, aux.longitud() + 1);
                aux2.insertar(codVuelo, aux2.longitud() + 1);
                escribir("Vuelo " + codVuelo + " agregado al sistema");
            }
        }
        return sigue;
    }

    private static void crearListaParaVuelo(HashMap mapaVuelo, String codVuelo) {
        Lista lis = new Lista();
        mapaVuelo.put(codVuelo, lis);
    }

    private static boolean agregarCliente(HashMap mapa, ArbolAVL arbCli, String tipo, String numDoc, String nomb, String ape) {
        ClaveCliente clav = new ClaveCliente(tipo, numDoc);
        boolean exito = arbCli.pertenece(clav);
        if (!exito) { //Si el cliente no pertenecía anteriormente seguimos
            Cliente cli = new Cliente(clav);
            cli.setNombres(nomb);
            cli.setApellidos(ape);
            arbCli.insertar(cli, clav); //se inserta el Cliente en el arbol AVL
            Lista lis = new Lista();
            mapa.put(clav, lis);
            exito = true;
            escribir("Cliente " + clav.toString() + " agregado al sistema");
        } else { //Si el cliente ya estaba cargado se avisa que no se pudo ingresar
            System.out.println("El cliente no se ingreso (ya esta cargado)");
            exito = false;
        }
        return exito;
    }

    private static boolean eliminarPasaje(ArbolAVL arbVue, ArbolAVL arbCli, HashMap mapCliente, HashMap mapVuelo, String codVuelo, String fecha, int posAsiento, String tipoDNI, String numDNI) {
        boolean seElimino = false;
        if (arbVue.pertenece(codVuelo)) { //Verificamos primero que el vuelo exista
            System.out.println("El vuelo " + codVuelo + " existe. Buscando el cliente que compro el pasaje...");
            ClaveCliente clave = new ClaveCliente(tipoDNI, numDNI);
            boolean hayVuelo = mapVuelo.get(codVuelo) != null;
            if (arbCli.pertenece(clave) && hayVuelo) {
                System.out.println("       El Cliente tambien existe. Buscando el pasaje de esa fecha...");
                Lista auxiliar = (Lista) mapCliente.get(clave);
                Pasaje pas = new Pasaje(codVuelo, fecha, posAsiento);
                //agregar if
                int locacion = auxiliar.localizar(pas);
                if (locacion != -1) {
                    seElimino = auxiliar.eliminar(locacion); //Borra el pasaje de la lista de pasajes del cliente.
                    if (seElimino) {
                        System.out.println("               El Pasaje se borro de la lista del Cliente. Liberando un asiento del vuelo...");
                        Lista auxVuelo = (Lista) mapVuelo.get(codVuelo);
                        int pos = auxVuelo.localizar(fecha);
                        if (pos != -1) {

                            ((Viaje) (auxVuelo.recuperar(pos))).borrarPasajeAlViaje(pas); //Borra el Pasaje para liberar ese asiento

                            seElimino = ((Viaje) (auxVuelo.recuperar(pos))).reducirVendidos(-1); //Reduzco en 1 los vendidos de ese dia
                            if (seElimino) {
                                escribir("Pasaje " + pas.toString() + " borrado del sistema");
                                System.out.println("                    EXITO! El Pasaje se borró completamente del sistema");
                            } else {
                                System.out.println("                    ERROR: El Pasaje no pudo borrarse");
                            }
                        } else {
                            System.out.println("               ERROR: El Pasaje no fue borrado de la lista del Cliente");
                        }
                    }
                }

            } else {
                System.out.println("       ERROR: El Cliente " + clave.toString() + " del pasaje no existe");
            }
        } else {
            System.out.println("ERROR: El vuelo no existe.");
        }
        return seElimino;
    }

    private static boolean agregarPasaje(ArbolAVL arbVue, ArbolAVL arbCli, HashMap mapCliente, HashMap mapVuelo, String codVuelo, String fecha, int posAsiento, String tipoDNI, String numDNI) {
        boolean todoCheck = false;
        if (arbVue.pertenece(codVuelo)) {//Se verifica que ese vuelo exista en el arbol de vuelos, tambien que el cliente exista
//            System.out.println("El vuelo " + codVuelo + " existe. Buscando el cliente que compro el pasaje...");
            ClaveCliente clave = new ClaveCliente(tipoDNI, numDNI);
            boolean hayVuelo = mapVuelo.get(codVuelo) != null;
            if (arbCli.pertenece(clave) && hayVuelo) {
//                System.out.println("       El Cliente tambien existe. Buscando si hay lugar en el avion para esa fecha...");
                Pasaje pas = new Pasaje(codVuelo, fecha, posAsiento);
                Lista lista = (Lista) mapVuelo.get(codVuelo);
                int posicion = lista.localizar(fecha); //Fecha formato yyyymmdd
                if (posicion != -1) { //Si ya existia un Viaje con esa fecha, agregamos mas asientos.
                    Viaje aux1 = (Viaje) lista.recuperar(posicion);
                    if (aux1.tieneLugar()) {
                        if (aux1.checkAsientoDisponible(codVuelo, posAsiento)) {
                            aux1.aumentarVendidos(1); //Se agrega un pasaje vendido para el viaje de dicha Fecha

                            aux1.agregarPasajeAlViaje(pas); //@@@@@@PARA AGREGAR PASAJE A LA LISTA DE UN VIAJE@@@@@@

                            todoCheck = true;
                        } else {
                            System.out.println("     ERROR: El asiento " + posAsiento + " esta ocupado");
                        }
                    } else {
                        System.out.println("               ERROR: El avion no tiene mas lugar para esta fecha");
                    }
                } else {
                    //El viaje con esta fecha no existia, lo creamos.
                    Viaje viaje = new Viaje(fecha);
                    viaje.aumentarVendidos(1);//Se agrega un pasaje vendido para el viaje de dicha Fecha

                    viaje.agregarPasajeAlViaje(pas);//@@@@@@PARA AGREGAR PASAJE A LA LISTA DE UN VIAJE@@@@@@

                    lista.insertar(viaje, lista.longitud() + 1);
                    todoCheck = true;
                }
//                mapVuelo.put(codVuelo, lista); //Para agregar un viaje mas a la lista de viajes de un Vuelo
                if (todoCheck) { //Si el Vuelo y Cliente existen y hay lugar en el avion...
                    Lista aux = (Lista) mapCliente.get(clave);
                    aux.insertar(pas, aux.longitud() + 1);
                    mapCliente.put(clave, aux);//Para agregar un pasaje a la lista de pasajes de un Cliente
                    escribir("Pasaje " + pas.toString() + " agregado al sistema");
//                    System.out.println("               -----Pasaje para el cliente " + clave.toString() + " se agrego con EXITO!-----");
                }
            } else {
                System.out.println("       ERROR: El Cliente " + clave.toString() + " del pasaje no existe");
            }
        } else {
            System.out.println("ERROR: El Vuelo no existe");
        }
        return todoCheck;
    }
}
