export class Session {

  public logIn(session: string): void {
    window.localStorage.setItem('session', session);
  }

  public logOut(): void {
    window.localStorage.removeItem('session');
  }

  public isLogedIn(): boolean {
    const session = window.localStorage.getItem('session');
    return session !== null && session !== undefined;
  }
}

export default new Session();
