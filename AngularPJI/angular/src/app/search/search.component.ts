import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthorizationService } from '../authorization.service';
import { SearchStoreService } from '../search-store.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  constructor(
      private authService : AuthorizationService ,
      private service : SearchStoreService ,
      private router : Router) { }

  // Property declarations to handle user input
  num !: number;
  zipcode !: number;
  city !: string;
  message="";

  ngOnInit(): void {
  }

  submit():void{
    this.service.setData({ n: this.num, z: this.zipcode, c: this.city});

 // Method to check inputs and navigate to the appropriate page
    if(!this.num && !this.zipcode && !this.city)
    {
      this.message="At least 1 input is required";
      this.router.navigate(['/search']);
    }
    else{
      console.log("Number",this.num);
      console.log("Zipcode",this.zipcode);
      console.log("City",this.city);
      this.router.navigate(['/searchResult']);
    }
  }
   // Method to check if a user has "HD" (Helpdesk) access level
  isHelpdesk():boolean{
    return this.authService.user.accessLevel=="HD";
  }


}
