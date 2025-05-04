import { Component, Input, OnInit } from '@angular/core';
import { ChartConfiguration, ChartData, ChartType } from 'chart.js';
import { AdminService } from 'src/admin/service/admin.service';

@Component({
  selector: 'app-bar-chart',
  templateUrl: './bar-chart.component.html',
  styleUrls: ['./bar-chart.component.scss']
})
export class BarChartComponent implements OnInit {

  @Input() chartDetails: any;

  constructor() { }

  ngOnInit(): void {
    this.barChartData.datasets[0].data = [this.chartDetails.active, this.chartDetails.inActive];
    this.isShow = true;
  }

  public barChartOptions: ChartConfiguration['options'] = {
      responsive: true,
    };
  
  isShow = false;
  public barChartLabels: string[] = ['Active', 'In Active'];
  
  public barChartData: ChartData<'bar'> = {
    labels: ['Active', 'In Active'],
    datasets: [
      {
        label: 'Employee Details',
        data: [0, 0],
        backgroundColor: '#42A5F5'
      }
    ]
  };
  
  public barChartType: ChartType = 'bar';
}
