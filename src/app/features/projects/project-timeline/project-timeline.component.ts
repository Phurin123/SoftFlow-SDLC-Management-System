import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { LucideAngularModule, Calendar, CheckCircle2, Circle, Clock } from 'lucide-angular';

@Component({
  selector: 'app-project-timeline',
  standalone: true,
  imports: [CommonModule, RouterModule, LucideAngularModule],
  template: `
    <div class="card animate-fade-in">
      <div class="card-header">
        <div>
          <h2 class="text-lg font-semibold text-gray-900">Project Timeline</h2>
          <p class="text-sm text-gray-500 mt-0.5">Phase and milestone tracking</p>
        </div>
      </div>
      <div class="p-6">
        <div class="relative">
          <div class="absolute left-6 top-0 bottom-0 w-0.5 bg-gray-200"></div>
          <div class="space-y-6">
            <div class="relative flex gap-4">
              <div class="flex-shrink-0 w-12 h-12 rounded-full bg-green-500 flex items-center justify-center z-10">
                <lucide-icon name="check-circle-2" class="w-6 h-6 text-white"></lucide-icon>
              </div>
              <div class="flex-1 pb-6 border-b border-gray-100">
                <h3 class="font-semibold text-gray-900">Phase 1: Requirements</h3>
                <p class="text-sm text-gray-500">Jan 15 - Feb 28, 2024</p>
                <span class="inline-flex items-center px-2 py-0.5 text-xs font-medium bg-green-100 text-green-800 rounded-full mt-2">Completed</span>
              </div>
            </div>
            <div class="relative flex gap-4">
              <div class="flex-shrink-0 w-12 h-12 rounded-full bg-green-500 flex items-center justify-center z-10">
                <lucide-icon name="check-circle-2" class="w-6 h-6 text-white"></lucide-icon>
              </div>
              <div class="flex-1 pb-6 border-b border-gray-100">
                <h3 class="font-semibold text-gray-900">Phase 2: Analysis</h3>
                <p class="text-sm text-gray-500">Mar 1 - Apr 15, 2024</p>
                <span class="inline-flex items-center px-2 py-0.5 text-xs font-medium bg-green-100 text-green-800 rounded-full mt-2">Completed</span>
              </div>
            </div>
            <div class="relative flex gap-4">
              <div class="flex-shrink-0 w-12 h-12 rounded-full bg-primary-500 flex items-center justify-center z-10">
                <lucide-icon name="clock" class="w-6 h-6 text-white"></lucide-icon>
              </div>
              <div class="flex-1 pb-6 border-b border-gray-100">
                <h3 class="font-semibold text-gray-900">Phase 3: Development</h3>
                <p class="text-sm text-gray-500">Apr 16 - Sep 30, 2024</p>
                <div class="mt-2">
                  <div class="flex items-center justify-between text-sm mb-1">
                    <span class="text-gray-600">Progress</span>
                    <span class="font-medium">65%</span>
                  </div>
                  <div class="w-full bg-gray-200 rounded-full h-2">
                    <div class="bg-primary-600 h-2 rounded-full" style="width: 65%"></div>
                  </div>
                </div>
              </div>
            </div>
            <div class="relative flex gap-4">
              <div class="flex-shrink-0 w-12 h-12 rounded-full bg-gray-200 flex items-center justify-center z-10">
                <lucide-icon name="circle" class="w-6 h-6 text-gray-400"></lucide-icon>
              </div>
              <div class="flex-1">
                <h3 class="font-semibold text-gray-900">Phase 4: Testing</h3>
                <p class="text-sm text-gray-500">Oct 1 - Nov 15, 2024</p>
                <span class="inline-flex items-center px-2 py-0.5 text-xs font-medium bg-gray-100 text-gray-600 rounded-full mt-2">Not Started</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  `
})
export class ProjectTimelineComponent {}
