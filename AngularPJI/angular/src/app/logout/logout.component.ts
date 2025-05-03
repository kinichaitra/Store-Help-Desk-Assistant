import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthorizationService } from '../authorization.service';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.css']
})
export class LogoutComponent implements OnInit {

  constructor(private authService : AuthorizationService, private router : Router) { }

  ngOnInit(): void {

    this.authService.logout();
    this.router.navigate(['login']);
  }

}
