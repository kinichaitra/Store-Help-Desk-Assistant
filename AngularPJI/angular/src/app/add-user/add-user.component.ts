
import { Component, OnInit } from '@angular/core';
import { AuthorizationService } from '../authorization.service';
import { Router } from '@angular/router';
import { User } from '../user.model';
import { FormGroup, FormControl, Validators } from '@angular/forms';
@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css']
})
export class AddUserComponent implements OnInit {
// name!: '';
// accessLevel!: '';
//   password!: '';


  constructor(
    private authService : AuthorizationService,
    private router : Router)
   {
console.log('Add user implemented');
  }
  user: User = new User();
  ngOnInit(): void {
  }
  saveUser() {
    this.authService.addUser(this.user).subscribe(data => {
          console.log(data);
          alert('user added successfully!');
          this.gotoUserList();

          // Reset form
        },
        ( error: any) => {
          console.error('Error adding user', error);
          alert('error adding user');
        }
      );

  }

  gotoUserList(){
    this.router.navigate(['/userList']);
  }
  submit(){
    console.log(this.user);
    this.saveUser();

  }
}
