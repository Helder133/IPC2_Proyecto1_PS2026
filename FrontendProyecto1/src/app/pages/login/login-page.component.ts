import { CommonModule } from '@angular/common';
import { Component, inject, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { LoginService } from '../../services/login/login.service';
import { LoginRequest } from '../../models/login/LoginRequest';
import { UsuarioResponse } from '../../models/usuario/UsuarioResponse';

@Component({
  selector: 'app-login-page',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './login-page.component.html'
})
export class LoginPageComponent implements OnInit {

  loginFormGroup!: FormGroup;
  isLoading = signal<boolean>(false);
  isError = signal<boolean>(false);
  mensajeError = signal<string>('');

  private formBuilder = inject(FormBuilder);
  private loginService = inject(LoginService);
  private router = inject(Router);

  ngOnInit(): void {
    this.loginFormGroup = this.formBuilder.group({
      nombre: ['', [Validators.required, Validators.maxLength(250)]],
      password: ['', [Validators.required, Validators.maxLength(250)]]
    })
  }

  login(): void {
    if (this.loginFormGroup.invalid) {
      this.loginFormGroup.markAllAsTouched();
      return;
    }

    this.isLoading.set(true);
    this.isError.set(false);

    const loginRequest = signal<LoginRequest>(this.loginFormGroup.value);

    this.loginService.loginUser(loginRequest()).subscribe({

      next: (usuarioResponse: UsuarioResponse) => {
        this.isLoading.set(false);

        localStorage.setItem('usuario_id', usuarioResponse.usuario_id.toString());
        localStorage.setItem('nombre', usuarioResponse.nombre);
        localStorage.setItem('rol', usuarioResponse.rol);

        this.router.navigate(['/home']);
      },
      error: (error: any) => {
        this.isLoading.set(false);
        this.isError.set(true);
        this.mensajeError.set(error.error?.error || "Error de conexión con el servidor.");
      }

    })

  }

}
