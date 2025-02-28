// axios.d.ts 
import "axios";

declare module "axios" {
  export interface InternalAxiosRequestConfig<T = any> {
    _retry?: boolean; 
  }
}

// -- IMPORTANT
// MODIFICATIONS FOR API_CLIENT AUTH FLOW
