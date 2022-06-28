import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {TokenGuard} from "./token.guard";
import {LoginComponent} from "./components/login/login.component";
import {UsersComponent} from "./components/users/users.component";
import {UsersReadComponent} from "./components/users-read/users-read.component";
import {UsersCreateComponent} from "./components/users-create/users-create.component";
import {UsersEditComponent} from "./components/users-edit/users-edit.component";
import {MachinesComponent} from "./components/machines/machines.component";
import {MachinesCreateComponent} from "./components/machines-create/machines-create.component";
import {MachinesSearchComponent} from "./components/machines-search/machines-search.component";

const routes: Routes = [
  {
    path: "login",
    component: LoginComponent
  },
  {
    path: "users",
    component: UsersComponent,
    canActivate: [TokenGuard]
  },
  {
    path: "users-read",
    component: UsersReadComponent,
    canActivate: [TokenGuard]
  },
  {
    path: "users-create",
    component: UsersCreateComponent,
    canActivate: [TokenGuard]
  },
  {
    path: "users-edit/:id",
    component: UsersEditComponent,
    canActivate: [TokenGuard]
  },
  {
    path: "machines",
    component: MachinesComponent,
    canActivate: [TokenGuard]
  },
  {
    path: "machines-create",
    component: MachinesCreateComponent,
    canActivate: [TokenGuard]
  },
  {
    path: "machines-search",
    component: MachinesSearchComponent,
    canActivate: [TokenGuard]
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
