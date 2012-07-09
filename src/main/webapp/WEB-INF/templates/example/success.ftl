<#include "setup.ftl" />
<html>
<head>
    <title>Success</title>
</head>
<body class="success">
<p>
    You submitted:<br>
    <span id="one">${document.one.value}</span><br>
    <span id="two">${document.two.value}</span><br>
    <span id="date">${document.date.value}</span><br>
</p>
<p>
    Click <a id="back" href="<@servletUrl oldFormLink />">here</a> to go back to the form.
</p>
<p>
    Click <a id="new" href="<@servletUrl newFormLink />">here</a> to start again.
</p>
<p>
    Click <a id="index" href="<@servletUrl indexLink />">here</a> to go to the list of forms.
</p>
</body>
</html>
