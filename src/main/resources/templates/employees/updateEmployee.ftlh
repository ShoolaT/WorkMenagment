<#import "../layout.ftlh" as main>
<@main.layout>
    <div class="container mt-5">
        <h2>Update Employee</h2>

        <form action="/employees/${employeeDto.id}/edit" method="post" class="needs-validation" novalidate>
            <div class="form-group">
                <label for="firstName">First Name:</label>
                <input type="text" class="form-control" id="firstName" name="firstName" value="${employeeDto.firstName}" required>
            </div>

            <div class="form-group">
                <label for="lastName">Last Name:</label>
                <input type="text" class="form-control" id="lastName" name="lastName" value="${employeeDto.lastName}" required>
            </div>

            <div class="form-group">
                <label for="middleName">Middle Name:</label>
                <input type="text" class="form-control" id="middleName" name="middleName" value="${employeeDto.middleName}">
            </div>

            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" class="form-control" id="email" name="email" value="${employeeDto.email}" required>
            </div>

            <div class="form-group">
                <label for="companyId">Company:</label>
                <select class="form-control" id="companyId" name="companyId" required>
                    <#list companies as company>
                        <option value="${company.id}"<#if company.id == employeeDto.companyId> selected</#if>>${company.name}</option>
                    </#list>
                </select>
            </div>

            <div class="mt-2">
                <button type="submit" class="btn btn-primary">Update</button>
                <a href="/employees/${employeeDto.id}" class="btn btn-secondary mx-3">Cancel</a>
            </div>
        </form>
    </div>
</@main.layout>
