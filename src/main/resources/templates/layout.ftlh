<#import "spring.ftl" as spring>
<#macro layout>

    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"
              name="viewport">
        <meta content="ie=edge" http-equiv="X-UA-Compatible">
        <meta name="_csrf_header" content="${_csrf.headerName}"/>
        <meta name="_csrf_token" content="${_csrf.token}"/>
        <title>Http server Index page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="/static/styles.css">
        <script type="text/javascript" src="/static/js/main.js"></script>
    </head>

    <body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="/"><@spring.message 'navbar.logo'/></a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                    aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link active" href="/"><@spring.message 'navbar.navi.home'/></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#"><@spring.message 'navbar.navi.books'/></a>
                    </li>
                </ul>
                <div class="dropdown">
                    <button class="btn btn-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown"
                            aria-expanded="false">
                        <i class="flag-united-kingdom flag m-0"></i>
                        <@spring.message 'navbar.navi.lang'/>
                    </button>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" href="?lang=en">English</a></li>
                        <li><a class="dropdown-item" href="?lang=ru">Русский</a></li>
                    </ul>
                </div>
                <#assign
                known = SPRING_SECURITY_CONTEXT??
                >
                <#if known>
                    <#assign
                    user = SPRING_SECURITY_CONTEXT.authentication.principal
                    name = user.getUsername()
                    >
                    <a href="/profile/${name}"
                       class="text-light m-2 m-sm-0"><@spring.message 'navbar.user.hi'/> ${name}</a>


                    <#if _csrf??>
                        <form action="/logout" method="post">
                            <input type="hidden" name="${(_csrf.parameterName)!'csrf'}"
                                   value="${(_csrf.token)!'--no-token--'}"/>
                            <button type="submit" class="btn btn-outline-info m-2"
                                    id="logout"><@spring.message 'navbar.user.logout'/></button>
                        </form>
                    <#else>
                        <a href="/logout" class="btn btn-outline-info m-2"
                           id="logout"><@spring.message 'navbar.user.logout'/></a>
                    </#if>

                <#else>
                    <a href="/auth/login"
                       class="btn btn-outline-info m-2"><@spring.message 'navbar.user.signin'/></a>
                    <a href="/auth/register"
                       class="btn btn-outline-info m-2"><@spring.message 'navbar.user.signup'/></a>
                </#if>
                <#--                </div>-->
            </div>
        </div>
    </nav>
    <i class="m-5"></i>
    <div class="container">
        <#nested spring>
    </div>


    <script>
        let logout = document.getElementById('logout')
        if (logout) {
            logout.addEventListener('click', () => localStorage.removeItem('user'))
        }
    </script>
    </body>
    </html>
</#macro>