package com.mycompany.agenciadetectives.modelo;

public class Pista {

    private final String codigo;
    private String descripcion;
    private String tipoEvidencia;
    private int nivelImportancia;
    private int nivelConfiabilidad;

    public Pista(String codigo, String descripcion, String tipoEvidencia,
                 int nivelImportancia, int nivelConfiabilidad) {

        // Validar todos los datos antes de asignarlos.
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El código de la pista no puede estar vacío.");
        }

        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "La descripción de la pista no puede estar vacía.");
        }

        if (tipoEvidencia == null || tipoEvidencia.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El tipo de evidencia no puede estar vacío.");
        }

        if (nivelImportancia < 1 || nivelImportancia > 10) {
            throw new IllegalArgumentException(
                    "El nivel de importancia debe estar entre 1 y 10.");
        }

        if (nivelConfiabilidad < 0 || nivelConfiabilidad > 100) {
            throw new IllegalArgumentException(
                    "El nivel de confiabilidad debe estar entre 0 y 100.");
        }

        this.codigo = codigo.trim();
        this.descripcion = descripcion.trim();
        this.tipoEvidencia = tipoEvidencia.trim();
        this.nivelImportancia = nivelImportancia;
        this.nivelConfiabilidad = nivelConfiabilidad;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getTipoEvidencia() {
        return tipoEvidencia;
    }

    public int getNivelImportancia() {
        return nivelImportancia;
    }

    public int getNivelConfiabilidad() {
        return nivelConfiabilidad;
    }

    public void actualizar(String descripcion, String tipoEvidencia,
                           int nivelImportancia, int nivelConfiabilidad) {

        // Validar todos los nuevos valores antes de modificar la pista.
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "La descripción de la pista no puede estar vacía.");
        }

        if (tipoEvidencia == null || tipoEvidencia.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El tipo de evidencia no puede estar vacío.");
        }

        if (nivelImportancia < 1 || nivelImportancia > 10) {
            throw new IllegalArgumentException(
                    "El nivel de importancia debe estar entre 1 y 10.");
        }

        if (nivelConfiabilidad < 0 || nivelConfiabilidad > 100) {
            throw new IllegalArgumentException(
                    "El nivel de confiabilidad debe estar entre 0 y 100.");
        }

        this.descripcion = descripcion.trim();
        this.tipoEvidencia = tipoEvidencia.trim();
        this.nivelImportancia = nivelImportancia;
        this.nivelConfiabilidad = nivelConfiabilidad;
    }

    @Override
    public String toString() {
        return "Código: " + codigo
                + "\nDescripción: " + descripcion
                + "\nTipo de evidencia: " + tipoEvidencia
                + "\nNivel de importancia: " + nivelImportancia
                + "\nNivel de confiabilidad: " + nivelConfiabilidad + "%";
    }
}