import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthorizationService } from '../authorization.service';
import { SearchStoreService } from 'src/app/search-store.service';
import { Store } from '../store.model';
import { FormBuilder, FormGroup } from '@angular/forms';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-updatestore',
  templateUrl: './updatestore.component.html',
  styleUrls: ['./updatestore.component.css']
})
export class UpdateStoreComponent implements OnInit {
    message!: string;
    number!: number;
    name : string =this.authService.user.name;
    accessLevel : string=this.authService.user.accessLevel;

    store:Store=new Store();
updateForm: any;

    constructor(
      private service: SearchStoreService,
      private authService:AuthorizationService ,
      private route: ActivatedRoute,
      private router : Router) {
        console.log("updatestore component") }

  ngOnInit() {
 //  const number = this.route.snapshot.params['number'];

      // this.service.getstorebynumber(number).subscribe( data => {
    this.number = this.route.snapshot.params['number'];
    this.service.getstorebynumber(this.number).subscribe( data => {
      console.log(data);
      this.store = data;
    }
    ,error => console.log(error)
  );
  }

  submit(){

    this.service.updateStore(this.number, this.store).subscribe( data => {
      this.store = data;
      console.log(data);
      this.message = "Store updated successfully";
      alert("Store will be successfully updated");
      this.goToviewstoresList();
    },
    (error:any) =>{
      console.log(error);
      if(error.status == 401)
        {
          alert("Session Expired!!! Login again");
          this.router.navigate(['/logout']);
        }
        else if(error.status == 0)
        {
          alert("Server Down!");
        }
    }
  );
  }

  goToviewstoresList(){
    this.router.navigate(['/viewstores']);
  }


}






































