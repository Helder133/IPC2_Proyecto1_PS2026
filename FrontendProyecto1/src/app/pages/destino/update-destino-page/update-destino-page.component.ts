import { Component, inject, OnInit, signal } from '@angular/core';
import { DestinoService } from '../../../services/destino/destino.service';
import { DestinoResponse } from '../../../models/destino/DestinoResponse';
import { ActivatedRoute, Router } from '@angular/router';
import { DestinoRequest } from '../../../models/destino/DestinoRequest';
import { DestinoUpdate } from '../../../models/destino/DestinoUpdate';
import { DestinoFormComponent } from "../../../components/destino/destino-form/destino-form.component";

@Component({
  selector: 'app-update-destino-page.component',
  imports: [DestinoFormComponent],
  templateUrl: './update-destino-page.component.html'
})
export class UpdateDestinoPageComponent implements OnInit {

  private destinoService = inject(DestinoService);
  private destinoId = signal<number>(null!);
  destinoOriginal = signal<DestinoResponse>(null!);
  mensajeError = signal<string>('');

  private router = inject(Router);
  private route = inject(ActivatedRoute);

  ngOnInit(): void {
    this.destinoId.set(this.route.snapshot.params['id']);
    this.destinoService.getDestino(this.destinoId()).subscribe({
      next: (destino) => {
        this.destinoOriginal.set(destino as DestinoResponse);
      },
      error: (error) => {
        this.router.navigate(['/destino']);
      }
    });
  }

  actualizarDestino(destinoRequest: DestinoRequest): void {
    if (this.destinoId() === null) return;

    const destinoUpdate: DestinoUpdate = {
      ...destinoRequest,
      destinoId: this.destinoId()
    };

    this.destinoService.actualizarDestino(destinoUpdate).subscribe({
      next: () => {
        this.router.navigate(['/destino']);
      },
      error: (error) => {
        this.mensajeError.set(error.error?.error || "Error de conexión con el servidor.");
      }
    });

  }

}
