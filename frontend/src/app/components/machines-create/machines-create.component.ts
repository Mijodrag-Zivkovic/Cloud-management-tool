import { Component, OnInit } from '@angular/core';
import {FetchService} from "../../services/fetch.service";

@Component({
  selector: 'app-machines-create',
  templateUrl: './machines-create.component.html',
  styleUrls: ['./machines-create.component.css']
})
export class MachinesCreateComponent implements OnInit {

  name: string;
  constructor(private fetchService: FetchService) {
    this.name='';
  }

  submit(){
    this.fetchService.createMachine(this.name).subscribe((result) => {
      console.log(result);

    })
  }

  ngOnInit(): void {
  }

}
