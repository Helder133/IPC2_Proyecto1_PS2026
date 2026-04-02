package org.proyecto1.proyecto1.models.reservacion;

public class ReservacionCliente {
    private int reservacionId;
    private int clienteId;

    public ReservacionCliente(int reservacionId, int clienteId) {
        this.reservacionId = reservacionId;
        this.clienteId = clienteId;
    }

    public int getReservacionId() {
        return reservacionId;
    }

    public void setReservacionId(int reservacionId) {
        this.reservacionId = reservacionId;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public boolean isValid() {
        return reservacionId > 0 && clienteId > 0;
    }

}
