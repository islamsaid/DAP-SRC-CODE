export interface Footprint {
  pageName: string;
  requestId: string; //traceId
  objectIdentifier?:any;
  actionId: number;
  requestBody: any;
  responseBody: any;
  oldValue: any;
  newValue: any;
}
