import { Component, OnInit, signal } from '@angular/core';
import { EnumUsuario } from '../../models/usuario/EnumUsuario';

@Component({
  selector: 'app-home-page.component',
  imports: [],
  templateUrl: './home-page.component.html'
})
export class HomePageComponent implements OnInit {
  
  nombreUsuario = signal<string>('');
  rolUsuario = signal<EnumUsuario>(null!);
  enumUsuario = EnumUsuario;

  ngOnInit(): void {
    this.nombreUsuario.set(localStorage.getItem('nombre') || '');
    this.rolUsuario.set(localStorage.getItem('rol') as EnumUsuario);
  }
}
