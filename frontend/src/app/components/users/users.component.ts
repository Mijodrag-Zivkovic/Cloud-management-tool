import { Component, OnInit } from '@angular/core';
import {FetchService} from "../../services/fetch.service";

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

  permissions: string[];

  constructor(private fetchService: FetchService) {
    this.permissions=[];

  }

  ngOnInit(): void {
    this.permissions=this.fetchService.getPermissions();
    // for(const permission of this.permissions){
    //   console.log(permission);
    // }
  }

}
