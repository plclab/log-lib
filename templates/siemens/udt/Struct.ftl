TYPE "${name}"
VERSION : 0.1
   STRUCT
<#if vars?has_content>
<#list vars as var>
      ${var.name} : ${var.type}<#if var.value??> := ${var.value}</#if>;
</#list>
</#if>
   END_STRUCT;

END_TYPE
