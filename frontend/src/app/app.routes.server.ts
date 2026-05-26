
import { ServerRoute, RenderMode } from '@angular/ssr';

export const serverRoutes: ServerRoute[] = [
  { path: 'dashboard', renderMode: RenderMode.Server },
  { path: 'customers', renderMode: RenderMode.Server },
  { path: 'projects', renderMode: RenderMode.Server },
  { path: 'projects/:id', renderMode: RenderMode.Server },
  { path: 'projects/:id/timeline', renderMode: RenderMode.Server },
  { path: 'projects/:id/requirements', renderMode: RenderMode.Server },
  { path: 'projects/:id/dfd', renderMode: RenderMode.Server },
  { path: 'projects/:id/er-diagram', renderMode: RenderMode.Server },
  { path: 'projects/:id/specifications', renderMode: RenderMode.Server },
  { path: 'approval-center', renderMode: RenderMode.Server },
  { path: '**', renderMode: RenderMode.Client }  // หน้าไม่เจอ ให้ render ที่ client
];