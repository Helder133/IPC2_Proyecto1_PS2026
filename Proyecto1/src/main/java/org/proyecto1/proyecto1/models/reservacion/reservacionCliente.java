package org.proyecto1.proyecto1.models.reservacion;

public class reservacionCliente {
    private int reservacionId;
    private int clienteId;

    public reservacionCliente(int reservacionId, int clienteId) {
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
}
