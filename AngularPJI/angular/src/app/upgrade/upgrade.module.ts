import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UpgradeComponent } from './upgrade/upgrade.component';
import { Routes, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';

const routes: Routes = [
  {path:'', component: UpgradeComponent}
];

@NgModule({
  declarations: [UpgradeComponent],
  imports: [
    NgMultiSelectDropDownModule.forRoot(),
    CommonModule,
    RouterModule.forChild(routes),
    FormsModule
  ]
})
export class UpgradeModule { }
