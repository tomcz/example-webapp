<html>
<head>
    <title>Form</title>
    <script type="text/javascript" src="$base$/static/js/form.js"></script>
</head>
<body class="form">
<form id="documentForm" method="post">
    $input(field=document.one)$
    $input(field=document.two)$
    $input(field=document.date)$
    <input type="submit" name="submit" value="Submit" />
</form>
<p>
    Click <a href="$indexLink$">here</a> to go to the list of forms.
</p>
</body>
</html>
