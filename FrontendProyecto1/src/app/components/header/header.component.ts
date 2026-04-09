import { Component, inject, OnInit, signal } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { EnumUsuario } from '../../models/usuario/EnumUsuario';

@Component({
  selector: 'app-header-component',
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './header.component.html'
})
export class HeaderComponent implements OnInit {

  nombreUsuario = signal<string>('');
  rolUsuario = signal<EnumUsuario>(null!);

  enumUsuario = EnumUsuario;

  private router = inject(Router);

  ngOnInit(): void {
    this.nombreUsuario.set(localStorage.getItem('nombre') || '');
    this.rolUsuario.set(localStorage.getItem('rol') as EnumUsuario || null!);
  }

  cerrarSesion(): void {
    localStorage.clear();
    this.router.navigate(['/login']);
  }

}
