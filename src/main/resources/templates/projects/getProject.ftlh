<#import "../layout.ftlh" as main>
<@main.layout>
    <h2>Project Details</h2>

<#--    <div class="card mt-3" style="width: 18rem;">-->
    <div class="card-body">
        <h5 class="card-title">${project.name}</h5>
        <p class="card-text">
        <p>Customer Company: ${project.customerCompanyId.name}</p>
        <p>Executor Company: ${project.executorCompanyId.name}</p>
        <p>Project
            Leader: ${project.projectLeader.firstName} ${project.projectLeader.lastName} ${project.projectLeader.middleName}</p>
        <p>Start Date: ${project.startDate}</p>
        <p>End Date: ${project.endDate}</p>
        <p>Priority: ${project.priority}</p>
        </p>
        <div class="mt-2">
            <hr>
            <#if tasks??>
                <h6>Tasks:</h6>
                <#list tasks as task>
                    <li class="list-group-item">
                        <div class="d-flex justify-content-between">
                            <span>${task.name} - ${task.status}</span>
                            <a href="/tasks/${task.id}" class="btn btn-outline-primary">Details</a>
                        </div>
                    </li>
                </#list>
            </#if>
            <a href="/tasks/create" class="btn btn-outline-success">Create Task</a>
        </div>
        <div class="mt-2">
        <hr>
            <#if employees??>
                <h6>Employees:</h6>
                <#list employees as employee>
                    <li class="list-group-item">
                        <div class="d-flex justify-content-between">
                            <span>${employee.firstName} ${employee.lastName}</span>
                            <a href="/employees/${employee.id}" class="btn btn-outline-primary">Details</a>
                            <a href="/projects/${project.id}/removeEmployee/${employee.id}"
                               class="btn btn-outline-danger mx-2">Delete employee</a>
                        </div>
                    </li>
                </#list>
            </#if>
            <a href="/projects/${project.id}/addEmployee" class="btn btn-outline-success mx-2">Add employee</a>
        </div>
        <hr>
    </div>

    <div class="mt-3">
        <a href="/projects/all" class="btn btn-primary">Back to Projects List</a>
        <a href="/projects/${project.id}/edit" class="btn btn-warning mx-2">Edit</a>
        <a href="/projects/${project.id}/delete" class="btn btn-danger mt-auto">Delete</a>
    </div>
</@main.layout>
