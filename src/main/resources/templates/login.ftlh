<#import "layout.ftlh" as main>
<@main.layout>
    <div class="container">
        <h2>Sign In</h2>
        <form action="/login" method="POST">
            <div>
                <label for="email">Email</label>
                <input id="email" type="text" name="email" placeholder="Email" />
            </div>
            <div>
                <label for="password">Password</label>
                <input id="password" type="password" name="password" placeholder="Password" />
            </div>
            <button type="submit">Sign In</button>
        </form>
    </div>
</@main.layout>
