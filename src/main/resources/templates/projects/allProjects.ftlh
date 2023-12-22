<#import "../layout.ftlh" as main>

<@main.layout>
    <div class="container">
        <h2>All Projects</h2>

        <div class="mt-3">
            <div class="dropdown">
                <button class="btn btn-outline-dark dropdown-toggle" type="button" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Sort By
                </button>
                <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                    <li><a class="dropdown-item" href="/projects/all?sort=name">Name</a></li>
                    <li><a class="dropdown-item" href="/projects/all?sort=customerCompanyId">Customer Company</a></li>
                    <li><a class="dropdown-item" href="/projects/all?sort=executorCompanyId">Executor Company</a></li>
                    <li><a class="dropdown-item" href="/projects/all?sort=projectLeader">Project Leader</a></li>
                    <li><a class="dropdown-item" href="/projects/all?sort=startDate">Start Date</a></li>
                    <li><a class="dropdown-item" href="/projects/all?sort=endDate">End Date</a></li>
                    <li><a class="dropdown-item" href="/projects/all?sort=priority">Priority</a></li>
                </ul>
            </div>
        </div>

        <div class="row justify-content-around mt-3">
            <#list tasks.content as project>
                <div class="card col-3 m-1" style="width: 18rem;">
                    <div class="card-body">
                        <h5 class="card-title">${project.name}</h5>
                        <p class="card-text">
                        <p>Customer Company: ${project.customerCompanyId.name}</p>
                        <p>Executor Company: ${project.executorCompanyId.name}</p>
                        <p>Project Leader: ${project.projectLeader.firstName} ${project.projectLeader.lastName}</p>
                        <p>Start Date: ${project.startDate}</p>
                        <p>End Date: ${project.endDate}</p>
                        <p>Priority: ${project.priority}</p>
                        </p>
                        <a href="/projects/${project.id}" class="card-link">See more information</a>
                    </div>
                </div>
            </#list>
        </div>

        <div class="mt-3">
            <a href="/projects/create" class="btn btn-success">Add Project</a>
        </div>
    </div>
</@main.layout>
