import { Component, OnInit } from '@angular/core';
import {Permission, User} from "../../models/models";
import {FetchService} from "../../services/fetch.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-users-edit',
  templateUrl: './users-edit.component.html',
  styleUrls: ['./users-edit.component.css']
})
export class UsersEditComponent implements OnInit {

  allPermissions: Permission[]
  userId: string;
  name: string
  surname: string
  email: string
  password: string
  permissions: Permission[]

  constructor(private fetchService: FetchService,private activatedRoute: ActivatedRoute) {
    this.allPermissions = [];
    this.userId = '';
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
    this.fetchService.updateUser(user,this.userId).subscribe((result) => {
      console.log(result);

    })
  }

  addPermission(permission:Permission){
    //console.log(permission);
    if(this.permissions.indexOf(permission)<0)
    {
      this.permissions.push(permission);
      console.log("dodat")
    }
  }

  removePermission(permission:Permission){
    //console.log(permission);
    let index = this.permissions.indexOf(permission);
    console.log(index);
    if(index>=0)
    {
      console.log("oduzet")
      this.permissions.splice(index,1);
      console.log(this.permissions)
    }
  }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => {
      this.userId = params['id'];
      console.log(this.userId);
      this.fetchService.getUserById(this.userId).subscribe((result) => {
        this.name=result.name;
        this.surname=result.surname;
        this.email=result.email;
        for(let p of result.permissions)
        {
          let permission:Permission=<Permission>{};
          permission.id=p.id;
          permission.permission=p.permission;
          this.permissions.push(permission);
        }
        //this.permissions=result.permissions;
        this.fetchService.getAllPermissions().subscribe((result) => {
          //console.log(result);
          //this.allPermissions=result;
          for(let p of result)
          {
            let permission:Permission=<Permission>{};
            permission.id = p.id;
            permission.permission = p.permission;
            this.allPermissions.push(permission);
          }
          //console.log(this.result);
        })
      });
    });

  }

}
