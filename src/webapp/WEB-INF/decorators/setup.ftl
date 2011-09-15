<#ftl strip_whitespace=true>
<#macro servletUrl url>${servletPath}${url}</#macro>
<#macro javascript src>
    <script type="text/javascript" src="${contextPath}${src}?${version}"></script>
</#macro>
<#macro stylesheet href>
    <link rel="stylesheet" href="${contextPath}${href}?${version}" type="text/css">
</#macro>