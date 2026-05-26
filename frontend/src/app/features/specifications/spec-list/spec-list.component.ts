import { Component, signal, viewChild, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { LucideAngularModule, Plus } from 'lucide-angular';
import {
  DataTableComponent,
  Column,
} from '../../../core/components/data-table/data-table.component';

@Component({
  selector: 'app-spec-list',
  standalone: true,
  imports: [CommonModule, RouterModule, LucideAngularModule, DataTableComponent],
  template: `
    <div class="space-y-6 animate-fade-in">
      <div class="page-header">
        <div class="flex items-center justify-between">
          <div>
            <h1 class="page-title">Specifications</h1>
            <p class="page-subtitle">UI, API, Business Rule and Data specifications</p>
          </div>
          <div class="flex items-center gap-2">
            <span class="text-xs text-amber-600 bg-amber-50 px-3 py-1.5 rounded-lg font-medium">
              <lucide-icon name="shield" class="w-3 h-3 inline mr-1"></lucide-icon>
              Gate: Requirements must be approved
            </span>
            <button class="btn-primary gap-2">
              <lucide-icon name="plus" class="w-4 h-4"></lucide-icon>
              New Specification
            </button>
          </div>
        </div>
      </div>

      <!-- Gate Info Cards -->
      <div class="grid grid-cols-1 lg:grid-cols-2 gap-4">
        <div class="bg-amber-50 border border-amber-200 rounded-xl p-4 flex items-start gap-3">
          <lucide-icon
            name="alert-triangle"
            class="w-5 h-5 text-amber-600 flex-shrink-0 mt-0.5"
          ></lucide-icon>
          <div>
            <h3 class="text-sm font-semibold text-amber-800">Requirement Gate</h3>
            <p class="text-xs text-amber-700 mt-1">
              Specifications can only be linked to requirements that are fully approved (BA +
              Customer).
            </p>
          </div>
        </div>
        <div class="bg-blue-50 border border-blue-200 rounded-xl p-4 flex items-start gap-3">
          <lucide-icon name="info" class="w-5 h-5 text-blue-600 flex-shrink-0 mt-0.5"></lucide-icon>
          <div>
            <h3 class="text-sm font-semibold text-blue-800">Task Creation Gate</h3>
            <p class="text-xs text-blue-700 mt-1">
              Development tasks can only be created from specifications that are approved by both
              Head and Customer.
            </p>
          </div>
        </div>
      </div>

      <!-- Approval Stats -->
      <div class="grid grid-cols-2 lg:grid-cols-4 gap-4">
        <div class="card p-4 flex items-center gap-3">
          <div class="p-2 bg-yellow-100 rounded-lg">
            <lucide-icon name="clock" class="w-5 h-5 text-yellow-600"></lucide-icon>
          </div>
          <div>
            <p class="text-2xl font-bold text-gray-900">3</p>
            <p class="text-xs text-gray-500">Pending Head Approval</p>
          </div>
        </div>
        <div class="card p-4 flex items-center gap-3">
          <div class="p-2 bg-amber-100 rounded-lg">
            <lucide-icon name="user-check" class="w-5 h-5 text-amber-600"></lucide-icon>
          </div>
          <div>
            <p class="text-2xl font-bold text-gray-900">2</p>
            <p class="text-xs text-gray-500">Pending Customer</p>
          </div>
        </div>
        <div class="card p-4 flex items-center gap-3">
          <div class="p-2 bg-green-100 rounded-lg">
            <lucide-icon name="check-circle-2" class="w-5 h-5 text-green-600"></lucide-icon>
          </div>
          <div>
            <p class="text-2xl font-bold text-gray-900">8</p>
            <p class="text-xs text-gray-500">Fully Approved</p>
          </div>
        </div>
        <div class="card p-4 flex items-center gap-3">
          <div class="p-2 bg-red-100 rounded-lg">
            <lucide-icon name="ban" class="w-5 h-5 text-red-600"></lucide-icon>
          </div>
          <div>
            <p class="text-2xl font-bold text-gray-900">1</p>
            <p class="text-xs text-gray-500">Rejected</p>
          </div>
        </div>
      </div>

      <app-data-table
        #specTable
        [columns]="columns"
        title="Specifications"
        subtitle="All specifications for this project"
        (view)="onView($event)"
        (edit)="onEdit($event)"
      >
      </app-data-table>
    </div>
  `,
})
export class SpecListComponent implements OnInit {
  readonly Plus = Plus;
  columns: Column[] = [
    { key: 'specCode', label: 'Spec Code', sortable: true },
    { key: 'screenName', label: 'Screen Name', sortable: true },
    { key: 'specType', label: 'Type', sortable: true, type: 'badge' },
    {
      key: 'estimatedManday',
      label: 'Manday',
      sortable: true,
      format: (v: number) => (v ? v + 'd' : '—'),
    },
    { key: 'headConfirmStatus', label: 'Head', sortable: true, type: 'badge' },
    { key: 'customerConfirmStatus', label: 'Customer', sortable: true, type: 'badge' },
    { key: 'status', label: 'Status', sortable: true, type: 'badge' },
    { key: 'actions', label: 'Actions', type: 'actions' },
  ];

  specs = signal([
    {
      id: '1',
      specCode: 'SPEC-001',
      screenName: 'Login Page',
      specType: 'UI',
      estimatedManday: 3,
      headConfirmStatus: 'CONFIRMED',
      customerConfirmStatus: 'CONFIRMED',
      status: 'CUSTOMER_APPROVED',
    },
    {
      id: '2',
      specCode: 'SPEC-002',
      screenName: 'User Management API',
      specType: 'API',
      estimatedManday: 5,
      headConfirmStatus: 'CONFIRMED',
      customerConfirmStatus: 'PENDING',
      status: 'IN_REVIEW',
    },
    {
      id: '3',
      specCode: 'SPEC-003',
      screenName: 'Dashboard Screen',
      specType: 'UI',
      estimatedManday: 8,
      headConfirmStatus: 'PENDING',
      customerConfirmStatus: 'PENDING',
      status: 'DRAFT',
    },
    {
      id: '4',
      specCode: 'SPEC-004',
      screenName: 'Role Permission',
      specType: 'PERMISSION',
      estimatedManday: 4,
      headConfirmStatus: 'CONFIRMED',
      customerConfirmStatus: 'CONFIRMED',
      status: 'CUSTOMER_APPROVED',
    },
    {
      id: '5',
      specCode: 'SPEC-005',
      screenName: 'Sales Report',
      specType: 'REPORT',
      estimatedManday: 6,
      headConfirmStatus: 'REJECTED',
      customerConfirmStatus: 'PENDING',
      status: 'REJECTED',
    },
  ]);

  table = viewChild<DataTableComponent>('specTable');

  ngOnInit() {
    setTimeout(() => this.table()?.setData(this.specs()));
  }

  onView(r: any) {
    console.log('View', r);
  }
  onEdit(r: any) {
    console.log('Edit', r);
  }
}
