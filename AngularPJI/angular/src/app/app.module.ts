import { NgModule } from '@angular/core';
import { BrowserModule, Title } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgxPaginationModule } from 'ngx-pagination';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { SearchComponent } from './search/search.component';
import { from } from 'rxjs';
import { LogoutComponent } from './logout/logout.component';
import { HomeComponent } from './home/home.component';
import { MatDialogModule } from '@angular/material/dialog';
import {MatButtonModule,MatSnackBarModule} from '@angular/material';
import { AddstoreComponent } from './addstore/addstore.component';
import { UpdateStoreComponent } from './updatestore/updatestore.component';
import { AddParameterComponent } from './add-parameter/add-parameter.component';
import { EditParameterComponent } from './edit-parameter/edit-parameter.component';
import { ViewParametersComponent } from './view-parameters/view-parameters.component';
import { UserListComponent } from './user-list/user-list.component';
import { ViewAllstoresComponent } from './view-allstores/view-allstores.component';
import { AddUserComponent } from './add-user/add-user.component';
import { UpdateUserComponent } from './update-user/update-user.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SearchComponent,
    LogoutComponent,
    HomeComponent,
    AddstoreComponent,
    UpdateStoreComponent,
    AddParameterComponent,
    EditParameterComponent,
    ViewParametersComponent,
    UserListComponent,
    ViewAllstoresComponent,
    AddUserComponent,
    UpdateUserComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatButtonModule,MatSnackBarModule,
    NgxPaginationModule,


  ],
  providers: [Title],
  bootstrap: [AppComponent]
})
export class AppModule { }
