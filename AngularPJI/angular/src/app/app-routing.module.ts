import { AddUserComponent } from './add-user/add-user.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { SearchComponent } from './search/search.component';
import { AuthGuard } from './auth.guard';
import { LogoutComponent } from './logout/logout.component';
import { AccessGuard } from './access.guard';
import { AdminGuard } from './admin.guard';
import { HomeComponent } from './home/home.component';
import { AddstoreComponent } from './addstore/addstore.component';
import { UpdateStoreComponent } from './updatestore/updatestore.component';
import { AddParameterComponent } from './add-parameter/add-parameter.component';
import { EditParameterComponent } from './edit-parameter/edit-parameter.component';
import { ViewParametersComponent } from './view-parameters/view-parameters.component' ;
import { UserListComponent } from './user-list/user-list.component';
import { ViewAllstoresComponent } from './view-allstores/view-allstores.component';
import { UpdateUserComponent } from './update-user/update-user.component';

const routes: Routes = [

  {path:'',redirectTo:'login',pathMatch:'full'},
  {path:'login',component:LoginComponent},
  {path:'search',component:SearchComponent,canActivate:[AuthGuard]},
  {path:'logout',component:LogoutComponent},
  {path:'home',canActivate:[AuthGuard],component: HomeComponent},

  {path:'addstore',component:AddstoreComponent,canActivate:[AdminGuard]},
  {path:'viewstores',canActivate:[AdminGuard],component: ViewAllstoresComponent },
  {path:'update/:number',canActivate:[AdminGuard],component: UpdateStoreComponent},

  {path:'userList',
  component: UserListComponent},
  {path:'add-user',
  component: AddUserComponent},
  // {path:'update/:id',
  //   component: UpdateUserComponent},
  {path:'update-user/:systemUserOid',
    component: UpdateUserComponent},

  // {path:'**',component: NotFoundComponent },

  {path:'searchResult',
  loadChildren: () => import('./search-result/search-result.module')
  .then(x => x.SearchResultModule),canActivate:[AuthGuard]},

  {path:'upgrade',
  loadChildren: () => import('./upgrade/upgrade.module')
  .then(x => x.UpgradeModule),canActivate:[AccessGuard]},

  {path:'addParameter',component:AddParameterComponent,canActivate:[AdminGuard]},
  {path:'editParameter/:parameter',canActivate:[AdminGuard],component: EditParameterComponent},
  {path:'viewParameters',canActivate:[AdminGuard],
  component : ViewParametersComponent},



];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
