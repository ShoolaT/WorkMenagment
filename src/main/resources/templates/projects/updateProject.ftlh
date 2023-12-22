<#import "../layout.ftlh" as main>
<@main.layout>
    <h2>Update Task</h2>
    <form action="/projects/${project.id}/edit" method="post" class="needs-validation" novalidate>
        <div class="form-group">
            <label for="name" class="form-label">Project Name</label>
            <input type="text" class="form-control" id="name" name="name" value="${project.name}" required>
        </div>

        <div class="form-group">
            <label for="customerCompanyId" class="form-label">Customer Company</label>
            <select class="form-select" id="customerCompanyId" name="customerCompanyId" required>
                <#list companies as company>
                    <option value="${company.id}" <#if project.customerCompanyId.id == company.id>selected</#if>>${company.name}</option>
                </#list>
            </select>
        </div>

        <div class="form-group">
            <label for="executorCompanyId" class="form-label">Executor Company</label>
            <select class="form-select" id="executorCompanyId" name="executorCompanyId" required>
                <#list companies as company>
                    <option value="${company.id}" <#if project.executorCompanyId.id == company.id>selected</#if>>${company.name}</option>
                </#list>
            </select>
        </div>

        <div class="form-group">
            <label for="projectLeader" class="form-label">Project Leader</label>
            <select class="form-select" id="projectLeader" name="projectLeader" required>
                <#list employees as employee>
                    <option value="${employee.id}" <#if project.projectLeader.id == employee.id>selected</#if>>${employee.firstName} ${employee.lastName}</option>
                </#list>
            </select>
        </div>

        <div class="form-group">
            <label for="startDate" class="form-label">Start Date</label>
            <input type="date" class="form-control" id="startDate" name="startDate" value="${project.startDate}" required>
        </div>

        <div class="form-group">
            <label for="endDate" class="form-label">End Date</label>
            <input type="date" class="form-control" id="endDate" name="endDate" value="${project.endDate}" required>
        </div>

        <div class="form-group">
            <label for="priority" class="form-label">Priority</label>
            <input type="number" class="form-control" id="priority" name="priority" value="${project.priority}" required>
        </div>

        <div class="mt-2">
            <button type="submit" class="btn btn-primary">Update</button>
            <a href="/projects/${project.id}" class="btn btn-secondary mx-3">Cancel</a>
        </div>
    </form>
</@main.layout>
