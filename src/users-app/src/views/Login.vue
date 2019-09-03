<template>
  <div class="wrapper fadeInDown">
    <!-- Login Form -->
    <div id="formContent">
      <h1>Login</h1>
      <form>
        <input
          type="text"
          v-model="username"
          class="fadeIn second"
          name="login"
          placeholder="login"
        />
        <input
          type="password"
          v-model="password"
          class="fadeIn third"
          name="login"
          placeholder="password"
        />
        <input type="button" class="fadeIn fourth" value="Log In" @click="doLogin" />
      </form>
    </div>
    <div v-if="error !== null" class="alert alert-danger" role="alert">
      {{error}}
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

@Component({})
export default class Login extends Vue {
  private username: string = "";
  private password: string = "";
  private error: string | null = null;

  doLogin(): void {
    const bodyFormData = new FormData();
    bodyFormData.set("username", this.username);
    bodyFormData.set("password", this.password);
    const config: AxiosRequestConfig = {
      method: "post",
      url: "http://localhost:8080/login",
      data: bodyFormData
    };
    axios(config)
      .then((response: AxiosResponse) => {
        //do login
        session.logIn(response.headers.authorization);
        this.error = null;
        this.$router.replace("/users");
      })
      .catch((error: AxiosError) => {
        if (error.response && error.response.status === 401) {
          //wrong credentials
          this.error = messages.getMessage("login_failed");
        } else {
          //generic error
          this.error = messages.getMessage("login_error");
        }
      });
  }
}
</script>

<style lang="scss" scoped>
/* BASIC */

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

/* FORM TYPOGRAPHY*/

input[type="button"],
input[type="submit"],
input[type="reset"] {
  background-color: #56baed;
  border: none;
  color: white;
  padding: 15px 80px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  text-transform: uppercase;
  font-size: 13px;
  -webkit-box-shadow: 0 10px 30px 0 rgba(95, 186, 233, 0.4);
  box-shadow: 0 10px 30px 0 rgba(95, 186, 233, 0.4);
  -webkit-border-radius: 5px 5px 5px 5px;
  border-radius: 5px 5px 5px 5px;
  margin: 5px 20px 40px 20px;
  -webkit-transition: all 0.3s ease-in-out;
  -moz-transition: all 0.3s ease-in-out;
  -ms-transition: all 0.3s ease-in-out;
  -o-transition: all 0.3s ease-in-out;
  transition: all 0.3s ease-in-out;
}

input[type="button"]:hover,
input[type="submit"]:hover,
input[type="reset"]:hover {
  background-color: #39ace7;
}

input[type="button"]:active,
input[type="submit"]:active,
input[type="reset"]:active {
  -moz-transform: scale(0.95);
  -webkit-transform: scale(0.95);
  -o-transform: scale(0.95);
  -ms-transform: scale(0.95);
  transform: scale(0.95);
}

input[type="text"],
input[type="password"] {
  background-color: #f6f6f6;
  border: none;
  color: #0d0d0d;
  padding: 15px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 5px;
  width: 85%;
  border: 2px solid #f6f6f6;
  -webkit-transition: all 0.5s ease-in-out;
  -moz-transition: all 0.5s ease-in-out;
  -ms-transition: all 0.5s ease-in-out;
  -o-transition: all 0.5s ease-in-out;
  transition: all 0.5s ease-in-out;
  -webkit-border-radius: 5px 5px 5px 5px;
  border-radius: 5px 5px 5px 5px;
}

input[type="text"],
input[type="password"]:focus {
  background-color: #fff;
  border-bottom: 2px solid #5fbae9;
}

input[type="text"],
input[type="password"]:placeholder {
  color: #cccccc;
}
</style>