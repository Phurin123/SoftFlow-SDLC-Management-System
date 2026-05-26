// src/app/app.routes.ts
import { Routes } from '@angular/router';
import { DashboardLayoutComponent } from './shared/components/dashboard-layout/dashboard-layout.component';

export const routes: Routes = [
  {
    path: '',
    component: DashboardLayoutComponent,
    children: [
      {
        path: '',
        redirectTo: 'projects',
        pathMatch: 'full'
      },
      {
        path: 'dashboard',
        loadComponent: () => import('./features/dashboard/dashboard.component')
          .then(m => m.DashboardComponent),
        title: 'Dashboard - SoftFlow'
      },
      {
        path: 'customers',
        loadComponent: () => import('./features/customers/customer-list/customer-list.component')
          .then(m => m.CustomerListComponent),
        title: 'Customers - SoftFlow'
      },
      {
        path: 'projects',
        loadComponent: () => import('./features/projects/project-list/project-list.component')
          .then(m => m.ProjectListComponent),
        title: 'Projects - SoftFlow'
      },
      {
        path: 'projects/:id',
        loadComponent: () => import('./features/projects/project-detail/project-detail.component')
          .then(m => m.ProjectDetailComponent),
        title: 'Project Detail - SoftFlow'
      },
      {
        path: 'projects/:id/timeline',
        loadComponent: () => import('./features/projects/project-timeline/project-timeline.component')
          .then(m => m.ProjectTimelineComponent),
        title: 'Project Timeline - SoftFlow'
      },
      {
        path: 'projects/:id/requirements',
        loadComponent: () => import('./features/requirements/requirement-list/requirement-list.component')
          .then(m => m.RequirementListComponent),
        title: 'Requirements - SoftFlow'
      },
      {
        path: 'projects/:id/dfd',
        loadComponent: () => import('./features/dfd-designer/dfd-list/dfd-list.component')
          .then(m => m.DfdListComponent),
        title: 'DFD Designer - SoftFlow'
      },
      {
        path: 'projects/:id/er-diagram',
        loadComponent: () => import('./features/er-designer/er-list/er-list.component')
          .then(m => m.ErListComponent),
        title: 'ER Diagram - SoftFlow'
      },
      {
        path: 'projects/:id/specifications',
        loadComponent: () => import('./features/specifications/spec-list/spec-list.component')
          .then(m => m.SpecListComponent),
        title: 'Specifications - SoftFlow'
      },
      {
        path: 'approval-center',
        loadComponent: () => import('./features/approval-center/approval-center.component')
          .then(m => m.ApprovalCenterComponent),
        title: 'Approval Center - SoftFlow'
      }
    ]
  },
  {
    path: '**',
    redirectTo: 'dashboard'
  }
];