import { Routes } from '@angular/router';
import { LoginPageComponent } from './pages/login/login-page.component';
import { HomePageComponent } from './pages/home/home-page.component';
import { MainLayoutComponent } from './layouts/main-layout/main-layout.component';
import { UsuarioPageComponent } from './pages/usuario/usuario-page/usuario-page.component';
import { CreateUsuarioPageComponent } from './pages/usuario/create-usuario-page.component/create-usuario-page.component';
import { UpdateUsuarioPageComponent } from './pages/usuario/update-usuario-page.component/update-usuario-page.component';
import { CargaArchivoPageComponent } from './pages/carga-archivo/carga-archivo-page.component';

export const routes: Routes = [
    {
        path: '',
        redirectTo: 'login',
        pathMatch: 'full'
    },
    {
        path: 'login',
        component: LoginPageComponent
    },
    {
        path: '',
        component: MainLayoutComponent,
        children: [
            {
                path: 'home',
                component: HomePageComponent
            },
            {
                path: 'usuario',
                component: UsuarioPageComponent,
            },
            {
                path: 'usuario/crear',
                component: CreateUsuarioPageComponent,
            },
            {
                path: 'usuario/actualizar/:id',
                component: UpdateUsuarioPageComponent
            }, 
            {
                path: 'carga-archivo',
                component: CargaArchivoPageComponent
            }
        ]

    },
    {
        path: '**',
        redirectTo: 'login'
    }
];
