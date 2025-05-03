import { Component, OnInit } from '@angular/core';
import { SearchStoreService } from '../search-store.service';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthorizationService } from '../authorization.service';
import { FormBuilder } from '@angular/forms';
import { Store } from '../store.model';
import { NgxPaginationModule } from 'ngx-pagination';
@Component({
  selector: 'app-view-allstores',
  templateUrl: './view-allstores.component.html',
  styleUrls: ['./view-allstores.component.css']
})
export class ViewAllstoresComponent implements OnInit {

 // Current page for pagination
  p: number=1;
// Function to update the current page
// pageChanged(event: any): void {
// console.log(event);
// this.p = event;
// }

  constructor(
    private service: SearchStoreService,
    private router: Router,
    private authService: AuthorizationService,
    private formBuilder:FormBuilder,
    private route: ActivatedRoute) {
      console.log("view all stores component") }

    stores !: Store[];

    ngOnInit(): void {
      this.getAllStores();
    }
    private getAllStores() {
      this.service.getAllStores().subscribe((data:Store[])=>{
        console.log(data);
        this.stores = data;
      }
    );
  }

updateStore(number:number) {
  console.log("update button clicked");
  this.router.navigate(['/update',number],{
  });
}

deletestore(number:number){
  console.log("delete button clicked");
  this.service.deleteStore(number).subscribe(data => {
    console.log(data);
    alert("Store will be deleted successfully");
    console.log("store deleted successfully");

    this.getAllStores();
},
error => {
  console.log(error);
  alert("An error occurred while deleting the store.");
}
);
}

// isNotEmpty():boolean{
//   if(this.stores.length==0)
//     return false;
//   else
//     return true;
// };




}

