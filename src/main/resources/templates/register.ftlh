<#import "layout.ftlh" as main>
<@main.layout>
    <div class="container">
        <h2>Sign Up</h2>
        <form action="#" method="POST">
            <div>
                <label for="firstname">First Name</label>
                <input id="firstname" type="text" name="firstName" placeholder="First Name"/>
            </div>
            <div>
                <label for="lastname">Last Name</label>
                <input id="lastname" type="text" name="lastName" placeholder="Last Name"/>
            </div>
            <div>
                <label for="middlename">Middle Name</label>
                <input id="middlename" type="text" name="middleName" placeholder="Middle Name"/>
            </div>
            <div>
                <label for="email">Email</label>
                <input id="email" type="text" name="email" placeholder="Email"/>
            </div>
            <div>
                <label for="password">Password</label>
                <input id="password" type="password" name="password" placeholder="Password"/>
            </div>
            <div class="form-group">
                <label for="companyId">Company</label>
                <select class="form-control" id="companyId" name="companyId" required>
                    <#list allCompanies as company>
                        <option value="${company.id}">${company.name}</option>
                    </#list>
                </select>
            </div>
            <div class="form-group">
                <label for="authorities">Authority</label>
                <select class="form-control" id="authorities" name="authorities" required>
                    <#list allAuthorities as authority>
                        <option value="${authority.name}">${authority.name}</option>
                    </#list>
                </select>
            </div>
            <button type="submit">Sign Up</button>
        </form>
    </div>
</@main.layout>
