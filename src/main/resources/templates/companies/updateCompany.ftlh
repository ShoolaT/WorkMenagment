<#import "../layout.ftlh" as main>

<@main.layout>
    <h2>Update Company</h2>

    <form action="/companies/${company.id}/edit" method="post" class="needs-validation" novalidate>
        <div class="mb-3">
            <label for="name" class="form-label">Company Name</label>
            <input type="text" class="form-control" id="name" name="name" value="${company.name}" required>
        </div>

        <button type="submit" class="btn btn-primary">Update Company</button>
    </form>
</@main.layout>
