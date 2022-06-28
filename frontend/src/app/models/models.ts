export  interface MyRequest {
  date: string,
  method: string,
  url: string
}

export interface Permission{
  id: string,
  permission: string,
}

export  interface User {
  name: string,
  surname: string,
  email: string,
  password: string,
  permissions: Permission[]
}
