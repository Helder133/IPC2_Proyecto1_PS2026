import { Routes } from '@angular/router';
import { LoginPageComponent } from './pages/login/login-page.component';
import { HomePageComponent } from './pages/home/home-page.component';
import { MainLayoutComponent } from './layouts/main-layout/main-layout.component';
import { UsuarioPageComponent } from './pages/usuario/usuario-page/usuario-page.component';
import { CreateUsuarioPageComponent } from './pages/usuario/create-usuario-page.component/create-usuario-page.component';
import { UpdateUsuarioPageComponent } from './pages/usuario/update-usuario-page.component/update-usuario-page.component';
import { CargaArchivoPageComponent } from './pages/carga-archivo/carga-archivo-page.component';
import { DestinoPageComponent } from './pages/destino/destino-page/destino-page.component';
import { CreateDestinoPageComponent } from './pages/destino/create-destino-page/create-destino-page.component';
import { UpdateDestinoPageComponent } from './pages/destino/update-destino-page/update-destino-page.component';
import { ProveedorPageComponent } from './pages/proveedor/proveedor-page/proveedor-page.component';
import { UpdateProveedorPageComponent } from './pages/proveedor/update-proveedor-page/update-proveedor-page.component';
import { CreateProveedorPageComponent } from './pages/proveedor/create-proveedor-page/create-proveedor-page.component';
import { PaquetePageComponent } from './pages/paqueteTuristico/paquete-page/paquete-page.component';
import { CreatePaquetePageComponent } from './pages/paqueteTuristico/create-paquete-page/create-paquete-page.component';
import { UpdatePaquetePageComponent } from './pages/paqueteTuristico/update-paquete-page/update-paquete-page.component';
import { ServicioPageComponent } from './pages/servicioPaquete/servicio-page/servicio-page.component';

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
            },
            {
                path: 'destino',
                component: DestinoPageComponent
            },
            {
                path: 'destino/crear',
                component: CreateDestinoPageComponent
            },
            {
                path: 'destino/actualizar/:id',
                component: UpdateDestinoPageComponent
            },
            {
                path: 'proveedor',
                component: ProveedorPageComponent
            },
            {
                path: 'proveedor/crear',
                component: CreateProveedorPageComponent
            },
            {
                path: 'proveedor/actualizar/:id',
                component: UpdateProveedorPageComponent
            },
            {
                path: 'paquete',
                component: PaquetePageComponent
            },
            {
                path: 'paquete/crear',
                component: CreatePaquetePageComponent
            },
            {
                path: 'paquete/actualizar/:id',
                component: UpdatePaquetePageComponent
            }, 
            {
                path: 'paquete/:id/servicio',
                component: ServicioPageComponent
            }
        ]

    },
    {
        path: '**',
        redirectTo: 'login'
    }
];
