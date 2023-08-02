export class ApiResponse<T> {
  // public body!: any;
  public status!: number; // return code
  public message!: string;
  public payload!: any;
  public statusCode!: number;
  public body: any;
}
