import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthorizationService } from './authorization.service';

@Injectable({
  providedIn: 'root'
})
export class AccessGuard implements CanActivate {

      constructor(private authservice: AuthorizationService, private router : Router){}
// checks user roles and permissions
      canActivate(): boolean {
        console.log("AL",localStorage.getItem("accessLevel"));
        console.log("boolean",localStorage.getItem("accessLevel")=="HD");

        if (this.authservice.loggedInUser() && localStorage.getItem("accessLevel")=="HD"){
          return true;
        }
        else{
          alert("you are not authorized to access this resources");
           this.router.navigate(['/search']);
    // this.router.navigate(['/login']);
          return false;
        }

    }
}
