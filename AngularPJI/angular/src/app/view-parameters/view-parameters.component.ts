import { Component, OnInit } from '@angular/core';
import { ActivatedRoute,Router } from '@angular/router';
import { AuthorizationService } from '../authorization.service';
import { SearchStoreService } from '../search-store.service';
import { Parameter } from '../parameter.model';
import { NgxPaginationModule } from 'ngx-pagination';
import { Observable } from 'rxjs';
@Component({
  selector: 'app-view-parameters',
  templateUrl: './view-parameters.component.html',
  styleUrls: ['./view-parameters.component.css']
})
export class ViewParametersComponent implements OnInit {

   // Current page for pagination
   p: number = 1;
   // Function to update the current page
   pageChanged(event: any): void {
   console.log(event);
   this.p = event;
   }

  name : string =this.authService.user.name;
  accessLevel : string=this.authService.user.accessLevel;

  constructor(
    private authService : AuthorizationService ,
    private service : SearchStoreService,
    private router : Router,
    private route: ActivatedRoute){
      console.log("View parameters Component");
     }
     parameters !: Parameter[];
  ngOnInit(): void{
    this.getAllParameters();
   }

  private getAllParameters() {
     this.service.getAllParameters().subscribe((data:Parameter[])=> {
      console.log(data);
      this.parameters = data;
     });
  }

  deleteParameter(parameter :string)
  {
    console.log("Delete button clicked");
    this.service.deleteParameter(parameter).subscribe(data => {
      console.log(data);
      alert("Parameter will be deleted successfully");
      console.log('parameter Deleted Successfully');
      console.log(data);

      this.getAllParameters()
    });
}

  addParameter(){
    this.router.navigate(['/addParameter']);
  }

  editParameter(parameter:any){
    console.log("update button clicked");
    // console.log(para);
  this.router.navigate(['editParameter/',parameter]);
  }



checkAccessLevel():boolean
{
  return (!(this.accessLevel=="AD"))
}
}
// isNotEmpty():boolean{
//   if(this.para.length==0)
//     return false;
//   else
//     return true;
// }


