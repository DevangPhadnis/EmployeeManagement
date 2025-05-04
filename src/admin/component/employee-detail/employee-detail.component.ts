import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { AdminService } from 'src/admin/service/admin.service';
import { AddEmployeeComponent } from '../add-employee/add-employee.component';
import { first } from 'rxjs';

@Component({
  selector: 'app-employee-detail',
  templateUrl: './employee-detail.component.html',
  styleUrls: ['./employee-detail.component.scss']
})
export class EmployeeDetailComponent implements OnInit {

  constructor(private adminService: AdminService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.loadEmployees({ first: 0, rows: this.rowCount });
  }

  employees: any[] = [];
  totalRecords: number = 0;
  pageNumber: number = 1;
  rowCount: number = 8;

  loadEmployees(event: any): void {
    const page = event.first / event.rows + 1;  // Calculate page number
    this.pageNumber = page;
    this.rowCount = event.rows;
    
    this.employees.splice(0);
    this.employees = [...this.employees];
    this.adminService.getEmployeeDetails(this.pageNumber, this.rowCount).subscribe((res: any) => {
      this.employees = res.data;
      this.totalRecords = res.totalRecords;
    })
  }

  addEmployee(): void {
    const dialogRef = this.dialog.open(AddEmployeeComponent, {
      height: '64vh',
      width: '55vw',
      disableClose: true,
      data: {
        header: 'Add New Employee',
      }
    })
    dialogRef.afterClosed().subscribe((res: boolean) => {
      if(res) {
        this.employees.splice(0);
        this.loadEmployees({ first: 0, rows: this.rowCount });
      }
    })
  }

  onCardClick(employeeDetails: any): void {
    const employeeData = {
      employeeId: employeeDetails?.employeeId,
      employeeFirstName: employeeDetails?.firstName,
      employeeMiddleName: employeeDetails?.middleName,
      employeeLastName: employeeDetails?.lastName,
      employeeEmail: employeeDetails?.email,
      employeeMobile: employeeDetails?.mobile,
      employeeDepartment: employeeDetails?.department?.departmentId,
      employeeDesignation: employeeDetails?.designation,
      joiningDate: employeeDetails?.joiningDate,
    }
    const dialogRef = this.dialog.open(AddEmployeeComponent, {
      height: '64vh',
      width: '55vw',
      disableClose: true,
      data: {
        header: 'Add New Employee',
        formData: employeeData
      }
    })
    dialogRef.afterClosed().subscribe((res: boolean) => {
      if(res) {
        this.employees.splice(0);
        this.loadEmployees({ first: 0, rows: this.rowCount });
      }
    })
  }
}
