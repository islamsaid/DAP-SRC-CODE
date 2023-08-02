import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { HeaderService } from 'src/app/core/services/header-service/header.service';
import { ValidationService } from 'src/app/validation/services/validation.service';
import { AppConfig } from '../../models/appconfig.model';
import { AppConfigService } from '../../services/app-config.service';

@Component({
  selector: 'app-chart-bar',
  templateUrl: './chart-bar.component.html',
  styleUrls: ['./chart-bar.component.scss'],
})
export class ChartBarComponent implements OnInit, OnDestroy {
  stackedData: any;
  stackedOptions: any = {
    tooltips: {
      mode: 'index',
      intersect: false,
    },
    responsive: true,
    scales: {
      xAxes: [
        {
          stacked: true,
        },
      ],
      yAxes: [
        {
          stacked: true,
        },
      ],
    },
  };
  subscription!: Subscription;
  config!: AppConfig;
  data: any;
  DateSubscriber!: Subscription;
  variance: number = 0;
  constructor(
    private configService: AppConfigService,
    private validationService: ValidationService,
    private headerService: HeaderService
  ) {}

  ngOnInit() {
    let date = localStorage.getItem('date');
    this.getBalance(Number(date));
    this.DateSubscriber = this.headerService.DateSubject.subscribe((date) => {
      this.getBalance(date);
    });

    this.config = this.configService.config;
    this.updateChartOptions();
    this.subscription = this.configService.configUpdate$.subscribe((config) => {
      this.config = config;
      this.updateChartOptions();
    });
  }
  getBalance(date: number) {
    this.validationService.retrieveBalance(date).subscribe((resp: any) => {
      this.data = resp.payload;
      this.variance = Math.abs(this.data.variance);
      console.log(this.variance);
      this.stackedData = {
        labels: ['Opening', 'Closing'],
        datasets: [
          {
            type: 'bar',
            label: 'Opening',
            backgroundColor: '#36ACFF',
            data: [this.data.opening, 0],
          },
          {
            type: 'bar',
            label: 'Closing',
            backgroundColor: '#69D0D0',
            data: [0, this.data.closing],
          },
          {
            type: 'bar',
            label: 'Variance',
            backgroundColor: '#E60000',
            data: [Math.abs(this.data.variance), 0],
          },
        ],
      };
    });
  }
  updateChartOptions() {
    if (this.config.dark) this.applyDarkTheme();
    else this.applyLightTheme();
  }

  applyLightTheme() {
    this.stackedOptions = {
      plugins: {
        tooltips: {
          mode: 'index',
          intersect: false,
        },
        legend: {
          labels: {
            color: '#495057',
          },
        },
      },
      scales: {
        x: {
          stacked: true,
          ticks: {
            color: '#495057',
          },
          grid: {
            color: '#ebedef',
          },
        },
        y: {
          stacked: true,
          ticks: {
            color: '#495057',
          },
          grid: {
            color: '#ebedef',
          },
        },
      },
    };
  }
  applyDarkTheme() {
    this.stackedOptions = {
      plugins: {
        legend: {
          labels: {
            color: '#ebedef',
          },
        },
        tooltips: {
          mode: 'index',
          intersect: false,
        },
      },
      scales: {
        x: {
          stacked: true,
          ticks: {
            color: '#ebedef',
          },
          grid: {
            color: 'rgba(255,255,255,0.2)',
          },
        },
        y: {
          stacked: true,
          ticks: {
            color: '#ebedef',
          },
          grid: {
            color: 'rgba(255,255,255,0.2)',
          },
        },
      },
    };
  }

  float(num1: number, num2: number) {
    let sum = Number(num1) + Number(num2);
    return (sum / 1000000).toFixed(2);
  }
  recalc(num1: number, num2: number) {
    let height = ((Number(num1) + Number(num2)) / 340824.3).toString();
    console.log(height);
    return height + 'px';
  }
  ngOnDestroy(): void {
    this.subscription.unsubscribe();
    this.DateSubscriber.unsubscribe();
  }
}
