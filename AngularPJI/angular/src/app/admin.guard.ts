import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthorizationService } from './authorization.service';

@Injectable({
  providedIn: 'root'
})
export class AdminGuard implements CanActivate {
  constructor(private authservice: AuthorizationService, private router : Router){}
// checks user roles and permissions
canActivate(): boolean {
  console.log("AL",localStorage.getItem("accessLevel"));
  console.log("boolean",localStorage.getItem("accessLevel")=="AD");

  if (this.authservice.loggedInUser() && localStorage.getItem("accessLevel")=="AD"){
    return true;
  }
  else{
    alert("you are not authorized to access this resources");
    // this.router.navigate(['/login']);
    this.router.navigate(['/search']);
    return false;
  }

}

}
