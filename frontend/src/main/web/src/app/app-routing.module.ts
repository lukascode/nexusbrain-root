import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {NotFoundComponent} from "@app/shared/components/not-found/not-found.component";
import {MainNavComponent} from "@app/main-nav/main-nav.component";

const routes: Routes = [
  {
    path: '', component: MainNavComponent, children: [
      {
        path: 'projects',
        loadChildren: () => import('./projects/projects.module').then(m => m.ProjectsModule)
      },
      {
        path: 'workers',
        loadChildren: () => import('./workers/workers.module').then(m => m.WorkersModule)
      }
    ]
  },
  {
    path: '**',
    component: NotFoundComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
