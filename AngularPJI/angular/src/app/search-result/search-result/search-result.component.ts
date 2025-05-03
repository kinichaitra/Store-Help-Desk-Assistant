import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthorizationService } from 'src/app/authorization.service';
import { SearchStoreService } from 'src/app/search-store.service';
import { Store } from 'src/app/store.model';

@Component({
  selector: 'app-search-result',
  templateUrl: './search-result.component.html',
  styleUrls: ['./search-result.component.css']
})
export class SearchResultComponent implements OnInit {

  s!:Store;
  store !: Store[];
  store1 !: Store[];
  name : string =this.authService.user.name;
  accessLevel : string=this.authService.user.accessLevel;
  message="";
  i!:any;
 OnClick: any;


  constructor(
    private service : SearchStoreService,
    private authService : AuthorizationService,
    private router : Router){
        console.log("Search Result Component");
    }

  num !: number;
  zipcode !: number;
  city !: string;
  noresult : boolean = true;
  para!:string;

  submit(){

    if(!(this.authService.loggedInUser()))
    {
      this.router.navigate(['/logout']);
    }
    this.service.setData({ n: this.num, z: this.zipcode, c: this.city});

    if(this.num==undefined && this.zipcode==undefined && (this.city==undefined || this.city==""))
    {
      this.message="At least 1 input is required";
      this.store=[];
    }
    else{
      console.log("Number",this.num);
      console.log("Zipcode",this.zipcode);
      console.log("City",this.city);
      this.ss();
    }
  }
  reset() {
    this.num = 0;
    this.city = '';
    this.zipcode = 0;
    this.message = '';
    this.store = [];
  }

  ngOnInit(): void {
    this.num=this.service.num;
    this.zipcode=this.service.zipcode;
    this.city=this.service.city;

    if(this.num==-1)
      this.num=null!;
    if(this.zipcode==-1)
      this.zipcode=null!;
    if(this.city=="-1")
      this.city="";
    this.ss();
  }

  ss(){
      this.service.storeSearch()
      .subscribe((data: Store[])=>{
        this.store=[];
                          console.log(data);
                          this.message="";
                          this.store1=data;

                                    for (this.i of this.store1)
                                    {
                                        this.s=new Store;
                                        this.s.city=this.i.city;
                                        this.s.number=this.i.number;
                                        this.s.zipcode=this.i.zipcode;
                                        this.s.storeParameterList=[];
                                        {
                                        for (var j of this.i.storeParameterList)
                                        {
                                            if (j.parameter=="ENABLE_ZO" && j.parameterValue==1){
                                              this.s.storeParameterList.push("Enable Delivery through Vendor Zoot");}
                                            if (j.parameter=="ENABLE_DLV" && j.parameterValue==1)
                                              this.s.storeParameterList.push("Enable Delivery through Vendors ");
                                              if (j.parameter=="ENABLE_COD" && j.parameterValue==1){
                                                this.s.storeParameterList.push("Enable Cash On Delivery for Store");}
                                              if (j.parameter=="ENABLE_CARRY_OUT" && j.parameterValue==1)
                                                this.s.storeParameterList.push("Enable Take Away Orders for Store ");
                                                if (j.parameter=="ENABLE_AUTOMATION" && j.parameterValue==1)
                                                this.s.storeParameterList.push("Enable Automatic Delivery ");
                                        }
                                        this.store.push(this.s);
                                      }
                                    }
                          },
                          (error:any)=>{
                            console.log(error);
                            if(error.status == 401)
                            {
                              alert("Session Expired!!! Login again");
                              this.router.navigate(['/logout']);
                            }
                            else if(error.status == 0)
                            {
                              alert("Server Down!");
                            }
                            else
                            {
                              this.message="Store does not Found";
                              this.store=[];
                            }

                          });
  }

  checkAccessLevel():boolean
  {
    return (!(this.accessLevel=="MN"))
  }
  isNotEmpty():boolean{
    if(this.store.length==0)
      return false;
    else
      return true;
  }

}


