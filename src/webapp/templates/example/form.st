<html>
<head>
    <title>Form</title>
    $javascript(src="/static/js/form.js")$
</head>
<body class="form">
<div class="form-container">
    <form id="documentForm" method="post">
        $input(field=document.one)$
        $input(field=document.two)$
        $input(field=document.date)$
        <input type="submit" name="submit" class="submit" value="Submit">
    </form>
</div>
<p>
    Click <a href="$indexLink$">here</a> to go to the list of forms.
</p>
</body>
</html>
