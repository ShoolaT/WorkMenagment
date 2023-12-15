<#import "../layout.ftlh" as main>
<@main.layout>
<div class="container mt-5">
    <h2>Employee Details</h2>

    <div class="container mt-5">
        <h2>Employee Details</h2>

        <div class="card">
            <div class="card-body">
                <h5 class="card-title">${employee.firstName} ${employee.lastName}</h5>
                <p class="card-text">
                    <strong>ID:</strong> ${employee.id}<br>
                    <strong>Email:</strong> ${employee.email}<br>
                    <strong>Company:</strong> ${company.name}<br>
                </p>
            </div>
        </div>
        <div class="mt-3">
            <a href="/" class="btn btn-primary">Back to Employees List</a>

            <!-- Add Edit and Delete buttons -->
            <a href="/employees/${employee.id}/edit" class="btn btn-warning mx-2">Edit</a>
            <a href="/employees/${employee.id}/delete" class="btn btn-danger mt-auto">Delete</a>
        </div>

        </@main.layout>
