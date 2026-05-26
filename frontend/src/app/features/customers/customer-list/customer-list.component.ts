import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { LucideAngularModule, Plus, Building2, Phone, Mail } from 'lucide-angular';
import {
  DataTableComponent,
  Column,
} from '../../../core/components/data-table/data-table.component';
import { Customer, CustomerService } from '../../../core/services/customer.service';

@Component({
  selector: 'app-customer-list',
  standalone: true,
  imports: [CommonModule, RouterModule, LucideAngularModule, DataTableComponent],
  templateUrl: './customer-list.component.html',
})
export class CustomerListComponent implements OnInit {
  readonly Plus = Plus;
  readonly Building2 = Building2;

  private readonly customerService = inject(CustomerService);

  customers = signal<Customer[]>([]);
  loading = signal(false);
  showModal = signal(false);

  columns: Column[] = [
    { key: 'customerName', label: 'Customer Name', sortable: true },
    { key: 'taxId', label: 'Tax ID', sortable: true },
    { key: 'contactPerson', label: 'Contact Person', sortable: true },
    { key: 'phone', label: 'Phone', sortable: false },
    { key: 'email', label: 'Email', sortable: true },
    {
      key: 'customerType',
      label: 'Type',
      sortable: true,
      type: 'badge',
      badgeClass: (v: string) => {
        const map: Record<string, string> = {
          COMPANY: 'bg-blue-100 text-blue-800',
          GOVERNMENT: 'bg-purple-100 text-purple-800',
          INDIVIDUAL: 'bg-gray-100 text-gray-800',
        };
        return `inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium ${map[v] || 'bg-gray-100 text-gray-800'}`;
      },
    },
    { key: 'status', label: 'Status', sortable: true, type: 'badge' },
    { key: 'actions', label: 'Actions', type: 'actions' },
  ];

  ngOnInit() {
    this.loadCustomers();
  }

  loadCustomers() {
    this.loading.set(true);
    this.customerService.getCustomers().subscribe({
      next: (res: any) => {
        this.customers.set(res.data?.content || []);
        this.loading.set(false);
      },
      error: () => {
        // Demo data if API not available
        this.customers.set([
          {
            id: '1',
            customerName: 'Acme Corporation',
            taxId: '1234567890123',
            contactPerson: 'John Doe',
            phone: '+66-2-123-4567',
            email: 'john@acme.com',
            customerType: 'COMPANY',
            status: 'ACTIVE',
            projectCount: 3,
            contractCount: 2,
            createdAt: '2024-01-15',
            updatedAt: '2024-05-20',
          },
          {
            id: '2',
            customerName: 'TechStart Ltd',
            taxId: '9876543210987',
            contactPerson: 'Jane Smith',
            phone: '+66-2-987-6543',
            email: 'jane@techstart.com',
            customerType: 'COMPANY',
            status: 'ACTIVE',
            projectCount: 1,
            contractCount: 1,
            createdAt: '2024-02-20',
            updatedAt: '2024-05-18',
          },
          {
            id: '3',
            customerName: 'Bangkok Municipality',
            taxId: '1112223334445',
            contactPerson: 'Somsak T.',
            phone: '+66-2-222-3333',
            email: 'somsak@bkk.go.th',
            customerType: 'GOVERNMENT',
            status: 'ACTIVE',
            projectCount: 2,
            contractCount: 2,
            createdAt: '2024-03-01',
            updatedAt: '2024-05-15',
          },
          {
            id: '4',
            customerName: 'Sarun Wong',
            taxId: '5556667778889',
            contactPerson: 'Sarun Wong',
            phone: '+66-8-123-4567',
            email: 'sarun@gmail.com',
            customerType: 'INDIVIDUAL',
            status: 'ACTIVE',
            projectCount: 0,
            contractCount: 0,
            createdAt: '2024-04-10',
            updatedAt: '2024-05-10',
          },
        ]);
        this.loading.set(false);
      },
    });
  }

  onView(customer: Customer) {
    console.log('View customer:', customer);
  }

  onEdit(customer: Customer) {
    console.log('Edit customer:', customer);
  }

  onDelete(customer: Customer) {
    console.log('Delete customer:', customer);
  }

  openModal() {
    this.showModal.set(true);
  }
}
