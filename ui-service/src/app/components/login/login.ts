
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { Auth } from '../../services/auth';
import { email } from '@angular/forms/signals';

@Component({
  selector: 'app-login',
  imports: [RouterLink,FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login implements OnInit {

  credentials={
    email:'',
    password:'',
    error:''

  }

  constructor(private auth:Auth,private router:Router){

  }

onSubmit(){
  if(this.credentials.email!=''&& this.credentials.password !='' ){
       this.auth.login({email:this.credentials.email,password:this.credentials.password}).subscribe({
        next:()=>this.router.navigate(['/dashboard']),
        error:()=>this.credentials.error="Invalid credentials"
       })
  }else{
    console.log("fiels are empty")
  }


}


  ngOnInit(): void {
    
  }

}
