export class AddWorkerRequest {
  fullName: string;
  email: string;

  constructor(fullName: string, email: string) {
    this.fullName = fullName;
    this.email = email;
  }
}

export class UpdateWorkerRequest {
  fullName: string;
  email: string;

  constructor(fullName: string, email: string) {
    this.fullName = fullName;
    this.email = email;
  }
}

export interface WorkerDetails {
  fullName: string;
  email: string;
  numberOfTeams: number;
}
