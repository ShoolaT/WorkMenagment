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
    <h2>Register New Employee</h2>
    <form action="@{/register}" method="POST" modelAttribute="employee">
        <div>
            <label for="firstname">First Name</label>
            <input id="firstname" type="text" name="firstName" placeholder="First Name"/>
        </div>
        <div>
            <label for="lastname">Last Name</label>
            <input id="lastname" type="text" name="lastName" placeholder="Last Name"/>
        </div>
        <div>
            <label for="email">Email</label>
            <input id="email" type="text" name="email" placeholder="Email"/>
        </div>
        <div>
            <label for="password">Password</label>
            <input id="password" type="password" name="password" placeholder="Password"/>
        </div>
        <button type="submit">Register!</button>
    </form>
</div>
</#assign>
<@spring.block section=section/>
</@spring.layout>
</body>
</html>
