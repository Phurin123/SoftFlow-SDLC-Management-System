import { Component, Input, Output, EventEmitter, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { LucideAngularModule, Search, ChevronLeft, ChevronRight, ChevronsLeft, ChevronsRight, ArrowUpDown, ArrowUp, ArrowDown, Eye, Pencil, Trash2 } from 'lucide-angular';

export interface Column {
  key: string;
  label: string;
  sortable?: boolean;
  width?: string;
  type?: 'text' | 'badge' | 'date' | 'actions' | 'custom';
  badgeClass?: (value: any) => string;
  format?: (value: any, row: any) => string;
}

export interface TableAction {
  label: string;
  icon?: any;
  class?: string;
  action: (row: any) => void;
}

@Component({
  selector: 'app-data-table',
  standalone: true,
  imports: [CommonModule, FormsModule, LucideAngularModule],
  templateUrl: './data-table.component.html'
})
export class DataTableComponent {
  readonly Search = Search;
  readonly ChevronLeft = ChevronLeft;
  readonly ChevronRight = ChevronRight;
  readonly ChevronsLeft = ChevronsLeft;
  readonly ChevronsRight = ChevronsRight;
  readonly ArrowUpDown = ArrowUpDown;
  readonly ArrowUp = ArrowUp;
  readonly ArrowDown = ArrowDown;
  readonly Eye = Eye;
  readonly Pencil = Pencil;
  readonly Trash2 = Trash2;

  @Input() columns: Column[] = [];
  @Input() actions: TableAction[] = [];
  @Input() title = '';
  @Input() subtitle = '';
  @Input() searchPlaceholder = 'Search...';
  @Input() emptyMessage = 'No data found';
  @Input() loading = false;

  @Output() search = new EventEmitter<string>();
  @Output() sort = new EventEmitter<{ column: string; direction: 'asc' | 'desc' }>();
  @Output() pageChange = new EventEmitter<number>();
  @Output() pageSizeChange = new EventEmitter<number>();
  @Output() rowClick = new EventEmitter<any>();
  @Output() view = new EventEmitter<any>();
  @Output() edit = new EventEmitter<any>();
  @Output() delete = new EventEmitter<any>();

  // Signals for state management
  rawData = signal<any[]>([]);
  filteredData = signal<any[]>([]);
  searchQuery = signal('');
  currentPage = signal(0);
  pageSize = signal(10);
  sortColumn = signal<string>('');
  sortDirection = signal<'asc' | 'desc'>('asc');
  totalElements = signal(0);

  // Computed values
  totalPages = computed(() => Math.ceil(this.totalElements() / this.pageSize()));
  
  paginatedData = computed(() => {
    const start = this.currentPage() * this.pageSize();
    const end = start + this.pageSize();
    return this.filteredData().slice(start, end);
  });

  startIndex = computed(() => this.filteredData().length === 0 ? 0 : this.currentPage() * this.pageSize() + 1);
  endIndex = computed(() => Math.min((this.currentPage() + 1) * this.pageSize(), this.totalElements()));

  // Sort indicator
  getSortIcon(column: Column): any {
    if (this.sortColumn() !== column.key) return this.ArrowUpDown;
    return this.sortDirection() === 'asc' ? this.ArrowUp : this.ArrowDown;
  }

  // Set data from parent
  setData(data: any[], total?: number) {
    this.rawData.set(data);
    this.filteredData.set(data);
    this.totalElements.set(total ?? data.length);
  }

  // Handle search input
  onSearch(event: Event) {
    const value = (event.target as HTMLInputElement).value;
    this.searchQuery.set(value);
    this.currentPage.set(0);
    
    if (value) {
      const lower = value.toLowerCase();
      this.filteredData.set(
        this.rawData().filter(row => 
          this.columns.some(col => {
            if (col.type === 'actions') return false;
            const cellValue = this.getCellValue(row, col);
            return cellValue?.toString().toLowerCase().includes(lower);
          })
        )
      );
    } else {
      this.filteredData.set(this.rawData());
    }
    this.totalElements.set(this.filteredData().length);
    this.search.emit(value);
  }

  // Handle sort
  onSort(column: Column) {
    if (!column.sortable) return;
    
    if (this.sortColumn() === column.key) {
      this.sortDirection.update(d => d === 'asc' ? 'desc' : 'asc');
    } else {
      this.sortColumn.set(column.key);
      this.sortDirection.set('asc');
    }
    
    // Local sorting
    const dir = this.sortDirection();
    const key = column.key;
    this.filteredData.update(data => [...data].sort((a, b) => {
      const aVal = a[key] ?? '';
      const bVal = b[key] ?? '';
      if (aVal < bVal) return dir === 'asc' ? -1 : 1;
      if (aVal > bVal) return dir === 'asc' ? 1 : -1;
      return 0;
    }));
    
    this.sort.emit({ column: key, direction: dir });
  }

  // Pagination
  goToPage(page: number) {
    if (page < 0 || page >= this.totalPages()) return;
    this.currentPage.set(page);
    this.pageChange.emit(page);
  }

  goToFirst() { this.goToPage(0); }
  goToPrevious() { this.goToPage(this.currentPage() - 1); }
  goToNext() { this.goToPage(this.currentPage() + 1); }
  goToLast() { this.goToPage(this.totalPages() - 1); }

  onPageSizeChange(event: Event) {
    const size = parseInt((event.target as HTMLSelectElement).value);
    this.pageSize.set(size);
    this.currentPage.set(0);
    this.pageSizeChange.emit(size);
  }

  // Cell value helpers
  getCellValue(row: any, column: Column): any {
    if (column.format) {
      return column.format(row[column.key], row);
    }
    return row[column.key];
  }

  getBadgeClass(column: Column, value: any): string {
    if (column.badgeClass) {
      return column.badgeClass(value);
    }
    // Default badge colors
    const statusColors: Record<string, string> = {
      'ACTIVE': 'bg-green-100 text-green-800',
      'INACTIVE': 'bg-gray-100 text-gray-800',
      'DRAFT': 'bg-yellow-100 text-yellow-800',
      'APPROVED': 'bg-green-100 text-green-800',
      'CONFIRMED': 'bg-green-100 text-green-800',
      'PENDING': 'bg-yellow-100 text-yellow-800',
      'REJECTED': 'bg-red-100 text-red-800',
      'DONE': 'bg-green-100 text-green-800',
      'IN_PROGRESS': 'bg-blue-100 text-blue-800',
      'NOT_STARTED': 'bg-gray-100 text-gray-800',
      'DELAYED': 'bg-red-100 text-red-800',
    };
    const color = statusColors[value?.toString().toUpperCase()] || 'bg-gray-100 text-gray-800';
    return `inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium ${color}`;
  }

  canGoPrevious = () => this.currentPage() > 0;
  canGoNext = () => this.currentPage() < this.totalPages() - 1;

  get pages(): number[] {
    const total = this.totalPages();
    const current = this.currentPage();
    const pages: number[] = [];
    
    let start = Math.max(0, current - 2);
    let end = Math.min(total - 1, start + 4);
    
    if (end - start < 4) {
      start = Math.max(0, end - 4);
    }
    
    for (let i = start; i <= end; i++) {
      pages.push(i);
    }
    return pages;
  }
}
