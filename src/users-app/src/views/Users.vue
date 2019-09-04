<template>
  <div class="users">
    <div class="row">
      <div class="col">
        <h1>Utilizadores</h1>
      </div>
      <div class="col-sm-4 buttons">
        <button class="btn btn-success" @click="createUser === null && create()">+ Novo Utilizador</button>
        <button type="button" class="btn btn-danger" @click="doLogout">Logout</button>
      </div>
    </div>
    <div class="row">
      <div class="col">
        <table class="table table-striped">
          <thead>
            <tr>
              <td>Username</td>
              <td>Nome</td>
              <td>Criado</td>
              <td>Atualizado</td>
              <td>Ações</td>
            </tr>
          </thead>
          <tbody>
            <tr v-for="user in users" v-bind:key="user.username">
              <td>{{user.username}}</td>
              <td>{{user.name}}</td>
              <td>{{printDate(user.createTimestamp)}}</td>
              <td>{{printDate(user.updateTimestamp)}}</td>
              <td>
                <button class="btn btn-info" @click="edit(user)">Editar</button>
                <button class="btn btn-danger" @click="deleteUser(user.username)">Eliminar</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      <div v-if="editUser !== null" class="col-md-4">
        <form>
          <h2>Editar {{editUser.username}}</h2>
          <div class="form-group">
            <label for="name">Nome:</label>
            <input id="name" type="name" class="form-control" v-model="editUser.name" />
          </div>
          <div class="form-group">
            <label for="pwd">Password:</label>
            <input id="pwd" type="password" class="form-control" v-model="editUser.password" />
            <span
              v-if="editUser.password !== null && editUser.password !== undefined && editUser.password.trim().length>0"
              class="label label-warning"
            >A Password vai ser modificada</span>
          </div>
          <div class="row">
            <button class="btn btn-primary" @click="saveUpdate()">Guardar</button>
            <button class="btn" @click="exitEdit">Cancelar</button>
          </div>
        </form>
      </div>
    </div>
    <div v-if="createUser !== null" class="row">
      <h2>Novo Utilizador</h2>
      <table class="table table-striped">
        <thead>
          <tr>
            <td>Username</td>
            <td>Nome</td>
            <td>Password</td>
            <td>Guardar</td>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>
              <input type="text" v-model="createUser.username" />
            </td>
            <td>
              <input type="text" v-model="createUser.name" />
            </td>
            <td>
              <input type="password" v-model="createUser.password" />
            </td>
            <td>
              <button class="btn btn-success" @click="saveCreate()">Guardar</button>
            </td>
          </tr>
        </tbody>
      </table>
      <button class="btn" @click="exitCreate">Cancelar</button>
    </div>
    <div v-if="errors !== null" class="row">
      <div class="alert alert-danger errors" role="alert">
        <div v-for="error in errors" v-bind:key="error.errorCode">{{getMessage(error.errorCode)}}</div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import session from "@/components/session";
import messages from "@/components/messages";
import axios, {
  AxiosRequestConfig,
  AxiosResponse,
  AxiosError,
  AxiosInstance,
  AxiosAdapter,
  Cancel,
  CancelToken,
  CancelTokenSource,
  Canceler
} from "axios";

class User {
  public username: string = "";
  public password: string | null = null;
  public name: string | null = null;
  public createTimestamp: Date | null = null;
  public updateTimestamp: Date | null = null;

  constructor() {}
}

class UserData {
  public username: string = "";
  public password: string | null = null;
  public name: string | null = null;
  public createTimestamp: number | null = null;
  public updateTimestamp: number | null = null;
}

class Error {
  public errorType: string | undefined;
  public errorCode: string = "";
}

@Component({})
export default class Users extends Vue {
  private errors: Array<Error> | null = null;
  private users: Array<User> | null = null;
  private editUser: User | null = null;
  private createUser: User | null = null;
  readonly USERS_URL: string = "/api/users";

  exitCreate() {
    this.errors = null;
    this.createUser = null;
  }

  exitEdit() {
    this.errors = null;
    this.editUser = null;
  }

  handleReqError(error: AxiosError): void {
    var me = this;
    if (error.response && error.response.status === 401) {
      //logout
      me.doLogout();
    } else if (
      error.response &&
      error.response.status === 400 &&
      error.response.data &&
      error.response.data.type == "validation"
    ) {
      //display errors
      me.errors = error.response.data.data;
    } else {
      //generic error
      me.errors = new Array<Error>();
      let error: Error = {
        errorType: "",
        errorCode: "users_error"
      };
      me.errors.push(error);
    }
  }

