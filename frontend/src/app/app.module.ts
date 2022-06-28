import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {FormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import { LoginComponent } from './components/login/login.component';
import { UsersComponent } from './components/users/users.component';
import { UsersReadComponent } from './components/users-read/users-read.component';
import { UsersCreateComponent } from './components/users-create/users-create.component';
import { UsersEditComponent } from './components/users-edit/users-edit.component';
import { MachinesComponent } from './components/machines/machines.component';
import { MachinesSearchComponent } from './components/machines-search/machines-search.component';
import { MachinesCreateComponent } from './components/machines-create/machines-create.component';



@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    UsersComponent,
    UsersReadComponent,
    UsersCreateComponent,
    UsersEditComponent,
    MachinesComponent,
    MachinesSearchComponent,
    MachinesCreateComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
