<#import "../layout.ftlh" as main>
<@main.layout>
    <div class="container mt-5">
        <h2>Create Employee</h2>

        <form action="/employees/create" method="post" class="needs-validation">
            <div class="form-group">
                <label for="firstName">First Name:</label>
                <input type="text" class="form-control" id="firstName" name="firstName" required>
            </div>

            <div class="form-group">
                <label for="lastName">Last Name:</label>
                <input type="text" class="form-control" id="lastName" name="lastName" required>
            </div>

            <div class="form-group">
                <label for="middleName">Middle Name:</label>
                <input type="text" class="form-control" id="middleName" name="middleName">
            </div>

            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" class="form-control" id="email" name="email" required>
            </div>

            <div class="form-group">
                <label for="companyId">Company:</label>
                <select class="form-control" id="companyId" name="companyId" required>
                    <#list companies as company>
                        <option value="${company.id}">${company.name}</option>
                    </#list>
                </select>
            </div>

            <div class="mt-2">
            <button type="submit" class="btn btn-primary">Create</button>
            <a href="/employees/all" class="btn btn-secondary mx-3">Cancel</a>
            </div>
        </form>
    </div>
</@main.layout>
