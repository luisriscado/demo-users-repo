<template >
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
          class="label label-warning red"
        >A Password vai ser modificada</span>
      </div>
      <div class="row">
        <button class="btn btn-primary" @click="saveUpdate()">Guardar</button>
        <button class="btn" @click="exitEdit">Cancelar</button>
      </div>
    </form>
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
    editUser: {
      type: Object as () => User | null
    }
  }
})
export default class UserCreate extends Vue {
  private editUser: User | null = null;
  readonly USERS_URL: string = "/api/users";

  exitEdit() {
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
        me.$emit("userEdited");
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
.red {
  color: red;
}
</style>