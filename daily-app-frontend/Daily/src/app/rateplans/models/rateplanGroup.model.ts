import { Rateplan } from './rateplan.model';

export interface RateplanGroup {
  description?: string;
  ratePlanGroup?: string;
  ratePlanGroupKey?: number;
  showFlag?: boolean;
  ratePlans?: any[];
  groupModel?: any;
}
