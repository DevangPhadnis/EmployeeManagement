import { Component, OnInit } from '@angular/core';
import { AdminService } from 'src/admin/service/admin.service';
import * as moment from 'moment';

@Component({
  selector: 'app-departments',
  templateUrl: './departments.component.html',
  styleUrls: ['./departments.component.scss']
})
export class DepartmentsComponent implements OnInit {

  constructor(private adminService: AdminService) { }

  ngOnInit(): void {
    this.adminService.fetchDepartments().subscribe((res: any) => {
      const rows: [] = res.map((row: any) => {
        return {
          departmentId: row?.departmentId,
          departmentName: row?.departmentName,
          createdDate: moment(new Date(row?.createdDate)).format('DD-MM-YYYY'),
          status: row?.status,
        }
      })
      this.filteredRows = [...rows];
    })
  }

  filteredRows = [];
  columns = [
    {
      prop: 'departmentId',
      name: 'Department Id'
    },
    {
      prop: 'departmentName',
      name: 'Department Name'
    },
    {
      prop: 'createdDate',
      name: 'Date of Creation'
    },
    {
      prop: 'status',
      name: 'Status'
    }
  ]
}
