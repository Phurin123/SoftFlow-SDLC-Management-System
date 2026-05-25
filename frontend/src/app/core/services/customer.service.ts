import { Injectable, inject } from '@angular/core';
import { ApiService, PaginatedResponse } from './api.service';
import { HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Customer {
  id: string;
  customerName: string;
  taxId?: string;
  address?: string;
  contactPerson?: string;
  phone?: string;
  email?: string;
  lineId?: string;
  customerType: string;
  status: string;
  remark?: string;
  createdAt: string;
  updatedAt: string;
  projectCount?: number;
  contractCount?: number;
}

export interface CustomerRequest {
  customerName: string;
  taxId?: string;
  address?: string;
  contactPerson?: string;
  phone?: string;
  email?: string;
  lineId?: string;
  customerType: string;
  status?: string;
  remark?: string;
}

@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  private readonly api = inject(ApiService);

  getCustomers(page = 0, size = 10, search = '', sortBy = 'createdAt', sortDir = 'desc'): Observable<any> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('sortBy', sortBy)
      .set('sortDir', sortDir);
    if (search) params = params.set('search', search);
    return this.api.get<PaginatedResponse<Customer>>('customers', params);
  }

  getCustomer(id: string): Observable<any> {
    return this.api.get<Customer>(`customers/${id}`);
  }

  createCustomer(request: CustomerRequest): Observable<any> {
    return this.api.post<Customer>('customers', request);
  }

  updateCustomer(id: string, request: CustomerRequest): Observable<any> {
    return this.api.put<Customer>(`customers/${id}`, request);
  }

  deleteCustomer(id: string): Observable<any> {
    return this.api.delete<void>(`customers/${id}`);
  }
}
