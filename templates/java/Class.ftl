package ${class.package};
<#if class.imports?has_content>

<#list class.imports as import>
import ${import};
</#list>
</#if>

<#if class.modifiers?has_content><#list class.modifiers as modifier>${modifier} </#list></#if>class ${class.name} <#if class.extends??>extends ${class.extends} </#if>{
    <#if class.constants?has_content>

    <#list class.constants as const>
    <#if const.modifiers?has_content><#list const.modifiers as modifier>${modifier} </#list></#if>${const.type} ${const.name} = ${const.value};
    </#list>
    </#if>
    <#if class.vars?has_content>

    <#list class.vars as var>
    <#if var.modifiers?has_content><#list var.modifiers as modifier>${modifier} </#list></#if>${var.type} ${var.name}<#if var.value??> = ${var.value}</#if>;
    </#list>
    </#if>
    
    <#if class.constructors?has_content>
    <#list class.constructors as constructor>

    <#if constructor.modifiers?has_content><#list constructor.modifiers as modifier>${modifier} </#list></#if>${class.name}(<#if constructor.args?has_content><#list constructor.args as arg>${arg.type} ${arg.name}<#sep>, </#sep></#list></#if>) {
        <#if constructor.instructions?has_content>
        <#list constructor.instructions as instruction>
        ${instruction}
        </#list>
        </#if>
    }
    </#list>
    </#if>
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
