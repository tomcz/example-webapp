<#include "setup.ftl" />
<html>
<head>
    <title>Form</title>
    <@javascript "/static/js/form.js" />
</head>
<body class="form">
<div class="form-container">
    <form id="documentForm" method="post">
        <@input document.one />
        <@select document.two fieldOptions />
        <@input document.date />
        <input type="submit" name="submit" class="submit" value="Submit">
    </form>
</div>
<p>
    Click <a href="<@servletUrl indexLink />">here</a> to go to the list of forms.
</p>
</body>
</html>
