<#macro servletUrl url>${servletPath}${url}</#macro>
<#macro javascript src>
    <script type="text/javascript" src="${contextPath}${src}?${version}"></script>
</#macro>
<#macro stylesheet href>
    <link rel="stylesheet" href="${contextPath}${href}?${version}" type="text/css">
</#macro>
<#macro input field>
    <div class="field <#if !field.valid>error</#if>">
        <input type="text" id="${field.name}" name="${field.name}" value="${field.value}">
        <#if !field.valid><div class="${field.name} message">${field.message}</div></#if>
    </div>
</#macro>
<#macro select field options>
    <div class="field <#if !field.valid>error</#if>">
        <select id="${field.name}" name="${field.name}">
            <#list options as option>
            <option value="${option.value}" <#if option.selected>selected</#if>>${option.value}</option>
            </#list>
        </select>
        <#if !field.valid><div class="${field.name} message">${field.message}</div></#if>
    </div>
</#macro>