  list(): void {
    const me = this;
    const config: AxiosRequestConfig = {
      method: "get",
      headers: { Authorization: session.getToken() },
      url: this.USERS_URL
    };
    axios(config)
      .then((response: AxiosResponse) => {
        if (
          !response.data ||
          !response.data.length ||
          !(response.data.length > 0)
        ) {
          me.users = [];
        } else {
          let users: Array<User> = (me.users = []);

          response.data.forEach((u: UserData) => {
            let newUser = new User();
            newUser.username = u.username;
            newUser.password = u.password;
            newUser.name = u.name;
            newUser.createTimestamp = u.createTimestamp
              ? new Date(u.createTimestamp)
              : null;
            newUser.updateTimestamp = u.updateTimestamp
              ? new Date(u.updateTimestamp)
              : null;

            users.push(newUser);
          });
        }
        me.errors = null;
      })
      .catch(this.handleReqError);
  }

  getMessage(err: string): any {
    return messages.getMessage(err);
  }

  mounted() {
    this.list();
  }
  saveCreate(): void {
    const me = this;
    const config: AxiosRequestConfig = {
      method: "post",
      headers: { Authorization: session.getToken() },
      url: this.USERS_URL,
      data: this.createUser
    };
    axios(config)
      .then((response: AxiosResponse) => {
        me.list();
        me.createUser = null;
        me.errors = null;
      })
      .catch(this.handleReqError);
  }

  saveUpdate(): void {
    const me = this;
    if (me.editUser == null) {
      return;
    }

    const config: AxiosRequestConfig = {
      method: "put",
      headers: { Authorization: session.getToken() },
      url: me.USERS_URL + "/" + me.editUser.username,
      data: me.editUser
    };
    axios(config)
      .then((response: AxiosResponse) => {
        me.list();
        me.editUser = null;
        me.errors = null;
      })
      .catch(this.handleReqError);
  }
  deleteUser(username: String): void {
    const me = this;
    const config: AxiosRequestConfig = {
      method: "delete",
      headers: { Authorization: session.getToken() },
      url: this.USERS_URL + "/" + username
    };
    axios
      .delete(this.USERS_URL + "/" + username, {
        headers: { Authorization: session.getToken() }
      })
      .then((response: AxiosResponse) => {
        me.list();
        me.errors = null;
      })
      .catch(this.handleReqError);
  }
  edit(user: User): void {
    this.editUser = new User();
    this.editUser.username = user.username;
    this.editUser.name = user.name;
  }
  create(): void {
    this.createUser = new User();
  }
  doLogout(): void {
    session.logOut();
    this.$router.replace("/login");
  }

  printDate(date: Date | null | undefined): string {
    if (!date) {
      return "";
    }

    return date.toLocaleString();
  }
}
</script>

<style lang="scss" scoped>
.buttons button {
  margin-left: 20px;
  margin-bottom: 10px;
}

.errors {
  width: 100%;
}
/* BASIC */
.users {
  margin-right: 10px;
  margin-left: 10px;
  margin-top: 10px;
}

html {
  background-color: #56baed;
}

body {
  font-family: "Poppins", sans-serif;
  height: 100vh;
}

a {
  color: #92badd;
  display: inline-block;
  text-decoration: none;
  font-weight: 400;
}

h2 {
  text-align: center;
  font-size: 16px;
  font-weight: 600;
  text-transform: uppercase;
  display: inline-block;
  margin: 40px 8px 10px 8px;
  color: #cccccc;
}

/* STRUCTURE */

.wrapper {
  display: flex;
  align-items: center;
  flex-direction: column;
  justify-content: center;
  width: 100%;
  min-height: 100%;
  padding: 20px;
}

#formContent {
  -webkit-border-radius: 10px 10px 10px 10px;
  border-radius: 10px 10px 10px 10px;
  background: #fff;
  padding: 30px;
  width: 90%;
  max-width: 450px;
  position: relative;
  padding: 0px;
  -webkit-box-shadow: 0 30px 60px 0 rgba(0, 0, 0, 0.3);
  box-shadow: 0 30px 60px 0 rgba(0, 0, 0, 0.3);
  text-align: center;
}
</style>