import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  saveEmployeeDetails(payload: any): Observable<any> {
    return this.http.post('/admin/saveEmployee', payload);
  }
  
  fetchDepartments(): Observable<any> {
    return this.http.get('/admin/fetchDepartment');
  }
}
