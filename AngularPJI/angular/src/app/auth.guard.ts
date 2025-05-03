import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthorizationService } from './authorization.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  // We inject Router and AuthorizationService to check if user is logged in or not
  constructor(
    private authservice: AuthorizationService,
    private router : Router){}

  canActivate(): boolean {
     // If user is logged in then return true and allow access to the requested page or route
    if (this.authservice.loggedInUser()){
      return true;
    }
    // If user is not logged in then redirect to login page
    else{
      this.router.navigate(['/login']);
      return false;
    }
  }
}
