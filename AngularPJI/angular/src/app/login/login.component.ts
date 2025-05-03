import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthorizationService } from '../authorization.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(
    private authService : AuthorizationService,
    private router : Router) { }

  userName !: string;
  password !: string;
  validCred : string =" ";
  validUsername : string =" ";
  validPassword : string =" ";

  loginForm = new FormGroup({
    userName:new FormControl('',[Validators.required]),
    password:new FormControl('',[Validators.required])
  });
  submitted= false;

  submit(){

    this.validCred=" ";
    this.submitted=true;


    if(this.loginForm.value.userName!='' && this.loginForm.value.password!='')
      this.login();
  }

  ngOnInit(): void {
     localStorage.removeItem('token');
     localStorage.removeItem('accessLevel');
     localStorage.removeItem('name');
     this.router.navigate(['/login']);
  }

  login(){
    this.authService.login(this.loginForm.value.userName!, this.loginForm.value.password!)
    .subscribe((data:any): void=>{
                        console.log(data);
                        console.log("Login Successful");
                        localStorage.setItem('token',("Bearer "+data.token));
                        localStorage.setItem('accessLevel',data.accessLevel);
                        localStorage.setItem('name',data.name);
                        this.authService.user.name=data.name;
                        this.authService.user.accessLevel=data.accessLevel;
                        console.log("Token:   ",localStorage.getItem('token'));
                        this.router.navigate(['/search'])
                      },
                (error:any)=>{
                  if(error.status == 0)
                            {
                              alert("Server Down! Can't Login");
                            }
                  else{
                    console.log(error);
                    this.validCred="Invalid Credentials !!!";
                    this.router.navigate(['/login']);
                    this.submitted=false;
                  }
                });

              }
}
