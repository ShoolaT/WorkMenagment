<#import "../layout.ftlh" as main>

<@main.layout>
    <div class="container">
        <h2>List of Employees</h2>

        <table class="table">
            <thead>
            <tr>
                <th>Employee Name</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <#list employees as employee>
                <tr>
                    <td>${employee.firstName} ${employee.lastName}</td>
                    <td>
                        <#if !projectEmployeesIds?seq_contains(employee.id)>
                            <form action="/projects/${projectId}/addEmployee/${employee.id}" method="post">
                                <button type="submit" class="btn btn-primary">Add to Project</button>
                            </form>
                        </#if>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</@main.layout>
