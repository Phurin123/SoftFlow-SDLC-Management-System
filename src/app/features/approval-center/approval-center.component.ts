import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { LucideAngularModule, 
  FileCheck, ClipboardCheck, GitBranch, Database, 
  FileText, AlertTriangle, CheckCircle2, XCircle, Clock
} from 'lucide-angular';

interface ApprovalItem {
  id: string;
  type: string;
  code: string;
  title: string;
  projectName: string;
  requester: string;
  approver: string;
  status: string;
  submittedAt: string;
  icon: any;
  color: string;
}

@Component({
  selector: 'app-approval-center',
  standalone: true,
  imports: [CommonModule, RouterModule, LucideAngularModule],
  template: `
    <div class="space-y-6 animate-fade-in">
      <div class="page-header">
        <h1 class="page-title">Approval Center</h1>
        <p class="page-subtitle">Review and approve items across all projects</p>
      </div>

      <!-- Stats -->
      <div class="grid grid-cols-2 lg:grid-cols-4 gap-4">
        <div class="card p-4 flex items-center gap-3">
          <div class="p-2 bg-amber-100 rounded-lg">
            <lucide-icon name="clock" class="w-5 h-5 text-amber-600"></lucide-icon>
          </div>
          <div>
            <p class="text-2xl font-bold text-gray-900">12</p>
            <p class="text-xs text-gray-500">Pending Your Approval</p>
          </div>
        </div>
        <div class="card p-4 flex items-center gap-3">
          <div class="p-2 bg-yellow-100 rounded-lg">
            <lucide-icon name="alert-triangle" class="w-5 h-5 text-yellow-600"></lucide-icon>
          </div>
          <div>
            <p class="text-2xl font-bold text-gray-900">3</p>
            <p class="text-xs text-gray-500">Urgent (Overdue)</p>
          </div>
        </div>
        <div class="card p-4 flex items-center gap-3">
          <div class="p-2 bg-green-100 rounded-lg">
            <lucide-icon name="check-circle-2" class="w-5 h-5 text-green-600"></lucide-icon>
          </div>
          <div>
            <p class="text-2xl font-bold text-gray-900">48</p>
            <p class="text-xs text-gray-500">Approved This Month</p>
          </div>
        </div>
        <div class="card p-4 flex items-center gap-3">
          <div class="p-2 bg-red-100 rounded-lg">
            <lucide-icon name="x-circle" class="w-5 h-5 text-red-600"></lucide-icon>
          </div>
          <div>
            <p class="text-2xl font-bold text-gray-900">5</p>
            <p class="text-xs text-gray-500">Rejected This Month</p>
          </div>
        </div>
      </div>

      <!-- Approval List -->
      <div class="card">
        <div class="card-header">
          <div class="flex items-center gap-4">
            <h2 class="text-lg font-semibold text-gray-900">Pending Approvals</h2>
            <div class="flex items-center gap-2">
              <button class="px-3 py-1.5 text-xs font-medium bg-primary-600 text-white rounded-lg">All</button>
              <button class="px-3 py-1.5 text-xs font-medium text-gray-600 hover:bg-gray-100 rounded-lg">Requirements</button>
              <button class="px-3 py-1.5 text-xs font-medium text-gray-600 hover:bg-gray-100 rounded-lg">Specifications</button>
              <button class="px-3 py-1.5 text-xs font-medium text-gray-600 hover:bg-gray-100 rounded-lg">DFD</button>
            </div>
          </div>
        </div>
        <div class="divide-y divide-gray-100">
          @for (item of approvals(); track item.id) {
            <div class="px-6 py-4 flex items-center gap-4 hover:bg-gray-50 transition-colors">
              <div class="p-2 rounded-lg flex-shrink-0" [class]="'bg-' + item.color + '-100'">
                <lucide-icon [name]="item.icon" [class]="'text-' + item.color + '-600'" class="w-5 h-5"></lucide-icon>
              </div>
              <div class="flex-1 min-w-0">
                <div class="flex items-center gap-2">
                  <span class="text-xs font-medium px-2 py-0.5 rounded-full"
                        [class.bg-gray-100]="true" [class.text-gray-600]="true">{{ item.type }}</span>
                  <span class="text-sm font-semibold text-gray-900">{{ item.code }}</span>
                </div>
                <p class="text-sm text-gray-700 mt-0.5">{{ item.title }}</p>
                <p class="text-xs text-gray-500 mt-0.5">{{ item.projectName }} | Requested by {{ item.requester }} | {{ item.submittedAt }}</p>
              </div>
              <div class="flex items-center gap-2 flex-shrink-0">
                <button class="btn-primary text-xs py-1.5 px-3">
                  <lucide-icon name="check-circle-2" class="w-3.5 h-3.5 mr-1"></lucide-icon>
                  Approve
                </button>
                <button class="btn-secondary text-xs py-1.5 px-3">
                  <lucide-icon name="x-circle" class="w-3.5 h-3.5 mr-1"></lucide-icon>
                  Reject
                </button>
                <button class="p-1.5 text-gray-400 hover:text-gray-600 hover:bg-gray-100 rounded-lg transition-colors">
                  <lucide-icon name="eye" class="w-4 h-4"></lucide-icon>
                </button>
              </div>
            </div>
          }
        </div>
      </div>
    </div>
  `
})
export class ApprovalCenterComponent {
  approvals = signal<ApprovalItem[]>([
    { id: '1', type: 'Requirement', code: 'REQ-002', title: 'Multi-language Support', projectName: 'E-commerce Platform', requester: 'Nattaporn S.', approver: 'You', status: 'PENDING', submittedAt: '2 hours ago', icon: ClipboardCheck, color: 'amber' },
    { id: '2', type: 'Specification', code: 'SPEC-002', title: 'User Management API', projectName: 'ERP System Migration', requester: 'Prasert K.', approver: 'You', status: 'PENDING', submittedAt: '5 hours ago', icon: FileText, color: 'blue' },
    { id: '3', type: 'DFD Process', code: 'P-002', title: 'Order Processing', projectName: 'E-commerce Platform', requester: 'SA_Pranee', approver: 'You', status: 'PENDING', submittedAt: '1 day ago', icon: GitBranch, color: 'purple' },
    { id: '4', type: 'ER Table', code: 'orders', title: 'Orders Table Design', projectName: 'ERP System Migration', requester: 'SA_Pranee', approver: 'You', status: 'PENDING', submittedAt: '1 day ago', icon: Database, color: 'pink' },
    { id: '5', type: 'Requirement', code: 'REQ-003', title: 'Data Export to Excel', projectName: 'ERP System Migration', requester: 'Nattaporn S.', approver: 'You', status: 'PENDING', submittedAt: '2 days ago', icon: ClipboardCheck, color: 'amber' },
    { id: '6', type: 'Specification', code: 'SPEC-003', title: 'Dashboard Screen', projectName: 'Smart City Dashboard', requester: 'Prasert K.', approver: 'You', status: 'PENDING', submittedAt: '3 days ago', icon: FileText, color: 'blue' },
  ]);
}
