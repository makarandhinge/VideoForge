import { Component } from '@angular/core';
import {  Router, RouterLink } from '@angular/router';
import { Auth } from '../../services/auth';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-register',
  imports: [RouterLink,FormsModule],
  templateUrl: './register.html',
  styleUrl: './register.css',
})
export class Register {

  Fname='';
  Lname='';
  email='';
  password='';
  error='';

  constructor(private auth:Auth,private router:Router){

  }

  onSubmit(){

    if(this.Fname!='' && this.Lname!=''&&this.email!=''&&this.password!=''){
      this.auth.register({
        Fname:this.Fname,
        Lname:this.Lname,
         email: this.email,
         password:this.password
         

      }
    
    
    ).subscribe({
        next:()=>this.router.navigate(['/login']),
        error:()=>this.error="Registration failed"
      });
    }
    console.log(this.Fname, this.Lname, this.email, this.password);

  }
  



}
