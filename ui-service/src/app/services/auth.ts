import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { tap } from 'rxjs';
@Injectable({
  providedIn: 'root',
})
export class Auth {

  private API='https://localhost:3000/api/v1/auth';
   

  constructor(private http:HttpClient){

  }

  register(user: { Fname: string; Lname:string; email: string; password: string }) {
  return this.http.post(`${this.API}/register`, user);
}


//Send the email and password to backend here .

  login(credentials:{email:string;password:string}){
    return this.http.post<any>(`${this.API}login`,credentials).pipe(
      tap(res=>localStorage.setItem('token',res.token))
    );
  }
  
  logout(){
    localStorage.removeItem('token');
  }

  getToken(){
    return localStorage.getItem('token');

  }

  isLoggedIn():boolean{
    return !!this.getToken();
  }
}
