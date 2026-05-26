import { Component, signal, viewChild, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { LucideAngularModule, Plus, Database } from 'lucide-angular';
import {
  DataTableComponent,
  Column,
} from '../../../core/components/data-table/data-table.component';

@Component({
  selector: 'app-er-list',
  standalone: true,
  imports: [CommonModule, RouterModule, LucideAngularModule, DataTableComponent],
  template: `
    <div class="space-y-6 animate-fade-in">
      <div class="page-header">
        <div class="flex items-center justify-between">
          <div>
            <h1 class="page-title">ER Diagram Designer</h1>
            <p class="page-subtitle">Entity-Relationship design linked to DFD and Requirements</p>
          </div>
          <div class="flex items-center gap-2">
            <span class="text-xs text-amber-600 bg-amber-50 px-3 py-1.5 rounded-lg font-medium">
              <lucide-icon name="shield" class="w-3 h-3 inline mr-1"></lucide-icon>
              Gate: Requirements must be approved
            </span>
            <button class="btn-primary gap-2">
              <lucide-icon name="plus" class="w-4 h-4"></lucide-icon>
              New Table
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
            ER Tables can only be created for requirements that have been approved by both BA and
            Customer. Each table tracks its related DFD Data Store and approved requirements.
          </p>
        </div>
      </div>

      <app-data-table
        #erTable
        [columns]="columns"
        title="ER Tables"
        subtitle="Database tables for this project"
        (view)="onView($event)"
        (edit)="onEdit($event)"
      >
      </app-data-table>
    </div>
  `,
})
export class ErListComponent implements OnInit {
  readonly Plus = Plus;
  columns: Column[] = [
    { key: 'tableName', label: 'Table Name', sortable: true },
    { key: 'displayName', label: 'Display Name', sortable: true },
    { key: 'columnCount', label: 'Columns', sortable: true },
    { key: 'relatedDfdDataStore', label: 'DFD Data Store', sortable: true },
    { key: 'status', label: 'Status', sortable: true, type: 'badge' },
    { key: 'actions', label: 'Actions', type: 'actions' },
  ];

  tables = signal([
    {
      id: '1',
      tableName: 'users',
      displayName: 'Users',
      columnCount: 8,
      relatedDfdDataStore: 'User DB',
      status: 'APPROVED',
    },
    {
      id: '2',
      tableName: 'orders',
      displayName: 'Orders',
      columnCount: 12,
      relatedDfdDataStore: 'Order DB',
      status: 'REVIEW',
    },
    {
      id: '3',
      tableName: 'products',
      displayName: 'Products',
      columnCount: 10,
      relatedDfdDataStore: 'Product DB',
      status: 'DRAFT',
    },
    {
      id: '4',
      tableName: 'customers',
      displayName: 'Customers',
      columnCount: 15,
      relatedDfdDataStore: 'Customer DB',
      status: 'APPROVED',
    },
  ]);

  table = viewChild<DataTableComponent>('erTable');

  ngOnInit() {
    setTimeout(() => this.table()?.setData(this.tables()));
  }

  onView(r: any) {
    console.log('View', r);
  }
  onEdit(r: any) {
    console.log('Edit', r);
  }
}
