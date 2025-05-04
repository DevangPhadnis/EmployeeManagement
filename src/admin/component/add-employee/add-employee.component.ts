import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { AdminService } from 'src/admin/service/admin.service';

@Component({
  selector: 'app-add-employee',
  templateUrl: './add-employee.component.html',
  styleUrls: ['./add-employee.component.scss']
})
export class AddEmployeeComponent implements OnInit {

  constructor(private matDialogRef: MatDialogRef<AddEmployeeComponent>,
    @Inject(MAT_DIALOG_DATA) dialogData: any,
    private adminService: AdminService
  ) { 
    this.header = dialogData?.header;
    this.formData = dialogData?.formData;
  }

  ngOnInit(): void {
    this.adminService.fetchDepartments().subscribe((res: any) => {
      this.departmentList = res;
      if(this.formData != null && this.formData != undefined) {
        this.employee?.patchValue(this.formData);
        this.employee?.disable();
        this.showSubmitButton = false;
      }
    })
  }

  header: string = "";
  departmentList = [];
  formData: any;
  showSubmitButton: any = true;

  employee = new FormGroup({
    employeeId: new FormControl(null, [Validators.required]),
    employeeFirstName: new FormControl('', [Validators.required]),
    employeeMiddleName: new FormControl(''),
    employeeLastName: new FormControl('', [Validators.required]),
    employeeEmail: new FormControl('', [Validators.required, Validators.pattern('^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$')]),
    employeeMobile: new FormControl('', [Validators.required, Validators.pattern('^[6-9][0-9]{9}$')]),
    employeeDepartment: new FormControl(-1, [Validators.required]),
    employeeDesignation: new FormControl('', [Validators.required]),
    joiningDate: new FormControl(null, [Validators.required]),
  })

  onSubmit(): void {
    const payload = {
      employeeId: this.employee?.controls?.employeeId?.value,
      firstName: this.employee?.controls?.employeeFirstName?.value,
      middleName: this.employee?.controls?.employeeMiddleName?.value,
      lastName: this.employee?.controls?.employeeLastName?.value,
      email: this.employee?.controls?.employeeEmail?.value,
      mobile: this.employee?.controls?.employeeMobile?.value,
      department: {
        departmentId: Number(this.employee?.controls?.employeeDepartment?.value)
      },
      designation: this.employee?.controls?.employeeDesignation?.value,
      joiningDate: this.employee?.controls?.joiningDate?.value,
    }

    this.adminService.saveEmployeeDetails(payload).subscribe((res: any) => {
      this.matDialogRef.close(true);
    }, (err) => {
      this.matDialogRef.close(false);
    })
  }

  onClose(): void {
    this.matDialogRef.close(false);
  }
}
