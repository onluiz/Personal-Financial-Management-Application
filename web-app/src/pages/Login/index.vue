<template>
  <div id="login">
    <v-layout id="card" row wrap>
      <v-flex 
        xs10 offset-xs1 
        sm6 offset-sm3
        md6 offset-md3
        lg4 offset-lg4
        ll2 offset-ll5>
        <v-card color="blue-grey lighten-5">
          <v-card-title primary-title class="center">
            <div>
              <h3 class="headline mb-0 text">Seu Financeiro</h3>
            </div>            
          </v-card-title>
          <v-form id="form" ref="form" v-model="valid" @keyup.native.enter="submit" lazy-validation>
            <v-text-field
              v-model="user.email"
              :rules="userRules.emailRules"
              color="teal"
              label="E-mail"
              required>
            </v-text-field>
            <v-text-field
              v-model="user.password"
              :append-icon="invisibility ? 'visibility' : 'visibility_off'"
              @click:append="() => (invisibility = !invisibility)"
              :type="invisibility ? 'password' : 'text'"
              :rules="userRules.passwordRule"
              color="teal"
              label="Senha"
              required>
            </v-text-field>
          </v-form>
          <v-card-actions class="center">
            <v-btn color="blue-grey lighten-5" @click="submit" class="black--text bold-text">Entrar</v-btn>
            <v-btn color="blue-grey lighten-5" :to="signUpAction" class="black--text bold-text">Cadastrar-se</v-btn>
          </v-card-actions>
          <v-snackbar
            :timeout="6000"
            :bottom="true"
            v-model="snackbar">
              Usuário ou senha incorretos!
            <v-btn flat color="red" @click.native="snackbar = false">Fechar</v-btn>
          </v-snackbar>
        </v-card>
      </v-flex>
    </v-layout>
  </div>
</template>

<script>
export default {
  name: 'Login',
  data() {
    return {
      valid: true,
      invisibility: true,      
      snackbar: false,
      user: {
        email: '',
        password: '',
      },
      userRules: {
        emailRules: [
          v => /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,4})+$/.test(v) || 'E-mail inválido!'
        ],
        passwordRule: [
          v => !!v || 'Senha é obrigatória!'
        ]
      },
      signUpAction: '/signup'
    }
  },
  methods: {
    submit () {
      if (this.$refs.form.validate()) {
        this.$auth.login({
          body: JSON.stringify(this.user),
          success: function (response) {
            localStorage.token = response.body.data.token
          },
          error: function () {
            delete localStorage.token
            this.snackbar = true
          },
          rememberMe: true,
          redirect: '/home'
        })
      }
    },
  }
}
</script>

<style scoped>
  #login { 
    height: 100%;
    display: flex;
    align-items: center;        
    background-color: #009688;  
    background-image: url('../../../static/icon.png');    
    background-position: 50% 85%;
    background-size: 15%;
  }

  #card {
    margin-top: -10%;
  }

  #form {
    padding: 0px 20px 0px 20px;
  }

  .center {
    justify-content: center;
  } 

  .text {
    color: #009688;
    font-weight: bold;
  }

  .bold-text {
    font-weight: bold;
  }

  @media screen and (max-width: 800px)  {
    #login { 
      background-position: 50% 95%;
      background-size: 40%;
    }
  }

  @media screen and (max-width: 600px)  {
    #login { 
      background-position: 50% 95%;
      background-size: 40%;
    }
  }

</style>


