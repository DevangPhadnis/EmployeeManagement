import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './component/home/home.component';
import { EmployeeDetailComponent } from './component/employee-detail/employee-detail.component';
import { DepartmentsComponent } from './component/departments/departments.component';

const routes: Routes = [
  {
    path: 'home',
    component: HomeComponent
  },
  {
    path: 'employee-details',
    component: EmployeeDetailComponent
  },
  {
    path: 'department-details',
    component: DepartmentsComponent
  },
  {
    path: '',
    redirectTo: 'home',
    pathMatch:'full'
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
