import { Component, OnInit } from '@angular/core';
import {FetchService} from "../../services/fetch.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username: string;
  password: string;
  permissions: string[]

  constructor(private fetchService: FetchService) {
    this.username='';
    this.password='';
    this.permissions=[]
  }

  login(){
    // console.log(this.username);
    // console.log(this.password);
    if(this.username!='' && this.password!='')
    {
      this.fetchService.login(this.username,this.password).subscribe((result) => {
        // console.log(result);
        // console.log(result.jwt)
        localStorage.setItem("jwt",result.jwt)
        for(const permission of result.user.permissions)
        {
          this.permissions.push(permission.permission);
          // console.log(permission.permission);
        }
        this.fetchService.setPermissions(this.permissions);
        //console.log(this.result);
      })
    }
    else{
      window.alert("All fields must be filled!");
    }

  }

  ngOnInit(): void {
  }

}
