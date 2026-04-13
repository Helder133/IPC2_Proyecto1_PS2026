import { CommonModule } from '@angular/common';
import { Component, signal } from '@angular/core';

@Component({
  selector: 'app-reporete-print.component',
  imports: [CommonModule],
  templateUrl: './reporete-print.component.html',
  styleUrl: './reporete-print.component.css',
})
export class ReporetePrintComponent  { 
  tipo = signal<string>('');
  datos = signal<any>(null);
  fechas = signal<{inicio: string, fin: string}>({inicio: '', fin: ''});
  fechaHoy = new Date();

  ngOnInit(): void {
    const d = localStorage.getItem('rep_data');
    const t = localStorage.getItem('rep_tipo');
    const f = localStorage.getItem('rep_fechas');
    
    if (d && t && f) {
      this.datos.set(JSON.parse(d));
      this.tipo.set(t);
      this.fechas.set(JSON.parse(f));
      
      localStorage.removeItem('rep_data');
      localStorage.removeItem('rep_tipo');
      localStorage.removeItem('rep_fechas');

      setTimeout(() => window.print(), 500);
    }
  }
}
