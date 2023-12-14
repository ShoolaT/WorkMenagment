<#import "../layout.ftlh" as main>
<@main.layout>
    <section class="vh-100">
        <div class="container py-5 h-100">
            <div class="row d-flex align-items-center justify-content-center h-100">
                <div class="col-md-8 col-lg-7 col-xl-6">
                    <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/draw2.svg"
                         class="img-fluid" alt="Phone image">
                </div>
                <div class="col-md-7 col-lg-5 col-xl-5 offset-xl-1">
                    <#--                    <#if RequestParameters.error??>-->
                    <#--                        <div class="alert alert-danger">-->
                    <#--                            <strong>Invalid Login!</strong><br>Invalid username or password-->
                    <#--                        </div>-->
                    <#--                    </#if>-->
                    <form action="/auth/login" method="post" id="login-form">
                        <#if _csrf??>
                            <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                                   value="${(_csrf.token)!'csrf-token'}"/>
                        </#if>

                        <#if error??>
                            <b class="error">${error}</b>
                        </#if>
                        <!-- Email input -->
                        <div class="form-outline mb-4">
                            <input type="email" name="username" id="form1Example13"
                                   class="form-control form-control-lg"/>
                            <label class="form-label" for="form1Example13">Email address</label>
                        </div>

                        <!-- Password input -->
                        <div class="form-outline mb-4">
                            <input type="password" name="password" id="form1Example23"
                                   class="form-control form-control-lg"/>
                            <label class="form-label" for="form1Example23">Password</label>
                        </div>

                        <div class="d-flex justify-content-around align-items-center mb-4">
                            <!-- Checkbox -->
                            <div class="form-check">
                                <input class="form-check-input" type="checkbox" value="true" id="form1Example3"
                                       name="remember-me" checked/>
                                <label class="form-check-label" for="form1Example3"> Remember me </label>
                            </div>
                            <a href="forgot_password">Forgot password?</a>
                        </div>

                        <!-- Submit button -->
                        <button type="submit" class="btn btn-primary btn-lg btn-block">Sign in</button>

                        <div class="divider d-flex align-items-center my-4">
                            <p class="text-center fw-bold mx-3 mb-0 text-muted">OR</p>
                        </div>

                        <div class="d-flex justify-content-center">
                            <a class="btn btn-outline-danger" href="/oauth2/authorization/google">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                                     class="bi bi-google" viewBox="0 0 16 16">
                                    <path d="M15.545 6.558a9.42 9.42 0 0 1 .139 1.626c0 2.434-.87 4.492-2.384 5.885h.002C11.978 15.292 10.158 16 8 16A8 8 0 1 1 8 0a7.689 7.689 0 0 1 5.352 2.082l-2.284 2.284A4.347 4.347 0 0 0 8 3.166c-2.087 0-3.86 1.408-4.492 3.304a4.792 4.792 0 0 0 0 3.063h.003c.635 1.893 2.405 3.301 4.492 3.301 1.078 0 2.004-.276 2.722-.764h-.003a3.702 3.702 0 0 0 1.599-2.431H8v-3.08h7.545z"/>
                                </svg>
                                Continue with Google
                            </a>
                        </div>

                    </form>
                </div>
            </div>
        </div>

        <script>
            document.getElementById('login-form').addEventListener('submit', onLoginHandler)

            function onLoginHandler(event) {
                let form = event.target
                let userFormData = new FormData(form)
                let user = Object.fromEntries(userFormData)
                saveUser(user)
            }
        </script>
    </section>
</@main.layout>