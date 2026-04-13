package org.proyecto1.proyecto1.dtos.reservacion;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import org.proyecto1.proyecto1.dtos.paqueteTuristico.PaqueteTuristicoResponse;
import org.proyecto1.proyecto1.models.reservacion.EnumReservacion;
import org.proyecto1.proyecto1.models.reservacion.Reservacion;

import java.time.LocalDate;

public class ReservacionResponse {
    private int reservacionId;
    private int paqueteId;
    private int usuarioId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate fechaViaje;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate fechaCreacion;
    private int cantidadPersona;
    private double costoTotal;
    private double costoAgencia;
    private EnumReservacion estado;
    private double reembolso;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate fechaCancelacion;
    private String codigoArchivo;
    private double totalPagoRealizado;
    private PaqueteTuristicoResponse paqueteTuristico;

    public ReservacionResponse(Reservacion  reservacion) {
        this.reservacionId = reservacion.getReservacionId();
        this.paqueteId = reservacion.getPaqueteId();
        this.usuarioId = reservacion.getUsuarioId();
        this.fechaViaje = reservacion.getFechaViaje();
        this.fechaCreacion = reservacion.getFechaCreacion();
        this.cantidadPersona = reservacion.getCantidadPersona();
        this.costoTotal = reservacion.getCostoTotal();
        this.costoAgencia = reservacion.getCostoAgencia();
        this.estado = reservacion.getEstado();
        this.reembolso = reservacion.getReembolso();
        this.fechaCancelacion = reservacion.getFechaCancelacion();
        this.codigoArchivo = reservacion.getCodigoArchivo();
        this.totalPagoRealizado = reservacion.getTotalPagoRealizado();
        this.paqueteTuristico = new PaqueteTuristicoResponse(reservacion.getPaqueteTuristico());
    }

    public int getReservacionId() {
        return reservacionId;
    }

    public void setReservacionId(int reservacionId) {
        this.reservacionId = reservacionId;
    }

    public int getPaqueteId() {
        return paqueteId;
    }

    public void setPaqueteId(int paqueteId) {
        this.paqueteId = paqueteId;
    }

    public LocalDate getFechaViaje() {
        return fechaViaje;
    }

    public void setFechaViaje(LocalDate fechaViaje) {
        this.fechaViaje = fechaViaje;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public int getCantidadPersona() {
        return cantidadPersona;
    }

    public void setCantidadPersona(int cantidadPersona) {
        this.cantidadPersona = cantidadPersona;
    }

    public double getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(double costoTotal) {
        this.costoTotal = costoTotal;
    }

    public double getCostoAgencia() {
        return costoAgencia;
    }

    public void setCostoAgencia(double costoAgencia) {
        this.costoAgencia = costoAgencia;
    }

    public EnumReservacion getEstado() {
        return estado;
    }

    public void setEstado(EnumReservacion estado) {
        this.estado = estado;
    }

    public double getReembolso() {
        return reembolso;
    }

    public void setReembolso(double reembolso) {
        this.reembolso = reembolso;
    }

    public LocalDate getFechaCancelacion() {
        return fechaCancelacion;
    }

    public void setFechaCancelacion(LocalDate fechaCancelacion) {
        this.fechaCancelacion = fechaCancelacion;
    }

    public String getCodigoArchivo() {
        return codigoArchivo;
    }

    public void setCodigoArchivo(String codigoArchivo) {
        this.codigoArchivo = codigoArchivo;
    }

    public double getTotalPagoRealizado() {
        return totalPagoRealizado;
    }

    public void setTotalPagoRealizado(double totalPagoRealizado) {
        this.totalPagoRealizado = totalPagoRealizado;
    }

    public PaqueteTuristicoResponse getPaqueteTuristico() {
        return paqueteTuristico;
    }

    public void setPaqueteTuristico(PaqueteTuristicoResponse paqueteTuristico) {
        this.paqueteTuristico = paqueteTuristico;
    }
}
