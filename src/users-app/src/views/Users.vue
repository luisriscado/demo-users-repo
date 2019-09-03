<template>
  <div class="users container-fluid">
    <div class="row">
      <h1>Users</h1>
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
      <div v-if="editUser !== null" class="col"></div>
    </div>
    <div class="row">
      <button class="btn btn-success" @click="create()">+ Criar</button>
    </div>
    <div v-if="createUser !== null" class="row">
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
              <input type="text" v-bind="createUser.username" />
            </td>
            <td>
              <input type="text" v-bind="createUser.name" />
            </td>
            <td>
              <input type="password" v-bind="createUser.password" />
            </td>
            <td>
              <button class="btn btn-success" @click="saveCreate()">Guardar</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <div v-if="errors !== null" class="row">
      <div
        v-for="error in errors"
        v-bind:key="error.errorCode"
        class="alert alert-danger"
        role="alert"
      >{{messages.getMessage(error.errorCode)}}</div>
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
  readonly HANDLE_REQ_ERROR = (error: AxiosError) => {
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
  };
  list(): void {
    const config: AxiosRequestConfig = {
      method: "get",
      headers: { Authorization: session.getToken() },
      url: this.USERS_URL
    };
    axios(config)
      .then((response: AxiosResponse) => {
        this.users = response.data;
      })
      .catch(this.HANDLE_REQ_ERROR);
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
      })
      .catch(this.HANDLE_REQ_ERROR);
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
      })
      .catch(this.HANDLE_REQ_ERROR);
  }
  edit(user: User): void {
    this.editUser = user;
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

    return date.toString();
  }
}
</script>

<style lang="scss" scoped>
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

#formFooter {
  background-color: #f6f6f6;
  border-top: 1px solid #dce8f1;
  padding: 25px;
  text-align: center;
  -webkit-border-radius: 0 0 10px 10px;
  border-radius: 0 0 10px 10px;
}

/* TABS */

h2.inactive {
  color: #cccccc;
}

h2.active {
  color: #0d0d0d;
  border-bottom: 2px solid #5fbae9;
}
</style>