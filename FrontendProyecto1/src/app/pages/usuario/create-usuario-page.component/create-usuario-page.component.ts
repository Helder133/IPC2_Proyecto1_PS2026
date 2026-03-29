import { Component, inject, signal } from '@angular/core';
import { UsuarioService } from '../../../services/usuario/usuario.service';
import { Router } from '@angular/router';
import { UsuarioRequest } from '../../../models/usuario/UsuarioRequest';
import { UsuarioFormComponent } from "../../../components/usuario-form/usuario-form.component";

@Component({
  selector: 'app-create-usuario-page',
  imports: [UsuarioFormComponent],
  templateUrl: './create-usuario-page.component.html'
})
export class CreateUsuarioPageComponent {
  mensajeError = signal<string>('');
  private usuarioService = inject(UsuarioService);
  private router = inject(Router);

  crearUsuario(usuarioRequest: UsuarioRequest): void {
    this.usuarioService.createUsuario(usuarioRequest).subscribe({
      next: () => {
        this.router.navigate(['/usuario']);
      },
      error: (error) => {
        this.mensajeError.set(error.error?.error || "Error de conexión con el servidor.");
      }
    });
  }
}
