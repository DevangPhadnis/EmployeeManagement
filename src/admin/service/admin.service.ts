import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http: HttpClient) { }

  private chartSubject = new BehaviorSubject<any>(null);
  data$ = this.chartSubject.asObservable();

  getEmployeeDetails(pageNumber: number, rowCount: number): Observable<any> {
    const params = new HttpParams().set('pageNumber', pageNumber).set('rowCount', rowCount);
    return this.http.get('/admin/fetchEmployee', { params: params });
  }

  saveEmployeeDetails(payload: any): Observable<any> {
    return this.http.post('/admin/saveEmployee', payload);
  }

  fetchDepartments(): Observable<any> {
    return this.http.get('/admin/fetchDepartment');
  }

  getChartData() {
    return this.http.get('/chart/dashboard');
  }
}
