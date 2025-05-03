import { Component, OnInit } from '@angular/core';
import { AuthorizationService } from '../authorization.service';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '../user.model';
import { FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {
Users!: User[];
id: any;
p:number=1;
  constructor(
    private authService : AuthorizationService,
    private router : Router,
    private formBuilder:FormBuilder,
    private route: ActivatedRoute)  {
      console.log("authorization service is implimented");
   }

  ngOnInit(): void {
    this.getAllUsers();
  }

  private getAllUsers() {
    this.authService.getAllUsers().subscribe((data:User[])=>{
      console.log(data);
      this.Users = data;
    }
  );
}
updateUser(systemUserOid:number) {
  console.log("update button clicked");
  // this.router.navigate(['/update',id],{
    this.router.navigate(['/update-user',systemUserOid],{
  });
}


// addUser() {
//   this.router.navigate(['/add-user']);
//   }

    deleteUser(systemUserOid: number) {
      console.log("Delete button clicked");
      this.authService.deleteUser(systemUserOid).subscribe(data => {
      console.log(data);
      alert("user will be deleted successfully");
      console.log('user Deleted Successfully');
      console.log(data);

      this.getAllUsers()
    },
    error => {
      console.log(error);
      alert("An error occurred while deleting the user.");
    });
    }




}
