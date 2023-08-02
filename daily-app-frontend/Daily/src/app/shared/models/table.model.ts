export class TableModel {
  cols!: Array<any>;
  data!: Array<any>;
  extracols!: Array<any>;
  pagination!: boolean;
  lockFlag!: boolean;
  editFlag!: boolean;
  deleteFlag!: boolean;
  name!: String;
  hasSubmit!: boolean;
  globalFilterFields!: string[];
}
