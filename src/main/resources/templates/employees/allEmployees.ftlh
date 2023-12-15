<#import "../layout.ftlh" as main>
<@main.layout>
    <h2>Employee List</h2>

    <div class="row justify-content-around">
        <#list employees.content as employee>
            <div class="card col-3 m-1">
                <div class="card-body">
                    <h5 class="card-title">${employee.firstName}</h5>
                    <hr>
                    <p class="card-text">Last Name: ${employee.lastName}</p>
                    <p class="card-text">Middle Name: ${employee.middleName}</p>
                    <#list companies as company>
                        <#if employee.companyId == company.id>
                            <p class="card-text">Company: ${company.name}</p>
                        </#if>
                    </#list>
                </div>
            </div>
        </#list>
    </div>

    <div class="mt-3">
        <a href="/employees/create" class="btn btn-success">Add Employee</a>
    </div>
</@main.layout>
