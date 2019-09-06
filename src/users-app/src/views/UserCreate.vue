<template >
  <div v-if="createUser !== null" class="row create">
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
    <button class="btn cancel" @click="exitCreate">Cancelar</button>
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

@Component({
  props: {
    createUser: {
      type: Object as () => User | null
    }
  }
})
export default class UserCreate extends Vue {
  private createUser: User | null = null;
  readonly USERS_URL: string = "/api/users";

  exitCreate() {
    this.$emit("exit");
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
      me.$emit("errors", error.response.data.data);
    } else {
      //generic error
      const errors = new Array<Error>();
      let error: Error = {
        errorType: "",
        errorCode: "users_error"
      };
      errors.push(error);

      me.$emit("errors", errors);
    }
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
        me.$emit("userAdded");
        me.$emit("exit");
        me.$emit("errors", null);
      })
      .catch(this.handleReqError);
  }

  doLogout(): void {
    session.logOut();
    this.$router.replace("/login");
  }
}
</script>

<style lang="scss" scoped>
.row.create {
  margin-bottom: 20px;
  border-bottom: 2px solid #cccccc;
  .btn.cancel {
    margin-left: 20px;
  }
}
</style>