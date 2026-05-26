import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs/operators';
import {
  LucideAngularModule,
  LayoutDashboard,
  Users,
  FolderKanban,
  FileCheck,
  Settings,
  Bell,
  Search,
  Menu,
  X,
  ChevronDown,
  ChevronLeft,
  ChevronRight,
  LogOut,
  User,
  HelpCircle,
  Shield,
  GitBranch,
  BookOpen,
  TestTube,
  Bug,
  Truck,
  Receipt,
  Wrench,
  ClipboardCheck,
  BarChart3,
  Workflow,
  Database,
  FileText,
  GitCommit,
  Layers,
} from 'lucide-angular';

interface MenuItem {
  label: string;
  icon: any;
  route?: string;
  children?: MenuItem[];
  badge?: number;
}

@Component({
  selector: 'app-dashboard-layout',
  standalone: true,
  imports: [CommonModule, RouterModule, LucideAngularModule],
  templateUrl: './dashboard-layout.component.html',
})
export class DashboardLayoutComponent {
  readonly LayoutDashboard = LayoutDashboard;
  readonly Users = Users;
  readonly FolderKanban = FolderKanban;
  readonly FileCheck = FileCheck;
  readonly Bell = Bell;
  readonly Search = Search;
  readonly Menu = Menu;
  readonly X = X;
  readonly ChevronDown = ChevronDown;
  readonly ChevronLeft = ChevronLeft;
  readonly ChevronRight = ChevronRight;
  readonly LogOut = LogOut;
  readonly User = User;
  readonly Settings = Settings;
  readonly HelpCircle = HelpCircle;
  readonly Layers = Layers;

  sidebarCollapsed = signal(false);
  mobileSidebarOpen = signal(false);
  expandedMenus = signal<Set<string>>(new Set());
  currentRoute = signal('');
  userMenuOpen = signal(false);
  notificationCount = signal(5);

  mainMenu: MenuItem[] = [
    { label: 'Dashboard', icon: LayoutDashboard, route: '/dashboard' },
    { label: 'Customers', icon: Users, route: '/customers' },
    {
      label: 'Projects',
      icon: FolderKanban,
      children: [
        { label: 'All Projects', icon: ChevronRight, route: '/projects' },
        { label: 'Timeline', icon: GitCommit, route: '/projects/timeline' },
        { label: 'Requirements', icon: ClipboardCheck, route: '/projects/requirements' },
        { label: 'DFD Designer', icon: Workflow, route: '/projects/dfd' },
        { label: 'ER Diagram', icon: Database, route: '/projects/er-diagram' },
        { label: 'Specifications', icon: FileText, route: '/projects/specifications' },
      ],
    },
    { label: 'Approval Center', icon: FileCheck, route: '/approval-center', badge: 3 },
  ];

  sdlcMenu: MenuItem[] = [
    { label: 'Analysis (DFD)', icon: GitBranch, route: '/projects/dfd' },
    { label: 'Design (ER)', icon: Database, route: '/projects/er-diagram' },
    { label: 'Specifications', icon: BookOpen, route: '/projects/specifications' },
    { label: 'Test Management', icon: TestTube, route: '#' },
    { label: 'Bug Tracking', icon: Bug, route: '#' },
    { label: 'Delivery', icon: Truck, route: '#' },
    { label: 'Invoices', icon: Receipt, route: '#' },
    { label: 'MA Support', icon: Wrench, route: '#' },
  ];

  bottomMenu: MenuItem[] = [
    { label: 'Settings', icon: Settings, route: '#' },
    { label: 'Help', icon: HelpCircle, route: '#' },
  ];

  constructor(private router: Router) {
    this.router.events
      .pipe(filter((event) => event instanceof NavigationEnd))
      .subscribe((event: any) => {
        this.currentRoute.set(event.urlAfterRedirects);
      });
  }

  toggleSidebar() {
    this.sidebarCollapsed.update((v) => !v);
  }

  toggleMobileSidebar() {
    this.mobileSidebarOpen.update((v) => !v);
  }

  toggleMenu(label: string) {
    this.expandedMenus.update((set) => {
      const newSet = new Set(set);
      if (newSet.has(label)) {
        newSet.delete(label);
      } else {
        newSet.add(label);
      }
      return newSet;
    });
  }

  isMenuExpanded(label: string): boolean {
    return this.expandedMenus().has(label);
  }

  isActive(route: string): boolean {
    if (!route || route === '#') return false;
    return this.currentRoute().startsWith(route);
  }

  toggleUserMenu() {
    this.userMenuOpen.update((v) => !v);
  }

  logout() {
    console.log('Logout clicked');
  }
}
