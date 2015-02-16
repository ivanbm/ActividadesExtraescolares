package com.izv.android.actividadesextraescolares;

/**
 * Created by Ivan on 14/12/2014.
 */
public class ActividadExtra implements Comparable<ActividadExtra> {

    private String titulo, descripcion, categoria, fecha, inscripciones, direccion;

    public ActividadExtra(String titulo, String descripcion, String categoria, String fecha, String inscripciones, String direccion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.fecha = fecha;
        this.inscripciones = inscripciones;
        this.direccion = direccion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getInscripciones() {
        return inscripciones;
    }

    public void setInscripciones(String inscripciones) {
        this.inscripciones = inscripciones;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActividadExtra)) return false;

        ActividadExtra that = (ActividadExtra) o;

        if (categoria != null ? !categoria.equals(that.categoria) : that.categoria != null)
            return false;
        if (descripcion != null ? !descripcion.equals(that.descripcion) : that.descripcion != null)
            return false;
        if (direccion != null ? !direccion.equals(that.direccion) : that.direccion != null)
            return false;
        if (fecha != null ? !fecha.equals(that.fecha) : that.fecha != null) return false;
        if (inscripciones != null ? !inscripciones.equals(that.inscripciones) : that.inscripciones != null)
            return false;
        if (titulo != null ? !titulo.equals(that.titulo) : that.titulo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = titulo != null ? titulo.hashCode() : 0;
        result = 31 * result + (descripcion != null ? descripcion.hashCode() : 0);
        result = 31 * result + (categoria != null ? categoria.hashCode() : 0);
        result = 31 * result + (fecha != null ? fecha.hashCode() : 0);
        result = 31 * result + (inscripciones != null ? inscripciones.hashCode() : 0);
        result = 31 * result + (direccion != null ? direccion.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(ActividadExtra actividadExtra) {
        return 0;
    }
}