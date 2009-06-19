<html>
<head>
    <title>Current Forms</title>
</head>
<body>
$if(!mappings.empty)$
<ul>
$mappings:{ mapping |
    <li><a href="$base$$mapping.value$">$mapping.key$</a></li>
}$
</ul>
$endif$
<p>
    Click <a href="$base$$newForm$">here</a> for a new form.
</p>
</body>
</html>
