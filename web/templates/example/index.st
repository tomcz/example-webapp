<html>
<head>
    <title>Current Forms</title>
    <link rel="stylesheet" href="$base$/static/table/table_jui.css" type="text/css"/>
    <script type="text/javascript" src="$base$/static/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript" src="$base$/static/js/table.js"></script>
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
