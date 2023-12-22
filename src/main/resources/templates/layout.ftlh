
<#macro layout>

    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"
              name="viewport">
        <meta content="ie=edge" http-equiv="X-UA-Compatible">
        <title>Http server Index page</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
              crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
                crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.3/font/bootstrap-icons.css">
        <link href="/static/styles.css">


        <style>
            .glow-on-hover {
                border: none;
                outline: none;
                color: #fff;
                background: #99b3ff;
                cursor: pointer;
                position: relative;
                z-index: 0;
                border-radius: 10px;
            }

            .glow-on-hover:before {
                content: '';
                background: linear-gradient(45deg, #ff0000, #ff7300, #fffb00, #48ff00, #00ffd5, #002bff, #7a00ff, #ff00c8, #ff0000);
                position: absolute;
                top: -2px;
                left:-2px;
                background-size: 400%;
                z-index: -1;
                filter: blur(5px);
                width: calc(100% + 4px);
                height: calc(100% + 4px);
                animation: glowing 20s linear infinite;
                opacity: 0;
                transition: opacity .3s ease-in-out;
                border-radius: 10px;
            }

            .glow-on-hover:active {
                color: #99b3ff
            }

            .glow-on-hover:active:after {
                background: transparent;
            }

            .glow-on-hover:hover:before {
                opacity: 1;
            }

            .glow-on-hover:after {
                z-index: -1;
                content: '';
                position: absolute;
                width: 100%;
                height: 100%;
                background: #111;
                left: 0;
                top: 0;
                border-radius: 10px;
            }

            @keyframes glowing {
                0% { background-position: 0 0; }
                50% { background-position: 400% 0; }
                100% { background-position: 0 0; }
            }
        </style>
    </head>

    <body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <a class="navbar-brand" href="/">Work Management</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="/">Home<span class="sr-only"></span></a>
                </li>
            </ul>

            <ul class="navbar-nav">
                                <#if user??>
                                    <li class="nav-item">
                                        <a class="nav-link" href="/logout">Sign Out</a>
                                    </li>
                                <#else>
                                    <li class="nav-item">
                                        <a class="nav-link" href="/login">Sign In</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" href="/register">Sign Up</a>
                                    </li>
                                </#if>
                            </ul>
        </div>
    </nav>

    <i class="m-5"></i>
    <div class="container border border-2 border-dark rounded-3 m-5" style="padding: 40px 55px;min-height: 300px">
        <#nested>
    </div>

    <script>





    </script>

    </body>
    </html>
</#macro>

