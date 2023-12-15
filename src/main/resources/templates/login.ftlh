<#import "/spring.ftl" as spring />

<#assign title = "Insert title here" />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${title}</title>
</head>
<body>
<#-- Include your FreeMarker layout or section handling here -->
<@spring.layout layout="base::layout">
    <#assign section>
        <div class="container">
            <h2>Login</h2>
            <form action="@{/login}" method="POST">
                <div>
                    <label for="email">Email</label>
                    <input id="email" type="text" name="email" placeholder="Email" />
                </div>
                <div>
                    <label for="password">Password</label>
                    <input id="password" type="password" name="password" placeholder="Password" />
                </div>
                <button type="submit">Login!</button>
            </form>
        </div>
    </#assign>
    <@spring.block section=section/>
</@spring.layout>
</body>
</html>
