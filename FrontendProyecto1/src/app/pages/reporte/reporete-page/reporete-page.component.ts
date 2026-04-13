import { CommonModule } from '@angular/common';
import { Component, inject, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ReporteService } from '../../../services/reporte/reporte.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-reporete-page.component',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './reporete-page.component.html'
})
export class ReporetePageComponent {
  private fb = inject(FormBuilder);
  private reporteService = inject(ReporteService);

  fechasForm: FormGroup = this.fb.group({
    fechaInicio: ['', Validators.required],
    fechaFin: ['', Validators.required]
  });

  cargando = signal<boolean>(false);
  mensajeError = signal<string>('');

  generarReporte(tipo: string): void {
    if (this.fechasForm.invalid) {
      this.mensajeError.set('Selecciona ambas fechas primero.');
      return;
    }
    this.cargando.set(true);
    const { fechaInicio, fechaFin } = this.fechasForm.value;
    const infoFechas = { inicio: fechaInicio, fin: fechaFin };

    let peticion$: Observable<any> | undefined;
    switch (tipo) {
      case 'ventas': peticion$ = this.reporteService.getVentas(fechaInicio, fechaFin); break;
      case 'cancelaciones': peticion$ = this.reporteService.getCancelaciones(fechaInicio, fechaFin); break;
      case 'ganancias': peticion$ = this.reporteService.getGanancias(fechaInicio, fechaFin); break;
      case 'agente_ventas': peticion$ = this.reporteService.getMejorAgente(fechaInicio, fechaFin); break;
      case 'agente_ganancias': peticion$ = this.reporteService.getAgenteMasGanancias(fechaInicio, fechaFin); break;
      case 'paquete_mas': peticion$ = this.reporteService.getPaqueteMasVendido(fechaInicio, fechaFin); break;
      case 'paquete_menos': peticion$ = this.reporteService.getPaqueteMenosVendido(fechaInicio, fechaFin); break;
      case 'destinos': peticion$ = this.reporteService.getDestinos(fechaInicio, fechaFin); break;
    }

    peticion$?.subscribe({
      next: (data: any) => {
        if (!data || (Array.isArray(data) && data.length === 0)) {
            this.mensajeError.set('No hay datos en este rango de fechas.');
            setTimeout(() => this.mensajeError.set(''), 3000);
            this.cargando.set(false);
            return;
        }
        localStorage.setItem('rep_tipo', tipo);
        localStorage.setItem('rep_data', JSON.stringify(data));
        localStorage.setItem('rep_fechas', JSON.stringify(infoFechas));
        this.cargando.set(false);
        window.open('/reporte/imprimir-reporte', '_blank');
      },
      error: () => {
        this.mensajeError.set('Error al conectar con la base de datos.');
        this.cargando.set(false);
      }
    });
  }
}
