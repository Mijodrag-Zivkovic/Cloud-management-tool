import { Component, OnInit } from '@angular/core';
import {FetchService} from "../../services/fetch.service";

@Component({
  selector: 'app-users-read',
  templateUrl: './users-read.component.html',
  styleUrls: ['./users-read.component.css']
})
export class UsersReadComponent implements OnInit {

  allUsers: any[];
  permissions: string[];

  constructor(private fetchService: FetchService) {
    this.allUsers=[];
    this.permissions=[]
  }

  delete(user:any){
    this.fetchService.deleteUser(user.id).subscribe((result) => {
      //console.log(result);
      let index = this.allUsers.indexOf(user);
      this.allUsers.splice(index,1);

      //console.log(this.result);
    })
  }

  ngOnInit(): void {
    this.permissions=this.fetchService.getPermissions();
    this.fetchService.getAllUsers().subscribe((result) => {
      //console.log(result);
      this.allUsers=result;

      //console.log(this.result);
    })
  }



}
