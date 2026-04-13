import { Component, OnInit, signal } from '@angular/core';
import { HistorialPagoResponse } from '../../../models/pago/HistorialPagoResponse';
import { ReservacionResponse } from '../../../models/reservacion/ReservacionResponse';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-recibo-pago-page.component',
  imports: [CommonModule],
  templateUrl: './recibo-pago-page.component.html',
  styleUrl: './recibo-pago-page.component.css',
})
export class ReciboPagoPageComponent implements OnInit {
  pago = signal<HistorialPagoResponse | null>(null);
  reservacion = signal<ReservacionResponse | null>(null);
  fechaActual = new Date();

  ngOnInit(): void {
    const pagoData = localStorage.getItem('recibo_pago');
    const resData = localStorage.getItem('recibo_reservacion');
    if (pagoData && resData) {
      this.pago.set(JSON.parse(pagoData));
      this.reservacion.set(JSON.parse(resData));
      localStorage.removeItem('recibo_pago');
      localStorage.removeItem('recibo_reservacion');
      setTimeout(() => {
        window.print();
      }, 700);
    }
  }
  imprimir(): void {
    window.print();
  }

}
