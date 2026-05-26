import { Component, signal, viewChild, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { LucideAngularModule, Plus } from 'lucide-angular';
import {
  DataTableComponent,
  Column,
} from '../../../core/components/data-table/data-table.component';

@Component({
  selector: 'app-requirement-list',
  standalone: true,
  imports: [CommonModule, RouterModule, LucideAngularModule, DataTableComponent],
  template: `
    <div class="space-y-6 animate-fade-in">
      <div class="page-header">
        <div class="flex items-center justify-between">
          <div>
            <h1 class="page-title">Requirements</h1>
            <p class="page-subtitle">Manage and track project requirements</p>
          </div>
          <button class="btn-primary gap-2">
            <lucide-icon name="plus" class="w-4 h-4"></lucide-icon>
            New Requirement
          </button>
        </div>
      </div>

      <div class="grid grid-cols-1 lg:grid-cols-3 gap-4 mb-6">
        <div class="card p-4 flex items-center gap-3">
          <div class="p-2 bg-yellow-100 rounded-lg">
            <lucide-icon name="clock" class="w-5 h-5 text-yellow-600"></lucide-icon>
          </div>
          <div>
            <p class="text-2xl font-bold text-gray-900">5</p>
            <p class="text-xs text-gray-500">Pending BA Review</p>
          </div>
        </div>
        <div class="card p-4 flex items-center gap-3">
          <div class="p-2 bg-amber-100 rounded-lg">
            <lucide-icon name="user-check" class="w-5 h-5 text-amber-600"></lucide-icon>
          </div>
          <div>
            <p class="text-2xl font-bold text-gray-900">3</p>
            <p class="text-xs text-gray-500">Pending Customer</p>
          </div>
        </div>
        <div class="card p-4 flex items-center gap-3">
          <div class="p-2 bg-green-100 rounded-lg">
            <lucide-icon name="check-circle-2" class="w-5 h-5 text-green-600"></lucide-icon>
          </div>
          <div>
            <p class="text-2xl font-bold text-gray-900">12</p>
            <p class="text-xs text-gray-500">Fully Approved</p>
          </div>
        </div>
      </div>

      <app-data-table
        #reqTable
        [columns]="columns"
        title="Requirements"
        subtitle="Track BA and Customer approval status"
        (view)="onView($event)"
        (edit)="onEdit($event)"
      >
      </app-data-table>
    </div>
  `,
})
export class RequirementListComponent implements OnInit {
  readonly Plus = Plus;
  columns: Column[] = [
    { key: 'requirementCode', label: 'Code', sortable: true },
    { key: 'title', label: 'Title', sortable: true },
    { key: 'requirementType', label: 'Type', sortable: true, type: 'badge' },
    { key: 'priority', label: 'Priority', sortable: true, type: 'badge' },
    { key: 'baConfirmStatus', label: 'BA Status', sortable: true, type: 'badge' },
    { key: 'customerConfirmStatus', label: 'Customer', sortable: true, type: 'badge' },
    { key: 'status', label: 'Status', sortable: true, type: 'badge' },
    { key: 'actions', label: 'Actions', type: 'actions' },
  ];

  requirements = signal([
    {
      id: '1',
      requirementCode: 'REQ-001',
      title: 'User Login with SSO',
      requirementType: 'FUNCTIONAL',
      priority: 'MUST',
      baConfirmStatus: 'CONFIRMED',
      customerConfirmStatus: 'CONFIRMED',
      status: 'APPROVED',
    },
    {
      id: '2',
      requirementCode: 'REQ-002',
      title: 'Multi-language Support',
      requirementType: 'NON_FUNCTIONAL',
      priority: 'SHOULD',
      baConfirmStatus: 'CONFIRMED',
      customerConfirmStatus: 'PENDING',
      status: 'IN_REVIEW',
    },
    {
      id: '3',
      requirementCode: 'REQ-003',
      title: 'Data Export to Excel',
      requirementType: 'REPORT',
      priority: 'MUST',
      baConfirmStatus: 'PENDING',
      customerConfirmStatus: 'PENDING',
      status: 'DRAFT',
    },
    {
      id: '4',
      requirementCode: 'REQ-004',
      title: 'Role-based Access Control',
      requirementType: 'SECURITY',
      priority: 'MUST',
      baConfirmStatus: 'CONFIRMED',
      customerConfirmStatus: 'CONFIRMED',
      status: 'APPROVED',
    },
    {
      id: '5',
      requirementCode: 'REQ-005',
      title: 'Dashboard Analytics',
      requirementType: 'UI',
      priority: 'COULD',
      baConfirmStatus: 'CONFIRMED',
      customerConfirmStatus: 'CONFIRMED',
      status: 'APPROVED',
    },
  ]);

  table = viewChild<DataTableComponent>('reqTable');

  ngOnInit() {
    setTimeout(() => this.table()?.setData(this.requirements()));
  }

  onView(r: any) {
    console.log('View', r);
  }
  onEdit(r: any) {
    console.log('Edit', r);
  }
}
