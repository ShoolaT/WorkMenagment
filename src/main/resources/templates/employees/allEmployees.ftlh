<#import "../layout.ftlh" as main>
<@main.layout>
    <h2>Employee List</h2>

    <div class="mt-3">
        <div class="btn-group">
            <button type="button" class="btn btn-outline-dark dropdown-toggle" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                Sort By
            </button>
            <ul class="dropdown-menu">
                <li><a class="dropdown-item" href="/employees/all?sort=firstName">First Name</a></li>
                <li><a class="dropdown-item" href="/employees/all?sort=lastName">Last Name</a></li>
                <li><a class="dropdown-item" href="/employees/all?sort=middleName">Middle Name</a></li>
                <li><a class="dropdown-item" href="/employees/all?sort=email">Email</a></li>
            </ul>
        </div>
    </div>


    <div class="row justify-content-around">
        <#list employees.content as employee>
            <div class="card col-3 m-1">
                <div class="card-body">
                    <h5 class="card-title">${employee.firstName}</h5>
                    <hr>
                    <p class="card-text">Last Name: ${employee.lastName}</p>
                    <p class="card-text">Middle Name: ${employee.middleName}</p>
                    <p class="card-text">Email: ${employee.email}</p>
                    <#list companies as company>
                        <#if employee.companyId == company.id>
                            <p class="card-text">Company: ${company.name}</p>
                        </#if>
                    </#list>
                    <a href="/employees/${employee.id}" class="card-link">See more information</a>
                </div>
            </div>
        </#list>
    </div>

    <div class="mt-3">
        <a href="/employees/create" class="btn btn-success">Add Employee</a>
    </div>
</@main.layout>
