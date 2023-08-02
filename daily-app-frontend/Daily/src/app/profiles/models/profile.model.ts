import { Privilege } from './privileges.model';

export interface Profile {
  id?: number;
  name?: string;
  privileges?: Privilege[];
  isActive?:number;
}
