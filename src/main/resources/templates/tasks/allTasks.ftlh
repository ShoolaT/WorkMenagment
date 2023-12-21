<#import "../layout.ftlh" as main>
<@main.layout>
    <h2>Employee List</h2>

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
