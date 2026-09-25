package com.mycompany.agenciadetectives;

import com.mycompany.agenciadetectives.modelo.Caso;
import com.mycompany.agenciadetectives.modelo.Pista;
import com.mycompany.agenciadetectives.modelo.Ubicacion;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class AgenciaDetectives {

    private final Scanner scanner;
    private Caso casoActual;

    public AgenciaDetectives() {
        scanner = new Scanner(System.in);
        casoActual = null;
    }

    public static void main(String[] args) {
        AgenciaDetectives programa = new AgenciaDetectives();
        programa.ejecutar();
    }

    public void ejecutar() {
        try {
            System.out.println("=== AGENCIA DE DETECTIVES ===");
            System.out.println("Registra los datos del caso inicial.");

            casoActual = solicitarCaso();

            int opcion = 0;

            do {
                mostrarMenu();

                try {
                    opcion = leerEntero("Selecciona una opcion: ");
                    ejecutarOpcion(opcion);

                } catch (IllegalArgumentException | IllegalStateException e) {
                    System.out.println("\nNo se pudo completar la operación.");
                    System.out.println("Motivo: " + e.getMessage());
                }

            } while (opcion != 13);

        } catch (NoSuchElementException e) {
            System.out.println(
                    "\nLa entrada se ha cerrado. El programa finalizara.");

        } finally {
            // Se cierra el lector cuando termina la sesión completa.
            scanner.close();
        }
    }

    private void mostrarMenu() {
        System.out.println("\n========== MENU PRINCIPAL ==========");
        System.out.println("1. Nuevo caso");
        System.out.println("2. Registrar ubicacion");
        System.out.println("3. Consultar ubicaciones");
        System.out.println("4. Consultar una ubicacion");
        System.out.println("5. Modificar ubicacion");
        System.out.println("6. Descartar ubicacion");
        System.out.println("7. Registrar pista");
        System.out.println("8. Consultar pistas");
        System.out.println("9. Buscar pista");
        System.out.println("10. Modificar pista");
        System.out.println("11. Eliminar pista");
        System.out.println("12. Mostrar reporte de investigacion");
        System.out.println("13. Salir");
        System.out.println("====================================");
    }

    private void ejecutarOpcion(int opcion) {

        switch (opcion) {

            case 1: {
                System.out.println("\n--- NUEVO CASO ---");

                // Solo se reemplaza el caso cuando el nuevo es válido.
                casoActual = solicitarCaso();

                System.out.println(
                        "Nuevo caso creado sin ubicaciones ni pistas.");
                break;
            }

            case 2: {
                System.out.println("\n--- REGISTRAR UBICACION ---");

                int posicion = leerEnteroEnRango("Posicion del arreglo (0 a 4): ", 0, 4);

                if (casoActual.consultarUbicacion(posicion) != null) {
                    throw new IllegalStateException(
                            "La posición seleccionada ya está ocupada.");
                }

                String codigo = leerTexto("Codigo: ");
                String nombre = leerTexto("Nombre del lugar: ");
                String direccion = leerTexto("Direccion o descripcion: ");
                int riesgo = leerEnteroEnRango("Nivel de riesgo (1 a 10): ", 1,10);
                String estado = leerTexto("Estado: ");

                Ubicacion ubicacion = new Ubicacion(
                        codigo, nombre, direccion, riesgo, estado
                );

                casoActual.registrarUbicacion(posicion, ubicacion);

                System.out.println(
                        "Ubicacion registrada en la posición " + posicion + ".");
                break;
            }

            case 3: {
                System.out.println("\n--- UBICACIONES REGISTRADAS ---");

                if (casoActual.contarUbicaciones() == 0) {
                    System.out.println("Todavia no hay ubicaciones registradas.");
                    break;
                }

                Ubicacion[] ubicaciones = casoActual.getUbicaciones();

                for (int i = 0; i < ubicaciones.length; i++) {
                    if (ubicaciones[i] != null) {
                        System.out.println("\nPosicion del arreglo: " + i);
                        System.out.println(ubicaciones[i]);
                    }
                }

                break;
            }

            case 4: {
                System.out.println("\n--- CONSULTAR UNA UBICACION ---");

                int posicion = leerEnteroEnRango("Posicion del arreglo (0 a 4): ",0,4);
                Ubicacion ubicacion = casoActual.consultarUbicacion(posicion);

                if (ubicacion == null) {
                    System.out.println(
                            "La posicion " + posicion + " esta vacia.");
                } else {
                    System.out.println("\nPosición del arreglo: " + posicion);
                    System.out.println(ubicacion);
                }

                break;
            }

            case 5: {
                System.out.println("\n--- MODIFICAR UBICACION ---");

                int posicion = leerEnteroEnRango("Posicion del arreglo (0 a 4): ",0,4);
                Ubicacion ubicacion = casoActual.consultarUbicacion(posicion);

                if (ubicacion == null) {
                    throw new IllegalStateException(
                            "No hay una ubicacion registrada en esa posicion.");
                }

                System.out.println("\nInformacion actual:");
                System.out.println(ubicacion);

                int riesgo = leerEnteroEnRango("\nNuevo nivel de riesgo (1 a 10): ",1,10);
                String estado = leerTexto("Nuevo estado: ");

                casoActual.modificarUbicacion(posicion, riesgo, estado);

                System.out.println("Ubicacion modificada correctamente.");
                break;
            }

            case 6: {
                System.out.println("\n--- DESCARTAR UBICACION ---");

                int posicion = leerEnteroEnRango("Posicion del arreglo (0 a 4): ",0,4);

                if (casoActual.descartarUbicacion(posicion)) {
                    System.out.println(
                            "Ubicacion descartada. La posicion quedo disponible.");
                } else {
                    System.out.println(
                            "La posicion indicada ya estaba vacia.");
                }

                break;
            }

            case 7: {
                System.out.println("\n--- REGISTRAR PISTA ---");

                String codigo = leerTexto("Codigo: ");

                if (casoActual.buscarPista(codigo) != null) {
                    throw new IllegalArgumentException(
                            "Ya existe una pista con ese codigo.");
                }

                String descripcion = leerTexto("Descripcion: ");
                String tipo = leerTexto("Tipo de evidencia: ");
                int importancia = leerEnteroEnRango("Nivel de importancia (1 a 10): ",1,10);
                int confiabilidad = leerEnteroEnRango(
                        "Nivel de confiabilidad (0 a 100): ",0,100);

                Pista pista = new Pista(
                        codigo, descripcion, tipo, importancia, confiabilidad
                );

                casoActual.registrarPista(pista);

                System.out.println("Pista registrada correctamente.");
                break;
            }

            case 8: {
                System.out.println("\n--- PISTAS REGISTRADAS ---");

                ArrayList<Pista> pistas = casoActual.getPistas();

                if (pistas.isEmpty()) {
                    System.out.println("Todavia no hay pistas registradas.");
                    break;
                }

                for (int i = 0; i < pistas.size(); i++) {
                    System.out.println("\nPista " + (i + 1));
                    System.out.println(pistas.get(i));
                }

                break;
            }

            case 9: {
                System.out.println("\n--- BUSCAR PISTA ---");

                String codigo = leerTexto("Codigo de la pista: ");
                Pista pista = casoActual.buscarPista(codigo);

                if (pista == null) {
                    System.out.println(
                            "No se encontro una pista con ese codigo.");
                } else {
                    System.out.println("\nPista encontrada:");
                    System.out.println(pista);
                }

                break;
            }

            case 10: {
                System.out.println("\n--- MODIFICAR PISTA ---");

                String codigo = leerTexto("Codigo de la pista: ");
                Pista pista = casoActual.buscarPista(codigo);

                if (pista == null) {
                    System.out.println(
                            "No se encontro una pista con ese codigo.");
                    break;
                }

                System.out.println("\nInformacion actual:");
                System.out.println(pista);

                String descripcion = leerTexto("\nNueva descripcion: ");
                String tipo = leerTexto("Nuevo tipo de evidencia: ");
                int importancia = leerEnteroEnRango(
                        "Nuevo nivel de importancia (1 a 10): ",1,10);
                int confiabilidad = leerEnteroEnRango(
                        "Nuevo nivel de confiabilidad (0 a 100): ",0,100);

                boolean modificada = casoActual.modificarPista(
                        codigo, descripcion, tipo, importancia, confiabilidad
                );

                if (modificada) {
                    System.out.println("Pista modificada correctamente.");
                } else {
                    System.out.println(
                            "No se encontro la pista que se quería modificar.");
                }

                break;
            }

            case 11: {
                System.out.println("\n--- ELIMINAR PISTA ---");

                String codigo = leerTexto("Codigo de la pista: ");

                if (casoActual.eliminarPista(codigo)) {
                    System.out.println("Pista eliminada correctamente.");
                } else {
                    System.out.println(
                            "No se encontro una pista con ese código.");
                }

                break;
            }

            case 12: {
                mostrarReporte();
                break;
            }

            case 13: {
                System.out.println("\nPrograma finalizado. Hasta luego.");
                break;
            }

            default: {
                throw new IllegalArgumentException(
                        "La opcion del menu debe estar entre 1 y 13.");
            }
        }
    }

    private Caso solicitarCaso() {

        while (true) {
            try {
                String nombre = leerTexto("Nombre del caso: ");
                String codigo = leerTexto("Codigo del caso: ");
                String detective = leerTexto("Detective responsable: ");

                return new Caso(nombre, codigo, detective);

            } catch (IllegalArgumentException e) {
                System.out.println(
                        "No se pudo crear el caso: " + e.getMessage());
                System.out.println("Ingresa nuevamente los datos.");
            }
        }
    }

    private int leerEntero(String mensaje) {

        while (true) {
            System.out.print(mensaje);

            try {
                int numero = scanner.nextInt();

                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                }

                return numero;

            } catch (InputMismatchException e) {
                System.out.println(
                        "Entrada invalida. Debes ingresar un numero entero.");

                // Eliminar la entrada incorrecta para poder reintentar.
                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                }
            }
        }
    }

    private int leerEnteroEnRango(String mensaje, int min, int max) {

        while (true) {
            int numero = leerEntero(mensaje);

            if (numero < min || numero > max) {
                System.out.println(
                        "El valor debe estar entre " + min + " y " + max + ". Intenta de nuevo.");
                continue;
            }

            return numero;
        }
    }

    private String leerTexto(String mensaje) {

        while (true) {
            System.out.print(mensaje);

            String texto = scanner.nextLine().trim();

            if (!texto.isEmpty()) {
                return texto;
            }

            System.out.println("Este dato no puede estar vacio.");
        }
    }

    private void mostrarReporte() {
        System.out.println("\n========== REPORTE DE INVESTIGACION ==========");
        System.out.println(casoActual);

        System.out.println(
                "\nCantidad de ubicaciones registradas: "
                + casoActual.contarUbicaciones());

        System.out.println(
                "Espacios disponibles para ubicaciones: "
                + casoActual.contarEspaciosDisponibles());

        Ubicacion mayorRiesgo = casoActual.obtenerUbicacionMayorRiesgo();

        if (mayorRiesgo == null) {
            System.out.println("Ubicación con mayor riesgo: sin datos.");
        } else {
            System.out.println(
                    "ubicacion con mayor riesgo: "
                    + mayorRiesgo.getNombre()
                    + " [" + mayorRiesgo.getCodigo() + "]"
                    + " - Riesgo: " + mayorRiesgo.getNivelRiesgo());
        }

        int cantidadPistas = casoActual.contarPistas();

        System.out.println(
                "\nCantidad de pistas registradas: " + cantidadPistas);

        if (cantidadPistas == 0) {
            System.out.println("Pista con mayor importancia: sin datos.");
            System.out.println("Pista con mayor confiabilidad: sin datos.");
            System.out.println("Promedio de importancia: sin datos.");
            return;
        }

        Pista mayorImportancia = casoActual.obtenerPistaMayorImportancia();
        Pista mayorConfiabilidad = casoActual.obtenerPistaMayorConfiabilidad();

        System.out.println(
                "Pista con mayor importancia: "
                + mayorImportancia.getCodigo()
                + " - " + mayorImportancia.getDescripcion()
                + " - Importancia: "
                + mayorImportancia.getNivelImportancia());

        System.out.println(
                "Pista con mayor confiabilidad: "
                + mayorConfiabilidad.getCodigo()
                + " - " + mayorConfiabilidad.getDescripcion()
                + " - Confiabilidad: "
                + mayorConfiabilidad.getNivelConfiabilidad() + "%");

        System.out.printf(
                "Promedio de importancia: %.2f%n",
                casoActual.calcularPromedioImportancia());
    }
}
