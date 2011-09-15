<#include "setup.ftl" />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>${title!?html}</title>
    <@stylesheet "/static/theme/jquery-ui-1.8.custom.css" />
    <@stylesheet "/static/css/main.css" />
    <@javascript "/static/js/jquery-1.4.2.min.js" />
    <@javascript "/static/js/jquery-ui-1.8.custom.min.js" />
    <@javascript "/static/js/buttons.js" />
    ${head!?trim}
</head>
<body class='${page.getProperty("body.class")}'>
<div id="container">
    <div id="header">
        <h1>${title!?html}</h1>
    </div>
    <div id="content">
        ${body!?trim}
    </div>
</div>
</body>
</html>
