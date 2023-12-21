<#import "../layout.ftlh" as main>
<@main.layout>
    <div class="container mt-5">
        <h2>Create Project</h2>

        <form action="/projects/create" method="post" class="needs-validation">
            <div class="form-group">
                <label for="name">Project Name:</label>
                <input type="text" class="form-control" id="name" name="name" required>
            </div>

            <div class="form-group">
                <label for="customerCompanyId">Customer Company:</label>
                <select class="form-control" id="customerCompanyId" name="customerCompanyId" required>
                    <#list companies as company>
                        <option value="${company.id}">${company.name}</option>
                    </#list>
                </select>
            </div>

            <div class="form-group">
                <label for="executorCompanyId">Executor Company:</label>
                <select class="form-control" id="executorCompanyId" name="executorCompanyId" required>
                    <#list companies as company>
                        <option value="${company.id}">${company.name}</option>
                    </#list>
                </select>
            </div>

            <div class="form-group">
                <label for="projectLeader">Project Leader:</label>
                <select class="form-control" id="projectLeader" name="projectLeader" required>
                    <#list employees as employee>
                        <option value="${employee.id}">${employee.firstName} ${employee.lastName}</option>
                    </#list>
                </select>
            </div>

            <div class="form-group">
                <label for="startDate">Start Date:</label>
                <input type="date" class="form-control" id="startDate" name="startDate" required>
            </div>

            <div class="form-group">
                <label for="endDate">End Date:</label>
                <input type="date" class="form-control" id="endDate" name="endDate" required>
            </div>

            <div class="form-group">
                <label for="priority">Priority:</label>
                <input type="number" class="form-control" id="priority" name="priority" required>
            </div>

            <div class="form-group">
                <label for="employees">Add Employees:</label>
                <select multiple class="form-control" id="employees" name="employees">
                    <#list employees as employee>
                        <option value="${employee.id}">${employee.firstName} ${employee.lastName}</option>
                    </#list>
                </select>
            </div>

            <div class="mt-2">
                <button type="submit" class="btn btn-primary">Create Project</button>
                <a href="/projects/all" class="btn btn-secondary mx-3">Cancel</a>
            </div>
        </form>
    </div>
</@main.layout>
