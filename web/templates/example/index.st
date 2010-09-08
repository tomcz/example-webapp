<html>
<head>
    <title>Current Forms</title>
    $stylesheet(href="static/table/table_jui.css")$
    $javascript(src="static/js/jquery.dataTables.min.js")$
    $javascript(src="static/js/table.js")$
</head>
<body class="index">
$if(!mappings.empty)$
<table id="currentForms">
    <thead>
        <tr>
            <th>Form</th>
            <th>Created At</th>
            <th>Updated At</th>
        </tr>
    </thead>
    <tbody>
        $mappings:{ mapping |
        <tr>
            <td><a href="$mapping.value$">$mapping.key.id$</a></td>
            <td>$mapping.key.createdAt$</td>
            <td>$mapping.key.updatedAt$</td>
        </tr>
        }$
    </tbody>
</table>
$endif$
<div id="newForm">
    Click <a href="$newForm$">here</a> for a new form.
</div>
</body>
</html>
