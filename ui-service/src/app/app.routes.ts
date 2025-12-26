import { Routes } from '@angular/router';
import { Login } from './components/login/login';
import { Register } from './components/register/register';
import { Dashboard } from './dashboard/dashboard';

export const routes: Routes = [

    {
        path:'',
        component:Login
    },
     {
        path:'register',
        component:Register,
        pathMatch:'full'
    },
    {
        path:'dashboard',
        component:Dashboard,
        pathMatch:'full'
    }
];
