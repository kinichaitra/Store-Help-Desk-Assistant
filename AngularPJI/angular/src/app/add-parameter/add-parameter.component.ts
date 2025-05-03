import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthorizationService } from '../authorization.service';
import { SearchStoreService } from 'src/app/search-store.service';
import { Parameter } from '../parameter.model';
@Component({
  selector: 'app-add-parameter',
  templateUrl: './add-parameter.component.html',
  styleUrls: ['./add-parameter.component.css']
})

export class AddParameterComponent implements OnInit {
  constructor(
    private service: SearchStoreService,
    private router: Router,
    private authService: AuthorizationService) {
      console.log("add Parameter component") }

para: Parameter = new Parameter();

  ngOnInit(): void {
  }

  saveParameter() {

    this.service.addParameter(this.para).subscribe((data: any) =>{
      console.log(data);
      alert('Parameter added successfully!');
      this.goToParameterList();
    },
      ( error: any) =>{
      console.log('Error adding parameter:',error);
      alert('Error adding parameter as Data already exists ');
  }
);
}
  goToParameterList() {
    this.router.navigate(['/viewParameters']);
  }

  submit(){
    console.log(this.para);
    this.saveParameter();
  }

}


