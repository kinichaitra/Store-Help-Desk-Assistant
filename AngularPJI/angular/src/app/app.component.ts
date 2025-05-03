import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { AuthorizationService } from './authorization.service';
import {MatButton,MatSnackBar} from '@angular/material';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  constructor(
    private authService : AuthorizationService,
    public router : Router,
    private title:Title) { }

  ngOnInit(): void {
    this.title.setTitle("Store Helpdesk");
  }


  loggedInUser!:boolean;
  name : string =this.authService.user.name;
  accessLevel : string=this.authService.user.accessLevel;

  isHelpdesk():boolean{
    return this.accessLevel=="Helpdesk";
  }

  isAdmin(): boolean {
    return this.accessLevel=="Admin";
    }




  loggedIn(): boolean {
    this.loggedInUser=this.authService.loggedInUser();
// returns the name of the user and their access level
    this.name=this.authService.user.name;
    if(this.authService.user.accessLevel=="HD")
    this.accessLevel="Helpdesk";
    else if(this.authService.user.accessLevel=="AD")
    this.accessLevel="Admin";
    else if(this.authService.user.accessLevel=="MN")
    this.accessLevel="Manager";

    return this.loggedInUser;
  }
}

