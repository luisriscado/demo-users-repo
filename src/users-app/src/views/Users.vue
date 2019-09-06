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
    <UserCreate
      v-bind:createUser="createUser"
      v-on:exit="exitCreate"
      v-on:userAdded="list"
      v-on:errors="showErrors"
    ></UserCreate>
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
      <UserEdit
      v-bind:editUser="editUser"
      v-on:exit="exitEdit"
      v-on:userEdited="list"
      v-on:errors="showErrors"
    ></UserEdit>
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

import { User, UserData, Error } from "@/components/user-types";
import UserCreate from "./UserCreate.vue";
import UserEdit from "./UserEdit.vue";

@Component({
  components: {
    UserCreate,
    UserEdit
  }
})
export default class Users extends Vue {
  private errors: Array<Error> | null = null;
  private users: Array<User> | null = null;
  private editUser: User | null = null;
  private createUser: User | null = null;
  readonly USERS_URL: string = "/api/users";

  showErrors(errors: Array<Error> | null) {
    this.errors = errors;
  }

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

.row.create {
  margin-bottom: 20px;
  border-bottom: 2px solid #cccccc;
  .btn.cancel {
    margin-left: 20px;
  }
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