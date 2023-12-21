<#import "../layout.ftlh" as main>
<@main.layout>
    <h2>Update Task</h2>
    <form action="/tasks/${task.id}/edit" method="post" class="needs-validation" novalidate>
        <div class="form-group">
            <label for="name" class="form-label">Task Name</label>
            <input type="text" class="form-control" id="name" name="name" value="${task.name}" required>
        </div>

        <div class="form-group">
            <label for="authorId" class="form-label">Author</label>
            <select class="form-select" id="authorId" name="authorId" required>
                <#list employees as employee>
                    <option value="${employee.id}"<#if employee.id == task.authorId.id> selected</#if>>${employee.firstName} ${employee.lastName}</option>
                </#list>
            </select>
        </div>

        <div class="form-group">
            <label for="projectId" class="form-label">Project</label>
            <select class="form-select" id="projectId" name="projectId" required>
                <#list projects as project>
                    <option value="${project.id}"<#if project.id == task.projectId.id> selected</#if>>${project.name}</option>
                </#list>
            </select>
        </div>

        <div class="form-group">
            <label for="executorId" class="form-label">Executor</label>
            <select class="form-select" id="executorId" name="executorId" required>
                <#list employees as employee>
                    <option value="${employee.id}"<#if employee.id == task.executorId.id> selected</#if>>${employee.firstName} ${employee.lastName}</option>
                </#list>
            </select>
        </div>

        <div class="form-group">
            <label for="status" class="form-label">Status</label>
            <select class="form-select" id="status" name="status" required>
                <#list statusList as status>
                    <option value="${status}"<#if status == task.status> selected</#if>>${status}</option>
                </#list>
            </select>
        </div>

        <div class="form-group">
            <label for="comment" class="form-label">Comment</label>
            <textarea class="form-control" id="comment" name="comment" rows="3">${task.comment}</textarea>
        </div>

        <div class="form-group">
            <label for="priority" class="form-label">Priority</label>
            <input type="number" class="form-control" id="priority" name="priority" value="${task.priority}" required>
        </div>

        <div class="mt-2">
            <button type="submit" class="btn btn-primary">Update</button>
            <a href="/tasks/${task.id}" class="btn btn-secondary mx-3">Cancel</a>
        </div>
    </form>
</@main.layout>
