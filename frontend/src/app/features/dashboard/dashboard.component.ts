import { Component, signal, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { LucideAngularModule, 
  FolderKanban, Users, FileCheck, AlertTriangle, 
  Clock, CheckCircle, TrendingUp, BarChart3, 
  ClipboardCheck, Bug, Receipt, Wrench, ArrowUpRight
} from 'lucide-angular';

interface DashboardCard {
  title: string;
  value: string;
  change: string;
  changeType: 'positive' | 'negative' | 'neutral';
  icon: any;
  iconBg: string;
  iconColor: string;
  link: string;
}

interface SdlcStage {
  name: string;
  status: 'completed' | 'active' | 'pending';
  count: number;
  color: string;
}

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule, LucideAngularModule],
  templateUrl: './dashboard.component.html'
})
export class DashboardComponent implements OnInit {
  readonly FolderKanban = FolderKanban;
  readonly Users = Users;
  readonly FileCheck = FileCheck;
  readonly AlertTriangle = AlertTriangle;
  readonly Clock = Clock;
  readonly CheckCircle = CheckCircle;
  readonly TrendingUp = TrendingUp;
  readonly BarChart3 = BarChart3;
  readonly ClipboardCheck = ClipboardCheck;
  readonly Bug = Bug;
  readonly Receipt = Receipt;
  readonly Wrench = Wrench;
  readonly ArrowUpRight = ArrowUpRight;

  stats = signal<DashboardCard[]>([
    { title: 'Active Projects', value: '12', change: '+2 this month', changeType: 'positive', icon: FolderKanban, iconBg: 'bg-blue-100', iconColor: 'text-blue-600', link: '/projects' },
    { title: 'Total Customers', value: '28', change: '+3 this month', changeType: 'positive', icon: Users, iconBg: 'bg-green-100', iconColor: 'text-green-600', link: '/customers' },
    { title: 'Pending Approvals', value: '5', change: '-2 from last week', changeType: 'positive', icon: FileCheck, iconBg: 'bg-amber-100', iconColor: 'text-amber-600', link: '/approval-center' },
    { title: 'Open Bugs', value: '8', change: '+3 critical', changeType: 'negative', icon: AlertTriangle, iconBg: 'bg-red-100', iconColor: 'text-red-600', link: '#' },
  ]);

  sdlcFlow = signal<SdlcStage[]>([
    { name: 'Prospect', status: 'completed', count: 5, color: 'bg-gray-500' },
    { name: 'Contract', status: 'completed', count: 12, color: 'bg-blue-500' },
    { name: 'Requirements', status: 'active', count: 3, color: 'bg-amber-500' },
    { name: 'Analysis', status: 'active', count: 4, color: 'bg-purple-500' },
    { name: 'Specification', status: 'pending', count: 2, color: 'bg-pink-500' },
    { name: 'Planning', status: 'pending', count: 0, color: 'bg-cyan-500' },
    { name: 'Development', status: 'pending', count: 6, color: 'bg-indigo-500' },
    { name: 'Testing', status: 'pending', count: 1, color: 'bg-orange-500' },
    { name: 'Delivery', status: 'pending', count: 0, color: 'bg-teal-500' },
    { name: 'MA', status: 'pending', count: 8, color: 'bg-red-500' },
  ]);

  recentActivity = signal([
    { type: 'requirement', message: 'REQ-005 approved by BA', time: '2 hours ago', icon: ClipboardCheck, color: 'text-green-500', bg: 'bg-green-100' },
    { type: 'bug', message: 'BUG-003 marked as fixed', time: '4 hours ago', icon: Bug, color: 'text-orange-500', bg: 'bg-orange-100' },
    { type: 'invoice', message: 'Invoice #INV-2024-012 sent to customer', time: '6 hours ago', icon: Receipt, color: 'text-blue-500', bg: 'bg-blue-100' },
    { type: 'ma', message: 'New MA ticket #MA-045 created', time: '8 hours ago', icon: Wrench, color: 'text-purple-500', bg: 'bg-purple-100' },
    { type: 'project', message: 'Project PRJ-008 moved to Development phase', time: '1 day ago', icon: FolderKanban, color: 'text-indigo-500', bg: 'bg-indigo-100' },
  ]);

  ngOnInit() {}
}
