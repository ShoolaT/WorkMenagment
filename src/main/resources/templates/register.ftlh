<#import "layout.ftlh" as main>
<@main.layout>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <h2 class="mb-4">Sign Up</h2>
                <form action="#" method="POST">
                    <div class="mb-3">
                        <label for="firstname" class="form-label">First Name</label>
                        <input id="firstname" type="text" name="firstName" class="form-control" placeholder="First Name"/>
                    </div>
                    <div class="mb-3">
                        <label for="lastname" class="form-label">Last Name</label>
                        <input id="lastname" type="text" name="lastName" class="form-control" placeholder="Last Name"/>
                    </div>
                    <div class="mb-3">
                        <label for="middlename" class="form-label">Middle Name</label>
                        <input id="middlename" type="text" name="middleName" class="form-control" placeholder="Middle Name"/>
                    </div>
                    <div class="mb-3">
                        <label for="email" class="form-label">Email</label>
                        <input id="email" type="text" name="email" class="form-control" placeholder="Email"/>
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label">Password</label>
                        <input id="password" type="password" name="password" class="form-control" placeholder="Password"/>
                    </div>
                    <div class="mb-3">
                        <label for="companyId" class="form-label">Company</label>
                        <select class="form-select" id="companyId" name="companyId" required>
                            <#list allCompanies as company>
                                <option value="${company.id}">${company.name}</option>
                            </#list>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="authorities" class="form-label">Authority</label>
                        <select class="form-select" id="authorities" name="authorities" required>
                            <#list allAuthorities as authority>
                                <option value="${authority.name}">${authority.name}</option>
                            </#list>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-primary">Sign Up</button>
                </form>
            </div>
        </div>
    </div>
</@main.layout>
