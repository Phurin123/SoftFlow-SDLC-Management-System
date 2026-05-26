import { Component, signal, viewChild, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { LucideAngularModule, Plus } from 'lucide-angular';
import {
  DataTableComponent,
  Column,
} from '../../../core/components/data-table/data-table.component';

@Component({
  selector: 'app-project-list',
  standalone: true,
  imports: [CommonModule, RouterModule, LucideAngularModule, DataTableComponent],
  templateUrl: './project-list.component.html',
})
export class ProjectListComponent implements OnInit {
  readonly Plus = Plus;

  private readonly router: Router;

  projects = signal<any[]>([]);
  loading = signal(false);

  columns: Column[] = [
    { key: 'projectCode', label: 'Project Code', sortable: true },
    { key: 'projectName', label: 'Project Name', sortable: true },
    { key: 'customerName', label: 'Customer', sortable: true },
    { key: 'sdlcStatus', label: 'SDLC Status', sortable: true, type: 'badge' },
    { key: 'priority', label: 'Priority', sortable: true, type: 'badge' },
    {
      key: 'progressPercentage',
      label: 'Progress',
      sortable: true,
      format: (v: number) => v + '%',
    },
    { key: 'projectManager', label: 'PM', sortable: true },
    { key: 'actions', label: 'Actions', type: 'actions' },
  ];

  table = viewChild<DataTableComponent>('projectTable');

  constructor(router: Router) {
    this.router = router;
  }

  ngOnInit() {
    this.projects.set([
      {
        id: '1',
        projectCode: 'PRJ-001',
        projectName: 'ERP System Migration',
        customerName: 'Acme Corporation',
        sdlcStatus: 'DEVELOPMENT',
        priority: 'HIGH',
        progressPercentage: 65,
        projectManager: 'Somchai P.',
        startDate: '2024-01-15',
      },
      {
        id: '2',
        projectCode: 'PRJ-002',
        projectName: 'E-commerce Platform',
        customerName: 'TechStart Ltd',
        sdlcStatus: 'REQUIREMENT_APPROVAL',
        priority: 'CRITICAL',
        progressPercentage: 25,
        projectManager: 'Nattapong S.',
        startDate: '2024-03-01',
      },
      {
        id: '3',
        projectCode: 'PRJ-003',
        projectName: 'Smart City Dashboard',
        customerName: 'Bangkok Municipality',
        sdlcStatus: 'SPECIFICATION_APPROVAL',
        priority: 'HIGH',
        progressPercentage: 40,
        projectManager: 'Pranee K.',
        startDate: '2024-02-10',
      },
      {
        id: '4',
        projectCode: 'PRJ-004',
        projectName: 'HR Portal',
        customerName: 'Acme Corporation',
        sdlcStatus: 'UAT',
        priority: 'MEDIUM',
        progressPercentage: 85,
        projectManager: 'Wichai T.',
        startDate: '2023-11-01',
      },
      {
        id: '5',
        projectCode: 'PRJ-005',
        projectName: 'Mobile Banking App',
        customerName: 'Thai Bank Co.',
        sdlcStatus: 'PLANNING',
        priority: 'CRITICAL',
        progressPercentage: 10,
        projectManager: 'Siriwan M.',
        startDate: '2024-05-01',
      },
      {
        id: '6',
        projectCode: 'PRJ-006',
        projectName: 'IoT Monitoring',
        customerName: 'Factory Pro',
        sdlcStatus: 'INTERNAL_TESTING',
        priority: 'MEDIUM',
        progressPercentage: 75,
        projectManager: 'Apichat R.',
        startDate: '2024-01-20',
      },
    ]);

    setTimeout(() => {
      this.table()?.setData(this.projects());
    });
  }

  onView(project: any) {
    this.router.navigate(['/projects', project.id]);
  }

  onEdit(project: any) {
    console.log('Edit project:', project);
  }

  onDelete(project: any) {
    console.log('Delete project:', project);
  }
}
