<#include "setup.ftl" />
<html>
<head>
    <title>Current Forms</title>
    <@stylesheet "/static/table/table_jui.css" />
    <@javascript "/static/js/jquery.dataTables.min.js" />
    <@javascript "/static/js/table.js" />
</head>
<body class="index">
<#if mappings?has_content>
<table id="currentForms">
    <thead>
        <tr>
            <th>Form</th>
            <th>Created At</th>
            <th>Updated At</th>
        </tr>
    </thead>
    <tbody>
        <#list mappings as mapping>
        <tr>
            <td><a id="${mapping.key.id}" href="<@servletUrl mapping.value />">${mapping.key.id}</a></td>
            <td>${mapping.key.createdAt}</td>
            <td>${mapping.key.updatedAt}</td>
        </tr>
        </#list>
    </tbody>
</table>
</#if>
<div id="newForm">
    Click <a href="<@servletUrl newForm />">here</a> for a new form.
</div>
</body>
</html>
