<#ftl strip_whitespace=true>
<#import "/spring.ftl" as spring />
<#assign htmlEscape=true in spring>
<#assign xhtmlCompliant=false in spring>
<#macro servletUrl url>${servletPath}${url}</#macro>
<#macro javascript src>
    <script type="text/javascript" src="${contextPath}${src}?${version}"></script>
</#macro>
<#macro stylesheet href>
    <link rel="stylesheet" href="${contextPath}${href}?${version}" type="text/css">
</#macro>
<#macro input field>
    <div class="field <#if !field.valid>error</#if>">
        <input type="text" id="${field.name!?html}" name="${field.name!?html}" value="${field.value!?html}">
        <#if !field.valid><div class="${field.name!?html} message">${field.message!?html}</div></#if>
    </div>
</#macro>
<#macro select field options>
    <div class="field <#if !field.valid>error</#if>">
        <select id="${field.name!?html}" name="${field.name!?html}">
            <#list options as option>
            <option value="${option.value!?html}" <#if option.selected>selected</#if>>${option.value!?html}</option>
            </#list>
        </select>
        <#if !field.valid><div class="${field.name!?html} message">${field.message!?html}</div></#if>
    </div>
</#macro>