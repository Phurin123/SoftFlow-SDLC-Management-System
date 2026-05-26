import { Component, signal, viewChild, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { LucideAngularModule, Plus } from 'lucide-angular';
import {
  DataTableComponent,
  Column,
} from '../../../core/components/data-table/data-table.component';

@Component({
  selector: 'app-dfd-list',
  standalone: true,
  imports: [CommonModule, RouterModule, LucideAngularModule, DataTableComponent],
  template: `
    <div class="space-y-6 animate-fade-in">
      <div class="page-header">
        <div class="flex items-center justify-between">
          <div>
            <h1 class="page-title">DFD Designer</h1>
            <p class="page-subtitle">Data Flow Diagram processes linked to approved requirements</p>
          </div>
          <div class="flex items-center gap-2">
            <span class="text-xs text-amber-600 bg-amber-50 px-3 py-1.5 rounded-lg font-medium">
              <lucide-icon name="shield" class="w-3 h-3 inline mr-1"></lucide-icon>
              Gate: Requirements must be approved
            </span>
            <button class="btn-primary gap-2">
              <lucide-icon name="plus" class="w-4 h-4"></lucide-icon>
              New Process
            </button>
          </div>
        </div>
      </div>

      <!-- Gate Info Card -->
      <div class="bg-amber-50 border border-amber-200 rounded-xl p-4 flex items-start gap-3">
        <lucide-icon
          name="alert-triangle"
          class="w-5 h-5 text-amber-600 flex-shrink-0 mt-0.5"
        ></lucide-icon>
        <div>
          <h3 class="text-sm font-semibold text-amber-800">Business Rule Gate</h3>
          <p class="text-xs text-amber-700 mt-1">
            DFD processes can only be created for requirements that have been approved by both BA
            and Customer. Unapproved requirements will be blocked at creation time.
          </p>
        </div>
      </div>

      <app-data-table
        #dfdTable
        [columns]="columns"
        title="DFD Processes"
        subtitle="Data flow processes for this project"
        (view)="onView($event)"
        (edit)="onEdit($event)"
      >
      </app-data-table>
    </div>
  `,
})
export class DfdListComponent implements OnInit {
  readonly Plus = Plus;
  columns: Column[] = [
    { key: 'processCode', label: 'Process Code', sortable: true },
    { key: 'processName', label: 'Process Name', sortable: true },
    { key: 'dfdLevel', label: 'Level', sortable: true, type: 'badge' },
    { key: 'status', label: 'Status', sortable: true, type: 'badge' },
    { key: 'owner', label: 'Owner', sortable: true },
    { key: 'inputData', label: 'Input', sortable: false },
    { key: 'outputData', label: 'Output', sortable: false },
    { key: 'actions', label: 'Actions', type: 'actions' },
  ];

  processes = signal([
    {
      id: '1',
      processCode: 'P-001',
      processName: 'User Authentication',
      dfdLevel: 'Level 0',
      status: 'APPROVED',
      owner: 'SA_Pranee',
      inputData: 'Credentials',
      outputData: 'Session Token',
    },
    {
      id: '2',
      processCode: 'P-002',
      processName: 'Order Processing',
      dfdLevel: 'Level 1',
      status: 'REVIEW',
      owner: 'SA_Pranee',
      inputData: 'Order Data',
      outputData: 'Invoice',
    },
    {
      id: '3',
      processCode: 'P-003',
      processName: 'Report Generation',
      dfdLevel: 'Level 1',
      status: 'DRAFT',
      owner: 'SA_Somsak',
      inputData: 'Filter Criteria',
      outputData: 'PDF Report',
    },
  ]);

  table = viewChild<DataTableComponent>('dfdTable');

  ngOnInit() {
    setTimeout(() => this.table()?.setData(this.processes()));
  }

  onView(r: any) {
    console.log('View', r);
  }
  onEdit(r: any) {
    console.log('Edit', r);
  }
}
