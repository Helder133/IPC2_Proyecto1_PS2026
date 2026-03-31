import { Component, inject, OnInit, signal } from '@angular/core';
import { UsuarioService } from '../../../services/usuario/usuario.service';
import { ActivatedRoute, Router } from '@angular/router';
import { UsuarioUpdate } from '../../../models/usuario/UsuarioUpdate';
import { UsuarioResponse } from '../../../models/usuario/UsuarioResponse';
import { UsuarioRequest } from '../../../models/usuario/UsuarioRequest';
import { UsuarioFormComponent } from "../../../components/usuario-form/usuario-form.component";

@Component({
  selector: 'app-update-usuario-page.component',
  imports: [UsuarioFormComponent],
  templateUrl: './update-usuario-page.component.html'
})
export class UpdateUsuarioPageComponent implements OnInit {
  
  private usuario_id = signal<number>(null!);
  usuarioOriginal = signal<UsuarioResponse>(null!);

  private usuarioService = inject(UsuarioService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);

  mensajeError = signal<string>('');

  ngOnInit(): void {
    this.usuario_id.set(this.route.snapshot.params['id']);
    this.usuarioService.getUsuarioById(this.usuario_id()).subscribe({
      next: (usuario) => {
        this.usuarioOriginal.set(usuario);
      },
      error: (error) => {
        this.router.navigate(['/usuario']);
      }
    });
  }

  actualizarUsuario(usuarioRequest: UsuarioRequest): void {
    if (this.usuario_id() === null) return;

    const usuarioUpdate: UsuarioUpdate = {
      ...usuarioRequest,
      usuario_id: this.usuario_id()
    }

    this.usuarioService.updateUsuario(usuarioUpdate).subscribe({
      next: () => {
        this.router.navigate(['/usuario']);
      },
      error: (error) => {
        this.mensajeError.set(error.error?.error || "Error de conexión con el servidor.");
      }
    });
  }

}
