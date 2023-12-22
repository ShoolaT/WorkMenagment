<#import "../layout.ftlh" as main>
<@main.layout>
    <h2>Tasks List</h2>

    <div class="mt-3">
        <div class="dropdown">
            <button class="btn btn-outline-dark dropdown-toggle" type="button" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                Sort By
            </button>
            <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                <li><a class="dropdown-item" href="/tasks/all?sort=name">Name</a></li>
                <li><a class="dropdown-item" href="/tasks/all?sort=status">Status</a></li>
                <li><a class="dropdown-item" href="/tasks/all?sort=priority">Priority</a></li>
            </ul>
        </div>
    </div>


    <div class="row justify-content-around">
        <#list tasks.content as task>
            <div class="card col-3 m-1" style="width: 18rem;">
                <div class="card-body">
                    <h5 class="card-title">${task.name}</h5>
                    <p class="card-text">
                        <p>Project :${task.projectId.name}</p>
                        <p>Executor :${task.executorId.firstName} ${task.executorId.lastName}</p>
                        <p>Status :${task.status}</p>
                        <p>Priority :${task.priority}</p>
                        <p>Comment :${task.comment}</p>
                    </p>
                    <a href="/tasks/${task.id}" class="card-link">See more information</a>
                </div>
            </div>
        </#list>
    </div>

    <div class="mt-3">
        <a href="/tasks/create" class="btn btn-success">Add Task</a>
    </div>
</@main.layout>
