export class Messages {
  private messages: Map<string, string>;

  constructor() {
    this.messages = new Map<string, string>();
    this.messages.set("login_error", "Erro Desconhecido");
    this.messages.set("login_failed", "Credenciais Erradas");
    this.messages.set("users_error", "Erro Desconhecido");
    this.messages.set("username_already_exists", "Utilizador já existe");
    this.messages.set("name_mandatory", "O campo Nome é obrigatório.");
    this.messages.set("username_mandatory", "O Campo Username é obrigatório");
    this.messages.set("password_mandatory", "O campo password é obrigatório");
    this.messages.set("invalid_strengh_password", "A password não tem complexidade suficiente");
    this.messages.set("user_dos_not_exist", "O utilizador não existe");
    this.messages.set("user_delete_not_allowed", "Não é possivel eliminar o utilizador");
  }

  public getMessage(key: string, def?: string): string | null {


    return this.messages.get(key) || this.messages.get(def || "") || def || key;
  }
}

export default new Messages();
