import { Component, OnInit } from '@angular/core';
import {MyRequest, Permission, User} from "../../models/models";
import {FetchService} from "../../services/fetch.service";

@Component({
  selector: 'app-users-create',
  templateUrl: './users-create.component.html',
  styleUrls: ['./users-create.component.css']
})
export class UsersCreateComponent implements OnInit {

  allPermissions: Permission[]
  name: string
  surname: string
  email: string
  password: string
  permissions: Permission[]

  constructor(private fetchService: FetchService) {
    this.allPermissions=[]
    this.name='';
    this.surname='';
    this.email='';
    this.password='';
    this.permissions=[];
  }

  submit(){
    let user:User=<User>{};
    user.name = this.name;
    user.surname = this.surname;
    user.email = this.email;
    user.password = this.password;
    user.permissions = this.permissions;
    this.fetchService.createUser(user).subscribe((result) => {
      console.log(result);

    })
  }

  addPermission(permission:Permission){
    console.log(permission);
    this.permissions.push(permission);
  }

  ngOnInit(): void {
    this.fetchService.getAllPermissions().subscribe((result) => {
      console.log(result);
      //this.allPermissions=result;
      for(let p of result)
      {
        console.log("permisija");
        let permission:Permission=<Permission>{};
        permission.id = p.id;
        permission.permission = p.permission;
        this.allPermissions.push(permission);
        console.log(permission);
      }
      for(let p of this.allPermissions)
      {

        console.log(p);
      }
      //console.log(this.result);
    })
  }

}
