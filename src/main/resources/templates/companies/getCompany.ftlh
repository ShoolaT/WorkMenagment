<#import "../layout.ftlh" as main>

<@main.layout>
    <h2>Company Details</h2>

    <h5 class="card-title">${company.name}</h5>
    <h6 class="mt-3">Employees:</h6>
    <ul class="list-group">
        <#list employees as employee>
            <li class="list-group-item">
                <div class="d-flex justify-content-between">
                    <span>${employee.firstName} ${employee.lastName}</span>
                    <a href="/employees/${employee.id}" class="btn btn-primary">Details</a>
                </div>
            </li>
        </#list>
    </ul>
    <div class="mt-3">
        <a href="/companies/all" class="btn btn-primary">Back to Companies List</a>
        <a href="/companies/${company.id}/edit" class="btn btn-warning mx-2">Edit</a>
        <a href="/companies/${company.id}/delete" class="btn btn-danger mt-auto">Delete</a>
    </div>
</@main.layout>
