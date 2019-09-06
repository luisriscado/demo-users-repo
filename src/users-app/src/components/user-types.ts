export class User {
  public username: string = "";
  public password: string | null = null;
  public name: string | null = null;
  public createTimestamp: Date | null = null;
  public updateTimestamp: Date | null = null;

  constructor() { }
}

export class UserData {
  public username: string = "";
  public password: string | null = null;
  public name: string | null = null;
  public createTimestamp: number | null = null;
  public updateTimestamp: number | null = null;
}

export class Error {
  public errorType: string | undefined;
  public errorCode: string = "";
}