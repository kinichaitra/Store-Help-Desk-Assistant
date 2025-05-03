import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { User } from './user.model';

@Injectable({
  providedIn: 'root'
})
export class AuthorizationService {

  user = new User();
  i=false;

  constructor(
    private http: HttpClient) { }

  login(userName:string, password:string) {

    return this.http.get('http://localhost:8091/authorization/login',
            {headers: {'userName':userName,'password':password},responseType: 'json'})
    .pipe(tap(res => {
                            }));
  }

  getAllUsers(): Observable<any> {
    return this.http.get(`http://localhost:8091/authorization`);
  }
  // getUserById(id: number): Observable<User> {
  //   return this.http.get<User>(`http://localhost:8091/authorization/user/{id}`);
  // }
  getUserById(systemUserOid: number): Observable<User> {
    return this.http.get<User>(`http://localhost:8091/authorization/user/${systemUserOid}`);
  }


  addUser(user:any): Observable<any> {
    return this.http.post(`http://localhost:8091/authorization/user`,user);
  }


  // updateUser(id: number, updateduser: User): Observable<any> {
  //   return this.http.put(`http://localhost:8091/authorization/update/{id}`, updateduser);
  // }

  // deleteUser(id: number): Observable<void> {
  //   return this.http.delete<void>(`http://localhost:8091/authorization/user/{id}`);
  // }
  updateUser(systemUserOid:number, updateduser: User): Observable<User> {
    return this.http.put<User>(`http://localhost:8091/authorization/update/${systemUserOid}`,updateduser);
  }
  deleteUser(systemUserOid: number): Observable<void> {
       return this.http.delete<void>(`http://localhost:8091/authorization/user/${systemUserOid}`);
     }


  logout(){
    localStorage.clear();

  }

  loggedInUser(): boolean{
    return !!localStorage.getItem('token');
  }

  // getUsers(): Observable<User[]> {
  //   return this.http.get<User[]>(`${this.baseUrl}`);
  // }

  // getUserById(id: number): Observable<User> {
  //   return this.http.get<User>(`${this.baseUrl}/${id}`);
  // }

  // addUser(user: User): Observable<User> {
  //   return this.http.post<User>(`${this.baseUrl}`, user);
  // }

  // updateUser(id: number, user: User): Observable<User> {
  //   return this.http.put<User>(`${this.baseUrl}/${id}`, user);
  // }

  // deleteUser(id: number): Observable<void> {
  //   return this.http.delete<void>(`${this.baseUrl}/${id}`);
  // }
}




/*
    this.http.get('http://localhost:8091/authorization/user',{headers: {'userName':userName}})
          .subscribe((data: any)=>{
                                    console.log(data);
                                    console.log("Got the user details");
                                    this.userDetails=data;
                                  });

    this.user.userName=userName;
    this.user.password=password;
*/
/*
    return this.http.post(`http://localhost:8091/authorization/login`,this.user,{responseType: 'text'}).
            pipe(tap(res => {
                              localStorage.setItem('token', res);
                            }));
*/
