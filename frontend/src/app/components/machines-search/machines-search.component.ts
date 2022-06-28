import { Component, OnInit } from '@angular/core';
import {FetchService} from "../../services/fetch.service";

@Component({
  selector: 'app-machines-search',
  templateUrl: './machines-search.component.html',
  styleUrls: ['./machines-search.component.css']
})
export class MachinesSearchComponent implements OnInit {

  name: string;
  status: string;
  dateFrom: string;
  dateTo: string;
  flag:boolean;
  constructor(private fetchService: FetchService) {
    this.name='';
    this.status='';
    this.dateFrom='';
    this.dateTo='';
    this.flag=true;
  }

  submit(){
    let data = [];
    if(this.name!='')
    {
      data.push(this.name);
    }
    if(this.status!='')
    {
      data.push(this.status);
    }
    if(this.dateFrom!='')
    {
      data.push({"dateFrom": this.dateFrom});
    }
    if(this.dateTo!='')
    {
      data.push({"dateTo": this.dateTo});
    }
    let data2={
      "name":"Machine 1",
      "status":"RUNNING"
    }
    console.log(data2);
    let data3 = {};

    //this.name,this.status,this.dateFrom,this.dateTo
    JSON.stringify(data);
    console.log(data);
    console.log(JSON.stringify(data))
    this.fetchService.searchMachines(data2).subscribe((result) => {
      console.log(result);
      this.flag=true;
    })
  }

  ngOnInit(): void {
  }

}
