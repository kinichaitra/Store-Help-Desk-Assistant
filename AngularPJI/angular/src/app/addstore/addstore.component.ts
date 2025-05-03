import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthorizationService } from '../authorization.service';
import { SearchStoreService } from 'src/app/search-store.service';
import { Store } from '../store.model';
import{FormControl,FormGroup,FormBuilder, Validators} from '@angular/forms';
@Component({
  selector: 'app-store',
  templateUrl: './addstore.component.html',
  styleUrls: ['./addstore.component.css']
})
export class AddstoreComponent implements OnInit {
  constructor(
    private service: SearchStoreService,
    private router: Router,
    private authService: AuthorizationService,
    private formBuilder:FormBuilder) {
      console.log("add store component") }

  saved=false;
  onsave(){
    this.saved=true;
  }
  message: boolean = false;
number!: number;
zipcode!: number;
city! :string;
submitted:boolean=false;
ngOnInit(): void {

}
addData=new FormGroup({
  number: new FormControl("", Validators.required),
  city: new FormControl("", Validators.required),
  zipcode: new FormControl("", Validators.required)

})
save() {
  this.submitted = true;
if(this.addData.invalid){
  return;
}
  // Declare the variable storeData
  this.service.addStore(this.addData.value).subscribe(
    (data: any) => {
    console.log(data);
    this.message = true;
    // this.addData.reset({});
  },
  (error: any) => {
    console.log('There was an error adding the store', error);

     if (error.status === 400) {
      alert( 'Bad request');
    } else if (error.status === 500) {
      alert('Internal server error');
    }
      else if(error.status == 401){
        alert("Session Expired!!! Login again");
        this.router.navigate(['/logout']);
      }
      else if(error.status == 0){
        alert("Server Down!!!");
      }

  });
}
onReset(){
  this.addData.reset();
  this.submitted= false;
}

removeMessage() {
  this.message = false;
  this.addData.reset();
  this.submitted=true;
}
}






