import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/login/service/authentication.service';
import * as CryptoJS from 'crypto-js';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.scss']
})
export class LoginFormComponent implements OnInit {

  constructor(private authenticationService: AuthenticationService,
    private router: Router
  ) { }

  ngOnInit(): void {
  }

  passphrase: string = "EmsTestingTeam@2025";

  login = new FormGroup({
    username: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required])
  })

  hidePassword: boolean = true;
  onLogin(): void {
    const key = CryptoJS.SHA256(this.passphrase);
    const iv = CryptoJS.enc.Hex.parse("00000000000000000000000000000000");
    const rawPassword: string = this.login?.controls?.password?.value || '';
    const encryptedPassword = CryptoJS.AES.encrypt(rawPassword, key,
      {
        mode: CryptoJS.mode.CBC,
        padding: CryptoJS.pad.Pkcs7,
        iv: iv
      }
    ).toString();
    const credentials = {
      userName: this.login?.controls?.username?.value,
      password: encryptedPassword,
    }
    this.authenticationService.login(credentials).subscribe((res: any) => {
      this.authenticationService.getProfileDetails().subscribe((res: any) => {
        if(res.data.role == "EMPLOYEE" && this.authenticationService?.isLoggedIn()) {
          this.authenticationService.setRole(res.data.role);
          void this.router.navigate(['/user'], { replaceUrl: true });
        }
        else if(res.data.role == "ADMIN" && this.authenticationService?.isLoggedIn()) {
          this.authenticationService.setRole(res.data.role);
          void this.router.navigate(['/admin'], { replaceUrl: true });
        }
        else {
          console.log("invalid credentials");
        }
      })
    })
  }

  togglePasswordVisibility(): void {
    this.hidePassword = !this.hidePassword;
  }
}
