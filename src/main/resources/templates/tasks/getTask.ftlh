<#import "../layout.ftlh" as main>
<@main.layout>
    <h2>Task Details</h2>

    <div class="card">
        <div class="card-body">
            <h5 class="card-title">${task.name}</h5>
            <p class="card-text">Author: ${task.authorId.firstName} ${task.authorId.lastName}</p>
            <p class="card-text">Executor: ${task.executorId.firstName} ${task.executorId.lastName}</p>
            <p class="card-text">Status: ${task.status}</p>
            <p class="card-text">Comment: ${task.comment}</p>
            <p class="card-text">Priority: ${task.priority}</p>
            <p class="card-text">Project: ${task.projectId.name}</p>
        </div>
    </div>

    <div class="mt-3">
        <a href="/tasks/all" class="btn btn-primary">Back to Tasks List</a>

        <!-- Add Edit and Delete buttons -->
        <a href="/tasks/${task.id}/edit" class="btn btn-warning mx-2">Edit</a>
        <a href="/tasks/${task.id}/delete" class="btn btn-danger mt-auto">Delete</a>
    </div>
</@main.layout>
