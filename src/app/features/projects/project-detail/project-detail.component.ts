import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, ActivatedRoute } from '@angular/router';
import { LucideAngularModule, 
  ArrowLeft, FolderKanban, Users, Calendar, 
  BarChart3, Clock, Target, ClipboardCheck, 
  GitBranch, Database, FileText, Bug
} from 'lucide-angular';

@Component({
  selector: 'app-project-detail',
  standalone: true,
  imports: [CommonModule, RouterModule, LucideAngularModule],
  templateUrl: './project-detail.component.html'
})
export class ProjectDetailComponent {
  readonly ArrowLeft = ArrowLeft;

  project = signal({
    id: '1',
    projectCode: 'PRJ-001',
    projectName: 'ERP System Migration',
    customerName: 'Acme Corporation',
    sdlcStatus: 'DEVELOPMENT',
    priority: 'HIGH',
    progressPercentage: 65,
    projectManager: 'Somchai P.',
    businessAnalyst: 'Nattaporn S.',
    systemAnalyst: 'Prasert K.',
    startDate: '2024-01-15',
    plannedEndDate: '2024-12-31',
    description: 'Enterprise Resource Planning system migration from legacy platform to cloud-based solution.',
    phases: [
      { name: 'Requirements', status: 'DONE', progress: 100 },
      { name: 'Analysis', status: 'DONE', progress: 100 },
      { name: 'Specification', status: 'DONE', progress: 100 },
      { name: 'Development', status: 'IN_PROGRESS', progress: 65 },
      { name: 'Testing', status: 'NOT_STARTED', progress: 0 },
      { name: 'UAT', status: 'NOT_STARTED', progress: 0 },
      { name: 'Delivery', status: 'NOT_STARTED', progress: 0 },
    ]
  });

  tabs = signal([
    { label: 'Overview', icon: FolderKanban, route: '' },
    { label: 'Timeline', icon: Calendar, route: 'timeline' },
    { label: 'Requirements', icon: ClipboardCheck, route: 'requirements' },
    { label: 'DFD', icon: GitBranch, route: 'dfd' },
    { label: 'ER Diagram', icon: Database, route: 'er-diagram' },
    { label: 'Specifications', icon: FileText, route: 'specifications' },
  ]);

  stats = signal([
    { label: 'Total Tasks', value: '45', icon: Target },
    { label: 'Completed', value: '29', icon: BarChart3 },
    { label: 'Open Bugs', value: '3', icon: Bug },
    { label: 'Days Left', value: '120', icon: Clock },
  ]);

  constructor(private route: ActivatedRoute) {}
}
