import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthorizationService } from '../authorization.service';
import { User } from '../user.model';

@Component({
  selector: 'app-update-user',
  templateUrl: './update-user.component.html',
  styleUrls: ['./update-user.component.css']
})
export class UpdateUserComponent implements OnInit {

  name : string =this.authService.user.name;
  accessLevel : string=this.authService.user.accessLevel;
  // id!:number;
  systemUserOid!:number;
  updateForm: any;
user: User=new User();

  constructor(
    private authService:AuthorizationService ,
      private route: ActivatedRoute,
      private router : Router
  ) {console.log("update user component")  }

  ngOnInit(): void {

      // const systemUserOid=this.route.snapshot.params['systemUserOid'];
      // this.authService.getUserById(systemUserOid).subscribe( data => {

    this.systemUserOid=this.route.snapshot.params['systemUserOid'];
    this.authService.getUserById(this.systemUserOid).subscribe( data => {
      console.log(data);
      this.user = data;
    }
    ,error => console.log(error)
  );
  }

  submit(){
  //  this.authService.updateUser(this.id, this.user).subscribe(data => {
    this.authService.updateUser(this.systemUserOid, this.user).subscribe(data => {
    this.user=data;
    console.log(data);
    alert("User will be successfully updated");
    this.goToUsersList();

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

goToUsersList(){
  this.router.navigate(['/userList']);
}

}



