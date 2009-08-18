<html>
<head>
    <title>Current Forms</title>
</head>
<body>
$if(!mappings.empty)$
<ul>
$mappings:{ mapping |
    <li><a href="$mapping.value$">$mapping.key$</a></li>
}$
</ul>
$endif$
<p>
    Click <a href="$newForm$">here</a> for a new form.
</p>
</body>
</html>
