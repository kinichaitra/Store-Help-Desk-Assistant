import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SearchResultComponent } from './search-result/search-result.component';
import { Routes, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';


const routes: Routes = [
  {path:'', component: SearchResultComponent}
];


@NgModule({
  declarations: [
    SearchResultComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    FormsModule
  ],
})
export class SearchResultModule { }
