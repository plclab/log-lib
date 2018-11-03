package ${class.package};
<#if class.imports?has_content>

<#list class.imports as import>
import ${import};
</#list>
</#if>

<#if class.modifiers?has_content><#list class.modifiers as modifier>${modifier} </#list></#if>class ${class.name} {

    <#if class.vars?has_content>
    <#list class.vars as var>
    <#if var.modifiers?has_content><#list var.modifiers as modifier>${modifier} </#list></#if>${var.type} ${var.name}<#if var.value??> = ${var.value}</#if>;
    </#list>
    </#if>
    
    
    public ${class.name}() {
         //Default constructor
    }
    <#if class.methods?has_content>
    <#list class.methods as method>

    <#if method.modifiers?has_content><#list method.modifiers as modifier>${modifier} </#list></#if>${method.returnType} ${method.name}(<#if method.args?has_content><#list method.args as arg>${arg.type} ${arg.name}<#sep>, </#sep></#list></#if>) {
        <#if method.instructions?has_content>
        <#list method.instructions as instruction>
        ${instruction}
        </#list>
        </#if>
    }
    </#list>
    </#if>

}
