import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthenticationService } from 'src/login/service/authentication.service';
import { UserService } from 'src/user/service/user.service';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.scss']
})
export class UserDetailsComponent implements OnInit {

  constructor(private userService: UserService,
    private authenticationService: AuthenticationService
  ) { }

  ngOnInit(): void {
    this.userService.fetchDepartments().subscribe((res: any) => {
      this.departmentList = res;
      this.authenticationService.getProfileDetails().subscribe((res: any) => {
        this.setFormValue(res.data);
        this.employee?.controls?.employeeId?.disable();
      })
    })
  }

  emp_id: number = -1;
  departmentList = [];

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

  setFormValue(restemp: any): void {
    this.employee?.controls?.employeeId?.setValue(restemp?.employeeId);
    this.employee?.controls?.employeeFirstName?.setValue(restemp?.firstName);
    this.employee?.controls?.employeeMiddleName?.setValue(restemp?.middleName);
    this.employee?.controls?.employeeLastName?.setValue(restemp?.lastName);
    this.employee?.controls?.employeeEmail?.setValue(restemp?.email);
    this.employee?.controls?.employeeMobile?.setValue(restemp?.mobile);
    let departmentId = Number(restemp?.departmentName);
    this.employee?.controls?.employeeDepartment?.setValue(departmentId);
    this.employee?.controls?.employeeDesignation?.setValue(restemp?.designation);
    this.employee?.controls?.joiningDate?.setValue(restemp?.joiningDate);
    this.emp_id = restemp?.emp_id;
  }

  onSubmit(): void {
    const payload = {
      emp_id: this.emp_id,
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
      status: 1,
    }

    this.userService.saveEmployeeDetails(payload).subscribe((res: any) => {
      this.authenticationService.getProfileDetails().subscribe((restemp: any) => {
        this.setFormValue(restemp.data);
      })
    }, (err) => {
      console.log("err", err);
    })
  }
}
