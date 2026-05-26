import { ApplicationConfig, importProvidersFrom } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideClientHydration, withEventReplay } from '@angular/platform-browser';
import { provideHttpClient } from '@angular/common/http';
import { provideAnimations } from '@angular/platform-browser/animations';
import { LucideAngularModule, 
  LayoutDashboard, Users, FolderKanban, CheckSquare, Layers,
  Calendar, FileText, Network, Database, FileCode
} from 'lucide-angular';

import { routes } from './app.routes';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideClientHydration(withEventReplay()),
    provideHttpClient(),
    provideAnimations(),
    importProvidersFrom(
      LucideAngularModule.pick({
        dashboard: LayoutDashboard,
        customers: Users,
        projects: FolderKanban,
        'approval-center': CheckSquare,
        layers: Layers,
        timeline: Calendar,
        requirements: FileText,
        dfd: Network,
        'er-diagram': Database,
        specifications: FileCode
      })
    )
  ]
};