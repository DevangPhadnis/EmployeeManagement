import { Component, Input, OnInit } from '@angular/core';
import { ChartConfiguration, ChartData, ChartType } from 'chart.js';

@Component({
  selector: 'app-line-chart',
  templateUrl: './line-chart.component.html',
  styleUrls: ['./line-chart.component.scss']
})
export class LineChartComponent implements OnInit {

  @Input() chartDetails: any;

  constructor() { }

  ngOnInit(): void {
    let chartData: any = [];
    Object.keys(this.chartDetails.lineChartData).forEach((key: any) => {
      this.lineChartLabels.push(key);
      chartData.push(this.chartDetails.lineChartData[key]);
    })
    this.lineChartData['labels'] = this.lineChartLabels;
    const dataSet = [
      {
        label: 'Employee Details',
        data: chartData,
        borderColor: 'blue',
        backgroundColor: 'rgba(0, 0, 255, 0.1)',
        tension: 0.4,
        fill: true
      }
    ]
    this.lineChartData['datasets'] = dataSet;
  }

  public lineChartLabels: string[] = [];
  public lineChartData: ChartConfiguration['data'] = {
    labels: [],
    datasets: []
  };

  public lineChartOptions: ChartConfiguration['options'] = {
    responsive: true,
    plugins: {
      legend: {
        display: true,
        position: 'top'
      },
      tooltip: {
        enabled: true
      }
    },
    scales: {
      y: {
        beginAtZero: true
      }
    },
    aspectRatio: 8
  };

  public lineChartType: ChartType = 'line';
}
