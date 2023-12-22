<#import "../layout.ftlh" as main>
<@main.layout>
    <h2>Create Task</h2>

    <form action="/tasks/create" method="post">
        <div class="mb-3">
            <label for="name" class="form-label">Task Name</label>
            <input type="text" class="form-control" id="name" name="name" required>
        </div>

        <div class="mb-3">
            <label for="authorId" class="form-label">Author</label>
            <select class="form-select" id="authorId" name="authorId" required>
                <#list employees as employee>
                    <option value="${employee.id}">${employee.firstName} ${employee.lastName}</option>
                </#list>
            </select>
        </div>

        <div class="mb-3">
            <label for="projectId" class="form-label">Project</label>
            <select class="form-select" id="projectId" name="projectId" required>
                <#list projects as project>
                    <option value="${project.id}">${project.name}</option>
                </#list>
            </select>
        </div>

        <div class="mb-3">
            <label for="executorId" class="form-label">Executor</label>
            <select class="form-select" id="executorId" name="executorId" required>
                <#list employees as employee>
                    <option value="${employee.id}">${employee.firstName} ${employee.lastName}</option>
                </#list>
            </select>
        </div>

        <div class="mb-3">
            <label for="status" class="form-label">Status</label>
            <select class="form-select" id="status" name="status" required>
                <#list statusList as status>
                    <option value="${status}">${status}</option>
                </#list>
            </select>
        </div>

        <div class="mb-3">
            <label for="comment" class="form-label">Comment</label>
            <textarea class="form-control" id="comment" name="comment" rows="3"></textarea>
        </div>

        <div class="mb-3">
            <label for="priority" class="form-label">Priority</label>
            <input type="number" class="form-control" id="priority" name="priority" required>
        </div>

        <button type="submit" class="btn btn-primary">Create Task</button>
    </form>
</@main.layout>
