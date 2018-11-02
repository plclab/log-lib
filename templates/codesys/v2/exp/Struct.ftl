(* @NESTEDCOMMENTS := 'No' *)
(* @PATH := '<#if path?has_content><#list path as dir>\/${dir}</#list></#if>' *)
(* @OBJECTFLAGS := '0, 8' *)
TYPE ${name} :
STRUCT
<#if vars?has_content>
<#list vars as var>
    ${var.name}: ${var.type}<#if var.value??> := ${var.value}</#if>;
</#list>
</#if>
END_STRUCT
END_TYPE

(* @END_DECLARATION := '0' *)
