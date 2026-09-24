package com.mycompany.agenciadetectives.modelo;

import java.util.ArrayList;

public class Caso {

    private static final int MAX_UBICACIONES = 5;

    private final String nombre;
    private final String codigo;
    private final String detectiveResponsable;
    private final Ubicacion[] ubicaciones;
    private final ArrayList<Pista> pistas;

    public Caso(String nombre, String codigo, String detectiveResponsable) {

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El nombre del caso no puede estar vacío.");
        }

        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El código del caso no puede estar vacío.");
        }

        if (detectiveResponsable == null
                || detectiveResponsable.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El nombre del detective no puede estar vacío.");
        }

        this.nombre = nombre.trim();
        this.codigo = codigo.trim();
        this.detectiveResponsable = detectiveResponsable.trim();

        // Las cinco posiciones comienzan con null.
        this.ubicaciones = new Ubicacion[MAX_UBICACIONES];

        // El caso comienza sin pistas registradas.
        this.pistas = new ArrayList<>();
    }

    // Operaciones sobre las ubicaciones.

    public Ubicacion[] getUbicaciones() {
        /* clone se hace para devolver una copia de ubicaciones esto con el fin
        de que no se pueda modificar el array original.
        */
        return ubicaciones.clone();
    }

    public void registrarUbicacion(int posicion, Ubicacion ubicacion) {
        validarPosicion(posicion);

        if (ubicacion == null) {
            throw new IllegalArgumentException(
                    "La ubicación que se desea registrar no puede ser null.");
        }

        if (ubicaciones[posicion] != null) {
            throw new IllegalStateException(
                    "La posición seleccionada ya tiene una ubicación.");
        }

        ubicaciones[posicion] = ubicacion;
    }

    public Ubicacion consultarUbicacion(int posicion) {
        validarPosicion(posicion);
        return ubicaciones[posicion];
    }

    public void modificarUbicacion(int posicion, int nivelRiesgo,
                                   String estado) {

        Ubicacion ubicacion = consultarUbicacion(posicion);

        if (ubicacion == null) {
            throw new IllegalStateException(
                    "No hay una ubicación registrada en esa posición.");
        }

        ubicacion.actualizar(nivelRiesgo, estado);
    }

    public boolean descartarUbicacion(int posicion) {
        validarPosicion(posicion);

        if (ubicaciones[posicion] == null) {
            return false;
        }

        ubicaciones[posicion] = null;
        return true;
    }

    public int contarUbicaciones() {
        int cantidad = 0;

        for (int i = 0; i < ubicaciones.length; i++) {
            if (ubicaciones[i] != null) {
                cantidad++;
            }
        }

        return cantidad;
    }

    public int contarEspaciosDisponibles() {
        return MAX_UBICACIONES - contarUbicaciones();
    }

    public Ubicacion obtenerUbicacionMayorRiesgo() {
        Ubicacion mayorRiesgo = null;

        for (int i = 0; i < ubicaciones.length; i++) {
            Ubicacion actual = ubicaciones[i];

            if (actual != null) {
                if (mayorRiesgo == null
                        || actual.getNivelRiesgo()
                        > mayorRiesgo.getNivelRiesgo()) {

                    mayorRiesgo = actual;
                }
            }
        }

        return mayorRiesgo;
    }

    // Operaciones sobre las pistas.

    public ArrayList<Pista> getPistas() {
        return new ArrayList<>(pistas);
    }

    public void registrarPista(Pista pista) {

        if (pista == null) {
            throw new IllegalArgumentException(
                    "La pista que se desea registrar no puede ser null.");
        }

        if (buscarIndicePista(pista.getCodigo()) != -1) {
            throw new IllegalArgumentException(
                    "Ya existe una pista con el código "
                    + pista.getCodigo() + ".");
        }

        pistas.add(pista);
    }

    public Pista buscarPista(String codigo) {
        int indice = buscarIndicePista(codigo);

        if (indice == -1) {
            return null;
        }

        return pistas.get(indice);
    }

    public boolean modificarPista(String codigo, String descripcion,
                                 String tipoEvidencia, int nivelImportancia,
                                 int nivelConfiabilidad) {

        int indice = buscarIndicePista(codigo);

        if (indice == -1) {
            return false;
        }

        Pista pista = pistas.get(indice);

        pista.actualizar(
                descripcion,
                tipoEvidencia,
                nivelImportancia,
                nivelConfiabilidad
        );

        return true;
    }

    public boolean eliminarPista(String codigo) {
        int indice = buscarIndicePista(codigo);

        if (indice == -1) {
            return false;
        }

        pistas.remove(indice);
        return true;
    }

    public int contarPistas() {
        return pistas.size();
    }

    public Pista obtenerPistaMayorImportancia() {

        if (pistas.isEmpty()) {
            return null;
        }

        Pista mayorImportancia = pistas.get(0);

        for (int i = 1; i < pistas.size(); i++) {
            Pista actual = pistas.get(i);

            if (actual.getNivelImportancia()
                    > mayorImportancia.getNivelImportancia()) {

                mayorImportancia = actual;
            }
        }

        return mayorImportancia;
    }

    public Pista obtenerPistaMayorConfiabilidad() {

        if (pistas.isEmpty()) {
            return null;
        }

        Pista mayorConfiabilidad = pistas.get(0);

        for (int i = 1; i < pistas.size(); i++) {
            Pista actual = pistas.get(i);

            if (actual.getNivelConfiabilidad()
                    > mayorConfiabilidad.getNivelConfiabilidad()) {

                mayorConfiabilidad = actual;
            }
        }

        return mayorConfiabilidad;
    }

    public double calcularPromedioImportancia() {

        if (pistas.isEmpty()) {
            throw new IllegalStateException(
                    "No se puede calcular el promedio porque no hay pistas.");
        }

        double suma = 0.0;

        for (int i = 0; i < pistas.size(); i++) {
            suma += pistas.get(i).getNivelImportancia();
        }

        return suma / pistas.size();
    }

    // Métodos internos.

    private void validarPosicion(int posicion) {

        if (posicion < 0 || posicion >= ubicaciones.length) {
            throw new IllegalArgumentException(
                    "La posición debe estar entre 0 y "
                    + (ubicaciones.length - 1) + ".");
        }
    }

    private int buscarIndicePista(String codigo) {

        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El código de la pista no puede estar vacío.");
        }

        String codigoBuscado = codigo.trim();

        for (int i = 0; i < pistas.size(); i++) {
            Pista pista = pistas.get(i);

            if (pista.getCodigo().equalsIgnoreCase(codigoBuscado)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public String toString() {
        return "Nombre del caso: " + nombre
                + "\nCódigo del caso: " + codigo
                + "\nDetective responsable: " + detectiveResponsable;
    }
}