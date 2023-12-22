<#import "../layout.ftlh" as main>

<@main.layout>
    <h2>All Companies</h2>

    <!-- Loop through companies and display relevant information -->
    <#list companies.content as company>
        <div class="card mb-3">
            <div class="card-body">
                <h5 class="card-title">${company.name}</h5>
                <!-- Add other fields as needed -->
                <a href="/companies/${company.id}" class="btn btn-primary">View Details</a>
            </div>
        </div>
    </#list>
    <div class="mt-3">
        <a href="/companies/create" class="btn btn-success">Add Company</a>
    </div>
</@main.layout>
