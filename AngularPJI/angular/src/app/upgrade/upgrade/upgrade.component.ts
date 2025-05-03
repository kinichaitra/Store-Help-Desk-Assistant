import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthorizationService } from '../../authorization.service';
import { SearchStoreService } from '../../search-store.service';
import { Store } from '../../store.model';
import { IDropdownSettings } from 'ng-multiselect-dropdown';



@Component({
  selector: 'app-upgrade',
  templateUrl: './upgrade.component.html',
  styleUrls: ['./upgrade.component.css']
})
export class UpgradeComponent implements OnInit {

  s!:Store;
  store !: Store[];
  store1 !: Store[];
  i!:any;
  onclick:any
   name : string =this.authService.user.name;
   accessLevel : string=this.authService.user.accessLevel;
   num !: number;
   zipcode !: number;
   city !: string;
  message="";
  message1="";
  message2="";
  disabled=false;

  parameter !: {
    item_text: string; item_id:string;
  }[];
  parameter1 !: string[];

   constructor(
    private service : SearchStoreService ,
    private authService : AuthorizationService,
    private router : Router) { }


   dropdownSettings: IDropdownSettings= {};

  ngOnInit(): void {

    this.message1='';
    this.message2='';
    this.dropdownSettings = {
      singleSelection: false,
      idField: 'item_id',
      textField: 'item_text',
      selectAllText: 'Select All',
      unSelectAllText: 'Unselect All',
      itemsShowLimit: 5,
      allowSearchFilter: true,
    };

    this.num=this.service.num;
    this.zipcode=this.service.zipcode;
    this.city=this.service.city;

    if(this.num==-1)
      this.num=null!;
    if(this.zipcode==-1)
      this.zipcode=null!;
    if(this.city=="-1")
      this.city="";

    if(!(this.city==undefined || this.city=="") && this.zipcode==undefined && this.num==undefined)
      this.ss();
  }

  onItemSelect(item: any) {
    console.log(item);
    this.message='';
    this.message1='';
    this.message2='';
  }
  onSelectAll(items: any) {
    console.log(items);
    this.message='';
    this.message1='';
    this.message2='';
  }
  onItemDeSelect(items: any) {
    console.log(items);
    this.message='';
    this.message1='';
    this.message2='';
  }


  submit(){

    if(!(this.authService.loggedInUser()))
    {
      this.router.navigate(['/logout']);
    }

    this.message1='';
    this.message2='';
    this.service.setData({ n: this.num, z: this.zipcode, c: this.city });
    if(this.num==undefined && this.zipcode==undefined && (this.city==undefined || this.city==""))
    {
      this.message="At least 1 input is required";
      this.store=[];
    }
    else{
      console.log("Number",this.num);
      console.log("City",this.city);
      console.log("Zipcode",this.zipcode);

      this.ss();
    }
  }

  ss(){
    this.message2='';
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
                                        this.s.dropdownList=[];

                                        for (var j of this.i.storeParameterList)
                                           {
                                            if (j.parameter=="ENABLE_ZO" && j.parameterValue==1){
                                              this.s.dropdownList.push({item_id:'ENABLE_ZO' ,item_text: 'Delivery through Vendor Zoot'});
                                            }
                                            if (j.parameter=="ENABLE_DLV" && j.parameterValue==1){
                                              this.s.dropdownList.push({item_id:'ENABLE_DLV' ,item_text: 'Delivery through Vendors' });
                                            }
                                            if (j.parameter=="ENABLE_COD" && j.parameterValue==1){
                                              this.s.dropdownList.push({item_id:'ENABLE_COD' ,item_text: 'Cash On Delivery for Store' });
                                            }
                                            if (j.parameter=="ENABLE_CARRY_OUT" && j.parameterValue==1){
                                              this.s.dropdownList.push({item_id:'ENABLE_CARRY_OUT' ,item_text: 'Take Away Orders for Store' });
                                            }
                                            if (j.parameter=="ENABLE_AUTOMATION" && j.parameterValue==1){
                                              this.s.dropdownList.push({item_id:'ENABLE_AUTOMATION' ,item_text: 'Automatic Delivery' });
                                            }

                                        }
                                        this.store.push(this.s);
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
                              this.message="Store Does not Found!!";
                              this.store=[];
                            }
                          });

      this.service.parameter()
      .subscribe((data: string[])=>{
        this.parameter=[];
                            console.log("List of all parameters",data);
                            this.parameter1=data;
                            for (this.i of this.parameter1)
                                    {

                                            if (this.i.parameter=="ENABLE_ZO"){
                                              this.parameter.push({item_id:'ENABLE_ZO' ,item_text: 'Delivery through Vendor Zoot'});
                                            }
                                            if (this.i.parameter=="ENABLE_DLV"){
                                              this.parameter.push({item_id:'ENABLE_DLV' ,item_text: 'Delivery through Vendors ' });
                                            }
                                            if (this.i.parameter=="ENABLE_COD"){
                                              this.parameter.push({item_id:'ENABLE_COD' ,item_text: 'Cash On Delivery for Store'});
                                            }
                                            if (this.i.parameter=="ENABLE_CARRY_OUT"){
                                              this.parameter.push({item_id:'ENABLE_CARRY_OUT' ,item_text: 'Take Away Orders for Store'});
                                            }
                                            if (this.i.parameter=="ENABLE_AUTOMATION"){
                                              this.parameter.push({item_id:'ENABLE_AUTOMATION' ,item_text: 'Automatic Delivery'});
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
                              alert("Server Down!!!");
                            }
                            else
                            {
                              this.message="Store Does not Found !!";
                              this.store=[];
                            }
                          });
    console.log("a",this.parameter1);
    console.log("b",this.parameter);
  }



  update(num:any,para:any){
    console.log("Update button clicked");
    this.message='';
    this.message1='';
    this.message2='';
    this.service.upgrade(num,para)
    .subscribe((data:any)=>{
                              console.log("Done",data)
                              this.message2="Delivery through Vendors for Store Number "+num+" has been updated";

                            },
                            (error:any)=>{
                              console.log("Error::::::",error);
                              if(error.status == 401)
                              {
                                alert("Session Expired! Login again");
                                this.router.navigate(['/logout']);
                              }
                              else if(error.status == 0)
                              {
                                alert("Server Down!!!");
                              }
                              else
                              {
                                this.message1="Delivery through Vendors is not enabled for Store Number "+num;
                                this.ss();
                              }

    });
  }


  isNotEmpty():boolean{
    if(this.store.length==0)
      return false;
    else
      return true;
  }
}
