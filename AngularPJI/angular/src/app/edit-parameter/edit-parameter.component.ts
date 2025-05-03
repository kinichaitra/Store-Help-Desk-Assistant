import { Component, OnInit } from '@angular/core';
import { ActivatedRoute,Router } from '@angular/router';
import { AuthorizationService } from '../authorization.service';
import { SearchStoreService } from 'src/app/search-store.service';
import { Parameter } from '../parameter.model';
@Component({
  selector: 'app-edit-parameter',
  templateUrl: './edit-parameter.component.html',
  styleUrls: ['./edit-parameter.component.css']
})
export class EditParameterComponent implements OnInit {
  name : string =this.authService.user.name;
  accessLevel : string=this.authService.user.accessLevel;

parameter!:string;
para:Parameter=new Parameter();
  message!: "";

  constructor(
    private service: SearchStoreService,
    private router: Router,
    private route: ActivatedRoute,
    private authService: AuthorizationService){
          console.log("edit Parameter component");}

  ngOnInit() {

    this.parameter = this.route.snapshot.params['parameter'];
    this.service.getParameterbyParameter(this.parameter).subscribe( data => {
      console.log(data);
      this.para = data;
    },
    error => console.log(error) );
  }

  submit() {
    this.service.editParameter(this.parameter, this.para).subscribe( data => {
      this.para = data;
      console.log(data);
      // this.message = "Store updated successfully";
      alert("Parameter will be successfully updated");
      this.goToParameterList();
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
      else {
        alert("An error occurred!");
      }
  });
}
  goToParameterList() {
    this.router.navigate(['/viewParameters']);
  }
}

