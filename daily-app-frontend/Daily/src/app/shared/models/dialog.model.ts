export interface DialogData {
    header: string,
    name:string,
    icon: string,
    inputs: DialogInputs[],
    buttons: DialogButtons[],
    id?: number;
    view?:boolean;
}

interface DialogInputs {
  label: string;
  element: string;
  type: string;
  fieldId: string;
  required:boolean
}

interface DialogButtons {
    label: string,
    type: string
}