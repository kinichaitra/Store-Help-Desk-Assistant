import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthorizationService } from '../authorization.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  name !:string;
  accessLevel!:string;

  constructor(private authService : AuthorizationService, public router : Router) { }

  ngOnInit(): void {
    this.name=this.authService.user.name;
    if(this.authService.user.accessLevel=="HD")
    this.accessLevel="Helpdesk";
    else if(this.authService.user.accessLevel=="AD")
    this.accessLevel="Admin";
    else if(this.authService.user.accessLevel=="MN")
    this.accessLevel="Manager";
  }

  SearchC(){
    this.router.navigate(['/search'])
  }
  UpgradeC(){
    this.router.navigate(['/upgrade']);
  }

  isHelpdesk():boolean{
    return this.authService.user.accessLevel=="HD";
  }

}
