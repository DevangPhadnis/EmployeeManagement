import { Component, Input, OnInit } from '@angular/core';
import { ChartConfiguration, ChartData, ChartType } from 'chart.js';
import { AdminService } from 'src/admin/service/admin.service';

@Component({
  selector: 'app-pie-chart',
  templateUrl: './pie-chart.component.html',
  styleUrls: ['./pie-chart.component.scss']
})
export class PieChartComponent implements OnInit {

  @Input() chartDetails: any;

  constructor() { }

  ngOnInit(): void {
    let chartData: any = [];
    let backgroundColorData = ['#FF6384', '#36A2EB', '#FFCE56'];
    let backgroundColor: any = [];
    let colorIndex = 0;
    Object.keys(this.chartDetails.pieChartData).forEach((key: any) => {
      this.pieChartLabels.push(key);
      chartData.push(this.chartDetails.pieChartData[key]);
      backgroundColor.push(backgroundColorData[colorIndex++]);
    })
    this.pieChartData['labels'] = this.pieChartLabels;
    const dataSet = [
      {
        data: chartData,
        backgroundColor: backgroundColor
      }
    ]
    this.pieChartData['datasets'] = dataSet;
  }

  public pieChartType: ChartType = 'pie';

  public pieChartOptions: ChartConfiguration['options'] = {
    responsive: true,
    plugins: {
      legend: {
        position: 'top'
      }
    },
    aspectRatio: 2
  };

  public pieChartLabels: string[] = [];

  public pieChartData: any = {
    labels: [],
    datasets: []
  };
    // labels: [],
    // datasets: [
    //   { data: [], backgroundColor: [] }
    // ]
}
