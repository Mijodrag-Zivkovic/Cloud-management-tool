import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {MyRequest, User} from "../models/models";

@Injectable({
  providedIn: 'root'
})
export class FetchService {

  permissions: string[];
  constructor(private httpClient: HttpClient) {
    this.permissions=[];

  }

  setPermissions(permissions: string[]){
    this.permissions=permissions;
  }

  getPermissions(){
    return this.permissions;
  }

  login(username:string, password: string):Observable<any>{
    return this.httpClient.post<any>(`${environment.loginUrl}`, {
      "email": username,
      "password": password
    });
  }

  getAllUsers():Observable<any>{
    const headers = this.getHeaders()
    return this.httpClient.get<any>(`${environment.usersUrl}/all`, { headers: headers });
  }

  getUserById(id:string):Observable<any>{
    const headers = this.getHeaders()
    return this.httpClient.get<any>(`${environment.usersUrl}/${id}`, { headers: headers });
  }

  getUserByEmail(email:string):Observable<any>{
    const headers = this.getHeaders()
    return this.httpClient.get<any>(`${environment.usersUrl}/email/${email}`, { headers: headers });
  }



  createUser(user:User):Observable<any>{
    const headers = this.getHeaders()
    return this.httpClient.post<any>(`${environment.usersUrl}`, {
      "name": user.name,
      "surname": user.surname,
      "email": user.email,
      "password": user.password,
      "permissions": user.permissions
    }, { headers: headers });
  }

  updateUser(user:User,id:String):Observable<any>{
    const headers = this.getHeaders()
    return this.httpClient.put<any>(`${environment.usersUrl}`, {
      "id": id,
      "name": user.name,
      "surname": user.surname,
      "email": user.email,
      "password": user.password,
      "permissions": user.permissions
    }, { headers: headers });
  }

  deleteUser(id:String):Observable<any>{
    const headers = this.getHeaders()
    return this.httpClient.delete<any>(`${environment.usersUrl}/${id}`, { headers: headers });
  }

  createMachine(name:string):Observable<any>{
    const headers = this.getHeaders()
    return this.httpClient.post<any>(`${environment.machinesUrl}`, {
      "name": name
    }, { headers: headers });
  }

  searchMachines(data:any):Observable<any>{
    const headers = this.getHeaders()
    return this.httpClient.get<any>(`${environment.machinesUrl}/search` ,{ headers: headers,
    params:data});
  }

  getAllPermissions():Observable<any>{
    const headers = this.getHeaders()
    return this.httpClient.get<any>(`${environment.permissionsUrl}/all`, { headers: headers });
  }

  getHeaders(){
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${localStorage.getItem("jwt")}`
    })

  }
}
