package com.mycompany.agenciadetectives.modelo;

public class Ubicacion {

    private final String codigo;
    private final String nombre;
    private final String direccion;
    private int nivelRiesgo;
    private String estado;

    public Ubicacion(String codigo, String nombre, String direccion,
                     int nivelRiesgo, String estado) {

        // Validar todos los datos antes de asignarlos.
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El codigo de la ubicacion no puede estar vacio.");
        }

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El nombre de la ubicacion no puede estar vacio.");
        }

        if (direccion == null || direccion.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "La direccion o descripcion del lugar no puede estar vacia.");
        }

        if (nivelRiesgo < 1 || nivelRiesgo > 10) {
            throw new IllegalArgumentException(
                    "El nivel de riesgo debe estar entre 1 y 10.");
        }

        if (estado == null || estado.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El estado de la ubicacion no puede estar vacio.");
        }

        this.codigo = codigo.trim();
        this.nombre = nombre.trim();
        this.direccion = direccion.trim();
        this.nivelRiesgo = nivelRiesgo;
        this.estado = estado.trim();
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public int getNivelRiesgo() {
        return nivelRiesgo;
    }

    public String getEstado() {
        return estado;
    }

    public void actualizar(int nivelRiesgo, String estado) {

        // Ambos valores deben ser válidos antes de modificar el objeto.
        if (nivelRiesgo < 1 || nivelRiesgo > 10) {
            throw new IllegalArgumentException(
                    "El nivel de riesgo debe estar entre 1 y 10.");
        }

        if (estado == null || estado.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El estado de la ubicacion no puede estar vacio.");
        }

        this.nivelRiesgo = nivelRiesgo;
        this.estado = estado.trim();
    }

    @Override
    public String toString() {
        return "Codigo: " + codigo
                + "\nNombre: " + nombre
                + "\nDirección o descripcion: " + direccion
                + "\nNivel de riesgo: " + nivelRiesgo
                + "\nEstado: " + estado;
    }
}
