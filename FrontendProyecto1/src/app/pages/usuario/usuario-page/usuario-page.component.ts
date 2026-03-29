import { Component, inject, OnInit, signal } from '@angular/core';
import { UsuarioResponse } from '../../../models/usuario/UsuarioResponse';
import { UsuarioService } from '../../../services/usuario/usuario.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UsuarioCardComponent } from '../../../components/usuario-card/usuario-card.component';
import { ConfirmationModalUsuarioComponent } from "../../../components/confirmation-modal/confirmation-modal-usuario/confirmation-modal-usuario.component";
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-usuario-page.component',
  imports: [CommonModule, FormsModule, UsuarioCardComponent, ConfirmationModalUsuarioComponent, RouterLink],
  templateUrl: './usuario-page.component.html'
})
export class UsuarioPageComponent implements OnInit {
  private usuarioService = inject(UsuarioService);
  usuarios = signal<UsuarioResponse[]>([]);
  concidencia = signal<string>('');
  mensajeError = signal<string>('');
  usuarioSeleccionado = signal<UsuarioResponse>(null!);
  eliminar = signal<boolean>(false);

  ngOnInit(): void {
    this.getUsuarios();
  }

  private getUsuarios(): void {
    this.usuarioService.getUsuarios().subscribe({
      next: (usuarios: UsuarioResponse[]) => {
        this.usuarios.set(this.descartarUsuarioLogeado(usuarios));
      },
      error: (error: any) => {
        this.mensajeError.set(error.error?.error || "Error de conexión con el servidor.");
      }
    });
  }

  buscar(): void {
    if (!this.concidencia().trim()) {
      this.getUsuarios();
      return;
    }
    
    this.usuarioService.getUsuarioByCoincidence(this.concidencia()).subscribe({
      next: (usuarios: UsuarioResponse[]) => {
        this.usuarios.set(this.descartarUsuarioLogeado(usuarios));
      },
      error: (error: any) => {
        this.mensajeError.set(error.error?.error || "Error de conexión con el servidor.");
      }
    });
  }

  private descartarUsuarioLogeado(usuarios: UsuarioResponse[]): UsuarioResponse[] {
    const usuarioLogeadoId = localStorage.getItem('usuario_id');
    return usuarios.filter(usuario => usuario.usuario_id.toString() !== usuarioLogeadoId);
  }

  seleccionarUsuario(usuario: UsuarioResponse): void {
    this.usuarioSeleccionado.set(usuario);
    this.eliminar.set(true);
  }

  editarUsuario(usuario: UsuarioResponse): void {
    console.log('Editar usuario:', usuario);
  }

  eliminarUsuario(): void {
    console.log('Eliminar usuario con ID:', this.usuarioSeleccionado().usuario_id);
  }

}
