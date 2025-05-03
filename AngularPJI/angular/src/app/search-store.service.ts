import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, catchError, throwError } from 'rxjs';
import { Store } from './store.model';
import { UpgradeData } from './upgrade-data.model';
import { Parameter } from './parameter.model';
import { map } from 'rxjs/operators';
@Injectable({
  providedIn: 'root'
})
export class SearchStoreService {



  num !: number;
  zipcode !: number;
  city !: string;
  upgradeData !: UpgradeData;
  para !: string;
  paraDesc !: string;

  constructor(
    private http:HttpClient,
    private router:Router) { }

    // private apiUrl = 'http://localhost:9091/store';

  setData({ n, z, c }: { n: number; z: number; c: string;}){
    this.num=n;
    this.zipcode=z;
    this.city=c;
  }

  storeSearch(): Observable<any> {
    if(this.num==undefined)
      this.num=-1;
    if (this.zipcode==undefined)
      this.zipcode=-1;
    if (this.city==undefined || this.city=="")
      this.city="-1";

    console.log("service called");
    return this.http.get('http://localhost:9091/store/search',{
      headers: {
        'number':this.num.toString(),
        'city':this.city,
        'zipcode':this.zipcode.toString(),
        'Authorization':localStorage.getItem('token')!}
   });

  }

  parameter(): Observable<any>{
    return this.http.get('http://localhost:9091/store/parameter',{
      headers: {
        'Authorization':localStorage.getItem('token')!}
   });
  }

  upgrade(numb: number, para: any): Observable<any> {
    this.upgradeData=new UpgradeData();
    this.upgradeData.num=Number(numb);
    this.upgradeData.para=[];

    for (var i of para)
    {
      console.log("items",i.item_id);
      this.upgradeData.para.push(i.item_id);
    }

    console.log("inside Upgrade Service",this.upgradeData);

    return this.http.post('http://localhost:9091/store/upgrade',this.upgradeData,{
      headers: {
        'Authorization':localStorage.getItem('token')!}
   });
  }
  getAllStores(): Observable<any> {
    return this.http.get('http://localhost:9091/store/findall');
  }

  getstorebynumber(number: number): Observable<Store> {
    return this.http.get<Store>(`http://localhost:9091/store/${number}`,{

    });
  }


  getAllParameters(): Observable<any> {
    return this.http.get('http://localhost:9091/Parameters/findallParameters');
  }

addStore(data:any)
{
return this.http.post('http://localhost:9091/store/add',data);
}



//updatestore by number
updateStore(number: number, updatedStore: Store): Observable<any> {
  return this.http.put(`http://localhost:9091/store/update/${number}`, updatedStore);
}

deleteStore(number: number): Observable<any> {
    return this.http.delete(`http://localhost:9091/store/delete/${number}`, {
        // headers: {
        //   'Authorization': localStorage.getItem('token')!
        // }
      });
    }


    addParameter(parameter:any): Observable<Object> {
      return this.http.post('http://localhost:9091/Parameters/add',parameter
        // headers: {
        //   'Authorization': localStorage.getItem('token')!
        // }
      );
    }


    editParameter(parameter:any,editedparameter:Parameter): Observable<any> {
      return this.http.put(`http://localhost:9091/Parameters/update/${parameter}`,editedparameter
        // headers: {
        //   'Authorization': localStorage.getItem('token')!
        // }
      );
    }
    deleteParameter(parameter:string): Observable<any> {
      return this.http.delete(`http://localhost:9091/Parameters/delete/${parameter}`, {
        // headers: {
        //   'Authorization': localStorage.getItem('token')!
        // }
      });
    }

    getParameterbyParameter(parameter:string): Observable<Parameter> {
      return this.http.get<Parameter>(`http://localhost:9091/Parameters/${parameter}`, {
        // headers: {
        //   'Authorization': localStorage.getItem('token')!
        // }
      });
    }



}
