<#macro bodyClass>${page.getProperty("body.class")}</#macro>
<#macro javascript src>
    <script type="text/javascript" src="${contextPath}${src}?${version}"></script>
</#macro>
<#macro stylesheet href>
    <link rel="stylesheet" href="${contextPath}${href}?${version}" type="text/css">
</#macro>