import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private http: HttpClient,
    private router: Router
  ) { }

  private tokenKey: string = "token";

  private loggedIn = new BehaviorSubject<boolean>(this.hasToken());

  private roleKey: string = "role";

  login(credentials: any): Observable<any> {
    return this.http.post('/admin/login', credentials).pipe(tap((response: any) => {
      if(response.data) {
        localStorage.setItem(this.tokenKey, response.data);
        this.loggedIn.next(true);
      }
      else {
        throw new Error('Token not found');
      }
    }))
  }

  hasToken(): boolean {
    return !!localStorage.getItem(this.tokenKey);
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  isLoggedIn(): boolean {
    return this.loggedIn.value;
  }

  getLoginStatus(): Observable<boolean> {
    return this.loggedIn.asObservable();
  }

  logout(): void {
    localStorage.removeItem(this.tokenKey);
    localStorage.removeItem(this.roleKey);
    this.loggedIn.next(false);
    this.router.navigate(['/login']);
  }

  getProfileDetails() {
    return this.http.get('/user/profile');
  }

  setRole(role: string): void {
    localStorage.setItem(this.roleKey, role);
  }

  getRole(): string {
    return String(localStorage.getItem(this.roleKey));
  }
}
