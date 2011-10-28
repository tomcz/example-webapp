<#include "setup.ftl" />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>${title!}</title>
    <@stylesheet "/static/theme/jquery-ui-1.8.custom.css" />
    <@stylesheet "/static/css/main.css" />
    <@javascript "/static/js/jquery-1.4.2.min.js" />
    <@javascript "/static/js/jquery-ui-1.8.custom.min.js" />
    <@javascript "/static/js/buttons.js" />
    <#noescape>${head!?trim}</#noescape>
</head>
<body class="<@bodyClass />">
<div id="container">
    <div id="header">
        <h1>${title!}</h1>
    </div>
    <div id="content">
        <#noescape>${body!?trim}</#noescape>
    </div>
</div>
</body>
</html>
